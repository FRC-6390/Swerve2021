package frc.robot.subsystems.drivetrain;

import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SwerveDriveTrain extends SubsystemBase {
  //Initiation
  //DriveTrain Motors
  private static TalonFX frontLeftMomentum,
  frontLeftRotation,
  backLeftMomentum,
  backLeftRotation,
  frontRightMomentum,
  frontRightRotation,
  backRightMomentum,
  backRightRotation,
  momentumMotorArray[],   //Motor Arrays
  rotationMotorArray[], 
  motorArray[];

  //External Encoders on modules
  private static CANCoder frontRightEncoder,
  frontLeftEncoder,
  backRightEncoder,
  backLeftEncoder,
  encoderArray[]; //External Encoder Array

  //Swerve Modules
  private final SwerveModule frontLeftModule,
  frontRightModule,
  backLeftModule,
  backRightModule;

  //Module Location
  private final Translation2d frontLeftLocation,
  frontRightLocation,
  backLeftLocation,
  backRightLocation;

  private final SwerveDriveKinematics kinematics;
  private ChassisSpeeds speeds; 

  public static AHRS gyro;
  final PowerDistributionPanel PDP;

  private static SwerveDriveTrain instance;

/*                                                    
 *                    <Front>
 *         Module 1               Module 2                     
 *        |-----------------------------|                    RULES:
 *        | 0M   |   \Intake/    |   1M |                    1. if you touch you die
 *        |   4R |               | 5R   |                    2. if you mess it up and dont know how to 
 *        |------                |------|                       fix it ask another team programmer
 *        |                             |                    3. Follow Rule # 1 and # 2
 *        |                             |  
 * <left> |         |Revolover|         | <Right>  
 *        |                             |                     M = Momentum Motor 
 *        |                             |                     R = Rotation Motor
 *        |------|               |------| 
 *        |   2M |   |Battery|   | 3M   | 
 *        | 6R   |   /Shooter\   |   7R | 
 *        |-----------------------------| 
 *         Module 3               Module 4
 *                    <Back>     
 *               
 */

  public SwerveDriveTrain() {

    //Declaring Motors
    frontLeftMomentum = new TalonFX(Constants.MOTORID.FRONT_LEFT_MOMENTUM.GetID());
    frontLeftRotation = new TalonFX(Constants.MOTORID.FRONT_LEFT_ROTATION.GetID());
    backLeftMomentum = new TalonFX(Constants.MOTORID.BACK_LEFT_MOMENTUM.GetID());
    backLeftRotation = new TalonFX(Constants.MOTORID.BACK_LEFT_ROTATION.GetID());
    frontRightMomentum = new TalonFX(Constants.MOTORID.FRONT_RIGHT_MOMENTUM.GetID());
    frontRightRotation = new TalonFX(Constants.MOTORID.FRONT_RIGHT_ROTATION.GetID());
    backRightMomentum = new TalonFX(Constants.MOTORID.BACK_RIGHT_MOMENTUM.GetID());
    backRightRotation = new TalonFX(Constants.MOTORID.BACK_RIGHT_ROTATION.GetID());

  
    //Adding Momentum Motors to Array
    momentumMotorArray = new TalonFX[4];
    momentumMotorArray[0] = frontLeftMomentum;
    momentumMotorArray[1] = frontRightMomentum;
    momentumMotorArray[2] = backLeftMomentum;
    momentumMotorArray[3] = backRightMomentum;

    

    //Adding Rotation Motors to Array
    rotationMotorArray = new TalonFX[4];
    rotationMotorArray[0] = frontLeftRotation;
    rotationMotorArray[1] = frontRightRotation;
    rotationMotorArray[2] = backLeftRotation;
    rotationMotorArray[3] = backRightRotation;

    

    //Adding all Drivetrain Motors to Array
    motorArray = new TalonFX[8];
      motorArray[0] = frontLeftMomentum;
      motorArray[1] = frontRightMomentum;
      motorArray[2] = backLeftMomentum;
      motorArray[3] = backRightMomentum;
      motorArray[4] = frontLeftRotation;
      motorArray[5] = frontRightRotation;
      motorArray[6] = backLeftRotation;
      motorArray[7] = backRightRotation;


    //Declaring Encoders
    frontRightEncoder = new CANCoder(Constants.SENSORS.FRONT_RIGHT_ENCODER.GetID());
    frontLeftEncoder = new CANCoder(Constants.SENSORS.FRONT_LEFT_ENCODER.GetID());
    backRightEncoder = new CANCoder(Constants.SENSORS.BACK_RIGHT_ENCODER.GetID());
    backLeftEncoder = new CANCoder(Constants.SENSORS.BACK_LEFT_ENCODER.GetID());

    //Adding Encoders to Array
    encoderArray = new CANCoder[4];
      encoderArray[0] = frontLeftEncoder;
      encoderArray[1] = frontRightEncoder;
      encoderArray[2] = backLeftEncoder;
      encoderArray[3] = backRightEncoder;


    //Declaring Modules
    frontLeftModule = new SwerveModule(Constants.SWERVE.FRONT_LEFT_MODULE.GetID());
    frontRightModule = new SwerveModule(Constants.SWERVE.FRONT_RIGHT_MODULE.GetID());
    backRightModule = new SwerveModule(Constants.SWERVE.BACK_LEFT_MODULE.GetID());
    backLeftModule = new SwerveModule(Constants.SWERVE.BACK_RIGHT_MODULE.GetID()); 

    //Declaring The Modules Location From the Center of The Bot
    frontLeftLocation= new Translation2d(Constants.SWERVE.LOCATION_FROM_CENTER.get(),Constants.SWERVE.LOCATION_FROM_CENTER.get());
    frontRightLocation = new Translation2d(Constants.SWERVE.LOCATION_FROM_CENTER.get(),-Constants.SWERVE.LOCATION_FROM_CENTER.get());
    backLeftLocation = new Translation2d(-Constants.SWERVE.LOCATION_FROM_CENTER.get(),Constants.SWERVE.LOCATION_FROM_CENTER.get());
    backRightLocation = new Translation2d(-Constants.SWERVE.LOCATION_FROM_CENTER.get(),-Constants.SWERVE.LOCATION_FROM_CENTER.get());


    //SAFETY FEATURES FOR MOTORS
    //Falcons go up to 40Amps
    //Supply is for motor controller Stator is for motor keeping number low for now
    //Drive Motors                                                               enabled | Limit(amp) | Trigger Threshold(amp) | Trigger Threshold Time(s)
    
    for (int i = 0; i < motorArray.length; i++) {
      motorArray[i].configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true,      30,                35,                1.0));
      motorArray[i].configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true,      20,                25,                0.5));
    }
    //Swevre Kinematics
    kinematics = new SwerveDriveKinematics(frontLeftLocation,frontRightLocation,backLeftLocation,backRightLocation);
    speeds = new ChassisSpeeds(0,0,0);

    //Gyro
    gyro = new AHRS(Port.kMXP);
    //Power Distribution Panel
    PDP = new PowerDistributionPanel(Constants.PDP_DEVICE_ID);

    gyro.reset();
  }

  //Used for actualy moving the Robot
  public void drive(double xSpeed, double ySpeed, double rotation){
    var swerveModuleStates = kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotation, gyro.getRotation2d()));
    SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates, Constants.ROBOT.MAX_SPEED.get());
    frontLeftModule.setDesiredState(swerveModuleStates[0]);
    frontRightModule.setDesiredState(swerveModuleStates[1]);
    backLeftModule.setDesiredState(swerveModuleStates[2]);
    backRightModule.setDesiredState(swerveModuleStates[3]);
  }

  //Sets Motor Speeds
  public static void setMotorSpeed(int motorId, double speed){
    motorArray[motorId].set(ControlMode.PercentOutput, speed);
  }

  //Moving all Module Motors
  public static void setModuleSpeed(int moduleId, double rotationSpeed, double momentumSpeed){
    motorArray[moduleId].set(ControlMode.PercentOutput, momentumSpeed);
    motorArray[moduleId+4].set(ControlMode.PercentOutput, rotationSpeed);
    //               ^bad way of doing this
  }


  //Get For the Array
  public static List<TalonFX> getMotorArray(){
    List<TalonFX> list = Arrays.asList(motorArray);
    return list;
  }

  public static List<TalonFX> getMomentumMotorArray(){
    List<TalonFX> list = Arrays.asList(momentumMotorArray);
    return list;
  }

  public static List<TalonFX> getRotationMotorArray(){
    List<TalonFX> list = Arrays.asList(rotationMotorArray);
    return list;
  }

  public static List<CANCoder> getEncoderArray(){
    List<CANCoder> list = Arrays.asList(encoderArray);
    return list;
  }

  //Resets Single Module Encoder
  public static void resetModuleEncoder(int encoderId){
    encoderArray[encoderId].setPosition(0.0);
    System.out.println("Reseted Module Encoder ID: "+encoderId);
  }

  //Restes all Module Encoders
  public static void resetModuleEncoders(){
    for (CANCoder canCoder : encoderArray) {
      canCoder.setPosition(0.0);
    }
    System.out.println("Rested All Module Encoders");
  }

  //Resets Single Motor Encoder
  public static void resetMotorEncoder(int motorId){
    motorArray[motorId].getSensorCollection().setIntegratedSensorPosition(0,0);
    System.out.println("Reseted Motor Encoder ID: "+motorId);
  }

  //Resets all Motor Encoders
  public static void resetMotorEncoders(){
    for (TalonFX motor : motorArray) {
      motor.getSensorCollection().setIntegratedSensorPosition(0,0);
    }
    System.out.println("Rested All Motor Encoders");
  }

  public static SwerveDriveTrain getInstance(){
    if(instance == null)
      instance = new SwerveDriveTrain();
    return instance;
  }


  @Override
  public void periodic() {
    //Displaying Values on Smart Dashboard
     //For later 
    // for (int i = 0; i < motorArray.length; i++) {
    //   SmartDashboard.putNumber(Constants.MOTORID, motorArray[i].getSensorCollection().getIntegratedSensorPosition());
    // }

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

    SmartDashboard.putNumber("GYRO", gyro.getAngle());
  }
}
