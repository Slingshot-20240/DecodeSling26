package org.firstinspires.ftc.teamcode.misc.gamepad;

import dev.nextftc.bindings.Button;
import dev.nextftc.ftc.Gamepads;

public class GamepadMapping {

    // INTAKE
    public Button intake;

    // SHOOT
    public Button shoot;

    // PARK
    public Button park;

    public GamepadMapping() {
        intake = Gamepads.gamepad1().rightBumper();
        shoot = Gamepads.gamepad1().leftBumper();
        park = Gamepads.gamepad1().dpadUp();
    }
}

