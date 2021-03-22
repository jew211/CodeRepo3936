package frc.robot;

public class ControlMap {
    //Set up the controller Maps
    private static int a_button = 1;
    private static int b_button = 2;
    private static int x_button = 3;
    private static int y_button = 4;
    private static int left_bumper = 5;
    private static int right_bumper = 6;
    private static int left_stick_x = 0;
    private static int left_stick_y = 1;
    private static int left_trigger = 2;
    private static int right_trigger = 3;
    private static int right_stick_x = 4;
    private static int right_stick_y = 5;


    public static int leftSideDrive = left_stick_y;
    public static int rightSideDrive = -right_stick_y;

    public static int liftButton = y_button;
    public static int downButton = b_button;
    
    public static int intakeIn = x_button;
    public static int intakeOut = a_button;
}
