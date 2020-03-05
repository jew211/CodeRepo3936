/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//Import the other files
import frc.robot.RobotMap; //Allows the use of the robotmap variables
import frc.robot.ControlMap; //Allows use of the control map variables

//WPILib imports
import edu.wpi.first.wpilibj.TimedRobot; //Setup for the settings
import edu.wpi.first.wpilibj.VictorSP;  //Allows for use of VictorSP sensor
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;  //Use for smart dashboard
import edu.wpi.first.wpilibj.DigitalInput; //use for limit switch
import edu.wpi.first.wpilibj.Encoder; //use for encoder
import edu.wpi.first.wpilibj.Joystick; //use for joystick
import edu.wpi.first.wpilibj.drive.DifferentialDrive; //Use for arcade drive
import edu.wpi.first.wpilibj.SpeedControllerGroup; //Use to create a group of Motor Controllers
import edu.wpi.first.wpilibj.I2C; //use to use I2C port
import edu.wpi.first.wpilibj.util.Color; //Allows the use of the color variable type

//rev Imports
import com.revrobotics.ColorMatch; //Allows to match a color to a set coloe
import com.revrobotics.ColorMatchResult; //Allows to match the color to a set color
import com.revrobotics.ColorSensorV3; //Allows use of the color senxsor

//CTRE Imports
import com.ctre.phoenix.motorcontrol.can.TalonSRX; //Allows use of the TalonSRX Controllers
import com.ctre.phoenix.motorcontrol.can.VictorSPX; //Allows use of the VictorSPX controllers

//Java Language Imports
import java.util.concurrent.TimeUnit; //llows for the use of seconds in the sleep functions



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //Setup I2C for color sensor
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  //Declare the color sensor
  private final ColorSensorV3 Pizza_Sensor = new ColorSensorV3(i2cPort);

  //Declare the color match for the color sensor
  private final ColorMatch Pizza_Sensor_Match = new ColorMatch();

  //Declare the targets for the color sensor
  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);


  //Joystick Setup
  Joystick driveJoystick = new Joystick(RobotMap.driveJoystickPort);
  Joystick manipJoystick = new Joystick(RobotMap.manipJoystickPort);

  //Setup for limitswitch
  DigitalInput bumpSwitch;

  //Encoder Setup
  Encoder leftEncoder = new Encoder(RobotMap.leftEncoderPort1, RobotMap.leftEncoderPort2);
  Encoder rightEncoder = new Encoder(RobotMap.rightEncoderPort1, RobotMap.rightEncoderPort2);


  //Drive Motor Setup
  VictorSP leftDrive1 = new VictorSP(RobotMap.leftDrivePort1);
  VictorSP leftDrive2 = new VictorSP(RobotMap.leftDrivePort2);
  VictorSP rightDrive1 = new VictorSP(RobotMap.rightDrivePort1);
  VictorSP rightDrive2 = new VictorSP(RobotMap.rightDrivePort2);


  //Set up the controller groups for the arcade drive
  SpeedControllerGroup leftDrive = new SpeedControllerGroup(leftDrive1, leftDrive2);
  SpeedControllerGroup rightDrive = new SpeedControllerGroup(rightDrive1, rightDrive2);

  //Set up the differential Drive for the arcade drive
  DifferentialDrive driveBase = new DifferentialDrive(leftDrive, rightDrive);

  //Climb Motor Setup
  TalonSRX climb1 = new TalonSRX(RobotMap.climbID1);
  TalonSRX climb2 = new TalonSRX(RobotMap.climbID2);
  TalonSRX climb3 = new TalonSRX(RobotMap.climbID3);

  //Set up the intake lift motors
  VictorSPX intakeLift1 = new VictorSPX(RobotMap.intakeLift1ID);
  VictorSPX intakeLift2 = new VictorSPX(RobotMap.intakeLift2ID);

  //Set up the intake spinner
  VictorSPX intakeSpinner = new VictorSPX(RobotMap.intakeSpinID);

  //Set up the pizza of fourtune
  VictorSP pizza = new VictorSP(RobotMap.pizzaPort);

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

    //Set the encoders to read per turns of shaft
    leftEncoder.setDistancePerPulse(1./2048.);
    rightEncoder.setDistancePerPulse(1./2048.);

    //Reverse Right drive
    rightDrive.setInverted(true);

    //add the matches for the color sensor
    Pizza_Sensor_Match.addColorMatch(kBlueTarget);
    Pizza_Sensor_Match.addColorMatch(kGreenTarget);
    Pizza_Sensor_Match.addColorMatch(kRedTarget);
    Pizza_Sensor_Match.addColorMatch(kYellowTarget);

    SmartDashboard.putNumber("Right", 0);
    SmartDashboard.putNumber("Left", 0);

    leftEncoder.reset();

  }

  @Override
  public void autonomousInit() {

    //set the left encoder to read the reverse value
    leftEncoder.setReverseDirection(true);

    //Reset the encoders for the initial move off of the line
    leftEncoder.reset();
    rightEncoder.reset();

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

    //Move off the line
    do{
      rightDrive.set(.5);
      leftDrive.set(.5);
    } while ( rightEncoder.getDistance() <= 3 & leftEncoder.getDistance() <= 3);
    rightDrive.set(0);
    leftDrive.set(0);

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
    driveBase.arcadeDrive(driveJoystick.getRawAxis(ControlMap.left_Axis), driveJoystick.getRawAxis(ControlMap.turn_axis));


    //Manipulator Control Blocks
    
    //Climb 
    climb1.set(null, manipJoystick.getRawAxis(ControlMap.climbAxis));
    climb2.set(null, manipJoystick.getRawAxis(ControlMap.climbAxis));
    climb3.set(null, manipJoystick.getRawAxis(ControlMap.climbAxis));

    //Spin intake
    if (manipJoystick.getRawButton(ControlMap.intakeSpinIn)){
      intakeSpinner.set(null, 1);
    } else if (manipJoystick.getRawButton(ControlMap.intakeSpinOut)){
      intakeSpinner.set(null, 1);
    } else{
      intakeSpinner.set(null, 0);
    }

    //Lift intake
    if (manipJoystick.getRawButton(ControlMap.intakeLower)){
      intakeLift1.set(null, ControlMap.intakeLiftPower);
      intakeLift2.set(null, -ControlMap.intakeLiftPower);
    } else if (manipJoystick.getRawButton(ControlMap.intakeLift)){
      intakeLift1.set(null, -ControlMap.intakeLowerPower);
      intakeLift2.set(null, ControlMap.intakeLowerPower);
    } else {
      intakeLift1.set(null, 0);
      intakeLift2.set(null, 0);
    }
    

    //Pizza control block
    pizza.set(manipJoystick.getRawAxis(ControlMap.pizzaAxis));



    //Pizza Sensing Block
    
    Color detectedColor = Pizza_Sensor.getColor();
    ColorMatchResult match = Pizza_Sensor_Match.matchClosestColor(detectedColor);
    String colorString;
    if (match.color == kBlueTarget) {
      colorString = "Blue";
    } else if (match.color == kRedTarget) {
      colorString = "Red";
    } else if (match.color == kGreenTarget) {
      colorString = "Green";
      } else if (match.color == kYellowTarget) {
      colorString = "Yellow";
    } else {
      colorString = "Unknown";
   }

   //Display for the color sensor, Manual Counting as temp
    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    SmartDashboard.putString("Detected Color", colorString);

    SmartDashboard.putString("Number of changes per turn", "8");

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
