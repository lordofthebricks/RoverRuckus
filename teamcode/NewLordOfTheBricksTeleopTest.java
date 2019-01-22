package org.firstinspires.ftc.teamcode;

import android.util.Range;

import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "SERVO TEST")
@Disabled
public class NewLordOfTheBricksTeleopTest extends LinearOpMode
{
    LOTBHardware robot = new LOTBHardware();
    public static int Move_Forward = 1;
    public static int Move_Backward = -1;
    public static double Servo = .1;
    public static double ServoP = .175;

    RevBlinkinLedDriver blinkinLedDriver;

    Telemetry.Item patternName;
    Telemetry.Item display;
    Blinky.DisplayKind displayKind;

    @Override
    public void runOpMode() throws InterruptedException{
        displayKind = Blinky.DisplayKind.MANUAL;
        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive()){
        robot.Dumper.setPosition(ServoP);
            // This is for reading in both color/ distance sensors
       /* if ((robot.RightDistanceSensor.getDistance(DistanceUnit.INCH)) <= 2.5 && (robot.LeftDistanceSensor.getDistance(DistanceUnit.INCH)) <= 2.5) {

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

            if (robot.RightColorSensor.equals(Color.YELLOW) && robot.LeftColorSensor.equals(Color.YELLOW)) {
                telemetry.addData("Color", "All Yellow");
                telemetry.update();
                robot.Blinky.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
            } else if (robot.RightColorSensor.equals(Color.YELLOW) && robot.LeftColorSensor.equals(Color.WHITE)) {
                telemetry.addData("Color", "Right Yellow, Left White");
                telemetry.update();
                robot.Blinky.setPattern(RevBlinkinLedDriver.BlinkinPattern.CONFETTI);
            } else if (robot.RightColorSensor.equals(Color.WHITE) && robot.LeftColorSensor.equals(Color.YELLOW)) {
                telemetry.addData("Color", "Right White, Left Yellow");
                telemetry.update();
                robot.Blinky.setPattern(RevBlinkinLedDriver.BlinkinPattern.LAWN_GREEN);
            } else if (robot.RightColorSensor.equals(Color.WHITE) && robot.LeftColorSensor.equals(Color.WHITE)) {
                telemetry.addData("Color", "All White");
                telemetry.update();
                robot.Blinky.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
            }

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

            if (robot.RightColorSensor.equals(Color.YELLOW)) {
                telemetry.addData("Color Right", "Yellow");
                telemetry.update();
                robot.Blinky.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLACK);
            } else if (robot.RightColorSensor.equals(Color.WHITE)) {
                telemetry.addData("Color Right", "White");
                telemetry.update();
                robot.Blinky.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_OCEAN_PALETTE);
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

            if (robot.LeftColorSensor.equals(Color.YELLOW)) {
                telemetry.addData("Color Left", "Yellow");
                telemetry.update();
                robot.Blinky.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_FOREST_PALETTE);
            } else if (robot.LeftColorSensor.equals(Color.WHITE)) {
                telemetry.addData("Color Left", "White");
                telemetry.update();
                robot.Blinky.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_RED);
            }
        }
        // This is for reading No Minerals
        else {
            telemetry.addData("No Minerals", "Still No Minerals!!!!!");
            telemetry.update();
            robot.Blinky.setPattern(RevBlinkinLedDriver.BlinkinPattern.ORANGE);
        }*/

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

            if (gamepad1.x) {
                /*robot.Dumper.setPosition(.175);*/
                ServoP += Servo;
                telemetry.addData("Dumper Status ", "Dumping");
                telemetry.update();
            } else if (gamepad1.b) {
               /* robot.Dumper.setPosition(.75);*/
               ServoP -= Servo;
                telemetry.addData("Dumper Status ", "Going up");
            }
            telemetry.update();

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
        }
    }

}