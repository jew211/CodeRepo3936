// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */



public final class Constants {
    public static final double ksVolts = 0.22;
    public static final double kvVoltSecondsPerMeter = 1.98;
    public static final double kaVoltSecondsSquaredPerMeter = 0.2;
    
    public static final double kPDriveVel = 8.5;
    
    public static final double kTrackwidthMeters = 0.69;
    
    public static final DifferentialDriveKinematics kDriveKinematics =
        new DifferentialDriveKinematics(kTrackwidthMeters);

    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;

      // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
      public static final double kRamseteB = 2;
      public static final double kRamseteZeta = 0.7;

          //motor ports
    public static int leftMasterPort = 0;
    public static int rightMasterPort = 2;
    public static int leftSlavePort = 1;
    public static int rightSlavePort = 3;

    //encoder ports
    public static int rightEncoder1 = 0;
    public static int rightEncoder2 = 1;
    public static boolean kRightEncoderReversed = false;

    public static int leftEncoder1 = 2;
    public static int leftEncoder2 = 3;
    public static boolean kLeftEncoderReversed = false;

    public static double kEncoderDistancePerPulse = 10;
}

