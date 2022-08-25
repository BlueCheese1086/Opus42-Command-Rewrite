package frc.robot.sensors.LimelightCommands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.sensors.LimelightSub;

public class LimelightDefault extends CommandBase {
    
    private final LimelightSub limelight;

    public LimelightDefault(LimelightSub limelight) {
        this.limelight = limelight;
        this.limelight.setLights(1);
        addRequirements(this.limelight);
    }

    public void initialize() {
        limelight.setLights(1);
    }

    @Override
    public void execute() {}

    public void end() {
        limelight.setLights(3);
    }
}
