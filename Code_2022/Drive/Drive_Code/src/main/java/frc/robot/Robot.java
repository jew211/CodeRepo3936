//FRC 3936 2022 ROBOT DRIVE CODE
package frc.robot;

//CTRE PHOENIX IMPORTS
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
//REV ROBOTICS IMPORTS
//import com.revrobotics.CANSparkMax;

//WPILIB IMPORTS
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.cameraserver.CameraServer;

public class Robot extends TimedRobot {
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
    //CANSparkMax bar1 = new CANSparkMax(RobotMap.climbbar1, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushed);
    //CANSparkMax bar2 = new CANSparkMax(RobotMap.climbbar2, com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushed);
    VictorSPX winch1 = new VictorSPX(RobotMap.winch1);
    VictorSPX winch2 = new VictorSPX(RobotMap.winch2);

    //Controller(s)
    Joystick driver = new Joystick(ControlMap.driverPort);
    Joystick manip = new Joystick(ControlMap.manipPort);

    //pneumatics
    Compressor compressor = new Compressor(1, PneumaticsModuleType.REVPH ); 
    Solenoid ballPop = new Solenoid(PneumaticsModuleType.REVPH, 0);

    //function to set drive base
    public void driveRobot(double left, double right){
      leftDrive1.set(ControlMode.PercentOutput, left);
      leftDrive2.set(ControlMode.PercentOutput, left);

      rightDrive1.set(ControlMode.PercentOutput, right);
      rightDrive2.set(ControlMode.PercentOutput, right);
    }
    

  @Override
  public void robotInit() {
    //Enable the Compressor
    compressor.enableAnalog(100, 120);
    //START THE CAMERA STREAM
    CameraServer.startAutomaticCapture();
  }

  @Override
  public void robotPeriodic() {
    //Display Compressor Info
    SmartDashboard.putBoolean("Compressor Enabled ", compressor.enabled()); //DISPLAY IF THE COMPRESSOR IS ON
    SmartDashboard.putNumber("Pressure Switch Open", compressor.getPressure()); //DISPLAY PRESSURE SWITCH
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    compressor.enableAnalog(65, 70);
    ballPop.set(false);
    int i = 0; //VARIABLE TO ONLY RUN THE AUTO ONCE
    //LOOP TO WAIT UNTIL PRESSURE IS GAINED
    if(compressor.getPressure() >= 45){
      //LOOP TO ONLY RUN AUTO ONCE
      if(i == 0){
        ballPop.set(true); //POP THE BALL OUT
        driveRobot(.5, .5); //DRIVE ROBOT BACKWARDS AT HALF SPEED
        Timer.delay(2); // FOR 2 SECONDS
        driveRobot(0, 0); //STOP THE ROBOT
        i ++; //INCREASE I SO LOOP DOESNT RUN AGAIN
        Timer.delay(13);
      }
    }
  }

  @Override
  public void teleopInit() {
    compressor.disable(); //SHUT THE COMPRESSOR OFF, WE DONT NEED IT HERE
  }

  @Override
  public void teleopPeriodic() {
     
    //Drive Base Code

     //Deadband loop
    rightDrive1.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.right_drive));
    rightDrive2.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.right_drive));
    leftDrive1.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.left_drive));
    leftDrive2.set(ControlMode.PercentOutput, driver.getRawAxis(ControlMap.left_drive));

     SmartDashboard.putNumber("leftDrive", driver.getRawAxis(ControlMap.left_drive));
     SmartDashboard.putNumber("rightDrive", driver.getRawAxis(ControlMap.right_drive));

     //Climb Winch
    climb1.set(manip.getRawAxis(ControlMap.climbControl));
    climb2.set(-manip.getRawAxis(ControlMap.climbControl));
    climb3.set(manip.getRawAxis(ControlMap.climbControl));
    climb4.set(-manip.getRawAxis(ControlMap.climbControl));

     //Arm winch
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
  public void testPeriodic() {
    compressor.enableAnalog(65, 70);
  }

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
   