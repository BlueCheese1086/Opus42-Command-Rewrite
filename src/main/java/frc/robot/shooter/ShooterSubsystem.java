package frc.robot.shooter;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;
import frc.robot.util.LookupTable;

public class ShooterSubsystem extends SubsystemBase {

    private static final TalonFX x = new TalonFX(51);
    private static final TalonFX y = new TalonFX(52);

    private static final Servo hood = new Servo(0);

    //Pass points in the format of {Distance, RPM, Hood Angle}
    private static final LookupTable table = new LookupTable(new double[][]{
        {-4.58, 4150, 1},
        {7.26, 3030, .25}
    });

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

        y.setInverted(true);
        y.config_kP(0, ShooterConstants.LAUNCHER_KP);
        y.config_kI(0, ShooterConstants.LAUNCHER_KI);
        y.config_kD(0, ShooterConstants.LAUNCHER_KD);
        y.config_kF(0, ShooterConstants.LAUNCHER_KF);


    }


    /**
     * Updates shooter rpm on Shuffleboard
     */
    public void periodic() {
        SmartDashboard.putNumber("Shooter speed", getMotorVelocity()*600/2048);
    }


    /**
     * Sets the falcon rpm
     * @param velo How fast you want the flywheel (rpm)
     */
    public void setMotorVelo(double velo) {
        velo = velo * 2048.0 / 600.0;
        x.set(TalonFXControlMode.Velocity, velo);
        y.set(TalonFXControlMode.Velocity, velo);
    }

    /**
     * Gets the shooter speed from limelight angle
     * @param distance Limelight angle
     */
    public double getSpeedFromDistance(double distance) {
        return table.calculate(distance)[0];
    }

    /**
     * Gets the hood angle from limelight angle
     * @param distance Limelight angle
     */
    public double getHoodAngleFromDistance(double distance) {
        return table.calculate(distance)[1];
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
        return x.getSelectedSensorVelocity();
    }

    /**
     * @return Current angle of hood
     */
    public double getHoodAngle() {
        return hood.get();
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
