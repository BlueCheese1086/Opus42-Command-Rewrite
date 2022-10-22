package frc.robot.drive.Commands;

import java.io.IOException;
import java.nio.file.Path;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.drive.DrivetrainSubsystem;

public class FollowPath extends RamseteCommand {

    public FollowPath(Trajectory t, DrivetrainSubsystem drive) {

        super(
                t,
                drive::getPose,
                new RamseteController(drive.b, drive.zeta),
                new SimpleMotorFeedforward(drive.Ks, drive.Kv, drive.Ka),
                drive.kinematics,
                drive::getWheelSpeeds,
                // new PIDController(0.1, 0.01, 0.5),
                // new PIDController(0.1, 0.01, 0.5),
                new PIDController(1, 0, 0),
                new PIDController(1, 0, 0),
                drive::voltDrive,
                drive);

    }

    public static Trajectory getTrajectoryFromPath(String s) {
        Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(s);
        Trajectory trajectory = new Trajectory();
        try {
            trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
        } catch (IOException e) {
            System.out.println("Couldnt find");
        }

        return trajectory;
    }

}
