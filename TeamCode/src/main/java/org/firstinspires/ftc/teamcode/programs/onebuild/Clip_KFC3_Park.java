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

@Autonomous(name = "AAA OB_CLIP_KFC3")
public class Clip_KFC3_Park extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(8, 64, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        SliderAction sliderAction = new SliderAction(hardwareMap);
        ClawAction clawAction = new ClawAction(hardwareMap);
        IntakeAction intakeAction = new IntakeAction(hardwareMap);
        SpecClawAction specClawAction = new SpecClawAction(hardwareMap);

        Actions.runBlocking(drive.actionBuilder(beginPose).
                stopAndAdd(intakeAction.wristReset()).
                stopAndAdd(sliderAction.highChamberLoad(true)).
//                strafeTo(new Vector2d(0, 40)).
                strafeTo(new Vector2d(2, 31)).
                waitSeconds(0.15).
                stopAndAdd(new SequentialAction(
                        sliderAction.highChamberScore(),
                        new SleepAction(0.3),
                        specClawAction.openClaw(),
                        new SleepAction(0.4)
                )).
                strafeTo(new Vector2d(40, 55)).
                stopAndAdd(sliderAction.reset()).
                strafeTo(new Vector2d(40, 42)). // second piece pickup
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
                strafeToLinearHeading(new Vector2d(56, 54), Math.toRadians(45)). // Second deposit position
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
                strafeToLinearHeading(new Vector2d(47, 44), Math.toRadians(270)). // third piece pickup
                        stopAndAdd(new ParallelAction(
                                intakeAction.wristLeft(),
                                clawAction.armCollect(),
                                intakeAction.intake()
                        )
                ).
                waitSeconds(1).
                strafeTo(new Vector2d(56, 44)). // Third final intake position
                        waitSeconds(0.4).
                stopAndAdd(new SequentialAction(intakeAction.stoptake(), intakeAction.wristReset(), clawAction.closeArm())).
                strafeToLinearHeading(new Vector2d(56, 54), Math.toRadians(45)). // Third deposit position
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
                strafeToLinearHeading(new Vector2d(56, 54), Math.toRadians(45)). // Fourth deposit position
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
//                strafeTo(new Vector2d(24, 11)). // Prevent depowering the motors
                build());
    }
}
