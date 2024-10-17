package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp
public class FieldCentricTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() {
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("leftBack");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("rightBack");
        DcMotor slideLeftMotor = hardwareMap.dcMotor.get("slideLeftMotor");
        DcMotor slideRightMotor = hardwareMap.dcMotor.get("slideRightMotor");
        Servo claw = hardwareMap.servo.get("claw");
        Servo armLeft = hardwareMap.servo.get("armLeft");
        Servo armRight = hardwareMap.servo.get("armRight");


        double armPosition = 0;

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        slideRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        armLeft.setDirection(Servo.Direction.REVERSE);

        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
//        slideLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        slideRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            telemetry.addData("Claw", claw.getPosition());
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = gamepad1.right_stick_x;
            // This button choice was made so that it is hard to hit on accident,
            // it can be freely changed based on preference.
            // The equivalent button is start on Xbox-style controllers.
            if (gamepad1.options) {
                imu.resetYaw();
            }

            // Change the Angle class from the new one to old if needed.
            double botHeading = imu.getRobotYawPitchRollAngles().getYaw(BNO055IMU.AngleUnit.RADIANS.toAngleUnit());

            // Rotate the movement direction counter to the bot's rotation
            double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
            double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

            rotX = rotX * 1.1;  // Counteract imperfect strafing

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
            double frontLeftPower = (rotY + rotX + rx) / denominator;
            double backLeftPower = (rotY - rotX + rx) / denominator;
            double frontRightPower = (rotY - rotX - rx) / denominator;
            double backRightPower = (rotY + rotX - rx) / denominator;

            if (gamepad2.left_bumper) {
                claw.setPosition(1);
            } else if (gamepad2.right_bumper) {
                claw.setPosition(0);
            }

            if (gamepad1.a) {
                armPosition = 300;
                // TODO: Low chamber
            } else if (gamepad1.b) {
                armPosition = 300;
                // TODO: High chamber
            } else if (gamepad1.x) {
                armPosition = 300;
                // TODO: Low basket
            } else if (gamepad1.y) {
                armPosition = 300;
                // TODO: High basket
            }

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            slideLeftMotor.setPower(-gamepad2.left_stick_y);
            slideRightMotor.setPower(-gamepad2.left_stick_y);
        }
    }
}
