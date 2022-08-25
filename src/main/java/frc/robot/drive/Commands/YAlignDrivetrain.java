package frc.robot.drive.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OogaBoogaPID;
import frc.robot.drive.DrivetrainSubsystem;
import frc.robot.sensors.GyroSub;
import frc.robot.sensors.LimelightSub;

public class YAlignDrivetrain extends CommandBase {
    private final DrivetrainSubsystem drive;
    private final GyroSub gyro;
    private final LimelightSub lime;

    private final OogaBoogaPID yAlignPID = new OogaBoogaPID(1.5, 23, .05, 30, 1, 1);

    public YAlignDrivetrain(DrivetrainSubsystem drive, GyroSub gyro, LimelightSub lime) {
        this.drive = drive;
        this.gyro = gyro;
        this.lime = lime;
        addRequirements(this.drive, this.gyro, this.lime);
    }

    @Override
    public void execute() {
        double ty = lime.getYAngle();
        double speed = yAlignPID.calculate(ty, 0);

        if (yAlignPID.atSetpoint()) {
            speed = 0;
        }

        drive.set(-speed, -speed);
    }

    public boolean isFinished() {
        return yAlignPID.atSetpoint();
    }
}
