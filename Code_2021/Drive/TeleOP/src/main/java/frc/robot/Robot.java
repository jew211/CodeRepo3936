// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;

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

   VictorSP leftDrive1 = new VictorSP(0);
   VictorSP leftDrive2 = new VictorSP(1);
   VictorSP rightDrive1 = new VictorSP(2);
   VictorSP rightDrive2 = new VictorSP(3);

   //left drive group
   SpeedControllerGroup leftDrive = new SpeedControllerGroup(leftDrive1, leftDrive2);
   //right drive group
   SpeedControllerGroup rightDrive = new SpeedControllerGroup(rightDrive1, rightDrive2);

   //The robot drive
   DifferentialDrive drive = new DifferentialDrive(leftDrive, rightDrive);

   //lift Motors
   VictorSP lift1 = new VictorSP(4);
   VictorSP lift2 = new VictorSP(5);

   //Intake
   Talon intake = new Talon(6);

   //Driver Joystick
   Joystick driver = new Joystick(0);

  @Override
  public void robotInit() {
    //start the camera stream
    CameraServer.getInstance().startAutomaticCapture();
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
    //Arcade Drive
    drive.arcadeDrive((driver.getRawAxis(0)  * .75), (-driver.getRawAxis(5) * .75));

    //lift run
    if(driver.getRawButton(2)){
      //pick up
      lift1.set(-1);
      lift2.set(1);
    } else if(driver.getRawButton(3)){
      //put down
      lift1.set(1);
      lift2.set(-1);
    } else {
      lift1.set(0);
      lift2.set(0);
    }

    //Intake run
    if(driver.getRawButton(1)){
      //rotate in
      intake.set(.5);
    }else if(driver.getRawButton(4)){
      //rotate out
      intake.set(-.5);
    } else{
      intake.set(0);
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
}
