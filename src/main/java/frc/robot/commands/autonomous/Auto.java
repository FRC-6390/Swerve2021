package frc.robot.commands.autonomous;

import frc.robot.subsystems.drivetrain.SwerveDriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Auto extends CommandBase {
  public SwerveDriveTrain drivetrain;
  public Auto(SwerveDriveTrain driveTrain) {
    this.drivetrain = driveTrain;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    drivetrain.drive(0.01, 0.0, 0.0, 1.0);  //forward
    drivetrain.drive(0.0, 0.01, 0.0, 1.0);  //left
    drivetrain.drive(0.015, 0.0, 0.02, 2.0);//forward + rotate
    drivetrain.drive(0.0, 0.01, 0.0, 1.0);  //left
    drivetrain.drive(-0.01, 0.0, 0.0, 2.0); //backwards
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
