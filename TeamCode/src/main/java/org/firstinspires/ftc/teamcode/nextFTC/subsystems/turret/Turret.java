package org.firstinspires.ftc.teamcode.nextFTC.subsystems.turret;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;

public class Turret implements Subsystem {
    // calc auto turn OR manual hardcoded thing

    // NextFTC Shooter Instance
    public static final Turret INSTANCE = new Turret();
    TurretMath turretMath = new TurretMath();
    private Turret() { }

    // NextFTC Hardware
    private final MotorEx autoTurret = new MotorEx("turret");

    // TeleOp Hardware and Constructor
    public DcMotorEx teleTurret;

    public Turret(HardwareMap hwMap) {
        teleTurret = hwMap.get(DcMotorEx.class, "turret");

        teleTurret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        teleTurret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    //Control System
    private final ControlSystem turret_controller = ControlSystem.builder()
            //.posPid(0.005, 0, 0) //turret_controller
            //ff elevator not needed because not a lift... PIG unnecessary
            .build();

    // TURRET CALCULATIONS
    // ----------------------------------
    private int turretEncoderVal = 0;
    private double turretNeededRot = 0;

    public int calcTurretVal(double xt, double yt, double xT, double yT, double headingRad) {
        // TODO: WITH MOTT'S THING
        turretNeededRot = TurretMath.requiredRotation(xt, yt, xT, yT, headingRad);
        // TODO: test how many ticks in 1 rotation (for now lets say it's 384 ticks)
        turretEncoderVal = (int) (384.0 * turretNeededRot / 2.0 * Math.PI);
        return turretEncoderVal;
    }

    // AUTONOMOUS COMMANDS - NextFTC
    // ----------------------------------

    public enum turretVals {

        //RED TURRET POSITIONS
        PRELOAD_BACK_RED(250),
        SCAN_TAG_RED(100),
        S1_BACK_RED(200),
        S2_BACK_RED(150),
        S3_BACK_RED(100),

        //BLUE TURRET POSITIONS
        PRELOAD_BACK_BLUE(-250),
        SCAN_TAG_BLUE(-100),
        S1_BACK_BLUE(-200),
        S2_BACK_BLUE(-150),
        S3_BACK_BLUE(-100);

        private final double turret_vals;

        turretVals(double pos) {
            this.turret_vals = pos;
        }
        public double getTurretVal() {
            return turret_vals;
        }
    }

    //-------------BLUE TURRET POSITIONS-------------\\
    public Command toPreloadB = new RunToPosition(
            turret_controller,
            turretVals.PRELOAD_BACK_BLUE.getTurretVal()
    ).requires(this);

    public Command set1BackB = new RunToPosition(
            turret_controller,
            turretVals.S1_BACK_BLUE.getTurretVal()
    ).requires(this);

    public Command set2BackB = new RunToPosition(
            turret_controller,
            turretVals.S2_BACK_BLUE.getTurretVal()
    ).requires(this);

    public Command set3BackB = new RunToPosition(
            turret_controller,
            turretVals.S3_BACK_BLUE.getTurretVal()
    ).requires(this);

    public Command scanTagB = new RunToPosition(
            turret_controller,
            turretVals.SCAN_TAG_BLUE.getTurretVal()
    ).requires(this);



    //-------------RED TURRET POSITIONS-------------\\
    public Command toPreloadR = new RunToPosition(
            turret_controller,
            turretVals.PRELOAD_BACK_RED.getTurretVal()
    ).requires(this);

    public Command set1BackR = new RunToPosition(
            turret_controller,
            turretVals.S1_BACK_RED.getTurretVal()
    ).requires(this);

    public Command set2BackR = new RunToPosition(
            turret_controller,
            turretVals.S2_BACK_RED.getTurretVal()
    ).requires(this);

    public Command set3BackR = new RunToPosition(
            turret_controller,
            turretVals.S3_BACK_RED.getTurretVal()
    ).requires(this);

    public Command scanTagR = new RunToPosition(
            turret_controller,
            turretVals.SCAN_TAG_RED.getTurretVal()
    ).requires(this);

    // TELEOP METHODS
    // ----------------------------------

    // TODO: BEE TEST THESE

    public void shootBackBlue() {
        teleTurret.setTargetPosition(100);
        teleTurret.setPower(1);
    }

    public void shootBackRed() {
        teleTurret.setTargetPosition(200);
        teleTurret.setPower(1);
    }

    public void shootFrontBlue() {
        teleTurret.setTargetPosition(300);
        teleTurret.setPower(1);
    }

    public void shootFrontRed() {
        teleTurret.setTargetPosition(400);
        teleTurret.setPower(1);
    }
    public void scanTagBlue() {
        teleTurret.setTargetPosition(500);
        teleTurret.setPower(1);
    }

    public void scanTagRed() {
        teleTurret.setTargetPosition(500);
        teleTurret.setPower(1);
    }

    public void setTurretPos(int pos, double power) {
        teleTurret.setTargetPosition(pos);
        teleTurret.setPower(power);
    }
}
