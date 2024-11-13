package org.firstinspires.ftc.teamcode.programs.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServoImplEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "AAA Tele V1")
public class TeleOPV1 extends LinearOpMode {
    public DcMotorEx climbLeft;
    public DcMotorEx climbRight;
    public DcMotorEx slideLeftMotor;
    public DcMotorEx slideRightMotor;
    public DcMotor frontLeftMotor;
    public DcMotor backLeftMotor;
    public DcMotor frontRightMotor;
    public DcMotor backRightMotor;
    public CRServoImplEx claw;
    public Servo claw2;
    public Servo arm;
    public Servo wrist;

    public DigitalChannel intakeSwitch;

    public static double spoolCircumfrence = 150;
    public static double slidesGearRatio = 18.9;
    public static int ticksPerRev = 28;
    public static double ticksPerOutputRev = ticksPerRev*slidesGearRatio;
    public static double ticksPerMM = ticksPerOutputRev/spoolCircumfrence;

    public static int slidesPos = 0;

    @Override
    public void runOpMode() {
        //Hardware map all the devices

        //Drivetrain
        frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        backLeftMotor = hardwareMap.dcMotor.get("leftBack");
        frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        backRightMotor = hardwareMap.dcMotor.get("rightBack");

        //Slides
        slideLeftMotor = hardwareMap.get(DcMotorEx.class,"slideLeftMotor");
        slideRightMotor = hardwareMap.get(DcMotorEx.class,"slideRightMotor");

        //Climbing
        climbRight = hardwareMap.get(DcMotorEx.class,"climbRight");
        climbLeft = hardwareMap.get(DcMotorEx.class,"climbLeft");

        //Manipulator
        claw = hardwareMap.get(CRServoImplEx.class,"claw");
        arm = hardwareMap.servo.get("arm");
        wrist = hardwareMap.servo.get("wrist");
        claw2 = hardwareMap.servo.get("claw2");
        intakeSwitch = hardwareMap.get(DigitalChannel.class,"intakeSwitch");

        int armPosition = 0;
        double speedMultiplyer = 1.0;
        int floorToggle = 0;
        boolean bPressed = false;

        //Reverse motors
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        slideRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //Reset slides
        slideRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideLeftMotor.setTargetPosition(0);
        slideRightMotor.setTargetPosition(0);

        slideRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        climbRight.setDirection(DcMotorSimple.Direction.REVERSE);


        //Constants
        /**************************************************************/
        //Claws
        //final double clawClosed = 0.50; //0.35
        //final double clawOpen = 0.85;
        final double claw2Open = 0.35;
        final double claw2Close = 0.74;

        //Arm
        final double armStowed = 0.11; //orig 0.14
        final double armCollect = 0.478;
        final double armHover = 0.42;
        final double armWall = 0.29;



        //Slides
        final int highBasket = 935; //mm
        final int lowBasket = 482; //mm
        final int specimenLoad = 519; //mm
        final int specimenScore = 397; //mm
        final int specimenPickup = 475;//mm
        final int bottom = 0;

        boolean specimenToggle = true;
        boolean intaking = false;
        boolean rightBumperReleased = true;

        //Wrist
        final double wristLeft = 1;
        final double wristRight = 0.45;
        final double wristCenter = 0.65;
        final double wristSide = 0;

        //Climb
        final int climbUp = -1100;
        final int climbDown = -50;

        //IMU Setup

        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        slideLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        imu.initialize(parameters);

        waitForStart();

        if (isStopRequested()) return;
        primeClimb();
        arm.setPosition(armStowed);
        while (opModeIsActive()) {
            telemetry.addData("Stall current left", slideLeftMotor.getCurrent(CurrentUnit.MILLIAMPS));
            telemetry.addData("Stall current right", slideRightMotor.getCurrent(CurrentUnit.MILLIAMPS));
            //telemetry.addData("Claw", claw.getPosition());
            //telemetry.addData("Arm Left", armLeft.getPosition());
            //telemetry.addData("Arm Right", arm.getPosition());
            //telemetry.addData("Slide Left", slideLeftMotor.getTargetPosition());
            //telemetry.addData("Slide Right", slideRightMotor.getTargetPosition());

            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x;
            double rx = 0.85 * gamepad1.right_stick_x; //halving for easy use
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

            //High speed
            if (gamepad1.a) {
                speedMultiplyer = 1.0;
                //Low speed
            } else if (gamepad1.b) {
                speedMultiplyer = 0.5;
            }

            //Manipulator
            /**********************************/
            //Intake
            telemetry.addData("intakeFull?", intakeFull());
            telemetry.addData("enabled?",claw.isPwmEnabled());
            if(! gamepad2.right_bumper){
                rightBumperReleased = true;
            }
            if (intaking){
                if(intakeFull() || (gamepad2.right_bumper && rightBumperReleased)){
                    if(gamepad2.right_bumper){
                        rightBumperReleased = false;
                    }
                    intaking = false;
                    //claw.setPower(0);
                    claw.setPwmDisable();
                }
            }
            if (! intaking &&gamepad2.right_bumper && !intakeFull() && rightBumperReleased) {
                rightBumperReleased = false;
                intaking = true;
                claw.setPower(-1);
                //Close Claw
            }else if (gamepad2.left_bumper) {
                intaking = false;
                claw.setPower(1);
            }else if (! intaking){
                //claw.setPower(0);
                claw.setPwmDisable();
            }



            if(slidesPos == bottom){
                claw2.setPosition(claw2Close);
            }else{
                if (gamepad2.right_trigger>0) {
                    claw2.setPosition(claw2Open);
                }else if (gamepad2.left_trigger > 0){
                    claw2.setPosition(claw2Close);
                }
            }

            //Wrist position
            if (gamepad2.dpad_right) {
                wrist.setPosition(wristRight);//45 right degree
            } else if (gamepad2.dpad_left) {
                wrist.setPosition(wristLeft); // 45 left degree
            } else if (gamepad2.dpad_up) {
                wrist.setPosition(wristCenter); // 0 degree
            } else if (gamepad2.dpad_down) {
                wrist.setPosition(wristSide);//90 degree
            }

            //Slides
            /***********************************/
            if ( ! gamepad2.b){
                bPressed= false;
            }
            if (gamepad2.b && floorToggle == 0 && ! bPressed) { // floor pickup
                //armLeft.setPosition(armHover);
                arm.setPosition(armHover);
                floorToggle = 1;
                bPressed = true;
            } else if (gamepad2.b && floorToggle == 1 && ! bPressed) {
                //armLeft.setPosition(armCollect);
                arm.setPosition(armCollect);
                floorToggle = 0;
                bPressed = true;
            } else if (gamepad2.a) { // stow
                //armLeft.setPosition(armStowed);
                arm.setPosition(armStowed);
                claw2.setPosition(claw2Close);
                slidesTo(bottom);
                wrist.setPosition(wristCenter); // 0 degree

            } else if (gamepad2.y) { // high basket
                //armLeft.setPosition(armStowed);
                arm.setPosition(armStowed);
                slidesTo(highBasket);


            } else if (gamepad2.x) { // low bucket
                //armLeft.setPosition(armStowed);
                //arm.setPosition(armStowed);
                slidesTo(lowBasket);

            } else if (gamepad2.share) { // wall spec pickup
                //armLeft.setPosition(armWall);
                // arm.setPosition(armWall);
                // low chamber


                slidesTo(specimenPickup);



                //Specimen score
            } else if (gamepad2.options) {
                //Flip the value of the toggle
                specimenToggle = !specimenToggle;
                if(specimenToggle){
                    slidesTo(specimenLoad);
                }else{
                    slidesTo(specimenScore);
                }

                sleep(500);


            }



            if (gamepad1.right_bumper){
                climbLeft.setTargetPosition(climbUp);
                climbRight.setTargetPosition(climbUp);
                climbLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                climbRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                climbLeft.setPower(1.0);
                climbRight.setPower(1.0);
            }else if (gamepad1.left_bumper){
                climbLeft.setTargetPosition(climbDown);
                climbRight.setTargetPosition(climbDown);
                climbLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                climbRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                climbLeft.setPower(1.0);
                climbRight.setPower(1.0);
            }

            //Manual reset
            if(gamepad1.dpad_up){
                //Move down manualy
                slideLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                slideRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                slideLeftMotor.setPower(0.5);
                slideRightMotor.setPower(0.5);
                sleep(50);
                slideLeftMotor.setPower(0);
                slideRightMotor.setPower(0);
            }else if(gamepad1.dpad_down){
                slideLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                slideRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                slideLeftMotor.setPower(-0.5);
                slideRightMotor.setPower(-0.5);
                sleep(50);
                slideLeftMotor.setPower(0);
                slideRightMotor.setPower(0);
            }else if(gamepad1.dpad_right){
                slideLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                slideRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }



            if (gamepad2.y && slideLeftMotor.getCurrentPosition() > 3250) {
                //armLeft.setPosition(0.275);
                arm.setPosition(0.275);
            }
            if (gamepad2.x && slideLeftMotor.getCurrentPosition() > 1450 && slideLeftMotor.getCurrentPosition() < 2000) {
                //armLeft.setPosition(0.3);
                arm.setPosition(0.3);
            }

            frontLeftMotor.setPower(frontLeftPower * speedMultiplyer);
            backLeftMotor.setPower(backLeftPower * speedMultiplyer);
            frontRightMotor.setPower(frontRightPower * speedMultiplyer);
            backRightMotor.setPower(backRightPower * speedMultiplyer); //0.8 power for easy use




            // slideLeftMotor.setPower(-gamepad2.left_stick_y);
            // slideRightMotor.setPower(-gamepad2.left_stick_y);
            telemetry.update();
        }
    }

