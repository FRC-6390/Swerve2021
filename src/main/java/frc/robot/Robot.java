package frc.robot;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.*;
import frc.robot.files.RIOLevel;
import frc.robot.files.RIOLog;
import frc.robot.subsystems.drivetrain.*;

public class Robot extends TimedRobot {
  public static SwerveDrive swerve;
  public static RIOLog fileManager;
  public static SwerveDriveTrain driveTrain;

  @Override
  public void robotInit() {
    swerve = new SwerveDrive();
    driveTrain = new SwerveDriveTrain();
    
    new RIOLog("OutputLog");
    RIOLog.Init();
    RIOLog.out.Write("This is a test with no RIOLevel");
    RIOLog.out.Write("This is a test with DEBUG", RIOLevel.DEBUG);
    RIOLog.out.Write("This is a test with INPUT", RIOLevel.INPUT);
    RIOLog.out.Write("This is a test with OUTPUT", RIOLevel.OUPUT);
    RIOLog.out.Write("This is a test with SYSTEM", RIOLevel.SYSTEM);
    RIOLog.out.Write("This is a test with ERROR", RIOLevel.ERROR);
    RIOLog.MoveFileToUsb();
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
    swerve.schedule();

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
