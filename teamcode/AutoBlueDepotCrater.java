package org.firstinspires.ftc.teamcode;

import android.os.storage.StorageManager;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.annotation.ElementType;
@Autonomous(name = "AutoBlueDepotCrater")
public class AutoBlueDepotCrater extends LinearOpMode
{

    LOTBHardware robot = new LOTBHardware();
    ElapsedTime runtime = new ElapsedTime();
    static final double     COUNTS_PER_MOTOR_REV    = 1120 ;    // eg: DC Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.14159265);
    /*double TTBattVolt = robot.Battery.getVoltage();
    double TBBattVolt = robot.Battery.getVoltage();
    double LTBattVolt = robot.Battery.getVoltage();
    double LBBattVolt = robot.Battery.getVoltage();*/
    static double dumperPosition = .75;
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();
        ElapsedTime runtime = new ElapsedTime();
        robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Left_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Right_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //robot.Dumper.setPosition(dumperPosition);
        //while (opModeIsActive()) {
        while (opModeIsActive() && (runtime.seconds() < 5)) {
            robot.Lift.setPower(1);
        }
        robot.Lift.setPower(0);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < .25)) {
                /*robot.Left_Bottom.setPower(.6);
                robot.Left_Top.setPower(-.6);
                robot.Right_Top.setPower(.6);
                robot.Right_Bottom.setPower(-.6);*/
            robot.Left_Bottom.setPower(-.5);
            robot.Left_Top.setPower(.5);
            robot.Right_Top.setPower(-.5);
            robot.Right_Bottom.setPower(.5);
        }
        robot.Left_Bottom.setPower(0);
        robot.Left_Top.setPower(0);
        robot.Right_Top.setPower(0);
        robot.Right_Bottom.setPower(0);

        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 5)) {
            robot.Lift.setPower(-1);
        }
        while (opModeIsActive() && (runtime.seconds() < .25 )) {
                /*robot.Left_Bottom.setPower(.6);
                robot.Left_Top.setPower(-.6);
                robot.Right_Top.setPower(.6);
                robot.Right_Bottom.setPower(-.6);*/
            robot.Left_Bottom.setPower(-.3);
            robot.Left_Top.setPower(.9);
            robot.Right_Top.setPower(-.9);
            robot.Right_Bottom.setPower(.3);
        }
        robot.Left_Bottom.setPower(0);
        robot.Left_Top.setPower(0);
        robot.Right_Top.setPower(0);
        robot.Right_Bottom.setPower(0);

        robot.Lift.setPower(0);
        runtime.reset();
        //robot.Sweeper.setPower(-.9);
telemetry.addData("Robot Status ", ".6, 100, 100, 100, 100, 3");
        encoderDrive(.6, 100,100,100,100,3);

        //robot.Sweeper.setPower(0);
        telemetry.addData("Robot Status ", ".6, 10, -10, -10, 10, 3");
        encoderDrive(.6,10,-10,-10,10,3);
        telemetry.addData("Robot Status ", ".6, 7, 7, 7, 7, 3");
        encoderDrive(.6, 7, 7, 7, 7, 3);

        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 3){
            robot.Dumper.setPosition(.180);
        }
        robot.Dumper.setPosition(.80);
        runtime.reset();
        telemetry.addData("Robot Status ", ".6, 24, -24, -24, 24, 3");
        encoderDrive(.6, 24, -24, -24, 24, 3 );
        telemetry.addData("Robot Status ", ".6, 62, 62, 62, 62, 3");
        encoderDrive(.9, 62,62,62,62,3);
        telemetry.addData("Robot Status ", ".6, -10, 10, 10, -10, 3");
        encoderDrive(.9, -10, 10, 10 , -10, 3);

        runtime.reset();

        while (opModeIsActive() && (runtime.seconds() < 5)) {
            robot.Elbow.setPosition(.3);
        }

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


//        hello
//}


