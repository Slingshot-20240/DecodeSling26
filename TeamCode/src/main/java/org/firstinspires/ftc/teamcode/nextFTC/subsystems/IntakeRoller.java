package org.firstinspires.ftc.teamcode.nextFTC.subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class IntakeRoller implements Subsystem {
    public static final IntakeRoller INSTANCE = new IntakeRoller();
    private IntakeRoller() { }

    private final MotorEx intakeRoller = new MotorEx("intakeRoller");

    private final ControlSystem lift_controller = ControlSystem.builder()
            .posPid(0.005, 0, 0) //lift_controller
            .elevatorFF(0) //compensates for gravity
            .build();

    private enum intake_speeds {
        IN(0.7),
        OUT(-0.7),
        IDLE(0.0);

        private final double intake_speeds;
        intake_speeds(double pos) {
            this.intake_speeds = pos;
        }
        public double getSpeed() {
            return intake_speeds;
        }
    }

    public Command in = new RunToVelocity(
            lift_controller,
            intake_speeds.IN.getSpeed()
    ).requires(this);

    public Command out = new RunToVelocity(
            lift_controller,
            intake_speeds.OUT.getSpeed()
    ).requires(this);

    public Command idle = new RunToVelocity(
            lift_controller,
            intake_speeds.IDLE.getSpeed()
    ).requires(this);


    @Override
    public void periodic() {
        intakeRoller.setPower(lift_controller.calculate(intakeRoller.getState()));
    }
}