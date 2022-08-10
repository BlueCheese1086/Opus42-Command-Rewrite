package frc.robot.drive.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.drive.Drivetrain;

public class Drive extends CommandBase {
    
    private final Drivetrain drivetrain;
    
    private final double forward, turn;


    public Drive(Drivetrain drive, double forward, double turn) {
        drivetrain = drive;
        this.forward = forward;
        this.turn = turn;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(forward, turn);
    }
}
