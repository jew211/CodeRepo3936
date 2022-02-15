// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
//import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with
 * arcade steering.
 */
public class Robot extends TimedRobot {
  private final Talon m_leftMotor = new Talon(0);
  private final Talon m_rightMotor = new Talon(1);
  private final Talon R_intake = new Talon(2);
  private final Talon L_intake = new Talon(3);
  private final Spark tunnel_Motor = new Spark(4);
  private final Talon shooter = new Talon(5);

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftMotor, m_rightMotor);

  private final Joystick m_stick = new Joystick(0);
  private final JoystickButton button_RB = new JoystickButton(m_stick,6);

  private final JoystickButton button_A = new JoystickButton(m_stick,1);
  private final JoystickButton button_B = new JoystickButton(m_stick,2);
  private final JoystickButton button_X = new JoystickButton(m_stick,3);
  private final JoystickButton button_Y = new JoystickButton(m_stick,4);

  @Override
  public void teleopPeriodic() {
    // Drive with arcade drive.
    // That means that the Y axis drives forward
    // and backward, and the X turns left and right.
    m_robotDrive.arcadeDrive(m_stick.getRawAxis(1), m_stick.getRawAxis(4));

    R_intake.set(m_stick.getRawAxis(3));
    L_intake.set(m_stick.getRawAxis(3));

    double tunnelspeed=0;
    double shooterspeed=0;
    if(button_RB.get())
    {
      tunnelspeed=0.5;
    }
    if(button_B.get())
    {
      shooterspeed=0.25;
    }
    else if(button_Y.get())
    {
      shooterspeed=0.5;
    }
    else if(button_X.get())
    {
      shooterspeed=0.75;
    }
    else if(button_A.get())
    {
      shooterspeed=1;
    }
    else shooterspeed=0;
    SmartDashboard.putNumber("tunnel speed", tunnelspeed);
    SmartDashboard.putNumber("shooter Speed", shooterspeed);

    tunnel_Motor.set(tunnelspeed);
    shooter.set(shooterspeed);
  }
}
