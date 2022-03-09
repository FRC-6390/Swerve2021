package frc.robot.commands.autonomous;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.files.models.JsonManager;
import frc.robot.subsystems.drivetrain.DesiredPosition;
import frc.robot.subsystems.drivetrain.SwerveDriveTrain;
import frc.robot.subsystems.drivetrain.DesiredPosition.DesiredSettings;

public class PointAtoB extends CommandBase {

  public SwerveDriveTrain drivetrain;
  public RobotContainer robotContainer;
  public JsonManager jsonManager;
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
    robotContainer = new RobotContainer();
    jsonManager = new JsonManager();

    String autoSelected = SmartDashboard.getString("Auto Selector","auto1");
    System.out.println("AUTO_________________: " + autoSelected);

    try {
      jsonManager.readJson(autoSelected);
    } catch (Exception e) {
      e.printStackTrace();
    }

    //iterate over json here 
    desiredList = new ArrayList<>();
    for(int z = 0; z < jsonManager.posList.size(); z++){
      desiredList.add(DesiredPosition.fromCords(jsonManager.xList.get(z), jsonManager.yList.get(z), jsonManager.thetaList.get(z)));
    }
    System.out.println(desiredList);

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

  // public static void main(String[] args) throws Exception {
  //   PointAtoB point = new PointAtoB();
  //   point.initialize();
  // }
 
}



