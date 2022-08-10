package frc.robot.sensors.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.sensors.Limelight;

public class limeOff extends CommandBase {

    private final Limelight lime;

    public limeOff(Limelight l) {
        lime = l;
        addRequirements(lime);
    }

    @Override
    public void execute() {
        // Lights off
        lime.setLights(1);
    }

    public void end(boolean interrupted) {
        if (interrupted) {
            // Lights on
            lime.setLights(3);
        }
    }

    public boolean isFinished() {
        return false;
    }
    
}
