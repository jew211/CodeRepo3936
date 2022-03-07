// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.SwerveClasses;

//WPILIB IMPORTS
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//NAVX GYRO
import com.kauailabs.navx.frc.AHRS;

/**This is the class that the drive base is ccreated in
 * using 4 swerveWheel objects
 */
public class SwerveDrive implements PIDOutput{
    
    //ADD THE 4 SWERVE WHEEL MODULES
    private SwerveWheel rightFrontWheel;
    private SwerveWheel rightBackWheel;
    private SwerveWheel leftFrontWheel;
    private SwerveWheel leftBackWheel;

    //THE NAVX GYRO
    private AHRS gyro = new AHRS(SPI.Port.kMXP);

    //MEASUREMENTS OF THE ROBOT
    private double wheelBase = 22.5;
    private double trackwidth = 24.5;

    //THE CONTSTRUCTOR
    public SwerveDrive(SwerveWheel rightFront, SwerveWheel rightBack, SwerveWheel leftFront, SwerveWheel leftBack){
        this.rightFrontWheel = rightFront;
        this.rightBackWheel = rightBack;
        this.leftFrontWheel = leftFront;
        this.leftBackWheel = leftBack;
        //CREATING THE SWERVE WHEELS IN THIS CLASS

        System.out.println("SwerveDrive Initialized"); //PRINT FOOR DEBUGGING
    }

    //WILL BE USED TO ACTUALLY CONTROL THE ROBOT
    public void drive(double directionX, double directionY, double rotation, boolean useGyro, boolean slowSpeed){

        //IF BOTH JOYSTICK IN THE CENTER, STOP
        if((directionX < 0.2 && directionX > -0.2) && (directionY < 0.2 && directionY > -0.2) && (rotation < 0.2 && rotation > -.02)){
            this.rightFrontWheel.updateSpeed(0);
            this.leftFrontWheel.updateSpeed(0);
            this.rightBackWheel.updateSpeed(0);
            this.leftBackWheel.updateSpeed(0);
            return;
        }
        //IF ROTATION JOYSTICK NEAR THE CENTER
        else if(rotation < 0.2 && rotation > -0.2){
            rotation = 0;
        }

        double L = this.wheelBase; //DISATNCE BETWEEN FRONT AND BACK WHEELS
        double W = this.trackwidth; //DISTANCE BETWEEN FRONT WHEELS
        double diameter = Math.sqrt ((L * L) + (W * W)); //SOMETHING ABOUT THE CIRCLE
        
        directionY *= -1;
        directionX *= -1;
        rotation *= -1; //INVERT THE AXIS

        double a = directionX - rotation * (L / diameter); //REAR AXLE
        double b = directionX + rotation * (L / diameter); //FRONT AXLE
        double c = directionY - rotation * (W / diameter); //LEFT TRACK
        double d = directionY = rotation * (W / diameter); //RIGHT TRACK


        //INSERTING HELPFUL LITLE DIAGRAM FROM 3707 HERE
          /*
         *                FRONT
         * 
         *            c          d
         *            | 		 |
         *       b ------------------ b
         *            |          |
         *            |          |
         * LEFT       |          |      RIGHT
         *            |          |
         *            |          |
         *       a ------------------ a
         *            |          |
         *            c          d
         * 
         *                BACK
         */

         //SET THE MOTOR SPEEFS FOR EACH WHEEL
         double backRightSpeed = Math.sqrt ((a * a) + (d * d));
         double backLeftSpeed = Math.sqrt ((a * a) + (c * c));
         double frontRightSpeed = Math.sqrt ((b * b) + (d * d));
         double frontLeftSpeed = Math.sqrt ((b * b) + (c * c));

         //SET WHEEL ANGLE FOR EACH WHEEL
         double backRightAngle = (Math.atan2 (a, d) / Math.PI) * 180;
         double backLeftAngle = (Math.atan2 (a, c) / Math.PI) * 180;
         double frontRightAngle = (Math.atan2 (b, d) / Math.PI) * 180;
         double frontLeftAngle = (Math.atan2 (b, c) / Math.PI) * 180;

         //GYRO/SLOW SPEED
         if(useGyro) {
            double gyroAngle = normalizeGyroAngle(gyro.getAngle()); 
            backRightAngle += gyroAngle;
            backLeftAngle += gyroAngle;
            frontRightAngle += gyroAngle;
            frontLeftAngle += gyroAngle;
        }
        if(slowSpeed) {
        	backRightSpeed *= 0.5;
        	backLeftSpeed *= 0.5;
        	frontRightSpeed *= 0.5;
        	frontLeftSpeed *= 0.5;
        }

        //SEND THE COMMANDS TO THE MOTORS
        this.rightFrontWheel.drive(frontRightSpeed, frontRightAngle);
    	this.leftFrontWheel.drive(frontLeftSpeed, frontLeftAngle);
    	this.leftBackWheel.drive(backLeftSpeed, backLeftAngle);
    	this.rightBackWheel.drive(backRightSpeed, backRightAngle);
    }
    @Override
	public void pidWrite(double output) {
		System.out.println("X");
		System.out.println(output);
        drive(output, 0, 0, false, false);
	}
    public double normalizeGyroAngle(double angle){
        return (angle - (Math.floor( angle / 360) * 360) );
    }
    public void driveSimple(double speed, double angle) {
        this.rightFrontWheel.drive(speed, angle);
		this.leftFrontWheel.drive(speed, angle);
		this.leftBackWheel.drive(speed, angle);
        this.rightBackWheel.drive(speed, angle);
    }
}
