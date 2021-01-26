package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwerveDrive extends CommandBase {

  public SwerveDrive() {

  }

  public final static double length = 30; // Robot Length
  public final static double width = 30; // Robot Width

  public static void drive(double x1, double y1, double x2) {
    double r = Math.sqrt((length * length) + (width * width));
    y1 *= -1;

    double a = x1 - x2 * (length / r);
    double b = x1 + x2 * (length / r);
    double c = y1 - x2 * (width / r);
    double d = y1 + x2 * (width / r);

    // Declaring Drive Motors
    double backRightSpeed = Math.sqrt((a * a) + (d * d));
    double backLeftSpeed = Math.sqrt((a * a) + (c * c));
    double frontRightSpeed = Math.sqrt((b * b) + (d * d));
    double frontLeftSpeed = Math.sqrt((b * b) + (c * c));

    // Declaring Turning Motors
    // Atan2 gives you the theta which is the rotation of the robot
    double backRightAngle = Math.atan2(a, d) / Math.PI;
    double backLeftAngle = Math.atan2(a, c) / Math.PI;
    double frontRightAngle = Math.atan2(b, d) / Math.PI;
    double frontLeftAngle = Math.atan2(b, c) / Math.PI;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
  }

  @Override
  public void end(boolean interrupted) {
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
