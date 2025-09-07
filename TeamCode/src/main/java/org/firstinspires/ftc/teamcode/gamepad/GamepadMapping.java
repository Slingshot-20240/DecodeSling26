package org.firstinspires.ftc.teamcode.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadMapping {

    private Gamepad gamepad1;
    private Gamepad gamepad2;

    // DRIVETRAIN
    // --------------
    public static double drive = 0.0;
    public static double strafe = 0.0;
    public static double turn = 0.0;

    // LOCKED HEADING
    // -----------------
    // public static Toggle toggleLockedHeading;
    public static boolean lock90 = false;
    public static boolean lock180 = false;
    public static boolean lock270 = false;
    public static boolean lock360 = false;
    public static Toggle lockedMode;

    public GamepadMapping(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        lockedMode = new Toggle(false);


    }

    public void joystickUpdate() {
        if(gamepad1.x){
            drive = gamepad1.left_stick_y*0.3;
            strafe = gamepad1.left_stick_x*0.3;
            turn = gamepad1.right_stick_x*0.3;
        }else {
            drive = gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            turn = gamepad1.right_stick_x;
        }
    }

    public void update() {
        joystickUpdate();


    }

    public void presModeUpdate() {

    }

    public void resetMultipleControls(Toggle... toggles) {
        for (Toggle toggle : toggles) {
            toggle.set(false);
        }
    }
}

