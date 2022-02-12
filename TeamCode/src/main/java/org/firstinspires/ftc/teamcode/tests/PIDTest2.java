package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp
public class PIDTest2 extends LinearOpMode {

    /**
     * make sure to make sure your the code matches your configuration
     */
    private DcMotorEx arm;

    private FtcDashboard dashboard = FtcDashboard.getInstance();

    public static double position = 140; //arbitrary number; static to allow for analyzing how PID performs through multiple speeds in dashboard

    public static PIDCoefficients pidCoeffs = new PIDCoefficients(0, 0, 0); //PID coefficients that need to be tuned probably through FTC dashboard
    public PIDCoefficients pidGains = new PIDCoefficients(0, 0, 0); //PID gains which we will define later in the process

    ElapsedTime PIDTimer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS); //timer

    @Override
    public void runOpMode() {
        /**
         * basic initialization stuff needs to be changed to suit your configuration (motor name, direction, etc.)
         */
        arm = hardwareMap.get(DcMotorEx.class, "arm");


//            shooterMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        if (opModeIsActive()) {
            while (opModeIsActive()) {

                PIDTest(position); //running the PID algorithm at defined speed

                telemetry.addData("error",position-arm.getCurrentPosition());
                telemetry.addData("target",position);
                telemetry.addData("current",arm.getCurrentPosition());
                telemetry.update();
            }
        }
    }

    double lastError = 0;
    double integral = 0;
    //initializing our variables

    public void PIDTest(double targetPosition) {
        PIDTimer.reset(); //resets the timer

        double currentPosition = arm.getCurrentPosition();
        double error = targetPosition - currentPosition; //pretty self explanatory--just finds the error

        double deltaError = error - lastError; //finds how the error changes from the previous cycle
        double derivative = deltaError / PIDTimer.time(); //deltaError/time gives the rate of change (sensitivity of the system)

        integral += error * PIDTimer.time();
        //continuously sums error accumulation to prevent steady-state error (friction, not enough p-gain to cause change)

        pidGains.p = error * pidCoeffs.p;
        //acts directly on the error; p-coefficient identifies how much to act upon it
        // p-coefficient (very low = not much effect; very high = lots of overshoot/oscillations)
        pidGains.i = integral * pidCoeffs.i;
        //multiplies integrated error by i-coefficient constant
        // i-coefficient (very high = fast reaction to steady-state error but lots of overshoot; very low = slow reaction to steady-state error)
        // for velocity, because friction isn't a big issue, only reason why you would need i would be for insufficient correction from p-gain
        pidGains.d = derivative * pidCoeffs.d;
        //multiplies derivative by d-coefficient
        // d-coefficient (very high = increased volatility; very low = too little effect on dampening system)

        arm.setPower(pidGains.p + pidGains.i + pidGains.d);
        //adds up the P I D gains with the targetVelocity bias

        lastError = error;
        //makes our current error as our new last error for the next cycle
    }
}
