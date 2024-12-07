package org.firstinspires.ftc.teamcode.programs.onebuild;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.actions.ClawAction;
import org.firstinspires.ftc.teamcode.actions.IntakeAction;
import org.firstinspires.ftc.teamcode.actions.SliderAction;
import org.firstinspires.ftc.teamcode.actions.SpecClawAction;

@Autonomous(name = "RoadrunnerFixYourDangCodeOrElse")
public class IWillMurder extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(40, 64, Math.toRadians(270));
        MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
        waitForStart();
        SliderAction sliderAction = new SliderAction(hardwareMap);
        ClawAction clawAction = new ClawAction(hardwareMap);
        IntakeAction intakeAction = new IntakeAction(hardwareMap);
        SpecClawAction specClawAction = new SpecClawAction(hardwareMap);

        Actions.runBlocking(drive.actionBuilder(beginPose).
                afterTime(0, sliderAction.highBucket()).
                strafeTo(new Vector2d(40, 32)).
                waitSeconds(0.9).
                stopAndAdd(intakeAction.outake()).
                waitSeconds(0.9).
                stopAndAdd(sliderAction.reset()).
                waitSeconds(4).
                build());

    }
}
