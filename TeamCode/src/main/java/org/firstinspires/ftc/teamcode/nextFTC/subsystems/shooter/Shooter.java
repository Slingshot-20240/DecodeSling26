package org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Shooter implements Subsystem {
    // NextFTC Shooter Instance
    public static final Shooter INSTANCE = new Shooter();
    private Shooter() { }

    // NextFTC Hardware
    private final MotorEx autoOuttakeL = new MotorEx("outtakeLeft"); // 0 CH
    private final MotorEx autoOuttakeR = new MotorEx("outtakeRight");
    private final ServoEx autoVariableHood = new ServoEx("variableHood");// 2 CH

    // TeleOp Hardware and Constructor
    public DcMotorEx teleOuttakeL;
    public DcMotorEx teleOuttakeR;
    public Servo teleVariableHood;

    public Shooter (HardwareMap hwMap) {
        teleOuttakeL = hwMap.get(DcMotorEx.class, "outtakeLeft");
        teleOuttakeR = hwMap.get(DcMotorEx.class, "outtakeRight");
        teleVariableHood = hwMap.get(Servo.class, "variableHood");

        teleOuttakeL.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    // SHOOTER CALCULATIONS
    // ---------------------------------

    private static final double launchHeight = 0; // TODO update this with CAD
    private static final double g = 9.81;
    private static final double H = .39 - launchHeight; // y distance, m distance from launch height to a little above hole on goal
    private static double shootVel;
    private static double R; // TODO: update R with April Tag value

    // Hardcoded distances from tip of triangle points to the middle of the goal (meters)
    // TODO: ADJUST FOR ROBOT DIMENSIONS
    static double front_dist = 2.1844;
    static double back_dist = 3.2004;

    public double calculateShooterVel() {
        double convertedVel = 0;
        double power = 0;
        // TODO: update to RPM
        shootVel = Math.sqrt(H * g + g * Math.sqrt(Math.pow(R, 2) + Math.pow(H, 2)));
        // .096 is the diameter in m of the flywheel
        power = convertVelToRPM(shootVel);
        return power;
    }

    public static double convertVelToRPM(double vel) {
        double newVel = (vel * 60) / 2 * .096 * Math.PI; // vel in RPM
        return newVel/6000;
    }

    // HOOD ANGLE CALCULATIONS - TODO: ASK RUPAL
    // ---------------------------------
    private static double hoodAngle;

    public double calculateHoodAngle() {
        hoodAngle = Math.atan(Math.pow(Shooter.getShootVel(), 2)/(g * R)) / 2 * Math.PI;
        return hoodAngle;
    }

    // AUTONOMOUS COMMANDS - NextFTC
    // ----------------------------------

    private final ControlSystem outtake_controller = ControlSystem.builder()
            .velPid(0.005, 0, 0) //out_controller
            //.elevatorFF(0) //compensates for gravity
            .build();

    public enum outtakeVels {
        PID_SHOOT(shootVel),
        // 5.059
        HARDCODED_SHOOT_TRIANGLE(convertVelToRPM(Math.sqrt(H * g + g * Math.sqrt(Math.pow(front_dist, 2) + Math.pow(H, 2))))),
        // 5.954
        HARDCODED_SHOOT_BACK(convertVelToRPM(Math.sqrt(H * g + g * Math.sqrt(Math.pow(back_dist, 2) + Math.pow(H, 2))))),
        IDLE(0);

        private final double outtake_vels;

        outtakeVels(double pos) {
            this.outtake_vels = pos;
        }
        public double getOuttakeVel() {
            return outtake_vels;
        }
    }

    public Command pidShoot = new RunToVelocity(
            outtake_controller,
            outtakeVels.PID_SHOOT.getOuttakeVel()
    ).requires(this);

    public Command triangleShoot = new RunToVelocity(
            outtake_controller,
            outtakeVels.HARDCODED_SHOOT_TRIANGLE.getOuttakeVel()
    ).requires(this);

    public Command backShoot = new RunToVelocity(
            outtake_controller,
            outtakeVels.HARDCODED_SHOOT_BACK.getOuttakeVel()
    ).requires(this);

    public Command idle = new RunToVelocity(
            outtake_controller,
            outtakeVels.IDLE.getOuttakeVel()
    );

    // TODO: test these values
    public Command upTriangle = new SetPosition(
            autoVariableHood,
            0.1
    ).requires(this);


    public Command downBack = new SetPosition(
            autoVariableHood,
            0.2
    ).requires(this);

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

    public void setHoodAngle(double angle) {
        teleVariableHood.setPosition(angle);
    }

    public static double getShootVel() {
        return shootVel;
    }

    // TODO: test these two
    public void hoodToBackTriPos() {
        teleVariableHood.setPosition(Math.atan(Math.pow(Shooter.getShootVel(), 2)/(g * back_dist)) / 2 * Math.PI);
    }

    public void hoodToFrontTriPos() {
        teleVariableHood.setPosition(Math.atan(Math.pow(Shooter.getShootVel(), 2)/(g * front_dist)) / 2 * Math.PI);
    }

    public void teleShootFromBack() {
        teleOuttakeL.setPower(outtakeVels.HARDCODED_SHOOT_BACK.getOuttakeVel());
        teleOuttakeR.setPower(outtakeVels.HARDCODED_SHOOT_BACK.getOuttakeVel());
    }

    public void teleShootFromFront() {
        teleOuttakeL.setPower(outtakeVels.HARDCODED_SHOOT_TRIANGLE.getOuttakeVel());
        teleOuttakeR.setPower(outtakeVels.HARDCODED_SHOOT_TRIANGLE.getOuttakeVel());
    }
}