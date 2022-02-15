// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

//CTRE Phoenix Imports
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
//REV Robotics imports
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

   //drive motors
    TalonSRX leftDrive1 = new TalonSRX(RobotMap.leftDrive1ID);
    TalonSRX leftDrive2 = new TalonSRX(RobotMap.leftDrive2ID);
    TalonSRX rightDrive1 = new TalonSRX(RobotMap.rightDrive1ID);
    VictorSPX rightDrive2 = new VictorSPX(RobotMap.rightDrive2ID);

    //Climb Motors
    VictorSP climb1 = new VictorSP(RobotMap.climb1);
    VictorSP climb2 = new VictorSP(RobotMap.climb2);
    VictorSP climb3 = new VictorSP(RobotMap.climb3);
    VictorSP climb4 = new VictorSP(RobotMap.climb4);

    //Climb Bar Motors
    CANSparkMax bar1 = new CANSparkMax(RobotMap.climbbar1, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);
    CANSparkMax bar2 = new CANSparkMax(RobotMap.climbbar2, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless);

    //Controller(s)
    Joystick driver = new Joystick(ControlMap.driverPort);

    //pneumatics
    Compressor compressor = new Compressor(1, PneumaticsModuleType.REVPH); 
    Solenoid climbLock = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
    DoubleSolenoid climbHook1 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 2);
    DoubleSolenoid climbHook2 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 3, 4);
    DoubleSolenoid ball = new DoubleSolenoid(PneumaticsModuleType.REVPH, 5, 6);
    

  @Override
  public void robotInit() {
    
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {

    //Display Compressor Info
    SmartDashboard.putBoolean("Compressor Enabled ", compressor.enabled());
    SmartDashboard.putBoolean("Pressure Switch Open", compressor.getPressureSwitchValue());
     //Drive Base Code
    leftDrive1.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.left_drive));
    leftDrive2.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.left_drive));
    rightDrive1.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.right_drive));
    rightDrive2.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.right_drive));

    //Climb Code
    climb1.set(driver.getRawAxis(ControlMap.climbAxis));
    climb2.set(driver.getRawAxis(ControlMap.climbAxis));
    climb3.set(driver.getRawAxis(ControlMap.climbAxis));
    climb4.set(driver.getRawAxis(ControlMap.climbAxis));

    //climb bars
    bar1.set(driver.getRawAxis(ControlMap.barControl));
    bar2.set(driver.getRawAxis(ControlMap.barControl));
    //climb solenoid
    if(driver.getRawButton(ControlMap.climbLock)){
      climbLock.toggle();
    }
  }
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
