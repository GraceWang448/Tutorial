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
@Autonomous(name = "A - BlueClosePark")
public class BlueClosePark extends LinearOpMode {

    // RED SPECIFIC DATA
    public static double STARTING_X = 12;
    public static double STARTING_Y = 67;

    public static double WAREHOUSE_X = 40;
    public static double WAREHOUSE_Y = 67;

    public static double PARK_X = 54;
    public static double PARK_Y = 42;

    public static int TEN_SEC_DELAY = 10000;


    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);

        Pose2d startPose = new Pose2d(STARTING_X, STARTING_Y, Math.toRadians(0));

        waitForStart();
        if (isStopRequested()) return;

        if (opModeIsActive()) {

            Trajectory StartToWarehouse = drive.trajectoryBuilder(startPose)
                    .lineToConstantHeading(new Vector2d(WAREHOUSE_X, WAREHOUSE_Y))
                    .addDisplacementMarker(() -> {
                        //mech.moveArm(Mechanisms.armState.TOP);
                        // add box
                    })
                    .build();

            Trajectory WarehouseToPark = drive.trajectoryBuilder(StartToWarehouse.end())
                    .lineToConstantHeading(new Vector2d(PARK_X, PARK_Y))
                    .addDisplacementMarker(() -> {
                        //mech.moveArm(Mechanisms.armState.TOP);
                        // add box
                    })
                    .build();


            //Park in Warehouse
            drive.followTrajectory(StartToWarehouse);
            mech.wait(2000);
            drive.followTrajectory(WarehouseToPark);
        }

    }
}