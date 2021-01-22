package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
//import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
//import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;


public class Drivetrain extends SubsystemBase {
     TalonSRX leftMaster = new TalonSRX(0);
     TalonSRX rightMaster = new TalonSRX(2);

     TalonSRX leftSlave = new TalonSRX(1);
     TalonSRX rightSlave = new TalonSRX(3);

     Encoder rightEncoder = new Encoder(0,1);
     Encoder leftEncoder = new Encoder(2, 3);

     AHRS gyro = new AHRS(SPI.Port.kMXP); //gyro object, check for specific gyro for correct

     DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Units.inchesToMeters(28));
     DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(getHeading());

     Pose2d pose; //stores position of the robot

    SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(0.268, 1.89, .243);  //NOT OUR VALUES, EXAMPLES

    PIDController leftPIDController = new PIDController(9.95, 0 , 0);
    PIDController rightPIDController = new PIDController(9.95, 0 , 0);


 public Drivetrain(){
     leftSlave.follow(leftMaster);
     rightSlave.follow(rightMaster); //sets the slaves to follow the masters

     rightMaster.setInverted(true); //sets right to invert
     leftMaster.setInverted(false);
 }

 public Rotation2d getHeading(){
     return Rotation2d.fromDegrees(-gyro.getAngle());
 }

 public DifferentialDriveWheelSpeeds getSpeeds(){
     return new DifferentialDriveWheelSpeeds(  //returning wheel speeds in MetersPerSecond
             rightEncoder.getRate() * 2 * Math.PI * Units.inchesToMeters(6) / 60,
             leftEncoder.getRate()  * 2 * Math.PI * Units.inchesToMeters(6) / 60
     );
 }

 public SimpleMotorFeedforward getFeedforward(){
     return feedforward;
 }
 public PIDController getLeftPIDController(){
        return leftPIDController;
 }
 public PIDController getRightPIDController(){
        return rightPIDController;
 }
 public DifferentialDriveKinematics getKinematics(){
        return kinematics;
 }
 public Pose2d getPose(){
        return pose;
 }
 public void setOutput(double leftVolts, double rightVolts){
        leftMaster.set(ControlMode.PercentOutput, leftVolts / 12);
        rightMaster.set(ControlMode.PercentOutput, rightVolts / 12);
 }
    @Override
    public void periodic() {
        pose = odometry.update(getHeading(), getSpeeds().rightMetersPerSecond, getSpeeds().leftMetersPerSecond);
    }
}
