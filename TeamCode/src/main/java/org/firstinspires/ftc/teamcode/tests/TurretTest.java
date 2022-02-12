package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@Disabled
@TeleOp
public class TurretTest extends LinearOpMode {

    private DcMotorEx turret;

    public static double tickVelocity = 900;

    public static int frontTicks = 0;
    public static int rightTicks = -258;
    public static int backTicks = -537;
    public static int leftTicks = -806;

    @Override
    public void runOpMode() {
        turret = hardwareMap.get(DcMotorEx.class, "turret");

        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("Turret", turret.getCurrentPosition());
            telemetry.update();

            if(gamepad1.dpad_up) {
                runToPosition(frontTicks,turret,tickVelocity);
            }

            if(gamepad1.dpad_right) {
                runToPosition(rightTicks,turret,tickVelocity);
            }

            if(gamepad1.dpad_down) {
                runToPosition(backTicks,turret,tickVelocity);
            }

            if(gamepad1.dpad_left) {
                runToPosition(leftTicks,turret,tickVelocity);
            }
        }
    }

    private void runToPosition(int ticks, DcMotorEx motor, double velocity) {
        motor.setTargetPosition(ticks);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setVelocity(velocity);
    }
}
