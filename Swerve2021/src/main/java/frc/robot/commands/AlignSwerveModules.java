package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class AlignSwerveModules extends CommandBase {
  boolean endCommand;

  public AlignSwerveModules() {
    endCommand = false; // Use this endCommand boolean to end the command
  }                     // The boolean should reset everythime the command is called so no need to reset it

  @Override
  public void initialize() {}

  @Override
  public void execute() {}

  @Override
  public void end(boolean interrupted) {}

  @Override
  public boolean isFinished() {
    return endCommand;
  }
}
