package frc.robot;

public class values {
    //track width
    public static int trackWidthInches = 28;


    //Motor feed forward values
    public static double staticGain = .925;
    public static double velocityGain = 1.53;
    public static double accelGain = .0154;
    
    //left pid controller
    public static double leftKP = .212;
    public static double leftKI = 0;
    public static double leftKD = 0;

    //right PID controller
    public static double rightKP = .212;
    public static double rightKI = 0;
    public static double rightKD = 0;

    //motor ports
    public static int leftMasterPort = 0;
    public static int rightMasterPort = 2;
    public static int leftSlavePort = 1;
    public static int rightSlavePort = 3;

    //encoder ports
    public static int rightEncoder1 = 0;
    public static int rightEncoder2 = 1;
    public static int leftEncoder1 = 2;
    public static int leftEncoder2 = 3;
}

