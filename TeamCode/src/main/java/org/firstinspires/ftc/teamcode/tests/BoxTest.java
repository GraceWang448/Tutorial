package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name = "BoxTest")
public class BoxTest extends LinearOpMode {

    public Servo box;

    public DcMotorEx arm;

    public static double BOX_INITIAL = 0.475;
    public static double BOX_UP = 0;
    public static double BOX_DOWN = 0.75;

    @Override
    public void runOpMode() {

        box = hardwareMap.get(Servo.class, "box");
        arm = hardwareMap.get(DcMotorEx.class, "arm");

        box.setDirection(Servo.Direction.FORWARD);

        box.setPosition(BOX_INITIAL);

        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.a) {
                box.setPosition(BOX_INITIAL);
            }

            if (gamepad1.left_bumper) {
                box.setPosition(BOX_UP);
            }
//right bumper is starting and a is drop
            if (gamepad1.right_bumper) {
                box.setPosition(BOX_DOWN);
                sleep(1000);
                box.setPosition(0);
            }
            if (arm.getVelocity() > 0) {
                if (arm.getCurrentPosition() < 25) {
                    box.setPosition(BOX_INITIAL);
                } else if (arm.getCurrentPosition() < 100) {
                    box.setPosition(BOX_INITIAL - 0.175);
                } else if (arm.getCurrentPosition() < 575) {
                    box.setPosition(0);
                }
            }

            if (arm.getVelocity() < 0) {
                if (arm.getCurrentPosition() < 315) {
                    box.setPosition(0);
                } else if (arm.getCurrentPosition() < 125) {
                    box.setPosition(BOX_INITIAL - 0.175);
                } else if (arm.getCurrentPosition() < 75) {
                    box.setPosition(BOX_INITIAL);
                }
            }


            telemetry.addData("box", box.getPosition());
            telemetry.addData("arm", arm.getCurrentPosition());
            telemetry.addData("armV", arm.getVelocity());
            telemetry.update();
        }
    }
}