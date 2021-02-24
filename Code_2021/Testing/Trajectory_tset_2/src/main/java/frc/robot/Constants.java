// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */

 import edu.wpi.first.wpilibj.util.Units;

public final class Constants {

	public static double trackWidthInches = 29;

	public static int leftDrivePort1 = 0;
    public static int leftDrivePort2 = 1;

    public static int rightDrivePort1 = 2;
    public static int rightDrivePort2 = 3;
    
    public static int leftEncoderPort1 = 0;
	public static int leftEncoderPort2 = 1;
    public static boolean leftEncoderReversed = false;
    
    public static int rightEncoderPort1 = 2;
    public static int rightEncoderPort2 = 3;
    public static boolean rightEncoderReversed = true;
    
    public static double leftDistancePerPulse = ((2 * Math.PI * Units.inchesToMeters(3)) / 2048);
	public static double rightDistancePerPulse = ((2 * Math.PI * Units.inchesToMeters(3)) / 2048);
	
	public static double ksVolts = .823;
	public static double kvVoltSecondsPerMeter = 1.46;
	
	public static DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(Units.inchesToMeters(trackWidthInches));
	
	public static double kMaxSpeedMetersPerSecond = 1;
	public static final double KaVoltsSecondsSquredPerMeter = .0042;
	public static double kMaxAccelerationMetersPerSecondSquared = .2;
	
	public static double kRamseteB = 2;
	public static double kRamseteZeta = 0.7;
	
	public static double kPDriveVel = .00127;

	



}
