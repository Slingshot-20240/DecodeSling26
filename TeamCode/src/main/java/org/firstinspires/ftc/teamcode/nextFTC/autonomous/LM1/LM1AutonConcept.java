package org.firstinspires.ftc.teamcode.nextFTC.autonomous.LM1;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.Intake;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.Limelight3A;
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

//9 Artifact Auton Concept
@Autonomous(name = "LM1 Autonomous")
public class LM1AutonConcept extends NextFTCOpMode {

    //---------------------- IMPORT SUBSYSTEMS & GROUPS ----------------------\\
    public LM1AutonConcept() {
        addComponents(
                new SubsystemComponent(
                        LM1SequencesGroup.INSTANCE,
                        Intake.INSTANCE, Transfer.INSTANCE,
                        Turret.INSTANCE, Shooter.INSTANCE,
                        Limelight3A.INSTANCE
                ),

                //You use this when reading multiple instances
                BulkReadComponent.INSTANCE,

                //Follower for Pedro
                new PedroComponent(Constants::createFollower)

        );
    }

    //May not be necessary
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
    private final Pose startPose = new Pose(56, 135.4, Math.toRadians(180));
    private final Pose scorePose = new Pose(48, 96, Math.toRadians(135));

    //Set1 Poses
    private final Pose prepareSet1Pose = new Pose(43, 84, Math.toRadians(180));
    private final Pose grabSet1Pose = new Pose(17, 84, Math.toRadians(180));

    //Set2 Poses
    private final Pose prepareSet2Pose = new Pose(43, 60, Math.toRadians(180));
    private final Pose grabSet2Pose = new Pose(17, 60, Math.toRadians(180));

    //Set3 Poses
    private final Pose prepareSet3Pose = new Pose(43, 35.5, Math.toRadians(180));
    private final Pose grabSet3Pose = new Pose(17, 35.5, Math.toRadians(180));

    //Park Pose (Center, update later)
    private final Pose parkPose = new Pose(48, 120, Math.toRadians(90));

    //Paths and PathChains
    private Path scorePreload;
    private PathChain prepareSet1;
    private PathChain grabSet1;
    private PathChain scoreSet1;
    private Path prepareSet2;
    private Path grabSet2;
    private Path scoreSet2;
    private Path prepareSet3;
    private Path grabSet3;
    private Path scoreSet3;
    private Path park;

    //------------------------------ BUILD PATHS -----------------------------\\
    public void buildPaths() {


        //---------- Preload ----------\\
        //Score Preload in front
        scorePreload = new Path(new BezierLine(startPose, scorePose));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());



        //---------- Set 1 ----------\\
        prepareSet1 = follower().pathBuilder()
                .addPath(new BezierLine(scorePose, prepareSet1Pose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), prepareSet1Pose.getHeading())
                .build();

        grabSet1 = follower().pathBuilder()
                .addPath(new BezierLine(prepareSet1Pose, grabSet1Pose))
                .setLinearHeadingInterpolation(prepareSet1Pose.getHeading(), grabSet1Pose.getHeading())
                .build();

        //Score Nearest Set in the Back
        scoreSet1 = follower().pathBuilder()
                .addPath(new BezierLine(grabSet1Pose, scorePose))
                .setLinearHeadingInterpolation(grabSet1Pose.getHeading(), scorePose.getHeading())
                .build();


        //---------- Set 2 ----------\\
        prepareSet1 = follower().pathBuilder()
                .addPath(new BezierLine(scorePose, prepareSet2Pose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), prepareSet2Pose.getHeading())
                .build();

        grabSet1 = follower().pathBuilder()
                .addPath(new BezierLine(prepareSet2Pose, grabSet1Pose))
                .setLinearHeadingInterpolation(prepareSet2Pose.getHeading(), grabSet2Pose.getHeading())
                .build();

        //Score Nearest Set in the Back
        scoreSet1 = follower().pathBuilder()
                .addPath(new BezierLine(grabSet2Pose, scorePose))
                .setLinearHeadingInterpolation(grabSet2Pose.getHeading(), scorePose.getHeading())
                .build();

        //---------- Set 3 ----------\\
        prepareSet1 = follower().pathBuilder()
                .addPath(new BezierLine(scorePose, prepareSet3Pose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), prepareSet3Pose.getHeading())
                .build();

        grabSet1 = follower().pathBuilder()
                .addPath(new BezierLine(prepareSet3Pose, grabSet1Pose))
                .setLinearHeadingInterpolation(prepareSet3Pose.getHeading(), grabSet3Pose.getHeading())
                .build();

        //Score Nearest Set in the Back
        scoreSet1 = follower().pathBuilder()
                .addPath(new BezierLine(grabSet3Pose, scorePose))
                .setLinearHeadingInterpolation(grabSet3Pose.getHeading(), scorePose.getHeading())
                .build();

    }

    //------------------------- MAKE COMMAND ROUTINES ------------------------\\

    // Init Commands
    private Command initRoutine() {
        return new SequentialGroup(
                new ParallelGroup(
                        Intake.INSTANCE.in,
                        Transfer.INSTANCE.transferOn
                ),
                new Delay(3),

                new ParallelGroup(
                        Intake.INSTANCE.idle
                        //Transfer.INSTANCE.transferOff
                )
        );
    }

    // Active Commands

    //--------- PRELOADS ---------\\
    public Command shootPreloads() {
        return new SequentialGroup(
                new FollowPath(scorePreload, true),
                LM1SequencesGroup.INSTANCE.shootSet
        );
    }

    //--------- SET 1 ---------\\
    public Command grabSet1() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(prepareSet1),
                        LM1SequencesGroup.INSTANCE.prepareForSet
                ),
                new ParallelGroup(
                        new FollowPath(grabSet1),
                        LM1SequencesGroup.INSTANCE.intakeSet
                )
        );
    }

    public Command shootSet1() {
        return new SequentialGroup(
                new FollowPath(scoreSet1, true),
                LM1SequencesGroup.INSTANCE.shootSet
        );
    }

    //--------- SET 1 ---------\\
    public Command grabSet2() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(prepareSet2),
                        LM1SequencesGroup.INSTANCE.prepareForSet
                ),
                new ParallelGroup(
                        new FollowPath(grabSet2),
                        LM1SequencesGroup.INSTANCE.intakeSet
                )
        );
    }

    public Command shootSet2() {
        return new SequentialGroup(
                new FollowPath(scoreSet2, true),
                LM1SequencesGroup.INSTANCE.shootSet
        );
    }

    //--------- SET 1 ---------\\
    public Command grabSet3() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(prepareSet3),
                        LM1SequencesGroup.INSTANCE.prepareForSet
                ),
                new ParallelGroup(
                        new FollowPath(grabSet3),
                        LM1SequencesGroup.INSTANCE.intakeSet
                )
        );
    }

    public Command shootSet3() {
        return new SequentialGroup(
                new FollowPath(scoreSet3, true),
                LM1SequencesGroup.INSTANCE.shootSet
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
            //parkFromSet2().schedule();
        } else { // output 3
            grabSet3().schedule();
            shootSet3().schedule();
            //parkFromSet3().schedule();
        }

    }
}