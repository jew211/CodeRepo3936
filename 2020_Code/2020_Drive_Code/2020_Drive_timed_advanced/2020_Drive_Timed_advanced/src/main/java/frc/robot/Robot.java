/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import java.util.concurrent.TimeUnit;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {


  //Joystick Setup
  public static int driveJoystickPort = 0;
  public static int manipJoystickPort = 1;

  Joystick driveJoystick = new Joystick(driveJoystickPort);
  Joystick manipJoystick = new Joystick(manipJoystickPort);

  //Setup for limitswitch
  DigitalInput bumpSwitch;

  //Encoder Setup
  Encoder leftEncoder = new Encoder(0, 1);
  Encoder rightEncoder = new Encoder(2, 3);


  //Drive Motor Setup
  public static int leftDrivePort1 = 0;
  public static int leftDrivePort2 = 1;
  public static int rightDrivePort1 = 2;
  public static int rightDrivePort2 = 3;

  PWMVictorSPX leftDrive1 = new PWMVictorSPX(leftDrivePort1);
  PWMVictorSPX leftDrive2 = new PWMVictorSPX(leftDrivePort2);
  PWMVictorSPX rightDrive1 = new PWMVictorSPX(rightDrivePort1);
  PWMVictorSPX rightDrive2 = new PWMVictorSPX(rightDrivePort2);

  SpeedControllerGroup leftDrive = new SpeedControllerGroup(leftDrive1, leftDrive2);
  SpeedControllerGroup rightDrive = new SpeedControllerGroup(rightDrive1, rightDrive2);

  DifferentialDrive driveBase = new DifferentialDrive(leftDrive, rightDrive);

  //Set up the ID's and controllers for the climb
  public static int climbID1 = 0;
  public static int climbID2 = 1;
  public static int climbID3 = 3;

  TalonSRX climb1 = new TalonSRX(climbID1);
  TalonSRX climb2 = new TalonSRX(climbID2);
  TalonSRX climb3 = new TalonSRX(climbID3);

  //Set up the intake lift motors
  public static int intakeLift1ID = 4;
  public static int intakeLift2ID = 5;

  VictorSPX intakeLift1 = new VictorSPX(intakeLift1ID);
  VictorSPX intakeLift2 = new VictorSPX(intakeLift2ID);

  //Set up the intake spinner
  public static int intakeSpinID = 6;

  VictorSPX intakeSpinner = new VictorSPX(intakeSpinID);

  //Set up the pizza of fourtune
  public static int pizzaPort = 4;

  PWMVictorSPX pizza = new PWMVictorSPX(pizzaPort);




  //Variable for the autonomous state machine
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

    leftEncoder.reset();

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
    //Drive code // Check for controller ports;
    driveBase.arcadeDrive(driveJoystick.getRawAxis(1), driveJoystick.getRawAxis(2));
    
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
