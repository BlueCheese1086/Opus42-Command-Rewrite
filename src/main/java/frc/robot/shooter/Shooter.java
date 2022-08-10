package frc.robot.shooter;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {

    private static final TalonFX x = new TalonFX(69);
    private static final TalonFX y = new TalonFX(70);

    private static final CANSparkMax towerOne = new CANSparkMax(41, MotorType.kBrushless);
    private static final CANSparkMax towerTwo = new CANSparkMax(42, MotorType.kBrushless);
    private static final CANSparkMax towerThree = new CANSparkMax(43, MotorType.kBrushless);
    private static final CANSparkMax towerFour = new CANSparkMax(44, MotorType.kBrushless);

    private static final Servo hood = new Servo(0);

    public Shooter() {

        towerOne.setInverted(true);
        towerTwo.follow(towerOne, true);
        towerThree.follow(towerOne, true);
        towerFour.follow(towerOne, false);

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

    public void setMotorVelo(double velo) {
        x.set(TalonFXControlMode.Velocity, velo);
        y.set(TalonFXControlMode.Velocity, velo);
    }

    public void setMotorPercentage(double velo) {
        x.set(TalonFXControlMode.PercentOutput, velo);
        y.set(TalonFXControlMode.PercentOutput, velo);
    }

    public double getMotorVelocity() {
        return x.getSelectedSensorVelocity();
    }
    
    public void stop() {
        setMotorPercentage(0);
        towerOne.set(0);
    }

    public void setHoodPosition(double pos) {
        hood.set(pos);
    }

    public void setTowerSpeed(double speed) {
        towerOne.set(speed);
    }

}
