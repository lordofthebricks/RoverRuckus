package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "Position1Alliance")

public class Position1Alliance extends LinearOpMode
{

    //int Coor = Math.round()
    LOTBHardware robot = new LOTBHardware();
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "AeNvkLn/////AAABmdnuZ8WnxkB4gq/UchazF7Jhi0jxEt/8ogNMFqNQ1cnklNl2EcvV3Yv+uRdtuYUcMnx8HKeZ3UPMwvLEyY8I+a7q21AQXiIFqHT+WZzqe5/e7HPzARhbiZ5jg5hLtx8V8bKby/HjJcZt26/pvQ6XlbhFK1AH3nAs3R9GHFb6GJOxVvlYmAtrWKIyx4x/Au67uyiFytK5gWG666XrNrbvpuj8daNMrQ6xyuKvtIAPJ8/PuOVWfKL0S7Kn0VP8EAcOQV89O7WSW+35Ufqoh8Xjlfi3vEuvIne1RgfY4Q1ZXmKn43Sd1xyYubFmIRWf6QSuHfsskxKa+s4xq1LsWE7jmkvEbAstBQzhcvoRneWoq4VD";
    ElapsedTime runtime = new ElapsedTime();
    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: DC Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.14159265);
    Orientation angles;
    public static double correction;
    //static double dumperPosition = .75;
    public static double globalAngle = .30;
    public static double power = correction;
    Orientation lastAngles = new Orientation();

    //double power = .6;
    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;
    //BNO055IMU imu;
    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    public void runOpMode()throws InterruptedException{
        runtime.reset();
        /*BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);*/

        // Set up our telemetry dashboard
        // composeTelemetry();

        // Wait until we're told to go
        //waitForStart();

        // Start the logging of measured acceleration
        //imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        // angles = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;

        robot.init(hardwareMap);

        robot.IMU.initialize(parameters);

        telemetry.addData("Mode", "calibrating...");
        telemetry.update();

        while (!isStopRequested() && !robot.IMU.isGyroCalibrated()){
            sleep(50);
            idle();
        }
        telemetry.addData("angle", getAngle());
        telemetry.addData("Mode", "waiting for start");
        telemetry.addData("imu calib status", robot.IMU.getCalibrationStatus().toString());
        telemetry.update();

        waitForStart();
        telemetry.addData("Mode", "running");
        telemetry.update();

        robot.Left_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Right_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        correction = checkDirection();

        // telemetry.addData("1 imu heading", lastAngles.firstAngle);
        telemetry.addData("2 global heading", globalAngle);
        telemetry.addData("3 correction", correction);
        telemetry.update();

       // robot.Left_Top.setPower(-power + correction);
        //robot.Left_Bottom.setPower(-power + correction);
        //robot.Right_Bottom.setPower(-power);
        //robot.Right_Top.setPower(-power);

        while (opModeIsActive()) {
            // telemetry.addData("firstangle", angles.firstAngle);

            Find();
        }
    }

    public void Find(){

runtime.reset();

        Recognition recognitions;


        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code on the next line, between the double quotes.
         */



        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();
        int saved;

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();

        waitForStart();

        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive() && (runtime.seconds() < 5)){
                robot.Lift.setPower(1);
            }

            robot.Lift.setPower(0);

            runtime.reset();

            while (opModeIsActive() && (runtime.seconds() < .35)){
                robot.Right_Bottom.setPower(.6);
                robot.Right_Top.setPower(-.6);
                robot.Left_Bottom.setPower(-.6);
                robot.Left_Top.setPower(.6);
            }

            robot.Left_Bottom.setPower(0);
            robot.Left_Top.setPower(0);
            robot.Right_Top.setPower(0);
            robot.Right_Bottom.setPower(0);

            runtime.reset();

            while (opModeIsActive() && (runtime.seconds() < 2)){
                robot.Lift.setPower(-1);
            }

            robot.Lift.setPower(0);

            runtime.reset();

            while (opModeIsActive() && (runtime.seconds() < .3)){
                robot.Right_Top.setPower(.6);
                robot.Right_Bottom.setPower(-.6);
                robot.Left_Top.setPower(-.6);
                robot.Left_Bottom.setPower(.6);
            }

            robot.Left_Bottom.setPower(0);
            robot.Left_Top.setPower(0);
            robot.Right_Top.setPower(0);
            robot.Right_Bottom.setPower(0);

            runtime.reset();
            while (opModeIsActive()) {


                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 2) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            int goldMineralRotation = 0;
                            int silverMineral1XRotation = 0;
                            int silverMineral2XRotation = 0;



                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                   // goldMineralRotation = (int) recognition.estimateAngleToObject(AngleUnit.DEGREES);
                                    int savedCoordinates = goldMineralX;
                                    //int savedAngle = goldMineralRotation;
                                    //GoToCoordinates(savedCoordinates,savedAngle);
                                   // GoToAngle(savedAngle,power);
                                    telemetry.addData("Coordinates", savedCoordinates);
                                    //telemetry.addData("Angle", savedAngle);
                                    //telemetry.addData("firstAngle", angles.firstAngle);
                                    //saved = goldMineralX;
                                    //telemetry.addData("gold mineral", saved);
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getLeft();
                                    silverMineral1XRotation = (int) recognition.estimateAngleToObject(AngleUnit.DEGREES);
                                } else {
                                    silverMineral2X = (int) recognition.getLeft();
                                    silverMineral2XRotation = (int) recognition.estimateAngleToObject(AngleUnit.DEGREES);
                                }
                            }

                            telemetry.addData("Status", "Going to Mineral");
                            //  GoToCoordinates(saved);

                            if (goldMineralX != -1 && silverMineral1X != -1) {
                                telemetry.addData("Minearals ", "Not equal to -1");
                                //if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                //if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                if (goldMineralX < silverMineral1X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    encoderDrive(1,20,20,20,20,3);
                                    encoderDrive(.9, 20,-20,20,-20,3);
                                    encoderDrive(.9, 25,25,25,25,3);
                                    encoderDrive(.6, 5,-5,-5,5,3);
                                    encoderDrive(1, 20,20,20,20,5);
                                    encoderDrive(.9, 7,-7,-7,7,3);
                                    //encoderDrive(.4,-3.72,3.72,3.72,-3.72, 3);
                                    //encoderDrive(.4,38,38,38,38,3);
                                    //encoderDrive(.4, 10,10,10,10,3);
                                   // encoderDrive(.4,5,-5,-5,5,3);
                                    //encoderDrive(.4,9.2,-9.2,-9.2,9.2,3);
                                    //encoderDrive(.4,16,16,16,16,3);
                                    while (runtime.seconds() < 3){
                                        robot.Dumper.setPosition(.175);
                                    }
                                    robot.Dumper.setPosition(.75);
                                    runtime.reset();
                                    encoderDrive(.9, 5,5,5,5,3);
                                    encoderDrive(.9, -3,-3,-3,-3,3);
                                    encoderDrive(.9, -20,20,-20,20,5);
                                    encoderDrive(.9, 2,2,2,2,3);
                                    encoderDrive(.9, -60,60,-60,60,7);
                                    encoderDrive(.9, -5,-5,-5,-5,5);
                                    encoderDrive(.9, 22,-22,-22,22,5);
                                    encoderDrive(.9, 4,4,4,4,3);
                                   // encoderDrive(.4,-114,114,-114,114,3);

                                    robot.Elbow.setPosition(.3);
                                    //rotate(-20,.3);
                                    /*while (robot.IMU.angles.secondAngle < 20){

                                    }*/
                               // } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                } else if (goldMineralX > silverMineral1X ) {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    encoderDrive(.9,38,38,38,38,3);
                                   // encoderDrive(.4,6,6,6,6,3);
                                    encoderDrive(.9,12,12,12,12,3);
                                   // encoderDrive(.4, 5,-5,-5,5,3);
                                   // encoderDrive(.4, 14,14,14,14,3);
                                   // encoderDrive(.4,8,-8,-8,8,1);
                                    while (runtime.seconds() < 3){
                                        robot.Dumper.setPosition(.175);
                                    }
                                    robot.Dumper.setPosition(.75);
                                    runtime.reset();
                                  encoderDrive(.9,-35,-35,-35,-35,6);
                                  //Right top is set negative, Right bottom is set postive, Left top is set positive, Left bottom is set negative
                                  //encoderDrive(.7, 3, -3, 3,-3, 1);
                                  encoderDrive(.9, -47,47,-47,47,5);
                                  encoderDrive(.9,29,-29,-29,29,3);
                                  encoderDrive(.9,7,7,7,7,3);
                                  //encoderDrive(.7,-30,-30,-30,-30,4);
                                  //encoderDrive(.5,-7, 7, -7, 7, 3);
                                  //encoderDrive(.4,46.5,-46.5,-46.5,46.5,5);
                                  //encoderDrive(.9, 15,15,15,15,3);
                                  //encoderDrive(.4, 7,0,0,7,3);

                                  robot.Elbow.setPosition(.3);
                                    //TEST  encoderDrive(.4,90,90,90,90,3);
                                    //encoderDrive(.4,-6,6,6,-6,3);
                                    //encoderDrive(.4,);
                                    //rotate(20,.3);
                                }
                                }
                            if (goldMineralX == -1){
                                telemetry.addData("Gold Mineral Position", "Right");
                                encoderDrive(1,16,16,16,16,3);
                                encoderDrive(.9, -20,20,-20,20,3);
                                encoderDrive(.9, 30,30,30,30,3);
                                encoderDrive(.9, -5,5,5,-5,3);
                                encoderDrive(.9, 15,15,15,15,3);

                               // encoderDrive(.4,3.72,-3.72,-3.72,3.72,3);
                               // encoderDrive(.4,38,38,38,38,3);
                               // encoderDrive(.4,-7.44,7.44,7.44,-7.44,3);
                               // encoderDrive(.4,18,18,18,18,3);

                                while (runtime.seconds() < 3){
                                    robot.Dumper.setPosition(.175);
                                }
                                robot.Dumper.setPosition(.75);
                                runtime.reset();
                                encoderDrive(.9, -10,-10,-10,-10,3);
                                encoderDrive(.9, -4,4,4,-4,3);
                                encoderDrive(1, -57,-57,-57,-57,5);
                                encoderDrive(.9, 45,-45,-45,45,3);

                                robot.Elbow.setPosition(.3);

                            }
                        }
                       /* else if (updatedRecognitions.size() == 0){


                        }*/
                        /*else if (updatedRecognitions.size() == 2) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int goldMineralRotation = 0;
                            int silverMineral1XRotation = 0;
                            int silverMineral2XRotation = 0;

                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                    telemetry.addData("Status", "Going to Mineral");
                                    goldMineralRotation = (int) recognition.estimateAngleToObject(AngleUnit.DEGREES);
                                    int savedCoordinates = goldMineralX;
                                    int savedAngle = goldMineralRotation;
                                    GoToCoordinates(savedCoordinates,savedAngle);
                                } else{
                                    silverMineral1X = (int) recognition.getLeft();
                                }
                            }
                            if (goldMineralX != -1 && silverMineral1X != -1) {
                                if (goldMineralX < silverMineral1X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                } else if (goldMineralX > silverMineral1X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                } else {
                                    telemetry.addData("Gold Mineral Position", "No Gold Mineral");
                                }
                            }
                        }
                        else if (updatedRecognitions.size() == 1) {
                            int goldMineralX = -1;
                            int goldMineralRotation = 0;
                            int silverMineral1XRotation = 0;
                            int silverMineral2XRotation = 0;


                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                    telemetry.addData("Status", "Going to Mineral");
                                    goldMineralRotation = (int) recognition.estimateAngleToObject(AngleUnit.DEGREES);
                                    int savedCoordinates = goldMineralX;
                                    int savedAngle = goldMineralRotation;
                                    GoToCoordinates(savedCoordinates,savedAngle);
                                }
                            }
                            if (goldMineralX != -1) {
                                telemetry.addData("Gold Mineral Status", "Gold Mineral Found");
                            }
                            else {
                                telemetry.addData("Gold Mineral Status", "No Gold Mineral");
                            }
                        }*/
                        telemetry.update();
                    }
                }

            }
        }

        //if (tfod != null) {
         //   tfod.shutdown();
        //}


        /**
         * Initialize the Vuforia localization engine.
         */


        /**
         * Initialize the Tensor Flow Object Detection engine.
         */

    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    public void MoveToGold(){
        initVuforia();
    }

    public void GoToCoordinates(int Coordinates, int Angle){
        //String CRD = Double.toString(Coordinates);
        GoToAngle(Angle, power);
        int newRBPositionTop = robot.Right_Top.getCurrentPosition() + (Coordinates);
        int newRBPositionBottom = robot.Right_Bottom.getCurrentPosition() + (Coordinates);
        int newLTPositionBottom = robot.Left_Bottom.getCurrentPosition() + (Coordinates);
        int newLTPositionTop = robot.Left_Top.getCurrentPosition() + (Coordinates);
        robot.Right_Top.setTargetPosition(newRBPositionTop);
        robot.Right_Bottom.setTargetPosition(newRBPositionBottom);
        robot.Left_Bottom.setTargetPosition(newLTPositionBottom);
        robot.Left_Top.setTargetPosition(newRBPositionTop);
        robot.Right_Top.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.Left_Top.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.Right_Top.setPower(-.6);
        robot.Right_Bottom.setPower(-.6);
        robot.Left_Bottom.setPower(-.6);
        robot.Left_Top.setPower(-.6);

    }

    /*public void encoderDrive(double speed,
                             double Left_Bottom_Inches,
                             double Right_Bottom_Inches,
                             double Right_Top_Inches,
                             double Left_Top_Inches,
                             double timeoutS) {
        int newLeftBottomTarget;
        int newRightBottomTarget;
        int newRightTopTarget;
        int newLeftTopTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            newLeftBottomTarget = robot.Left_Bottom.getCurrentPosition() + (int)(Left_Bottom_Inches * COUNTS_PER_INCH);
            newRightBottomTarget = robot.Right_Bottom.getCurrentPosition() + (int)(Right_Bottom_Inches * COUNTS_PER_INCH);
            newRightTopTarget = robot.Right_Top.getCurrentPosition() + (int) (Right_Top_Inches * COUNTS_PER_INCH);
            newLeftTopTarget = robot.Left_Top.getCurrentPosition() + (int) (Left_Top_Inches * COUNTS_PER_INCH);

            robot.Left_Bottom.setTargetPosition(newLeftBottomTarget);
            robot.Right_Bottom.setTargetPosition(newRightBottomTarget);
            robot.Right_Top.setTargetPosition(newRightTopTarget);
            robot.Left_Top.setTargetPosition(newLeftTopTarget);

            // Turn On RUN_TO_POSITION
            robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Left_Top.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Right_Top.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.Left_Bottom.setPower(Math.abs(speed));
            robot.Right_Bottom.setPower(Math.abs(speed));
            robot.Left_Top.setPower(Math.abs(speed));
            robot.Right_Top.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.Left_Bottom.isBusy() && robot.Right_Bottom.isBusy() && robot.Left_Top.isBusy() && robot.Right_Top.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftBottomTarget,newRightBottomTarget,newLeftTopTarget,newRightTopTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.Left_Bottom.getCurrentPosition(),
                        robot.Right_Bottom.getCurrentPosition());
                robot.Left_Top.getCurrentPosition();
                robot.Right_Top.getCurrentPosition();
                telemetry.update();
            }

            // Stop all motion;
            robot.Left_Bottom.setPower(0);
            robot.Right_Bottom.setPower(0);
            robot.Left_Top.setPower(0);
            robot.Right_Top.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Left_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Right_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move

        }
    }*/
    public void GoToAngle(int Angle, double power){
    /*int newRBPosition = robot.Right_Bottom.getCurrentPosition() + (Angle);
    int newLTPosition = robot.Left_Top.getCurrentPosition() + (Angle);
    int newLBPosition = robot.Left_Bottom.getCurrentPosition() + (Angle);
    int newRTPosition = robot.Right_Top.getCurrentPosition() + (Angle);
    robot.Right_Bottom.setTargetPosition(newRBPosition);
    robot.Left_Top.setTargetPosition(newLTPosition);
    robot.Left_Bottom.setTargetPosition(newLBPosition);
    robot.Right_Top.setTargetPosition(newRTPosition);
    robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.Left_Top.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.Right_Top.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    robot.Right_Bottom.setPower(-.6);
    robot.Left_Top.setPower(-.6);
    robot.Left_Bottom.setPower(-.6);
    robot.Right_Top.setPower(-.6);*/
        while (angles.firstAngle < Angle){
            robot.Right_Top.setPower(-power);
            robot.Right_Bottom.setPower(-power);
            robot.Left_Bottom.setPower(power);
            robot.Left_Top.setPower(power);
        } while (angles.firstAngle > Angle){
            robot.Right_Top.setPower(power);
            robot.Right_Bottom.setPower(power);
            robot.Left_Bottom.setPower(-power);
            robot.Left_Top.setPower(-power);
        } while (Angle == angles.firstAngle){
            robot.Right_Top.setPower(0);
            robot.Right_Bottom.setPower(0);
            robot.Left_Bottom.setPower(0);
            robot.Left_Top.setPower(0);
        }
    }

    private void resetAngle()
    {
        lastAngles = robot.IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }







    private double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = robot.IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }










    private double checkDirection()
    {
        // The gain value determines how sensitive the correction is to direction changes.
        // You will have to experiment with your robot to get small smooth direction changes
        // to stay on a straight line.
        double correction, angle, gain = .10;

        angle = getAngle();

        if (angle == 0)
            correction = 0;             // no adjustment.
        else
            correction = -angle;        // reverse sign of angle for correction.

        correction = correction * gain;

        return correction;
    }










    private void rotate(int degrees, double power)
    {
        double  leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0)
        {   // turn right.
            leftPower = -power;
            rightPower = power;
        }
        else if (degrees > 0)
        {   // turn left.
            leftPower = power;
            rightPower = -power;
        }
        else return;

        // set power to rotate.
        robot.Right_Top.setPower(rightPower);
        robot.Right_Bottom.setPower(rightPower);
        robot.Left_Bottom.setPower(leftPower);
        robot.Left_Top.setPower(leftPower);

        // rotate until turn is completed.
        if (degrees < 0)
        {
            // On right turn we have to get off zero first.
            //while (opModeIsActive() && getAngle() == 0) {}

            while (opModeIsActive() && getAngle() > degrees)
            {

                robot.Right_Top.setPower(-power);
                robot.Right_Bottom.setPower(-power);
                robot.Left_Bottom.setPower(power);
                robot.Left_Top.setPower(power);

            }
        }
        else {  // left turn.
            while (opModeIsActive() && getAngle() < degrees) {

                robot.Right_Top.setPower(power);
                robot.Right_Bottom.setPower(power);
                robot.Left_Bottom.setPower(-power);
                robot.Left_Top.setPower(-power);

            }
        }

        // turn the motors off.
        robot.Right_Top.setPower(0);
        robot.Right_Bottom.setPower(0);
        robot.Left_Bottom.setPower(0);
        robot.Left_Top.setPower(0);

        // wait for rotation to stop.
        sleep(1000);

        // reset angle tracking on new heading.
        resetAngle();
    }

    public void encoderDrive(double speed,
                             double Left_Bottom_Inches,
                             double Right_Bottom_Inches,
                             double Right_Top_Inches,
                             double Left_Top_Inches,
                             double timeoutS) {
        int newLeftBottomTarget;
        int newRightBottomTarget;
        int newRightTopTarget;
        int newLeftTopTarget;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {


            // Determine new target position, and pass to motor controller
            newLeftBottomTarget = robot.Left_Bottom.getCurrentPosition() + (int)(Left_Bottom_Inches * COUNTS_PER_INCH);
            newRightBottomTarget = robot.Right_Bottom.getCurrentPosition() + (int)(Right_Bottom_Inches * COUNTS_PER_INCH);
            newRightTopTarget = robot.Right_Top.getCurrentPosition() + (int) (Right_Top_Inches * COUNTS_PER_INCH);
            newLeftTopTarget = robot.Left_Top.getCurrentPosition() + (int) (Left_Top_Inches * COUNTS_PER_INCH);

            robot.Left_Bottom.setTargetPosition(newLeftBottomTarget);
            robot.Right_Bottom.setTargetPosition(newRightBottomTarget);
            robot.Right_Top.setTargetPosition(newRightTopTarget);
            robot.Left_Top.setTargetPosition(newLeftTopTarget);

            // Turn On RUN_TO_POSITION
            robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Left_Top.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.Right_Top.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.Left_Bottom.setPower(Math.abs(speed));
            robot.Right_Bottom.setPower(Math.abs(speed));
            robot.Left_Top.setPower(Math.abs(speed));
            robot.Right_Top.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.Left_Bottom.isBusy() && robot.Right_Bottom.isBusy() && robot.Left_Top.isBusy() && robot.Right_Top.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d", newLeftBottomTarget,newRightBottomTarget,newLeftTopTarget,newRightTopTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d",
                        robot.Left_Bottom.getCurrentPosition(),
                        robot.Right_Bottom.getCurrentPosition());
                robot.Left_Top.getCurrentPosition();
                robot.Right_Top.getCurrentPosition();
                telemetry.update();
            }

            // Stop all motion;
            robot.Left_Bottom.setPower(0);
            robot.Right_Bottom.setPower(0);
            robot.Left_Top.setPower(0);
            robot.Right_Top.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Left_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.Right_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move

        }
    }

}
