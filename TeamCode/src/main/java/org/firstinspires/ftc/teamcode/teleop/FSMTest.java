package org.firstinspires.ftc.teamcode.teleop;

import org.firstinspires.ftc.teamcode.Robot;

import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.driving.MecanumDriverControlled;

public class FSMTest extends NextFTCOpMode {
    public Command driverControlled;
    private Robot robot;

    @Override
    public void onInit() {
        robot = new Robot(hardwareMap);
    }
}
