package org.firstinspires.ftc.teamcode.teleop.servoTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.impl.ServoEx;

@TeleOp
public class FlapTest extends NextFTCOpMode {
    public static double flapPos = 0.5;
    ServoEx flap;

    @Override
    public void onInit() {
        flap = VariableHood.INSTANCE.variableHood;
    }

    @Override
    public void onUpdate() {
        flap.setPosition(flapPos);
    }
}
