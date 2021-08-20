package frc.robot.commands.autonomous;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.subsystems.drivetrain.DesiredPosition;
import frc.robot.subsystems.drivetrain.SwerveDriveTrain;
import frc.robot.subsystems.drivetrain.DesiredPosition.DesiredSettings;

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
    done = false;
    Robot.runningCommand = true;
    drivetrain = SwerveDriveTrain.getInstance();
    desiredList = new ArrayList<>(){{
      add(DesiredPosition.fromCords(0.5 , 0.5, 10.0));
    }};
    //add for loop for x, y, theta lists for cords
    desiredIterator = desiredList.iterator();
    desiredPosition = desiredIterator.next();
  }


  @Override
  public void execute() {
    drivetrain.driveToPosition(desiredPosition);
    if(desiredPosition.atDesiredPosition()){
      if(desiredIterator.hasNext())
        desiredPosition = desiredIterator.next();
      else
        done = true;
      
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