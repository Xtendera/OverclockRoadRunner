package org.firstinspires.ftc.teamcode.programs.onebuild;

import androidx.annotation.NonNull;

import com.acmerobotics.roadrunner.Arclength;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.PosePath;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.actions.ClawAction;
import org.firstinspires.ftc.teamcode.actions.IntakeAction;
import org.firstinspires.ftc.teamcode.actions.SliderAction;
import org.firstinspires.ftc.teamcode.actions.SpecClawAction;

@Autonomous(name = "OB_Clip_Push3_Clip2_Park_HP")
public class Clip_Push3_Clip2_Park_HP extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-8, 64, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        SliderAction sliderAction = new SliderAction(hardwareMap);
        ClawAction clawAction = new ClawAction(hardwareMap);
        IntakeAction intakeAction = new IntakeAction(hardwareMap);
        SpecClawAction specClawAction = new SpecClawAction(hardwareMap);



        Actions.runBlocking(drive.actionBuilder(beginPose, 9).
                stopAndAdd(intakeAction.wristReset()).
                stopAndAdd(sliderAction.highChamberLoad()).
//                strafeTo(new Vector2d(0, 40)).
                strafeTo(new Vector2d(0, 31)).
                stopAndAdd(new SequentialAction(
                        sliderAction.highChamberScore(),
                        specClawAction.openClaw()
                )).
                strafeTo(new Vector2d(0, 55)).
                stopAndAdd(sliderAction.wallPickup()).
                splineTo(new Vector2d(-37,48), Math.toRadians(180)).
                strafeTo(new Vector2d(-37, 15)). // First push
                strafeTo(new Vector2d(-48, 15)).
                strafeTo(new Vector2d(-48, 58)).
                strafeTo(new Vector2d(-48, 15)). // Second push
                strafeToLinearHeading(new Vector2d(-53, 15), Math.toRadians(190)).
                strafeTo(new Vector2d(-64, 54)).
                strafeToLinearHeading(new Vector2d(-52, 48), Math.toRadians(90)).
                strafeTo(new Vector2d(-52, 60)). // Mini-strafe to prevent robot from bouncing off the wall
                strafeTo(new Vector2d(-52, 67)).
                strafeTo(new Vector2d(-40, 67)).
                stopAndAdd(new SequentialAction(
                        specClawAction.closeClaw(),
                        new SleepAction(0.75),
                        sliderAction.highChamberLoad()
                )).
                strafeToLinearHeading(new Vector2d(2, 40), Math.toRadians(270)).
                strafeTo(new Vector2d(2, 25)).
                stopAndAdd(new SequentialAction(
                        sliderAction.highChamberScore(),
                        specClawAction.openClaw()
                )).
                strafeTo(new Vector2d(2, 32)).
                stopAndAdd(sliderAction.wallPickup()).
                splineTo(new Vector2d(-52, 48), Math.toRadians(90)).
                strafeTo(new Vector2d(-52, 60)). // Mini-strafe to prevent robot from bouncing off the wall
                strafeTo(new Vector2d(-52, 67)).
                strafeTo(new Vector2d(-40, 67)).
                stopAndAdd(new SequentialAction(
                        specClawAction.closeClaw(),
                        new SleepAction(0.75),
                        sliderAction.highChamberLoad()
                )).
                strafeToLinearHeading(new Vector2d(4, 40), Math.toRadians(270)).
                strafeTo(new Vector2d(4, 27)).
                stopAndAdd(new SequentialAction(
                        sliderAction.highChamberScore(),
                        specClawAction.openClaw()
                )).
                build());
        wait(999999);
    }
}
