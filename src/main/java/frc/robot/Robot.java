package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.*;
import frc.robot.files.RioLog;
import frc.robot.files.RioLog.RioLevel;
import frc.robot.subsystems.drivetrain.*;
import frc.robot.vission.Camera;

public class Robot extends TimedRobot {
  public static SwerveDrive swerve;
  public static RioLog fileManager;
  public static SwerveDriveTrain driveTrain;
  public static Camera camera;

  @Override
  public void robotInit() {
    // swerve = new SwerveDrive();
    // driveTrain = new SwerveDriveTrain();
    
    new RioLog("OutputLog");
    RioLog.Init();
    RioLog.setLogLevel(RioLevel.ERROR);
    RioLog.out.Write("This is a test with no RIOLevel");
    RioLog.out.Write("This is a test with DEBUG", RioLevel.DEBUG);
    RioLog.out.Write("This is a test with IO", RioLevel.IO); 
    RioLog.out.Write("This is a test with SYSTEM", RioLevel.SYSTEM);
    RioLog.out.Write("This is a test with ERROR", RioLevel.ERROR);
    RioLog.MoveFileToUsb();

    driveTrain = SwerveDriveTrain.getInstance();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {

  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    //swerve.schedule();

  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }
}
