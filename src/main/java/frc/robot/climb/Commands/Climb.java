package frc.robot.climb.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.climb.ClimbSubsystem;

public class Climb extends CommandBase {

    private final ClimbSubsystem climbSub;

    private final DoubleSupplier leftSpeed;
    private final DoubleSupplier rightSpeed;

    public Climb(ClimbSubsystem clime, DoubleSupplier leftSpeed, DoubleSupplier rightSpeed) {
        this.climbSub = clime;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
    }
    
    @Override
    public void initialize() {
        climbSub.unlock();
    }

    @Override
    public void execute() {
        climbSub.setLeft(leftSpeed.getAsDouble());
        climbSub.setRight(rightSpeed.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean inter) {
        climbSub.setLeft(0);
        climbSub.setRight(0);
        climbSub.lock();
    }



}
