package org.firstinspires.ftc.teamcode.actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SpecClawAction {
    private final Servo clawServo;

    public SpecClawAction(HardwareMap hardwareMap) {
        clawServo = hardwareMap.servo.get("claw2");
        clawServo.setPosition(0.7);
    }

    public class CloseClaw implements Action {
        private boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                clawServo.setPosition(0.7);
                isInit = true;
            }

            return false;
        }
    }

    public class OpenClaw implements Action {
        private boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                clawServo.setPosition(-0.6);
                isInit = true;
            }

//            return Math.abs(clawServo.getPosition() + 0.6) > 0.02;
            return false;
        }
    }

    public Action closeClaw() {
        return new CloseClaw();
    }


    public Action openClaw() {

        return new OpenClaw();
    }

}
