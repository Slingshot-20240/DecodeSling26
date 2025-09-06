package org.firstinspires.ftc.teamcode.nextFTC.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.nextFTC.subsystems.Claw;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.Lift;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "NextFTC Autonomous Program")
public class NextFTCSampleAuton extends NextFTCOpMode {

    public NextFTCSampleAuton() {
        addComponents(
                new SubsystemComponent(
                        //Here you get AutonSequencesGroup instance
                        AutonSequencesGroup.INSTANCE,
                        //Here you get individual subsystem instances
                        Lift.INSTANCE, Claw.INSTANCE
                ),
                //You use this when reading multiple instances
                BulkReadComponent.INSTANCE
        );
    }

    private Command autonomousRoutine() {
        return new SequentialGroup(
                Lift.INSTANCE.toHigh,
                new ParallelGroup(
                        Lift.INSTANCE.toTransfer,
                        Claw.INSTANCE.close
                ),
                new Delay(0.5),
                new ParallelGroup(
                        Claw.INSTANCE.open,
                        Lift.INSTANCE.toLow
                )
        );
    }

    @Override
    public void onStartButtonPressed() {
        autonomousRoutine().schedule();
    }
}