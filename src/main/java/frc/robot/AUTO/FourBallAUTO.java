package frc.robot.AUTO;

import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.drive.DrivetrainSubsystem;
import frc.robot.drive.Commands.FollowPath;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.intake.Commands.IntakeBall;
import frc.robot.sensors.LimelightSub;
import frc.robot.shooter.ShooterSubsystem;
import frc.robot.shooter.Commands.AUTOShoot;
import frc.robot.shooter.Commands.RunShooter;
import frc.robot.shooter.Commands.ShooterDistance;
import frc.robot.tower.TowerSubsystem;

public class FourBallAUTO extends SequentialCommandGroup {
    
    public FourBallAUTO(DrivetrainSubsystem drive, IntakeSubsystem intake, LimelightSub lime, ShooterSubsystem shoot, TowerSubsystem tower) {
        HashMap<String, Command> markers = new HashMap<>();
        markers.put("shoot1", new SequentialCommandGroup(
            new WaitCommand(1).raceWith(new IntakeBall(intake)),
            new AUTOShoot(drive, lime, shoot, tower),
            new RunShooter(shoot, () -> 0)
        ));
        markers.put("intake2", new WaitCommand(1).raceWith(new IntakeBall(intake)));
        markers.put("RevShooter", new ShooterDistance(shoot, lime));
        addCommands(
            new FollowPath(drive, 
            PathPlanner.loadPath("4 Ball", new PathConstraints(6, 1)),
            markers),
            new AUTOShoot(drive, lime, shoot, tower));
    }


}
