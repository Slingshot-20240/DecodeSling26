package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.Encoder;
import com.pedropathing.ftc.localization.constants.TwoWheelConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(5.2)

            //Forward Zero Power Acceleration
            //.forwardZeroPowerAcceleration(deceleration)

            //Lateral Zero Power Acceleration
            //.lateralZeroPowerAcceleration(deceleration)

            //Secondary PID Tuners Toggle
            //.useSecondaryTranslationalPIDF(true)
            //.useSecondaryHeadingPIDF(true)
            //.useSecondaryDrivePIDF(true)

            //Translational PID
            //.translationalPIDFCoefficients(new PIDFCoefficients(0.1, 0, 0.01, 0))
            //.secondaryTranslationalPIDFCoefficients(new PIDFCoefficients(0.1,0,0.01,0))

            //Heading PID
            //.headingPIDFCoefficients(new PIDFCoefficients(0.1, 0, 0.01, 0))
            //.secondaryHeadingPIDFCoefficients(new PIDFCoefficients(0.1,0,0.01,0))

            //Drive PID
            //.drivePIDFCoefficients(new FilteredPIDFCoefficients(0.1,0.0,0.01,0.6,0.0))
            //.secondaryDrivePIDFCoefficients(new FilteredPIDFCoefficients(0.1,0,0.01,0.6,0.01))


            ;

    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);


    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightBack")
            .leftRearMotorName("leftFront")
            .leftFrontMotorName("leftBack")
            .leftFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .leftRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightRearMotorDirection(DcMotorSimple.Direction.REVERSE)

            //Forward Velocity Tuner
            //.xVelocity(velocity)

            //Lateral Velocity Tuner
            //.yVelocity(velocity)

            ;

    public static TwoWheelConstants localizerConstants = new TwoWheelConstants()
            .forwardEncoder_HardwareMapName("leftFront")
            .strafeEncoder_HardwareMapName("rightBack")
            .IMU_HardwareMapName("imu")
            .IMU_Orientation(
                    new RevHubOrientationOnRobot(
                            RevHubOrientationOnRobot.LogoFacingDirection.UP,
                            RevHubOrientationOnRobot.UsbFacingDirection.LEFT
                    )
            )
            //Offset
            .forwardPodY(5.5)
            .strafePodX(1.1)

            //Encoder Directions
            .forwardEncoderDirection(Encoder.REVERSE)
            // and/or
            .strafeEncoderDirection(Encoder.REVERSE)

            //Forward Tuner
            //.forwardTicksToInches(multiplier)

            //Lateral Tuner
            //.strafeTicksToInches(multiplier)
            ;
}
