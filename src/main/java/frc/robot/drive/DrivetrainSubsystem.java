package frc.robot.drive;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.ExternalFollower;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DrivetrainSubsystem extends SubsystemBase {

    // Initializing SPARKY MAXES HOW ELECTRIFYING
    private static final CANSparkMax rightMaster = new CANSparkMax(2, MotorType.kBrushless);
    private static final CANSparkMax rightSlave = new CANSparkMax(4, MotorType.kBrushless);
    private static final CANSparkMax leftMaster = new CANSparkMax(1, MotorType.kBrushless);
    private static final CANSparkMax leftSlave = new CANSparkMax(3, MotorType.kBrushless);

    private static final RelativeEncoder rightEncoder = rightMaster.getEncoder();
    private static final RelativeEncoder leftEncoder = leftMaster.getEncoder();

    public DrivetrainSubsystem() {

        // West Coast Moment
        rightMaster.follow(ExternalFollower.kFollowerDisabled, 0);
        leftMaster.follow(ExternalFollower.kFollowerDisabled, 0);
        rightSlave.follow(rightMaster);
        leftSlave.follow(leftMaster);

        rightMaster.setIdleMode(IdleMode.kBrake);
        leftMaster.setIdleMode(IdleMode.kBrake);

        // Demos
        leftMaster.setOpenLoopRampRate(0);
        rightMaster.setOpenLoopRampRate(0);

        // Inverting left side
        leftMaster.setInverted(true);
        rightMaster.setInverted(false);

        // Resetting encoders
        rightEncoder.setPosition(0);
        leftEncoder.setPosition(0);

    }

    public void periodic() {
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getPosition());
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getPosition());
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
        this.set(forward + turn, forward - turn);
    }

    /**
     * Statically set motor speeds
     * @param left Left motor speed
     * @param right Right motor speed
     */
    public void set(double left, double right) {
        leftMaster.set(left);
        rightMaster.set(right);
    }
    
}
