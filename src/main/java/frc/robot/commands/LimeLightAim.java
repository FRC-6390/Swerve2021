package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.subsystems.vission.LimeLight;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class LimeLightAim extends CommandBase {

  private LimeLight limelight;
  private XboxController controller;

  public boolean held, done;
  private NetworkTable table;
  public NetworkTableEntry pipeline, ledMode, camMode, txvalue, tyvalue, tvvalue, tVertvalue, tHorvalue;
  public double kP, tx, ty, tv, heading_error, distance_error, rotation_adjust, distance_adjust, rotation, kpAim, kpDistance;

  public LimeLightAim(LimeLight limelight, XboxController controller) {
    this.limelight = limelight;
    this.controller = controller;
  }

  public LimeLightAim(boolean isHeld) 
  {
    held = isHeld;
    table = NetworkTableInstance.getDefault().getTable("limelight");
    ledMode = table.getEntry("ledMode");
    camMode = table.getEntry("camMode");
    txvalue = table.getEntry("tx");
    tyvalue = table.getEntry("ty");
    tvvalue = table.getEntry("tv");
    tVertvalue = table.getEntry("thor");
    tHorvalue = table.getEntry("tvert");
  }

  @Override
  public void initialize() {
    if (held == true)
    {
        kP = 0.014;
        kpDistance = 0.08;
        rotation_adjust = 0.0;
        tx = txvalue.getDouble(0.0);
        ty = tyvalue.getDouble(0.0);
        camMode.setDouble(0.0);
        done = false;
    }
    else if(held == false)
    {
        camMode.setDouble(1.0);
        done = true;
    }
  }

  @Override
  public void execute() {
      tx = txvalue.getDouble(0.0);
      ty = tyvalue.getDouble(0.0);
      tv = tvvalue.getDouble(0.0);
      heading_error = -tx;
      rotation = kP * -tx;
      //distance_error = ty;
      //distance_adjust = kpDistance * distance_error;
      Robot.driveTrain.drive(0, 0, rotation);
      //Robot.driveTrain.drive(0, 0, -rotation);
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return done;
  }
}
