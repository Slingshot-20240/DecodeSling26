package org.firstinspires.ftc.teamcode.nextFTC.subsystems.templates;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Servo_Template implements Subsystem {
    public static final Servo_Template INSTANCE = new Servo_Template();
    private Servo_Template() { }

    private final ServoEx servo = new ServoEx("servo");

    public Command open = new SetPosition(
            servo,
            0.1
    ).requires(this);

    public Command close = new SetPosition(
            servo,
            0.2
    ).requires(this);
}