package org.firstinspires.ftc.teamcode.nextFTC.autonomous;

import org.firstinspires.ftc.teamcode.nextFTC.subsystems.Claw;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.Lift;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.SubsystemGroup;

public class AutonSequencesGroup extends SubsystemGroup {
    public static final AutonSequencesGroup INSTANCE = new AutonSequencesGroup();

    private AutonSequencesGroup() {
        super(
                Claw.INSTANCE,
                Lift.INSTANCE
        );
    }

    public final Command score = new SequentialGroup(
            Lift.INSTANCE.toHigh,
            Claw.INSTANCE.open
    ).named("Score"); //Used to find bugs in logging

}