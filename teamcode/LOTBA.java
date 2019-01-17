package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "LOTBAutonomous")
@Disabled
public class LOTBA extends LinearOpMode
{
    LOTBHardware robot = new LOTBHardware();
    ElapsedTime runtime = new ElapsedTime();
    public void runOpMode() throws InterruptedException{
        robot.init(hardwareMap);
        waitForStart();


        while(opModeIsActive() && (runtime.seconds() < 3.0)){
            robot.Left_Bottom.setPower(robot.Move_Forward);
            robot.Left_Top.setPower(robot.Move_Forward);
            robot.Right_Top.setPower(robot.Move_Forward);
            robot.Right_Bottom.setPower(robot.Move_Forward);
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)){
            robot.Left_Bottom.setPower(robot.Move_Forward);
            robot.Left_Top.setPower(robot.Move_Backward);
            robot.Right_Top.setPower(robot.Move_Forward);
            robot.Right_Bottom.setPower(robot.Move_Backward);
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)){
            robot.Left_Bottom.setPower(robot.Move_Backward);
            robot.Left_Top.setPower(robot.Move_Backward);
            robot.Right_Top.setPower(robot.Move_Backward);
            robot.Right_Bottom.setPower(robot.Move_Backward);
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)){
            robot.Left_Bottom.setPower(robot.Move_Backward);
            robot.Left_Top.setPower(robot.Move_Forward);
            robot.Right_Top.setPower(robot.Move_Backward);
            robot.Right_Bottom.setPower(robot.Move_Forward);
        }
        runtime.reset();
        while(opModeIsActive() && (runtime.seconds() < 3.0)){
            robot.Left_Top.setPower(robot.Move_Forward);
            robot.Right_Bottom.setPower(robot.Move_Forward);
            robot.Right_Top.setPower(0);
            robot.Left_Bottom.setPower(0);
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)){
            robot.Left_Bottom.setPower(robot.Move_Forward);
            robot.Right_Top.setPower(robot.Move_Forward);
            robot.Left_Top.setPower(0);
            robot.Right_Bottom.setPower(0);

        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)){
            robot.Left_Top.setPower(robot.Move_Backward);
            robot.Right_Bottom.setPower(robot.Move_Backward);
            robot.Left_Bottom.setPower(0);
            robot.Right_Top.setPower(0);
        }
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 3.0)){
            robot.Left_Bottom.setPower(robot.Move_Backward);
            robot.Right_Top.setPower(robot.Move_Backward);
            robot.Left_Top.setPower(0);
            robot.Right_Bottom.setPower(0);
        }
        runtime.reset();
    }

}
