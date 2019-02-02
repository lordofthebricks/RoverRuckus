package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "ServoColorTestJohn")
public class ServoColorTestJohn extends LinearOpMode
{
    LOTBHardware robot = new LOTBHardware();

    public double LED_YELLOW = .5;
    public double LED_WHITE = .69;
    public double LED_RED = .20999999999999286;
    public double LED_GREEN = 0.0;
    public double LED_BLUE = .389999999999993;
    public double LED_NONE = .82;

    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive()){
            if (gamepad1.a){
                robot.LEDServo.setPosition(LED_RED);
            } else if (gamepad1.b){
                robot.LEDServo.setPosition(LED_BLUE);
            }else if (gamepad1.x){
                robot.LEDServo.setPosition(LED_GREEN);
            } else if (gamepad1.y){
                robot.LEDServo.setPosition(LED_WHITE);
            } else if (gamepad1.dpad_down){
                robot.LEDServo.setPosition(LED_YELLOW);
            } else if (gamepad1.dpad_up){
                robot.LEDServo.setPosition(LED_NONE);
            }
        }
    }

}
