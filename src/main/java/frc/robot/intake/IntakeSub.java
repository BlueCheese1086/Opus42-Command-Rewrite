package frc.robot.intake;

import com.revrobotics.CANSparkMax;
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

        rearTop.setInverted(true);
        rearBottom.setInverted(true);

        frontBottom.follow(rearBottom, true);
        frontTop.follow(rearTop, false);

        rightIndexer.follow(leftIndexer, true);

    }

    // Indexer stuff

    public void indexerIn() {
        rightIndexer.set(-0.8);
    }

    public void indexerOut() {
        rightIndexer.set(0.8);
    }

    public void indexerStop() {
        rightIndexer.set(0);
    }

    // Front intake stuff

    public void intake() {
        frontIntake.set(-0.55);
    }

    public void outtake() {
        frontIntake.set(0.55);
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
        rearBottom.set(0);
    }

    public void runBottom() {
        rearBottom.set(.8);
    }

    public void runTop() {
        rearTop.set(.8);
    }

    public void setTowerSpeed(double speed) {
        rearTop.set(speed);
        rearBottom.set(speed);
    }
    
}
