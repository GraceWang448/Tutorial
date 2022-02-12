package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

@Config
@TeleOp(name="Arm Triggers")
public class ArmTriggers extends LinearOpMode {

    DcMotorEx arm;

    Servo box;

    public static double clip = 1.0;

    @Override
    public void runOpMode() {

        arm = hardwareMap.get(DcMotorEx.class,"arm");
        box = hardwareMap.get(Servo.class,"box");

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addLine("Ready!");
        telemetry.update();

        waitForStart();
        while(opModeIsActive()) {
            arm.setPower(Range.clip(-gamepad1.left_trigger + gamepad1.right_trigger,-clip,clip));

//            if(arm.getCurrentPosition() < 150) {
//                box.setPosition(0);
//            }
//
//            if(arm.getCurrentPosition() > 150) {
//                box.setPosition(-1);
//            }


            telemetry.addData("measuredPosition", arm.getCurrentPosition());
            telemetry.update();
        }
    }
}
