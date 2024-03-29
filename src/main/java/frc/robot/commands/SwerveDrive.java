package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.drivetrain.SwerveDriveTrain;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.SlewRateLimiter;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwerveDrive extends CommandBase {

  private SwerveDriveTrain driveTrain;
  private XboxController controller;

  //Slew Limmiter smooths out controller inputs
  private final SlewRateLimiter xspeedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter yspeedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter rotLimiter = new SlewRateLimiter(3);
  
  public SwerveDrive(SwerveDriveTrain driveTrain, XboxController controller) {
    this.driveTrain = driveTrain;
    this.controller = controller;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {

    //Tennery used to control the deadzone
    //Regular Movement 
    double leftY = -xspeedLimiter.calculate(controller.getY(GenericHID.Hand.kLeft) >= Constants.ROBOT.DEAD_ZONE_MAX.get() || controller.getY(GenericHID.Hand.kLeft) <= Constants.ROBOT.DEAD_ZONE_MIN.get() ? controller.getY(GenericHID.Hand.kLeft) : 0) * 0.8;
    double leftX = -yspeedLimiter.calculate(controller.getX(GenericHID.Hand.kLeft) >= Constants.ROBOT.DEAD_ZONE_MAX.get() || controller.getX(GenericHID.Hand.kLeft) <= Constants.ROBOT.DEAD_ZONE_MIN.get() ? controller.getX(GenericHID.Hand.kLeft) : 0) * 0.8;
    //Rotation
    double rightX = -rotLimiter.calculate(controller.getX(GenericHID.Hand.kRight) >= Constants.ROBOT.DEAD_ZONE_MAX.get() || controller.getX(GenericHID.Hand.kRight) <= Constants.ROBOT.DEAD_ZONE_MIN.get() ? controller.getX(GenericHID.Hand.kRight) : 0) * 0.8;

    if(!Robot.runningCommand)
      driveTrain.drive(leftY * 0.1, leftX * 0.1, rightX * 0.1, true);

    //Displays joystick values on Smart Dashboard
    SmartDashboard.putNumber("Left Y", leftY);
    SmartDashboard.putNumber("Left X", leftX);
    SmartDashboard.putNumber("Right X", rightX);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  //true = Swerve
    //false = Tank
    // boolean driveMode = false;

    // //choose between tank drive and swerve drive
    // if(controller.getBumperPressed(GenericHID.Hand.kRight)){
    //   driveMode = true;      
    //   System.out.println("............Tank Drive");
    // }
    // if(controller.getBumperPressed(GenericHID.Hand.kLeft)){
    //   driveMode = false;
    //   System.out.println("............Swerve Drive");
    // }

    // if(driveMode == true){
      
    //   driveTrain.drive(leftY * 0.2, leftX * 0.2, rightX * 0.2);
    // }
    // else{
      
    //   driveTrain.tankDriveLeft(leftY * 0.2);
    //   driveTrain.tankDriveRight(rightY * 0.2);
    // }
}
