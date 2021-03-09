package frc.robot.commands;

import frc.robot.subsystems.drivetrain.*;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class AlignSwerveModules extends CommandBase {
  boolean endCommand;
  public AlignSwerveModules() {
  }

  @Override
  public void initialize() {
    System.out.println("AlignSwerveModules Started");
    endCommand = false;
  }

  @Override
  public void execute() {
    
    //Loops through Rotation Array 
    for (TalonFX rotationMotor : SwerveDriveTrain.getRotationMotorArray()) {
      System.out.println("Started to align a new module");
      boolean doneMotor = false;
        //if motor is not alligned goes through while loop to allign it 
        while(!doneMotor){
          int id = rotationMotor.getBaseID(); // may need to switch
          System.out.println("Aligning module ID:" + id);
            if(SwerveDriveTrain.getLimitSwitchArray().get(id).get()){
              SwerveDriveTrain.getEncoderArray().get(id).setPosition(0.0);
              SwerveDriveTrain.setMotorSpeed(id, 0.0);
              System.out.println("Aligned module ID:" + id);
              //Once all motors are alligned loops ends
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
    System.out.println("SwerveModules Alignment Ended");
  }

  @Override
  public boolean isFinished() {
    return endCommand;
  }
}
