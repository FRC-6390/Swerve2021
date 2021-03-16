package frc.robot.subsystems.drivetrain;

import java.util.Arrays;
import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.StatorCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.Constants;
import frc.robot.files.RioLog;
import frc.robot.files.RioLog.RioLevel;

public class SwerveDriveTrain extends SubsystemBase {
  //Initiation
  //DriveTrain Motors
  private static TalonFX frontLeftDrive,
  frontLeftRotation,
  backLeftDrive,
  backLeftRotation,
  frontRightDrive,
  frontRightRotation,
  backRightDrive,
  backRightRotation,
  driveMotorArray[],   //Motor Arrays
  rotationMotorArray[], 
  motorArray[];

  //External Encoders on modules
  private static CANCoder frontRightEncoder,
  frontLeftEncoder,
  backRightEncoder,
  backLeftEncoder,
  encoderArray[]; //External Encoder Array

  //Swerve Modules
  private static SwerveModule frontLeftModule,
  frontRightModule,
  backLeftModule,
  backRightModule,
  swerveModuleArray[];

  //Module Location
  private final Translation2d frontLeftLocation,
  frontRightLocation,
  backLeftLocation,
  backRightLocation;

  private final SwerveDriveKinematics kinematics;

  private static AHRS gyro;
  private static PowerDistributionPanel pdp;

  private static SwerveDriveTrain instance;

/*                                                    
 *                    <Front>
 *         Module 1               Module 2                     
 *        |-----------------------------|                    RULES:
 *        |   4R |   \Intake/    | 1D   |                    1. if you touch you die
 *        | 0D   |               |   5R |                    2. if you mess it up and dont know how to 
 *        |------                |------|                       fix it ask another team programmer
 *        |                             |                    3. Follow Rule # 1 and # 2
 *        |                             |  
 * <left> |         |Revolover|         | <Right>  
 *        |                             |                     D = Drive Motor 
 *        |                             |                     R = Rotation Motor
 *        |------|               |------| 
 *        | 6R   |   |Battery|   |   3D |                     Rotation Motors are on the left side of the module when looking from the outside
 *        |   2D |   /Shooter\   | 7R   |                     Drive Motors are on the right side of the module when looking from the outside
 *        |-----------------------------|                     The Limit Switches are attached to the rotation motor of each module
 *         Module 3               Module 4      
 *                    <Back>     
 *               
 */

  public SwerveDriveTrain() {

    //Declaring Motors
    frontLeftDrive = new TalonFX(Constants.MOTORID.FRONT_LEFT_DRIVE.GetID());
    frontLeftRotation = new TalonFX(Constants.MOTORID.FRONT_LEFT_ROTATION.GetID());
    backLeftDrive = new TalonFX(Constants.MOTORID.BACK_LEFT_DRIVE.GetID());
    backLeftRotation = new TalonFX(Constants.MOTORID.BACK_LEFT_ROTATION.GetID());
    frontRightDrive = new TalonFX(Constants.MOTORID.FRONT_RIGHT_DRIVE.GetID());
    frontRightRotation = new TalonFX(Constants.MOTORID.FRONT_RIGHT_ROTATION.GetID());
    backRightDrive = new TalonFX(Constants.MOTORID.BACK_RIGHT_DRIVE.GetID());
    backRightRotation = new TalonFX(Constants.MOTORID.BACK_RIGHT_ROTATION.GetID());

    //Adding Drive Motors to Array
    driveMotorArray = new TalonFX[]{
      frontLeftDrive,
      frontRightDrive,
      backLeftDrive,
      backRightDrive
    };

    //Adding Rotation Motors to Array
    rotationMotorArray = new TalonFX[]{
      frontLeftRotation,
      frontRightRotation,
      backLeftRotation,
      backRightRotation
    };
    
    //Adding all Drivetrain Motors to Array
    motorArray = new TalonFX[]{
      frontLeftDrive,
      frontRightDrive,
      backLeftDrive,
      backRightDrive,
      frontLeftRotation,
      frontRightRotation,
      backLeftRotation,
      backRightRotation
    };
      
    //Declaring Encoders
    frontRightEncoder = new CANCoder(Constants.SENSORS.FRONT_RIGHT_ENCODER.GetID());
    frontLeftEncoder = new CANCoder(Constants.SENSORS.FRONT_LEFT_ENCODER.GetID());
    backRightEncoder = new CANCoder(Constants.SENSORS.BACK_RIGHT_ENCODER.GetID());
    backLeftEncoder = new CANCoder(Constants.SENSORS.BACK_LEFT_ENCODER.GetID());

    //Adding Encoders to Array
    encoderArray = new CANCoder[]{
      frontLeftEncoder,
      frontRightEncoder,
      backLeftEncoder,
      backRightEncoder
    };

    //Declaring Modules
    frontLeftModule = new SwerveModule(Constants.SWERVE.FRONT_LEFT_MODULE.GetID(), Rotation2d.fromDegrees(Constants.SWERVE.FRONT_LEFT_OFFSET.get()));
    frontRightModule = new SwerveModule(Constants.SWERVE.FRONT_RIGHT_MODULE.GetID(), Rotation2d.fromDegrees(Constants.SWERVE.FRONT_RIGHT_OFFSET.get()));
    backRightModule = new SwerveModule(Constants.SWERVE.BACK_LEFT_MODULE.GetID(), Rotation2d.fromDegrees(Constants.SWERVE.BACK_LEFT_OFFSET.get()));
    backLeftModule = new SwerveModule(Constants.SWERVE.BACK_RIGHT_MODULE.GetID(), Rotation2d.fromDegrees(Constants.SWERVE.BACK_RIGHT_OFFSET.get())); 

    //Module Array
    swerveModuleArray = new SwerveModule[]{
      frontLeftModule,
      frontRightModule,
      backLeftModule,
      backRightModule
    };

    //Declaring The Modules Location From the Center of The Bot
    frontLeftLocation= new Translation2d(Constants.SWERVE.LOCATION_FROM_CENTER.get(),Constants.SWERVE.LOCATION_FROM_CENTER.get());
    frontRightLocation = new Translation2d(Constants.SWERVE.LOCATION_FROM_CENTER.get(),-Constants.SWERVE.LOCATION_FROM_CENTER.get());
    backLeftLocation = new Translation2d(-Constants.SWERVE.LOCATION_FROM_CENTER.get(),Constants.SWERVE.LOCATION_FROM_CENTER.get());
    backRightLocation = new Translation2d(-Constants.SWERVE.LOCATION_FROM_CENTER.get(),-Constants.SWERVE.LOCATION_FROM_CENTER.get());

    //SAFETY FEATURES FOR MOTORS
    //Falcons go up to 40Amps
    //Supply is for motor controller Stator is for motor keeping number low for now
    //Drive Motors                                                               enabled | Limit(amp) | Trigger Threshold(amp) | Trigger Threshold Time(s)
    // for (int i = 0; i < motorArray.length; i++) {
    //   motorArray[i].configStatorCurrentLimit(new StatorCurrentLimitConfiguration(true,      30,                35,                1.0));
    //   motorArray[i].configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true,      20,                25,                0.5));
    // }
      for (int i = 0; i < rotationMotorArray.length; i++) {
        rotationMotorArray[i].configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
      }
    //Swevre Kinematics
    kinematics = new SwerveDriveKinematics(frontLeftLocation,frontRightLocation,backLeftLocation,backRightLocation);
    //Gyro
    gyro = new AHRS(SerialPort.Port.kUSB);
    //Power Distribution Panel
    pdp = new PowerDistributionPanel(Constants.PDP_DEVICE_ID);

  }

