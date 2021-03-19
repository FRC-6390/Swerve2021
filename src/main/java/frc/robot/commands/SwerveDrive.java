package frc.robot.commands;

import frc.robot.Constants;
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
  private final SlewRateLimiter xspeedLimiter = new SlewRateLimiter(6);
  private final SlewRateLimiter yspeedLimiter = new SlewRateLimiter(6);
  private final SlewRateLimiter rotLimiter = new SlewRateLimiter(6);
  
  public SwerveDrive(SwerveDriveTrain driveTrain, XboxController controller) {
    this.driveTrain = driveTrain;
    this.controller = controller;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {

    //Tennery used to control the deadzone     if less than max and more than -min then 0 else move
    //Regular Movement                            {^ deadzone                     }    
    // double leftY = controller.getY(GenericHID.Hand.kLeft) <= Constants.ROBOT.DEAD_ZONE_MAX.get() && controller.getY(GenericHID.Hand.kLeft) >= -Constants.ROBOT.DEAD_ZONE_MIN.get() ? 0.0 : -xspeedLimiter.calculate(controller.getY(GenericHID.Hand.kLeft)) * Constants.ROBOT.MAX_SPEED.get();
    // double leftX = controller.getX(GenericHID.Hand.kLeft) <= Constants.ROBOT.DEAD_ZONE_MAX.get() && controller.getX(GenericHID.Hand.kLeft) >= -Constants.ROBOT.DEAD_ZONE_MIN.get() ? 0.0 : yspeedLimiter.calculate(controller.getX(GenericHID.Hand.kLeft))) * Constants.ROBOT.MAX_SPEED.get();
    // //Rotation
    // double rightX = controller.getX(GenericHID.Hand.kRight) <= Constants.ROBOT.DEAD_ZONE_MAX.get() && controller.getX(GenericHID.Hand.kRight) >= -Constants.ROBOT.DEAD_ZONE_MIN.get() ? 0.0 : -rotLimiter.calculate(controller.getX(GenericHID.Hand.kRight)) * Constants.ROBOT.MAX_SPEED.get();
    double leftX;
    double leftY;
    double rightX;
  
    if(controller.getY(GenericHID.Hand.kLeft) <= Constants.ROBOT.DEAD_ZONE_MAX.get() && controller.getY(GenericHID.Hand.kLeft) >= -Constants.ROBOT.DEAD_ZONE_MIN.get()){
      leftY = -xspeedLimiter.calculate(controller.getY(GenericHID.Hand.kLeft)) * Constants.ROBOT.MAX_SPEED.get();
    }
    else{
      leftY = 0.0;
    }

    if(controller.getX(GenericHID.Hand.kLeft) <= Constants.ROBOT.DEAD_ZONE_MAX.get() && controller.getX(GenericHID.Hand.kLeft) >= -Constants.ROBOT.DEAD_ZONE_MIN.get()){
      leftX = yspeedLimiter.calculate(controller.getX(GenericHID.Hand.kLeft)) * Constants.ROBOT.MAX_SPEED.get();
    }
    else{
      leftX = 0.0;
    }

    if(controller.getX(GenericHID.Hand.kRight) <= Constants.ROBOT.DEAD_ZONE_MAX.get() && controller.getX(GenericHID.Hand.kRight) >= -Constants.ROBOT.DEAD_ZONE_MIN.get()){
      rightX = -rotLimiter.calculate(controller.getX(GenericHID.Hand.kRight)) * Constants.ROBOT.MAX_SPEED.get();
    }
    else{
      rightX = 0.0;
    }

    driveTrain.drive(leftX, leftY, rightX);
    //driveTrain.setMotorSpeed(3, 0.2);
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
}
