package org.firstinspires.ftc.teamcode.programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Breadcrumbs")
public class SensorTest extends LinearOpMode {
    private DigitalChannel redLED;
    private DigitalChannel greenLED;
    private TouchSensor magneticLimit;

    @Override
    public void runOpMode() throws InterruptedException {
        redLED = hardwareMap.get(DigitalChannel.class, "red");
        greenLED = hardwareMap.get(DigitalChannel.class, "green");
        magneticLimit = hardwareMap.get(TouchSensor.class, "magLimit");

        redLED.setMode(DigitalChannel.Mode.OUTPUT);
        greenLED.setMode(DigitalChannel.Mode.OUTPUT);
        ElapsedTime mRuntime = new ElapsedTime();
        mRuntime.reset();

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("isLimited", magneticLimit.isPressed());
            telemetry.update();
            if (Math.floor(mRuntime.seconds()) % 2 == 0) {
                redLED.setState(true);
                greenLED.setState(false);
            } else {
                greenLED.setState(true);
                redLED.setState(false);
            }
        }
    }
}
