package frc.robot;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.SwerveDrive;
import frc.robot.files.RioLog;
import frc.robot.files.RioLog.RioLevel;
import frc.robot.subsystems.drivetrain.SwerveDriveTrain;
import frc.robot.subsystems.vission.Camera;

public class Robot extends TimedRobot {
  private RobotContainer robotContainer;
  private SwerveDrive swerveDrive;
  private Command autonomousCommand;
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
    autonomousCommand = robotContainer.getAutonomousCommand();

    // schedule the autonomous command
    if(autonomousCommand != null) {
      autonomousCommand.schedule();
    }
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }
    swerveDrive.schedule();
    SwerveDriveTrain.startUp();
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }

  @Override
  public void testPeriodic() {
  }

}
