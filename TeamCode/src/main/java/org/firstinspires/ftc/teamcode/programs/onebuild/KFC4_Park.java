package org.firstinspires.ftc.teamcode.programs.onebuild;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
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
public class KFC4_Park extends LinearOpMode {

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
                strafeTo(new Vector2d(40, 58)).// Mini strafe to prevent wall-hitting
                strafeToLinearHeading(new Vector2d(57, 55), Math.toRadians(45)). // First deposit position
                stopAndAdd(new SequentialAction(sliderAction.highBucket(), clawAction.armHover())).
                waitSeconds(0.5). // 0.5
                stopAndAdd(intakeAction.outake()).
                waitSeconds(1).
                stopAndAdd(new SequentialAction(
                        new ParallelAction( // Reset arms/sliders
                            intakeAction.stoptake(),
                            clawAction.closeArm(),
                            sliderAction.reset()
                        )
                )).
                strafeToLinearHeading(new Vector2d(40, 42), Math.toRadians(270)). // second piece pickup
                        stopAndAdd(new ParallelAction(
                            intakeAction.wristLeft(),
                            clawAction.armCollect(),
                            intakeAction.intake()
                        )
                ).
                waitSeconds(1).
                strafeTo(new Vector2d(47, 42)). // Second final intake position
                        waitSeconds(0.4).
                stopAndAdd(new SequentialAction(intakeAction.stoptake(), intakeAction.wristReset(), clawAction.closeArm())).
                strafeToLinearHeading(new Vector2d(57, 55), Math.toRadians(45)). // Second deposit position
                stopAndAdd(new SequentialAction(sliderAction.highBucket(), clawAction.armHover())).
                waitSeconds(0.5). // 0.5
                stopAndAdd(intakeAction.outake()).
                waitSeconds(1).
                stopAndAdd(new SequentialAction(
                        new ParallelAction( // Reset arms/sliders
                                intakeAction.stoptake(),
                                clawAction.closeArm(),
                                sliderAction.reset()
                        )
                )).
                strafeToLinearHeading(new Vector2d(47, 41), Math.toRadians(270)). // third piece pickup
                        stopAndAdd(new ParallelAction(
                                intakeAction.wristLeft(),
                                clawAction.armCollect(),
                                intakeAction.intake()
                        )
                ).
                waitSeconds(1).
                strafeTo(new Vector2d(56, 41)). // Third final intake position
                        waitSeconds(0.4).
                stopAndAdd(new SequentialAction(intakeAction.stoptake(), intakeAction.wristReset(), clawAction.closeArm())).
                strafeToLinearHeading(new Vector2d(57, 55), Math.toRadians(45)). // Third deposit position
                        stopAndAdd(new SequentialAction(sliderAction.highBucket(), clawAction.armHover())).
                waitSeconds(0.5). // 0.5
                        stopAndAdd(intakeAction.outake()).
                waitSeconds(1).
                stopAndAdd(new SequentialAction(
                        new ParallelAction( // Reset arms/sliders
                                intakeAction.stoptake(),
                                clawAction.closeArm(),
                                sliderAction.reset()
                        )
                )).
                strafeToLinearHeading(new Vector2d(43, 25), Math.toRadians(0)). // Fourth piece pickup
                        stopAndAdd(new ParallelAction(
                                intakeAction.wristReset(),
                                clawAction.armCollect(),
                                intakeAction.intake()
                        )
                ).
                waitSeconds(1).
                strafeTo(new Vector2d(50, 25)). // Fourth final intake position
                        waitSeconds(0.4).
                stopAndAdd(new SequentialAction(intakeAction.stoptake(), intakeAction.wristReset(), clawAction.closeArm())).
                strafeToLinearHeading(new Vector2d(57, 55), Math.toRadians(45)). // Fourth deposit position
                        stopAndAdd(new SequentialAction(sliderAction.highBucket(), clawAction.armHover())).
                waitSeconds(0.5). // 0.5
                        stopAndAdd(intakeAction.outake()).
                waitSeconds(1).
                stopAndAdd(new SequentialAction(
                        new ParallelAction( // Reset arms/sliders
                                intakeAction.stoptake(),
                                clawAction.closeArm(),
                                sliderAction.reset()
                        )
                )).
//                waitSeconds(1). // 0.75
//                stopAndAdd(intakeAction.outake()). // Deposit second piece
//                waitSeconds(3). // 0.5
//                stopAndAdd(new ParallelAction( // Reset arms/sliders
//                        intakeAction.stoptake(),
//                        clawAction.closeArm(),
//                        sliderAction.reset()
//                )).
//                splineTo(new Vector2d(48, 40), Math.toRadians(270)). // third piece pickup
//                stopAndAdd(new ParallelAction(
//                        intakeAction.wristLeft(),
//                        clawAction.armCollect(),
//                        intakeAction.intake()
//                )).
//                strafeTo(new Vector2d(58, 40)). // Third final intake position
//                waitSeconds(1). // 0.5
//                splineTo(new Vector2d(57, 53), Math.toRadians(45)). // (Third) Deposit position
//                afterTime(0, new ParallelAction(
//                        intakeAction.stoptake(),
//                        clawAction.closeArm(),
//                        sliderAction.highBucket(),
//                        intakeAction.wristReset()
//                )).
//                stopAndAdd(clawAction.armHighBasket()). // Third score arm position
//                waitSeconds(1). // 0.75
//                stopAndAdd(intakeAction.outake()).
//                waitSeconds(1). // 0.5
//                stopAndAdd(new ParallelAction( // Do final reset
//                        intakeAction.stoptake(),
//                        clawAction.closeArm(),
//                        sliderAction.reset()
//                )).
                waitSeconds(4). // Prevent depowering the motors
                build());
    }
}
