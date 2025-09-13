package org.firstinspires.ftc.teamcode.teleop.misc;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;
import org.firstinspires.ftc.teamcode.nextFTC.subsystems.intake.IntakeRoller;

public class ShooterTest extends OpMode {
    private GamepadMapping controls;
    public static double power = 0;
    public static boolean gamepadControl = true;
    @Override
    public void init() {
        controls = new GamepadMapping();
    }

    @Override
    public void loop() {
        if (gamepadControl) {
            controls.intake
                    .toggleOnBecomesTrue()
                    .whenBecomesTrue(IntakeRoller.INSTANCE.in)
                    .whenBecomesFalse(IntakeRoller.INSTANCE.out);
        } else {
            IntakeRoller.INSTANCE.setIntakePower(power);
        }
    }
}
