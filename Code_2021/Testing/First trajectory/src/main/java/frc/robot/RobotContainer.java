package frc.robot;

import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.subsystems.Drivetrain;

import java.util.Arrays;

public class RobotContainer {

     private Drivetrain drive = new Drivetrain();
     public Command getAutonomousCommand(){
          TrajectoryConfig config = new TrajectoryConfig(Units.feetToMeters(2) , Units.feetToMeters(2));
          config.setKinematics(drive.getKinematics());

          Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                  Arrays.asList(new Pose2d(), new Pose2d(1.0, 0 , new Rotation2d())), config);

          RamseteCommand command = new RamseteCommand(
          trajectory, 
          drive::getPose, 
          new RamseteController(2, 0.7), 
          drive.getFeedforward(), 
          drive.getKinematics(), 
          drive::getSpeeds,
          drive.getLeftPIDController(),
          drive.getRightPIDController(), 
          drive::setOutput, 
          drive);
          return command;
     }

}
