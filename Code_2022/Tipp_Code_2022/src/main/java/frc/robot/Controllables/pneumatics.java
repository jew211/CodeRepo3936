package frc.robot.Controllables;

//FILES
import frc.robot.RobotMap;
//PCH
    //import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
//Solenoids
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class pneumatics {
    //COMPRESSOR
    Compressor compressor = new Compressor(RobotMap.PCH, PneumaticsModuleType.REVPH);   
    
    //BALLPOPPER
    Solenoid ballPop = new Solenoid(RobotMap.PCH, PneumaticsModuleType.REVPH, RobotMap.ballPop);
    //BALLPOPPER RELEASE
    Solenoid PopRelease = new Solenoid(RobotMap.PCH, PneumaticsModuleType.REVPH, RobotMap.popRelease);
    //LEFTLIFT
    DoubleSolenoid leftLift = new DoubleSolenoid(RobotMap.PCH, PneumaticsModuleType.REVPH, RobotMap.leftLift1, RobotMap.leftLift2);
    //RIGHTLIFT
    DoubleSolenoid rightLift = new DoubleSolenoid(RobotMap.PCH, PneumaticsModuleType.REVPH, RobotMap.rightLift1, RobotMap.rightLift2);
}
