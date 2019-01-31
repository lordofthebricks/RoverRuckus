package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@TeleOp(name = "NewTeleop")
public class NewLordOfTheBricksTeleop extends LinearOpMode
{
    LOTBHardware robot = new LOTBHardware();
    public static int Move_Forward = 1;
    public static int Move_Backward = -1;
    public double LED_YELLOW = .5;
    public double LED_WHITE = .69;
    public double LED_RED = .17;
    public double LED_GREEN = .08;
    public double LED_BLUE = .345;
    public double LED_NONE = .82;
    RevBlinkinLedDriver blinkinLedDriver;

    Telemetry.Item patternName;
    Telemetry.Item display;
    Blinky.DisplayKind displayKind;

    @Override
    public void runOpMode() throws InterruptedException{
        displayKind = Blinky.DisplayKind.MANUAL;
        robot.init(hardwareMap);
        robot.LEDServo.setPosition(.82);
        waitForStart();

        while (opModeIsActive()){
            telemetry.addData("Right Top Motor Speed", robot.Right_Top.getPower());
            telemetry.addData("Right Bottom Motor Speed", robot.Right_Bottom.getPower());
            telemetry.addData("Left Top Motor Speed", robot.Left_Top.getPower());
            telemetry.addData("Left Bottom Motor Speed", robot.Left_Bottom.getPower());
            //telemetry.addData("")

            // This is for reading in both color/ distance sensors
        if ((robot.RightDistanceSensor.getDistance(DistanceUnit.INCH)) <= 2.5 && (robot.LeftDistanceSensor.getDistance(DistanceUnit.INCH)) <= 2.5) {

            telemetry.addData("Distance (in)",
                    String.format(Locale.US, "%.02f", robot.LeftDistanceSensor.getDistance(DistanceUnit.INCH)));
            telemetry.update();
            telemetry.addData("Distance (in)",
                    String.format(Locale.US, "%.02f", robot.RightDistanceSensor.getDistance(DistanceUnit.INCH)));
            telemetry.update();
            telemetry.addData("Red  ", robot.RightColorSensor.red());
            telemetry.addData("Green", robot.RightColorSensor.green());
            telemetry.addData("Blue ", robot.RightColorSensor.blue());
            telemetry.addData("Yellow", robot.RightColorSensor.green() - robot.RightColorSensor.blue());
            telemetry.update();
            telemetry.addData("Red  ", robot.LeftColorSensor.red());
            telemetry.addData("Green", robot.LeftColorSensor.green());
            telemetry.addData("Blue ", robot.LeftColorSensor.blue());
            telemetry.addData("Yellow", robot.LeftColorSensor.green() - robot.LeftColorSensor.blue());
            telemetry.update();

            if (robot.RightColorSensor.equals(robot.RightColorSensor.green() - robot.RightColorSensor.blue() >= 450) && robot.LeftColorSensor.equals(robot.RightColorSensor.green() - robot.RightColorSensor.blue() >= 450)) {
                telemetry.addData("Color", "All Yellow");
                telemetry.update();
                robot.LEDServo.setPosition(LED_YELLOW);
            } else if (robot.RightColorSensor.equals(robot.RightColorSensor.green() - robot.RightColorSensor.blue() >= 450) && robot.LeftColorSensor.equals(robot.RightColorSensor.green() - robot.RightColorSensor.blue() <= 451)) {
                telemetry.addData("Color", "Right Yellow, Left White");
                telemetry.update();
                robot.LEDServo.setPosition(LED_RED);
            } else if (robot.RightColorSensor.equals(robot.RightColorSensor.green() - robot.RightColorSensor.blue() <= 451) && robot.LeftColorSensor.equals(robot.RightColorSensor.green() - robot.RightColorSensor.blue() >= 450)) {
                telemetry.addData("Color", "Right White, Left Yellow");
                telemetry.update();
                robot.LEDServo.setPosition(LED_RED);
            } else if (robot.RightColorSensor.equals(robot.RightColorSensor.green() - robot.RightColorSensor.blue() <= 451) && robot.LeftColorSensor.equals(robot.RightColorSensor.green() - robot.RightColorSensor.blue()<= 451)) {
                telemetry.addData("Color", "All White");
                telemetry.update();
                robot.LEDServo.setPosition(LED_WHITE);
            } //else robot.LEDServo.setPosition(1);

        }
            // This is for reading the right color sensor
        else if ((robot.RightDistanceSensor.getDistance(DistanceUnit.INCH)) <= 2.5) {

            telemetry.addData("Distance (in)",
                    String.format(Locale.US, "%.02f", robot.RightDistanceSensor.getDistance(DistanceUnit.INCH)));
            telemetry.update();
            telemetry.addData("Red  ", robot.RightColorSensor.red());
            telemetry.addData("Green", robot.RightColorSensor.green());
            telemetry.addData("Blue ", robot.RightColorSensor.blue());
            telemetry.addData("Yellow", robot.RightColorSensor.green() - robot.RightColorSensor.blue());
            telemetry.update();

            if (robot.RightColorSensor.equals(robot.RightColorSensor.green() - robot.RightColorSensor.blue() >= 450)) {
                telemetry.addData("Color Right", "Yellow");
                telemetry.update();
                robot.LEDServo.setPosition(LED_GREEN);
            } else if (robot.RightColorSensor.equals(robot.RightColorSensor.green() - robot.RightColorSensor.blue() <= 451)) {
                telemetry.addData("Color Right", "White");
                telemetry.update();
                robot.LEDServo.setPosition(LED_BLUE);
            }
        }
        // This is for reading the left color sensor
        else if ((robot.LeftDistanceSensor.getDistance(DistanceUnit.INCH)) <= 2.5) {

            telemetry.addData("Distance (in)",
                    String.format(Locale.US, "%.02f", robot.LeftDistanceSensor.getDistance(DistanceUnit.INCH)));
            telemetry.update();
            telemetry.addData("Red  ", robot.LeftColorSensor.red());
            telemetry.addData("Green", robot.LeftColorSensor.green());
            telemetry.addData("Blue ", robot.LeftColorSensor.blue());
            telemetry.addData("Yellow", robot.LeftColorSensor.green() - robot.LeftColorSensor.blue());
            telemetry.update();

            if (robot.RightColorSensor.equals(robot.RightColorSensor.green() - robot.RightColorSensor.blue() >= 450)) {
                telemetry.addData("Color Left", "Yellow");
                telemetry.update();
                robot.LEDServo.setPosition(LED_GREEN);
            } else if (robot.RightColorSensor.equals(robot.RightColorSensor.green() - robot.RightColorSensor.blue() <= 451)) {
                telemetry.addData("Color Left", "White");
                telemetry.update();
                robot.LEDServo.setPosition(LED_BLUE);
            }
        }
        // This is for reading No Minerals
        else {
            telemetry.addData("No Minerals", "Still No Minerals!!!!!");
            telemetry.update();
            robot.LEDServo.setPosition(LED_NONE);
        }

            /*if (robot.RightColorSensor.equals(Color.YELLOW)) {
                telemetry.addData("Color", "Yellow");
                telemetry.update();
                robot.Blinky.setPattern(patternGM);
            }

            else if (robot.RightColorSensor.equals(Color.WHITE)) {
                    telemetry.addData("Color", "White");
                    telemetry.update();
                    robot.Blinky.setPattern(patternSM);
            }

            if (robot.LeftColorSensor.equals(Color.YELLOW)) {
                telemetry.addData("Color", "Yellow");
                telemetry.update();
                robot.Blinky.setPattern(patternGM);
            }

            else if (robot.LeftColorSensor.equals(Color.WHITE)) {
                telemetry.addData("Color", "White");
                telemetry.update();
                robot.Blinky.setPattern(patternSM);
            }*/


            robot.Right_Top.setPower(-gamepad1.right_stick_y);
            robot.Right_Bottom.setPower(-gamepad1.right_stick_y);
            robot.Left_Top.setPower(-gamepad1.left_stick_y);
            robot.Left_Bottom.setPower(-gamepad1.left_stick_y);
            // robot.Conveyor.setPower(.5);
            /*while (gamepad1.right_stick_x == 1){
                robot.Right_Top.setPower(.3);
                robot.Right_Bottom.setPower(.3);
            }

            while (gamepad1.left_stick_x == 1){
                robot.Left_Top.setPower(.3);
                robot.Left_Bottom.setPower(.3);
            }

            while (gamepad1.left_stick_x == -1){
                robot.Left_Bottom.setPower(.3);
                robot.Left_Top.setPower(.3);
            }

            while (gamepad1.right_stick_x == -1){
                robot.Right_Top.setPower(.3);
                robot.Right_Bottom.setPower(.3);
            }

            while (gamepad1.left_stick_button){
                robot.Left_Bottom.setPower(-.3);
                robot.Left_Top.setPower(.3);
                robot.Right_Top.setPower(-.3);
                robot.Right_Bottom.setPower(.3);
            }

            while (gamepad1.right_stick_button){
                robot.Left_Bottom.setPower(.3);
                robot.Left_Top.setPower(-.3);
                robot.Right_Bottom.setPower(-.3);
                robot.Right_Top.setPower(.3);
            }*/



            while (gamepad1.right_stick_x == 1 && gamepad1.right_stick_y == 1){
                robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Left_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Left_Top.setPower(.6);
                robot.Right_Bottom.setPower(.6);
            } while(gamepad1.right_stick_y == -1 && gamepad1.right_stick_x == -1) {
                robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Left_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Left_Top.setPower(-.6);
                robot.Right_Bottom.setPower(-.6);
            } while (gamepad1.left_stick_x == 1 && gamepad1.right_stick_y == 1){
                robot.Right_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Left_Bottom.setPower(.6);
                robot.Right_Top.setPower(.6);
            } while(gamepad1.left_stick_x == -1 && gamepad1.left_stick_y == 1){
                robot.Right_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Left_Bottom.setPower(-.6);
                robot.Right_Top.setPower(-.6);
            } while (gamepad1.right_stick_x == 1){
                //robot.Right_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                //robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                //robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                //robot.Left_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Right_Top.setPower(-.7);
                robot.Right_Bottom.setPower(.7);
                robot.Left_Bottom.setPower(-.7);
                robot.Left_Top.setPower(.7);
            } while (gamepad1.left_stick_x == -1){
              //  robot.Right_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
              //  robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
              //  robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
              //  robot.Left_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Right_Top.setPower(.7);
                robot.Right_Bottom.setPower(-.7);
                robot.Left_Bottom.setPower(.7);
                robot.Left_Top.setPower(-.7);
            }

            if (gamepad1.a) {
                robot.Lift.setPower(-1);
            } else if (gamepad1.y){
                robot.Lift.setPower(1);
            } else if (gamepad1.right_trigger == 1){
                robot.Lift.setPower(0);
            }




            if (gamepad1.left_bumper) {
                robot.Intake.setPower(.5);
            } else if (gamepad1.right_bumper) {
                robot.Intake.setPower(-.5);
            } else if (gamepad1.left_trigger == 1) {
                robot.Intake.setPower(0);
            }

            if (gamepad1.dpad_up) {
                robot.Shoulder.setPower(.5);
            } else if (gamepad1.dpad_down) {
                robot.Shoulder.setPower(-.3);
            } else if (gamepad1.dpad_left){
                robot.Shoulder.setPower(.1);
            }

            if (gamepad1.b){
                robot.Elbow.setPosition(.3);
            }
        }
    }

}