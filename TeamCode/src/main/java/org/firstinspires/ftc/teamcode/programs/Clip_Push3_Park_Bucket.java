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

        Actions.runBlocking(new SequentialAction((Action) new ParallelAction(
                drive.actionBuilder(beginPose).strafeTo(new Vector2d(8, 46)).build(),
                sliderAction.highChamberLoad()
        ),
                sliderAction.highChamberScore(),
                drive.actionBuilder(new Pose2d(8, 46, Math.toRadians(270))).strafeTo(new Vector2d(8, 29)).
                        build(),
                specClawAction.openClaw(),
//                clawAction.closeArm(),
//                sliderAction.reset(),
                drive.actionBuilder(new Pose2d(8, 29,
                        Math.toRadians(270))).build(),
//                                clawAction.closeArm(),
//                                sliderAction.reset(),
                //strafeTo(new Vector2d(-11, 48))
                drive.actionBuilder(new Pose2d(8,29, Math.toRadians(270))).
                        strafeTo(new Vector2d(8, 48)).build(),
                clawAction.closeArm(),
                specClawAction.closeClaw(),
                sliderAction.reset(),
                drive.actionBuilder(new Pose2d(8,48,Math.toRadians(270))).
                        strafeTo(new Vector2d(36, 48)).
                        strafeTo(new Vector2d(36, 10)).
                        //turnTo(Math.toRadians(90)).
                                strafeTo(new Vector2d(48, 10)).
                        strafeTo(new Vector2d(48, 61)). //first push
                        strafeTo(new Vector2d(46, 10)). //back up
                        strafeTo(new Vector2d(57, 10)). //strafe to second piece
                        strafeTo(new Vector2d(57, 55)). //second push
                        strafeTo(new Vector2d(57, 10)). //back up
                        strafeTo(new Vector2d(63, 10)). //strafe
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
