package org.firstinspires.ftc.teamcode.actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeAction {
    private final Servo wristServo;
    private final CRServoImplEx intakeServo;
    private final DigitalChannel intakeSwitch;
    public IntakeAction(HardwareMap hardwareMap) {
        wristServo = hardwareMap.servo.get("wrist");
        intakeServo = hardwareMap.get(CRServoImplEx.class, "claw");
        intakeSwitch = hardwareMap.get(DigitalChannel.class, "intakeSwitch");
        wristServo.setPosition(0.65);
    }

    public class WristLeft implements Action {
        boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                wristServo.setPosition(1);
                isInit = true;
            }
            return false;
        }
    }

    public class WristReset implements Action {
        boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                wristServo.setPosition(0.65);
                isInit = true;
            }
            return false;
        }
    }


    public class Outake implements Action {
        boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeServo.setPower(1.0);
            return false;
        }
    }

    public class Intake implements Action {
        boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeServo.setPower(-1.0);
            return false;
        }
    }
    public class Stoptake implements Action {
        boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            intakeServo.setPower(0);
            return false;
        }
    }

    public Action outake() {
        return new Outake();
    }

    public Action intake() {
        return new Intake();
    }

    public Action stoptake() {
        return new Stoptake();
    }

    public Action wristLeft() {
        return new WristLeft();
    }
    public Action wristReset() {
        return new WristReset();
    }
}
