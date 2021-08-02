// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.SwerveDriveTrain;

public class ResetDevices extends CommandBase {
  /** Creates a new ResetDevices. */
  public ResetDevices() {
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("yeet");
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    for (TalonFX motors : SwerveDriveTrain.getMotorArray()) {
      motors.getSensorCollection().setIntegratedSensorPosition(0.0, 0);
    }

    for (CANCoder encoder : SwerveDriveTrain.getEncoderArray()) {
      encoder.setPosition(0, 0);
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}
