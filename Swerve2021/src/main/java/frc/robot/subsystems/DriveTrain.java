package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  // They are TalonFX speed controllers not TalonSPX
  private TalonFX frontLeftMomentum = new TalonFX(Constants.MOTORID.FRONT_LEFT_MOMENTUM.GetID());
  private TalonFX frontLeftRotation = new TalonFX(Constants.MOTORID.FRONT_LEFT_ROTATION.GetID());
  private TalonFX backLeftMomentum = new TalonFX(Constants.MOTORID.BACK_LEFT_MOMENTUM.GetID());
  private TalonFX backLeftRotation = new TalonFX(Constants.MOTORID.BACK_LEFT_ROTATION.GetID());
  private TalonFX frontRightMomentum = new TalonFX(Constants.MOTORID.FRONT_RIGHT_MOMENTUM.GetID());
  private TalonFX frontRightRotation = new TalonFX(Constants.MOTORID.FRONT_RIGHT_ROTATION.GetID());
  private TalonFX backRightMomentum = new TalonFX(Constants.MOTORID.BACK_RIGHT_MOMENTUM.GetID());
  private TalonFX backRightRotation = new TalonFX(Constants.MOTORID.BACK_RIGHT_ROTATION.GetID());

  //-----KINEMATICS-------
  Translation2d m_frontleftlocation = new Translation2d(0.3302,0.3302);
  Translation2d m_frontrightlocation = new Translation2d(0.3302,-0.3302);

  Translation2d m_backleftlocation = new Translation2d(-0.3302,0.3302);
  Translation2d m_backrightlocation = new Translation2d(-0.3302,-0.3302);

  SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(
        m_frontleftlocation, m_frontrightlocation, m_backleftlocation, m_backrightlocation
  );

  ChassisSpeeds speeds = new ChassisSpeeds(1.0,1.0,1.5);

  SwerveModuleState[] moduleStates = m_kinematics.toSwerveModuleStates(speeds);

  SwerveModuleState frontLeft = moduleStates[0];
  SwerveModuleState frontRight = moduleStates[1];
  SwerveModuleState backLeft = moduleStates[2];
  SwerveModuleState backRight = moduleStates[3];

  var frontLeftOptimized = SwerveModuleState.optimize(frontLeft,
   new Rotation2d(m_turningEncoder.getDistance()));

  ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(
  2.0, 2.0, Math.PI / 2.0, Rotation2d.fromDegrees(45.0));

  // Now use this in our kinematics
  SwerveModuleState[] moduleStates = kinematics.toSwerveModuleStates(speeds);

  // Example module states
  var frontLeftState = new SwerveModuleState(23.43, Rotation2d.fromDegrees(-140.19));
  var frontRightState = new SwerveModuleState(23.43, Rotation2d.fromDegrees(-39.81));
  var backLeftState = new SwerveModuleState(54.08, Rotation2d.fromDegrees(-109.44));
  var backRightState = new SwerveModuleState(54.08, Rotation2d.fromDegrees(-70.56));
  

  // Convert to chassis speeds
  ChassisSpeeds chassisSpeeds = kinematics.toChassisSpeeds(
    frontLeftState, frontRightState, backLeftState, backRightState);

  // Getting individual speeds
  double forward = chassisSpeeds.vxMetersPerSecond;
  double sideways = chassisSpeeds.vyMetersPerSecond;
  double angular = chassisSpeeds.omegaRadiansPerSecond;


  //------ODOMETRY-------
  // Creating my odometry object from the kinematics object. Here,
  // our starting pose is 5 meters along the long end of the field and in the
  // center of the field along the short end, facing forward.
  SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(m_kinematics,
    getGyro(), new Pose2d(5.0, 13.5, new Rotation2d()));

  /*
   * Front Front Left Right |-----------------------------| | M | \Intake/ | M | |
   * R | | R | |-----| |-----| | | | | M = Momentom | |Revolover| | R = Rotation |
   * | | | |-----| |-----| | R | | R | | M | /Shooter\ | M |
   * |-----------------------------| Bottom Bottom Left Right
   * 
   */

  // GYRO
  private AHRS gyro = new AHRS(Port.kMXP);
  // PDP
  final PowerDistributionPanel PDP = new PowerDistributionPanel(Constants.PDP_DEVICE_ID);

  public DriveTrain() {
    gyro.reset();
  }

  public Rotation2d getGyro(){
    gyro.getAngle();
  }
  
  public void resetGyro() {
    gyro.reset();
  }

  public void zeroDriveEncoders() {
    resetEncodersFL();
    resetEncodersBL();
    resetEncodersFR();
    resetEncodersBR();
    System.out.println("Drive Train Encoders Reset");
  }

  public void resetEncodersFL() {
    // Resets Top Left Module
    frontLeftMomentum.getSensorCollection().setIntegratedSensorPosition(0, 0);
    frontLeftRotation.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public void resetEncodersBL() {
    // Resets Back Left Module
    backLeftMomentum.getSensorCollection().setIntegratedSensorPosition(0, 0);
    backLeftRotation.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public void resetEncodersFR() {
    // Resets Top Right Module
    frontRightMomentum.getSensorCollection().setIntegratedSensorPosition(0, 0);
    frontRightRotation.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public void resetEncodersBR() {
    // Resets Top Right Module
    backRightMomentum.getSensorCollection().setIntegratedSensorPosition(0, 0);
    backRightRotation.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public void factoryResetDrive() {
    frontLeftMomentum.configFactoryDefault();
    frontLeftRotation.configFactoryDefault();
    backLeftMomentum.configFactoryDefault();
    backLeftRotation.configFactoryDefault();
    frontRightMomentum.configFactoryDefault();
    frontRightRotation.configFactoryDefault();
    backRightMomentum.configFactoryDefault();
    backRightRotation.configFactoryDefault();
  }

  @Override
  public void periodic() {
    // Display Motors on SDB
    SmartDashboard.putNumber("Top Left Motor 1", frontLeftMomentum.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Top Left Motor 2", frontLeftRotation.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Left Motor 1", backLeftMomentum.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Left Motor 2", backLeftRotation.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Top Right Motor 1",
        frontRightMomentum.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Top Right Motor 2",
        frontRightRotation.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Right Motor 1",
        backRightMomentum.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Right Motor 2",
        backRightRotation.getSensorCollection().getIntegratedSensorPosition());

    //-----ODOMETRY------
    // Get my gyro angle. We are negating the value because gyros return positive
    // values as the robot turns clockwise. This is not standard convention that is
    // used by the WPILib classes.
    var gyroAngle = Rotation2d.fromDegrees(-gyro.getAngle());

    // Update the pose
    m_pose = m_odometry.update(gyroAngle, frontLeftState.getState(), m_frontRightModule.getState(),
        m_backLeftModule.getState(), backRight.getState());
    }

  public void resetRobot() {
    zeroDriveEncoders();
    factoryResetDrive();
    resetGyro();
    PDP.clearStickyFaults();
    System.out.println("Robot Reset");
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}