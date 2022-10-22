package frc.robot.shooter.Commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.drive.DrivetrainSubsystem;
import frc.robot.drive.Commands.XAlignDrivetrain;
import frc.robot.intake.IntakeSub;
import frc.robot.sensors.LimelightSub;
import frc.robot.shooter.ShooterSub;

public class AUTOShoot extends SequentialCommandGroup {
    

    public AUTOShoot(DrivetrainSubsystem drive, LimelightSub lime, ShooterSub shoot, IntakeSub in) {
        addCommands(
            new XAlignDrivetrain(drive, lime).raceWith(new ShooterDistance(shoot, lime)), 
            new ShootBall(in));
    }

}
