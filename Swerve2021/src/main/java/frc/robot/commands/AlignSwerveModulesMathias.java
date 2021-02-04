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
    
    for (TalonFX rotationMotor : SwerveDriveTrain.getRotationMotorArray()) {
      boolean doneMotor = false;
        while(!doneMotor){
          int id = rotationMotor.getBaseID(); // may need to switch
            if(SwerveDriveTrain.getLimitSwitchArray().get(id).get()){
              SwerveDriveTrain.getEncoderArray().get(id).setPosition(0.0);
              doneMotor = true;
              SwerveDriveTrain.setMotorSpeed(id, 0.0);
            }
            else{
              SwerveDriveTrain.setMotorSpeed(id, 0.1);

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
