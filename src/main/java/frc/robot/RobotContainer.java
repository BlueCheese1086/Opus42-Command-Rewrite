// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.AUTO.TwoBallAUTO;
import frc.robot.Constants.DriveConstants;
import frc.robot.RapperClass.FiftyCent;
import frc.robot.climb.ClimbSubsystem;
import frc.robot.climb.Commands.Climb;
import frc.robot.climb.Commands.SetLock;
import frc.robot.drive.DrivetrainSubsystem;
import frc.robot.drive.Commands.DefaultDrive;
import frc.robot.drive.Commands.XAlignDrivetrain;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.intake.Commands.IndexBall;
import frc.robot.intake.Commands.IntakeBall;
import frc.robot.intake.Commands.OuttakeBall;
import frc.robot.sensors.LimelightSub;
import frc.robot.shooter.ShooterSubsystem;
import frc.robot.shooter.Commands.RunShooter;
import frc.robot.shooter.Commands.ShootBall;
import frc.robot.shooter.Commands.ShooterDistance;
import frc.robot.tower.TowerSubsystem;

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
  private final ShooterSubsystem shooter = new ShooterSubsystem();
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final ClimbSubsystem climb = new ClimbSubsystem();
  public final LimelightSub limelight = new LimelightSub();
  private final TowerSubsystem tower = new TowerSubsystem();

  XboxController driver = new XboxController(0);
  XboxController operator = new XboxController(1);

  SendableChooser<Command> autoList = new SendableChooser<>();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */

   //Testing changes
  public RobotContainer() {

    autoList.setDefaultOption("Two Ball Auto", new TwoBallAUTO(drivetrain, limelight, shooter, intake, tower));

    // Put the init motors on dashboard
    FiftyCent.putShuffleboard();
    Shuffleboard.update();

    // This does the vroom vroom drive drive :D
    drivetrain.setDefaultCommand(
        new DefaultDrive(drivetrain, this::getForward, this::getTurn));

    shooter.setDefaultCommand(new RunShooter(shooter, () -> 0.0));

    // Configure the button bindings
    configureButtonBindings();
    SmartDashboard.putNumber("Shooter Target", 10);
    SmartDashboard.putNumber("Hood Target", 0.5);

    Shuffleboard.getTab("Auto").add("Auto Chooser", autoList);
  }

  private void configureButtonBindings() {

    // Driver Controls

    new JoystickButton(driver, Button.kB.value).toggleWhenActive(new ShooterDistance(shooter, limelight, driver));
    new JoystickButton(driver, Button.kX.value).toggleWhenActive(new RunShooter(shooter, () -> SmartDashboard.getNumber("Shooter Target", 1), () -> SmartDashboard.getNumber("Hood Target", 0)));
    new JoystickButton(driver, Button.kA.value).whileHeld(new ShootBall(tower));

    new JoystickButton(driver, Button.kY.value).whileHeld(new XAlignDrivetrain(drivetrain, limelight));

    //new JoystickButton(driver, Button.kX.value).whileHeld(new IntakeBall(intake));

    //new JoystickButton(driver, Button.kRightBumper.value).whileHeld();

    //new JoystickButton(driver, Button.kRightBumper.value).whileHeld(new FollowPathGenerator(FollowPathGenerator.getTrajectoryFromPath("ShorterStart2ball1.wpilib.json"), drivetrain).getCmd());

    //new POVButton(driver, 0).whileActiveOnce(new FollowPathGenerator(FollowPathGenerator.getTrajectoryFromPath("Start2Ball1.wpilib.json"), drivetrain).getCmd());


    // Op Controls

    // Climb
    new Trigger(() -> Math.abs(operator.getLeftY()) > 0.1 || Math.abs(operator.getRightY()) > 0.1).whileActiveContinuous(new Climb(climb, () -> operator.getLeftY(), () -> operator.getRightY()));

    new JoystickButton(operator, Button.kRightBumper.value).whileHeld(new IndexBall(intake, true));
    new JoystickButton(operator, Button.kLeftBumper.value).whileHeld(new IndexBall(intake, false));

    new JoystickButton(driver, Button.kRightBumper.value).whileHeld(new IntakeBall(intake));

    new JoystickButton(operator, Button.kX.value).whileHeld(new OuttakeBall(intake));
    
    new JoystickButton(operator, Button.kY.value).whileHeld(new SetLock(climb, false)).whenReleased(new SetLock(climb, true));

    //toshi is coding very fast right now bruuuuuuuuuu so much coding this is crazy can't wait to see what these 


    /*
     * TODO
     * Driver
     * - Drive --
     * - Rev Shooter --
     * - Shoot --
     * - X Align --
     * Operator
     * - Climb --
     * - Indexer Individual --
     * - Hopper Individual
     * - Bottom Tower Control
     * - Intake Control (Intake extention and motor)
     */

  }

  // Input filtering

  private double getForward() {
    return Util.doMagik(driver.getRightTriggerAxis() - driver.getLeftTriggerAxis());
  }

  private double getTurn() {
    return -Util.doTurnMagik(driver.getLeftX());
  }

  public Command getAutonomousCommand() {
    return getAuto();
  }

  public Command getAuto() {
    final TwoBallAUTO twoBall = new TwoBallAUTO(drivetrain, limelight, shooter, intake, tower);
    drivetrain.resetOdometry(twoBall.getInitialPose());
    return twoBall;
  }

  public Command getTrajectoryTest() {
    Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve("Start1 to Ball1.wpilib.json");
    Trajectory trajectory = new Trajectory();
    try {
      trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
    } catch (IOException e) {
      System.out.println("Couldnt find");
    }

    RamseteCommand ramsete = new RamseteCommand(
      trajectory,
        drivetrain::getPose,
        new RamseteController(DriveConstants.b, DriveConstants.zeta),
        new SimpleMotorFeedforward(DriveConstants.Ks, DriveConstants.Kv, DriveConstants.Ka),
        drivetrain.kinematics,
        drivetrain::getWheelSpeeds,
        // new PIDController(0.1, 0.01, 0.5),
        // new PIDController(0.1, 0.01, 0.5),
        new PIDController(1, 0, 0),
        new PIDController(1, 0, 0),
        drivetrain::voltDrive,
        drivetrain);

    drivetrain.resetOdometry(trajectory.getInitialPose());

    return ramsete.andThen(() -> drivetrain.voltDrive(0, 0));
  }

}
