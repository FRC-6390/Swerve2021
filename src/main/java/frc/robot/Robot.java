package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.SwerveDrive;
import frc.robot.files.RioLog;
import frc.robot.files.RioLog.RioLevel;
import frc.robot.subsystems.drivetrain.SwerveDriveTrain;
import frc.robot.vission.Camera;

public class Robot extends TimedRobot {
  private RobotContainer robotContainer;
  private SwerveDrive swerveDrive;
  public static SwerveDriveTrain driveTrain;
  public static Camera camera;

  @Override
  public void robotInit() {
    robotContainer = new RobotContainer();
    driveTrain = SwerveDriveTrain.getInstance();
    swerveDrive = new SwerveDrive(driveTrain, RobotContainer.xbox);

    new RioLog("OutputLog");
    RioLog.Init();
    RioLog.setLogLevel(RioLevel.DEBUG);    
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    SwerveDriveTrain.startup();
    SwerveDriveTrain.setOffset();
    swerveDrive.schedule();
  }

  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {}
}
