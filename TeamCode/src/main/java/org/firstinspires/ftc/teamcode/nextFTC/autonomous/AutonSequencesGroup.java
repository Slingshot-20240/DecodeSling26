package org.firstinspires.ftc.teamcode.nextFTC.autonomous;

import org.firstinspires.ftc.teamcode.nextFTC.subsystems.templates.CRServo_Template;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.templates.Servo_Template;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.templates.Lift;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.SubsystemGroup;

public class AutonSequencesGroup extends SubsystemGroup {
    public static final AutonSequencesGroup INSTANCE = new AutonSequencesGroup();

    private AutonSequencesGroup() {
        super(
                Servo_Template.INSTANCE,
                Lift.INSTANCE
        );
    }

    public final Command score = new SequentialGroup(
            Lift.INSTANCE.toHigh,
            Servo_Template.INSTANCE.open
    ).named("Score"); //Used to find bugs in logging

}