    public void primeClimb(){
        climbLeft.setPower(0.5);
        while(climbLeft.getCurrent(CurrentUnit.MILLIAMPS) < 2500 && opModeIsActive()){
            telemetry.addData("Stall current", climbLeft.getCurrent(CurrentUnit.MILLIAMPS));
            //telemetry.addData("Ticks", climbLeft.getCurrentPosition());
            telemetry.update();
        }
        climbLeft.setPower(0);
        climbLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        climbRight.setPower(0.5);
        while(climbRight.getCurrent(CurrentUnit.MILLIAMPS) < 2500 && opModeIsActive()){
            telemetry.addData("Stall current", climbRight.getCurrent(CurrentUnit.MILLIAMPS));
            //telemetry.addData("Ticks", climbLeft.getCurrentPosition());
            telemetry.update();
        }
        climbRight.setPower(0);
        climbRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void slidesTo(int mm){
        int ticks = (int) (mm*ticksPerMM);
        slidesPos = mm;
        slideLeftMotor.setTargetPosition(ticks);
        slideRightMotor.setTargetPosition(ticks);
        slideLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideLeftMotor.setPower(1.0);
        slideRightMotor.setPower(1.0);
    }

    public boolean intakeFull(){
        return intakeSwitch.getState();
    }
}
