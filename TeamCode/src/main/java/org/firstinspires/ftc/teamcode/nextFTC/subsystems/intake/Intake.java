package org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class Intake implements Subsystem {
    // NextFTC Intake Instance
    public static final Intake INSTANCE = new Intake();
    private Intake() { }

    // NextFTC Hardware
    private final MotorEx autoIntakeRoller = new MotorEx("intake");

    // TeleOp Hardware and Constructor
    public DcMotorEx teleIntakeRoller;

    public Intake(HardwareMap hwMap) {
        teleIntakeRoller = hwMap.get(DcMotorEx.class, "intake");
    }

    // AUTONOMOUS COMMANDS - NextFTC
    // ----------------------------------

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

    @Override
    public void periodic() {
        autoIntakeRoller.setPower(intake_controller.calculate(autoIntakeRoller.getState()));
    }

    // TELEOP METHODS
    // ----------------------------------

    public void intakeOn() {
        autoIntakeRoller.setPower(1);
    }

    public void intakeOff() {
        autoIntakeRoller.setPower(0);
    }

    public void intakeReverse() {
        autoIntakeRoller.setPower(-1);
    }
}