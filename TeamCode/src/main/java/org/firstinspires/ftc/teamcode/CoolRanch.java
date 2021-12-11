package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

@Config
@TeleOp(name = "Cool Ranch - 12/11/21")
public class CoolRanch extends LinearOpMode {

    private DcMotorEx leftFront, leftRear, rightRear, rightFront;

    public static double DRIVE_WEIGHT = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
//        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftRear = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightRear = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");


        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
        rightFront.setDirection(DcMotorSimple.Direction.FORWARD);
        rightRear.setDirection(DcMotorSimple.Direction.FORWARD);

        Mechanisms mech = new Mechanisms(hardwareMap);

        leftFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addLine("Ready to start");
        telemetry.update();
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {

            double forward = gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double turn = gamepad1.right_stick_x;

            leftFront.setPower(Range.clip(forward + turn + strafe, -DRIVE_WEIGHT, DRIVE_WEIGHT));
            rightFront.setPower(Range.clip(forward - turn - strafe, -DRIVE_WEIGHT, DRIVE_WEIGHT));
            leftRear.setPower(Range.clip(forward + turn - strafe, -DRIVE_WEIGHT, DRIVE_WEIGHT));
            rightRear.setPower(Range.clip(forward - turn + strafe, -DRIVE_WEIGHT, DRIVE_WEIGHT));


            // Gamepad 2 - Atharv
            // Intake
            if (gamepad2.left_trigger > 0.5) {
                mech.runIntake(Mechanisms.intakeState.IN);
            } else {
                mech.runIntake(Mechanisms.intakeState.OFF);
            }
            if (gamepad2.right_trigger > 0.5) {
                mech.runIntake(Mechanisms.intakeState.OUT);
            } else {
                mech.runIntake(Mechanisms.intakeState.OFF);
            }

            // Unlock Object
            if (gamepad2.left_bumper) mech.unlockControl(Mechanisms.objectUnlockState.OPEN);
            if (gamepad2.right_bumper) mech.unlockControl(Mechanisms.objectUnlockState.CLOSE);

            // Slides
            if (gamepad2.dpad_up) mech.runSlides(Mechanisms.slideState.TOP);
            if (gamepad2.dpad_left || gamepad2.dpad_right)
                mech.runSlides(Mechanisms.slideState.MIDDLE);
            if (gamepad2.dpad_down) mech.runSlides(Mechanisms.slideState.BOTTOM);

            // Ducks
            if (gamepad2.a) {
                mech.runDuckSpin(Mechanisms.duckSpinState.ON);
            } else {
                mech.runDuckSpin(Mechanisms.duckSpinState.OFF);
            }
        }
    }
}
