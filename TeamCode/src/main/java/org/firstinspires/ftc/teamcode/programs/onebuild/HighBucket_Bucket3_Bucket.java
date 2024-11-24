package org.firstinspires.ftc.teamcode.programs.onebuild;

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

@Autonomous(name = "OB_HighBucket_Bucket3_Bucket")
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

        Actions.runBlocking(drive.actionBuilder(beginPose).
                splineTo(new Vector2d(55, 53), Math.toRadians(45)). // First deposit position
                        afterTime(0, sliderAction.highBucket()).
                stopAndAdd(clawAction.armHighBasket()).
                waitSeconds(0.75f).
                stopAndAdd(intakeAction.outake()).
                waitSeconds(0.5f). // Wait for outtake
                        stopAndAdd(new ParallelAction( // Reset arms/sliders
                        intakeAction.stoptake(),
                        clawAction.closeArm(),
                        sliderAction.reset()
                )).
                splineTo(new Vector2d(44, 41), Math.toRadians(270)). // second piece pickup
                        afterTime(0, new ParallelAction(
                                intakeAction.wristLeft(),
                                clawAction.armCollect(),
                                intakeAction.intake()
                        )
                ).
                strafeTo(new Vector2d(52, 41)). // Second final intake position
                waitSeconds(0.5f).
                splineTo(new Vector2d(55, 55), Math.toRadians(45)). // (Second) Deposit position
                afterTime(0, new ParallelAction( // Also reset robot while moving, move slides up (second)
                        intakeAction.stoptake(),
                        clawAction.closeArm(),
                        sliderAction.highBucket(),
                        intakeAction.wristReset()
                )).
                stopAndAdd(clawAction.armHighBasket()).
                waitSeconds(0.75f).
                stopAndAdd(intakeAction.outake()). // Deposit second piece
                waitSeconds(0.5f).
                stopAndAdd(new ParallelAction( // Reset arms/sliders
                        intakeAction.stoptake(),
                        clawAction.closeArm(),
                        sliderAction.reset()
                )).
                splineTo(new Vector2d(48, 40), Math.toRadians(270)). // third piece pickup
                stopAndAdd(new ParallelAction(
                        intakeAction.wristLeft(),
                        clawAction.armCollect(),
                        intakeAction.intake()
                )).
                strafeTo(new Vector2d(58, 40)). // Third final intake position
                waitSeconds(0.5f).
                splineTo(new Vector2d(57, 53), Math.toRadians(45)). // (Third) Deposit position
                afterTime(0, new ParallelAction(
                        intakeAction.stoptake(),
                        clawAction.closeArm(),
                        sliderAction.highBucket(),
                        intakeAction.wristReset()
                )).
                stopAndAdd(clawAction.armHighBasket()). // Third score arm position
                waitSeconds(0.75f).
                stopAndAdd(intakeAction.outake()).
                waitSeconds(0.5f).
                stopAndAdd(new ParallelAction( // Do final reset
                        intakeAction.stoptake(),
                        clawAction.closeArm(),
                        sliderAction.reset()
                )).
                waitSeconds(4). // Prevent depowering the motors
                build());
    }
}
