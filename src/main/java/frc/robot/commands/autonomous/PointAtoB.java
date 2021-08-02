package frc.robot.commands.autonomous;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.drivetrain.DesiredPosition;
import frc.robot.subsystems.drivetrain.SwerveDriveTrain;

public class PointAtoB extends CommandBase {

  public SwerveDriveTrain drivetrain;
  private List<DesiredPosition> desiredPositions;

  public PointAtoB() {

  }

  @Override
  public void initialize() {
    drivetrain = SwerveDriveTrain.getInstance();
    Pose2d pos = SwerveDriveTrain.getRobotPosition();
    desiredPositions = new ArrayList<>();
     desiredPositions.add(DesiredPosition.fromCoordinates((pos.getX()+1.0) , 0.0, 0.0));
  }

  @Override
  public void execute() {
    DesiredPosition desiredPosition;
    for (int i = 0; i < desiredPositions.size(); i++) {
      desiredPosition = desiredPositions.get(i);
      drivetrain.driveToPosition(desiredPosition);
      if(!desiredPosition.atDesiredPosition(SwerveDriveTrain.getRobotPosition()))
        i--;
    }
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return true;
  }
}
