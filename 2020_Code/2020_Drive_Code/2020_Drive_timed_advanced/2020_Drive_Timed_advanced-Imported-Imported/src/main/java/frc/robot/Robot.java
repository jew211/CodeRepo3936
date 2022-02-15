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
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;  //Allows for use of VictorSP sensor
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;  //Use for smart dashboard
import edu.wpi.first.wpilibj.DigitalInput; //use for limit switch
import edu.wpi.first.wpilibj.Encoder; //use for encoder
import edu.wpi.first.wpilibj.Joystick; //use for joystick
import edu.wpi.first.wpilibj.drive.DifferentialDrive; //Use for arcade drive
import edu.wpi.first.wpilibj.SpeedControllerGroup; //Use to create a group of Motor Controllers
import edu.wpi.first.wpilibj.I2C; //use to use I2C port
import edu.wpi.first.wpilibj.util.Color; //Allows the use of the color variable type
import edu.wpi.first.wpilibj.motorcontrol.Spark; // Used for Rev Blinkin control
//import edu.wpi.first.cscore.UsbCamera; //Used for usb camera
import edu.wpi.first.cameraserver.CameraServer; //Used to send camera stream to the smart dashboard
import edu.wpi.first.wpilibj.DriverStation; //Allows us to get the gamedata
//rev Imports
import com.revrobotics.ColorMatch; //Allows to match a color to a set coloe
import com.revrobotics.ColorMatchResult; //Allows to match the color to a set color
import com.revrobotics.ColorSensorV3; //Allows use of the color senxsor
import com.ctre.phoenix.motorcontrol.ControlMode;
//CTRE Imports
import com.ctre.phoenix.motorcontrol.can.TalonSRX; //Allows use of the TalonSRX Controllers
import com.ctre.phoenix.motorcontrol.can.VictorSPX; //Allows use of the VictorSPX controllers

