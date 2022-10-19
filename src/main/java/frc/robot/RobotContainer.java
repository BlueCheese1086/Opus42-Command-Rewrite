// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.RapperClass.FiftyCent;
import frc.robot.drive.DrivetrainSubsystem;
import frc.robot.drive.Commands.DefaultDrive;
import frc.robot.intake.IntakeSub;
import frc.robot.intake.Commands.IntakeBall;
import frc.robot.intake.Commands.OuttakeBall;
import frc.robot.intake.Commands.RunTower;
import frc.robot.sensors.GyroSub;
import frc.robot.sensors.LimelightSub;
import frc.robot.sensors.LimelightCommands.LimelightDefault;
import frc.robot.shooter.ShooterSub;
import frc.robot.shooter.Commands.DefaultShooter;
import frc.robot.shooter.Commands.RevShooter;
import frc.robot.shooter.Commands.Shootball;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final DrivetrainSubsystem drivetrain = new DrivetrainSubsystem();
  private final ShooterSub shooter = new ShooterSub();
  private final IntakeSub intake = new IntakeSub();

  private final LimelightSub limelight = new LimelightSub();
  //private final GyroSub gyro = new GyroSub();

  private double shootSpeed = 0;

  XboxController driver = new XboxController(0);
  //XboxController operator = new XboxController(1);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Put the init motors on dashboard
    FiftyCent.putShuffleboard();

    Shuffleboard.update();

    // This does the vroom vroom drive drive :D
    drivetrain.setDefaultCommand(
        new DefaultDrive(drivetrain, this::getForward, this::getTurn));

    // Limelight lights default off unless another command is using the limelight subsystem
    limelight.setDefaultCommand(
      new LimelightDefault(limelight));

    shooter.setDefaultCommand(
      new DefaultShooter(shooter, 0));

    // Configure the button bindings
    configureButtonBindings();
  }

  // Self made periodic method for debugging
  // Called in Robot.java
  public void periodic() {
    SmartDashboard.putNumber("Shooter Target", SmartDashboard.getNumber("Shooter Target", shootSpeed));
    shootSpeed = SmartDashboard.getNumber("Shooter Target", 0);
    SmartDashboard.putNumber("Actual Target Speed", shootSpeed);
    SmartDashboard.putNumber("Turn", getTurn());
    SmartDashboard.putNumber("Forward", getForward());
  }

  private void configureButtonBindings() {

    new JoystickButton(driver, Button.kB.value).whenHeld(new IntakeBall(intake));
    new JoystickButton(driver, Button.kX.value).whenHeld(new OuttakeBall(intake));

    new JoystickButton(driver, Button.kA.value).whenHeld(new RevShooter(shooter).andThen(new Shootball(intake)));


  }


  // Input filtering

  private double getForward() {
    return Meth.doMagik(driver.getRightTriggerAxis() - driver.getLeftTriggerAxis());
  }

  private double getTurn() {
    return -Meth.doTurnMagik(driver.getLeftX());
  }

}
