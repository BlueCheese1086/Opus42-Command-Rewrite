package frc.robot.drive.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.drive.DrivetrainSubsystem;

public class DefaultDrive extends CommandBase {

    private final DrivetrainSubsystem drive;
    private final DoubleSupplier forward;
    private final DoubleSupplier turn;

    public DefaultDrive(DrivetrainSubsystem drive, DoubleSupplier forward, DoubleSupplier turn) {
        this.drive = drive;
        this.forward = forward;
        this.turn = turn;
        addRequirements(this.drive);
    }

    @Override
    public void execute() {
        drive.arcadeDrive(forward.getAsDouble()/4, turn.getAsDouble()/4);
    }
    
}
