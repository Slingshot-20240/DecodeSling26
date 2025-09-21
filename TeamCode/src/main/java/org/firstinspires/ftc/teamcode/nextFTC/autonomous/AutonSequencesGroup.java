package org.firstinspires.ftc.teamcode.nextFTC.autonomous;

import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.transfer.Transfer;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.Shooter;


import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.SubsystemGroup;

public class AutonSequencesGroup extends SubsystemGroup {
    public static final AutonSequencesGroup INSTANCE = new AutonSequencesGroup();

    private AutonSequencesGroup() {
        super(
                Intake.INSTANCE, Transfer.INSTANCE, Shooter.INSTANCE
        );
    }

    public final Command intake = new SequentialGroup(
            Intake.INSTANCE.in,
            Transfer.INSTANCE.transferOn
    ).named("Intake"); //Used to find bugs in logging

}