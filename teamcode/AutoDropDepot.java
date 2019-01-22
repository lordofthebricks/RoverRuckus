package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;
@Autonomous(name ="AutoDropDepot", group ="Antonio")
@Disabled
public class AutoDropDepot extends LinearOpMode
{

    LOTBHardware robot = new LOTBHardware();
    ElapsedTime runtime = new ElapsedTime();

    public void runOpMode(){
        robot.init(hardwareMap);
        waitForStart();
       // runtime.reset();
        while(opModeIsActive()){
            telemetry.addData("Time", runtime.seconds());
            telemetry.update();
            //runtime.reset();
            while (opModeIsActive() && (runtime.seconds() < 3)){
                robot.Lift.setPower(.8);
            }
            robot.Lift.setPower(0);

            runtime.reset();
            while(opModeIsActive() && (runtime.seconds() < 1)){

                robot.Left_Bottom.setPower(.3);
                robot.Left_Top.setPower(-.9);
                robot.Right_Top.setPower(.9);
                robot.Right_Bottom.setPower(-.3);
            }
            while(opModeIsActive() && (runtime.seconds() < 1)){

                robot.Left_Bottom.setPower(0);
                robot.Left_Top.setPower(0);
                robot.Right_Top.setPower(0);
                robot.Right_Bottom.setPower(0);
            }

                runtime.reset();
            while(opModeIsActive() && (runtime.seconds() < 3)){
                    robot.Lift.setPower(-.8);
            }
        }

    }


}



