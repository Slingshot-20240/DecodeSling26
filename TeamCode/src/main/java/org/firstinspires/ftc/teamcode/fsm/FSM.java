package org.firstinspires.ftc.teamcode.fsm;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.IntakeRoller;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.OuttakeMotor;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.VariableHood;

public class FSM {

    public Robot robot;
    public FSMStates state = FSMStates.BASE_STATE;
    private GamepadMapping gamepad;

    public FSM(HardwareMap hardwareMap, GamepadMapping gamepad) {
        robot = new Robot(hardwareMap);
        this.gamepad = gamepad;
    }

    public void update() {

        robot.driverControlled.schedule();

        switch (state) {
            case BASE_STATE:
                // TODO: change buttons to what Viktor & Arhaan want, also test button jawns

                VariableHood.INSTANCE.up.schedule();
                // Turret at rest
                // Intake lowered
                // Park retracted

                gamepad.intake
                        .toggleOnBecomesTrue()
                        .whenBecomesTrue(IntakeRoller.INSTANCE.in)
                        .whenBecomesFalse(IntakeRoller.INSTANCE.idle);

                if (gamepad.shoot.get()) {
                    state = FSMStates.SHOOTING;
                }

                if (gamepad.park.get()) {
                    state = FSMStates.PARK;
                }
                break;

            case SHOOTING:
                // choose color (concurrent)
                // adjust angle (concurrent)
                // hold to shoot with a small wait

                // TODO: THEORETICAL METHODS
                // ColorPicker.INSTANCE.chooseColor.schedule() -> maybe Instant command
                // VariableHood.INSTANCE.adjust.schedule()
                gamepad.shoot
                        .whenTrue(OuttakeMotor.INSTANCE.shoot)
                        // TODO wait somehow
                        .whenFalse(OuttakeMotor.INSTANCE.idle);

                if (!gamepad.shoot.get()) {
                    state = FSMStates.BASE_STATE;
                }
                break;

            case PARK:
                gamepad.park
                        .toggleOnBecomesTrue();
                        // TODO Make if we do park
                        // .whenBecomesTrue(Park.INSTANCE.extend)
                        // .toggleOnBecomesFalse(Park.INSTANCE.retract);

                if (!gamepad.park.get()) {
                    state = FSMStates.BASE_STATE;
                }
        }

    }

    public enum FSMStates {
        BASE_STATE,
        SHOOTING,
        PARK
    }
}
