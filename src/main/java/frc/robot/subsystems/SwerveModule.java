package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.util.Units;
import frc.robot.Constants;

public class SwerveModule {

    final int k100msPerSecond = 10;
    private final TalonFXSensorCollection momentumEncoder, rotationEncoder;

    private final PIDController momentumController = new PIDController(1, 0,0);

    private final SimpleMotorFeedforward momentumFeedforward = new SimpleMotorFeedforward(1, 3);
    private final SimpleMotorFeedforward rotationFeedforward = new SimpleMotorFeedforward(1, 0.5);

    public SwerveModule(int ModuleId) {

        momentumMotor = new TalonFX(ModuleId);
        rotationMotor = new TalonFX(ModuleId+4);
        
        momentumEncoder = momentumMotor.getSensorCollection();
        rotationEncoder = rotationMotor.getSensorCollection();

        rotationController.enableContinuousInput(-Math.PI, Math.PI);
        
    }


    public SwerveModuleState getState(){
        return new SwerveModuleState(velocityToUnits(unitsToDistance(momentumEncoder.getIntegratedSensorPosition())) , new Rotation2d(rotationEncoder.getIntegratedSensorPosition()));
    }

    public void setDesiredState(SwerveModuleState desiredState){
        SwerveModuleState state = optimize(desiredState, new Rotation2d(rotationEncoder.getIntegratedSensorPosition()));

        final double momentumOutput = momentumController.calculate(velocityToUnits(unitsToDistance(momentumEncoder.getIntegratedSensorPosition())), state.speedMetersPerSecond);
        final double momentumFeed = momentumFeedforward.calculate(state.speedMetersPerSecond);
        final double rotationOutput = rotationController.calculate(rotationEncoder.getIntegratedSensorPosition(),state.angle.getRadians());
        final double rotationFeed = rotationFeedforward.calculate(rotationController.getSetpoint().velocity);

        momentumMotor.set(ControlMode.PercentOutput, momentumOutput+momentumFeed);
        rotationMotor.set(ControlMode.PercentOutput, rotationOutput+rotationFeed);
    }

    // //converts units to meters
    // private int distanceToUnits(double positionMeters){
    //   double wheelRotations = positionMeters/(2 * Math.PI * Units.inchesToMeters(kWheelRadiusInches));
    //   double motorRotations = wheelRotations * kGearRatio;
    //   int sensorCounts = (int)(motorRotations * kCountsPerRev);
    //   return sensorCounts;
    // }

    //converts velocity to native Units
    int motorRotationsPer100ms = 0;
    private int velocityToUnits(double velocityMetersPerSecond){
      double wheelRotationsPerSecond = velocityMetersPerSecond/(2* Math.PI * Units.inchesToMeters(kWheelRadiusInches));
      double motorRotationsPerSecond = wheelRotationsPerSecond * Constants.SWERVE.GEAR_RATIO.get();
      double motorRotationsPer100ms = motorRotationsPerSecond / k100msPerSecond;
      int sensorCountsPer100ms = (int)(motorRotationsPer100ms * Constants.SENSORS.INTERNAL_ENCODER_RESOLUTION.GetResolution());
      return sensorCountsPer100ms;
 
 
    }

    //Converts Native Units to Meters
    private double unitsToDistance(double sensorCounts){
      double motorRotations = (double)sensorCounts / Constants.SENSORS.INTERNAL_ENCODER_RESOLUTION.GetResolution();
      double wheelRotations = motorRotations / Constants.SWERVE.GEAR_RATIO.get();
      double positionMeters = wheelRotations * (2 * Math.PI * Constants.ROBOT.WHEEL_RADIUS.get());
      return positionMeters;
    }
    

    private static SwerveModuleState optimize(
      SwerveModuleState desiredState, Rotation2d currentAngle) {
        var delta = desiredState.angle.minus(currentAngle);
          if (Math.abs(delta.getDegrees()) > 90.0) {
            return new SwerveModuleState(
            -desiredState.speedMetersPerSecond,
            desiredState.angle.rotateBy(Rotation2d.fromDegrees(180.0)));
          } else {
              return new SwerveModuleState(desiredState.speedMetersPerSecond, desiredState.angle);
            }
  }
}
