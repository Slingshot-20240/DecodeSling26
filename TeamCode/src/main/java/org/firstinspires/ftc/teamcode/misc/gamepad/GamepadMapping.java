package org.firstinspires.ftc.teamcode.misc.gamepad;

import dev.nextftc.bindings.Button;
import dev.nextftc.ftc.Gamepads;

public class GamepadMapping {

    // INTAKE
    public Button intake;

    // SHOOT
    public Button pidShoot;
    public Button shootBack;
    public Button shootTriangle;

    // PARK
    public Button park;

    public GamepadMapping() {
        shootBack = Gamepads.gamepad2().rightBumper();
        shootTriangle = Gamepads.gamepad2().leftBumper();
        intake = Gamepads.gamepad1().rightBumper();
        pidShoot = Gamepads.gamepad2().a();
        park = Gamepads.gamepad1().dpadUp();
    }
}

