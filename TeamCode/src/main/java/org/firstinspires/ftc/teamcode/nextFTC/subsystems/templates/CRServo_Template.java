package org.firstinspires.ftc.teamcode.nextFTC.subsystems.templates;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

public class CRServo_Template implements Subsystem {
    public static final CRServo_Template INSTANCE = new CRServo_Template();
    private CRServo_Template() { }

    private final CRServoEx crServo = new CRServoEx("servo");

    public Command right = new SetPower(
            crServo,
            0.5
    );

    public Command idle = new SetPower(
            crServo,
            0
    );

    public Command left = new SetPower(
            crServo,
            -0.5
    );
}