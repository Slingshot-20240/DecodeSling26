package org.firstinspires.ftc.teamcode.fsm;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.IntakeRoller;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.Transfer;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.VariableHood;

public class FSM {

    public Robot robot;
    public FSMStates state = FSMStates.BASE_STATE;
    public ControlType type = ControlType.HARDCODED_CONTROL;
    private GamepadMapping gamepad;

    public FSM(HardwareMap hardwareMap, GamepadMapping gamepad) {
        robot = new Robot(hardwareMap);
        this.gamepad = gamepad;
    }

    public void update() {

        // follower class
        // can do getHeading()
        // can do getPose() for x y values
        robot.driverControlled.schedule();

        switch (state) {
            case BASE_STATE:
                // TODO: change buttons to what Viktor & Arhaan want, also test button jawns
                // Hardcoded Control only, set hood to up position for scoring



                if (type == ControlType.HARDCODED_CONTROL) {
                    VariableHood.INSTANCE.upTriangle.schedule();
                }

                gamepad.intake
                        .toggleOnBecomesTrue()
                        .whenBecomesTrue(IntakeRoller.INSTANCE.in)
                        .whenBecomesFalse(IntakeRoller.INSTANCE.idle);

                if (gamepad.pidShoot.get() || gamepad.shootTriangle.get() || gamepad.shootBack.get()) {
                    state = FSMStates.SHOOTING;
                }

                if (gamepad.park.get()) {
                    state = FSMStates.PARK;
                }
                break;

            case SHOOTING:
                // Hardcoded control AND we're at the back shooting zone
//                if (type == ControlType.HARDCODED_CONTROL && gamepad.shootBack.get()) {
//                    gamepad.shootBack
//                            .whenTrue(Shooter.INSTANCE.backShoot.and(VariableHood.INSTANCE.downBack))
//                            // TODO wait needed?
//                            .whenFalse(Shooter.INSTANCE.idle);
//                }
//                // Hardcoded control AND we're at the tip of the triangle of the front shooting zone
//                else if (type == ControlType.HARDCODED_CONTROL && gamepad.shootTriangle.get()) {
//                    gamepad.shootTriangle
//                            .whenTrue(Shooter.INSTANCE.triangleShoot.and(VariableHood.INSTANCE.upTriangle))
//                            // TODO wait needed?
//                            .whenFalse(Shooter.INSTANCE.idle);
//                }
//                // PID control that adjusts depending on our distance - TO BE IMPLEMENTED
//                else if (type == ControlType.PID_CONTROL && gamepad.pidShoot.get()) {
//                    gamepad.pidShoot
//                            .whenTrue(Shooter.INSTANCE.pidShoot)
//                            // TODO wait somehow
//                            .whenFalse(Shooter.INSTANCE.idle);

                            // TODO: THEORETICAL METHODS
                            // ColorPicker.INSTANCE.chooseColor.schedule() -> maybe Instant command
                            // VariableHood.INSTANCE.adjust.schedule()
                //}
                // Return to base state if shooting is false
//                if (!gamepad.pidShoot.get() || !gamepad.shootTriangle.get() || !gamepad.shootBack.get()) {
//                    state = FSMStates.BASE_STATE;
//                }
//                break;

            case PARK:
                gamepad.park
                        .toggleOnBecomesTrue();
                        // TODO Make if we do park
                        // .whenBecomesTrue(Park.INSTANCE.extend)
                        // .toggleOnBecomesFalse(Park.INSTANCE.retract);

                if (!gamepad.park.get()) {
                    state = FSMStates.BASE_STATE;
                }

            case TRANSFER:
                // TODO we may not want to have a seperate state for this
                gamepad.transfer
                        .toggleOnBecomesTrue()
                        .whenBecomesTrue(Transfer.INSTANCE.up);
                        // TODO Constantly running?
                        // .whenBecomesFalse(Transfer.INSTANCE.idle);

//                if (!gamepad.transfer.get()) {
//                    state = FSMStates.BASE_STATE;
//                }
                //yo - idk ts sorry broskis
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

