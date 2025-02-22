package org.firstinspires.ftc.teamcode.actions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SliderAction {
    private final DcMotor sliderRightMotor;
    private final DcMotor sliderLeftMotor;

    public SliderAction (HardwareMap hardwareMap) {
        sliderRightMotor = hardwareMap.dcMotor.get("slideRightMotor");
        sliderLeftMotor = hardwareMap.dcMotor.get("slideLeftMotor");
        sliderRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public class LowChamber implements Action {
        private boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                sliderRightMotor.setPower(0.4);
                sliderLeftMotor.setPower(0.4);
                isInit = true;
            }

            if (sliderRightMotor.getCurrentPosition() < 1000 && sliderLeftMotor.getCurrentPosition() < 1000) {
                return true;
            }
            return false;
        }
    }
    public class HighChamberLoad implements Action {
        private boolean isInit = false;
        private boolean isPar = false;

        public HighChamberLoad() {

        }
        public HighChamberLoad(boolean parallel) {
            isPar = parallel;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                sliderRightMotor.setTargetPosition((int) (519*3.528F));
                sliderLeftMotor.setTargetPosition((int) (519*3.528F));
                sliderRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sliderLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sliderRightMotor.setPower(1.0f);
                sliderLeftMotor.setPower(1.0f);
                isInit = true;
            }

            if (sliderRightMotor.getCurrentPosition() < (int) (519*3.528F) && sliderLeftMotor.getCurrentPosition() < (int) (519*3.528F) && !isPar) {
                return true;
            }
            return false;
        }
    }
    public class HighChamber implements Action {
        private boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                sliderRightMotor.setTargetPosition(1100);
                sliderLeftMotor.setTargetPosition(1100);
                sliderRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sliderLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sliderRightMotor.setPower(1.0f);
                sliderLeftMotor.setPower(1.0f);
                isInit = true;
            }

            return sliderRightMotor.getCurrentPosition() < 1100 && sliderLeftMotor.getCurrentPosition() < 1100;
        }
    }
    public class HighChamberScore implements Action {
        private boolean isInit = false;
        private boolean isPar = false;
        
        public HighChamberScore() {
            
        }

        public HighChamberScore(boolean parallel) {
            isPar = parallel;
        }
        
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                sliderRightMotor.setTargetPosition((int) (397*3.528F));
                sliderLeftMotor.setTargetPosition((int) (397*3.528F));
                sliderRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sliderLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sliderRightMotor.setPower(1.0f);
                sliderLeftMotor.setPower(1.0f);
                isInit = true;
            }
            if (!isPar) {
                return sliderRightMotor.getCurrentPosition() > (int) (397 * 3.528F) && sliderLeftMotor.getCurrentPosition() > (int) (397 * 3.528F);
            } else {
                return false;
            }
        }
    }
    public class HighBucket implements Action {
        private boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                sliderRightMotor.setTargetPosition((int) (825*3.528F));
                sliderLeftMotor.setTargetPosition((int) (825*3.528F));
                sliderRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sliderLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sliderRightMotor.setPower(1.0f);
                sliderLeftMotor.setPower(1.0f);
                isInit = true;
            }

            return sliderRightMotor.getCurrentPosition() < (int) (825 * 3.528F) && sliderLeftMotor.getCurrentPosition() < (int) (935 * 3.528F);
        }
    }
    public class WallPickup implements Action {
        private boolean isInit = false;
        private boolean isPar = false;

        public WallPickup() {

        }

        public WallPickup(boolean parallel) {
            isPar = parallel;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                sliderRightMotor.setTargetPosition((int) (130*3.528F));
                sliderLeftMotor.setTargetPosition((int) (130*3.528F));
                sliderRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sliderLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sliderRightMotor.setPower(1.0f);
                sliderLeftMotor.setPower(1.0f);
                isInit = true;
            }
            if (!isPar) {
                return sliderRightMotor.getCurrentPosition() < (int) (130 * 3.528F) && sliderLeftMotor.getCurrentPosition() < (int) (130 * 3.528F);
            } else {
                return false;
            }
        }
    }

    public class Reset implements Action {
        private boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                sliderRightMotor.setTargetPosition(0);
                sliderLeftMotor.setTargetPosition(0);
                sliderRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sliderLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                sliderRightMotor.setPower(1.0f);
                sliderLeftMotor.setPower(1.0f);
                isInit = true;
            }

            return sliderRightMotor.getCurrentPosition() < 0 && sliderLeftMotor.getCurrentPosition() < 0;
        }
    }

    public Action lowChamber() {
        return new LowChamber();
    }
    public Action highChamber() {
        return new HighChamber();
    }
    public Action highChamberLoad(boolean parallel) {
        return new HighChamberLoad(parallel);
    }

    public Action highChamberLoad() {
        return new HighChamberLoad();
    }

    public Action highChamberScore() {
        return new HighChamberScore();
    }
    public Action highChamberScore(boolean parallel) {
        return new HighChamberScore(parallel);
    }
    public Action highBucket() {
        return new HighBucket();
    }
    public Action wallPickup() {
        return new WallPickup();
    }
    public Action wallPickup(boolean parallel) {
        return new WallPickup(parallel);
    }
    public Action reset() {
        return new Reset();
    }
}