//Java Language Imports
//import java.util.concurrent.TimeUnit; //llows for the use of seconds in the sleep functions



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  
  //Setup the controller for rev blinkin
  Spark lights = new Spark(RobotMap.Blinkin);

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
  VictorSPX climb1 = new VictorSPX(RobotMap.climbID1);
  VictorSPX climb2 = new VictorSPX(RobotMap.climbID2);
  VictorSPX climb3 = new VictorSPX(RobotMap.climbID3);

  //Set up the intake lift motors
  TalonSRX intakeLift1 = new TalonSRX(RobotMap.intakeLift1ID);
  TalonSRX intakeLift2 = new TalonSRX(RobotMap.intakeLift2ID);

  //Set up the intake spinner
  TalonSRX intakeSpinner = new TalonSRX(RobotMap.intakeSpinID);

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

  //Variable to store the gamedata
  String gameData;



  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    //Camera init and display
    CameraServer.startAutomaticCapture();

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

    //Set the variables for auto starting config
    encoder_placeholder = 3;
    Init_Finished = 0;
    right = 0;
    left = 0;
    state_count = 0;
    pixy_centered = 1; //using for a placeholder


    //Get input from the user for if auto needs to turn left or right
    if (SmartDashboard.getNumber("Right", 0) == 1) {
      right = 1;
      left = 0;
    } else if (SmartDashboard.getNumber("Left", 0) == 1) {
      left = 1;
      right = 0;
    }



    //THIS BLOCK WAS USED FOR TESTING, WAITING TO DELETE UNTIL WE KNOW AUTO WORKS
    /* SmartDashboard.putNumber("Right_Test", right);
    SmartDashboard.putNumber("left_Test", left);

    // Put the init state here

    SmartDashboard.putString("Auto Init", "True");
    state_count++;
    Init_Finished = 1; */

    

  }

  @Override
  public void autonomousPeriodic() {

    //TEMP AUTO CODE, INTENDED TO BE USED UNTIL FULL AUTO WAS TESTED AND PROGRAMMED

    //Move off the line
    do{
      rightDrive.set(.25);
      leftDrive.set(.25);
    } while ( rightEncoder.getDistance() <= 3 & leftEncoder.getDistance() <= 3);
    rightDrive.set(0);
    leftDrive.set(0);

    //AUTO CODE, CURRENTLY NOT IN USE, 
    //NO actual moving code here yet, state machine is in place however no movement commands are included.

   /*  SmartDashboard.putNumber("State_count", state_count);
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
        e.printStackTrace();
      }
      state_count ++;

    } */

    //END AUTO CODE
  }

  @Override
  public void teleopInit() {

    //WILL SIGNAL TELEOP INITIATED
    //Set the Blinkin to cycle
    //lights.set(.53);
  }

  @Override
  public void teleopPeriodic(){

    //Check for the gamedata
    gameData = DriverStation.getGameSpecificMessage();

    //Display the color selection to the dashboard
    switch(gameData.charAt(0)){
      case 'B': SmartDashboard.putString("Color Control", "Blue");
        break;
      case 'G': SmartDashboard.putString("Color Control", "Green");
        break;
      case 'R': SmartDashboard.putString("Color Control", "Red");
        break;
      case 'Y': SmartDashboard.putString("Color Control", "Yellow");
        break;
      default: SmartDashboard.putString("Color Control", "Null");
    }
    
    //Drive code
    //Runs the arcade drive
    driveBase.arcadeDrive(driveJoystick.getRawAxis(ControlMap.turn_axis), -driveJoystick.getRawAxis(ControlMap.forward_Axis));



    //Manipulator Control Blocks
    
    
    //Climb 

    //Setting a deadzone on the stick, fixed with controller swap but keeping just in case.
    if ((manipJoystick.getRawAxis(ControlMap.climbAxis) < .25) & (manipJoystick.getRawAxis(ControlMap.climbAxis) < -.25)){
      climb1.set(ControlMode.PercentOutput, 0);
      climb2.set(ControlMode.PercentOutput, 0);
      climb3.set(ControlMode.PercentOutput, 0);
    }else {
      climb1.set(ControlMode.PercentOutput, manipJoystick.getRawAxis(ControlMap.climbAxis));
      climb2.set(ControlMode.PercentOutput, manipJoystick.getRawAxis(ControlMap.climbAxis));
      climb3.set(ControlMode.PercentOutput, manipJoystick.getRawAxis(ControlMap.climbAxis));
    }

    //Spin intake
    if (manipJoystick.getRawButton(ControlMap.intakeSpinIn)){
      intakeSpinner.set(ControlMode.PercentOutput, 1);
    } else if (manipJoystick.getRawButton(ControlMap.intakeSpinOut)){
      intakeSpinner.set(ControlMode.PercentOutput, -1);
    } else{
      intakeSpinner.set(ControlMode.PercentOutput, 0);
    }

    //Lift intake
    if (manipJoystick.getRawButton(ControlMap.intakeLower)){
      intakeLift1.set(ControlMode.PercentOutput, ControlMap.intakeLiftPower);
      intakeLift2.set(ControlMode.PercentOutput, -ControlMap.intakeLiftPower);
    } else if (manipJoystick.getRawButton(ControlMap.intakeLift)){
      intakeLift1.set(ControlMode.PercentOutput, -ControlMap.intakeLowerPower);
      intakeLift2.set(ControlMode.PercentOutput, ControlMap.intakeLowerPower);
    } else {
      intakeLift1.set(ControlMode.PercentOutput, 0);
      intakeLift2.set(ControlMode.PercentOutput, 0);
    }
    

    //Pizza control block
    pizza.set(manipJoystick.getRawAxis(ControlMap.pizzaAxis) * ControlMap.pizzaPower);



    //Pizza Sensing Block
    
    //MIGHT NEED FIXED, NOT SURE IF HARDWARE OR SOFTWARE ISSUE
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

    //Displaying because nobody will remember how many times is changes per turn, this includes me.
    SmartDashboard.putString("Number of changes per turn", "8");

  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
