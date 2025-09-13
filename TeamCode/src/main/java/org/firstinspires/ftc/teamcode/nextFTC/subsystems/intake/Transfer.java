package org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class Transfer implements Subsystem {
    public static final Transfer INSTANCE = new Transfer();
    private Transfer() { }

    private final MotorEx transfer = new MotorEx("transfer");


    private final ControlSystem transfer_controller = ControlSystem.builder()
            .posPid(0.005, 0, 0) //transfer_controller
            .elevatorFF(0) //compensates for gravity
            .build();

    private enum transfer_states {
        UP(0.2),
        IDLE(0),
        DOWN(-0.2);

        private final double transfer_states;
        transfer_states(double vel) {
            this.transfer_states = vel;
        }
        public double getSpeed() {
            return transfer_states;
        }
    }

    public Command up = new RunToVelocity(
            transfer_controller,
            Transfer.transfer_states.UP.getSpeed()
    ).requires(this);

    public Command idle = new RunToVelocity(
            transfer_controller,
            Transfer.transfer_states.IDLE.getSpeed()
    ).requires(this);

    public Command down = new RunToVelocity(
            transfer_controller,
            Transfer.transfer_states.DOWN.getSpeed()
    ).requires(this);



    @Override
    public void periodic() {
        transfer.setPower(transfer_controller.calculate(transfer.getState()));
    }

}