package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ServoPos")
public class ServoPos extends LinearOpMode{
    LOTBHardware robot = new LOTBHardware();
    public static double First = 0.0;
    public static double Second = .1;
    public static double Third = .2;
    public static double Fourth = .3;
    public static double Fifth = .4;
    public static double Sixth = .5;
    public static double Seventh = .6;
    public static double Eighth = .7;
    public static double Ninth = .8;
    public static double Tenth = .9;
    public static double Eleventh = 1;




    public void runOpMode(){
robot.init(hardwareMap);
        waitForStart();
        telemetry.addData("Driver Status ", "Initialized");
        telemetry.update();
        while (opModeIsActive()){
            if (gamepad1.a){
                robot.LEDServo.setPosition(First);
            } else if (gamepad1.x){
                robot.LEDServo.setPosition(Second);
            } else if (gamepad1.y){
                robot.LEDServo.setPosition(Third);
            } else if (gamepad1.b){
                robot.LEDServo.setPosition(Fourth);
            } else if (gamepad1.right_trigger == 1){
                robot.LEDServo.setPosition(Fifth);
            } else if (gamepad1.right_bumper){
                robot.LEDServo.setPosition(Sixth);
            } else if (gamepad1.left_trigger == 1){
                robot.LEDServo.setPosition(Seventh);
            } else if (gamepad1.left_bumper){
                robot.LEDServo.setPosition(Eighth);
            } else if (gamepad1.dpad_right){
                robot.LEDServo.setPosition(Ninth);
            } else if (gamepad1.dpad_down){
                robot.LEDServo.setPosition(Tenth);
            } else if (gamepad1.dpad_left){
                robot.LEDServo.setPosition(Eleventh);
            }
        }
    }
}
