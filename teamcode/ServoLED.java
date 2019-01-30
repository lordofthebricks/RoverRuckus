package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "ServoLED")
public class ServoLED extends LinearOpMode
{
    LOTBHardware robot = new LOTBHardware();
    public double LEDPos = 0;
    public double LEDSpeed = .01;

    public void runOpMode()throws InterruptedException{

        robot.init(hardwareMap);
        LEDPos = robot.LEDServo.getPosition();
        waitForStart();

        while (opModeIsActive()) {
            robot.LEDServo.setPosition(LEDPos);
            if (gamepad1.y){
                LEDPos += LEDSpeed;
                telemetry.addData("Servo Position", robot.LEDServo.getPosition());
                telemetry.update();
            }
            else if (gamepad1.a){
                LEDPos -= LEDSpeed;
                telemetry.addData("Servo Position", robot.LEDServo.getPosition());
                telemetry.update();
            }
            /*if (gamepad1.a){
                robot.Dumper.setPosition(.1);
            } else if (gamepad1.b){
                robot.Dumper.setPosition(.2);
            } else if (gamepad1.y){
                robot.Dumper.setPosition(.3);
            } else if (gamepad1.x){
                robot.Dumper.setPosition(.4);
            } else if (gamepad1.right_trigger == 1){
                robot.Dumper.setPosition(.5);
            } else if (gamepad1.left_trigger == 1){
                robot.Dumper.setPosition(.6);
            } else if (gamepad1.left_bumper){
                robot.Dumper.setPosition(.7);
            } else if (gamepad1.right_bumper){
                robot.Dumper.setPosition(.8);
            } else if (gamepad1.dpad_down){
                robot.Dumper.setPosition(.9);
            }

        }*/
        }
    }

}
