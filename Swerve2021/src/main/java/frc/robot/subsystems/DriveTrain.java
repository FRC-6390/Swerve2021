package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  // Motors
  private TalonFX frontLeftMomentum = new TalonFX(Constants.MOTORID.FRONT_LEFT_MOMENTUM.GetID());
  private TalonFX frontLeftRotation = new TalonFX(Constants.MOTORID.FRONT_LEFT_ROTATION.GetID());
  private TalonFX backLeftMomentum = new TalonFX(Constants.MOTORID.BACK_LEFT_MOMENTUM.GetID());
  private TalonFX backLeftRotation = new TalonFX(Constants.MOTORID.BACK_LEFT_ROTATION.GetID());
  private TalonFX frontRightMomentum = new TalonFX(Constants.MOTORID.FRONT_RIGHT_MOMENTUM.GetID());
  private TalonFX frontRightRotation = new TalonFX(Constants.MOTORID.FRONT_RIGHT_ROTATION.GetID());
  private TalonFX backRightMomentum = new TalonFX(Constants.MOTORID.BACK_RIGHT_MOMENTUM.GetID());
  private TalonFX backRightRotation = new TalonFX(Constants.MOTORID.BACK_RIGHT_ROTATION.GetID());
  //Encoders
  private CANCoder frontRightEncoder = new CANCoder(Constants.SENSORS.FRONT_RIGHT_ENCODER.GetID());
  private CANCoder frontLeftEncoder = new CANCoder(Constants.SENSORS.FRONT_LEFT_ENCODER.GetID());
  private CANCoder backRightEncoder = new CANCoder(Constants.SENSORS.BACK_RIGHT_ENCODER.GetID());
  private CANCoder backLeftEncoder = new CANCoder(Constants.SENSORS.BACK_LEFT_ENCODER.GetID());
  //Limit Switches
  private DigitalInput frontRightLimit = new DigitalInput(Constants.LIMITSWITCH.FRONT_RIGHT_LIMIT.GetID());
  private DigitalInput frontLeftLimit = new DigitalInput(Constants.LIMITSWITCH.FRONT_LEFT_LIMIT.GetID());
  private DigitalInput backRightLimit = new DigitalInput(Constants.LIMITSWITCH.BACK_RIGHT_LIMIT.GetID());
  private DigitalInput backLeftLimit = new DigitalInput(Constants.LIMITSWITCH.BACK_LEFT_LIMIT.GetID());

  Translation2d frontleftlocation= new Translation2d(Constants.SWERVE_LOCATION_FROM_CENTER,Constants.SWERVE_LOCATION_FROM_CENTER);
  Translation2d frontrightlocation = new Translation2d(Constants.SWERVE_LOCATION_FROM_CENTER,-Constants.SWERVE_LOCATION_FROM_CENTER);

  Translation2d backleftlocation = new Translation2d(-Constants.SWERVE_LOCATION_FROM_CENTER,Constants.SWERVE_LOCATION_FROM_CENTER);
  Translation2d backrightlocation = new Translation2d(-Constants.SWERVE_LOCATION_FROM_CENTER,-Constants.SWERVE_LOCATION_FROM_CENTER);

  SwerveDriveKinematics m_kinematics = new SwerveDriveKinematics(
        frontleftlocation, frontrightlocation, backleftlocation, backrightlocation
  );

  ChassisSpeeds speeds; 

  SwerveModuleState[] moduleStates; 
  SwerveModuleState frontLeft;
  SwerveModuleState frontRight;
  SwerveModuleState backLeft;
  SwerveModuleState backRight;

  
  

  /*                                                    stap touching the all mighty diagram
  * 
  * Front Front          Left Right                     This is our lord and savour the robot diagram           
  * |-----------------------------|                     please kneel in his presence, in his greatness.
  * | 1   |     \Intake/    |   1 | 
  * |   2 |                 | 2   | 
  * |-----|                 |-----| 
  * |                             | 
  * |                             | 
  * |         |Revolover|         |
  * |                             | 
  * |                             |
  * |-----|                 |-----| 
  * |   2 |                 | 2   | 
  * | 1   |    /Shooter\    |   1 | 
  * |-----------------------------| 
  * Bottom                   Bottom
  * Left                      Right
  * 
*/

  // GYRO
  private AHRS gyro = new AHRS(Port.kMXP);
  // PDP
  final PowerDistributionPanel PDP = new PowerDistributionPanel(Constants.PDP_DEVICE_ID);

  public DriveTrain() {
    speeds = new ChassisSpeeds(0,0,0);
    moduleStates = m_kinematics.toSwerveModuleStates(speeds);
    frontLeft = moduleStates[0];
    frontRight = moduleStates[1];
    backLeft = moduleStates[2];
    backRight= moduleStates[3];
    gyro.reset();
  }

  public void setSpeed(double fowardInput, double strafeInput, double rotationInput){
    ChassisSpeeds.fromFieldRelativeSpeeds(fowardInput, strafeInput, Math.PI / rotationInput, Rotation2d.fromDegrees(45));
  }



  public void centerFrontLeft(){
    //centers top left module
    boolean limit = frontLeftLimit.get();
    double encoder = frontLeftEncoder.getPosition();
    if(limit == true || encoder == 0){
      frontLeftEncoder.setPosition(0.0);
    }
    else{
      setSpeed(0, 0, 0.2);
    }
  }

  public void centerFrontRight(){
    //centers top right module
    boolean limit = frontRightLimit.get();
    double encoder = frontRightEncoder.getPosition();
    if(limit == true || encoder == 0){
      frontRightEncoder.setPosition(0.0);
    }
    else{
      setSpeed(0, 0, 0.2);
    }
  }

  public void centerBackLeft(){
    //center back left module
    boolean limit = backLeftLimit.get();
    double encoder = backLeftEncoder.getPosition();
    if(limit == true || encoder == 0){
      backLeftEncoder.setPosition(0.0);
    }
    else{
      setSpeed(0, 0, 0.2);
    }
  }

  public void centerBackRight(){
    //centers back right module
    boolean limit = backRightLimit.get();
    double encoder = backRightEncoder.getPosition();
    if(limit == true || encoder == 0){
      backRightEncoder.setPosition(0.0);
    }
    else{
      setSpeed(0, 0, 0.2);
    }
  }

  public void centerModules(){
    //centers all modules
    centerFrontLeft();
    centerFrontRight();
    centerBackLeft();
    centerBackRight();
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
    SmartDashboard.putNumber("Top Right Motor 1",frontRightMomentum.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Top Right Motor 2",frontRightRotation.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Right Motor 1",backRightMomentum.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Right Motor 2",backRightRotation.getSensorCollection().getIntegratedSensorPosition());

    SmartDashboard.putNumber("Forward Velocity", speeds.vyMetersPerSecond);
    SmartDashboard.putNumber("Strafe Velocity", speeds.vxMetersPerSecond);
    SmartDashboard.putNumber("Rotation Velocity", speeds.omegaRadiansPerSecond);
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