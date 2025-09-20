package org.firstinspires.ftc.teamcode.teleop.misc;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import dev.nextftc.ftc.NextFTCOpMode;

@TeleOp(name = "ShooterPID", group = "Testing")
public class ShooterPID extends NextFTCOpMode {

    DcMotorEx flywheel;

    @Override
    public void runOpMode() {
        flywheel = hardwareMap.get(DcMotorEx.class, "flywheel");
        
        // Set PIDF (start with defaults, tune later)
        flywheel.setVelocityPIDFCoefficients(0.2, 2.0, 0.002, 0.0);

        waitForStart();

        double time = 0;
        while (opModeIsActive()) {
            time += 0.02; // ~50 Hz loop

            //sine wave/varieble setpoint between 2000 and 5000 ticks/sec
            double targetVel = 3500 + 1500 * Math.sin(2 * Math.PI * 0.5 * time);

            // Send target to REV Hub PID
            flywheel.setVelocity(targetVel);

            // Read actual velocity
            double actualVel = flywheel.getVelocity();

            // Telemetry
            telemetry.addData("Target (ticks/s): ", targetVel);
            telemetry.addData("Actual (ticks/s): ", actualVel);
            telemetry.update();

            sleep(20);
        }
    }
}