package frc.robot.intake.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSubsystem;

public class IntakeBall extends CommandBase {

    private final IntakeSubsystem intake;

    public IntakeBall(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(this.intake);
    }

    @Override
    public void execute() {
        intake.setIntakeExtension(true);
        intake.intake();
        intake.indexerIn();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public void end(boolean interr) {
        intake.setIntakeExtension(false);
        intake.stopIntake();
        intake.indexerStop();
    }
    
}
