package org.firstinspires.ftc.teamcode.teleop.misc;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter.Shooter;

import dev.nextftc.ftc.NextFTCOpMode;

@Config
@TeleOp
public class ShooterTest extends OpMode {
    private Telemetry dashboardTelemetry;
    private GamepadMapping controls;
    public static double Lpower = 0;
    public static double Rpower = 0;
    public static boolean gamepadControl = false;
    private Shooter shooter;
    @Override
    public void init() {
        shooter = new Shooter(hardwareMap);
        // controls = new GamepadMapping();
        dashboardTelemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());


    }

    @Override
    public void loop() {
//        if (gamepadControl) {
//            controls.intake
//                    .toggleOnBecomesTrue()
//                    .whenBecomesTrue(IntakeRoller.INSTANCE.in)
//                    .whenBecomesFalse(IntakeRoller.INSTANCE.out);
//        } else {
//            Shooter.INSTANCE.setShooterPower(Lpower, Rpower);
//        }
        shooter.setShooterPower(Lpower, Rpower);
        dashboardTelemetry.addData("actual velo", shooter.outtakeL.getVelocity(AngleUnit.RADIANS));

        dashboardTelemetry.update();
    }
}
