package frc.robot.shooter.Commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.sensors.LimelightSub;
import frc.robot.shooter.ShooterSubsystem;

public class ShooterDistance extends CommandBase {

    private final ShooterSubsystem shooter;
    private final LimelightSub lime;
    
    private final XboxController x;

    public ShooterDistance(ShooterSubsystem shoot, LimelightSub lime, XboxController x) {
        shooter = shoot;
        this.lime = lime;
        this.x = x;
        addRequirements(shooter, this.lime);
    }

    public ShooterDistance(ShooterSubsystem shoot, LimelightSub lime) {
        shooter = shoot;
        this.lime = lime;
        this.x = null;
        addRequirements(shooter, this.lime);
    }
    
    @Override
    public void initialize() {}

    @Override
    public void execute() {
        //shooter.setMotorVelo(shooter.getSpeedFromDistance(lime.getYAngle()));
        shooter.setMotorVelo(shooter.rpmToEncoderShitters(5000));
        //shooter.setHoodPosition(-0.026*lime.getYAngle()+0.0055);
        shooter.setHoodPosition(1);
        if (x == null) return;
        if (Math.abs(shooter.getMotorVelocity() - shooter.getSpeedFromDistance(lime.getYAngle())) < 100) {
            x.setRumble(RumbleType.kLeftRumble, 1);
            x.setRumble(RumbleType.kRightRumble, 1);
        } else {
            x.setRumble(RumbleType.kLeftRumble, 0);
            x.setRumble(RumbleType.kRightRumble, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setMotorVelo(0);
        if (x == null) return;
        x.setRumble(RumbleType.kLeftRumble, 0);
        x.setRumble(RumbleType.kRightRumble, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
