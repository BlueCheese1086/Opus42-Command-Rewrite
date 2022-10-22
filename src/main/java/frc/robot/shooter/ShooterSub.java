package frc.robot.shooter;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSub extends SubsystemBase {

    private static final TalonFX x = new TalonFX(51);
    private static final TalonFX y = new TalonFX(52);

    private static final Servo hood = new Servo(0);

    public ShooterSub() {

        hood.set(0);

        x.setInverted(false);
        x.config_kP(0, Constants.LAUNCHER_KP);
        x.config_kI(0, Constants.LAUNCHER_KI);
        x.config_kD(0, Constants.LAUNCHER_KD);
        x.config_kF(0, Constants.LAUNCHER_KF);

        y.setInverted(true);
        y.config_kP(0, Constants.LAUNCHER_KP);
        y.config_kI(0, Constants.LAUNCHER_KI);
        y.config_kD(0, Constants.LAUNCHER_KD);
        y.config_kF(0, Constants.LAUNCHER_KF);

    }

    public void periodic() {
        SmartDashboard.putNumber("Shooter speed", getMotorVelocity());
    }

    public void setMotorVelo(double velo) {
        x.set(TalonFXControlMode.Velocity, velo);
        y.set(TalonFXControlMode.Velocity, velo);
    }

    /**
     * Gets shooter speed from limelight angle
     * @param distance Limelight angle
     */
    public double getSpeedFromDistance(double distance) {
        return distance*-96.4+7537.7;
    }

    public void setMotorPercentage(double velo) {
        x.set(TalonFXControlMode.PercentOutput, velo);
        y.set(TalonFXControlMode.PercentOutput, velo);
    }

    public double getMotorVelocity() {
        return x.getSelectedSensorVelocity();
    }

    public void setHoodPosition(double pos) {
        hood.set(pos);
    }

    public void stop() {
        this.setMotorPercentage(0);
    }

}
