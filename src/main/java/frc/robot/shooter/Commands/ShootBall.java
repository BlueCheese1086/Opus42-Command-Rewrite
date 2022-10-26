package frc.robot.shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.tower.TowerSubsystem;

public class ShootBall extends CommandBase {

    private final TowerSubsystem tower;

    public ShootBall(TowerSubsystem in) {
        this.tower = in;
        addRequirements(this.tower);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        tower.runBottom();
        tower.runTop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interr) {
        tower.stopTower();
    }
    
}
