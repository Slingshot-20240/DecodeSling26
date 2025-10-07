package org.firstinspires.ftc.teamcode.nextFTC.autonomous.LM1;

import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.Limelight3A;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.transfer.Transfer;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.turret.Turret;


import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.SubsystemGroup;

public class LM1SequencesGroup extends SubsystemGroup {
    public static final LM1SequencesGroup INSTANCE = new LM1SequencesGroup();

    private LM1SequencesGroup() {
        super(
                LM1SequencesGroup.INSTANCE,
                Intake.INSTANCE, Transfer.INSTANCE,
                Turret.INSTANCE, Shooter.INSTANCE,
                Limelight3A.INSTANCE
        );
    }

    //TODO - Make Sequences Logic

    //Should spin up the shooter and shoot.
    public final Command shootSet = new SequentialGroup(
            //Add Code here
    ).named("ShootSet");

    //Prepare for set
    public final Command prepareForSet = new SequentialGroup(
            Intake.INSTANCE.in,
            Transfer.INSTANCE.transferOn
    ).named("PrepareForSet");

    //Should get balls ready to shoot, one in turret, two in intake
    public final Command intakeSet = new SequentialGroup(
            Intake.INSTANCE.in,
            Transfer.INSTANCE.transferOn
    ).named("Intake3");

    //Should reset all subsystems, ready for tele
    public final Command resetSubsystems = new SequentialGroup(
            Intake.INSTANCE.idle
            //Transfer.INSTANCE.transferOff
    ).named("ResetSubsystems");

}