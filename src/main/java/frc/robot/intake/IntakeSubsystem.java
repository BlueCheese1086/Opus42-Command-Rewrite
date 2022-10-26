package frc.robot.intake;

import com.revrobotics.CANSparkMax.ExternalFollower;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RapperClass.FiftyCent;

public class IntakeSubsystem extends SubsystemBase {

    private static final FiftyCent frontIntake = new FiftyCent(31, MotorType.kBrushless);

    private static final Solenoid intakeExtender = new Solenoid(PneumaticsModuleType.CTREPCM, 3);


    /* TODO
    * Move all tower control to tower subsystem
    */

    private static final FiftyCent leftIndexer = new FiftyCent(21, MotorType.kBrushless);
    private static final FiftyCent rightIndexer = new FiftyCent(22, MotorType.kBrushless);


    public IntakeSubsystem() {

        // Indexer following
        rightIndexer.follow(leftIndexer, true);
        leftIndexer.follow(ExternalFollower.kFollowerDisabled, 0);

        intakeExtender.set(false);

    }

    // Indexer stuff

    /**
     * Sets indexer speed to take in balls
     */
    public void indexerIn() {
        leftIndexer.set(-0.8);
    }

    /**
     * Sets indexer speed to spit out balls
     */
    public void indexerOut() {
        leftIndexer.set(0.8);
    }

    /**
     * Stops the indexer
     */
    public void indexerStop() {
        leftIndexer.set(0);
    }


    // Front intake stuff
    /**
     * Sets intake speed to take in balls
     */
    public void intake() {
        frontIntake.set(-1);
    }

    /**
     * Stops the intake
     */
    public void stopIntake() {
        frontIntake.set(0);
    }

    /**
     * Sets intake speed to spit out balls
     */
    public void outtake() {
        frontIntake.set(1);
    }

    /**
     * Toggles current state of intake solenoid
     */
    public void toggleIntakeExtension() {
        intakeExtender.set(!intakeExtender.get());
    }

    /**
     * Manually set the position on the intake
     * @param on true - Intake extended; false - Intake retracted
     */
    public void setIntakeExtension(boolean on) {
        intakeExtender.set(on);
    }
    
}
