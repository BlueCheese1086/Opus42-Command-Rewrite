package frc.robot.drive.Commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OogaBoogaPID;
import frc.robot.Constants.DriveConstants;
import frc.robot.drive.DrivetrainSubsystem;
import frc.robot.sensors.LimelightSub;

public class XAlignDrivetrain extends CommandBase {
    private final DrivetrainSubsystem drive;
    private final LimelightSub lime;

    //private final OogaBoogaPID xAlignPID = new OogaBoogaPID(0.3, 28.0, 0.01, 15, 3, .5);
    private final OogaBoogaPID xAlignPID = new OogaBoogaPID(0.3, 0.0, 0.02, 15, 3, 1);

    public XAlignDrivetrain(DrivetrainSubsystem drive, LimelightSub lime) {
        this.drive = drive;
        this.lime = lime;
        addRequirements(this.drive, this.lime);
    }

    @Override
    public void execute() {
        double tx = lime.getXAngle();
        double speed = xAlignPID.calculate(tx, 0) * 0.3;
        drive.set(-speed, speed);
    }

    public boolean isFinished() {
        return false;
        //return xAlignPID.atSetpoint();
    }

    public void end(boolean interr) {
        drive.stop();
    }
}
