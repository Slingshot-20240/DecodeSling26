package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.fsm.FSM;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.driving.MecanumDriverControlled;

@TeleOp
public class FSMTest extends NextFTCOpMode {
    private Robot robot;
    private FSM fsm;
    private GamepadMapping gamepad;

    @Override
    public void onInit() {
        gamepad = new GamepadMapping();
        fsm = new FSM(hardwareMap, gamepad);
        robot = fsm.robot;
    }

    @Override
    public void onUpdate() {
        fsm.update();
    }
}
