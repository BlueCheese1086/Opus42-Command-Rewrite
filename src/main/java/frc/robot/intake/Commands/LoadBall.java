package frc.robot.intake.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSub;

public class LoadBall extends CommandBase {
    
    /****************************
     * WIP - Waiting on sensors *
     ****************************/

    private final IntakeSub intake;

    public LoadBall(IntakeSub intake) {
        this.intake = intake;
        addRequirements(this.intake);
    }

    @Override
    public void execute() {

    }
}
