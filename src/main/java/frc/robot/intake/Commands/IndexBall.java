package frc.robot.intake.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSubsystem;

public class IndexBall extends CommandBase {

    private final IntakeSubsystem intake;

    private final boolean in;

    public IndexBall(IntakeSubsystem intake, boolean in) {
        this.intake = intake;
        this.in = in;
        addRequirements(this.intake);
    }

    @Override
    public void execute() {
        if (in) intake.indexerIn();
        else intake.indexerOut();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public void end(boolean interr) {
        intake.indexerStop();
    }
    
}
