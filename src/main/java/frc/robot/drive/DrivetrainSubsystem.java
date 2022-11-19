package frc.robot.drive;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.ExternalFollower;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.RapperClass.FiftyCent;

public class DrivetrainSubsystem extends SubsystemBase {

    // Initializing SPARKY MAXES HOW ELECTRIFYING
    private static final FiftyCent rightMaster = new FiftyCent(2, MotorType.kBrushless, "Front Right");
    private static final FiftyCent rightSlave = new FiftyCent(4, MotorType.kBrushless, "Back Right");
    private static final FiftyCent leftMaster = new FiftyCent(1, MotorType.kBrushless, "Front Left");
    private static final FiftyCent leftSlave = new FiftyCent(3, MotorType.kBrushless, "Back Left");

    private static final RelativeEncoder rightEncoder = rightMaster.getEncoder();
    private static final RelativeEncoder leftEncoder = leftMaster.getEncoder();

    private static SparkMaxPIDController leftPid;
    private static SparkMaxPIDController rightPid;

    
    private static final AHRS gyro = new AHRS();
    
    //Play with this later for better odometry
    private static final Accelerometer accelerometer = new BuiltInAccelerometer();

    public DifferentialDriveKinematics kinematics = DriveConstants.kinematics;//new DifferentialDriveKinematics(0.762);
    private DifferentialDriveWheelSpeeds diffSpeeds = DriveConstants.wheelSpeeds;//new DifferentialDriveWheelSpeeds(0.0, 0.0);
    //private ChassisSpeeds speeds = new ChassisSpeeds(0.0, 0.0, 0.0);

