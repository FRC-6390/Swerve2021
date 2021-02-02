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
import com.ctre.phoenix.sensors.CANCoder;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

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

  CANCoder test = new CANCoder(1);

  Translation2d m_frontleftlocation= new Translation2d(0.3302,0.3302);
  Translation2d m_frontrightlocation = new Translation2d(0.3302,-0.3302);

  Translation2d m_backleftlocation = new Translation2d(-0.3302,0.3302);
  Translation2d m_backrightlocation = new Translation2d(-0.3302,-0.3302);

  SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(
        m_frontleftlocation, m_frontrightlocation, m_backleftlocation, m_backrightlocation
  );

  ChassisSpeeds speeds; 

  SwerveModuleState[] moduleStates; 
  SwerveModuleState frontLeft;
  SwerveModuleState frontRight;
  SwerveModuleState backLeft;
  SwerveModuleState backRight;

  

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
    speeds = new ChassisSpeeds(1.0,1.0,1.5);
    moduleStates = m_kinematics.toSwerveModuleStates(speeds);
    frontLeft = moduleStates[0];
    frontRight = moduleStates[1];
    backLeft = moduleStates[2];
    backRight= moduleStates[3];
    gyro.reset();

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

  }

  public void setSpeed(double fowardInput, double strafeInput, double rotationInput){
    ChassisSpeeds.fromFieldRelativeSpeeds(fowardInput, strafeInput, Math.PI / rotationInput, Rotation2d.fromDegrees(45));
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