package org.firstinspires.ftc.teamcode;

import android.provider.ContactsContract;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Hardware;

public class BLKARWHardware
{

    public DcMotor Right_Motor;
    public DcMotor Left_Motor;

    public BNO055IMU IMU;

    HardwareMap hwmap = null;
    ElapsedTime runtime = new ElapsedTime();

    public BLKARWHardware (){

    }
    public void init(HardwareMap ahwmap){

        hwmap = ahwmap;

        Right_Motor = hwmap.get(DcMotor.class, "Right_Motor");
        Left_Motor = hwmap.get(DcMotor.class, "Left_Motor");

        IMU = hwmap.get(BNO055IMU.class, "IMU");

    }

}
