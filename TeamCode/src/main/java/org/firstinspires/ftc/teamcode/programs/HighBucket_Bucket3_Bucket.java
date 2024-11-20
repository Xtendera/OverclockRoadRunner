package org.firstinspires.ftc.teamcode.programs;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.actions.ClawAction;
import org.firstinspires.ftc.teamcode.actions.IntakeAction;
import org.firstinspires.ftc.teamcode.actions.SliderAction;
import org.firstinspires.ftc.teamcode.actions.SpecClawAction;

@Autonomous(name = "HighBucket_Bucket3_Bucket")
public class HighBucket_Bucket3_Bucket extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(40, 64, Math.toRadians(0));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        SliderAction sliderAction = new SliderAction(hardwareMap);
        ClawAction clawAction = new ClawAction(hardwareMap);
        IntakeAction intakeAction = new IntakeAction(hardwareMap);
        SpecClawAction specClawAction = new SpecClawAction(hardwareMap);

        Actions.runBlocking(new SequentialAction(
                new ParallelAction(
                sliderAction.highBucket(),
                drive.actionBuilder(beginPose).splineTo(new Vector2d(55, 55), Math.toRadians(45)).build()
                ),
                clawAction.armHighBasket(),
                intakeAction.outake(),
                new SleepAction(1),
                intakeAction.stoptake(),
                new ParallelAction(
                        clawAction.closeArm(),
                        sliderAction.reset()
                ),
                drive.actionBuilder(new Pose2d(55, 55, Math.toRadians(45))).splineTo(new Vector2d(44, 38), Math.toRadians(270)).build(),
                new ParallelAction(
                        intakeAction.wristLeft(),
                        clawAction.armCollect()
                ),
                drive.actionBuilder(new Pose2d(44, 38, Math.toRadians(270))).strafeTo(new Vector2d(50, 38)).build(),
                new SleepAction(500)
        ));
    }
}
