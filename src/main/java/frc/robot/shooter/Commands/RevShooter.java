package frc.robot.shooter.Commands;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.shooter.ShooterSub;

public class RevShooter extends CommandBase {

    private final ShooterSub shoot;

    private double speed;

    public RevShooter(ShooterSub shoot) {
        this.shoot = shoot;
        addRequirements(this.shoot);
    }

    public void initialize() {
        speed = SmartDashboard.getNumber("Shooter Target", 0);
    }

    @Override
    public void execute() {
        shoot.setMotorVelo(speed);
    }

    public boolean isFinished() {
        return Math.abs(shoot.getMotorVelocity() - speed) < 250 ? true : false;
        //return false;
    }
}
