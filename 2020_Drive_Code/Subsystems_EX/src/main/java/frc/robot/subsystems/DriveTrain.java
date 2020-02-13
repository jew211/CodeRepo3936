/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.TankDrive;


/**
 * Add your docs here.
 */
public class DriveTrain extends Subsystem {

  //Failsafe so in other parts not just one motor can be accessed
  private TalonSRX motorLeft1 = new TalonSRX (RobotMap.MOTOR_LEFT_1_ID);
  private TalonSRX motorLeft2 = new TalonSRX (RobotMap.MOTOR_LEFT_2_ID);
  private TalonSRX motorRight1 = new TalonSRX (RobotMap.MOTOR_RIGHT_1_ID);
  private TalonSRX motorRight2 = new TalonSRX (RobotMap.MOTOR_RIGHT_2_ID);
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new TankDrive());
  }

  public void setLeftMotors(double speed){
    //Allows for setting of both left motors
    //Negative on speed allows for reverse of one side of drivetrain
    motorLeft1.set(ControlMode.PercentOutput, -speed);
    motorLeft2.set(ControlMode.PercentOutput, -speed);
  }

  public void setRightMotors(double speed){
    motorRight1.set(ControlMode.PercentOutput, speed);
    motorRight2.set(ControlMode.PercentOutput, speed);
  }
}
