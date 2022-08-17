package frc.robot.shooter.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.shooter.ShooterSub;

public class Shootball extends CommandBase {

    private final ShooterSub shoot;

    private final double speed;

    public Shootball(ShooterSub shoot, double speed) {
        this.shoot = shoot;
        this.speed = speed;
    }

    @Override
    public void execute() {
        shoot.setMotorVelo(speed);
    }

}
