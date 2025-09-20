package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.drivetrain.Drivetrain;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.transfer.Transfer;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.turret.Turret;

public class Robot {
    // MECHANISMS
    private final IMU imu;
    public Intake intake;
    public Transfer transfer;
    public Turret turret;
    public Shooter shooter;
    public Drivetrain drivetrain;

    public GamepadMapping controls;

    public Robot(HardwareMap hardwareMap, GamepadMapping controls) {
        this.controls = controls;

        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD)));
        imu.resetYaw();

        intake = new Intake(hardwareMap);
        transfer = new Transfer(hardwareMap);
        turret = new Turret(hardwareMap);
        shooter = new Shooter(hardwareMap);
        drivetrain = new Drivetrain(hardwareMap, imu, controls);

    }
}
