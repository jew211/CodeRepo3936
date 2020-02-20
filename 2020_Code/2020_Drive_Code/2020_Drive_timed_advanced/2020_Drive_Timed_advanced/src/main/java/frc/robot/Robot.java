/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.Spark; //Used for motor controller

import java.util.concurrent.TimeUnit;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import java.util.concurrent.TimeUnit;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  

  DigitalInput bumpSwitch;

  public static int leftDriveport = 0;
  public static int rightDrivePort = 1;

  int Init_Finished;
  int right;
  int left;
  int encoder_placeholder;
  int state_count;
  int pixy_centered;

  double pixy_reading;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    SmartDashboard.putNumber("Right", 0);
    SmartDashboard.putNumber("Left", 0);

  }

  @Override
  public void autonomousInit() {

    encoder_placeholder = 3;
    Init_Finished = 0;
    right = 0;
    left = 0;
    state_count = 0;
    pixy_centered = 1; //using for a placeholder

    if (SmartDashboard.getNumber("Right", 0) == 1) {
      right = 1;
      left = 0;
    } else if (SmartDashboard.getNumber("Left", 0) == 1) {
      left = 1;
      right = 0;
    }

    SmartDashboard.putNumber("Right_Test", right);
    SmartDashboard.putNumber("left_Test", left);

    // Put the init state here

    SmartDashboard.putString("Auto Init", "True");
    state_count++;
    Init_Finished = 1;

  }

  @Override
  public void autonomousPeriodic() {

    SmartDashboard.putNumber("State_count", state_count);
    if ((encoder_placeholder == 3 & right == 1) | (left == 1 & pixy_centered == 1 && state_count == 3)) {
      // Right Turn State
      SmartDashboard.putString("Auto Init", "False");
      SmartDashboard.putString("Right_Turn", "True");
      SmartDashboard.putString("Left Turn", "False");
      SmartDashboard.putString("Approach", "False");
      SmartDashboard.putString("Pixy", "False");
      SmartDashboard.putString("Dump", "False");
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      state_count++;
      encoder_placeholder++;

    } else if ((encoder_placeholder == 3 & left == 1) | (right == 1 & pixy_centered == 1 & state_count == 3)) {
      // Left Turn state
      SmartDashboard.putString("Auto Init", "False");
      SmartDashboard.putString("Right_Turn", "False");
      SmartDashboard.putString("Left Turn", "True");
      SmartDashboard.putString("Approach", "False");
      SmartDashboard.putString("Pixy", "False");
      SmartDashboard.putString("Dump", "False");
      state_count++;
      encoder_placeholder++;
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } else if (state_count == 2) {
      // pixy state
      SmartDashboard.putString("Auto Init", "False");
      SmartDashboard.putString("Right_Turn", "False");
      SmartDashboard.putString("Left Turn", "False");
      SmartDashboard.putString("Approach", "False");
      SmartDashboard.putString("Pixy", "True");
      SmartDashboard.putString("Dump", "False");
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      state_count++;

    } else if (state_count == 4) {
      // approach state
      SmartDashboard.putString("Approach", "True");
      SmartDashboard.putString("Auto Init", "False");
      SmartDashboard.putString("Right_Turn", "False");
      SmartDashboard.putString("Left Turn", "False");
      SmartDashboard.putString("Pixy", "False");
      SmartDashboard.putString("Dump", "False");
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      

    } else if (bumpSwitch.get()) {

      // DUMP state
      SmartDashboard.putString("Dump", "True");
      SmartDashboard.putString("Approach", "False");
      SmartDashboard.putString("Auto Init", "False");
      SmartDashboard.putString("Right_Turn", "False");
      SmartDashboard.putString("Left Turn", "False");
      SmartDashboard.putString("Pixy", "False");
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      state_count ++;

    }
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic(){
  }


  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
