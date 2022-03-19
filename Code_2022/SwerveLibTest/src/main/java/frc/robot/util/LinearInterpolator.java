package frc.robot.util;

/**
 * I am not sure what this is for or if this program even uses it
 * its in the example so im putting it here just in case
 */

public class LinearInterpolator {
    
    private final double m, b;

    public LinearInterpolator(double x1, double y1, double x2, double y2){
        m = (y1 - y2) / (x1 - x2);
        b = y1 - m * x1;
    }

    /**
     * Converts an input x vale to an output y value
     */
    public double interpolate(double x){
        return m*x + b;
    }
    /**
     * Converts an output Y value to n input x value
     */
    public double invInterpolate(double y){
        return (y-b) / m;
    }
}
