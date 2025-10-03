package org.firstinspires.ftc.teamcode.teleop.mechTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp(group = "mech tests")
public class TransferTest extends OpMode {
    Robot robot;
    GamepadMapping controls;
    public static double power = 0;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, controls);
    }

    @Override
    public void loop() {
        controls.update();

        if(controls.failSafeReset.value()) {
            robot.transfer.transferOff();
        } else {
            robot.transfer.transferOn();
        }
    }
}
