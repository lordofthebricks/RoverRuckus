package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

public class BLKARRWOWHRDWARE
{

    public DcMotor RightMotor;
    //public DcMotor Right_Top;
    public DcMotor LeftMotor;
   // public DcMotor Left_Top;
    //public DcMotor Sweeper;


    //public ColorSensor colorSensor;


    public double Move_Forward = .3;
    public double Move_Backward = -.3;

    public VuforiaLocalizer vuforia;


    public TFObjectDetector tfod;


    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public
    BLKARRWOWHRDWARE() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // save reference to HW Map
        hwMap = ahwMap;

        RightMotor = hwMap.get(DcMotor.class, "RightMotor");
       // Right_Top = hwMap.get(DcMotor.class, "Right_Top");
        LeftMotor = hwMap.get(DcMotor.class, "LeftMotor");
        //Left_Top = hwMap.get(DcMotor.class, "Left_Top");
        //Sweeper = hwMap.get(DcMotor.class, "Sweeper");

        //colorSensor = hwMap.get(ColorSensor.class, "Color_Sensor");

        LeftMotor.setDirection(DcMotor.Direction.REVERSE);
        //Left_Bottom.setDirection(DcMotor.Direction.REVERSE);

        RightMotor.setPower(0);
        //Right_Top.setPower(0);
        LeftMotor.setPower(0);
        //Left_Top.setPower(0);
        //Sweeper.setPower(0);
    }

    /*public void GoToCoordinates(int Coordinates){
           //String CRD = Double.toString(Coordinates);
           Right_Bottom.setTargetPosition(Coordinates);
           Left_Top.setTargetPosition(Coordinates);
           Left_Bottom.setTargetPosition(Coordinates);
           Right_Top.setTargetPosition(Coordinates);
    }*/

}
