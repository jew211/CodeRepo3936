package frc.robot;

public class ControlMap { 
    //Set up the controller Maps
    private static int a_button = 1;
    private static int b_button = 2;
    private static int x_button = 3;
    private static int y_button = 4;
    //private static int left_bumper = 5;
    private static int right_bumper = 6;
    private static int left_stick_x = 0;
    private static int left_stick_y = 1;
    //private static int left_trigger = 2;
    //private static int right_trigger = 3;
    private static int right_stick_x = 4;
    private static int right_stick_y = 5;

    public static int left_drive = left_stick_y;
    public static int right_drive = right_stick_y;

	public static int climbAxis = right_stick_x;

    public static int driverPort = 0;

    public static int climbLock = right_bumper;

    public static int barControl = left_stick_x;
    public static int climbDown = a_button;
    public static int ClimbUp = y_button;
    public static int barsForward = b_button;
    public static int barsBackward = x_button;

}
