package frc.robot.AUTO;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.drive.DrivetrainSubsystem;
import frc.robot.drive.Commands.FollowPathGenerator;
import frc.robot.drive.Commands.XAlignDrivetrain;
import frc.robot.intake.IntakeSub;
import frc.robot.intake.Commands.IntakeBall;
import frc.robot.sensors.LimelightSub;
import frc.robot.shooter.ShooterSub;
import frc.robot.shooter.Commands.AUTOShoot;
import frc.robot.shooter.Commands.RunShooter;

public class TwoBallAUTO extends SequentialCommandGroup {

    private final Trajectory t;

    public TwoBallAUTO(DrivetrainSubsystem drive, LimelightSub lime, ShooterSub shoot, IntakeSub in) {
        t = FollowPathGenerator.getTrajectoryFromPath("ShorterStart2ball1.wpilib.json");
        addCommands(
            //new AUTOShoot(drive, lime, shoot, in),
            new FollowPathGenerator(t, drive).getCmd().raceWith(new IntakeBall(in)),
            new XAlignDrivetrain(drive, lime),
            new AUTOShoot(drive, lime, shoot, in),
            new WaitCommand(2),
            new RunShooter(shoot, () -> 0));
    }

    public Pose2d getInitialPose() {
        return t.getInitialPose();
    }

}
