package org.firstinspires.ftc.teamcode.nextFTC.subsystems.transfer;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.PowerableGroup;
import dev.nextftc.hardware.powerable.SetPower;

public class Transfer implements Subsystem {
    // NextFTC Shooter Instance
    public static final Transfer INSTANCE = new Transfer();

    private Transfer() { }

    // NextFTC Hardware
    private final CRServoEx autoTransferR = new CRServoEx("transferServoR");
    private final CRServoEx autoTransferL = new CRServoEx("transferServoL");
    @Override
    public void initialize() {
        autoTransferL.getServo().setDirection(DcMotorSimple.Direction.REVERSE);
    }


    // TeleOp Hardware and Constructor
    public ServoEx teleTransferR;
    public ServoEx teleTransferL;

    public Transfer(HardwareMap hwMap) {
        teleTransferR = hwMap.get(ServoEx.class, "transferServoR");
        teleTransferL = hwMap.get(ServoEx.class, "transferServoL");
    }



    // AUTONOMOUS COMMANDS - NextFTC


    public enum transferVals {
        ON(1),
        OFF(-1),
        IDLE(0);

        private final double transfer_vals;

        transferVals(double pos) {
            this.transfer_vals = pos;
        }
        public double getTransferVal() {
            return transfer_vals;
        }
    }

    // ISHAAN TODO :)
    public PowerableGroup servos = new PowerableGroup(
            autoTransferL,
            autoTransferR
    );

    public Command transferOn = new SetPower(servos, 0.5);

//    public Command transferOn = new SetPosition(
//            autoTransferR,
//            1
//    ).requires(this);

//    public Command transferOff = new SetPosition(
//            autoTransferR,
//            0
//    ).requires(this);

    // TELEOP METHODS
    public void transferOn() {
        teleTransferR.setPosition(1);
        teleTransferR.setPosition(1);

        // Or if CR servo
//        teleTransferR.setPower(1);
//        teleTransferR.setPower(1);
    }

    public void transferOff() {
        teleTransferR.setPosition(0);
        teleTransferR.setPosition(0);

        // Or if CR servo
//        teleTransferR.setPower(0);
//        teleTransferR.setPower(0);
    }
}
