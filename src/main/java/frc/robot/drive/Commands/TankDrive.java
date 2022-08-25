package frc.robot.drive.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.drive.DrivetrainSubsystem;

public class TankDrive extends CommandBase {

    private DoubleSupplier left;
    private DoubleSupplier right;

    private DrivetrainSubsystem drive;

    public TankDrive(DrivetrainSubsystem drive, DoubleSupplier left, DoubleSupplier right) {
        this.left = left;
        this.right = right;
        this.drive = drive;
        addRequirements(this.drive);
    }

    @Override
    public void execute() {
        drive.set(left.getAsDouble(), right.getAsDouble());
    }
    
}
