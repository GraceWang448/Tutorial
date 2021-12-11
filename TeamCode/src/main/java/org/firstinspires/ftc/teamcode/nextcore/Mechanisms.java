package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class Mechanisms {
    // Init Objects
    public ElapsedTime runtime = new ElapsedTime();

    // Init Objects: DcMotorEx
    public DcMotorEx intake;
    public DcMotorEx leftSlide, rightSlide;

    // Init Objects: CRServo
    public CRServo duckSpin;

    // Init Objects: Servo
    public Servo objectUnlock;

    // Power Enum
    public enum intakeState {
        IN, OUT, OFF
    }

    public enum objectUnlockState {
        OPEN, CLOSE
    }

    public enum slideState {
        TOP, MIDDLE, BOTTOM
    }

    public enum duckSpinState {
        ON, OFF
    }

    // Power Values
    public static double INTAKE_POWER = 0.7;
    public static double SPIN_POWER = 1;
    public static double SLIDE_VELOCITY = 100; // ticks per second
    private static final double OFF_POWER = 0;

    // Tick Values
    public static int TOP_LEVEL = 1000;
    public static int MIDDLE_LEVEL = 500;
    public static int BOTTOM_LEVEL = 0;

    public static double OBJECT_OPEN = 0.2;
    public static double OBJECT_CLOSE = 0.1;


    // Constructor
    public Mechanisms(HardwareMap hardwareMap) {
        //  Hardware mapping: DcMotorEx
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        leftSlide = hardwareMap.get(DcMotorEx.class, "leftSlide");
        rightSlide = hardwareMap.get(DcMotorEx.class, "rightSlide");

        //  Hardware mapping: CRServo
        duckSpin = hardwareMap.get(CRServo.class, "duckSpin");

        //  Hardware mapping: Servo
        objectUnlock = hardwareMap.get(Servo.class, "objectUnlock");

        // Set Directions
        intake.setDirection(DcMotorEx.Direction.FORWARD);
        rightSlide.setDirection(DcMotorEx.Direction.REVERSE);

        // Set Encoders
        leftSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

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

    // Runs duckSpin
    public void runDuckSpin(duckSpinState state) {
        switch (state) {
            case ON:
                duckSpin.setPower(SPIN_POWER);
                break;
            case OFF:
                duckSpin.setPower(OFF_POWER);
        }
    }

    // Runs unlockObject
    public void unlockControl(objectUnlockState state) {
        switch (state) {
            case OPEN:
                objectUnlock.setPosition(OBJECT_OPEN);
                break;
            case CLOSE:
                objectUnlock.setPosition(OBJECT_CLOSE);
                break;
        }
    }


    // Runs Slides
    public void runSlides(slideState state) {
        switch (state) {
            case TOP:
                runSlidePosition(TOP_LEVEL);
                break;
            case MIDDLE:
                runSlidePosition(MIDDLE_LEVEL);
                break;
            case BOTTOM:
                runSlidePosition(BOTTOM_LEVEL);
        }
    }

    // Sub function for run to position for slides
    private void runSlidePosition(int ticks) {
        leftSlide.setTargetPosition(ticks);
        rightSlide.setTargetPosition(ticks);

        leftSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rightSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);

        leftSlide.setVelocity(SLIDE_VELOCITY);
        rightSlide.setVelocity(SLIDE_VELOCITY);
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
