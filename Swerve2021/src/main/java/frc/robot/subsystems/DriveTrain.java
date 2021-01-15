package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  public DriveTrain() {
    final TalonFX frontLeftMotor = new TalonFX(Constants.FRONT_LEFT_MOTOR_ID);
    final TalonFX backLeftMotor = new TalonFX(Constants.BACK_LEFT_MOTOR_ID);
    final TalonFX frontRightMotor = new TalonFX(Constants.FRONT_RIGHT_MOTOR_ID);
    final TalonFX backRightMotor = new TalonFX(Constants.BACK_RIGHT_MOTOR_ID);

    final CANEncoder frontLeftEncoder = new CANSparkMax(Constants.FRONT_LEFT_MOTOR_ID, MotorType.kBrushless)
        .getEncoder();
    final CANEncoder backLeftEncoder = new CANSparkMax(Constants.BACK_LEFT_MOTOR_ID, MotorType.kBrushless).getEncoder();
    final CANEncoder frontRightEncoder = new CANSparkMax(Constants.FRONT_RIGHT_MOTOR_ID, MotorType.kBrushless)
        .getEncoder();
    final CANEncoder backRightEncoder = new CANSparkMax(Constants.BACK_RIGHT_MOTOR_ID, MotorType.kBrushless)
        .getEncoder();

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