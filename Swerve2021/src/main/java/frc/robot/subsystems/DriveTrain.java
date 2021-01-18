package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  // They are TalonFX speed controllers not TalonSPX
  private TalonFX frontLeftMotor_1 = new TalonFX(Constants.MOTORID.FRONT_LEFT_MOMENTUM.GetID());
  private TalonFX frontLeftMotor_2 = new TalonFX(Constants.MOTORID.FRONT_LEFT_ROTATION.GetID());
  private TalonFX backLeftMotor_1 = new TalonFX(Constants.MOTORID.BACK_LEFT_MOMENTUM.GetID());
  private TalonFX backLeftMotor_2 = new TalonFX(Constants.MOTORID.BACK_LEFT_ROTATION.GetID());
  private TalonFX frontRightMotor_1 = new TalonFX(Constants.MOTORID.FRONT_RIGHT_MOMENTUM.GetID());
  private TalonFX frontRightMotor_2 = new TalonFX(Constants.MOTORID.FRONT_RIGHT_ROTATION.GetID());
  private TalonFX backRightMotor_1 = new TalonFX(Constants.MOTORID.BACK_RIGHT_MOMENTUM.GetID());
  private TalonFX backRightMotor_2 = new TalonFX(Constants.MOTORID.BACK_RIGHT_ROTATION.GetID());


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
    gyro.zeroYaw();
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
    frontLeftMotor_1.getSensorCollection().setIntegratedSensorPosition(0, 0);
    frontLeftMotor_2.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public void resetEncodersBL(){
    //Resets Back Left Module
    backLeftMotor_1.getSensorCollection().setIntegratedSensorPosition(0, 0);
    backLeftMotor_2.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public void resetEncodersFR(){
    //Resets Top Right Module
    frontRightMotor_1.getSensorCollection().setIntegratedSensorPosition(0, 0);
    frontRightMotor_2.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public void resetEncodersBR(){
    //Resets Top Right Module
    backRightMotor_1.getSensorCollection().setIntegratedSensorPosition(0, 0);
    backRightMotor_2.getSensorCollection().setIntegratedSensorPosition(0, 0);
  }

  public void factoryResetDrive(){
    frontLeftMotor_1.configFactoryDefault();
    frontLeftMotor_2.configFactoryDefault();
    backLeftMotor_1.configFactoryDefault();
    backLeftMotor_2.configFactoryDefault();
    frontRightMotor_1.configFactoryDefault();
    frontRightMotor_2.configFactoryDefault();
    backRightMotor_1.configFactoryDefault();
    backRightMotor_2.configFactoryDefault();
  }

  @Override
  public void periodic() {
    //Display Motors on SDB
    SmartDashboard.putNumber("Top Left Motor 1", frontLeftMotor_1.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Top Left Motor 2", frontLeftMotor_2.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Left Motor 1", backLeftMotor_1.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Left Motor 2", backLeftMotor_2.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Top Right Motor 1", frontRightMotor_1.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Top Right Motor 2", frontRightMotor_2.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Right Motor 1", backRightMotor_1.getSensorCollection().getIntegratedSensorPosition());
    SmartDashboard.putNumber("Back Right Motor 2", backRightMotor_2.getSensorCollection().getIntegratedSensorPosition());
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