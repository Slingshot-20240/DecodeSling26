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

    //---------------------- IMPORT SUBSYSTEMS & GROUPS ----------------------\\
    public PathingTest1() {
        addComponents(
                new SubsystemComponent(
                        AutonSequencesGroup.INSTANCE,
                        Intake.INSTANCE, Transfer.INSTANCE, Shooter.INSTANCE
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
    //Start Pose, scoreBackPose
    private final Pose startPose = new Pose(40, 8, Math.toRadians(90));
    private final Pose scoreBackPose = new Pose(60, 18, Math.toRadians(110));

    //Set1 Poses
    private final Pose s1control1 = new Pose(60, 18);
    private final Pose s1control2 = new Pose(47, 32);
    private final Pose grabSet1Pose = new Pose(17, 36, Math.toRadians(180));

    //Set2 Poses
    private final Pose s2control1 = new Pose(60, 18);
    private final Pose s2control2 = new Pose(48, 36);
    private final Pose grabSet2Pose = new Pose(17, 36, Math.toRadians(180));

    //Set3 Poses
    private final Pose s3control1 = new Pose(60, 18);
    private final Pose s3control2 = new Pose(48, 36);
    private final Pose grabSet3Pose = new Pose(17, 36, Math.toRadians(180));

    //Park Pose
    private final Pose parkPose = new Pose(60, 18, Math.toRadians(90));

    //Paths and PathChains
    private Path scorePreload, parkFromSet2, parkFromSet3;
    private PathChain grabSet1, scoreSet1, grabSet2, scoreSet2, grabSet3, scoreSet3;


    //------------------------------ BUILD PATHS -----------------------------\\
    public void buildPaths() {

        //Score Preload in the Back
        scorePreload = new Path(new BezierLine(startPose, scoreBackPose));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scoreBackPose.getHeading());

        //Grab Nearest Set AND Vision Calc for Motif with Turret
        grabSet1 = follower().pathBuilder()
                .addPath(new BezierCurve(scoreBackPose, s1control1, s1control2, grabSet1Pose))
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

        //--------- Set 2 ---------\\
        grabSet2 = follower().pathBuilder()
                .addPath(new BezierCurve(scoreBackPose, s2control1, s2control2, grabSet2Pose))
                .setLinearHeadingInterpolation(scoreBackPose.getHeading(), grabSet2Pose.getHeading())
                .build();

        scoreSet2 = follower().pathBuilder()
                .addPath(new BezierLine(grabSet2Pose, scoreBackPose))
                .setLinearHeadingInterpolation(grabSet2Pose.getHeading(), scoreBackPose.getHeading())
                .build();

        parkFromSet2 = new Path(new BezierCurve(grabSet2Pose, parkPose));
        parkFromSet2.setLinearHeadingInterpolation(grabSet2Pose.getHeading(), parkPose.getHeading());

        //--------- Set 3 ---------\\
        grabSet3 = follower().pathBuilder()
                .addPath(new BezierCurve(scoreBackPose, s3control1, s3control2, grabSet3Pose))
                .setLinearHeadingInterpolation(scoreBackPose.getHeading(), grabSet3Pose.getHeading())
                .build();

        scoreSet3 = follower().pathBuilder()
                .addPath(new BezierLine(grabSet3Pose, scoreBackPose))
                .setLinearHeadingInterpolation(grabSet3Pose.getHeading(), scoreBackPose.getHeading())
                .build();

        parkFromSet3 = new Path(new BezierCurve(grabSet3Pose, parkPose));
        parkFromSet3.setLinearHeadingInterpolation(grabSet3Pose.getHeading(), parkPose.getHeading());

    }

    //------------------------- MAKE COMMAND ROUTINES ------------------------\\
    private Command initRoutine() {
        return new SequentialGroup(
                new ParallelGroup(
                        Intake.INSTANCE.in,
                        Transfer.INSTANCE.up
                ),
                new Delay(0.5)
        );
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

    public Command grabSet1() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(grabSet1),
                        //Have a sequence for getting balls into scoring position
                        //One in turret, 2 in intake
                        Intake.INSTANCE.in
                )
        );
    }

    public Command shootSet1() {
        return new SequentialGroup(
                new FollowPath(scoreSet1, true)
                //Have a sequence for shooting 3
                //AutonSequencesGroup.INSTANCE.shoot3
        );
    }

    public Command grabSet2() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(grabSet2),
                        //Have a sequence for getting balls into scoring position
                        //One in turret, 2 in intake
                        Intake.INSTANCE.in
                )
        );
    }

    public Command shootSet2() {
        return new SequentialGroup(
                new FollowPath(scoreSet2, true)
                //Have a sequence for shooting 3
                //AutonSequencesGroup.INSTANCE.shoot3
        );
    }

    public Command parkFromSet2() {
        return new SequentialGroup(
                new ParallelGroup(
                        //Reset Subsystems while parking
                        new FollowPath(parkFromSet2, true)
                )

        );
    }

    public Command grabSet3() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(grabSet3),
                        //Have a sequence for getting balls into scoring position
                        //One in turret, 2 in intake
                        Intake.INSTANCE.in
                )
        );
    }

    public Command shootSet3() {
        return new SequentialGroup(
                new FollowPath(scoreSet3, true)
                //Have a sequence for shooting 3
                //AutonSequencesGroup.INSTANCE.shoot3
        );
    }

    public Command parkFromSet3() {
        return new SequentialGroup(
                new ParallelGroup(
                        //Reset Subsystems while parking
                        new FollowPath(parkFromSet3, true)
                )
        );
    }

    @Override
    public void onInit() {
        buildPaths();
        initRoutine().schedule();
    }

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