package frc.robot.shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSub;

public class ShootBall extends CommandBase {

    private final IntakeSub intake;

    public ShootBall(IntakeSub in) {
        this.intake = in;
        addRequirements(this.intake);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        intake.runBottom();
        intake.runTop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interr) {
        intake.stopTower();
    }
    
}
