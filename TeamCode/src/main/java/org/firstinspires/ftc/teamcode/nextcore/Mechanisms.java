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
        DOWN, UP, RESET, BACK
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

    public static double INTAKE_POWER = 0.7;
    public static double TURRET_VELOCITY = 0.7; // ticks per second
    public static double ARM_VELOCITY = 0.7; // ticks per second
    public static double OFF_POWER = 0;
    public static double SPIN_POWER = 1;

    public static int TURRET_FRONT = 0;
    public static int TURRET_RIGHT = 500;
    public static int TURRET_LEFT = 1500;
    public static int TURRET_BACK = 1000;

    public static int ARM_DOWN = 0;
    public static int ARM_UP = 500;

    public static double BOX_DOWN_POSITION = 0;
    public static double BOX_UP_POSITION = 0;

    public Mechanisms(HardwareMap hardwareMap) {

        intake = hardwareMap.get(DcMotorEx.class, "intake");
        turret = hardwareMap.get(DcMotorEx.class, "turret");
        arm = hardwareMap.get(DcMotorEx.class, "arm");

        leftDuck = hardwareMap.get(CRServo.class, "leftDuck");
        rightDuck = hardwareMap.get(CRServo.class, "rightDuck");

        box = hardwareMap.get(Servo.class, "box");

        turret.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runIntake(intakeState state) {
        switch (state) {
            case IN:
                intake.setPower(INTAKE_POWER);
            case OUT:
                intake.setPower(-INTAKE_POWER);
            case OFF:
                intake.setPower(OFF_POWER);
        }
    }

    public void runDuckSpin(duckSpinState state, duckSpinSide side) {
       switch(side) {
           case LEFT: switch (state) {
               case ON:
                   leftDuck.setPower(SPIN_POWER);
               case OFF:
                   leftDuck.setPower(OFF_POWER);
           }
           case RIGHT: switch (state) {
               case ON:
                   rightDuck.setPower(SPIN_POWER);
               case OFF:
                   rightDuck.setPower(OFF_POWER);
           }
       }
    }

    public void turnTurret(turretState state) {
        switch (state) {
            case FRONT:
                runTurretPosition(TURRET_FRONT);
            case RIGHT:
                runTurretPosition(TURRET_RIGHT);
            case BACK:
                runTurretPosition(TURRET_BACK);
            case LEFT:
                runTurretPosition(TURRET_LEFT);
        }
    }

    public void moveArm(armState state) {
        switch (state) {
            case DOWN:
            case RESET:
                runArmPosition(ARM_DOWN);
            case UP:
            case BACK:
                runArmPosition(ARM_UP);
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
//        turret.setTargetPosition(ticks);
//        turret.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        turret.setVelocity(TURRET_VELOCITY);
        runToPosition(ticks,turret,TURRET_VELOCITY);
    }

    private void runArmPosition(int ticks) {
        runToPosition(ticks,arm,ARM_VELOCITY);
    }

    private void runToPosition(int ticks, DcMotorEx motor, double velocity) {
        motor.setTargetPosition(ticks);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setVelocity(velocity);
    }

    public void wait(int milliseconds) {
        double currentTime = runtime.milliseconds();
        double waitUntil = currentTime + milliseconds;

        while(runtime.milliseconds() < waitUntil) {}
    }
}
