package frc.robot.shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.sensors.LimelightSub;
import frc.robot.shooter.ShooterSub;

public class ShooterDistance extends CommandBase {

    private final ShooterSub shooter;
    private final LimelightSub lime;

    public ShooterDistance(ShooterSub shoot, LimelightSub lime) {
        shooter = shoot;
        this.lime = lime;
        addRequirements(shooter, this.lime);
    }
    
    @Override
    public void initialize() {}

    @Override
    public void execute() {
        shooter.setMotorVelo(shooter.getSpeedFromDistance(lime.getXAngle()));
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setMotorVelo(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
