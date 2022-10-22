package frc.robot.drive;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.RelativeEncoder;
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
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RapperClass.FiftyCent;

public class DrivetrainSubsystem extends SubsystemBase {

    // Initializing SPARKY MAXES HOW ELECTRIFYING
    private static final FiftyCent rightMaster = new FiftyCent(2, MotorType.kBrushless, "Front Right");
    private static final FiftyCent rightSlave = new FiftyCent(4, MotorType.kBrushless, "Back Right");
    private static final FiftyCent leftMaster = new FiftyCent(1, MotorType.kBrushless, "Front Left");
    private static final FiftyCent leftSlave = new FiftyCent(3, MotorType.kBrushless, "Back Left");

    private static final RelativeEncoder rightEncoder = rightMaster.getEncoder();
    private static final RelativeEncoder leftEncoder = leftMaster.getEncoder();
    
    private static final AHRS gyro = new AHRS();

    public DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(0.762);
    private DifferentialDriveWheelSpeeds diffSpeeds = new DifferentialDriveWheelSpeeds(0.0, 0.0);
    //private ChassisSpeeds speeds = new ChassisSpeeds(0.0, 0.0, 0.0);

    private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(0.0), new Pose2d());
    private final Field2d field = new Field2d();

    private static final SimpleMotorFeedforward feedForward = new SimpleMotorFeedforward(0.37003, 1.1603);


    // CONSTANTS STUFF 
    public final double MAX_FORWARD_VELOCITY = 6;
    public final double GEARBOX_RATIO = 8.89;
    public final double WHEEL_CIRCUMPHRENCE = Units.inchesToMeters(6 * Math.PI);
    public final double MAX_RADIANS_TURN_VELO_THING_IDK = 20;

    public final double Ks = 0.37003;
    public final double Kv = 1.1603;
    public final double Ka = 0.40226;

    // Ramsete Stuff
    public final double b = 1.25;
    public final double zeta = 1;

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
        rightEncoder.setVelocityConversionFactor(1);
        leftEncoder.setVelocityConversionFactor(1);
        rightEncoder.setPositionConversionFactor(1);
        leftEncoder.setPositionConversionFactor(1);


        // West Coast Moment
        // ONLY COPIES VOLTAGE
        rightMaster.follow(ExternalFollower.kFollowerDisabled, 0);
        leftMaster.follow(ExternalFollower.kFollowerDisabled, 0);
        rightSlave.follow(rightMaster);
        leftSlave.follow(leftMaster);

        Shuffleboard.getTab("Odometry").add("Odometry", field)
            .withWidget(BuiltInWidgets.kField);

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Right Encoder", rightEncoder.getPosition() / GEARBOX_RATIO);
        SmartDashboard.putNumber("Left Encoder", leftEncoder.getPosition() / GEARBOX_RATIO);
        //FiftyCent.putShuffleboard();

        odometry.update(gyro.getRotation2d(), leftEncoder.getPosition() / GEARBOX_RATIO * WHEEL_CIRCUMPHRENCE, rightEncoder.getPosition() / GEARBOX_RATIO * WHEEL_CIRCUMPHRENCE);
        field.setRobotPose(odometry.getPoseMeters());
    }

    // Gets left encoder
    public RelativeEncoder getRightEncoder() {
        return rightEncoder;
    }

    // Gets right encoder
    public RelativeEncoder getLeftEncoder() {
        return leftEncoder;
    }

    public void resetOdometry(Pose2d pose) {
        odometry.resetPosition(pose, gyro.getRotation2d());
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    // Gets gyro angle
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

    public void kinDrive(ChassisSpeeds speeds) {
        this.diffSpeeds = kinematics.toWheelSpeeds(speeds);
        //System.out.println(speeds);
        this.diffSpeeds.desaturate(MAX_FORWARD_VELOCITY);
        //System.out.println(this.diffSpeeds);
        //System.out.println(feedForward.calculate(diffSpeeds.leftMetersPerSecond));
        leftMaster.setVoltage(feedForward.calculate(diffSpeeds.leftMetersPerSecond));
        rightMaster.setVoltage(feedForward.calculate(diffSpeeds.rightMetersPerSecond));
        //this.set(feedForward.calculate(diffSpeeds.leftMetersPerSecond), feedForward.calculate(diffSpeeds.rightMetersPerSecond));
    }

    public void voltDrive(double vLeft, double vRight) {
        leftMaster.setVoltage(vLeft);
        rightMaster.setVoltage(vRight);
        
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        //return new DifferentialDriveWheelSpeeds(leftEncoder.getVelocity(), rightEncoder.getVelocity());
        //return diffSpeeds;
        return new DifferentialDriveWheelSpeeds((-leftEncoder.getVelocity() / GEARBOX_RATIO * WHEEL_CIRCUMPHRENCE) / 60.0, (-rightEncoder.getVelocity() / GEARBOX_RATIO * WHEEL_CIRCUMPHRENCE) / 60.0);
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
    
}
