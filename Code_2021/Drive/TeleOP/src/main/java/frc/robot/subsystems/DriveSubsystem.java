package frc.robot.subsystems;

import frc.robot.Constants;

import java.lang.Math;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.CounterBase;

public class DriveSubsystem extends SubsystemBase{
    
    //drive motors
    VictorSP leftDrive1 = new VictorSP(Constants.Drive.leftDrivePort1);
    VictorSP leftDrive2 = new VictorSP(Constants.Drive.leftDrivePort2);

    VictorSP rightDrive1 = new VictorSP(Constants.Drive.rightDrivePort1);
    VictorSP rightDrive2 = new VictorSP(Constants.Drive.rightDrivePort2);

    //Encoders
    Encoder leftEncoder = new Encoder(Constants.Drive.leftEncoderPort1, Constants.Drive.leftEncoderPort2, false, CounterBase.EncodingType.k1X);
    Encoder rightEncoder = new Encoder(Constants.Drive.rightDrivePort1, Constants.Drive.rightDrivePort2, false, CounterBase.EncodingType.k1X);

    //gyro
    ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kMXP);


    //groups
    private final SpeedControllerGroup leftMotors = new SpeedControllerGroup(leftDrive1, leftDrive2);
    private final SpeedControllerGroup rightMotors = new SpeedControllerGroup(rightDrive1, rightDrive2);

    private final DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

    public DriveSubsystem(){
        leftEncoder.setDistancePerPulse(Constants.Drive.distancePerPulse);
        rightEncoder.setDistancePerPulse(Constants.Drive.distancePerPulse);
    }

    //drive
    public void arcadeDrive(double fwd, double rot){
        drive.arcadeDrive(fwd, rot);
    }

    public double getleftDistance(){
        return (leftEncoder.getDistance() * (2 * Math.PI * .0762));
    }
    
    public double getRightDistance(){
        return (rightEncoder.getDistance()* (2 * Math.PI * .0762));
    }
    public double getGyroAngle(){
        return gyro.getAngle();
    }
}


