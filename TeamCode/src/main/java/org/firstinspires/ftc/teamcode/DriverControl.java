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
        telemetry.clearAll();
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            telemetry.addData("armv",mech.getArmVelocity());
            telemetry.addData("arm",mech.getArmPosition());
            telemetry.addData("box",mech.getBoxPosition());
            telemetry.update();

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
              nmk
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
//            if(gamepad2.left_bumper) {
//                mech.moveBox(Mechanisms.boxState.UP);
//            }
//            if(gamepad2.right_bumper) {
//                mech.moveBox(Mechanisms.boxState.DOWN);
//            }
            if(gamepad2.b) {
                mech.moveArm(Mechanisms.armState.BOTTOM);
            }

            if(gamepad2.right_stick_button) {
                mech.moveArm(Mechanisms.armState.BOTTOM_APPROACH);
            }

            if(gamepad2.left_bumper) {
                mech.dropBox();
            }

            if(gamepad2.right_bumper) {
                mech.startBox();
            }

            if (mech.getArmVelocity() > 0) {
                if (mech.getArmPosition() < 25) {
                    mech.setBoxPosition(Mechanisms.BOX_INITIAL);
                } else if (mech.getArmPosition() < 100) {
                    mech.setBoxPosition(Mechanisms.BOX_INITIAL-0.175);
                } else if (mech.getArmPosition() < 575 && mech.getArmPosition() > 315) {
                    mech.setBoxPosition(0);
                }
            }

            if (mech.getArmVelocity() < 0) {
                if (mech.getArmPosition() < 315 && mech.getArmPosition() > 100) {
                    mech.setBoxPosition(Mechanisms.BOX_INITIAL - 0.175);
                }
            }

        }
    }
}
