package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.TrajectoryAccelerationConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.nextcore.Mechanisms;

import java.util.List;

@Config
@Autonomous(name = "A - BlueClose")
public class BlueClose extends LinearOpMode {

    // RED SPECIFIC DATA
    public static double BLUE_STARTING_X = 12;
    public static double BLUE_STARTING_Y = 67;

    public static double MID_X = 8;
    public static double MID_Y = 71;

    public static double WAREHOUSE_X = 54;
    public static double WAREHOUSE_Y = 69;

    public static double SHIPPING_X = -12;
    public static double SHIPPING_Y = 37;


    public static int AUTON_DELAY = 10000;

    private boolean autonRunning = true;

    @Override
    public void runOpMode() {

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);

        Pose2d startPose = new Pose2d(BLUE_STARTING_X, BLUE_STARTING_Y, Math.toRadians(0));

        drive.setPoseEstimate(startPose);

        Pose2d poseEstimate = drive.getPoseEstimate();

        waitForStart();
        if (isStopRequested()) return;

        if (opModeIsActive()) {
            Trajectory MidToWarehouse = drive.trajectoryBuilder(startPose)
                    .lineToConstantHeading(new Vector2d(WAREHOUSE_X, WAREHOUSE_Y))
                    .addDisplacementMarker(30, () -> {
                        mech.runIntake(Mechanisms.intakeState.IN);
                    })
                    .build();


            Trajectory WarehouseToMid = drive.trajectoryBuilder(MidToWarehouse.end())
                    .addDisplacementMarker(() -> {
                        mech.runIntake(Mechanisms.intakeState.OFF);
                    })
                    .lineToConstantHeading(new Vector2d(MID_X, MID_Y))
                    .build();

            Trajectory MidToShipping = drive.trajectoryBuilder(WarehouseToMid.end())
                    .lineToLinearHeading(new Pose2d(SHIPPING_X, SHIPPING_Y, Math.toRadians(90)))
                    .addDisplacementMarker(() -> {

                    })
                    .build();

            Trajectory ShippingToMid = drive.trajectoryBuilder(MidToShipping.end())
                    .lineToLinearHeading(new Pose2d(MID_X, MID_Y, Math.toRadians(0)))
                    .addDisplacementMarker(() -> {
                        mech.moveArm(Mechanisms.armState.TOP);
                        // add box
                    })
                    .build();

            Trajectory StrafeLeft = drive.trajectoryBuilder(ShippingToMid.end())
                    .strafeLeft(3.0)
                    .build();

            Trajectory MidToWarehouse2 = drive.trajectoryBuilder(ShippingToMid.end())
                    .lineToConstantHeading(new Vector2d(WAREHOUSE_X, WAREHOUSE_Y))
                    .addDisplacementMarker(30, () -> {
                        mech.runIntake(Mechanisms.intakeState.IN);
                    })
                    .build();

            Trajectory ParkInWareHouse = drive.trajectoryBuilder(StrafeLeft.end())
                    .lineToConstantHeading(new Vector2d(WAREHOUSE_X, WAREHOUSE_Y-20))
                    .addDisplacementMarker(30, () -> {
                        mech.runIntake(Mechanisms.intakeState.IN);
                    })
                    .build();


            //Cycle One
            drive.followTrajectory(MidToWarehouse); // from start to warehouse
            mech.wait(2000);
            drive.followTrajectory(WarehouseToMid); // from warehouse to start
            drive.followTrajectory(MidToShipping); // start to ship
            mech.wait(2000);

            //Reset to mid
            drive.followTrajectory(ShippingToMid); // ship to start
            drive.followTrajectory(StrafeLeft); // correction

            //Cycle Two
            drive.followTrajectory(MidToWarehouse2);
            mech.wait(2000);
            drive.followTrajectory(WarehouseToMid);
            drive.followTrajectory(MidToShipping);
            mech.wait(2000);

            // Reset to mid
            drive.followTrajectory(ShippingToMid); // ship to start
            drive.followTrajectory(StrafeLeft); // correction

            // Park
            drive.followTrajectory(ParkInWareHouse);
        }

    }
}