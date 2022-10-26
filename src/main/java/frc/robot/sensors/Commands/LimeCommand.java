package frc.robot.sensors.Commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.sensors.LimelightSub;

public class LimeCommand extends CommandBase {
    
    private final LimelightSub lime;

    public LimeCommand(LimelightSub lime) {
        this.lime = lime;
        addRequirements(lime);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        lime.setLights(1);
    }

    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean inter) {
        lime.setLights(3);
    }

}
