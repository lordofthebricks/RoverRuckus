package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "Humpty_Dumpty")

public class Humpty_Dumpty extends LinearOpMode {

    LOTBHardware robot = new LOTBHardware();
    ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();


        while (opModeIsActive()) {
            while (runtime.seconds() < 2) {
                robot.Dumper.setPosition(.175);
            }
            robot.Dumper.setPosition(.75);
        }



    }
}