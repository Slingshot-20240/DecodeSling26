package org.firstinspires.ftc.teamcode.teleop.servoTests;

import com.bylazar.gamepad.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp (group = "servo tests")
public class HoodServoTest extends OpMode {
    Robot robot;
    public static double servoPos = 0;
    GamepadMapping controls;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, controls);
    }

    @Override
    public void loop() {
        robot.shooter.teleVariableHoodR.setPosition(servoPos);
        robot.shooter.teleVariableHoodL.setPosition(servoPos);
    }
}
