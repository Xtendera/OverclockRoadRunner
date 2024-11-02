package org.firstinspires.ftc.teamcode.programs;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.actions.ClawAction;
import org.firstinspires.ftc.teamcode.actions.SliderAction;

@Autonomous(name = "Clip_1basket_park_Basket")
public class Clip_1basket_park_Basket extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(8, 61, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        SliderAction sliderAction = new SliderAction(hardwareMap);
        ClawAction clawAction = new ClawAction(hardwareMap);

        Actions.runBlocking(new SequentialAction(new ParallelAction(
                drive.actionBuilder(beginPose).strafeTo(new Vector2d(8, 46)).build(),
                sliderAction.specHighChamber()
        ),
                sliderAction.clipSpec(),
                drive.actionBuilder(new Pose2d(8, 46, Math.toRadians(270))).strafeTo(new Vector2d(8, 29)).
                        build(),
                clawAction.openClaw(),
                clawAction.closeArm(),
                sliderAction.reset(),
                drive.actionBuilder(new Pose2d(8, 29,
                                Math.toRadians(270))).
                            strafeTo(new Vector2d(8, 48)). //back up
                            strafeTo(new Vector2d(49.5, 46)).build(), //change x,y based on where to pick up
                            clawAction.armHover(),//drop arm
                            clawAction.armCollect(),//
                            clawAction.closeClaw(),
                            clawAction.closeArm(),
                drive.actionBuilder(new Pose2d(49.5, 46,
                        Math.toRadians(270))).
                        strafeTo(new Vector2d(57,57)).
                        turnTo(Math.toRadians(45)).build(),
                        sliderAction.HighBucket(),//elevator
                        clawAction.armBasket(),//arm score
                        clawAction.openClaw(),//claw open
                        clawAction.closeArm(),
                        sliderAction.reset(),
                drive.actionBuilder(new Pose2d(57, 57,
                                Math.toRadians(45))).
                        strafeTo(new Vector2d(57,57)).
                        turnTo(Math.toRadians(90)).
                        strafeTo(new Vector2d(-45,60)).build()
                ));

    }
}
