package org.firstinspires.ftc.teamcode.programs;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.TankDrive;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;

@Autonomous(name="KYS")
public final class TestAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 70, 0);
        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                drive.actionBuilder(new Pose2d(10, 61, Math.toRadians(0)))
                        .strafeToLinearHeading(new Vector2d(45, 61), Math.toRadians(0))
                        .waitSeconds(.5)
                        //Score
                        .strafeToLinearHeading(new Vector2d(48, 41), Math.toRadians(90))
                        .waitSeconds(.5)
                        //Intake
                        .strafeToLinearHeading(new Vector2d(50, 50), Math.toRadians(45))
                        .waitSeconds(.5)
                        //Score
                        .strafeToLinearHeading(new Vector2d(58, 40), Math.toRadians(90))
                        .waitSeconds(.5)
                        //Intake
                        .strafeToLinearHeading(new Vector2d(57, 50), Math.toRadians(55))
                        .waitSeconds(.5)
                        //Score
                        .strafeToLinearHeading(new Vector2d(58, 37), Math.toRadians(130))
                        .waitSeconds(.5)
                        //Intake
                        .splineTo(new Vector2d(57, 50), Math.toRadians(55))
                        .waitSeconds(.5)
                        //Score
                        .build());
        } else if (TuningOpModes.DRIVE_CLASS.equals(TankDrive.class)) {
            TankDrive drive = new TankDrive(hardwareMap, beginPose);

            waitForStart();

            Actions.runBlocking(
                    drive.actionBuilder(beginPose)
                            .splineTo(new Vector2d(30, 30), Math.PI / 2)
                            .splineTo(new Vector2d(0, 60), Math.PI)
                            .build());
        } else {
            throw new RuntimeException();
        }
    }
}
