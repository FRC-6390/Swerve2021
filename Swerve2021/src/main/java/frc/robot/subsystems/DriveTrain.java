package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.SensorCollection;
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


  /*
  * Front Front          Left Right 
  * |-----------------------------| 
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

  }

  public void resetGyro() {
    gyro.reset();
  }

  public void zeroDriveEncoders(){
    resetEncodersFL();
    resetEncodersBL();
    resetEncodersFR();
    resetEncodersBR();
    System.out.println("Drive Train Encoders Reset");
  }

  public void resetEncodersFL(){
    //Resets Top Left Module
    frontLeftMomentum.getSensorCollection().setIntegratedSensorPosition(0, 0);
    frontLeftRotation.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public void resetEncodersBL(){
    //Resets Back Left Module
    backLeftMomentum.getSensorCollection().setIntegratedSensorPosition(0, 0);
    backLeftRotation.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public void resetEncodersFR(){
    //Resets Top Right Module
    frontRightMomentum.getSensorCollection().setIntegratedSensorPosition(0, 0);
    frontRightRotation.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public void resetEncodersBR(){
    //Resets Top Right Module
    backRightMomentum.getSensorCollection().setIntegratedSensorPosition(0, 0);
    backRightRotation.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public void factoryResetDrive(){
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
    //Display Motors on SDB
    SmartDashboard.putNumber("Top Left Motor 1", frontLeftMomentum.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Top Left Motor 2", frontLeftRotation.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Left Motor 1", backLeftMomentum.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Left Motor 2", backLeftRotation.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Top Right Motor 1", frontRightMomentum.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Top Right Motor 2", frontRightRotation.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Right Motor 1", backRightMomentum.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Right Motor 2", backRightRotation.getSensorCollection().getIntegratedSensorPosition());
  }

  public void resetRobot(){
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