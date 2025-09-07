package org.firstinspires.ftc.teamcode.nextFTC.subsystems;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class OuttakeMotor implements Subsystem {
    public static final OuttakeMotor INSTANCE = new OuttakeMotor();
    private OuttakeMotor() { }

    private final MotorEx outtake = new MotorEx("outtake");

    private final ControlSystem outtake_controller = ControlSystem.builder()
            .posPid(0.005, 0, 0) //out_controller
            .elevatorFF(0) //compensates for gravity
            .build();


    //shootVel formula goes here. Temporarily hardcoded
    double shootVel = 0.5;

    private enum outtake_vels {

        SHOOT(shootVel),
        IDLE(0);

        private final double outtake_vels;

        outtake_vels(double pos) {
            this.outtake_vels = pos;
        }
        public double getOuttakeVel() {
            return outtake_vels;
        }
    }

    public Command shoot = new RunToVelocity(
            outtake_controller,
            outtake_vels.SHOOT.getOuttakeVel()
    ).requires(this);

    public Command idle = new RunToVelocity(
            outtake_controller,
            outtake_vels.IDLE.getOuttakeVel()
    );

    @Override
    public void periodic() {
        outtake.setPower(outtake_controller.calculate(outtake.getState()));
        outtake.setPower(outtake_controller.calculate(outtake.getState()));

    }
}