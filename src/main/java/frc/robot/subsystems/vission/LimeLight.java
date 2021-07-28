package frc.robot.subsystems.vission;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class LimeLight extends SubsystemBase{
    public boolean held, done;
    private NetworkTable table;
    public NetworkTableEntry pipeline, ledMode, camMode, txvalue, tyvalue;
    public double kP, tx, ty, heading_error, distance_error, rotation_adjust, distance_adjust, rotation, kpAim, kpDistance;

    public LimeLight(boolean isHeld) {
        held = isHeld;
        table = NetworkTableInstance.getDefault().getTable("limelight");
        ledMode = table.getEntry("ledMode");
        camMode = table.getEntry("camMode");
        txvalue = table.getEntry("tx");
        tyvalue = table.getEntry("ty");
        pipeline = table.getEntry("pipeline");
      }
      public void ChangePipeline(int Pipeline)
      {
        pipeline.setDouble(Pipeline);
      }
    
      @Override
      public void periodic() 
      {
        
      }
}
