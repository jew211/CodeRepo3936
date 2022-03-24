package frc.robot;

//FILES
import frc.robot.Controllables.motors;
import frc.robot.Controllables.pneumatics;
//WPILIB IMPORTS
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
//CTRE IMPORTS
import com.ctre.phoenix.motorcontrol.ControlMode;

public class AutoModes {
    int selectedMode;
    public AutoModes(int mode){
        selectedMode = mode;
    }
        public void run(){
            switch(selectedMode){
                case 1: //STRAIGHT BACK
                lifterUp(); //MAKE SURE LIFTER IS UP
                Timer.delay(1);
                if(pneumatics.compressor.getPressure() > 90){       
                    pneumatics.ballPop.set(true);
                    Timer.delay(.5);
                    robotDrive(.5 , .5);
                    Timer.delay(2);
                    robotDrive(0,0);
                    lifterDown();
                    Timer.delay(1);
                    pneumatics.PopRelease.set(true);
                    Timer.delay(15);
                }   
                break;
                case 2: //STRAIGHT PICK UP BALL
                lifterUp(); //MAKE SURE LIFTER IS UP
                Timer.delay(1);
                if(pneumatics.compressor.getPressure() > 90){       
                    pneumatics.ballPop.set(true);
                    Timer.delay(.5);
                    lifterDown();
                    Timer.delay(1);
                    pneumatics.PopRelease.set(true);
                    Timer.delay(1);
                    motors.rightIntake.set(1); //TODO: CHECK DIRECTION ON THIS
                    robotDrive(.5,.5);
                    Timer.delay(2);
                    robotDrive(0,0);
                    motors.rightIntake.set(0);
                }
                break;
                case 3:
                    lifterUp(); //MAKE SURE LIFTER IS UP
                    Timer.delay(1);
                    if(pneumatics.compressor.getPressure() > 90){       
                        pneumatics.ballPop.set(true);
                        Timer.delay(.5);
                        lifterDown();
                        Timer.delay(25);
                        pneumatics.PopRelease.set(true);
                        motors.rightIntake.set(1); //TODO: CHECK DIRECTION ON THIS
                        robotDrive(.5,.5);
                        Timer.delay(1.5); //TODO: CHECK THIS DISTANCE
                        robotDrive(0,0);
                        motors.rightIntake.set(0);
                        Timer.delay(.25);
                        robotDrive(-.1,.1); //TODO: CHECK POWER
                        Timer.delay(1); //TODO: CHECK DISTANCE
                        robotDrive(0,0);
                        lifterUp();
                        Timer.delay(.5);
                        robotDrive(.75,.75); //SLAM INTO THE FENDER TO ALIGN //TODO: MAKE SURE THIS DOESNT DESTROY ITSELF
                        Timer.delay(1.5);
                        motors.rightIntake.set(-1); //TODO: CHECK DIRECTION ON THIS
                        Timer.delay(2); 
                        motors.rightIntake.set(0);
                        Timer.delay(15);
                    }
                    break;
            }
        }
private void robotDrive(double left, double right){
    motors.leftDrive1.set(ControlMode.PercentOutput, left);
    motors.leftDrive2.set(ControlMode.PercentOutput, left);

    motors.rightDrive1.set(ControlMode.PercentOutput, -right);
    motors.rightDrive2.set(ControlMode.PercentOutput, -right);
}
//Lifter Control Functions
private void lifterUp(){
    pneumatics.rightLift.set(Value.kForward);
    pneumatics.leftLift.set(Value.kForward);
  }
private void lifterDown(){
    pneumatics.rightLift.set(Value.kReverse);
    pneumatics.leftLift.set(Value.kReverse);
  }
}

