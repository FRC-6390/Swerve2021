package frc.robot.commands.autonomous;

import frc.robot.subsystems.drivetrain.SwerveDriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Auto extends CommandBase {
  public SwerveDriveTrain drivetrain;
  public Auto() {
  }

  @Override
  public void initialize() {
    drivetrain = SwerveDriveTrain.getInstance();
  }

  @Override
  public void execute() {
    drivetrain.autoDrive(1.0, 0.01, 0.0, 0.0);  //forward
    drivetrain.autoDrive(1.0, 0.0, 0.01, 0.0);  //left
    drivetrain.autoDrive(2.0, 0.015, 0.0, 0.02);//forward + rotate
    drivetrain.autoDrive(1.0, 0.0, 0.01, 0.0);  //left
    drivetrain.autoDrive(2.0, -0.01, 0.0, 0.0); //backwards
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
