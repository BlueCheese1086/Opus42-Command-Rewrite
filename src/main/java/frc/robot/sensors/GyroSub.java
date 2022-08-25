package frc.robot.sensors;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class GyroSub extends SubsystemBase {
    public static final AHRS gyro = new AHRS();

    public GyroSub() {
        gyro.calibrate();
    }

}
