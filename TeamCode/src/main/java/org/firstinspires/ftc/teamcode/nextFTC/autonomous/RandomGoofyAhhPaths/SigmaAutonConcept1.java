package org.firstinspires.ftc.teamcode.nextFTC.autonomous.RandomGoofyAhhPaths;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.nextFTC.autonomous.AutonSequencesGroup;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.transfer.Transfer;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.Shooter;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.turret.Turret;
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
public class SigmaAutonConcept1 extends NextFTCOpMode {

    //---------------------- IMPORT SUBSYSTEMS & GROUPS ----------------------\\
    public SigmaAutonConcept1() {
        addComponents(
                new SubsystemComponent(
                        AutonSequencesGroup.INSTANCE,
                        Intake.INSTANCE, Transfer.INSTANCE,
                        Turret.INSTANCE, Shooter.INSTANCE
                ),

                //You use this when reading multiple instances
                BulkReadComponent.INSTANCE,

                //Follower for Pedro
                new PedroComponent(Constants::createFollower)

        );
    }

    //---------------------- VISION OUTPUT CALCULATIONS ----------------------\\

    /* Output Value is from 1-3,
     3 is furthest | G P P
     2 is middle   | P G P
     1 is closest  | P P G

     NOTE - have to intake in reverse order so they outtake in the order of motif
     */

    double visionOutput = 1;

    //--------------------------- INITIALIZE PATHS ---------------------------\\
    //Start Pose, scorePreloadPose
    private final Pose startPose = new Pose(40, 8, Math.toRadians(90));
    private final Pose scorePreloadPose = new Pose(60, 18, Math.toRadians(119));


    //Set1 Poses
    private final Pose s1control1 = new Pose(55, 32.5);
    private final Pose grabSet1Pose = new Pose(17, 36, Math.toRadians(180));
    private final Pose scoreSet1Pose = new Pose(60, 18, Math.toRadians(104));

    //Set2 Poses
    private final Pose s2control1 = new Pose(47, 61);
    private final Pose grabSet2Pose = new Pose(17, 60, Math.toRadians(180));
    private final Pose scoreSet2Pose = new Pose(60, 18, Math.toRadians(90));

    //Set3 Poses - UPDATE VALUES
    private final Pose s3control1 = new Pose(51, 89);
    private final Pose grabSet3Pose = new Pose(17, 83.8, Math.toRadians(180));
    //Turns to go straight back
    private final Pose turnToScoreSet3Pose = new Pose(17, 83.8, Math.toRadians(122));
    private final Pose scoreSet3Pose = new Pose(60, 18, Math.toRadians(90));

    //Park Pose
    private final Pose parkPose = new Pose(60, 18, Math.toRadians(90));

    //Paths and PathChains
    private Path scorePreload, parkFromSet2, parkFromSet3;
    private PathChain grabSet1, scoreSet1, grabSet2, scoreSet2, grabSet3, scoreSet3;


    //------------------------------ BUILD PATHS -----------------------------\\
    public void buildPaths() {

        //Score Preload in the Back
        scorePreload = new Path(new BezierLine(startPose, scorePreloadPose));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePreloadPose.getHeading());

        //Grab Nearest Set AND Vision Calc for Motif with Turret
        grabSet1 = follower().pathBuilder()
                .addPath(new BezierCurve(scorePreloadPose, s1control1, grabSet1Pose))
                .setLinearHeadingInterpolation(scorePreloadPose.getHeading(), grabSet1Pose.getHeading())
                .build();

        //Score Nearest Set in the Back
        scoreSet1 = follower().pathBuilder()
                .addPath(new BezierLine(grabSet1Pose, scoreSet1Pose))
                .setLinearHeadingInterpolation(grabSet1Pose.getHeading(), scoreSet1Pose.getHeading())
                .build();

        //Go to corresponding set based on correct motif pattern for the round
        //If nearest one was motif for the round, grab the middle set
        //I will make paths for both middle (set 2) AND furthest set (set 3)

        //--------- Set 2 ---------\\
        grabSet2 = follower().pathBuilder()
                .addPath(new BezierCurve(scoreSet1Pose, s2control1, grabSet2Pose))
                .setLinearHeadingInterpolation(scoreSet1Pose.getHeading(), grabSet2Pose.getHeading())
                .build();

        scoreSet2 = follower().pathBuilder()
                .addPath(new BezierLine(grabSet2Pose, scoreSet2Pose))
                .setLinearHeadingInterpolation(grabSet2Pose.getHeading(), scoreSet2Pose.getHeading())
                .build();

