package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name ="Lift_Down", group ="Antonio")
public class Lift_Down extends LinearOpMode {

    LOTBHardware robot = new LOTBHardware();
    ElapsedTime runtime = new ElapsedTime();

    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {
            telemetry.addData("Time", runtime.seconds());
            telemetry.update();
            while (runtime.seconds() < 3) {
                robot.Lift.setPower(-1);
            }

        }

    }

}