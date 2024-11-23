package org.firstinspires.ftc.teamcode.actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import java.util.Timer;
import java.util.TimerTask;

public class UtilityAction {
    public class FracSleepAction implements Action {
        Timer currentTimer;
        boolean isInit = false;
        boolean isDone = false;
        float timeSeconds;

        public FracSleepAction (float seconds) {
            timeSeconds = seconds;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                isInit = true;
            }
            currentTimer = new Timer();
            currentTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isDone = true;
                }
            }, (long) timeSeconds * 1000);
            return !isDone;
        }
    }
}
