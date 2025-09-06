package org.firstinspires.ftc.teamcode.nextFTC.subsystems;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class Claw implements Subsystem {
    public static final Claw INSTANCE = new Claw();
    private Claw() { }

    private final ServoEx claw = new ServoEx("claw");

    public Command open = new SetPosition(
            claw,
            0.1
    ).requires(this);

    public Command close = new SetPosition(
            claw,
            0.2
    ).requires(this);
}