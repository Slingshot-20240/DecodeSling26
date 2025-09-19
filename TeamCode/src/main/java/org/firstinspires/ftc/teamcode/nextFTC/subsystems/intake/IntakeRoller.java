package org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class IntakeRoller implements Subsystem {

    public static final IntakeRoller INSTANCE = new IntakeRoller();
    private IntakeRoller() { }

    private final MotorEx intakeRoller = new MotorEx("intake");

    private final ControlSystem intake_controller = ControlSystem.builder()
            .posPid(0.005, 0, 0) //intake_controller
            //.elevatorFF(0) //compensates for gravity
            .build();

    private enum intake_vels {
        IN(0.7),
        OUT(-0.7),
        IDLE(0.0);

        private final double intake_vels;
        intake_vels(double pos) {
            this.intake_vels = pos;
        }
        public double getSpeed() {
            return intake_vels;
        }
    }

    public Command in = new RunToVelocity(
            intake_controller,
            intake_vels.IN.getSpeed()
    ).requires(this);

    public Command out = new RunToVelocity(
            intake_controller,
            intake_vels.OUT.getSpeed()
    ).requires(this);

    public Command idle = new RunToVelocity(
            intake_controller,
            intake_vels.IDLE.getSpeed()
    ).requires(this);


    public void setIntakePower(double power) {
        intakeRoller.setPower(power);
    }

    @Override
    public void periodic() {
        intakeRoller.setPower(intake_controller.calculate(intakeRoller.getState()));
    }
}