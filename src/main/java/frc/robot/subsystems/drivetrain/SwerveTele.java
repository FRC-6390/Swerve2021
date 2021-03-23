package frc.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.SwerveDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class SwerveTele extends SwerveDriveTrain{
    
    static SwerveDriveTrain instance;

    public static SwerveDriveTrain getInstance(){
        return instance == null ? instance = new SwerveTele() : instance;
    }
    
    @Override
    public void drive(double xSpeed, double ySpeed, double rotation){
        SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(xSpeed, ySpeed, rotation, gyro.getRotation2d()));
        SwerveDriveKinematics.normalizeWheelSpeeds(swerveModuleStates, Constants.ROBOT.MAX_SPEED.get());
    
        for (int i = 0; i < swerveModuleStates.length; i++){
          swerveModuleArray[i].setDesiredState(swerveModuleStates[i]);
          SmartDashboard.putNumber(String.valueOf(i), swerveModuleArray[i].getRawAngle());
    
        }
        
      }
}
