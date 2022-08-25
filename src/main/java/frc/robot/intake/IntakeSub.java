package frc.robot.intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ExternalFollower;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSub extends SubsystemBase {

    private static final CANSparkMax frontIntake = new CANSparkMax(31, MotorType.kBrushless);

    private static final Solenoid intakeExtender = new Solenoid(PneumaticsModuleType.CTREPCM, 3);

    private static final CANSparkMax rearTop = new CANSparkMax(41, MotorType.kBrushless);
    private static final CANSparkMax rearBottom = new CANSparkMax(42, MotorType.kBrushless);
    private static final CANSparkMax frontBottom = new CANSparkMax(43, MotorType.kBrushless);
    private static final CANSparkMax frontTop = new CANSparkMax(44, MotorType.kBrushless);

    private static final CANSparkMax leftIndexer = new CANSparkMax(21, MotorType.kBrushless);
    private static final CANSparkMax rightIndexer = new CANSparkMax(22, MotorType.kBrushless);

    public IntakeSub() {

        // Inverting
        rearTop.setInverted(true);
        frontTop.setInverted(false);
        rearBottom.setInverted(false);
        frontBottom.setInverted(false);

        // Tower Brake Mode
        rearTop.setIdleMode(IdleMode.kBrake);
        frontTop.setIdleMode(IdleMode.kBrake);
        rearBottom.setIdleMode(IdleMode.kBrake);
        frontBottom.setIdleMode(IdleMode.kBrake);

        // Tower Following
        frontBottom.follow(ExternalFollower.kFollowerDisabled, 0);
        frontTop.follow(ExternalFollower.kFollowerDisabled, 0);

        // Tower Ramp up rates
        rearTop.setOpenLoopRampRate(0);
        rearBottom.setOpenLoopRampRate(0);

        // Indexer following
        rightIndexer.follow(leftIndexer, true);
        leftIndexer.follow(ExternalFollower.kFollowerDisabled, 0);

        intakeExtender.set(false);

    }

    // Indexer stuff

    public void indexerIn() {
        leftIndexer.set(-0.8);
    }

    public void indexerOut() {
        leftIndexer.set(0.8);
    }

    public void indexerStop() {
        leftIndexer.set(0);
    }

    // Front intake stuff

    public void intake() {
        frontIntake.set(-1);
    }

    public void outtake() {
        frontIntake.set(1);
    }

    public void toggleIntakeExtension() {
        intakeExtender.set(!intakeExtender.get());
    }

    public void setIntakeExtension(boolean on) {
        intakeExtender.set(on);
    }

    public void stopIntake() {
        frontIntake.set(0);
    }

    // Tower shit now

    public void stopTower() {
        rearTop.set(0);
        frontTop.set(0);
        rearBottom.set(0);
        frontBottom.set(0);
    }

    public void runBottom() {
        frontBottom.set(1);
        rearBottom.set(1);
    }

    public void runTop() {
        frontTop.set(1);
        rearTop.set(1);
    }

    public void runSection(int section, double speed) {
        switch (section) {
            case 1:
                frontTop.set(speed);
                break;
            case 2:
                rearTop.set(speed);
                break;
            case 3:
                frontBottom.set(speed);
                break;
            case 4:
                rearBottom.set(speed);
                break;
        }
    }

    public void setTowerSpeed(double speed) {
        rearTop.set(speed);
        rearBottom.set(speed);
    }
    
}
