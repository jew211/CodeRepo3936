package frc.robot;

public final class Constants {
    public static final class Drive{
        public static final int leftDrivePort1 = 0;
        public static final int leftDrivePort2 = 1;
        public static final int rightDrivePort1 = 2;
        public static final int rightDrivePort2 = 3;

        public static final int leftEncoderPort1 = 0;
        public static final int leftEncoderPort2 = 1;
        public static final int rightEncoderPort1 = 2;
        public static final int rightEncoderPort2 = 3;

        public static final double distancePerPulse = (1/2048);
    }
    public static final class Control{
        public static final int driveContollerPort = 0;
    }
}