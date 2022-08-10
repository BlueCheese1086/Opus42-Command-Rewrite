package frc.robot.drive.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OogaBoogaPID;
import frc.robot.drive.Drivetrain;
import frc.robot.sensors.Limelight;

public class AlignRobot extends CommandBase {

    private final Drivetrain drivetrain;
    private final Limelight lime;
    private final OogaBoogaPID xAlignPID = new OogaBoogaPID(0.3, 28.0, 0.01, 15, 3, .5);

    public AlignRobot(Drivetrain drive, Limelight lime) {
        this.drivetrain = drive;
        this.lime = lime;
    }

    @Override
    public void execute() {
        double tx = lime.getXAngle();
        double speed = xAlignPID.calculate(tx, 0);
        if (xAlignPID.atSetpoint()) {
           speed = 0;
        }
        drivetrain.staticDrive(-speed, speed);
    }
    
}
