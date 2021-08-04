package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.RemoteSensorSource;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.util.Units;
import frc.robot.Constants;

public class SwerveModule {

    private final TalonFX driveMotor, rotationMotor;
    private final CANCoder moduleEncoder;
    private CANCoderConfiguration moduleEncoderConfiguration;
    private TalonFXConfiguration rotationConfiguration, driveConfiguration;
    private int ModuleId;
    
    public SwerveModule(int id, Rotation2d offset) {
        this.ModuleId = id;
        //Motors
        driveMotor = new TalonFX(ModuleId);
        rotationMotor = new TalonFX(ModuleId+4);

        rotationMotor.configFactoryDefault();
        driveMotor.configFactoryDefault();
        //Module Encoders
        moduleEncoder = new CANCoder(ModuleId);
        moduleEncoder.configFactoryDefault();
        
        rotationConfiguration = new TalonFXConfiguration(){{
          slot0.kP = Constants.SWERVE.P_ROTATION.get();
          slot0.kI = Constants.SWERVE.I_ROTATION.get();
          slot0.kD = Constants.SWERVE.D_ROTATION.get();
          remoteFilter0.remoteSensorDeviceID = ModuleId;
          remoteFilter0.remoteSensorSource = RemoteSensorSource.CANCoder;
          primaryPID.selectedFeedbackSensor = FeedbackDevice.RemoteSensor0;
        }};
        
        rotationMotor.configAllSettings(rotationConfiguration);
        rotationMotor.setNeutralMode(NeutralMode.Brake);

        driveConfiguration = new TalonFXConfiguration(){{
          slot0.kP = Constants.SWERVE.P_DRIVE.get();
          slot0.kI = Constants.SWERVE.I_DRIVE.get();
          slot0.kD = Constants.SWERVE.D_DRIVE.get();
          slot0.kF = Constants.SWERVE.F_DRIVE.get();
        }};
        driveMotor.configAllSettings(driveConfiguration);
        driveMotor.setNeutralMode(NeutralMode.Brake);

        moduleEncoderConfiguration = new CANCoderConfiguration(){{
          magnetOffsetDegrees = offset.getDegrees();
        }};
        
        moduleEncoder.configAllSettings(moduleEncoderConfiguration);
      }

      
    public void setDesiredState(SwerveModuleState desiredState){

        Rotation2d currentRotation = getAngle();
        SwerveModuleState state = SwerveModuleState.optimize(desiredState, currentRotation);

        Rotation2d rotationDelta = state.angle.minus(currentRotation);

        double deltaTicks = (rotationDelta.getDegrees() / 360) * Constants.SENSORS.EXTERNAL_ENCODER_RESOLUTION.GetResolution();
        double currentTicks = moduleEncoder.getPosition() / moduleEncoder.configGetFeedbackCoefficient();
        double desiredTicks = currentTicks + deltaTicks;

        rotationMotor.set(ControlMode.Position, desiredTicks);

        double feetPerSecond = Units.metersToFeet(state.speedMetersPerSecond);
        
        driveMotor.set(ControlMode.PercentOutput, feetPerSecond / Constants.ROBOT.MAX_SPEED.get());

      }
  
      public SwerveModuleState getState() {
        return new SwerveModuleState(nativeUnitsToDistanceMeters(driveMotor.getSelectedSensorVelocity()*10), getAngle());
      }

    public double getRawAngle() { 
      return  moduleEncoder.getAbsolutePosition();
    }

    public Rotation2d getAngle() {
      return Rotation2d.fromDegrees(moduleEncoder.getAbsolutePosition());
    }

    public double getWheelMovedMeters(){
      return nativeUnitsToDistanceMeters(driveMotor.getSelectedSensorPosition());
    }

    private double nativeUnitsToDistanceMeters(double sensorCounts){
      double motorRotations = (double)sensorCounts / Constants.SENSORS.INTERNAL_ENCODER_RESOLUTION.GetResolution();
      double wheelRotations = motorRotations / Constants.SWERVE.DRIVE_GEAR_RATIO.get();
      double positionMeters = wheelRotations * (2 * Math.PI * Units.inchesToMeters(2));
      return positionMeters;
    } 
}

