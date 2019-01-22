package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp (name = "ServoTest")
@Disabled
public class ServoTest extends LinearOpMode
{
    LOTBHardware robot = new LOTBHardware();

    public void runOpMode()throws InterruptedException{

        robot.init(hardwareMap);
        waitForStart();

        while (opModeIsActive()){
            if (gamepad1.a){
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

        }
    }

}
