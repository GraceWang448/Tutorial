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
@Autonomous(name = "R-Ware B-Other")
public class RedWarehousePark extends LinearOpMode {

    double STARTING_X, STARTING_Y = 0;
    Pose2d startPose = new Pose2d(STARTING_X, STARTING_Y, Math.toRadians(0));

    double FOWARD_POWER = 1;
    double STRAFE_POWER = 0.5;

    @Override
    public void runOpMode() {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Mechanisms mech = new Mechanisms(hardwareMap);
        waitForStart();

        //strafeLeft 3 sec

        drive.rightFront.setPower(STRAFE_POWER);
        drive.leftFront.setPower(-STRAFE_POWER);
        drive.rightRear.setPower(-STRAFE_POWER);
        drive.leftRear.setPower(STRAFE_POWER);
        mech.wait(500);
        drive.rightFront.setPower(0);
        drive.leftFront.setPower(0);
        drive.rightRear.setPower(0);
        drive.leftRear.setPower(0);


        mech.wait(3000);

        //drive foward 2 sec
        drive.rightFront.setPower(FOWARD_POWER);
        drive.leftFront.setPower(FOWARD_POWER);
        drive.rightRear.setPower(FOWARD_POWER);
        drive.rightRear.setPower(FOWARD_POWER);
        mech.wait(3000);
        drive.rightFront.setPower(0);
        drive.leftFront.setPower(0);
        drive.rightRear.setPower(0);
        drive.leftRear.setPower(0);


    }
}
