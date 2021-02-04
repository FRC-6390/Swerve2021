package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import frc.robot.Constants;

public class SwerveModule {

    private final TalonFX momentumMotor;
    private final TalonFX rotationMotor;

    
    //TODO check if this actually works with CAN bus
    //if it doesnt try this
    //RotationEncoder.setPosition(2*Math.PI/Constants.SENSORS.ENCODER_RESOLUTION.GetResolution());
    //private final CANCoder RotationEncoder = new CANCoder(0);
    private final Encoder momentumEncoder = new Encoder(0,0);
    private final Encoder rotationEncoder = new Encoder(0,0);

    private final PIDController momentumController = new PIDController(1, 0,0);
    private final ProfiledPIDController rotationController = new ProfiledPIDController(1, 0,0,new TrapezoidProfile.Constraints(Constants.ROBOT_MAX_ANGULAR_SPEED, Constants.ROBOT_MAX_ANGULAR_ACCELERATION));

    private final SimpleMotorFeedforward momentumFeedforward = new SimpleMotorFeedforward(1, 3);
    private final SimpleMotorFeedforward rotationFeedforward = new SimpleMotorFeedforward(1, 0.5);

    public SwerveModule(int momentumMotorID, int rotationMotorID) {

        momentumMotor = new TalonFX(momentumMotorID);
        rotationMotor = new TalonFX(rotationMotorID);
        
        momentumEncoder.setDistancePerPulse(2*Math.PI*Constants.ROBOT_WHEEL_RADIUS/Constants.SENSORS.ENCODER_RESOLUTION.GetResolution());
        rotationEncoder.setDistancePerPulse(2*Math.PI/Constants.SENSORS.ENCODER_RESOLUTION.GetResolution());
        rotationController.enableContinuousInput(-Math.PI, Math.PI);


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
