package org.firstinspires.ftc.teamcode.nextFTC.subsystems.templates;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;

public class Lift implements Subsystem {
    public static final Lift INSTANCE = new Lift();
    private Lift() { }

    private final MotorEx liftl = new MotorEx("liftl");
    private final MotorEx liftr = new MotorEx("liftr");


    private final ControlSystem lift_controller = ControlSystem.builder()
            .posPid(0.005, 0, 0) //lift_controller
            .elevatorFF(0) //compensates for gravity (as i like to call it anti-gravity) hehehe
            .build();

    private enum lift_positions {
        DOWN (0),
        TRANSFER (400),
        LOW_BASKET (1400),
        HIGH_BASKET(2000);
        private final int lift_position;
        lift_positions(int pos) {
            this.lift_position = pos;
        }
        public int getPosition() {
            return lift_position;
        }
    }

    public Command toDown = new RunToPosition(
            lift_controller,
            lift_positions.DOWN.getPosition()
    ).requires(this);

    public Command toTransfer = new RunToPosition(
            lift_controller,
            lift_positions.TRANSFER.getPosition()
    ).requires(this);

    public Command toLow = new RunToPosition(
            lift_controller,
            lift_positions.LOW_BASKET.getPosition()
    ).requires(this);

    public Command toHigh = new RunToPosition(
            lift_controller,
            lift_positions.HIGH_BASKET.getPosition()
    ).requires(this);

    @Override
    public void periodic() {
        liftl.setPower(lift_controller.calculate(liftl.getState()));
        liftr.setPower(lift_controller.calculate(liftr.getState()));

    }
}