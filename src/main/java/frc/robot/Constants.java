// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static class ShooterConstants {
        public static final double CAMERA_HEIGHT = 0.762; // meters
        public static final double CAMERA_ANGLE = 45; // degrees
        public static final double LAUNCHER_WHEEL_DIAMETER = 0.1016; // m e t er s??
        public static final double LAUNCHER_WHEEL_CIRCUMFERENCE = 2 * Math.PI * (LAUNCHER_WHEEL_DIAMETER / 2);
        public static final double LAUNCHER_ENCODER_UNITS_PER_ROTATION = 2048.0;
        
        // Max motor rpm * Encoder units per rev / minutes to 100 ms
        // Max Encoder units per minute / 100 ms
        public static final double LAUNCHER_MAX_VELOCITY = 6380.0 * 2048.0 / 600.0;

        public static final double TARGET_HEIGHT = Units.feetToMeters(10); // meters
        public static final double GRAVITY = 9.8; // meters/second
        public static final double CARGO_DIAMETER = Units.inchesToMeters(9.5);

        public static final double HEIGHT_DIFFERENCE = TARGET_HEIGHT - CAMERA_HEIGHT;

        public static final double LAUNCHER_KP = 0.205;
        public static final double LAUNCHER_KI = 0.0;
        public static final double LAUNCHER_KD = 2.8;
        public static final double LAUNCHER_KF = 1023 / LAUNCHER_MAX_VELOCITY;
        //public static final double LAUNCHER_KF = 1100 / LAUNCHER_MAX_VELOCITY;
    }

    public static final double DRIVETRAIN_POSITION_SCALE = (8.98) / (ShooterConstants.CARGO_DIAMETER * Math.PI); // m ->
                                                                                                                 // rotation
    public static final double WHEEL_TO_WHEEL_RADIUS = Math.sqrt(Math.pow(74.72, 2) + Math.pow(52.75, 2)); // cm

    public static final double TURN_ERROR = 0.005 * DRIVETRAIN_POSITION_SCALE; // rotations
    public static final double DRIVE_ERROR = 0.003 * DRIVETRAIN_POSITION_SCALE; // rotaions

    public static class DriveConstants {

        public static final double kDriveP = 0.5;
        public static final double kDriveI = 0.0;
        public static final double kDriveD = 0.01;
        public static final double kDriveFF = 0.1;

        public static final double RIGHT_MASTER_ID = 2;
        public static final double RIGHT_SLAVE_ID = 4;
        public static final double LEFT_MASTER_ID = 1;
        public static final double LEFT_SLAVE_ID = 3;

        public static final double Ks = 0.37003;
        public static final double Kv = 1.1603;
        public static final double Ka = 0.40226;

        // m/s
        public static final double MAX_FORWARD_VELOCITY = 6.0;
        // rad/s
        public static final double MAX_TURNING_VELOCITY = 20;

        // Ramsete stuff
        public static final double b = 1.25;
        public static final double zeta = 1;

        public static final double MP_DRIVE_FF = 1.0 / 5676.0;
        public static final double MP_DRIVE_KP = 1.0 / 1000.0;
        public static final double MP_DRIVE_KI = 0;
        public static final double MP_DRIVE_KD = 0;

        // Meters
        public static final double TRACK_WIDTH = 0.762;
        // Meters
        public static final double WHEEL_CIRCUMPHRENCE = Units.inchesToMeters(6 * Math.PI);
        public static final double GEARBOX_RATIO = 8.89;

        public static final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(TRACK_WIDTH);
        public static final DifferentialDriveWheelSpeeds wheelSpeeds = new DifferentialDriveWheelSpeeds(0.0, 0.0);
    }
}
