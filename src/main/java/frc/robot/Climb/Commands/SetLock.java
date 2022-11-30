package frc.robot.Climb.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Climb.ClimbSubsystem;

public class SetLock extends CommandBase {

    private final ClimbSubsystem climb;

    private final boolean lockOn;

    public SetLock(ClimbSubsystem climb, boolean lockOn) {
        this.climb = climb;
        this.lockOn = lockOn;
    }

    @Override
    public void initialize() {
        if (lockOn) climb.lock();
        else climb.unlock();
    }
    
}
