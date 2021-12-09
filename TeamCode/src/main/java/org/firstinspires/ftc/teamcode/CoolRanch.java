package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

@Config
@TeleOp(name = "Cool Ranch - 12/11/21")
public class CoolRanch extends LinearOpMode {
    // FIELD SPECIFIC DATA
    public static double MAX_VALUE = 72;

    public static double TILE = 24;
    public static double HALF_TILE = TILE / 2;


    enum Mode {
        DRIVER_CONTROL,
        AUTOMATIC_CONTROL
    }

    Mode currentMode = Mode.DRIVER_CONTROL;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);

        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        Pose2d startPose = new Pose2d(RED_ENDING_X, RED_ENDING_Y, Math.toRadians(0));
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            drive.update();
//
//            // Read pose
//            Pose2d poseEstimate = drive.getPoseEstimate();
//
//            // Print pose to telemetry
//            telemetry.addData("mode", currentMode);
//            telemetry.addData("x", poseEstimate.getX());
//            telemetry.addData("y", poseEstimate.getY());
//            telemetry.addData("heading", poseEstimate.getHeading());
//            telemetry.update();


            // Gamepad 1 -
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            // Gamepad 2 - Michael
        }
    }
}
