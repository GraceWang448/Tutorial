package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

@Config
@TeleOp(name = "Driver Control")
public class DriverControl extends LinearOpMode {

    public static double DRIVE_WEIGHT = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);


        telemetry.addLine("Ready to start");
        telemetry.update();
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            // Gamepad 1 - Drivetrain
            drive.setWeightedDrivePower(
                    new Pose2d(
                            Range.clip(-gamepad1.left_stick_y, -DRIVE_WEIGHT, DRIVE_WEIGHT),
                            Range.clip(-gamepad1.left_stick_x, -DRIVE_WEIGHT, DRIVE_WEIGHT),
                            Range.clip(-gamepad1.right_stick_x, -DRIVE_WEIGHT, DRIVE_WEIGHT)
                    )
            );

            if (gamepad1.a) {
                mech.runIntake(Mechanisms.intakeState.IN);
            } else {
                mech.runIntake(Mechanisms.intakeState.OFF);
            }
            if (gamepad1.b) {
                mech.runIntake(Mechanisms.intakeState.OUT);
            } else {
                mech.runIntake(Mechanisms.intakeState.OFF);
            }

            if (gamepad1.left_bumper) {
                mech.runDuckSpin(Mechanisms.duckSpinState.ON, Mechanisms.duckSpinSide.LEFT);
            }  else {
                mech.runDuckSpin(Mechanisms.duckSpinState.OFF,Mechanisms.duckSpinSide.LEFT);
            }

            if (gamepad1.right_bumper) {
                mech.runDuckSpin(Mechanisms.duckSpinState.ON, Mechanisms.duckSpinSide.RIGHT);
            } else {
                mech.runDuckSpin(Mechanisms.duckSpinState.OFF,Mechanisms.duckSpinSide.RIGHT);
            }


            // Gamepad 2 - Mechanisms
            if (gamepad2.dpad_up) {
                mech.turnTurret(Mechanisms.turretState.FRONT);
            }
            if (gamepad2.dpad_right) {
                mech.turnTurret(Mechanisms.turretState.RIGHT);
            }
            if (gamepad2.dpad_down) {
                mech.turnTurret(Mechanisms.turretState.BACK);
            }
            if (gamepad2.dpad_left) {
                mech.turnTurret(Mechanisms.turretState.LEFT);
            }

            if (gamepad2.a) {
                mech.moveArm(Mechanisms.armState.INITIAL);
            }

            if(gamepad2.y) {
                mech.moveArm(Mechanisms.armState.TOP);
            }
            if(gamepad2.x) {
                mech.moveArm(Mechanisms.armState.MIDDLE);
            }

            if(gamepad2.b) {
                mech.moveArm(Mechanisms.armState.BOTTOM);
            }

        }
    }
}
