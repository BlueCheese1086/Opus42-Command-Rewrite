package frc.robot.tower;

import com.revrobotics.CANSparkMax.ExternalFollower;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RapperClass.FiftyCent;

public class TowerSubsystem extends SubsystemBase {

    // FRONT OF OPUS IS THE SIDE WITH THE INDEXER
    private static final FiftyCent rearTop = new FiftyCent(41, MotorType.kBrushless, "Back Top");
    private static final FiftyCent frontTop = new FiftyCent(44, MotorType.kBrushless, "Front Top");
    private static final FiftyCent rearBottom = new FiftyCent(42, MotorType.kBrushless, "Back Bottom");
    private static final FiftyCent frontBottom = new FiftyCent(43, MotorType.kBrushless, "Front Bottom");
    
    /**
     * Initializes all tower motors and sensors 
     */
    public TowerSubsystem() {
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
    }

    /**
     * Stops all tower movement
     */
    public void stopTower() {
        rearTop.set(0);
        frontTop.set(0);
        rearBottom.set(0);
        frontBottom.set(0);
    }

    /**
     * Runs bottom to move ball up
     */
    public void runBottom() {
        frontBottom.set(1);
        rearBottom.set(1);
    }

    /**
     * Runs top to move ball up
     */
    public void runTop() {
        frontTop.set(1);
        rearTop.set(1);
    }


    /**
     * Runs a specific section to run
     * 1 - Front Top
     * 2 - Back Top
     * 3 - Front Bottom
     * 4 - Back Bottom
     * @param section Which motor to run
     * @param speed How fast to run the motor [-1, +1]
     */
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

    /**
     * Run the whole tower at a set speed
     * @param speed How fast to run the tower [-1, +1]
     */
    public void setTowerSpeed(double speed) {
        rearTop.set(speed);
        rearBottom.set(speed);
    }

}
