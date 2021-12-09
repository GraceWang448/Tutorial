package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

@Config
@Autonomous(name = "BLUE Nacho Cheese - 12/11/21")
public class NachoCheeseBlue extends LinearOpMode {

    // BLUE SPECIFIC DATA
    public static double BLUE_STARTING_X = 0;  // STARTING X FOR AUTON
    public static double BLUE_STARTING_Y = 0;  // STARTING Y FOR AUTON

    public static double BLUE_ENDING_X = 0; // STARTING X FOR TELEOP + ENDING X FOR AUTON
    public static double BLUE_ENDING_Y = 0; // STARTING Y FOR TELEOP + ENDING Y FOR AUTON

    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);

        Pose2d startPose = new Pose2d(BLUE_STARTING_X, BLUE_ENDING_Y, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        waitForStart();
        if (isStopRequested()) return;

        // build trajectories here
    }
}
