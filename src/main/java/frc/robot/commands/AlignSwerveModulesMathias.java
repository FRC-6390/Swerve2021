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
    System.out.println("AlignSwerveModules Started");
    endCommand = false;
  }

  @Override
  public void execute() {
    
    for (TalonFX rotationMotor : SwerveDriveTrain.getRotationMotorArray()) {
      System.out.println("Started to align a new module");
      boolean doneMotor = false;
        while(!doneMotor){
          int id = rotationMotor.getBaseID(); // may need to switch
          System.out.println("Aligning module ID:" + id);
            if(SwerveDriveTrain.getLimitSwitchArray().get(id-1).get()){
              SwerveDriveTrain.getEncoderArray().get(id-1).setPosition(0.0);
              SwerveDriveTrain.setMotorSpeed(id, 0.0);
              System.out.println("Aligned module ID:" + id);
              doneMotor = true;
            }
            else{
              SwerveDriveTrain.setMotorSpeed(id, 0.1);

            }
        }
    }
        System.out.println("Done Aligning Motors Ending Command");
        endCommand = true;
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("AlignSwerveModules Ended");
  }

  @Override
  public boolean isFinished() {
    return endCommand;
  }
}
