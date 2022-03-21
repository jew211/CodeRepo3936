package frc.robot.Controllables;

//FILES
import frc.robot.RobotMap;
//CTRE MOTORS
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//REV MOTORS
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class motors {
    
    //top winch
    public VictorSPX topWinch1 = new VictorSPX(RobotMap.topWinch1);
    public VictorSPX topWinch2 = new VictorSPX(RobotMap.topWinch2);

    //Left Drive
    public TalonSRX leftDrive1 = new TalonSRX(RobotMap.leftDrive1);
    public TalonSRX leftDrive2 = new TalonSRX(RobotMap.leftDrive2);

    //Intakes
    public CANSparkMax leftIntake = new CANSparkMax(RobotMap.leftIntake, MotorType.kBrushed);
    public CANSparkMax rightIntake = new CANSparkMax(RobotMap.rightIntake, MotorType.kBrushed);

    //Climb Winch
    public VictorSPX winch1 = new VictorSPX(RobotMap.winch1);
    public VictorSPX winch2 = new VictorSPX(RobotMap.winch2);
    public VictorSPX winch3 = new VictorSPX(RobotMap.winch3);
    public VictorSPX winch4 = new VictorSPX(RobotMap.winch4);

    //Right Drive
    public TalonSRX rightDrive1 = new TalonSRX(RobotMap.rightDrive1);
    public TalonSRX rightDrive2 = new TalonSRX(RobotMap.rightDrive2);
}
