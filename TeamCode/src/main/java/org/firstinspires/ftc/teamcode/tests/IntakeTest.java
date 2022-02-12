package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

@Config
@Disabled
@TeleOp(name = "Intake Test")
public class IntakeTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Mechanisms mech = new Mechanisms(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
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
        }
    }

}
