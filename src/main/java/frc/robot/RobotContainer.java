// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.drive.DrivetrainSubsystem;
import frc.robot.drive.Commands.DefaultDrive;
import frc.robot.intake.IntakeSub;
import frc.robot.intake.Commands.IntakeBall;
import frc.robot.sensors.LimelightSub;
import frc.robot.shooter.ShooterSub;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
  private final ShooterSub shooter = new ShooterSub();
  private final LimelightSub limelight = new LimelightSub();
  private final IntakeSub intake = new IntakeSub();

  XboxController driver = new XboxController(0);
  XboxController operator = new XboxController(1);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {


    // This does the vroom vroom drive drive :D
    drivetrain.setDefaultCommand(
      new DefaultDrive(drivetrain, driver::getLeftY, driver::getRightY));

      // Configure the button bindings
    configureButtonBindings();
  }
 

  // Self made periodic method for debugging
  // Called in Robot.java
  public void periodic() {
    SmartDashboard.putNumber("Trigger", driver.getRightTriggerAxis());
  }

  private void configureButtonBindings() {

    new JoystickButton(operator, Button.kA.value).whenHeld(new IntakeBall(intake));
    

  }

}
