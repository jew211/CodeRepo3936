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
//import edu.wpi.first.wpilibj.DoubleSolenoid;


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
    CANSparkMax bar1 = new CANSparkMax(RobotMap.climbbar1, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushed);
    CANSparkMax bar2 = new CANSparkMax(RobotMap.climbbar2, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushed);
    VictorSPX winch1 = new VictorSPX(RobotMap.winch1);
    VictorSPX winch2 = new VictorSPX(RobotMap.winch2);

    //Controller(s)
    Joystick driver = new Joystick(ControlMap.driverPort);
    Joystick manip = new Joystick(ControlMap.manipPort);

    //pneumatics
    Compressor compressor = new Compressor(1, PneumaticsModuleType.REVPH ); 
    Solenoid climbLock = new Solenoid(PneumaticsModuleType.REVPH, 0);
    //DoubleSolenoid climbHook1 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 1, 2);
    //DoubleSolenoid climbHook2 = new DoubleSolenoid(PneumaticsModuleType.REVPH, 3, 4);
    //DoubleSolenoid ball = new DoubleSolenoid(PneumaticsModuleType.REVPH, 5, 6);
    

  @Override
  public void robotInit() {
    compressor.enableAnalog(100, 120);
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
    SmartDashboard.putNumber("Pressure Switch Open", compressor.getPressure());
     //Drive Base Code

     //Deadband loop
     if(driver.getRawAxis(ControlMap.left_drive) >= .05 || driver.getRawAxis(ControlMap.left_drive) <= -.05){
      leftDrive1.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.left_drive));
      leftDrive2.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.left_drive));
     }
     else{
       leftDrive1.set(ControlMode.PercentOutput, 0);
       leftDrive2.set(ControlMode.PercentOutput, 0);
     }
     if(driver.getRawAxis(ControlMap.right_drive) >= .05 || driver.getRawAxis(ControlMap.right_drive) <= -.05){
      rightDrive1.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.left_drive));
      rightDrive2.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.left_drive));
     } else{
       rightDrive1.set(ControlMode.PercentOutput, 0);
       rightDrive1.set(ControlMode.PercentOutput, 0);
     }

    //rightDrive1.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.right_drive));
    //rightDrive2.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.right_drive));

    //climb bars
   //bar1.set(manip.getRawAxis(ControlMap.barControl));
   //bar2.set(manip.getRawAxis(ControlMap.barControl));
    //climb solenoid
    if(driver.getRawButton(ControlMap.climbLock)){
      climbLock.set(true); 
    }else {
      climbLock.set(false);
    }
    /*if(manip.getRawButton(ControlMap.ClimbUp)){
      climb1.set(-1);
      climb2.set(1);
      climb3.set(-1);
      climb4.set(1);
    } else if(manip.getRawButton(ControlMap.climbDown)){
      climb1.set(1);
      climb2.set(-1);
      climb3.set(1);
      climb4.set(-1);
    } else {
      climb1.set(0);
      climb2.set(0);
      climb3.set(0);
      climb4.set(0);
    }
    */


    climb1.set(manip.getRawAxis(ControlMap.climbControl));
    climb2.set(-manip.getRawAxis(ControlMap.climbControl));
    climb3.set(manip.getRawAxis(ControlMap.climbControl));
    climb4.set(-manip.getRawAxis(ControlMap.climbControl));

    winch1.set(ControlMode.PercentOutput, manip.getRawAxis(ControlMap.winchControl));
    winch2.set(ControlMode.PercentOutput, manip.getRawAxis(ControlMap.winchControl));
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
   