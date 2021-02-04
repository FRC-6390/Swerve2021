package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

//comment 
public class SwerveDriveTrain extends SubsystemBase {
  
  private static TalonFX frontLeftMomentum,
  frontLeftRotation,
  backLeftMomentum,
  backLeftRotation,
  frontRightMomentum,
  frontRightRotation,
  backRightMomentum,
  backRightRotation;

  private static CANCoder frontRightEncoder,
  frontLeftEncoder,
  backRightEncoder,
  backLeftEncoder;

  private final SwerveModule frontLeftModule,
  frontRightModule,
  backLeftModule,
  backRightModule;

  private static DigitalInput frontRightLimit,
  frontLeftLimit,
  backRightLimit,
  backLeftLimit;

  private final Translation2d frontLeftLocation,
  frontRightLocation,
  backLeftLocation,
  backRightLocation;

  private final SwerveDriveKinematics m_kinematics;

  ChassisSpeeds speeds; 

  

  public static AHRS gyro;

  final PowerDistributionPanel PDP;

  public SwerveDriveTrain() {

    frontLeftMomentum = new TalonFX(Constants.MOTORID.FRONT_LEFT_MOMENTUM.GetID());
    frontLeftRotation = new TalonFX(Constants.MOTORID.FRONT_LEFT_ROTATION.GetID());
    backLeftMomentum = new TalonFX(Constants.MOTORID.BACK_LEFT_MOMENTUM.GetID());
    backLeftRotation = new TalonFX(Constants.MOTORID.BACK_LEFT_ROTATION.GetID());
    frontRightMomentum = new TalonFX(Constants.MOTORID.FRONT_RIGHT_MOMENTUM.GetID());
    frontRightRotation = new TalonFX(Constants.MOTORID.FRONT_RIGHT_ROTATION.GetID());
    backRightMomentum = new TalonFX(Constants.MOTORID.BACK_RIGHT_MOMENTUM.GetID());
    backRightRotation = new TalonFX(Constants.MOTORID.BACK_RIGHT_ROTATION.GetID());

    frontRightEncoder = new CANCoder(Constants.SENSORS.FRONT_RIGHT_ENCODER.GetID());
    frontLeftEncoder = new CANCoder(Constants.SENSORS.FRONT_LEFT_ENCODER.GetID());
    backRightEncoder = new CANCoder(Constants.SENSORS.BACK_RIGHT_ENCODER.GetID());
    backLeftEncoder = new CANCoder(Constants.SENSORS.BACK_LEFT_ENCODER.GetID());

    frontLeftModule = new SwerveModule(1,2);
    frontRightModule = new SwerveModule(3,4);
    backRightModule = new SwerveModule(5,6);
    backLeftModule = new SwerveModule(7,8); 

    frontRightLimit = new DigitalInput(Constants.SENSORS.FRONT_RIGHT_LIMIT.GetID());
    frontLeftLimit = new DigitalInput(Constants.SENSORS.FRONT_LEFT_LIMIT.GetID());
    backRightLimit = new DigitalInput(Constants.SENSORS.BACK_RIGHT_LIMIT.GetID());
    backLeftLimit = new DigitalInput(Constants.SENSORS.BACK_LEFT_LIMIT.GetID());

    frontLeftLocation= new Translation2d(Constants.SWERVE_LOCATION_FROM_CENTER,Constants.SWERVE_LOCATION_FROM_CENTER);
    frontRightLocation = new Translation2d(Constants.SWERVE_LOCATION_FROM_CENTER,-Constants.SWERVE_LOCATION_FROM_CENTER);
    backLeftLocation = new Translation2d(-Constants.SWERVE_LOCATION_FROM_CENTER,Constants.SWERVE_LOCATION_FROM_CENTER);
    backRightLocation = new Translation2d(-Constants.SWERVE_LOCATION_FROM_CENTER,-Constants.SWERVE_LOCATION_FROM_CENTER);

    m_kinematics = new SwerveDriveKinematics(frontLeftLocation,frontRightLocation,backLeftLocation,backRightLocation);

    speeds = new ChassisSpeeds(0,0,0);

    gyro = new AHRS(Port.kMXP);

    PDP = new PowerDistributionPanel(Constants.PDP_DEVICE_ID);
  }

  public void drive(double xSpeed, double ySpeed, double rot, ){
    frontLeftModule.setDesiredState(desiredState);
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
