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

    final int kCountsPerRev = 2048;//units per revolution for talon fx
    final double kGearRatio = 8.16;
    final double kWheelRadiusInches = 3;//3 inch radius
    final int k100msPerSecond = 10;

    private final double momentumMath = 2*Math.PI*Constants.ROBOT_WHEEL_RADIUS;
    private final double rotationMath = 2*Math.PI;



    private final TalonFX momentumMotor, rotationMotor;

    private final TalonFXSensorCollection momentumEncoder, rotationEncoder;

    private final CANCoder test;

    private final PIDController momentumController = new PIDController(1, 0,0);
    private final ProfiledPIDController rotationController = new ProfiledPIDController(1, 0,0,new TrapezoidProfile.Constraints(Constants.ROBOT_MAX_ANGULAR_SPEED, Constants.ROBOT_MAX_ANGULAR_ACCELERATION));

    private final SimpleMotorFeedforward momentumFeedforward = new SimpleMotorFeedforward(1, 3);
    private final SimpleMotorFeedforward rotationFeedforward = new SimpleMotorFeedforward(1, 0.5);

    public SwerveModule(int ModuleId) {

        momentumMotor = new TalonFX(ModuleId);
        rotationMotor = new TalonFX(ModuleId+4);

        test = new CANCoder(ModuleId);

        test.get();

        
        
        momentumEncoder = momentumMotor.getSensorCollection();
        rotationEncoder = rotationMotor.getSensorCollection();

        momentumEncoder.setDistancePerPulse(2*Math.PI*Constants.ROBOT_WHEEL_RADIUS/Constants.SENSORS.ENCODER_RESOLUTION.GetResolution());
        rotationEncoder.setDistancePerPulse(2*Math.PI/Constants.SENSORS.ENCODER_RESOLUTION.GetResolution());

        rotationController.enableContinuousInput(-Math.PI, Math.PI);

        //posible soultion
        rotationEncoder.setIntegratedSensorPosition(unitsToDistance(DriveTrain.backLeftRotation.getSelectedSensorPosition()));

    }


    public SwerveModuleState getState(){
        return new SwerveModuleState(momentumEncoder.getRate(), new Rotation2d(rotationEncoder.get()));
    }

    public void setDesiredState(SwerveModuleState desiredState){
        SwerveModuleState state = optimize(desiredState, new Rotation2d(rotationEncoder.get()));

        final double momentumOutput = momentumController.calculate(momentumEncoder.getRate(), state.speedMetersPerSecond);
        final double momentumFeed = momentumFeedforward.calculate(state.speedMetersPerSecond);
        final double rotationOutput = rotationController.calculate(rotationEncoder.get(),state.angle.getRadians());
        final double rotationFeed = rotationFeedforward.calculate(rotationController.getSetpoint().velocity);

        momentumMotor.set(ControlMode.PercentOutput, momentumOutput+momentumFeed);
        rotationMotor.set(ControlMode.PercentOutput, rotationOutput+rotationFeed);
    }

    //converts units to meters
    private int distanceToUnits(double positionMeters){
      double wheelRotations = positionMeters/(2 * Math.PI * Units.inchesToMeters(kWheelRadiusInches));
      double motorRotations = wheelRotations * kGearRatio;
      int sensorCounts = (int)(motorRotations * kCountsPerRev);
      return sensorCounts;
    }

    //converts velocity to native Units
    private int velocityToUnits(double velocityMetersPerSecond){
      double wheelRotationsPerSecond = velocityMetersPerSecond/(2* Math.PI * Units.inchesToMeters(kWheelRadiusInches));
      double motorRotationsPerSecond = wheelRotationsPerSecond * kGearRatio;
      double motorRotationsPer100ms = motorRotationsPerSecond / k100msPerSecond;
      int sensorCountsPer100ms = (int)(motorRotationsPer100ms * kCountsPerRev);
      return sensorCountsPer100ms;
 
 
    }

    //Converts Native Units to Meters
    private double unitsToDistance(double sensorCounts){
      double motorRotations = (double)sensorCounts / kCountsPerRev;
      double wheelRotations = motorRotations / kGearRatio;
      double positionMeters = wheelRotations * (2 * Math.PI * Units.inchesToMeters(kWheelRadiusInches));
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
