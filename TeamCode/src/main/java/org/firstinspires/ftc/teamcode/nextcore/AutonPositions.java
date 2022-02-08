package org.firstinspires.ftc.teamcode.nextcore;

import com.acmerobotics.dashboard.config.Config;

@Config
public class AutonPositions {

    public static double MAX_VALUE = 72;
    public double MIN_VALUE = -MAX_VALUE;

    public static double TILE = 24;
    public static double HALF_TILE = TILE/24;

    public static double ONE_AND_HALF_TILE = TILE + HALF_TILE;

    public static double ORIGIN = 0;

    public static double ROBOT_LENGTH = 14;
    public static double ROBOT_WIDTH = 12.589;

    public AutonPositions() {}

    public double againstWallValueX(double value) {
        if(value < 0) {
            return value + ROBOT_LENGTH/2;
        } else {
            return value - ROBOT_LENGTH/2;
        }
    }
}
