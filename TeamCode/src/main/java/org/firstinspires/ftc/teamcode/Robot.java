package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.MotorEx;

public class Robot {
    // MECHANISMS
    private MotorEx leftFront;
    private MotorEx rightFront;
    private MotorEx leftBack;
    private MotorEx rightBack;
    private IMU imu;

    public Command driverControlled;

    public Robot(HardwareMap hardwareMap) {
        leftFront = new MotorEx("frontLeft");
        rightFront = new MotorEx("rightFront");
        leftBack = new MotorEx("leftBack");
        rightBack = new MotorEx("rightBack");

        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        imu.resetYaw();

        driverControlled = new MecanumDriverControlled(
                leftFront, rightFront, leftBack, rightBack,
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX()
        );

    }
}
