package org.firstinspires.ftc.teamcode.programs.teleop;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(name = "AAA Tele V2")
public class TeleOPV2 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        boolean isNormal = false;
        if (TeleStore.getInstance().beginPose == null) {
            telemetry.addLine("No begin position found. Turning on NM and setting default program position. Use at your own risk!");
            // We don't know starting position, turn on normal mode.
            isNormal = true;
            // Set the begin position to the default
            TeleStore.getInstance().beginPose = new Pose2d(-8, 61, Math.toRadians(270));
        }
        MecanumDrive drive = new MecanumDrive(hardwareMap, TeleStore.getInstance().beginPose);

        waitForStart();
        if(isStopRequested()) return;
        while (opModeIsActive()) {
            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x
                    ),
                    -gamepad1.right_stick_x * 0.85
            ));

            drive.updatePoseEstimate();
        }
    }

}
