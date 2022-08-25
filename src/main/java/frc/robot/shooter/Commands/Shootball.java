package frc.robot.shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSub;

public class Shootball extends CommandBase {

    private final IntakeSub intake;

    public Shootball(IntakeSub intake) {
        this.intake = intake;
        addRequirements(this.intake);
    }

    @Override
    public void execute() {
        //intake.indexerIn();
        intake.runTop();
        intake.runBottom();
    }

    public boolean isFinished() {
        return false;
    }

    public void end(boolean interrupted) {
        intake.stopTower();
        intake.indexerStop();
    }
    
}
