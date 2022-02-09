package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

@Config
@TeleOp(name = "Duck Test")
public class DuckTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Mechanisms mech = new Mechanisms(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            if (gamepad1.left_bumper) {
                mech.runDuckSpin(Mechanisms.duckSpinState.ON, Mechanisms.duckSpinSide.LEFT);
            } else {
                mech.runDuckSpin(Mechanisms.duckSpinState.OFF, Mechanisms.duckSpinSide.LEFT);
            }

            if (gamepad1.right_bumper) {
                mech.runDuckSpin(Mechanisms.duckSpinState.ON, Mechanisms.duckSpinSide.RIGHT);
            } else {
                mech.runDuckSpin(Mechanisms.duckSpinState.OFF, Mechanisms.duckSpinSide.RIGHT);
            }
        }
    }

}
