/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.io.IOException;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
  private com.cyberbotics.webots.controller.Robot robot;
  private int timeStep;

  @Override
  public void robotInit() {
    robot = new com.cyberbotics.webots.controller.Robot();
    timeStep = (int) Math.round(robot.getBasicTimeStep());
    // Make sure to remove the robot when the WPIlib simulation ends
    Runtime.getRuntime().addShutdownHook(new Thread(robot::delete));

    /*
    If you want to connect your PWM motors to Webots, you should add this line for EACH motor controller:

    new PWMSim(<MOTOR PORT NUMBER>).registerSpeedCallback(<YOUR NOTIFY CALLBACK OBJECT>, true);

    NOTE: In the future, most of the registration of callback methods for motor controllers, gyros, encoders, etc. will be handled
    automatically every time you initialize a new motor controller or sensor.
    But for now, you NEED to add this line for your wpilib motors to talk to your webots motors.

    For more information look at this repo's master branch.
    */
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    if (RobotBase.isSimulation()) robot.step(timeStep);
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }
  
  @Override
  public void testPeriodic() {
  }
}
