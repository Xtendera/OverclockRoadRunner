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

@Autonomous(name = "Clip_Park_Bucket")
public class Clip_Park_Bucket extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(8, 61, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        SliderAction sliderAction = new SliderAction(hardwareMap);
        ClawAction clawAction = new ClawAction(hardwareMap);

        Actions.runBlocking(new SequentialAction(new ParallelAction(
                drive.actionBuilder(beginPose).strafeTo(new Vector2d(0, 46)).build(),
                sliderAction.highChamberLoad()
        ),
                sliderAction.highChamberScore(),
                drive.actionBuilder(new Pose2d(0, 46, Math.toRadians(270))).
                        strafeTo(new Vector2d(0, 29)).
                        build(),
                clawAction.openClaw(),
                clawAction.closeArm(),
                sliderAction.reset(),
                drive.actionBuilder(new Pose2d(0, 29,
                                    Math.toRadians(270))).
                                    strafeTo(new Vector2d(0, 60)).
                                    strafeTo(new Vector2d(-45,60)).build() //final push
        ));
        // sleep(9999999);
    }
}
