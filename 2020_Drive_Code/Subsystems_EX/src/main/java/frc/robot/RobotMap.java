/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

  //Drivetrain Ports
	public static final int MOTOR_LEFT_1_ID = 0;
	public static final int MOTOR_LEFT_2_ID = 1;
	public static final int MOTOR_RIGHT_1_ID = 2;
  public static final int MOTOR_RIGHT_2_ID = 3;
  
  //Controller Ports
	public static final int DRIVER_JOYSTICK_PORT = 0;
	public static final int LEFT_STICK_Y = 1;
	public static final int RIGHT_STICK_Y = 3;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}
