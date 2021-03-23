package frc.robot.commands;

import frc.robot.subsystems.drivetrain.SwerveDriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class CenterRobot extends CommandBase {
  public SwerveDriveTrain drivetrain;
  public CenterRobot() {
  }

  @Override
  public void initialize() {
    drivetrain = SwerveDriveTrain.getInstance();
  }

  @Override
  public void execute() {
    drivetrain.rotate(0.01, 0.0);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
