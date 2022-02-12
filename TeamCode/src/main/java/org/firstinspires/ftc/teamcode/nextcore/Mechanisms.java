package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class Mechanisms {

    public ElapsedTime runtime = new ElapsedTime();

    public DcMotorEx intake;
    public DcMotorEx turret, arm;

    public CRServo leftDuck, rightDuck;

    public Servo box;

    public enum intakeState {
        IN, OUT, OFF
    }

    public enum turretState {
        FRONT, RIGHT, LEFT, BACK
    }

    public enum armState {
     INITIAL,TOP,MIDDLE,BOTTOM
    }

    public enum boxState {
        UP, DOWN
    }

    public enum duckSpinState {
        ON, OFF
    }

    public enum duckSpinSide {
        LEFT, RIGHT
    }

    public static double INTAKE_POWER = 1;
    public static double TURRET_VELOCITY = 900; // ticks per second
    public static double ARM_VELOCITY = 900; // ticks per second
    public static double OFF_POWER = 0;
    public static double SPIN_POWER = 1;

    public static int TURRET_FRONT = 0;
    public static int TURRET_RIGHT = -258;
    public static int TURRET_LEFT = 269; // -806 if you want other way around
    public static int TURRET_BACK = -537;

    public static int ARM_INITIAL = 0;
    public static int ARM_TOP = 614;
    public static int ARM_MIDDLE = 748;
    public static int ARM_BOTTOM = 816;

    public static double BOX_DOWN_POSITION = 0;
    public static double BOX_UP_POSITION = 0;

    public Mechanisms(HardwareMap hardwareMap) {

        intake = hardwareMap.get(DcMotorEx.class, "intake");
        turret = hardwareMap.get(DcMotorEx.class, "turret");
        arm = hardwareMap.get(DcMotorEx.class, "arm");

        leftDuck = hardwareMap.get(CRServo.class, "leftDuck");
        rightDuck = hardwareMap.get(CRServo.class, "rightDuck");

        box = hardwareMap.get(Servo.class, "box");

        turret.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

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


    public void runDuckSpin(duckSpinState state, duckSpinSide side) {
        if (state == duckSpinState.ON) {
            if (side == duckSpinSide.LEFT) {
                spin(state, leftDuck);
            }

            if (side == duckSpinSide.RIGHT) {
                spin(state, rightDuck);
            }
        }

        if (state == duckSpinState.OFF) {
            if (side == duckSpinSide.LEFT) {
                spin(state, leftDuck);
            }

            if (side == duckSpinSide.RIGHT) {
                spin(state, rightDuck);
            }
        }
    }

    private void spin(duckSpinState state, CRServo servo) {
        switch (state) {
            case ON:
                servo.setPower(SPIN_POWER);
                break;
            case OFF:
                servo.setPower(OFF_POWER);
                break;
        }
    }

    public void turnTurret(turretState state) {
        switch (state) {
            case FRONT:
                runTurretPosition(TURRET_FRONT);
                break;
            case RIGHT:
                runTurretPosition(TURRET_RIGHT);
                break;
            case BACK:
                runTurretPosition(TURRET_BACK);
                break;
            case LEFT:
                runTurretPosition(TURRET_LEFT);
                break;
        }
    }

    public void moveArm(armState state) {
        switch (state) {
            case INITIAL:
                runArmPosition(ARM_INITIAL);
                break;
            case TOP:
                runArmPosition(ARM_TOP);
                break;
            case MIDDLE:
                runArmPosition(ARM_MIDDLE);
                break;
            case BOTTOM:
                runArmPosition(ARM_BOTTOM);
                break;
        }
    }

    public void moveBox(boxState state) {
        switch (state) {
            case DOWN:
                box.setPosition(BOX_DOWN_POSITION);
            case UP:
                box.setPosition(BOX_UP_POSITION);
        }
    }

    private void runTurretPosition(int ticks) {
        runToPosition(ticks, turret, TURRET_VELOCITY);
    }

    private void runArmPosition(int ticks) {
        runToPosition(ticks, arm, ARM_VELOCITY);
    }

    private void runToPosition(int ticks, DcMotorEx motor, double velocity) {
        motor.setTargetPosition(ticks);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setVelocity(velocity);
    }

    public void wait(int milliseconds) {
        double currentTime = runtime.milliseconds();
        double waitUntil = currentTime + milliseconds;

        while (runtime.milliseconds() < waitUntil) {
        }
    }
}
