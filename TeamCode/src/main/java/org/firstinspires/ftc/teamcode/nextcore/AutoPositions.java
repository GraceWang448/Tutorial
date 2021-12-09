package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.dashboard.config.Config;

@Config
public class AutoPositions {

    // FIELD SPECIFIC DATA
    public static double MAX_VALUE = 72;
    public double MIN_VALUE = -MAX_VALUE;

    public static double TILE = 24;
    public static double HALF_TILE = TILE / 2;
    public static double ONE_HALF_TILE = TILE + HALF_TILE;

    public static double ORIGIN = 0;

    // ROBOT SPECIFIC DATA
    public static double ROBOT_LENGTH = 18; // LENGTH OF WHEELS SIDE
    public static double ROBOT_WIDTH = 18; // LENGTH OF INTAKE SIDE

    // Constructor
    public AutoPositions() {
    }

    // GETS X VALUE AGAINST THE WALL
    public double againstWallValueX(double value) {
        if (value < 0) {
            return value + ROBOT_LENGTH / 2;
        } else {
            return value - ROBOT_LENGTH / 2;
        }
    }
}
