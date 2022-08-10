package frc.robot.drive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {

    // Initializing SPARKY MAXES HOW ELECTRIFYING
    private static final CANSparkMax rightMaster = new CANSparkMax(1, MotorType.kBrushless);
    private static final CANSparkMax rightSlave = new CANSparkMax(1, MotorType.kBrushless);
    private static final CANSparkMax leftMaster = new CANSparkMax(1, MotorType.kBrushless);
    private static final CANSparkMax leftSlave = new CANSparkMax(1, MotorType.kBrushless);

    private static final RelativeEncoder rightEncoder = rightMaster.getEncoder();
    private static final RelativeEncoder leftEncoder = leftMaster.getEncoder();

    public Drivetrain() {

        // West Coast Moment
        rightSlave.follow(rightMaster);
        leftSlave.follow(leftMaster);

        // Resetting encoders
        rightEncoder.setPosition(0);
        leftEncoder.setPosition(0);

    }

    // Gets left encoder
    public RelativeEncoder getRightEncoder() {
        return rightEncoder;
    }

    // Gets right encoder
    public RelativeEncoder getLeftEncoder() {
        return leftEncoder;
    }

    /**
     * Arcade style drive
     * @param forward Forward axis position
     * @param turn Turn axis position
     */
    public void arcadeDrive(double forward, double turn) {
        rightMaster.set(forward + turn);
        leftMaster.set(forward - turn);
    }

    /**
     * Statically set motor speeds
     * @param left Left motor speed
     * @param right Right motor speed
     */
    public void staticDrive(double left, double right) {
        rightMaster.set(right);
        leftMaster.set(left);
    }
    
}
