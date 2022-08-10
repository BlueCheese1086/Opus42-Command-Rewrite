package frc.robot.sensors.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.sensors.Limelight;

public class limeOn extends CommandBase {

    private final Limelight lime;

    public limeOn(Limelight l) {
        lime = l;
        addRequirements(lime);
    }

    @Override
    public void execute() {
        lime.setLights(3);
    }
    
}