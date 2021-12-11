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
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {
            drive.update();
            // Gamepad 1 - Michael
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );


            // Gamepad 2 - Atharv
            // Intake
            if (gamepad2.left_trigger > 0) mech.runIntake(Mechanisms.intakeState.IN);
            if (gamepad2.right_trigger > 0) mech.runIntake(Mechanisms.intakeState.OUT);

            // Unlock Object
            if(gamepad2.left_bumper) mech.unlockControl(Mechanisms.objectUnlockState.OPEN);
            if(gamepad2.right_bumper) mech.unlockControl(Mechanisms.objectUnlockState.CLOSE);

            // Slides
            if (gamepad2.dpad_up) mech.runSlides(Mechanisms.slideState.TOP);
            if (gamepad2.dpad_left || gamepad2.dpad_right) mech.runSlides(Mechanisms.slideState.MIDDLE);
            if (gamepad2.dpad_down) mech.runSlides(Mechanisms.slideState.BOTTOM);

            // Ducks
            if(gamepad2.a) mech.runDuckSpin(Mechanisms.duckSpinState.ON);
        }
    }
}
