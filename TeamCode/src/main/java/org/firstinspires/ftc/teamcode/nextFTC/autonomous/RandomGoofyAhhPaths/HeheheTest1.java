package org.firstinspires.ftc.teamcode.nextFTC.autonomous.RandomGoofyAhhPaths;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;


public class HeheheTest1 {

    //figure this out before going to bed
    public static PathBuilder builder = new PathBuilder();

    public static PathChain line1 = builder
            .addPath(new BezierLine(new Pose(56.000, 8.000), new Pose(56.000, 36.000)))
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierCurve(
                            new Pose(56.000, 36.000),
                            new Pose(62.038, 62.848),
                            new Pose(46.812, 48.108),
                            new Pose(35.636, 51.510)
                    )
            )
            .setTangentHeadingInterpolation()
            .build();
}