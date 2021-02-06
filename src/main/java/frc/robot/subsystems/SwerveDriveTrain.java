package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SwerveDriveTrain extends SubsystemBase {
  
  private static TalonFX frontLeftMomentum,
  frontLeftRotation,
  backLeftMomentum,
  backLeftRotation,
  frontRightMomentum,
  frontRightRotation,
  backRightMomentum,
  backRightRotation;

  private static List<TalonFX> momentumMotorArray,
  rotationMotorArray, motorArray;

  private static CANCoder frontRightEncoder,
  frontLeftEncoder,
  backRightEncoder,
  backLeftEncoder;

  private static List<CANCoder> encoderArray;

  private final SwerveModule frontLeftModule,
  frontRightModule,
  backLeftModule,
  backRightModule;

  private static DigitalInput frontRightLimit,
  frontLeftLimit,
  backRightLimit,
  backLeftLimit;

  private static List<DigitalInput> limitSwitchArray;

  private final Translation2d frontLeftLocation,
  frontRightLocation,
  backLeftLocation,
  backRightLocation;

  private final SwerveDriveKinematics kinematics;

  private ChassisSpeeds speeds; 

  public static AHRS gyro;

  final PowerDistributionPanel PDP;

/*                                                    stap touching the all mighty diagram
 *                     <Front>
 *         Module 1               Module 2                     This is our lord and savour the robot diagram           
 *        |-------------------------------|                    please kneel in his presence, in his greatness.
 *        | 1M   |     \Intake/    |   2M |                     
 *        |   5R |                 | 6R   | 
 *        |------|                 |------| 
 *        |                               | 
 *        |                               |  
 * <left> |          |Revolover|          | <Right>  
 *        |                               |                     M = Momentum Motor 
 *        |                               |                     R = Rotation Motor
 *        |------|                 |------| 
 *        |   3M |                 | 4M   | 
 *        | 7R   |    /Shooter\    |   8R | 
 *        |-------------------------------| 
 *         Module 3               Module 4
 *                     <Back>     
 *               
 */

  public SwerveDriveTrain() {

    frontLeftMomentum = new TalonFX(Constants.MOTORID.FRONT_LEFT_MOMENTUM.GetID());
    frontLeftRotation = new TalonFX(Constants.MOTORID.FRONT_LEFT_ROTATION.GetID());
    backLeftMomentum = new TalonFX(Constants.MOTORID.BACK_LEFT_MOMENTUM.GetID());
    backLeftRotation = new TalonFX(Constants.MOTORID.BACK_LEFT_ROTATION.GetID());
    frontRightMomentum = new TalonFX(Constants.MOTORID.FRONT_RIGHT_MOMENTUM.GetID());
    frontRightRotation = new TalonFX(Constants.MOTORID.FRONT_RIGHT_ROTATION.GetID());
    backRightMomentum = new TalonFX(Constants.MOTORID.BACK_RIGHT_MOMENTUM.GetID());
    backRightRotation = new TalonFX(Constants.MOTORID.BACK_RIGHT_ROTATION.GetID());

    momentumMotorArray = new ArrayList<TalonFX>(){{
      set(1,frontLeftMomentum);
      set(2,frontRightMomentum);
      set(3,backLeftMomentum);
      set(4,backRightMomentum);

    }};

    rotationMotorArray = new ArrayList<TalonFX>(){{
      set(1,frontLeftRotation);
      set(2,frontRightRotation);
      set(3,backLeftRotation);
      set(4,backRightRotation);

    }};

    motorArray = new ArrayList<TalonFX>(){{
      set(1,frontLeftMomentum);
      set(2,frontRightMomentum);
      set(3,backLeftMomentum);
      set(4,backRightMomentum);
      set(5,frontLeftRotation);
      set(6,frontRightRotation);
      set(7,backLeftRotation);
      set(8,backRightRotation);
    }};

    frontRightEncoder = new CANCoder(Constants.SENSORS.FRONT_RIGHT_ENCODER.GetID());
    frontLeftEncoder = new CANCoder(Constants.SENSORS.FRONT_LEFT_ENCODER.GetID());
    backRightEncoder = new CANCoder(Constants.SENSORS.BACK_RIGHT_ENCODER.GetID());
    backLeftEncoder = new CANCoder(Constants.SENSORS.BACK_LEFT_ENCODER.GetID());

    encoderArray = new ArrayList<CANCoder>(){{
      set(1,frontLeftEncoder);
      set(2,frontRightEncoder);
      set(3,backLeftEncoder);
      set(4,backRightEncoder);
    }};

    frontLeftModule = new SwerveModule(Constants.SWERVE.FRONT_LEFT_MODULE.GetID());
    frontRightModule = new SwerveModule(Constants.SWERVE.FRONT_RIGHT_MODULE.GetID());
    backRightModule = new SwerveModule(Constants.SWERVE.BACK_LEFT_MODULE.GetID());
    backLeftModule = new SwerveModule(Constants.SWERVE.BACK_RIGHT_MODULE.GetID()); 

    frontRightLimit = new DigitalInput(Constants.SENSORS.FRONT_RIGHT_LIMIT.GetID());
    frontLeftLimit = new DigitalInput(Constants.SENSORS.FRONT_LEFT_LIMIT.GetID());
    backRightLimit = new DigitalInput(Constants.SENSORS.BACK_RIGHT_LIMIT.GetID());
    backLeftLimit = new DigitalInput(Constants.SENSORS.BACK_LEFT_LIMIT.GetID());

    limitSwitchArray = new ArrayList<DigitalInput>(){{
      set(1,frontLeftLimit);
      set(2,frontRightLimit);
      set(3,backLeftLimit);
      set(4,backRightLimit);
    }};

    frontLeftLocation= new Translation2d(Constants.SWERVE_LOCATION_FROM_CENTER,Constants.SWERVE_LOCATION_FROM_CENTER);
    frontRightLocation = new Translation2d(Constants.SWERVE_LOCATION_FROM_CENTER,-Constants.SWERVE_LOCATION_FROM_CENTER);
    backLeftLocation = new Translation2d(-Constants.SWERVE_LOCATION_FROM_CENTER,Constants.SWERVE_LOCATION_FROM_CENTER);
    backRightLocation = new Translation2d(-Constants.SWERVE_LOCATION_FROM_CENTER,-Constants.SWERVE_LOCATION_FROM_CENTER);

    kinematics = new SwerveDriveKinematics(frontLeftLocation,frontRightLocation,backLeftLocation,backRightLocation);

    speeds = new ChassisSpeeds(0,0,0);

    gyro = new AHRS(Port.kMXP);

    PDP = new PowerDistributionPanel(Constants.PDP_DEVICE_ID);

    gyro.reset();
  }

  public void drive(double xSpeed, double ySpeed, double rotation){

    var swerveModuleStates = kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotation, gyro.getRotation2d()));
    SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates, Constants.ROBOT_MAX_SPEED);
    frontLeftModule.setDesiredState(swerveModuleStates[0]);
    frontRightModule.setDesiredState(swerveModuleStates[1]);
    backLeftModule.setDesiredState(swerveModuleStates[2]);
    backRightModule.setDesiredState(swerveModuleStates[3]);

  }

  public static void setMotorSpeed(int motorId, double speed){
    int id = motorId == 0 ? 1 : motorId;
    motorArray.get(id).set(ControlMode.PercentOutput, speed);
  }

  public static void setModuleSpeed(int moduleId, double rotationSpeed, double momentumSpeed){
    int id = moduleId == 0 ? 1 : moduleId;
    motorArray.get(id).set(ControlMode.PercentOutput, momentumSpeed);
    motorArray.get(id+4).set(ControlMode.PercentOutput, rotationSpeed);
    //               ^bad way of doing this
  }


  //gets for the arrays
  public static List<TalonFX> getMotorArray(){
    return motorArray;
  }

  public static List<TalonFX> getMomentumMotorArray(){
    return momentumMotorArray;
  }

  public static List<TalonFX> getRotationMotorArray(){
    return rotationMotorArray;
  }

  public static List<CANCoder> getEncoderArray(){
    return encoderArray;
  }

  public static List<DigitalInput> getLimitSwitchArray(){
    return limitSwitchArray;
  }

  public static void resetModuleEncoder(int encoderId){
    int id = encoderId == 0 ? 1 : encoderId;

    encoderArray.get(id).setPosition(0.0);
    System.out.println("Reseted Module Encoder ID: "+id);
  }

  public static void resetModuleEncoders(){
    for (CANCoder canCoder : encoderArray) {
      canCoder.setPosition(0.0);
    }
    System.out.println("Rested All Module Encoders");
  }

  public static void resetMotorEncoder(int motorId){
    int id = motorId == 0 ? 1 : motorId;

    motorArray.get(id).getSensorCollection().setIntegratedSensorPosition(0,0);
    System.out.println("Reseted Motor Encoder ID: "+id);
  }

  public static void resetMotorEncoders(){
    for (TalonFX motor : motorArray) {
      motor.getSensorCollection().setIntegratedSensorPosition(0,0);
    }
    System.out.println("Rested All Motor Encoders");
  }


  @Override
  public void periodic() {
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
}