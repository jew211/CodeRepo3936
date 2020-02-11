/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

//Import the can controllers
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.VictorSP;

import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorMatch;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  //Set up the Device ID's for the CAN Controller
  WPI_TalonSRX Talon1 = new WPI_TalonSRX(1);
  WPI_TalonSRX Talon2 = new WPI_TalonSRX(2);

  //Set up ports for PWM Controllers
  public static final int aux1motorport = 0;
  VictorSP Aux1 = new VictorSP(aux1motorport);

  //Set up a Joystick for control
  Joystick test_joystick = new Joystick(0);

  //Set the Correct I2c Port for color sensor
  private final I2C.Port i2cPort = I2C.Port.kOnboard;

  private final ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);

  private final ColorMatch m_colorMatcher = new ColorMatch();

  private final Color kBlueTarget = ColorMatch.makeColor(0.143, 0.427, 0.429);
  private final Color kGreenTarget = ColorMatch.makeColor(0.197, 0.561, 0.240);
  private final Color kRedTarget = ColorMatch.makeColor(0.561, 0.232, 0.114);
  private final Color kYellowTarget = ColorMatch.makeColor(0.361, 0.524, 0.113);


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    m_colorMatcher.addColorMatch(kBlueTarget);
    m_colorMatcher.addColorMatch(kGreenTarget);
    m_colorMatcher.addColorMatch(kRedTarget);
    m_colorMatcher.addColorMatch(kYellowTarget);  

    //Set the Talons to Factory Defaults
    Talon1.configFactoryDefault();

    //reverse right drive
    Talon2.setInverted(false);
    Talon1.setInverted(true);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Talon1.set(100);
    Talon2.set(100);

    //Display the Value of the Joystick to the smart dashboard, for troubleshooting
    SmartDashboard.putNumber("Left Y Joystick", test_joystick.getRawAxis(1));
    SmartDashboard.putNumber("Right Y Joystick", test_joystick.getRawAxis(3));

    Color detectedColor = m_colorSensor.getColor();

    //Setting for AUX port one, static button hold
    
    Aux1.set(100);
    

    String colorString;
    ColorMatchResult match = m_colorMatcher.matchClosestColor(detectedColor);

     int numcolorswitch;
     numcolorswitch = 0;





    //if (match.color == kBlueTarget) {
      //colorString = "Blue";
      //for(1){
        //numcolorswitch = numcolorswitch + 1;
      //}
    //} else if (match.color == kRedTarget) {
      //colorString = "Red";
      //for(1){
        //numcolorswitch = numcolorswitch + 1;
     // }
    //} else if (match.color == kGreenTarget) {
      //colorString = "Green";
      //for(1){
        //numcolorswitch = numcolorswitch + 1;
      //}
    //} else if (match.color == kYellowTarget) {
      //colorString = "Yellow";for(1){
        //numcolorswitch = numcolorswitch + 1;
     // }
    //} else {
      //colorString = "Unknown";
   // }

    SmartDashboard.putNumber("Red", detectedColor.red);
    SmartDashboard.putNumber("Green", detectedColor.green);
    SmartDashboard.putNumber("Blue", detectedColor.blue);
    SmartDashboard.putNumber("Confidence", match.confidence);
    //SmartDashboard.putString("Detected Color", colorString);


  }

   
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
