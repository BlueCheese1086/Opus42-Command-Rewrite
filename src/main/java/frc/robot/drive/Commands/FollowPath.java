package frc.robot.drive.Commands;

import java.util.HashMap;

import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPRamseteCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.DriveConstants;
import frc.robot.drive.DrivetrainSubsystem;

public class FollowPath extends SequentialCommandGroup {

    public FollowPath(DrivetrainSubsystem drive, PathPlannerTrajectory traj, HashMap<String, Command> eventMap) {
        addCommands(
            new PPRamseteCommand(
                traj, 
                drive::getPose, 
                new RamseteController(), 
                new SimpleMotorFeedforward(DriveConstants.Ks, DriveConstants.Kv),
                DriveConstants.kinematics,
                drive::getWheelSpeeds,
                new PIDController(1, 0, 0),
                new PIDController(1, 0, 0),
                drive::voltDrive,
                eventMap,
                drive)
        );
    }

    public FollowPath(DrivetrainSubsystem drive, PathPlannerTrajectory traj) {
        addCommands(
            new PPRamseteCommand(
                traj, 
                drive::getPose, 
                new RamseteController(), 
                new SimpleMotorFeedforward(DriveConstants.Ks, DriveConstants.Kv),
                DriveConstants.kinematics,
                drive::getWheelSpeeds,
                new PIDController(1, 0, 0),
                new PIDController(1, 0, 0),
                drive::voltDrive,
                drive)
        );
    }

}
