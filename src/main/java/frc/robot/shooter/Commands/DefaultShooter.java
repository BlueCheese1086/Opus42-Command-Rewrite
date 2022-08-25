package frc.robot.shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.shooter.ShooterSub;

public class DefaultShooter extends CommandBase {

    private final ShooterSub shoot;

    private final double speed;

    public DefaultShooter(ShooterSub shoot, double speed) {
        this.shoot = shoot;
        this.speed = speed;
        addRequirements(this.shoot);
    }

    @Override
    public void execute() {
        shoot.setMotorVelo(speed);
    }
}
