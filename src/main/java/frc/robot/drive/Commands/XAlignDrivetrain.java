package frc.robot.drive.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OogaBoogaPID;
import frc.robot.drive.DrivetrainSubsystem;
import frc.robot.sensors.LimelightSub;

public class XAlignDrivetrain extends CommandBase {
    private final DrivetrainSubsystem drive;
    private final LimelightSub lime;

    private final OogaBoogaPID xAlignPID = new OogaBoogaPID(0.3, 28.0, 0.01, 15, 3, .5);

    public XAlignDrivetrain(DrivetrainSubsystem drive, LimelightSub lime) {
        this.drive = drive;
        this.lime = lime;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        lime.turnOnLights();
    }

    @Override
    public void execute() {
        double tx = lime.getXAngle();
        double speed = xAlignPID.calculate(tx, 0);
        if (xAlignPID.atSetpoint()) {
           speed = 0;
        }
        drive.set(-speed, speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        lime.turnOffLights();
    }
}
