package frc.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DesiredPosition {

    public Pose2d currentPos,desiredPos;
    
    public DesiredPosition(Pose2d currentPos, Pose2d desiredPos){
        this.currentPos = currentPos;
        this.desiredPos = desiredPos;

    }

    public Pose2d getDesiredPosition(){
        return desiredPos;
    }

    public Pose2d getCurrentPosition(){
        return currentPos;
    }

    public void updatePosition(Pose2d currentPos){
        this.currentPos = currentPos;
    }

    public double getXspeed(){
        double distance = currentPos.getX() - desiredPos.getX();
        SmartDashboard.putNumber("Distance X", distance);

        if(distance <= 0.1)
        return 0.01;
        else return 0.0;
    }

    public double getYspeed(){
        double distance = currentPos.getY() - desiredPos.getY();
        SmartDashboard.putNumber("Distance Y", distance);

        if(distance <= 0.1)
        return 0.0;
        else return 0.0;
    }

    public double getThetaSpeed(){
        // if(currentPos.getRotation().getDegrees() - desiredPos.getRotation().getDegrees()<=0.1)
        //SmartDashboard.putNumber("Distance Theta", distance);

        // return 0.01;
         return 0.0;
    }
}
