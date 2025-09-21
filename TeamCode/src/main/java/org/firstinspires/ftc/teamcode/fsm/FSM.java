package org.firstinspires.ftc.teamcode.fsm;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.transfer.Transfer;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.turret.Turret;

public class FSM {
    // GENERAL ROBOT STATES + CLASSES
    public Robot robot;
    public FSMStates state = FSMStates.BASE_STATE;
    public ControlType type = ControlType.HARDCODED_CONTROL;
    private GamepadMapping gamepad;

    // SUBSYSTEMS
    private Intake intake;
    private Turret turret;
    private Transfer transfer;
    private Shooter shooter;
    private Drivetrain drivetrain;

    public FSM(HardwareMap hardwareMap, GamepadMapping gamepad) {
        robot = new Robot(hardwareMap, gamepad);
        this.gamepad = robot.controls;

        intake = robot.intake;
        turret = robot.turret;
        transfer = robot.transfer;
        shooter = robot.shooter;

        drivetrain = robot.drivetrain;
    }

    public void update() {
        // Updates driver controls here as well
        drivetrain.update();
        // Updates all other controls
        gamepad.update();

        // TODO: ODO IN TELE
        // follower class
        // can do getHeading()
        // can do getPose() for x y values

        switch (state) {
            case BASE_STATE:
                // if hardcoded control, set hood to back shooting position
                if (type.equals(ControlType.HARDCODED_CONTROL)) {
                    shooter.hoodToBackTriPos();
                }
                // shooter off :)
                shooter.setShooterPower(0);

                // Going to try transfer always on, may need to add some delays
                transfer.transferOff();

                // Intake button toggle, intake on/off
                if (gamepad.intake.value()) {
                    intake.intakeOn();
                } else if (!gamepad.intake.value())
                    intake.intakeOff();

                if (gamepad.pidShoot.value() || gamepad.shootTriangle.value() || gamepad.shootBack.value()) {
                    state = FSMStates.SHOOTING;
                }

                if (gamepad.park.value()) {
                    state = FSMStates.PARK;
                }

                break;

            case SHOOTING:
                turret.setTurretPos(turret.calcTurretVal(), 1);
                // turn transfer off while shooting until back to base state
                transfer.transferOff();
                // Hardcoded control AND we're at the back shooting zone
                if (type == ControlType.HARDCODED_CONTROL && gamepad.shootBack.value()) {
                    shooter.hoodToBackTriPos();
                    shooter.teleShootFromBack();
                }
                // Hardcoded control AND we're at the tip of the triangle of the front shooting zone
                else if (type == ControlType.HARDCODED_CONTROL && gamepad.shootTriangle.value()) {
                    shooter.hoodToFrontTriPos();
                    shooter.teleShootFromFront();
                }
                // PID control that adjusts depending on our distance - TO BE IMPLEMENTED
                else if (type == ControlType.PID_CONTROL && gamepad.pidShoot.value()) {
                   shooter.setShooterPower(shooter.calculateShooterVel());
                   shooter.setHoodAngle(shooter.calculateHoodAngle());
                }
                // Return to base state if shooting is false
                // TODO: this may not work
                if (gamepad.pidShoot.changed() || gamepad.shootTriangle.changed() || gamepad.shootBack.changed()) {
                    state = FSMStates.BASE_STATE;
                    gamepad.resetMultipleControls(gamepad.pidShoot, gamepad.shootBack, gamepad.shootTriangle);
                }
                break;

            case PARK:
                // TODO Make if we do park
                // park.extend()

                if (!gamepad.park.value()) {
                    state = FSMStates.BASE_STATE;
                    // park.retract();
                }

        }
    }




    public enum FSMStates {
        BASE_STATE,
        SHOOTING,
        PARK,
        TRANSFER
    }

    public enum ControlType {
        HARDCODED_CONTROL,
        PID_CONTROL
    }

}
