// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;
//import java.util.List;
import java.nio.file.Path;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
//import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
//import edu.wpi.first.wpilibj.geometry.Pose2d;
//import edu.wpi.first.wpilibj.geometry.Rotation2d;
//import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
//import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
//import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.DriveTrain;
//import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
//import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private DriveTrain Drive = new DriveTrain();
  // The robot's subsystems and commands are defined here...
  //private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  //private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   * 
   * @param DriveTrain
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    //set a voltage contraint
    var autoVoltageContstraint = new DifferentialDriveVoltageConstraint(new SimpleMotorFeedforward(Constants.ksVolts, Constants.kvVoltSecondsPerMeter, Constants.KaVoltsSecondsSquredPerMeter),
        Constants.kDriveKinematics, 10);

        //create config for the trajectory
        TrajectoryConfig config = new TrajectoryConfig(Constants.kMaxSpeedMetersPerSecond, 
        Constants.kMaxAccelerationMetersPerSecondSquared)
        //adding kinematics to enjusre max speed is obeyed
        .setKinematics(Constants.kDriveKinematics)
        //apply the voltage constraint
        .addConstraint(autoVoltageContstraint);

        String trajectoryJSON = "paths/BarrelRacing.wpilib.json";
        Trajectory trajectory = new Trajectory();
             try {
                  Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
                  trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath);
             } catch (IOException ex) {
                  DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
             }

    RamseteCommand ramseteCommand = new RamseteCommand(
             trajectory,
             Drive::getPose,
             new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta),
             new SimpleMotorFeedforward(Constants.ksVolts,
                                        Constants.kvVoltSecondsPerMeter,
                                        Constants.KaVoltsSecondsSquredPerMeter),
             Constants.kDriveKinematics,
             Drive::getWheelSpeeds,
             new PIDController(Constants.kPDriveVel, 0, 0),
             new PIDController(Constants.kPDriveVel, 0, 0),
             Drive::tankDriveVolts,
             Drive
    );

    //reset odometry to starting point
    Drive.resetOdometry(trajectory.getInitialPose());

    //run path following command, then stop
    return ramseteCommand.andThen(() -> Drive.tankDriveVolts(0, 0));
  }
}
