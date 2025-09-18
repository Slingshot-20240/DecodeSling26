package org.firstinspires.ftc.teamcode.nextFTC.subsystems.shooter;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

public class Limelight3ASubsystem implements Subsystem {
    public static Limelight3ASubsystem INSTANCE = new Limelight3ASubsystem();
    private Limelight3ASubsystem() { }

    private Limelight3A limelight3A;

    public Command limelightDetection = new LambdaCommand()
            .setStart(() -> {
                limelight3A = ActiveOpMode.hardwareMap().get(com.qualcomm.hardware.limelightvision.Limelight3A.class, "limelight");
                limelight3A.pipelineSwitch(0);
                limelight3A.start();
            })
            .setUpdate(() -> {
                LLResult result = limelight3A.getLatestResult();
                if (result != null) {
                    if (result.isValid()) {
                        Pose3D botpose = result.getBotpose();
                        /*
                        **CALL TELEMETRY IN OPMODE INSTEAD OF HERE** IT WOULD BE MORE SIGMA HEHE
                        * no but seriously call in opmode
                        ActiveOpMode.telemetry().addData("tx", result.getTx());
                        ActiveOpMode.telemetry().addData("ty", result.getTy());
                        ActiveOpMode.telemetry().addData("Botpose", botpose.toString());
                        ActiveOpMode.telemetry().update();

                         */
                    }
                }
            });
}