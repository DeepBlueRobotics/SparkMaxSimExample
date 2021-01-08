# Robot Code Simulator

A WPIlib Robot project that allows robot code to deployed to a Webots simulation. The `master` branch simulates a drivetrain with teloperated control and pathfollowing capabilities and 
the `simple-project` branch contains only the base simulation code and can be used for copy-pasting your own code.

## Installation/Setup Instructions

To simulate a completely new robot project, follow all of the below steps. To simply run the `master` branch, skip steps 3 and 5.

1. Install Webots and follow the installation instructions. You can download the most recent version of Webots [here](https://cyberbotics.com/). Make sure that your computer meets the [system requirements](https://cyberbotics.com/doc/guide/system-requirements).

2. Once you have finished installing Webots, open Webots and take a look through at the guided tutorial to familiarize yourself. You might also want to read the [Getting Started with Webots](https://cyberbotics.com/doc/guide/getting-started-with-webots) section of the Webots User Guide, especially [The User Interface](https://cyberbotics.com/doc/guide/the-user-interface),
[The 3D Window](https://cyberbotics.com/doc/guide/the-3d-window), and [The Scene Tree](https://cyberbotics.com/doc/guide/the-scene-tree).

3. Clone the `simple-project` branch of the RobotCodeSimulator repository. This project has empty Robot.java and RobotContainer.java files and an example Command and Subsystem (both of which do nothing).

4. Build the project. If the build fails due to the error `Could not access submodule 'lib199'`, remove the lib199 folder and type `git submodule update --init` in the terminal at your RobotCodeSimulator directory. If you also receive an error saying that .settings is missing in lib199, clean the java language workspace (in VSCode, you can do this by typing Ctrl + Shift + P for Windows users or Cmd + Shift + P for Mac users and then run the `Clean the Java Language Server Workspace` command).

5. Copy your code into the project.

6. When you are ready to simulate the code, open your desired world file in Webots (the world files have extension .wbt and are located in the `RobotCodeSimulator/Webots/` directory). Test.wbt is a 4-wheeled drivetrain for testing purposes and Training.wbt is a 6-wheeled drivetrain for training new programmers with the simulator.

7. Now open up the list of VSCode commands (Ctrl + Shift + P on Windows, Cmd + Shift + P on Mac) and run "WPILib: Simulate Robot Code on Desktop". The project should build and you'll be prompted to select extensions to run. Make sure you choose the options named `halsim_gui` and `halsim_ws_server`.

8. Familiarize yourself with the layout of the WPIlib simulator using this [link](https://docs.wpilib.org/en/stable/docs/software/wpilib-tools/robot-simulation/simulation-gui.html#learning-the-layout) and add a joystick from the list of system joysticks. We do not have mocked joysticks yet, so you cannot control the robot in teleop if you do not have a USB joystick (such as a Logitech, Playstation, or Xbox controller). We currently are not mocking joysticks for non-joystick users, however, Windows users can emulate a joystick using [vJoy](http://vjoystick.sourceforge.net/site/) in combination with [UCR](https://autohotkey.com/boards/viewtopic.php?t=12249) as a short-term solution.

Happy simulating!
