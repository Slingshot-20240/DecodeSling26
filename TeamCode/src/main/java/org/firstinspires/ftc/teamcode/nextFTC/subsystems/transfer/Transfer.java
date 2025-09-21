package org.firstinspires.ftc.teamcode.nextFTC.subsystems.transfer;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;
import dev.nextftc.hardware.powerable.SetPower;

public class Transfer implements Subsystem {
    // NextFTC Shooter Instance
    public static final Transfer INSTANCE = new Transfer();
    private Transfer() { }

    // NextFTC Hardware
    private final MotorEx autoTransfer = new MotorEx("transfer");

    // TeleOp Hardware and Constructor
    public DcMotorEx teleTransfer;

    public Transfer(HardwareMap hwMap) {
        teleTransfer = hwMap.get(DcMotorEx.class, "transfer");
    }

    // AUTONOMOUS COMMANDS - NextFTC
    // ----------------------------------

    public enum transferVals {

        ON(1),
        IDLE(0);

        private final double transfer_vals;

        transferVals(double pos) {
            this.transfer_vals = pos;
        }
        public double getTransferVal() {
            return transfer_vals;
        }
    }

    public Command transferOn = new SetPower(
            autoTransfer,
            1
    ).requires(this);

    public Command transferOff = new SetPower(
            autoTransfer,
            0
    ).requires(this);

    // TELEOP METHODS
    // ----------------------------------

    public void transferOn() {
        teleTransfer.setPower(1);
    }

    public void transferOff() {
        teleTransfer.setPower(0);
    }
}
