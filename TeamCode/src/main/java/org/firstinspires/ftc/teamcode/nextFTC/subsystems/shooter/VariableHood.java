package org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class VariableHood implements Subsystem {
    public static final VariableHood INSTANCE = new VariableHood();
    private VariableHood() { }

    public final ServoEx variableHood = new ServoEx("variableHood");

    //Calculation code here
    private static final double g = 9.81;
    private static double R; // get from April Tag, x distance

    double hoodAngle = Math.atan(Math.pow(OuttakeMotor.getShootVel(), 2)/(g * R));

    // TODO: test these
    public Command up = new SetPosition(
            variableHood,
            0.1
    ).requires(this);


    public Command down = new SetPosition(
            variableHood,
            0.2
    ).requires(this);
}

//Range options
/*
April Tags - For turret angle, to get distance for Shooter Angle
Limelight
Ultrasonic

 */