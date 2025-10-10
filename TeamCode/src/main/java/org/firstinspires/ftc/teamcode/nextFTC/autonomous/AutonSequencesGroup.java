package org.firstinspires.ftc.teamcode.nextFTC.autonomous;

import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.transfer.Transfer;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.turret.Turret;


import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.SubsystemGroup;

public class AutonSequencesGroup extends SubsystemGroup {
    public static final AutonSequencesGroup INSTANCE = new AutonSequencesGroup();

    private AutonSequencesGroup() {
        super(
                AutonSequencesGroup.INSTANCE,
                Intake.INSTANCE, Transfer.INSTANCE,
                Turret.INSTANCE, Shooter.INSTANCE
        );
    }

    //TODO - Make Sequences Logic

    //Should spin up the shooter and shoot.
    public final Command spinUpShoot3 = new SequentialGroup(
            //Add Code here
    ).named("SpinUpAndShoot3");

    //Should get balls ready to shoot, one in turret, two in intake
    public final Command intake3 = new SequentialGroup(
            Intake.INSTANCE.in,
            Transfer.INSTANCE.transferOn
    ).named("Intake3");

    //Should reset all subsystems, ready for tele
    public final Command resetSubsystems = new SequentialGroup(
            Intake.INSTANCE.idle,
            Transfer.INSTANCE.transferOn
    ).named("ResetSubsystems");

}