package frc.robot.shooter.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.shooter.Shooter;

public class FeedTower extends CommandBase {
    
    private final Shooter shooter;
    private final double speed;

    public FeedTower(Shooter shooter, double speed) {
        this.shooter = shooter;
        this.speed = speed;
        addRequirements(shooter);
    }

    @Override
    public void execute() {
        shooter.setTowerSpeed(speed);
    }

    public void end() {
        shooter.setTowerSpeed(0);
    }

}
