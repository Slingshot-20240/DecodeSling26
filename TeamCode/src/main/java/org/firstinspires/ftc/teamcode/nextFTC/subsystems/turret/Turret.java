package org.firstinspires.ftc.teamcode.nextFTC.subsystems.turret;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.nextFTC.subsystems.transfer.Transfer;

import dev.nextftc.hardware.impl.MotorEx;

public class Turret {
    // calc auto turn OR manual hardcoded thing

    // NextFTC Shooter Instance
    public static final Turret INSTANCE = new Turret();
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

    // TURRET CALCULATIONS
    // ----------------------------------
    private int turretEncoderVal = 0;

    public int calcTurretVal() {
        // TODO: WITH MOTT'S THING
        return turretEncoderVal;
    }

    // AUTONOMOUS COMMANDS - NextFTC
    // ----------------------------------

    public enum turretVals {

        // TODO: ISHAAN DO STATES PLEASE :)
        AUTO_SHOOT_BACK(1);

        private final double turret_vals;

        turretVals(double pos) {
            this.turret_vals = pos;
        }
        public double getTurretVal() {
            return turret_vals;
        }
    }

    // TODO: ISHAAN DO COMMANDS FOR AUTO TOO :)

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

    public void setTurretPos(int pos, double power) {
        teleTurret.setTargetPosition(pos);
        teleTurret.setPower(power);
    }
}
