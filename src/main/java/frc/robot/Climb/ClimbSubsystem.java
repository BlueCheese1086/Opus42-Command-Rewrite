package frc.robot.Climb;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RapperClass.FiftyCent;

public class ClimbSubsystem extends SubsystemBase {

    private final FiftyCent leftClimb;
    private final FiftyCent rightClimb;

    private final RelativeEncoder leftEncoder;
    private final RelativeEncoder rigthEncoder;

    private final Solenoid lock;

    public ClimbSubsystem() {

        lock = new Solenoid(PneumaticsModuleType.CTREPCM, 2);

        leftClimb = new FiftyCent(11, MotorType.kBrushless);
        rightClimb = new FiftyCent(12, MotorType.kBrushless);

        leftEncoder = leftClimb.getEncoder();
        rigthEncoder = rightClimb.getEncoder();

        leftEncoder.setPosition(0);
        rigthEncoder.setPosition(0);

        leftClimb.setIdleMode(IdleMode.kBrake);
        rightClimb.setIdleMode(IdleMode.kBrake);
    }


    public void setLeft(double speed) {
        leftClimb.set(speed);
    }

    public void setRight(double speed) {
        rightClimb.set(speed);
    }

    /**
     * Locks climb solenoid
     */
    public void lock() {
        lock.set(false);
    }

    /**
     * Gets climb solenoid position
     * @return Returns solenoid position
     */
    public boolean getLock() {
        return lock.get();
    }

    /**
     * Unlocks lock solenoid
     */
    public void unlock() {
        lock.set(true);
    }
    
}
