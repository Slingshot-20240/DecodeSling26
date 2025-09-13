package org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class IntakeRoller implements Subsystem {
    public static final IntakeRoller INSTANCE = new IntakeRoller();
    private IntakeRoller() { }

    private final MotorEx intakeRollerL = new MotorEx("intakeRollerLeft");
    private final MotorEx intakeRollerR = new MotorEx("intakeRollerRight");

    private final ControlSystem intake_controller = ControlSystem.builder()
            .posPid(0.005, 0, 0) //intake_controller
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
            intake_controller,
            intake_speeds.IN.getSpeed()
    ).requires(this);

    public Command out = new RunToVelocity(
            intake_controller,
            intake_speeds.OUT.getSpeed()
    ).requires(this);

    public Command idle = new RunToVelocity(
            intake_controller,
            intake_speeds.IDLE.getSpeed()
    ).requires(this);

    public void setIntakePower(double lPower, double rPower) {
        intakeRollerL.setPower(lPower);
        intakeRollerR.setPower(rPower);
    }

    @Override
    public void periodic() {
        intakeRollerL.setPower(intake_controller.calculate(intakeRollerL.getState()));
        intakeRollerR.setPower(intake_controller.calculate(intakeRollerR.getState()));
    }
}