package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.*;

public class AlignSwerveModules extends CommandBase {
  boolean endCommand;

  public AlignSwerveModules( String motor, double speed) {
    
      switch(motor){
        case "frontLeft":
          //centers front left module
          if(DriveTrain.frontLeftLimit.get() == true || DriveTrain.frontLeftEncoder.getPosition() == 0){
            DriveTrain.frontLeftEncoder.setPosition(0.0);
            endCommand = false;
          }
          else{
            DriveTrain.frontLeftRotation.set(ControlMode.PercentOutput, speed);
          }
        break;
  
        case "frontRight":
          //centers front Right module
          if(DriveTrain.frontRightLimit.get() == true || DriveTrain.frontRightEncoder.getPosition() == 0){
            DriveTrain.frontRightEncoder.setPosition(0.0);
            endCommand = false;
          }
          else{
            DriveTrain.frontRightRotation.set(ControlMode.PercentOutput, speed);
          }
        break;
  
        case "backLeft":
          //centers back left module
          if(DriveTrain.backLeftLimit.get() == true || DriveTrain.backLeftEncoder.getPosition() == 0){
            DriveTrain.backLeftEncoder.setPosition(0.0);
            endCommand = false;
          }
          else{
            DriveTrain.backLeftRotation.set(ControlMode.PercentOutput, speed);
          }
        break;
  
        case "backRight":
          //centers back left module
          if(DriveTrain.backRightLimit.get() == true || DriveTrain.backRightEncoder.getPosition() == 0){
            DriveTrain.backRightEncoder.setPosition(0.0);
            endCommand = false;
          }
          else{
            DriveTrain.backRightRotation.set(ControlMode.PercentOutput, speed);
          }
        break;
  
        case "all":
          //centers all module 

          boolean backRight = false;
          boolean backLeft = false;
          boolean frontLeft = false;
          boolean frontRight = false;

          // loops to check if all wheels are centered
          while(backRight == false && backLeft == false && frontRight == false && frontLeft == false){
          if(DriveTrain.backRightLimit.get() == true || DriveTrain.backRightEncoder.getPosition() == 0){
            DriveTrain.backRightEncoder.setPosition(0.0);
            backRight = true;
          }
          else{
            DriveTrain.backRightRotation.set(ControlMode.PercentOutput, 0.3);
            backRight = false;
          }
   
          if(DriveTrain.backLeftLimit.get() == true || DriveTrain.backLeftEncoder.getPosition() == 0){
            DriveTrain.backLeftEncoder.setPosition(0.0);
            backLeft = true;
          }
          else{
            DriveTrain.backLeftRotation.set(ControlMode.PercentOutput, speed);
            backLeft = false;
          }

          if(DriveTrain.frontRightLimit.get() == true || DriveTrain.frontRightEncoder.getPosition() == 0){
            DriveTrain.frontRightEncoder.setPosition(0.0);
            frontRight = true;
          }
          else{
            DriveTrain.frontRightRotation.set(ControlMode.PercentOutput, speed);
            frontRight = false;
          }

          if(DriveTrain.frontLeftLimit.get() == true || DriveTrain.frontLeftEncoder.getPosition() == 0){
            DriveTrain.frontLeftEncoder.setPosition(0.0);
            frontLeft = true;
          }
          else{
            DriveTrain.frontLeftRotation.set(ControlMode.PercentOutput, speed);
            frontLeft = false;
          }
        }
        endCommand = false;
        break;
  
      }
      
    
  }                    
   // The boolean should reset everythime the command is called so no need to reset it

  @Override
  public void initialize() {}

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return endCommand;
  }
}
