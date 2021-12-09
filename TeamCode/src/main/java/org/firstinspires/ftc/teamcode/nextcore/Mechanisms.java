package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class Mechanisms {
    // Init Objects
    public ElapsedTime runtime = new ElapsedTime();

    // Init Objects: DcMotorEx
    public DcMotorEx intake;


    // Power Enum
    public enum intakeState {
        IN, OUT, OFF
    }

    // Power Values
    public static double INTAKE_POWER = 0.9;

    private static double OFF_POWER = 0;

    // Constructor
    public Mechanisms(HardwareMap hardwareMap) {
        //  Hardware mapping: DcMotorEx
        intake = hardwareMap.get(DcMotorEx.class, "intake");

        // Hardware mapping: CRServo

        //  Hardware mapping: Servo

        // Set Directions
        intake.setDirection(DcMotorEx.Direction.FORWARD);
    }

    // Runs intake dependent on given state
    public void runIntake(intakeState state) {
        switch (state) {
            case IN:
                intake.setPower(INTAKE_POWER);
                break;
            case OUT:
                intake.setPower(-INTAKE_POWER);
                break;
            case OFF:
                intake.setPower(OFF_POWER);
        }
    }

    // Wait function that doesn't interrupt program runtime, uses elapsed time
    public void wait(int milliseconds) {
        double currTime = runtime.milliseconds();
        double waitUntil = currTime + milliseconds;
        while (runtime.milliseconds() < waitUntil) {
            // remain empty
        }
    }

}
