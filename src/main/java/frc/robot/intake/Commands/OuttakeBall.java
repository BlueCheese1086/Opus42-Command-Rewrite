package frc.robot.intake.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSubsystem;

public class OuttakeBall extends CommandBase {

    private final IntakeSubsystem intake;

    public OuttakeBall(IntakeSubsystem intake) {
        this.intake = intake;
        addRequirements(this.intake);
    }

    @Override
    public void execute() {
        intake.setIntakeExtension(true);
        intake.outtake();
        intake.indexerOut();
    }

    public void end(boolean interr) {
        intake.setIntakeExtension(false);
        intake.stopIntake();
        intake.indexerStop();
    }
    
}
