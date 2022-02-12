package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name="BoxTest")
public class BoxTest extends LinearOpMode {

    public Servo box;

    public static double BOX_DOWN = 0;
    public static double BOX_UP = 0;
    public static double BOX_INITIAL = 0;

    @Override
    public void runOpMode() {

        box = hardwareMap.get(Servo.class, "box");

        box.setDirection(Servo.Direction.FORWARD);

        box.setPosition(BOX_INITIAL);

        waitForStart();
        while(opModeIsActive()) {

            if(gamepad1.a) {
                box.setPosition(BOX_INITIAL);
            }

            if(gamepad1.left_bumper) {
                box.setPosition(BOX_UP);
            }

            if(gamepad1.right_bumper) {
                box.setPosition(BOX_DOWN);
            }

            telemetry.addData("box",box.getPosition());
            telemetry.update();
        }
    }
}
