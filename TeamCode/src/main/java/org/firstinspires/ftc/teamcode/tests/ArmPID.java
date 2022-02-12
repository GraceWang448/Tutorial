package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp(name = "ArmPID")
public class ArmPID extends LinearOpMode {

    DcMotorEx arm;

    double integral = 0;
    double repetitions = 0;

    public static PIDCoefficients armPID = new PIDCoefficients(0.00005, 0.000001, 0.000001);

    ElapsedTime PIDTimer = new ElapsedTime();

    public static double TARGET_POS = 140;

    private FtcDashboard dashboard = FtcDashboard.getInstance();

    public double setPID = 0;

    @Override
    public void runOpMode() {
        arm = hardwareMap.get(DcMotorEx.class, "arm");

        arm.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        telemetry.addLine("Ready!");
        telemetry.update();
        telemetry.clearAll();

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.a) {
                moveArm(TARGET_POS);
            }

            if (gamepad1.b) {
                moveArm(0);
            }

            telemetry.addData("targetPosition", TARGET_POS);
            telemetry.addData("measuredPosition", arm.getCurrentPosition());
            telemetry.addData("error", TARGET_POS - arm.getCurrentPosition());
            telemetry.addData("set", setPID);
            telemetry.addData("repetitions", repetitions);
            telemetry.update();
        }
    }

    void moveArm(double targetPosition) {
        double error = arm.getCurrentPosition();
        double lastError = 0;

        while (Math.abs(error) <= 10 && repetitions < 500) {
            error = arm.getCurrentPosition() - targetPosition;
            double changeInError = lastError - error;

            integral += changeInError * PIDTimer.time();
            double derivative = changeInError / PIDTimer.time();

            double P = armPID.p * error;
            double I = armPID.i * integral;
            double D = armPID.d * derivative;
            setPID = P + I + D;
            arm.setPower(P + I + D);
            error = lastError;
            repetitions++;
            PIDTimer.reset();
        }
    }

}
