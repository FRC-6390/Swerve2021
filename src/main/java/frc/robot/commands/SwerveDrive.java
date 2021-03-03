package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwerveDrive extends CommandBase {



  public SwerveDrive() {

  }
  

  

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {

    //Setting Joystick Values to Doubles
    double RightX = RobotContainer.xbox.getRawAxis(Constants.XBOX.XBOX_RIGHT_AXIS_Y.GetAxis()); 
    double RightY = RobotContainer.xbox.getRawAxis(Constants.XBOX.XBOX_RIGHT_AXIS_X.GetAxis()); 

    double LeftX = RobotContainer.xbox.getRawAxis(Constants.XBOX.XBOX_LEFT_AXIS_Y.GetAxis()); 
    //double LeftY = RobotContainer.xbox.getRawAxis(Constants.XBOX.XBOX_LEFT_AXIS_X.GetAxis()); 


    //Put fancy math here if needed
    //RightX = RightX;
    //RightY = RightY;
    //LeftX  = LeftX;

    //Calls the Drive void and sets the power of the motors using doubles created ^
    Robot.driveTrain.drive(RightX, RightY, LeftX);
    
    //Displays joystick values on Smart Dashboard
    SmartDashboard.putNumber("Right X", RightX);
    SmartDashboard.putNumber("Right Y", RightY);
    SmartDashboard.putNumber("Left X", LeftX);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
