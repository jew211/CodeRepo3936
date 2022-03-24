// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.TimedRobot;

//WPILIB IMPORTS
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PowerDistribution;
//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.CameraServer;
//FILES
import frc.robot.Controllables.motors;
import frc.robot.Controllables.pneumatics;
//import frc.robot.AutoModes;

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

  //Controllers
  XboxController drive = new XboxController(0); //Driver Controller on port 0
  XboxController manip = new XboxController(1); //Manipulator Controller on port 1

  //PDP
  PowerDistribution PDP = new PowerDistribution();

  //AUTONOMOUS CHOOSER
  SendableChooser<AutoModes> autoMode = new SendableChooser<AutoModes>();


  @Override
  public void robotInit() {
    pneumatics.compressor.enableAnalog(100,120); //ENABLE THE COMPRESSOR

    //START CAMERA
    CameraServer.startAutomaticCapture();

    //MAKE SURE AUTO SOLENOIDS ARE OFF
    pneumatics.ballPop.set(false);
    pneumatics.PopRelease.set(false);

    //SET LIFTER SOLENOIDS TO UP
    lifterUp();

    AutoModes straightBack = new AutoModes(1);
    AutoModes straightPickUpBall = new AutoModes(2);
    AutoModes twoBallAuto = new AutoModes(3);

    //AUTO MODES TO DASHBOARD
   // autoMode.addOption("Straight Back", straightBack);
    autoMode.addOption("Straight Pick Up Ball", straightPickUpBall);
    autoMode.addOption("2 Ball Auto", twoBallAuto);

    //SET THE STRAIGHT BACK AS DEFAULT
    autoMode.setDefaultOption("Straight Back", straightBack);
  }

  @Override
  public void robotPeriodic() {
    //OUTPUT STORED PRESSURE
    SmartDashboard.putNumber("Stored Pressure (PSI)", pneumatics.compressor.getPressure());
    //Output POWER INFORMATION
    SmartDashboard.putNumber("Total Amperage", PDP.getTotalCurrent());
    SmartDashboard.putNumber("Total Voltage", PDP.getVoltage());
    SmartDashboard.putNumber("PDP TEMP ", PDP.getTemperature());
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    
   /* 
    Timer.delay(1); // WAIT 1 SECOND
    //PRESSURE SAFETY
    if(pneumatics.compressor.getPressure() > 90){
      pneumatics.ballPop.set(true);
      Timer.delay(.5);
      robotDrive(-.25, -.25);
      Timer.delay(2); //MOVE BACK FOR 2 SEC
      robotDrive(0,0);
      Timer.delay(15); //WAIT UNTIL END OF AUTO
    }
    */

    //RUN THE CHOSEN AUTO
    autoMode.getSelected().run();
  }

  @Override
  public void teleopInit() {
    //RELEASE THE BALL POPPER
    pneumatics.PopRelease.set(true);
  }

  @Override
  public void teleopPeriodic() {
     /**
      * This is where all the controls for the robot will be defined
      */

      //DriveTrain

      //Tank Drive Config
      robotDrive(drive.getLeftY(), drive.getRightY());

      //Manip

      //Climber Controls
      climbWinch(manip.getRightY());
    
      //TOP WINCH Control
      topWinch(manip.getLeftX());

      //Intake Control
      if(manip.getYButton()){
        motors.rightIntake.set(-1);
      } else if(manip.getAButton()){
        motors.rightIntake.set(1);
      } else {
        motors.rightIntake.set(0);
      }

      //Lifter Control
      if(manip.getRightBumper()){
        lifterUp();
      } else if(manip.getLeftBumper()){
        lifterDown();
      }
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {
    pneumatics.compressor.enableAnalog(110, 120); //FOR PRECHARGING
  }

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}



  //ROBOT DRIVE FUNCTION
  public void robotDrive(double left, double right){
    motors.leftDrive1.set(ControlMode.PercentOutput, left);
    motors.leftDrive2.set(ControlMode.PercentOutput, left);

    motors.rightDrive1.set(ControlMode.PercentOutput, -right);
    motors.rightDrive2.set(ControlMode.PercentOutput, -right);
  }
  //Climb Winch Function
  public void climbWinch(double power){
    //TODO: CHECK CLIMB MOTORS REVERSED
    motors.winch1.set(ControlMode.PercentOutput, power);
    motors.winch2.set(ControlMode.PercentOutput, -power); //MOTORS 2 AND 4 ARE INVERTED
    motors.winch3.set(power); 
    motors.winch4.set(-power);
  }
  //TOP WINCH FUNCTION
  public void topWinch(double power){
    motors.topWinch1.set(ControlMode.PercentOutput, power);
    motors.topWinch2.set(ControlMode.PercentOutput, power);
  }
  //Lifter Control Functions
  public void lifterUp(){
    pneumatics.rightLift.set(Value.kForward);
    pneumatics.leftLift.set(Value.kForward);
  }
  public void lifterDown(){
    pneumatics.rightLift.set(Value.kReverse);
    pneumatics.leftLift.set(Value.kReverse);
  }
}

