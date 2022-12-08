package frc.robot.shooter;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {

    private static final TalonFX x = new TalonFX(51);
    private static final TalonFX y = new TalonFX(52);

    private static final Servo hood = new Servo(0);

    /**
     * Sets hood position to 0
     * Sets invertion and PID constants to falcons
     */
    public ShooterSubsystem() {

        hood.set(0);

        x.setInverted(false);
        x.config_kP(0, ShooterConstants.LAUNCHER_KP);
        x.config_kI(0, ShooterConstants.LAUNCHER_KI);
        x.config_kD(0, ShooterConstants.LAUNCHER_KD);
        x.config_kF(0, ShooterConstants.LAUNCHER_KF);
        x.configVoltageCompSaturation(x.getBusVoltage());

        y.setInverted(true);
        y.config_kP(0, ShooterConstants.LAUNCHER_KP);
        y.config_kI(0, ShooterConstants.LAUNCHER_KI);
        y.config_kD(0, ShooterConstants.LAUNCHER_KD);
        y.config_kF(0, ShooterConstants.LAUNCHER_KF);
        y.configVoltageCompSaturation(y.getBusVoltage());

    }


    /**
     * Updates shooter rpm on Shuffleboard
     */
    public void periodic() {
        SmartDashboard.putNumber("Shooter speed", getMotorVelocity());
    }


    /**
     * Sets the falcon rpm
     * @param velo How fast you want the flywheel (rpm)
     */
    public void setMotorVelo(double velo) {
        x.set(TalonFXControlMode.Velocity, velo);
        y.set(TalonFXControlMode.Velocity, velo);
    }

    public double rpmToEncoderShitters(double rpm) {
        return rpm * 2048 / 600;
    }

    /**
     * Gets shooter speed from limelight angle
     * @param distance Limelight angle
     */
    public double getSpeedFromDistance(double distance) {
        return distance*-96.4+7537.7;
    }

    /**
     * Sets the speed of flywheel in percentage of motor ability
     * Reccommended use is to stop motors
     * @param percentage How fast you want the motors to move
     */
    public void setMotorPercentage(double percentage) {
        x.set(TalonFXControlMode.PercentOutput, percentage);
        y.set(TalonFXControlMode.PercentOutput, percentage);
    }


    /**
     * @return RPM of the motor
     */
    public double getMotorVelocity() {
        // Encoder units per 100ms to RPM (2048 encoder units/revolution) (600 is ms to m)
        return x.getSelectedSensorVelocity() * 600 / 2048;
    }

    /**
     * @return Enocder units per 100ms
     */
    public double getRawMotorVelocity() {
        return x.getSelectedSensorVelocity();
    }

    /**
     * Sets the servo hood position
     * @param pos Sets the servo hood position between 0 and 1
     */
    public void setHoodPosition(double pos) {
        hood.set(pos);
    }

    /**
     * Stops shooter motors
     */
    public void stop() {
        this.setMotorPercentage(0);
    }

}
