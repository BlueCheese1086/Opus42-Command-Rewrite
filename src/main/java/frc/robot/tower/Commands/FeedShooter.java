package frc.robot.tower.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.tower.TowerSubsystem;

public class FeedShooter extends CommandBase {

    private final TowerSubsystem tower;

    public FeedShooter(TowerSubsystem tower) {
        this.tower = tower;
    }

}
