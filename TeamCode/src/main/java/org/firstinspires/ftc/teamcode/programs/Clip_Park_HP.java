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

@Autonomous(name = "Clip_Park_HP")
public class Clip_Park_HP extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-8, 61, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        SliderAction sliderAction = new SliderAction(hardwareMap);
        ClawAction clawAction = new ClawAction(hardwareMap);

        Actions.runBlocking(new SequentialAction(new ParallelAction(
                drive.actionBuilder(beginPose).strafeTo(new Vector2d(-8, 46)).build(),
                sliderAction.highChamberLoad()
        ),
                sliderAction.highChamberScore(),
                drive.actionBuilder(new Pose2d(-8, 46, Math.toRadians(270))).strafeTo(new Vector2d(-8, 29)).
                        build(),
                clawAction.openClaw(),
                clawAction.closeArm(),
                sliderAction.reset(),
                drive.actionBuilder(new Pose2d(-8, 29,
                                Math.toRadians(270))).
                        //strafeTo(new Vector2d(-11, 48))
                            strafeTo(new Vector2d(-8, 60)).
                            strafeTo(new Vector2d(-45,60)).build() //final push
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
