package org.firstinspires.ftc.teamcode.teleop.mechTests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

@TeleOp(group = "mech tests")
public class ShooterTest extends OpMode {
    Robot robot;
    GamepadMapping controls;

    public static boolean backShoot = false;

    @Override
    public void init() {
        controls = new GamepadMapping(gamepad1, gamepad2);
        robot = new Robot(hardwareMap, controls);
    }

    @Override
    public void loop() {
        controls.update();

        if (backShoot) {
            if(controls.shootBack.value()) {
                robot.shooter.teleShootFromBack();
            } else {
                robot.transfer.transferOff();
            }
        }

        if (!backShoot) {
            if(controls.shootTriangle.value()) {
                robot.shooter.teleShootFromFront();
            } else {
                robot.transfer.transferOff();
            }
        }
    }
}
