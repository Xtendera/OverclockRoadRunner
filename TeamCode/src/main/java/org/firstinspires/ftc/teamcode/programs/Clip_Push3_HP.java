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
import org.firstinspires.ftc.teamcode.actions.SpecClawAction;

@Autonomous(name = "Clip_Push3_HP")
public class Clip_Push3_HP extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-8, 61, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        SliderAction sliderAction = new SliderAction(hardwareMap);
        ClawAction clawAction = new ClawAction(hardwareMap);
        SpecClawAction specClawAction = new SpecClawAction(hardwareMap);

        Actions.runBlocking(new SequentialAction(new ParallelAction(
                drive.actionBuilder(beginPose).strafeTo(new Vector2d(-8, 46)).build(),
                sliderAction.highChamberLoad()
        ),
                drive.actionBuilder(new Pose2d(-8, 46, Math.toRadians(270))).strafeTo(new Vector2d(0, 29)).
                        build(),
                sliderAction.highChamberScore(),
                specClawAction.openClaw(),
                drive.actionBuilder(new Pose2d(0, 29,
                        Math.toRadians(270))).
                        //strafeTo(new Vector2d(-11, 48))
                        strafeTo(new Vector2d(0, 48)).build(),
                specClawAction.closeClaw(),
                sliderAction.reset(),
                drive.actionBuilder(new Pose2d(0,48, Math.toRadians(270))).
                        strafeTo(new Vector2d(-36, 48)).
                        strafeTo(new Vector2d(-36, 10)).
                        strafeTo(new Vector2d(-46, 10)).
                        strafeTo(new Vector2d(-46, 63)). //first push
                        strafeTo(new Vector2d(-46, 10)). //back up
                        strafeTo(new Vector2d(-57, 10)). //strafe to second piece
                        strafeTo(new Vector2d(-57, 63)). //second push
                        strafeTo(new Vector2d(-57, 10)). //back up
                        strafeTo(new Vector2d(-63, 10)). //strafe
                        strafeTo(new Vector2d(-63, 63)).build() //final push
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
