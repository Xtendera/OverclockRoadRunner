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

    public class SliderGoUp implements Action {
        private boolean isInit = false;
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            if (!isInit) {
                sliderRightMotor.setPower(0.4);
                sliderLeftMotor.setPower(0.4);
                isInit = true;
            }

            telemetryPacket.put("slideRightPos:", sliderRightMotor.getCurrentPosition());
            return sliderRightMotor.getCurrentPosition() < 1000 && sliderLeftMotor.getCurrentPosition() < 1000;
        }
    }
    public Action sliderGoUp() {
        return new SliderGoUp();
    }
}
