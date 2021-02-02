package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

public class RobotContainer {
  public Joystick stick = new Joystick(Constants.JOYSTICK_CONTROLLER);

  public static XboxController xbox = new XboxController(Constants.XBOX_CONTROLLER);
  
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  private void configureButtonBindings() {
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

}
