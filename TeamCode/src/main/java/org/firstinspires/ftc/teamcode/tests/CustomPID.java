package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp(name = "CustomPID")
public class CustomPID extends LinearOpMode {
    DcMotor testMotor;

    double integral = 0;
    double repetitions = 0;

    public static PIDCoefficients testPID = new PIDCoefficients(0,0,0);

    private FtcDashboard dashboard = FtcDashboard.getInstance();

    public static double TARGET_POS = 100; // 100 is default value

    ElapsedTime PIDTimer = new ElapsedTime();

    @Override
    public void runOpMode() {
        testMotor = hardwareMap.dcMotor.get("arm");

        testMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        testMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        waitForStart();

     while(opModeIsActive()) {
         if(gamepad1.a) {
             moveTestMotor(TARGET_POS);
         }

         if(gamepad1.b) {
             moveTestMotor(0);
         }

         telemetry.addData("targetPosition", TARGET_POS);
         telemetry.addData("measuredPosition", testMotor.getCurrentPosition());
//         telemetry.addData("error", TARGET_POS - testMotor.getCurrentPosition());
//         telemetry.addData("repetitions", repetitions);
         telemetry.update();
     }

    }
    void moveTestMotor(double targetPosition) {
        double error = testMotor.getCurrentPosition();
        double lastError = 0;

        /*
         * Comparison value dependent on motor tick count
         * Higher end motor tick count: higher value
         * Lower end motor tick count: lower value
         */
        while (Math.abs(error) <= 9 /*Modify with above comments*/ && repetitions < 40 /*Modify*/) {
            error = testMotor.getCurrentPosition() - targetPosition;
            double changeInError = lastError - error;
            integral += changeInError * PIDTimer.time();
            double derivative = changeInError / PIDTimer.time();
            double P = testPID.p * error;
            double I = testPID.i * integral;
            double D = testPID.d * derivative;
            testMotor.setPower(P + I + D);
            error = lastError;
            PIDTimer.reset();
            repetitions ++;
        }
    }
}