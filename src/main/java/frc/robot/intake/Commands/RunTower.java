package frc.robot.intake.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.tower.TowerSubsystem;

public class RunTower extends CommandBase {

    private final TowerSubsystem tower;

    private final int motor;
    private final double speed;

    public RunTower(TowerSubsystem tower, int motorid, double speed) {
        this.tower = tower;
        motor = motorid;
        this.speed = speed;
        addRequirements(tower);
    }

    @Override
    public void execute() {
        tower.runSection(motor, speed);
    }

    public void end(boolean interr) {
        tower.runSection(motor, 0);
    }
    
}
