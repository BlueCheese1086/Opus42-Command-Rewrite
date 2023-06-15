package frc.robot.tower.Commands;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.tower.TowerSubsystem;

public class LoadBall extends CommandBase {

    private final IntakeSubsystem intake;
    private final TowerSubsystem tower;

    private final DigitalInput in = new DigitalInput(0);

    public LoadBall(IntakeSubsystem intake, TowerSubsystem tower) {
        this.intake = intake;
        this.tower = tower;
        addRequirements(this.intake, this.tower);
    }

    
    
}
