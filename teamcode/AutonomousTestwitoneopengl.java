package org.firstinspires.ftc.teamcode;

import android.support.annotation.Nullable;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;
import java.util.Locale;

@Autonomous (name = "AutonomousTestwitonegl")
@Disabled
public class AutonomousTestwitoneopengl extends LinearOpMode
{

    //int Coor = Math.round()
    LOTBHardware robot = new LOTBHardware();
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String Vuforia_Trackable_Asset = "RoverRuckus.xml";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private static final String VUFORIA_KEY = "Afmtnq//////AAABmXiFE5Eii0OSmSrhYwOyxSNXLzUNHKjYznH2YvpEv2l+rXgaEk84AlF1PXFbbDLAx59ziFqsh9K7RyTwh9TggW1qLEMUPgrpUSFHKZ1wAACtX01yOo4acjmOjpu3FTlV0N9H/jV6aalq9H8znCkxxHjIEW6TBopNoFnAK1H+5V7ZdfPwH46uTuaWEk7/Aw5y8n8iIDSh2q5UE9ohLWXMCVWwyC+kF/lky7MRPMKnERebL4jyfHJ5JJaRN6BCP1UG66kjjG+zp7wSvBdet/bv+ktDH3f+6368UaFi2Oieg8bCNjtFGk7GrN7PLreb/19ZkXSI9Ku73LT5QzcIk/c+h69n2UpYcR7V1ai3UI6A2Isy";
    ElapsedTime runtime = new ElapsedTime();
    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: DC Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.14159265);
    Orientation angles;
    Acceleration gravity;
    double power = .6;
    double Ticks = 1120;
    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;



    BNO055IMU imu;
    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    public void runOpMode()throws InterruptedException{

        BNO055IMU.Parameters parametersB = new BNO055IMU.Parameters();
        parametersB.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parametersB.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parametersB.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parametersB.loggingEnabled      = true;
        parametersB.loggingTag          = "IMU";
        parametersB.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        composeTelemetry();
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parametersB);


        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Set up our telemetry dashboard
        /*VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);*/

        // Wait until we're told to go
        waitForStart();

        // Start the logging of measured acceleration
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        angles = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        robot.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
telemetry.addData("firstangle", angles.firstAngle);

            Find();
        }
    }

    public void Find(){

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
        //initVuforia();
        VuforiaTrackables minerals = vuforia.loadTrackablesFromAsset(Vuforia_Trackable_Asset);
        VuforiaTrackableDefaultListener gold = (VuforiaTrackableDefaultListener) minerals.get(0).getListener();
        minerals.get(0).setName(LABEL_GOLD_MINERAL);
        minerals.get(1).setName(LABEL_SILVER_MINERAL);
        robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Right_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Left_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        int saved;

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();


        if (opModeIsActive()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive()) {
            minerals.activate();
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        /*if (updatedRecognitions.size() == 3) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            int goldMineralRotation = 0;
                            int silverMineral1XRotation = 0;
                            int silverMineral2XRotation = 0;

                            for (Recognition recognition : updatedRecognitions) {
                          //Recognition recognition;
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getLeft();
                                    goldMineralRotation = (int) recognition.estimateAngleToObject(AngleUnit.DEGREES);
                                    int savedCoordinates = goldMineralX;
                                    int savedAngle = goldMineralRotation;
                                    //GoToCoordinates(savedCoordinates,savedAngle);
                                    GoToAngle(savedAngle,power);
                                    telemetry.addData("Coordinates", savedCoordinates);
                                    telemetry.addData("Angle", savedAngle);
                                    telemetry.addData("firstAngle", angles.firstAngle);
                                    saved = goldMineralX;
                                     telemetry.addData("gold mineral", saved);
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

                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                }
                            }
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
                         //if (updatedRecognitions.size() == 1) {
                            int goldMineralX = -1;
                            int goldMineralRotation = 0;


                            for (Recognition recognition : updatedRecognitions) {

                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {

                                    //goldMineralX = (int) recognition.getLeft();
                                    goldMineralX = (int) recognition.getLeft();
                                    goldMineralRotation = (int) recognition.estimateAngleToObject(AngleUnit.DEGREES);
                                    int savedCoordinates = goldMineralX;
                                    int savedAngle = goldMineralRotation;
                                    telemetry.addData("Status", "Going to Mineral");
                                    telemetry.update();
                                    VectorF angles = anglesFromTarget(gold);
                                    VectorF translation = navOffWall(gold.getPosePhone().getTranslation(), Math.toDegrees(angles.get(0) - 90), new VectorF(51,0,0));

                                    if (translation.get(0) > 0){
                                        robot.Left_Top.setPower(-.4);
                                        robot.Left_Bottom.setPower(-.4);
                                        robot.Right_Top.setPower(.4);
                                        robot.Right_Bottom.setPower(.4);
                                    } else{
                                        robot.Left_Top.setPower(.4);
                                        robot.Left_Bottom.setPower(.4);
                                        robot.Right_Top.setPower(-.4);
                                        robot.Right_Bottom.setPower(-.4);
                                    }
                                    do {
                                        if (gold.getPose() != null){
                                            translation = navOffWall(gold.getPose().getTranslation(), Math.toDegrees(angles.get(0) - 90), new VectorF(51,0,0));
                                        }
                                        idle();
                                    } while (opModeIsActive() && Math.abs(translation.get(0)) > 30);
                                    robot.Left_Top.setPower(0);
                                    robot.Left_Bottom.setPower(0);
                                    robot.Right_Top.setPower(0);
                                    robot.Right_Bottom.setPower(0);

                                    robot.Left_Top.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                                    robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                                    robot.Right_Top.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                                    robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                                    robot.Right_Top.setTargetPosition((int)(robot.Right_Top.getCurrentPosition() + ((Math.hypot(translation.get(0), translation.get(2) + 203 / 101.6 * Ticks)))));
                                    robot.Right_Bottom.setTargetPosition((int)(robot.Right_Bottom.getCurrentPosition() + ((Math.hypot(translation.get(0), translation.get(2) + 203 / 101.6 * Ticks)))));
                                    robot.Left_Bottom.setTargetPosition((int)(robot.Left_Bottom.getCurrentPosition() + ((Math.hypot(translation.get(0), translation.get(2) + 203 / 101.6 * Ticks)))));
                                    robot.Left_Top.setTargetPosition((int)(robot.Left_Top.getCurrentPosition() + ((Math.hypot(translation.get(0), translation.get(2) + 203 / 101.6 * Ticks)))));

                                    robot.Left_Top.setPower(.4);
                                    robot.Left_Bottom.setPower(.4);
                                    robot.Right_Top.setPower(.4);
                                    robot.Right_Bottom.setPower(.4);

                                    while (opModeIsActive() && robot.Left_Top.isBusy() && robot.Left_Bottom.isBusy() && robot.Right_Bottom.isBusy() && robot.Right_Top.isBusy()){
                                        idle();
                                    }
                                    robot.Left_Top.setPower(0);
                                    robot.Left_Bottom.setPower(0);
                                    robot.Right_Top.setPower(0);
                                    robot.Right_Bottom.setPower(0);

                                    while (opModeIsActive() && (gold.getPose() == null || Math.abs(gold.getPose().getTranslation().get(0)) > 10)){
                                        if (gold.getPose() == null){

                                            if (gold.getPose().getTranslation().get(0) > 0){
                                                robot.Left_Top.setPower(.4);
                                                robot.Left_Bottom.setPower(.4);
                                                robot.Right_Top.setPower(-.4);
                                                robot.Right_Bottom.setPower(-.4);
                                            } else {
                                                robot.Left_Top.setPower(-.4);
                                                robot.Left_Bottom.setPower(-.4);
                                                robot.Right_Top.setPower(.4);
                                                robot.Right_Bottom.setPower(.4);
                                            }

                                        }else {
                                            robot.Left_Top.setPower(.4);
                                            robot.Left_Bottom.setPower(.4);
                                            robot.Right_Top.setPower(-.4);
                                            robot.Right_Bottom.setPower(-.4);
                                        }
                                    }

                                    robot.Left_Top.setPower(0);
                                    robot.Left_Bottom.setPower(0);
                                    robot.Right_Top.setPower(0);
                                    robot.Right_Bottom.setPower(0);

                                    //GoToCoordinates(savedCoordinates,savedAngle);
                                }
                            }
                            if (goldMineralX != -1) {
                                telemetry.addData("Gold Mineral Status", "Gold Mineral Found");
                            }
                            else {
                                telemetry.addData("Gold Mineral Status", "No Gold Mineral");
                            }
                        //}
                        telemetry.update();
                    }
                }

            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }


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
        int newRBPosition = robot.Right_Bottom.getCurrentPosition() + (Coordinates);
        int newLTPosition = robot.Left_Top.getCurrentPosition() + (Coordinates);
        int newLBPosition = robot.Left_Bottom.getCurrentPosition() + (Coordinates);
        int newRTPosition = robot.Right_Top.getCurrentPosition() + (Coordinates);
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
        robot.Right_Top.setPower(-.6);

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

    while (angles.secondAngle < Angle){
        robot.Left_Bottom.setPower(power);
        robot.Right_Bottom.setPower(-power);
        robot.Left_Top.setPower(power);
        robot.Right_Top.setPower(-power);

    } while (angles.secondAngle > Angle){
        robot.Left_Bottom.setPower(-power);
        robot.Right_Bottom.setPower(power);
        robot.Left_Top.setPower(-power);
        robot.Right_Top.setPower(power);

    } while (Angle == angles.secondAngle){
        robot.Left_Bottom.setPower(0);
        robot.Right_Bottom.setPower(0);
        robot.Left_Top.setPower(0);
        robot.Right_Top.setPower(0);
    }

    //while (angles.firstAngle < 45)
}
    void composeTelemetry() {

// At the beginning of each telemetry update, grab a bunch of data
// from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() { @Override public void run()
        {
// Acquiring the angles is relatively expensive; we don't want
// to do that in each of the three items that need that info, as that's
// three times the necessary expense.
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            gravity = imu.getGravity();
        }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override public String value() {
                        return imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override public String value() {
                        return imu.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });

        telemetry.addLine()
                .addData("grvty", new Func<String>() {
                    @Override public String value() {
                        return gravity.toString();
                    }
                })
                .addData("mag", new Func<String>() {
                    @Override public String value() {
                        return String.format(Locale.getDefault(), "%.3f",
                                Math.sqrt(gravity.xAccel*gravity.xAccel
                                        + gravity.yAccel*gravity.yAccel
                                        + gravity.zAccel*gravity.zAccel));
                    }
                });
    }
    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit , angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
    public VectorF navOffWall(VectorF trans, double robotAngle, VectorF offWall){
        return new VectorF((float) (trans.get(0) - offWall.get(0) * Math.sin(Math.toRadians(robotAngle)) - offWall.get(2) * Math.cos(Math.toRadians(robotAngle))), trans.get(1), (float) (trans.get(2) + offWall.get(0) * Math.cos(Math.toRadians(robotAngle)) - offWall.get(2) * Math.sin(Math.toRadians(robotAngle))));
    }

    public VectorF anglesFromTarget(VuforiaTrackableDefaultListener image){
        float [] data = image.getVuforiaCameraFromTarget().getData();
        float [] [] rotation = {{data[0], data[1]}, {data[4], data[5], data[6]}, {data[8], data[9], data[10]}};

        double thetaX = Math.atan2(rotation[2][1], rotation[2][2]);
        double thetaY = Math.atan2(-rotation[2][0], Math.sqrt(rotation[2][1] * rotation[2][1] + rotation[2][2] * rotation[2][2]));
        double thetaZ = Math.atan2(rotation[1][0], rotation[0][0]);
        return new VectorF((float)thetaX, (float)thetaY, (float)thetaZ);
    }
    /*public OpenGLMatrix gertraw (){
    return get
    }
    public OpenGLMatrix getVuforiaCameraFromTarget()
    {
        synchronized (lock)
        {
            if (this.currentPoseAndCamera != null)
            {
                return this.currentPoseAndCamera.pose.toOpenGL();
            }
            else
                return null;
        }
    }*/
}
