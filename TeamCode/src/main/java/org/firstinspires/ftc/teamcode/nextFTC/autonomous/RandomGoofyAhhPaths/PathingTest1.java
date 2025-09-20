package org.firstinspires.ftc.teamcode.nextFTC.autonomous.RandomGoofyAhhPaths;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.nextFTC.autonomous.AutonSequencesGroup;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.Transfer;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

@Autonomous(name = "PathingTest1")
public class PathingTest1 extends NextFTCOpMode {

    public PathingTest1() {
        addComponents(
                new SubsystemComponent(
                        AutonSequencesGroup.INSTANCE,
                        Intake.INSTANCE, Transfer.INSTANCE, Shooter.INSTANCE

                ),
                //You use this when reading multiple instances
                BulkReadComponent.INSTANCE,
                new PedroComponent(Constants::createFollower)

        );
    }

    //Updated Real
    private final Pose startPose = new Pose(40, 8, Math.toRadians(90));
    private final Pose scoreBackPose = new Pose(60, 18, Math.toRadians(106));
    private final Pose grabSet1Pose = new Pose(44, 35, Math.toRadians(180));

    //Not real values
    private final Pose grabSet2Pose = new Pose(50, 35, Math.toRadians(180));
    private final Pose grabSet3Pose = new Pose(55, 35, Math.toRadians(180));

    private final Pose parkPose = new Pose(60, 98, Math.toRadians(90));
    private Path scorePreload, park;
    private PathChain grabSet1, scoreSet1, grabSet2, grabSet3;


    private Command initRoutine() {
        return new SequentialGroup(
                new ParallelGroup(
                        Intake.INSTANCE.in,
                        Transfer.INSTANCE.up
                ),
                new Delay(0.5)
        );
    }

    public void buildPaths() {

        //Score Preload in the Back
        scorePreload = new Path(new BezierLine(startPose, scoreBackPose));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scoreBackPose.getHeading());

        //Grab Nearest Set AND Vision Calc for Motif
        grabSet1 = follower().pathBuilder()
                .addPath(new BezierLine(scoreBackPose, grabSet1Pose))
                .setLinearHeadingInterpolation(scoreBackPose.getHeading(), grabSet1Pose.getHeading())
                .build();

        //Score Nearest Set in the Back
        scoreSet1 = follower().pathBuilder()
                .addPath(new BezierLine(grabSet1Pose, scoreBackPose))
                .setLinearHeadingInterpolation(grabSet1Pose.getHeading(), scoreBackPose.getHeading())
                .build();

        //Go to corresponding set based on correct motif pattern for the round
        //If nearest one was motif for the round, grab the middle set
        //I will make paths for both middle (set 2) AND furthest set (set 3)

        grabSet2 = follower().pathBuilder()
                .addPath(new BezierLine(scoreBackPose, grabSet1Pose))
                .setLinearHeadingInterpolation(scoreBackPose.getHeading(), grabSet1Pose.getHeading())
                .build();

        grabSet3 = follower().pathBuilder()
                .addPath(new BezierLine(scoreBackPose, grabSet1Pose))
                .setLinearHeadingInterpolation(scoreBackPose.getHeading(), grabSet1Pose.getHeading())
                .build();


        parkFromSet2 = new Path(new BezierCurve(grabSet2Pose, parkPose));
        parkFromSet2.setLinearHeadingInterpolation(grabSet2Pose.getHeading(), parkPose.getHeading());
    }


    // Active Commands
    public Command shootPreloads() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(scorePreload),
                        AutonSequencesGroup.INSTANCE.intake
                )
        );
    }

    @Override
    public void onInit() {
        initRoutine().schedule();
    }

    @Override
    public void onStartButtonPressed() {
        buildPaths();

        shootPreloads().schedule();

    }
}