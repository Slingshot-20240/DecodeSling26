package org.firstinspires.ftc.teamcode.nextFTC.subsystems.templates;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;

public class Motor implements Subsystem {
    public static final Motor INSTANCE = new Motor();
    private Motor() { }

    private final MotorEx motor = new MotorEx("motor");


    private final ControlSystem motor_controller = ControlSystem.builder()
            .posPid(0.005, 0, 0) //motor_controller
            .elevatorFF(0) //compensates for gravity (as i like to call it anti-gravity) hehehe
            .build();

    private enum motor_positions {
        DOWN (0),
        TRANSFER (400),
        LOW_BASKET (1400),
        HIGH_BASKET(2000);
        private final int motor_position;
        motor_positions(int pos) {
            this.motor_position = pos;
        }
        public int getPosition() {
            return motor_position;
        }
    }

    public Command toDown = new RunToPosition(
            motor_controller,
            motor_positions.DOWN.getPosition()
    ).requires(this);

    public Command toTransfer = new RunToPosition(
            motor_controller,
            motor_positions.TRANSFER.getPosition()
    ).requires(this);

    public Command toLow = new RunToPosition(
            motor_controller,
            motor_positions.LOW_BASKET.getPosition()
    ).requires(this);

    public Command toHigh = new RunToPosition(
            motor_controller,
            motor_positions.HIGH_BASKET.getPosition()
    ).requires(this);

    @Override
    public void periodic() {
        motor.setPower(motor_controller.calculate(motor.getState()));
    }
}