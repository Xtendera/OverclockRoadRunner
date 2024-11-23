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

@Autonomous(name = "Clip_Push3_Clip2_Park_HP")
public class Clip_Push3_Clip2_Park_HP extends LinearOpMode {

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
                drive.actionBuilder(new Pose2d(-8, 46, Math.toRadians(270)), 9).strafeTo(new Vector2d(-8, 29)).
                        build(),
                sliderAction.highChamberScore(),
                specClawAction.openClaw(),
                drive.actionBuilder(new Pose2d(-8, 29,
                        Math.toRadians(270))).
                        //strafeTo(new Vector2d(-11, 48))
                                strafeTo(new Vector2d(-8, 48)).build(),
                specClawAction.closeClaw(),
                sliderAction.reset(),
                drive.actionBuilder(new Pose2d(-8,48, Math.toRadians(270))).
                        splineTo(new Vector2d(-36,48), Math.toRadians(90)).
                        strafeTo(new Vector2d(-36, 10)).
                        strafeTo(new Vector2d(-46, 10)).
                        strafeTo(new Vector2d(-46, 60)). //first push
                        strafeTo(new Vector2d(-46, 10)). //back up
                        strafeTo(new Vector2d(-57, 10)). //strafe to second piece
                        strafeTo(new Vector2d(-57, 58)). //second push
                        strafeTo(new Vector2d(-57, 10)). //back up
                        strafeTo(new Vector2d(-64, 10)). //strafe
                        strafeTo(new Vector2d(-64, 55)). //final push
                        strafeTo(new Vector2d(-45, 63)).build(),
                        new ParallelAction(specClawAction.openClaw(),
                                sliderAction.wallPickup(),
                                drive.actionBuilder(new Pose2d(-45, 63, Math.toRadians(90))).
                                strafeTo(new Vector2d(-30, 63)).build()),
                        specClawAction.closeClaw(),
                        new ParallelAction(sliderAction.highChamberLoad(),
                                drive.actionBuilder(new Pose2d(-30, 63, Math.toRadians(90)), 9).
                                splineTo(new Vector2d(-8,29), Math.toRadians(270)).build()),
                        sliderAction.highChamberScore(),
                        specClawAction.openClaw(),
                        drive.actionBuilder(new Pose2d(-8, 29, Math.toRadians(270))).
                        strafeTo(new Vector2d(-8, 48)).build(),

                        new ParallelAction(sliderAction.wallPickup(),
                                drive.actionBuilder(new Pose2d(-8,48, Math.toRadians(270))).
                                splineTo(new Vector2d(-45, 63), Math.toRadians(90)).
                                        strafeTo(new Vector2d(-30, 63)).build()),
                        specClawAction.closeClaw(),
                        new ParallelAction(sliderAction.highChamberLoad(),
                                drive.actionBuilder(new Pose2d(-30, 63, Math.toRadians(90)), 9).
                                        splineTo(new Vector2d(-8,29), Math.toRadians(270)).build()),
                        sliderAction.highChamberScore(),
                        specClawAction.openClaw(),
                        drive.actionBuilder(new Pose2d(-8, 29, Math.toRadians(270))).
                                strafeTo(new Vector2d(-8, 48)).build()

//                        -30,63
//                        strafeToLinearHeading(new Vector2d(-43, 7), Math.toRadians(183)).
//                        strafeTo(new Vector2d(-43, 57)).
//                        strafeTo(new Vector2d(-43, 10)).strafeTo(new Vector2d(-52, 10)).strafeTo(new Vector2d(-52, 57)).build()
        ));
        // sleep(9999999);
    }
}
