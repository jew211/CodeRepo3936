package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.team1711.swerve.subsystems.SwerveDrive;
import frc.team1711.swerve.subsystems.SwerveWheel;
import frc.team1711.swerve.subsystems.AutoSwerveWheel;
import frc.team1711.swerve.util.Angles;

public class SwerveModule extends AutoSwerveWheel{
    
    /**
     * PID STEER VALUES
     */
    private static final double 
        steerPIDkP = 2.2,
        steerPIDkI = 0,
        steerPIDkD = 0;

        //Constants
        private final String name;
        private double drivePositionOffset;
        private final PIDController steerPID;
        private double directionAbsoluteOffset;
        private final TalonSRX steerController;
        private final VictorSP driveController;
        
        private final int steerEncoderID;

        public SwerveModule(String name, int steerControllerID, int driveControllerId, int steerEncoderID, double offset){
            this.name = name;

            driveController = new VictorSP(driveControllerId);
            steerController = new TalonSRX(steerControllerID);

            this.steerEncoderID = steerEncoderID;
           // steerEncoder = steerController.getSensorCollection().getPulseWidthPosition();
            directionAbsoluteOffset = offset;
            steerPID = new PIDController(steerPIDkP, steerPIDkI, steerPIDkD);

        }
        //Direcction Methods
        @Override
        protected double getDirection(){
            return Angles.wrapDegrees(getRawDirection());
        }
        private double getRawDirection(){
            return steerController.getSensorCollection().getPulseWidthPosition() - directionAbsoluteOffset;
        }

        public void configDirectionEncoder(){

        }

        @Override
        protected void setDirection(double targetDirection){
            double directionChange = Angles.wrapDegreesZeroCenter(targetDirection - getDirection());

            double steerSpeed = -steerPID.calculate(0, directionChange/360);

            steerController.set(ControlMode.PercentOutput, steerSpeed);
        }
        @Override
        protected void stopSteering(){
            steerController.set(ControlMode.PercentOutput, 0);
        }
        @Override
        protected void setDriveSpeed (double speed){
            driveController.set(speed);
        }
        @Override 
        protected double getPositionDifference(){
            return 0.0;
            //NOTHING HERE AS WE DONT HAVE A DRIVE ENCODER
        }
        @Override
        protected void resetDriveEncoder(){
            /*
            *Crickets*
            */
        }
    }
