/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.commands.Drive;
import frc.robot.subsystems.Drivetrain;

import com.cyberbotics.webots.controller.Robot;

public class RobotContainer {

  private final Drivetrain drivetrain;
  private final Joystick joystick = new Joystick(Constants.joystickPort);

  public RobotContainer(Robot robot) {
    drivetrain = new Drivetrain(robot);
    drivetrain.setDefaultCommand(new Drive(drivetrain, joystick));
  }

  public Drivetrain getDrivetrain() { return drivetrain; }
}
