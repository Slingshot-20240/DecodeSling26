package org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;

public class Shooter implements Subsystem {
    // NextFTC Shooter Instance
    public static final Shooter INSTANCE = new Shooter();
    private Shooter() { }

    // NextFTC Motors
    private final MotorEx autoOuttakeL = new MotorEx("outtakeLeft"); // 0 CH
    private final MotorEx autoOuttakeR = new MotorEx("outtakeRight"); // 2 CH

    // TeleOp Motors and Constructor
    public DcMotorEx teleOuttakeL;
    public DcMotorEx teleOuttakeR;

    public Shooter (HardwareMap hwMap) {
        teleOuttakeL = hwMap.get(DcMotorEx.class, "outtakeLeft");
        teleOuttakeR = hwMap.get(DcMotorEx.class, "outtakeRight");

        teleOuttakeL.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    // SHOOTER CALCULATIONS
    // ---------------------------------

    private static final double launchHeight = 0; // TODO update this with CAD
    private static final double g = 9.81;
    private static final double H = 39 - launchHeight; // y distance, cm distance from launch height to a little above hole on goal
    private static double R; // x distance, get from AprilTag

    private static final double shootVel = Math.sqrt(H * g + g * Math.sqrt(Math.pow(R, 2) + Math.pow(H, 2)));

    // AUTONOMOUS COMMANDS - NextFTC
    // ----------------------------------

    private final ControlSystem outtake_controller = ControlSystem.builder()
            .posPid(0.005, 0, 0) //out_controller
            .elevatorFF(0) //compensates for gravity
            .build();

    public enum outtake_vels {

        PID_SHOOT(shootVel),
        HARDCODED_SHOOT_TRIANGLE(1), // TODO: CALC WITH FIELD
        HARDCODED_SHOOT_BACK(1), // TODO: CALC WITH FIELD
        IDLE(0);

        private final double outtake_vels;

        outtake_vels(double pos) {
            this.outtake_vels = pos;
        }
        public double getOuttakeVel() {
            return outtake_vels;
        }
    }

    public Command pidShoot = new RunToVelocity(
            outtake_controller,
            outtake_vels.PID_SHOOT.getOuttakeVel()
    ).requires(this);

    public Command triangleShoot = new RunToVelocity(
            outtake_controller,
            outtake_vels.HARDCODED_SHOOT_TRIANGLE.getOuttakeVel()
    ).requires(this);

    public Command backShoot = new RunToVelocity(
            outtake_controller,
            outtake_vels.HARDCODED_SHOOT_BACK.getOuttakeVel()
    ).requires(this);

    public Command idle = new RunToVelocity(
            outtake_controller,
            outtake_vels.IDLE.getOuttakeVel()
    );

    @Override
    public void periodic() {
        autoOuttakeL.setPower(outtake_controller.calculate(autoOuttakeL.getState()));
        autoOuttakeR.setPower(outtake_controller.calculate(autoOuttakeR.getState()));

    }

    // TELEOP METHODS
    // ----------------------------------

    public void setShooterPower(double power) {
        teleOuttakeL.setPower(power);
        teleOuttakeR.setPower(power);
    }

    public static double getShootVel() {
        return shootVel;
    }
}