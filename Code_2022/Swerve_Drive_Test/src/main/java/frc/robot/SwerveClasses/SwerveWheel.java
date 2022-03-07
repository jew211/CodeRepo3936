// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.SwerveClasses;
/**This class is for each individual wheel that is part of the swerve drive 
 * base, these will be used by the SwerveDrive class to create the drive base
 */

 //WPI IMPORTS
 import edu.wpi.first.wpilibj.PIDController;
 import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class SwerveWheel {
    private PIDController rotation; //CREATE THE PIDCONTROLLER OBJECT
    private MotorController speed; //CREATE THE MOTORONTROLLER OBJECT
    private double offset; //IDK SOME DOUBLE OR SOMTHING

    public SwerveWheel(PIDController rotation, MotorController speed, double offset){ //CONSTRUCTOR FOR THE CLASS
        System.out.println("wheel initialized"); //PRINT FOR DEBUGGING
        this.rotation = rotation;
        this.speed = speed;
        this.offset = offset; //SETTING THE VARIABLES FROM THE CONSTRUCTOR
    }
    public void drive(double newSpeed, double newAngle){ //DRIVE METHOD
        updateSpeed(newSpeed);
        updateRotation(newAngle); //METHODS DEFINED BELOW
    }
    public void updateSpeed(double newSpeed){
        speed.set(newSpeed);    
    }
    public void updateRotation(double newAngle){
        newAngle = newAngle + offset;

        if(newAngle < 0){
            rotation.setSetpoint(360 - (newAngle * -1));
        } else if (newAngle > 360){
            rotation.setSetpoint(newAngle - 360);
        }
        else {
            rotation.setSetpoint(newAngle);
        } //LITTLE LOGIC HERE FOR INTERPRETATION OF GYRO DIRECTION AND MORE THAN 360 DEG
    }
    public void disable(){
        rotation.disable();
    }
    public void enable(){
        rotation.enable(); //Self explanatory disable and enable of the PID controller
    }
}

