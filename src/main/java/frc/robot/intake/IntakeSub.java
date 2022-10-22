package frc.robot.intake;

import com.revrobotics.CANSparkMax.ExternalFollower;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RapperClass.FiftyCent;

public class IntakeSub extends SubsystemBase {

    private static final FiftyCent frontIntake = new FiftyCent(31, MotorType.kBrushless);

    private static final Solenoid intakeExtender = new Solenoid(PneumaticsModuleType.CTREPCM, 3);

    private static final FiftyCent rearTop = new FiftyCent(41, MotorType.kBrushless, "Back Top");
    private static final FiftyCent frontTop = new FiftyCent(44, MotorType.kBrushless, "Front Top");
    private static final FiftyCent rearBottom = new FiftyCent(42, MotorType.kBrushless, "Back Bottom");
    private static final FiftyCent frontBottom = new FiftyCent(43, MotorType.kBrushless, "Front Bottom");

    private static final FiftyCent leftIndexer = new FiftyCent(21, MotorType.kBrushless);
    private static final FiftyCent rightIndexer = new FiftyCent(22, MotorType.kBrushless);


    public IntakeSub() {

        // Inverting
        rearTop.setInverted(true);
        frontTop.setInverted(true);
        rearBottom.setInverted(false);
        frontBottom.setInverted(false);

        // Tower Brake Mode
        rearTop.setIdleMode(IdleMode.kCoast);
        frontTop.setIdleMode(IdleMode.kCoast);
        rearBottom.setIdleMode(IdleMode.kCoast);
        frontBottom.setIdleMode(IdleMode.kCoast);

        // Tower Following
        frontBottom.follow(ExternalFollower.kFollowerDisabled, 0);
        frontTop.follow(ExternalFollower.kFollowerDisabled, 0);

        // Tower Ramp up rates
        rearTop.setOpenLoopRampRate(0);
        rearBottom.setOpenLoopRampRate(0);

        // Set Curremt Limits so Neo doesnt smell bad again
        // Prevents overheating
        rearTop.setSmartCurrentLimit(30);
        frontTop.setSmartCurrentLimit(30);
        rearBottom.setSmartCurrentLimit(45);
        frontBottom.setSmartCurrentLimit(45);

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
