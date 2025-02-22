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

@Autonomous(name = "AAA OB_KFC4")
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
                strafeToLinearHeading(new Vector2d(56, 54), Math.toRadians(45)). // First deposit position
                stopAndAdd(new SequentialAction(sliderAction.highBucket(), clawAction.armHover())).
                waitSeconds(0.3). // 0.5
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
                strafeToLinearHeading(new Vector2d(47, 43), Math.toRadians(270)). // third piece pickup
                        stopAndAdd(new ParallelAction(
                                intakeAction.wristLeft(),
                                clawAction.armCollect(),
                                intakeAction.intake()
                        )
                ).
                waitSeconds(1).
                strafeTo(new Vector2d(56, 43)). // Third final intake position
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
                strafeToLinearHeading(new Vector2d(43, 26), Math.toRadians(0)). // Fourth piece pickup
                        stopAndAdd(new ParallelAction(
                                intakeAction.wristReset(),
                                clawAction.armCollect(),
                                intakeAction.intake()
                        )
                ).
                waitSeconds(1).
                strafeTo(new Vector2d(50, 26)). // Fourth final intake position
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
                                sliderAction.wallPickup()
                        )
                )).
                strafeToLinearHeading(new Vector2d(50, 11), Math.toRadians(80)).
                turnTo(Math.toRadians(180)).
                stopAndAdd(clawAction.armContact()).
                strafeTo(new Vector2d(20, 11)).
                waitSeconds(4).
                build());
    }
}
