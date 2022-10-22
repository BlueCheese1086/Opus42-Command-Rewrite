package frc.robot.AUTO;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.drive.DrivetrainSubsystem;
import frc.robot.drive.Commands.FollowPath;
import frc.robot.intake.IntakeSub;
import frc.robot.intake.Commands.IntakeBall;
import frc.robot.sensors.LimelightSub;
import frc.robot.shooter.ShooterSub;
import frc.robot.shooter.Commands.AUTOShoot;

public class TwoBallAUTO extends SequentialCommandGroup {

    private final Trajectory t;

    public TwoBallAUTO(DrivetrainSubsystem drive, LimelightSub lime, ShooterSub shoot, IntakeSub in) {
        t = FollowPath.getTrajectoryFromPath("Start2Ball1.json");
        addCommands(
            new AUTOShoot(drive, lime, shoot, in),
            new FollowPath(t, drive)
            .raceWith(new IntakeBall(in)));
    }

    public Pose2d getInitialPose() {
        return t.getInitialPose();
    }

}
