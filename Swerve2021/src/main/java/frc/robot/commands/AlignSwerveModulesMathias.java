package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.SwerveDriveTrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.Talon;
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
    
    for (TalonFX rotationMotor : SwerveDriveTrain.rotationMotorArray) {
      boolean doneMotor = false;
        while(!doneMotor){
          int id = rotationMotor.getBaseID(); // may need to switch
            if(SwerveDriveTrain.limitSwitchArray.get(id).get()){
              SwerveDriveTrain.encoderArray.get(id).setPosition(0.0);
              doneMotor = true;
              rotationMotor.set(ControlMode.PercentOutput, 0.0);
            }
            else{
            rotationMotor.set(ControlMode.PercentOutput, 0.1);
            }
        }
      
    }



  }

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return endCommand;
  }
}