    private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(0.0), new Pose2d());
    private final Field2d field = new Field2d();

    private static final SimpleMotorFeedforward feedForward = new SimpleMotorFeedforward(0.37003, 1.1603);

    public DrivetrainSubsystem() {

        gyro.calibrate();

        // Break Mode
        rightMaster.setIdleMode(IdleMode.kBrake);
        rightSlave.setIdleMode(IdleMode.kBrake);
        leftMaster.setIdleMode(IdleMode.kBrake);
        leftSlave.setIdleMode(IdleMode.kBrake);

        // Demos
        leftMaster.setOpenLoopRampRate(0);
        leftSlave.setOpenLoopRampRate(0);
        rightMaster.setOpenLoopRampRate(0);
        rightSlave.setOpenLoopRampRate(0);

        // Inverting left side
        leftMaster.setInverted(true);
        leftSlave.setInverted(true);
        rightMaster.setInverted(false);
        rightSlave.setInverted(false);
        
        //Set Current Limits
        leftMaster.setSmartCurrentLimit(0);
        leftSlave.setSmartCurrentLimit(0);
        rightMaster.setSmartCurrentLimit(0);
        rightSlave.setSmartCurrentLimit(0);

        // Resetting encoders
        rightEncoder.setPosition(0);
        leftEncoder.setPosition(0);
        rightEncoder.setVelocityConversionFactor(DriveConstants.WHEEL_CIRCUMPHRENCE / DriveConstants.GEARBOX_RATIO / 60);
        leftEncoder.setVelocityConversionFactor(DriveConstants.WHEEL_CIRCUMPHRENCE / DriveConstants.GEARBOX_RATIO / 60);
        rightEncoder.setPositionConversionFactor(DriveConstants.WHEEL_CIRCUMPHRENCE / DriveConstants.GEARBOX_RATIO);
        leftEncoder.setPositionConversionFactor(DriveConstants.WHEEL_CIRCUMPHRENCE / DriveConstants.GEARBOX_RATIO);

        // PIDS
        leftPid = leftMaster.getPIDController();
        rightPid = rightMaster.getPIDController();

        leftPid.setP(DriveConstants.kDriveP);
        leftPid.setI(DriveConstants.kDriveI);
        leftPid.setD(DriveConstants.kDriveD);

        rightPid.setP(DriveConstants.kDriveP);
        rightPid.setI(DriveConstants.kDriveI);
        rightPid.setD(DriveConstants.kDriveD);


        // West Coast Moment
        // ONLY COPIES VOLTAGE
        rightMaster.follow(ExternalFollower.kFollowerDisabled, 0);
        leftMaster.follow(ExternalFollower.kFollowerDisabled, 0);
        rightSlave.follow(rightMaster);
        leftSlave.follow(leftMaster);

        Shuffleboard.getTab("Odometry").add("Odometry", field)
            .withWidget(BuiltInWidgets.kField);

            Shuffleboard.getTab("Kin").addNumber("Left Speed", () -> leftEncoder.getVelocity());
            Shuffleboard.getTab("Kin").addNumber("Right Speed", () -> rightEncoder.getVelocity());
        }


    /**
     * Periodic - Every tick
     * Putting Left and Right Encoder on Shuffleboard
     * 
     * Updating odometry
     * 
     */
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getPosition() / DriveConstants.GEARBOX_RATIO);
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getPosition() / DriveConstants.GEARBOX_RATIO);
        //FiftyCent.putShuffleboard();
        //testTab.addNumber("Left Acceleration", );

        //testTab.addNumber("Desired RPM", );

        odometry.update(gyro.getRotation2d(), leftEncoder.getPosition() / DriveConstants.GEARBOX_RATIO * DriveConstants.WHEEL_CIRCUMPHRENCE, rightEncoder.getPosition() / DriveConstants.GEARBOX_RATIO * DriveConstants.WHEEL_CIRCUMPHRENCE);
        field.setRobotPose(odometry.getPoseMeters());
    }

    /**
     * Gets right encoder object
     * @return Right encoder object
     */
    public RelativeEncoder getRightEncoder() {
        return rightEncoder;
    }

    /**
     * Gets left encoder object
     * @return Left encoder object
     */
    public RelativeEncoder getLeftEncoder() {
        return leftEncoder;
    }

    /**
     * Resets odometry to given pose
     * @param pose Pose to reset odometry to
     */
    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(pose, gyro.getRotation2d());
    }


    /**
     * Gets currect robot odometry pose
     * @return Robot pose
     */
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    /**
     * Get current robot angle
     * @return Robot angle
     */
    public double getXRotation() {
        return gyro.getAngle();
    }

    /**
     * Arcade style drive
     * @param forward Forward axis position
     * @param turn Turn axis position
     */
    public void arcadeDrive(double forward, double turn) {
        this.set(forward + turn, forward - turn);
    }

    /**
     * Kinematics drive
     * @param speeds The X, Y, and Rot velo to move at
     */
    public void kinDrive(ChassisSpeeds speeds) {
        this.diffSpeeds = kinematics.toWheelSpeeds(speeds);
        //System.out.println(speeds);
        this.diffSpeeds.desaturate(DriveConstants.MAX_FORWARD_VELOCITY);
        //System.out.println(this.diffSpeeds);
        //System.out.println(feedForward.calculate(diffSpeeds.leftMetersPerSecond));
        /*leftMaster.setVoltage(feedForward.calculate(diffSpeeds.leftMetersPerSecond));
        rightMaster.setVoltage(feedForward.calculate(diffSpeeds.rightMetersPerSecond));*/
        leftPid.setReference(diffSpeeds.leftMetersPerSecond, ControlType.kVelocity);
        rightPid.setReference(diffSpeeds.rightMetersPerSecond, ControlType.kVelocity);
        //this.set(feedForward.calculate(diffSpeeds.leftMetersPerSecond), feedForward.calculate(diffSpeeds.rightMetersPerSecond));
    }


    /**
     * Set drive motors with voltage
     * @param vLeft Left voltage
     * @param vRight Right voltage
     */
    public void voltDrive(double vLeft, double vRight) {
        leftMaster.setVoltage(vLeft);
        rightMaster.setVoltage(vRight);        
    }

    /**
     * Get the current wheel speeds of the robot (m/s)
     * @return The current wheel speeds
     */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        //return new DifferentialDriveWheelSpeeds(leftEncoder.getVelocity(), rightEncoder.getVelocity());
        //return diffSpeeds;
        return new DifferentialDriveWheelSpeeds((-leftEncoder.getVelocity() / DriveConstants.GEARBOX_RATIO * DriveConstants.WHEEL_CIRCUMPHRENCE) / 60.0, (-rightEncoder.getVelocity() / DriveConstants.GEARBOX_RATIO * DriveConstants.WHEEL_CIRCUMPHRENCE) / 60.0);
    }

    /**
     * Statically set motor speeds
     * @param left Left motor speed
     * @param right Right motor speed
     */
    public void set(double left, double right) {
        leftMaster.set(left);
        rightMaster.set(right);
    }

    public boolean leftTraction() {
        //if (rightMaster.getEncoder().getVelocity() > 0) {}
        return Math.abs(getWheelSpeeds().leftMetersPerSecond) <= Math.abs((DriveConstants.Ks*-Math.signum(getWheelSpeeds().leftMetersPerSecond)+feedForward.calculate(diffSpeeds.leftMetersPerSecond))/DriveConstants.Kv);
    }

    public boolean rightTraction() {
        return Math.abs(getWheelSpeeds().rightMetersPerSecond) <= Math.abs((DriveConstants.Ks*-Math.signum(getWheelSpeeds().rightMetersPerSecond)+feedForward.calculate(diffSpeeds.rightMetersPerSecond))/DriveConstants.Kv);
    }

}
