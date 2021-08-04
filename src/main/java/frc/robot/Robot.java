package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.SwerveDrive;
import frc.robot.files.RioLog;
import frc.robot.files.RioLog.RioLevel;
import frc.robot.subsystems.drivetrain.SwerveDriveTrain;
import frc.robot.subsystems.vission.Camera;

public class Robot extends TimedRobot {
  private RobotContainer robotContainer;
  private SwerveDrive swerveDrive;
  public static SwerveDriveTrain driveTrain;
  public static Camera camera;
  public static boolean runningCommand;

  @Override
  public void robotInit() {
    robotContainer = new RobotContainer();
    driveTrain = SwerveDriveTrain.getInstance();
    swerveDrive = new SwerveDrive(driveTrain, RobotContainer.xbox);
    runningCommand = false;
    new RioLog("OutputLog");
    RioLog.Init();
    RioLog.setLogLevel(RioLevel.DEBUG);

  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void disabledPeriodic() {
    if(robotContainer.XboxA.get()){
      SwerveDriveTrain.getMotorArray().forEach(motor -> motor.setNeutralMode(NeutralMode.Coast));
    }
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    swerveDrive.schedule();
    SwerveDriveTrain.startUp();
  }

  @Override
  public void teleopPeriodic() {
    // SwerveDriveTrain.setMotorSpeed(7, 0.2);
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

}
