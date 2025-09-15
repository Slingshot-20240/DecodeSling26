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
//    public static final Shooter INSTANCE = new Shooter();
//    private Shooter() { }

//    private MotorEx outtakeL; // 0
//    private MotorEx outtakeR;

    public DcMotorEx outtakeL; // 0
    public DcMotorEx outtakeR;

    public Shooter (HardwareMap hwMap) {
//        outtakeL = new MotorEx("outtakeLeft"); // 0
//        outtakeR = new MotorEx("outtakeRight"); // 2
        outtakeL = hwMap.get(DcMotorEx.class, "outtakeLeft");
        outtakeR = hwMap.get(DcMotorEx.class, "outtakeRight");



        outtakeL.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    // Motors for shooter
//    outtakeL = new MotorEx("outtakeLeft"); // 0
//    outtakeR = new MotorEx("outtakeRight"); // 2

    // Servos for spinny thing (maybe going to be a motor)
//    private final ServoEx spin1 = new ServoEx("spin1");
//    private final ServoEx spin2 = new ServoEx("spin2");

//    private final ControlSystem outtake_controller = ControlSystem.builder()
//            .posPid(0.005, 0, 0) //out_controller
//            .elevatorFF(0) //compensates for gravity
//            .build();

//    private static final double launchHeight = 0; // TODO update this with CAD
//    private static final double g = 9.81;
//    private static final double H = 39 - launchHeight; // y distance, cm distance from launch height to a little above hole on goal
//    private static double R; // x distance, get from AprilTag
//
//    private static final double shootVel = Math.sqrt(H * g + g * Math.sqrt(Math.pow(R, 2) + Math.pow(H, 2)));
//
//    public static double getShootVel() {
//        return shootVel;
//    }

//    public enum outtake_vels {
//
//        PID_SHOOT(shootVel),
//        HARDCODED_SHOOT_TRIANGLE(1), // TODO: CALC WITH FIELD
//        HARDCODED_SHOOT_BACK(1), // TODO: CALC WITH FIELD
//        IDLE(0);
//
//        private final double outtake_vels;
//
//        outtake_vels(double pos) {
//            this.outtake_vels = pos;
//        }
//        public double getOuttakeVel() {
//            return outtake_vels;
//        }
//    }

    public void setShooterPower(double lPower, double rPower) {
        outtakeL.setPower(lPower);
        outtakeR.setPower(rPower);
    }

//    public Command pidShoot = new RunToVelocity(
//            outtake_controller,
//            outtake_vels.PID_SHOOT.getOuttakeVel()
//    ).requires(this);
//
//    public Command triangleShoot = new RunToVelocity(
//            outtake_controller,
//            outtake_vels.HARDCODED_SHOOT_TRIANGLE.getOuttakeVel()
//    ).requires(this);
//
//    public Command backShoot = new RunToVelocity(
//            outtake_controller,
//            outtake_vels.HARDCODED_SHOOT_BACK.getOuttakeVel()
//    ).requires(this);
//
//    public Command idle = new RunToVelocity(
//            outtake_controller,
//            outtake_vels.IDLE.getOuttakeVel()
//    );

//    public void spinRight() {
//        spin1.setPosition(spin1.getPosition() + .1);
//        spin2.setPosition(spin1.getPosition() + .1); // TODO: REVERSE AND MAKE ANALOG METHODS FOR GET POSITION
//    }
//
//    public void spinLeft() {
//        spin1.setPosition(spin1.getPosition() - .1);
//        spin2.setPosition(spin1.getPosition() - .1); // TODO: REVERSE AND MAKE ANALOG METHODS FOR GET POSITION
//    }

//    @Override
//    public void periodic() {
//        outtakeL.setPower(outtake_controller.calculate(outtakeL.getState()));
//        outtakeR.setPower(outtake_controller.calculate(outtakeR.getState()));
//
//    }
}