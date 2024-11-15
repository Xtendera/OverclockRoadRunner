package org.firstinspires.ftc.teamcode.programs.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.InstantFunction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.actions.SliderAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@TeleOp(name = "AAA Tele V2")
public class TeleOPV2 extends LinearOpMode {
    private final FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();

    // OnPress stuff
    Map<InstantFunction, Supplier<Boolean>> callbacks = new HashMap<>();
    Map<InstantFunction, Boolean> downList = new HashMap<>();

    private void addPressAction(InstantFunction action, Supplier<Boolean> supplier) {
        callbacks.put(action, supplier);
        downList.put(action, supplier.get());
    }

    private void runActions() {
        TelemetryPacket packet = new TelemetryPacket();

        // Add press actions to the actions list.
        for (Map.Entry<InstantFunction, Supplier<Boolean>> actionCB : callbacks.entrySet()) {
            if (actionCB.getValue().get() && !Boolean.TRUE.equals(downList.get(actionCB.getKey()))) {
                    runningActions.add(new SequentialAction(
                            new SleepAction(0.5), // Temp testing delay. You can remove after!
                            new InstantAction(actionCB.getKey())
                    ));
            }
            downList.put(actionCB.getKey(), actionCB.getValue().get());
        }

        // update running actions
        List<Action> newActions = new ArrayList<>();
        for (Action action : runningActions) {
            action.preview(packet.fieldOverlay()); // optional, but doesn't really impact anything
            if (action.run(packet)) {
                newActions.add(action);
            }
        }
        runningActions = newActions;

        dash.sendTelemetryPacket(packet);
    }

    @Override
    public void runOpMode() throws InterruptedException {
        boolean isNormal = false;
        if (TeleStore.getInstance().beginPose == null) {
            telemetry.addLine("No begin position found. Turning on NM and setting default program position. Use at your own risk!");
            // We don't know starting position, turn on normal mode.
            isNormal = true;
            // Set the begin position to the default
            TeleStore.getInstance().beginPose = new Pose2d(-8, 61, Math.toRadians(270));
        }
        MecanumDrive drive = new MecanumDrive(hardwareMap, TeleStore.getInstance().beginPose);

        waitForStart();
        SliderAction sliderAction = new SliderAction(hardwareMap);

        // Add ALL press callbacks here! They will automatically be run in the loop! DO NOT ADD CALLBACKS IN A LOOP!

        // Example action. The first parameter is weird java for referencing that action method.
        // The second is a small lambda that returns a boolean saying if that button is pressed or not.
        // TLDR: Every time gamepad1.a is pressed, highChamberLoad() will be ran. No need to fw loops or nothing.
        addPressAction(sliderAction::highChamberLoad, () -> gamepad1.a);

        if(isStopRequested()) return;
        while (opModeIsActive()) {
            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x
                    ),
                    -gamepad1.right_stick_x * 0.85
            ));
            drive.updatePoseEstimate(); // Updates our coordinates based on how far it thinks we have traveled.
            runActions();
        }
    }

}
