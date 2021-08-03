package frc.robot.commands.autonomous;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.drivetrain.DesiredPosition;
import frc.robot.subsystems.drivetrain.SwerveDriveTrain;
import frc.robot.subsystems.drivetrain.DesiredPosition.DesiredPositionSpeeds;

public class PointAtoB extends CommandBase {

  public SwerveDriveTrain drivetrain;
  private List<DesiredPosition> desiredList;
  private Iterator<DesiredPosition> desiredIterator;
  private DesiredPosition desiredPosition;
  private boolean done;


  public PointAtoB() {

  }

  @Override
  public void initialize() {
    Robot.runningCommand = true;
    done = false;
    drivetrain = SwerveDriveTrain.getInstance();
    Pose2d pos = SwerveDriveTrain.getRobotPosition();
    desiredList = new ArrayList<>();
     desiredList.add(DesiredPosition.fromCoordinates((pos.getX()+1.0) , 0.0, 180.0, new DesiredPositionSpeeds(0.01,0.01,0.01,0.02),0.1, 2));
     desiredList.add(DesiredPosition.fromCoordinates((pos.getX()-1.0) , 0.0, 0.0, new DesiredPositionSpeeds(0.01,0.01,0.01,0.02),0.1, 2));
     desiredIterator = desiredList.iterator();
     desiredPosition = desiredIterator.next();
  }


  @Override
  public void execute() {
    drivetrain.driveToPosition(desiredPosition);
    if(desiredPosition.atDesiredPosition(SwerveDriveTrain.getRobotPosition())){
      if(desiredIterator.hasNext()){
        desiredPosition = desiredIterator.next();
      }else{
        done = true;
      }
    } 
  }

  @Override
  public void end(boolean interrupted) {
    Robot.runningCommand = false;
  }

  @Override
  public boolean isFinished() {
    return done;
  }

 
}
