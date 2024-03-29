package frc.robot.Controllables;

//FILES
import frc.robot.RobotMap;
//CTRE MOTORS
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
//REV MOTORS
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class motors {
    
    //top winch
    public static VictorSPX topWinch1 = new VictorSPX(RobotMap.topWinch1);
    public static VictorSPX topWinch2 = new VictorSPX(RobotMap.topWinch2);

    //Left Drive
    public static TalonSRX leftDrive1 = new TalonSRX(RobotMap.leftDrive1);
    public static TalonSRX leftDrive2 = new TalonSRX(RobotMap.leftDrive2);

    //Intakes
    //public static CANSparkMax leftIntake = new CANSparkMax(RobotMap.leftIntake, MotorType.kBrushed); //Priobabaly not used
    public static VictorSP rightIntake = new VictorSP(RobotMap.rightIntake);

    //Climb Winch
    public static VictorSPX winch1 = new VictorSPX(RobotMap.winch1);
    public static VictorSPX winch2 = new VictorSPX(RobotMap.winch2);
    public static CANSparkMax winch3 = new CANSparkMax(RobotMap.winch3, MotorType.kBrushed);
    public static CANSparkMax winch4 = new CANSparkMax(RobotMap.winch4, MotorType.kBrushed);

    //Right Drive
    public static TalonSRX rightDrive1 = new TalonSRX(RobotMap.rightDrive1);
    public static TalonSRX rightDrive2 = new TalonSRX(RobotMap.rightDrive2);
}
