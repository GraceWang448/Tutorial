package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

import java.util.List;

@Config
@Autonomous(name = "A - RedFarPark")
public class RedFarPark extends LinearOpMode {

    // RED SPECIFIC DATA
    public static double STARTING_X = -36;
    public static double STARTING_Y = -67;

    public static double PARK_X = -60;
    public static double PARK_Y = -36;

    public static int TEN_SEC_DELAY = 10000;


    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);

        Pose2d startPose = new Pose2d(STARTING_X, STARTING_Y, Math.toRadians(0));

        waitForStart();
        if (isStopRequested()) return;

        if (opModeIsActive()) {

            Trajectory ParkInSquare = drive.trajectoryBuilder(startPose)
                    .lineToConstantHeading(new Vector2d(PARK_X, PARK_Y))
                    .addDisplacementMarker(() -> {
                        //mech.moveArm(Mechanisms.armState.TOP);
                        // add box
                    })
                    .build();


            //Wait 20 Seconds
            //mech.wait(2*TEN_SEC_DELAY);

            //Park in Square
            drive.followTrajectory(ParkInSquare);
        }

    }
}