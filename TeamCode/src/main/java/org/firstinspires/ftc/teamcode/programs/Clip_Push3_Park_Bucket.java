package org.firstinspires.ftc.teamcode.programs;

import com.acmerobotics.roadrunner.Action;
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

@Autonomous(name = "Clip_Push3_Park_Bucket")
public class Clip_Push3_Park_Bucket extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(8, 61, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        SliderAction sliderAction = new SliderAction(hardwareMap);
        ClawAction clawAction = new ClawAction(hardwareMap);
        SpecClawAction specClawAction = new SpecClawAction(hardwareMap);
        IntakeAction intakeAction = new IntakeAction(hardwareMap);

        Actions.runBlocking(new SequentialAction((Action) new ParallelAction(
                drive.actionBuilder(beginPose).strafeTo(new Vector2d(0, 46)).build(),
                sliderAction.highChamberLoad()
        ),
                drive.actionBuilder(new Pose2d(0, 46, Math.toRadians(270))).strafeTo(new Vector2d(0, 29)).
                        build(),
                sliderAction.highChamberScore(),
                specClawAction.openClaw(),
                drive.actionBuilder(new Pose2d(0,29, Math.toRadians(270))).
                        strafeTo(new Vector2d(0, 48)).build(),
                specClawAction.closeClaw(),
                sliderAction.reset(),
                drive.actionBuilder(new Pose2d(0,48,Math.toRadians(270))).
                        strafeTo(new Vector2d(36, 48)).
                        strafeTo(new Vector2d(36, 13)).
                        //turnTo(Math.toRadians(90)).
                                strafeTo(new Vector2d(48, 13)).
                        strafeTo(new Vector2d(48, 61)). //first push
                        strafeTo(new Vector2d(46, 13)). //back up
                        strafeTo(new Vector2d(57, 13)). //strafe to second piece
                        strafeTo(new Vector2d(57, 55)). //second push
                        strafeTo(new Vector2d(57, 13)).build(), //back up
                        drive.actionBuilder(new Pose2d(57, 13, Math.toRadians(270)), 9.0).strafeTo(new Vector2d(63, 13)). //strafe
                        strafeTo(new Vector2d(63, 53)).
                        strafeTo(new Vector2d(63, 40)).
                        strafeTo(new Vector2d(-40,40)).
                        strafeTo(new Vector2d(-40,60)).build() //final push

//                        turnTo(Math.toRadians(93)).
//                        strafeTo(new Vector2d(-34, 48)).
//                        strafeTo(new Vector2d(-34, 7)).
//                        turnTo(133).
//                        strafeToLinearHeading(new Vector2d(-43, 7), Math.toRadians(183)).
//                        strafeTo(new Vector2d(-43, 57)).
//                        strafeTo(new Vector2d(-43, 10)).strafeTo(new Vector2d(-52, 10)).strafeTo(new Vector2d(-52, 57)).build()
        ));
        // sleep(9999999);
    }
}
