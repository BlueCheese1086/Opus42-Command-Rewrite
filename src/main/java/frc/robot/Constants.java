// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final double LAUNCHER_DEFAULT_VELOCITY = 9.0; //meters/second

    public static final double LAUNCHER_MIN_ANGLE = 80.2141;
    public static final double LAUNCHER_MAX_ANGLE = 90;

    public static final double CAMERA_HEIGHT = 0.762; //meters
    public static final double CAMERA_ANGLE = 45; //degrees
    public static final double LAUNCHER_WHEEL_DIAMETER = 0.1016; //m e t er s??
    public static final double LAUNCHER_WHEEL_CIRCUMFERENCE = 2 * Math.PI * (LAUNCHER_WHEEL_DIAMETER / 2);
    public static final double LAUNCHER_ENCODER_UNITS_PER_ROTATION = 2048.0;
    public static final double LAUNCHER_MAX_VELOCITY = 6380.0 * 2048.0 / 600.0; 

    public static final double UPPER_HUB_HEIGHT = 2.64; //meters
    public static final double GRAVITY = 9.8; //meters/second
    public static final double CARGO_DIAMETER = 0.24;


    public static final double LAUNCHER_KP = 0.2;
    public static final double LAUNCHER_KI = 0.0;
    public static final double LAUNCHER_KD = 2.8;
    public static final double LAUNCHER_KF = 1100 / LAUNCHER_MAX_VELOCITY;

    public static final double MP_DRIVE_FF = 1.0 / 5676.0;
    public static final double MP_DRIVE_KP = 1.0 / 1000.0;
    public static final double MP_DRIVE_KI = 0;
    public static final double MP_DRIVE_KD = 0;

    public static final double DRIVETRAIN_POSITION_SCALE = (8.98) / (CARGO_DIAMETER * Math.PI); // m -> rotation
    public static final double WHEEL_TO_WHEEL_RADIUS = Math.sqrt(Math.pow(74.72, 2) + Math.pow(52.75, 2)); // cm

    public static final double TURN_ERROR = 0.005 * DRIVETRAIN_POSITION_SCALE; // rotations
    public static final double DRIVE_ERROR = 0.003 * DRIVETRAIN_POSITION_SCALE; // rotaions
}
