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
    public static Compressor compressor = new Compressor(RobotMap.PCH, PneumaticsModuleType.REVPH);   
    
    //BALLPOPPER
    public static Solenoid ballPop = new Solenoid(RobotMap.PCH, PneumaticsModuleType.REVPH, RobotMap.ballPop);
    //BALLPOPPER RELEASE
    public static Solenoid PopRelease = new Solenoid(RobotMap.PCH, PneumaticsModuleType.REVPH, RobotMap.popRelease);
    //LEFTLIFT
    public static DoubleSolenoid leftLift = new DoubleSolenoid(RobotMap.PCH, PneumaticsModuleType.REVPH, RobotMap.leftLift1, RobotMap.leftLift2);
    //RIGHTLIFT
    public static DoubleSolenoid rightLift = new DoubleSolenoid(RobotMap.PCH, PneumaticsModuleType.REVPH, RobotMap.rightLift1, RobotMap.rightLift2);
}
