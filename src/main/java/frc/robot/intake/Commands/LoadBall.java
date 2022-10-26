package frc.robot.intake.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSubsystem;

public class LoadBall extends CommandBase {
    
    /****************************
     * WIP - Waiting on sensors *
     ****************************/

    private final IntakeSubsystem intake;

    public LoadBall(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(this.intake);
    }

    @Override
    public void execute() {

    }
}