  public static void startup(){
    gyro.reset();
    resetModuleEncoders();
  }

  //Used for actualy moving the Robot
  public void drive(double xSpeed, double ySpeed, double rotation){
    SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotation, Rotation2d.fromDegrees(-gyro.getAngle())));
    SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates, Constants.ROBOT.MAX_SPEED.get());

    for (int i = 0; i < swerveModuleStates.length; i++){
      swerveModuleArray[i].setDesiredState(swerveModuleStates[i]);
      SmartDashboard.putNumber(String.valueOf(i), swerveModuleArray[i].getRawAngle());

    }
    
  }

  //Sets Motor Speeds
  public static void setMotorSpeed(int motorId, double speed){
    motorArray[motorId].set(ControlMode.PercentOutput, speed);
  }

  //Moving all Module Motors
  public static void setModuleSpeed(int moduleId, double rotationSpeed, double driveSpeed){
    motorArray[moduleId].set(ControlMode.PercentOutput, driveSpeed);
    motorArray[moduleId+4].set(ControlMode.PercentOutput, rotationSpeed);
    //                 ^ bad way of doing this
  }


  //Gets For the Array
  public static List<TalonFX> getMotorArray(){
    List<TalonFX> list = Arrays.asList(motorArray);
    return list;
  }

  public static List<TalonFX> getDriveMotorArray(){
    List<TalonFX> list = Arrays.asList(driveMotorArray);
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
    RioLog.out.Write("Reseted Module Encoder ID: "+encoderId, RioLevel.SYSTEM);
  }

  //Restes all Module Encoders
  public static void resetModuleEncoders(){
    for (CANCoder canCoder : encoderArray)
      canCoder.setPosition(0.0, 0);
    
      RioLog.out.Write("Rested All Module Encoders", RioLevel.SYSTEM);
  }

  //Resets Single Motor Encoder
  public static void resetMotorEncoder(int motorId){
    motorArray[motorId].getSensorCollection().setIntegratedSensorPosition(0,0);
    RioLog.out.Write("Reseted Motor Encoder ID: "+motorId, RioLevel.SYSTEM);
  }

  //Resets all Motor Encoders
  public static void resetMotorEncoders(){
    for (TalonFX motor : motorArray) 
      motor.getSensorCollection().setIntegratedSensorPosition(0,0);
      RioLog.out.Write("Rested All Motor Encoders", RioLevel.SYSTEM);
  }

  public static List<SwerveModule> getModuleArray(){
    List<SwerveModule> list = Arrays.asList(swerveModuleArray);
    return list;
  }

  public static SwerveDriveTrain getInstance(){
    return instance == null ? instance = new SwerveDriveTrain() : instance;
  }

  public static AHRS getGyro(){
    return gyro;
  }

  public static PowerDistributionPanel getPDP(){
    return pdp;
  }


  @Override
  public void periodic() {
    //Displays Motor Values to The Smart Dashboard by looping through motor id's
    for (int i = 0; i < motorArray.length; i++) 
      SmartDashboard.putNumber(Constants.MOTORID.MOTOR_NAME.GetName()[i], motorArray[i].getSensorCollection().getIntegratedSensorPosition());
 

      
    SmartDashboard.putNumber("GYRO", gyro.getAngle());
  }
}
