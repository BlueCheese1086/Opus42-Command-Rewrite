package frc.robot.shooter.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.shooter.ShooterSub;

public class RunShooter extends CommandBase {

    private final ShooterSub shoot;

    private final DoubleSupplier speed;

    public RunShooter(ShooterSub shoot, DoubleSupplier speed) {
        this.shoot = shoot;
        this.speed = speed;
        addRequirements(this.shoot);
    }

    @Override
    public void execute() {
        shoot.setMotorVelo(speed.getAsDouble());
    }

    @Override
    public void end(boolean inter) {
        shoot.setMotorVelo(0);
    }
}
