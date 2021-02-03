package frc.robot.commands;

import static frc.robot.subsystems.DriveTrain.backLeftRotation;
import static frc.robot.subsystems.DriveTrain.backRightRotation;
import static frc.robot.subsystems.DriveTrain.frontLeftRotation;
import static frc.robot.subsystems.DriveTrain.frontRightRotation;

import static frc.robot.subsystems.DriveTrain.backRightEncoder;
import static frc.robot.subsystems.DriveTrain.backLeftEncoder;
import static frc.robot.subsystems.DriveTrain.frontLeftEncoder;
import static frc.robot.subsystems.DriveTrain.frontRightEncoder;

import static frc.robot.subsystems.DriveTrain.frontLeftLimit;
import static frc.robot.subsystems.DriveTrain.frontRightLimit;
import static frc.robot.subsystems.DriveTrain.backLeftLimit;
import static frc.robot.subsystems.DriveTrain.backRightLimit;



import edu.wpi.first.wpilibj2.command.CommandBase;

public class AlignSwerveModulesMathias extends CommandBase {
  boolean endCommand;
  public AlignSwerveModulesMathias() {
  }

  @Override
  public void initialize() {
    endCommand = false;
  }

  @Override
  public void execute() {
    
    if(backRightLimit.get()){

    }
  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return endCommand;
  }
}
