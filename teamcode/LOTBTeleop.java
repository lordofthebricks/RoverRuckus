package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Teleop")
@Disabled
public class LOTBTeleop extends LinearOpMode
{
    LOTBHardware robot = new LOTBHardware();
    public static int Move_Forward = 1;
    public static int Move_Backward = -1;

    @Override
    public void runOpMode() throws InterruptedException{
        robot.init(hardwareMap);
        waitForStart();



        while (opModeIsActive()){

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
                robot.Right_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Left_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                  robot.Right_Top.setPower(.6);
                  robot.Right_Bottom.setPower(-.6);
                  robot.Left_Bottom.setPower(.6);
                  robot.Left_Top.setPower(-.6);
            } while (gamepad1.left_stick_x == -1){
                robot.Right_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Right_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Left_Bottom.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Left_Top.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                robot.Right_Top.setPower(-.9);
                robot.Right_Bottom.setPower(.3);
                robot.Left_Bottom.setPower(-.3);
                robot.Left_Top.setPower(.9);
            }
            if (gamepad1.a) {
                robot.Lift.setPower(-1);
            } else if (gamepad1.y){
                  robot.Lift.setPower(1);
            } else if (gamepad1.right_trigger == 1){
                  robot.Lift.setPower(0);
            }

            /*if (gamepad1.x){
                  robot.Sweeper.setPower(1);
            } else if (gamepad1.b){
                  robot.Sweeper.setPower(-1);
            } else if (gamepad1.left_trigger == 1){
                  robot.Sweeper.setPower(0);
            }

            if (gamepad1.dpad_up){
                  robot.Shoulder.setPower(.3);
            } else if (gamepad1.dpad_down){
                  robot.Shoulder.setPower(-.3);
            }*/
        }
    }

}
