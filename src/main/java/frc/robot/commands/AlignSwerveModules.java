package frc.robot.commands;

import frc.robot.files.RioLog;
import frc.robot.files.RioLog.RioLevel;
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
    RioLog.out.Write("AlignSwerveModules Started");
    endCommand = false;
  }

  @Override
  public void execute() {
    //Loops through Rotation Array 
    for (TalonFX rotationMotor : SwerveDriveTrain.getRotationMotorArray()) {
      RioLog.out.Write("Started to align a new module");
      boolean doneMotor = false;
        //if motor is not alligned goes through while loop to allign it 
        int id = rotationMotor.getDeviceID() - 4;
        RioLog.out.Write("Aligning module ID:" + id);
        while(!doneMotor){
          
            if(rotationMotor.isRevLimitSwitchClosed() == 0){
              SwerveDriveTrain.getEncoderArray().get(id).setPosition(0.0);
              SwerveDriveTrain.setMotorSpeed(id + 4, 0.0);
              RioLog.out.Write("Aligned module ID:" + id);
              //Once all motors are alligned loops ends
              doneMotor = true;
            }
            else{
              SwerveDriveTrain.setMotorSpeed(id+4, 0.2);
            }
        }
    }
        RioLog.out.Write("Done Aligning Motors Ending Command");
        endCommand = true;
  }

  @Override
  public void end(boolean interrupted) {
    RioLog.out.Write("SwerveModules Alignment Ended", RioLevel.SYSTEM);
  }

  @Override
  public boolean isFinished() {
    return endCommand;
  }
}
