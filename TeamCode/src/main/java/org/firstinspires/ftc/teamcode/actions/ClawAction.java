package org.firstinspires.ftc.teamcode.actions;
//
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawAction {
//    private final Servo clawServo;
    private final Servo armLeft;

    public ClawAction(HardwareMap hardwareMap) {
//        clawServo = hardwareMap.servo.get("claw");
        armLeft = hardwareMap.servo.get("arm");
//        clawServo.setPosition(0.85);
        armLeft.setPosition(0.7);
    }

//    public class CloseClaw implements Action {
//        private boolean isInit = false;
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            if (!isInit) {
//                clawServo.setPosition(0.85);
//                isInit = true;
//            }
//
//            return Math.abs(clawServo.getPosition() - 0.85) > 0.02;
//        }
//    }
//
//    public class OpenClaw implements Action {
//        private boolean isInit = false;
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            if (!isInit) {
//                clawServo.setPosition(0.35);
//                isInit = true;
//            }
//
//            return Math.abs(clawServo.getPosition() - 0.35) > 0.02;
//        }
//    }

    public class CloseArm implements Action {
        private boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                armLeft.setPosition(0.7);
                isInit = true;
            }

            return Math.abs(armLeft.getPosition() - 0.7) > 0.02;
        }
    }

    public class ArmHover implements Action {
        private boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                armLeft.setPosition(0.55);
                isInit = true;
            }
            return Math.abs(armLeft.getPosition() - 0.55) > 0.02;
        }
    }

    public class ArmCollect implements Action {
        private boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                armLeft.setPosition(0.12f);
                isInit = true;
            }
            return Math.abs(armLeft.getPosition() - 0.12f) > 0.02;
        }
    }
    public class ArmBasket implements Action {
        private boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                armLeft.setPosition(0.29);
                isInit = true;
            }
            return Math.abs(armLeft.getPosition() - 0.29) > 0.02;
        }
    }

    public class ArmHighBasket implements Action {
        private boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                armLeft.setPosition(0.255f);
                isInit = true;
            }
            return Math.abs(armLeft.getPosition() - 0.255f) > 0.02;
        }
    }

    public Action closeClaw() {
        return new SleepAction(0.25);
    }

    public Action closeArm() {
        return new CloseArm();
    }

    public Action openClaw() {
        return new SleepAction(0.25);
    }
    public Action armHover() {
        return new ArmHover();
    }
    public Action armCollect() {
        return new ArmCollect();
    }
    public Action armBasket() {
        return new ArmBasket();
    }

    public Action armHighBasket() {
        return new ArmHighBasket();
    }
}
