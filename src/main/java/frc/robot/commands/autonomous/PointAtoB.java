package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DesiredPosition;
import frc.robot.subsystems.drivetrain.SwerveDriveTrain;

public class PointAtoB extends CommandBase{

    public SwerveDriveTrain drivetrain;
    DesiredPosition desiredPosition;

    public PointAtoB(){

    }

    @Override
  public void initialize() {
    drivetrain = SwerveDriveTrain.getInstance();
    Pose2d pos = SwerveDriveTrain.getRobotPosition();
    desiredPosition = new DesiredPosition(SwerveDriveTrain.getRobotPosition(),new Pose2d(pos.getX()+1, 0, Rotation2d.fromDegrees(0.0)));
  }

  @Override
  public void execute() {
    drivetrain.driveToPosition(desiredPosition);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
