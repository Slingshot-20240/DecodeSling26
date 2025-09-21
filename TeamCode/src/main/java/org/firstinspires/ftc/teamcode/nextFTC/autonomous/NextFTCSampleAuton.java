package org.firstinspires.ftc.teamcode.nextFTC.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.nextFTC.subsystems.templates.Servo_Template;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.templates.Motor;

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
                        Motor.INSTANCE, Servo_Template.INSTANCE
                ),
                //You use this when reading multiple instances
                BulkReadComponent.INSTANCE
        );
    }

    private Command autonomousRoutine() {
        return new SequentialGroup(
                Motor.INSTANCE.toHigh,
                new ParallelGroup(
                        Motor.INSTANCE.toTransfer,
                        Servo_Template.INSTANCE.close
                ),
                new Delay(0.5),
                AutonSequencesGroup.INSTANCE.intake3
        );
    }

    @Override
    public void onStartButtonPressed() {
        autonomousRoutine().schedule();
    }
}