        parkFromSet2 = new Path(new BezierCurve(scoreSet2Pose, parkPose));
        parkFromSet2.setLinearHeadingInterpolation(scoreSet2Pose.getHeading(), parkPose.getHeading());

        //--------- Set 3 ---------\\
        grabSet3 = follower().pathBuilder()
                .addPath(new BezierCurve(scoreSet1Pose, s3control1, grabSet3Pose))
                .setLinearHeadingInterpolation(scoreSet1Pose.getHeading(), grabSet3Pose.getHeading())
                .build();

        scoreSet3 = follower().pathBuilder()
                .addPath(new BezierLine(grabSet3Pose, turnToScoreSet3Pose))
                .setLinearHeadingInterpolation(grabSet3Pose.getHeading(), scoreSet3Pose.getHeading())
                .addPath(new BezierLine(turnToScoreSet3Pose,scoreSet3Pose))
                .setConstantHeadingInterpolation(turnToScoreSet3Pose.getHeading())
                .build();

        parkFromSet3 = new Path(new BezierCurve(scoreSet3Pose, parkPose));
        parkFromSet3.setLinearHeadingInterpolation(scoreSet3Pose.getHeading(), parkPose.getHeading());

    }

    //------------------------- MAKE COMMAND ROUTINES ------------------------\\
    private Command initRoutine() {
        return new SequentialGroup(
                new ParallelGroup(
                        Intake.INSTANCE.in,
                        Transfer.INSTANCE.transferOn
                ),
                new Delay(3),

                new ParallelGroup(
                        Intake.INSTANCE.idle,
                        Transfer.INSTANCE.transferOff
                )
        );
    }

    // Active Commands

    //--------- PRELOADS ---------\\
    public Command shootPreloads() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(scorePreload, true),
                        Turret.INSTANCE.toPreloadR
                ),
                AutonSequencesGroup.INSTANCE.spinUpShoot3
        );
    }

    //--------- SET 1 ---------\\
    public Command grabSet1() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(grabSet1),
                        AutonSequencesGroup.INSTANCE.intake3
                )
        );
    }

    public Command shootSet1() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(scoreSet1, true),
                        Turret.INSTANCE.set1BackR
                ),
                AutonSequencesGroup.INSTANCE.spinUpShoot3
        );
    }

    //--------- SET 2 ---------\\
    public Command grabSet2() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(grabSet2),
                        AutonSequencesGroup.INSTANCE.intake3
                )
        );
    }

    public Command shootSet2() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(scoreSet2, true),
                        Turret.INSTANCE.set2BackR
                ),
                AutonSequencesGroup.INSTANCE.spinUpShoot3
        );
    }

    public Command parkFromSet2() {
        return new SequentialGroup(
                new ParallelGroup(
                        AutonSequencesGroup.INSTANCE.resetSubsystems,
                        new FollowPath(parkFromSet2, true)
                )
        );
    }

    //--------- SET 3 ---------\\
    public Command grabSet3() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(grabSet3),
                        AutonSequencesGroup.INSTANCE.intake3
                )
        );
    }

    public Command shootSet3() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(scoreSet3, true),
                        Turret.INSTANCE.set3BackR
                ),
                AutonSequencesGroup.INSTANCE.spinUpShoot3
        );
    }

    public Command parkFromSet3() {
        return new SequentialGroup(
                new ParallelGroup(
                        AutonSequencesGroup.INSTANCE.resetSubsystems,
                        new FollowPath(parkFromSet3, true)
                )
        );
    }

    //------------------------- INIT ------------------------\\
    @Override
    public void onInit() {
        buildPaths();
        initRoutine().schedule();
    }

    //------------------------- MAIN LOOP ------------------------\\

    @Override
    public void onStartButtonPressed() {
        // Shoot Preloads
        shootPreloads().schedule();

        // Grab and Shoot Set 1

        //get vision output from turret cam WHILE grabbing
        // 1 (closest), 2 (middle), or 3 (furthest)
        grabSet1().schedule();
        shootSet1().schedule();

        // Grab and Shoot Set 2 OR Set 3 depending on visionOutput
        if (visionOutput == 1) {
            grabSet2().schedule();
            shootSet2().schedule();
        } else if (visionOutput == 2) {
            grabSet2().schedule();
            shootSet2().schedule();
            parkFromSet2().schedule();
        } else { // output 3
            grabSet3().schedule();
            shootSet3().schedule();
            parkFromSet3().schedule();
        }

    }
}