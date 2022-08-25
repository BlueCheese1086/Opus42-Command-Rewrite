package frc.robot.intake.Commands;

import java.util.function.IntSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSub;

public class RunTower extends CommandBase {

    private final IntakeSub intake;

    private final int motor;
    private final double speed;

    public RunTower(IntakeSub in, int motorid, double speed) {
        intake = in;
        motor = motorid;
        this.speed = speed;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.runSection(motor, speed);
    }

    public void end(boolean interr) {
        intake.runSection(motor, 0);
    }
    
}
