package frc.robot;

public class ControlMap{

    //Set up the controller Maps
    private static int a_button = 1;
    private static int b_button = 2;
    private static int x_button = 3;
    private static int y_button = 4;
    //private static int left_bumper = 5;
    //private static int right_bumper = 6;
    //private static int left_stick_x = 0;
    private static int left_stick_y = 1;
    //private static int left_trigger = 2;
    //private static int right_trigger = 3;
    private static int right_stick_x = 4;
    private static int right_stick_y = 5;




    //Axis for the arcade drive

    public static int forward_Axis = left_stick_y;
    public static int turn_axis = right_stick_x;


   
    //Stick axis for the climb 
    public static int climbAxis = right_stick_y;

    //Intake Spin In button
    public static int intakeSpinIn = a_button;

    //Intake Spin out button
    public static int intakeSpinOut = y_button;

    //Lift Intake
    public static int intakeLift = x_button;

    //Lower Intake
    public static int intakeLower = b_button;

    //Power on the lift of the intake
    public static double intakeLiftPower = 1;

    //Power on lift to lower
    public static double intakeLowerPower = 1;

    //button to start spin control
    //public static int spinControl = 3;

    //button to start color control
    //public static int colorControl = 6;

    //Axis for pizza
    public static int pizzaAxis = right_stick_x;

    //Pizza POWER
    public static double pizzaPower = .25;
    
}