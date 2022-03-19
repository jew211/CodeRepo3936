package frc.robot.subsystems;

import frc.team1711.swerve.subsystems.AutoSwerveDrive;

import com.kauailabs.navx.frc.AHRS;
import frc.robot.Constants;

public class Swerve extends AutoSwerveDrive{
    
    public static Swerve swerveInstance;

    public static Swerve getInstance(){
        if(swerveInstance == null) swerveInstance = new Swerve(
            new AHRS(),
            new SwerveModule("Front Left Module",
            Constants.FRONT_LEFT_STEER_ID,
            Constants.FRONT_LEFT_DRIVE_ID,
            Constants.FRONT_LEFT_STEER_ENCODER_ID,
            Constants.FRONT_LEFT_STEER_OFFSET),
            new SwerveModule("Front Left Module",
            Constants.FRONT_RIGHT_STEER_ID,
            Constants.FRONT_RIGHT_DRIVE_ID,
            Constants.FRONT_RIGHT_STEER_ENCODER_ID,
            Constants.FRONT_RIGHT_STEER_OFFSET),
            new SwerveModule("Front Left Module",
            Constants.BACK_LEFT_STEER_ID,
            Constants.BACK_LEFT_DRIVE_ID,
            Constants.BACK_LEFT_STEER_ENCODER_ID,
            Constants.BACK_LEFT_STEER_OFFSET),
            new SwerveModule("Front Left Module",
            Constants.BACK_RIGHT_STEER_ID,
            Constants.BACK_RIGHT_DRIVE_ID,
            Constants.BACK_RIGHT_STEER_ENCODER_ID,
            Constants.BACK_RIGHT_STEER_OFFSET));
            return swerveInstance;
        }
    private static final double trackToWheelBaseRatio = 1/1; //TODO: Measure this

    private final AHRS gyro;


    private final SwerveModule
        flWheel,
        frWheel,
        rlWheel,
        rrWheel;

        private Swerve(
                        AHRS gyro,
                        SwerveModule flWheel,
                        SwerveModule frWheel,
                        SwerveModule rlWheel,
                        SwerveModule rrWheel){
                            super(gyro, flWheel, frWheel, rlWheel, rrWheel, trackToWheelBaseRatio);
                            this.flWheel = flWheel;
                            this.frWheel = frWheel;
                            this.rlWheel = rlWheel;
                            this.rrWheel = rrWheel;

                            this.gyro = gyro;

                        }
}
