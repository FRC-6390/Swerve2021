package frc.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class SwerveAuto extends SwerveDriveTrain {
    
    static SwerveDriveTrain instance;

    public static SwerveDriveTrain getInstance(){
        return instance == null ? instance = new SwerveAuto() : instance;
    }
    
    @Override
    public void drive(double xSpeed, double ySpeed, double rotation,double time){
        Timer timer = new Timer();
        timer.start();
        
        while(timer.get() < time){
          System.out.println(timer.get());
          SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotation, gyro.getRotation2d()));
          SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates, Constants.ROBOT.MAX_SPEED.get());
    
          for (int i = 0; i < swerveModuleStates.length; i++){
            swerveModuleArray[i].setDesiredState(swerveModuleStates[i]);
            SmartDashboard.putNumber(String.valueOf(i), swerveModuleArray[i].getRawAngle());
    
          }
       }
       timer.stop();
      }
}
