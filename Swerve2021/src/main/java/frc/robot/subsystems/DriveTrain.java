package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  //They are TalonFX speed controllers not TalonSPX
  private TalonFX frontLeftMotor_1 = new TalonFX(Constants.MOTORID.FRONT_LEFT_MOMENTUM.GetID());
  private TalonFX frontLeftMotor_2 = new TalonFX(Constants.MOTORID.FRONT_LEFT_ROTATION.GetID());
  private TalonFX backLeftMotor_1 = new TalonFX(Constants.MOTORID.BACK_LEFT_MOMENTUM.GetID());
  private TalonFX backLeftMotor_2 = new TalonFX(Constants.MOTORID.BACK_LEFT_ROTATION.GetID());
  private TalonFX frontRightMotor_1 = new TalonFX(Constants.MOTORID.FRONT_RIGHT_MOMENTUM.GetID());
  private TalonFX frontRightMotor_2 = new TalonFX(Constants.MOTORID.FRONT_RIGHT_ROTATION.GetID());
  private TalonFX backRightMotor_1 = new TalonFX(Constants.MOTORID.BACK_RIGHT_MOMENTUM.GetID());
  private TalonFX backRightMotor_2 = new TalonFX(Constants.MOTORID.BACK_RIGHT_ROTATION.GetID());

  /*
   * frontLeftMotor_1.configSelectedFeedbackSensor(Constants.
   * FRONT_LEFT_MOTOR_ENCODER_1, 0, 10);
   * frontLeftMotor_2.configSelectedFeedbackSensor(Constants.
   * FRONT_LEFT_MOTOR_ENCODER_2, 0, 10);
   * backLeftMotor_1.configSelectedFeedbackSensor(Constants.
   * BACK_LEFT_MOTOR_ENCODER_1, 0, 10);
   * backLeftMotor_2.configSelectedFeedbackSensor(Constants.
   * BACK_LEFT_MOTOR_ENCODER_2, 0, 10);
   * frontRightMotor_1.configSelectedFeedbackSensor(Constants.
   * FRONT_RIGHT_MOTOR_ENCODER_1, 0, 10);
   * frontRightMotor_2.configSelectedFeedbackSensor(Constants.
   * FRONT_RIGHT_MOTOR_ENCODER_2, 0, 10);
   * backRightMotor_1.configSelectedFeedbackSensor(Constants.
   * BACK_RIGHT_MOTOR_ENCODER_1, 0, 10);
   * backRightMotor_2.configSelectedFeedbackSensor(Constants.
   * BACK_RIGHT_MOTOR_ENCODER_2, 0, 10);
   */

  /*
   * 
   * Front Front Left Right |-----------------------------| | 1 | \Intake/ | 1 | |
   * 2 | | 2 | |-----| |-----| | | | | | |Revolover| | | | | | |-----| |-----| | 2
   * | | 2 | | 1 | /Shooter\ | 1 | |-----------------------------| Bottom Bottom
   * Left Right
   * 
   */

  // GYRO
  private AHRS gyro = new AHRS(Port.kMXP);
  // PDP
  final PowerDistributionPanel PDP = new PowerDistributionPanel(Constants.PDP_DEVICE_ID);

  public void resetGyro() {
    gyro.reset();
    gyro.zeroYaw();
  }

  public DriveTrain() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}