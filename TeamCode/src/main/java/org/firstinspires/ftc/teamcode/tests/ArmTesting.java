package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name = "ArmTest")
public class ArmTesting extends LinearOpMode {

    private DcMotorEx arm;

    private Servo box;

    public static double tickVelocity = 200;

    public static int positionOne = 0;
    public static int positionTwo = 38;
    public static int positionThree = 275;

    @Override
    public void runOpMode() {
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        box = hardwareMap.get(Servo.class, "box");

        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Arm", arm.getCurrentPosition());
            telemetry.update();

            if(gamepad1.a) {
                runToPosition(positionOne,arm,tickVelocity);
                box.setPosition(-0.4);
                runToPosition(positionTwo,arm,tickVelocity);
            }

            if(gamepad1.b) {
                    runToPosition(positionTwo,arm,tickVelocity);
            }
        }
    }

    private void runToPosition(int ticks, DcMotorEx motor, double velocity) {
        motor.setTargetPosition(ticks);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setVelocity(velocity);
    }
}
