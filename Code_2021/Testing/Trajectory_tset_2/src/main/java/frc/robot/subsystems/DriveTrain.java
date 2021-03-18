package frc.robot.subsystems;

import frc.robot.Constants;

//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.SPI;

public class DriveTrain extends SubsystemBase{
    //left drive motors
    private final SpeedControllerGroup leftDrive = new SpeedControllerGroup(new VictorSP(Constants.leftDrivePort1), new VictorSP(Constants.leftDrivePort2));
    //right drive motors
    private final SpeedControllerGroup rightDrive = new SpeedControllerGroup(new VictorSP(Constants.rightDrivePort1), new VictorSP(Constants.rightDrivePort2));

    //differential drive for the robot
    private final DifferentialDrive drive = new DifferentialDrive(leftDrive, rightDrive);

    //left side encoder
    private final Encoder leftEncoder = new Encoder(Constants.leftEncoderPort1, Constants.leftEncoderPort2, Constants.leftEncoderReversed);

    //right side encoder
    private final Encoder rightEncoder = new Encoder(Constants.rightEncoderPort1, Constants.rightEncoderPort2, Constants.rightEncoderReversed);

    //gyro
    ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kMXP);

    //odometry, tracks robot pose
    private final DifferentialDriveOdometry odometry;

    //create the drivetrain subsystem
    public DriveTrain(){
        //set the encoders distance per pulse
        leftEncoder.setDistancePerPulse(Constants.leftDistancePerPulse);
        rightEncoder.setDistancePerPulse(Constants.rightDistancePerPulse);

        //reset encoders
        resetEncoders();
        odometry = new DifferentialDriveOdometry(gyro.getRotation2d()); 
    }

    @Override
    public void periodic(){
        //update the odometry
        odometry.update(gyro.getRotation2d(), leftEncoder.getDistance(), rightEncoder.getDistance());
    }

    //returns the pose
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    //return wheel speeds of the robot
    public DifferentialDriveWheelSpeeds getWheelSpeeds(){
        return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
    }
    //reset odometry to given pose
    public void resetOdometry(Pose2d pose){
        resetEncoders();
        odometry.resetPosition(pose, gyro.getRotation2d());
    }

    //arcade drive controls
    public void arcadeDrive(double fwd, double rot){
        drive.arcadeDrive(fwd, rot);
    }
    //set tank drive volts
    public void tankDriveVolts(double leftVolts, double rightVolts){
        leftDrive.setVoltage(leftVolts);
        rightDrive.setVoltage(rightVolts);
        drive.feed();
    }
    //reset the encoders
    public void resetEncoders(){
        leftEncoder.reset();
        rightEncoder.reset();
    }
    //get average encoder distance
    public double getAverageEncoderDistance(){
        return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2.0;
    }
    //return left encoder
    public Encoder getLeftEncoder(){
        return leftEncoder;
    }
    //return right encoder
    public Encoder getRightEncoder(){
        return rightEncoder;
    }
    //set max output of the drive
    public void setMaxOutput(double output){
        drive.setMaxOutput(output);
    }
    //zero the heading of the robot
    public void zeroHeading(){
        gyro.reset();
    }
    //return the heading of the robot
    public double getHeading(){
        return gyro.getRotation2d().getDegrees();
    }
    //return trun rate of the robot
    public double getTurnRate(){
        return -gyro.getRate();
    }
}
