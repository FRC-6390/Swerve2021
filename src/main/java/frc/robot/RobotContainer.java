package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.AlignSwerveModules;
import frc.robot.commands.LimeLightAim;
import frc.robot.commands.ResetDevices;
import frc.robot.commands.autonomous.Auto;
import frc.robot.commands.autonomous.PointAtoB;

public class RobotContainer {
  //Declaring Controllers for both XBOX and LOGITECH
  public Joystick stick = new Joystick(Constants.JOYSTICK.JOYSTICK_PORT_ID.Get());
  public static XboxController xbox = new XboxController(Constants.XBOX.XBOX_PORT_ID.Get());

  //LOGITECH CONTROLER
  public JoystickButton 
  // Buttons
  button1 = new JoystickButton(stick, Constants.JOYSTICK.JOYSTICK_BUTTON_1.GetButton()),
  button2 = new JoystickButton(stick, Constants.JOYSTICK.JOYSTICK_BUTTON_2.GetButton()),
  button3 = new JoystickButton(stick, Constants.JOYSTICK.JOYSTICK_BUTTON_3.GetButton()),
  button4 = new JoystickButton(stick, Constants.JOYSTICK.JOYSTICK_BUTTON_4.GetButton()),
  button5 = new JoystickButton(stick, Constants.JOYSTICK.JOYSTICK_BUTTON_5.GetButton()),
  button6 = new JoystickButton(stick, Constants.JOYSTICK.JOYSTICK_BUTTON_6.GetButton()),
  button7 = new JoystickButton(stick, Constants.JOYSTICK.JOYSTICK_BUTTON_7.GetButton()),
  button8 = new JoystickButton(stick, Constants.JOYSTICK.JOYSTICK_BUTTON_8.GetButton()),
  button9 = new JoystickButton(stick, Constants.JOYSTICK.JOYSTICK_BUTTON_9.GetButton()),
  button10 = new JoystickButton(stick, Constants.JOYSTICK.JOYSTICK_BUTTON_10.GetButton()),
  button11 = new JoystickButton(stick, Constants.JOYSTICK.JOYSTICK_BUTTON_11.GetButton()),
  button12 = new JoystickButton(stick, Constants.JOYSTICK.JOYSTICK_BUTTON_12.GetButton()),
  // POV
  POV_T = new JoystickButton(xbox, Constants.JOYSTICK.JOYSTICK_POV_TOP.GetPOV()),
  POV_TR = new JoystickButton(xbox, Constants.JOYSTICK.JOYSTICK_POV_TOP_RIGHT.GetPOV()),
  POV_TL = new JoystickButton(xbox, Constants.JOYSTICK.JOYSTICK_POV_TOP_LEFT.GetPOV()),
  POV_B = new JoystickButton(xbox, Constants.JOYSTICK.JOYSTICK_POV_BOTTOM.GetPOV()),
  POV_BR = new JoystickButton(xbox, Constants.JOYSTICK.JOYSTICK_POV_BOTTOM_RIGHT.GetPOV()),
  POV_BL = new JoystickButton(xbox, Constants.JOYSTICK.JOYSTICK_POV_BOTTOM_LEFT.GetPOV()),
  POV_R = new JoystickButton(xbox, Constants.JOYSTICK.JOYSTICK_POV_BOTTOM_RIGHT.GetPOV()),
  POV_L = new JoystickButton(xbox, Constants.JOYSTICK.JOYSTICK_POV_LEFT.GetPOV());
  
  //XBOX CONTROLER
  public JoystickButton 
  // Buttons
  XboxA = new JoystickButton(xbox, Constants.XBOX.XBOX_A.GetButton()),
  XboxB = new JoystickButton(xbox, Constants.XBOX.XBOX_B.GetButton()),
  XboxX = new JoystickButton(xbox, Constants.XBOX.XBOX_X.GetButton()),
  XboxY = new JoystickButton(xbox, Constants.XBOX.XBOX_Y.GetButton()),
  XboxBumperLeft = new JoystickButton(xbox, Constants.XBOX.XBOX_BUMBER_LEFT.GetButton()),
  XboxBumperRight = new JoystickButton(xbox, Constants.XBOX.XBOX_BUMBER_RIGHT.GetButton()),
  XboxBack = new JoystickButton(xbox, Constants.XBOX.XBOX_BACK.GetButton()),
  XboxStart = new JoystickButton(xbox, Constants.XBOX.XBOX_START.GetButton()),
  XboxLeftStickIn = new JoystickButton(xbox, Constants.XBOX.XBOX_LEFT_JOYSTICK_IN.GetButton()),
  XboxRightStickIn = new JoystickButton(xbox, Constants.XBOX.XBOX_RIGHT_JOYSTICK_IN.GetButton()),
  // POV
  XboxPOV_T = new JoystickButton(xbox, Constants.XBOX.XBOX_POV_TOP.GetPOV()),
  XboxPOV_TR = new JoystickButton(xbox, Constants.XBOX.XBOX_POV_TOP_RIGHT.GetPOV()),
  XboxPOV_TL = new JoystickButton(xbox, Constants.XBOX.XBOX_POV_TOP_LEFT.GetPOV()),
  XboxPOV_B = new JoystickButton(xbox, Constants.XBOX.XBOX_POV_BOTTOM.GetPOV()),
  XboxPOV_BR = new JoystickButton(xbox, Constants.XBOX.XBOX_POV_BOTTOM_RIGHT.GetPOV()),
  XboxPOV_BL = new JoystickButton(xbox, Constants.XBOX.XBOX_POV_BOTTOM_LEFT.GetPOV()),
  XboxPOV_R = new JoystickButton(xbox, Constants.XBOX.XBOX_POV_BOTTOM_RIGHT.GetPOV()),
  XboxPOV_L = new JoystickButton(xbox, Constants.XBOX.XBOX_POV_LEFT.GetPOV());

  public RobotContainer() {
    configureButtonBindings();
    configureCommands();
  }

  private void configureCommands() {
    
  }

  private void configureButtonBindings() {
    //Assign Buttons to Commands Here
    XboxA.whenPressed(new ResetDevices(), true);
    XboxB.whenPressed(new AlignSwerveModules(), false);
    XboxY.whenPressed(new Auto(), true);    
    XboxBumperRight.whileHeld(new LimeLightAim(true));
    XboxBumperRight.whenReleased(new LimeLightAim(false));
    XboxBumperLeft.whenPressed(new PointAtoB());

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

}
