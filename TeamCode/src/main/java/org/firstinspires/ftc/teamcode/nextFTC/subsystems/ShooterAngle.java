package org.firstinspires.ftc.teamcode.nextFTC.subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class ShooterAngle implements Subsystem {
    public static final ShooterAngle INSTANCE = new ShooterAngle();
    private ShooterAngle() { }

    private final ServoEx shooterAngle = new ServoEx("shooterAngle");

    //Calculation code here

    public Command up = new SetPosition(
            shooterAngle,
            0.1
    ).requires(this);


    public Command down = new SetPosition(
            shooterAngle,
            0.2
    ).requires(this);
}

//Range options
/*
April Tags - For turret angle, to get distance for Shooter Angle
Limelight -
Ultrasonic

 */