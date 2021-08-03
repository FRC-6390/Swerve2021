package frc.robot.subsystems.drivetrain;

import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class DesiredPosition {

    public Pose2d desiredCord;
    public List<DesiredSettings> settings;
    public DesiredPID pid;
    public DesiredSpeeds desiredSpeeds;

    public DesiredPosition(Pose2d desiredCord, DesiredSettings... settings){
        this(desiredCord, new DesiredPID(), settings);
    }

    public DesiredPosition(Pose2d desiredCord, DesiredPID pid, DesiredSettings... settings){
        this.desiredCord = desiredCord;
        this.settings = Arrays.asList(settings);
        desiredSpeeds = new DesiredSpeeds();

    }

    public DesiredSpeeds getDesiredSpeeds(Pose2d currentPosition){

        if(!settings.isEmpty()){
            if(settings.contains(DesiredSettings.Ignore_Rotation)){
                desiredSpeeds.theta = 0.0;
            }else if(settings.contains(DesiredSettings.Point_To_Heading)){
                desiredCord = pointToHeading(currentPosition);
            }
        }else{
            desiredSpeeds.x = calculateSpeed(calculateDistance(currentPosition.getX(), desiredCord.getX()));
            desiredSpeeds.y = calculateSpeed(calculateDistance(currentPosition.getY(), desiredCord.getY()));
            desiredSpeeds.theta = calculateSpeed(calculateDistance(currentPosition.getRotation().getDegrees(), desiredCord.getRotation().getDegrees()));    
        }
        return desiredSpeeds; 
    }

    public Pose2d pointToHeading(Pose2d currentPosition){
        return new Pose2d(desiredCord.getX(), desiredCord.getY(), Rotation2d.fromDegrees(angleFromCoordinate(desiredCord.getX(), desiredCord.getY(), currentPosition.getX(), currentPosition.getY())));
    }

    //https://stackoverflow.com/questions/3932502/calculate-angle-between-two-latitude-longitude-points
    public double angleFromCoordinate(double x1, double y1, double x2,double y2) {

        double ydiff = (y2 - y1);

        double y = Math.sin(ydiff) * Math.cos(x2);
        double x = Math.cos(x1) * Math.sin(x2) - Math.sin(x1)* Math.cos(x2) * Math.cos(ydiff);

        double brng = Math.atan2(y, x);

        brng = (Math.toDegrees(brng) + 360) % 360;
        brng = 360 - brng; // count degrees counter-clockwise - remove to make clockwise

        // brng = Math.toDegrees(brng);
        // brng = (brng + 360) % 360;
        // brng = 360 - brng; // count degrees counter-clockwise - remove to make clockwise

        return brng;
    }

    public double calculateDistance(double currentLocation, double desiredLocation){
        return desiredLocation - currentLocation;
    }

    public double calculateSpeed(double error){
        double dt = Timer.getFPGATimestamp() - pid.timeStamp;
        if(Math.abs(error) < pid.iLimit)
            pid.sum += error * dt;
        double errorRate = (error - pid.prevError) / dt;
        double output = (pid.p * error) + (pid.i * pid.sum) + (pid.d * errorRate);
        pid.timeStamp = Timer.getFPGATimestamp();
        pid.prevError = error;
        return output;
    }

    public boolean atDesiredPosition(Pose2d currentPosition){
        return desiredSpeeds.getTotal() < pid.threshold;
    }

    public static DesiredPosition fromCords(double x, double y, double theta){
        return fromCords(x,y,theta, new DesiredPID());
    }

    public static DesiredPosition fromCords(double x, double y, double theta, DesiredPID pid){
        return new DesiredPosition(new Pose2d(x,y,Rotation2d.fromDegrees(theta)), pid);
    }

    public static enum DesiredSettings{
        Ignore_Rotation, Point_To_Heading
    }


    public static class DesiredSpeeds{

        public double x;
        public double y;
        public double theta;

        public DesiredSpeeds(){
            this.x = 0;
            this.y = 0;
            this.theta = 0;
        }

        public double getTotal(){
            return (x+y+theta);
        }
        
    }

    public static class DesiredPID{

        private static final double DEFAULT_P = 0.01;
        private static final double DEFAULT_I = 0.001;
        private static final double DEFAULT_D = 0;
        private static final double DEFAULT_I_LIMIT = 0.1;
        private static final double DEFAULT_THRESHOLD = 0.001;


        public double p;
        public double i;
        public double d;
        public double iLimit;
        public double prevError = 0;
        public double sum = 0;
        public double timeStamp = Timer.getFPGATimestamp();
        public double threshold;

        public DesiredPID(){
            this(DEFAULT_P, DEFAULT_I, DEFAULT_D);
        }

        public DesiredPID(double p, double i, double d){
           this(p, i, d, DEFAULT_I_LIMIT, DEFAULT_THRESHOLD);
        }

        public DesiredPID(double p, double i, double d, double iLimit, double threshold){
            this.p = p;
            this.i = i;
            this.d = d;
            this.iLimit = iLimit;
            this.threshold = threshold;
        }

        public static DesiredPID fromValues(double p, double i, double d){
            return fromValues(p, i, d, DEFAULT_I_LIMIT, DEFAULT_THRESHOLD);
        }

        public static DesiredPID fromValues(double p, double i, double d, double iLimit, double threshold){
            return new DesiredPID(p, i, d, iLimit, threshold);
        }
        
    }

    public static void main(String[] args) {
        new DesiredPosition(null, new DesiredPID()).run();
    }

    public void run(){
        System.out.println(calculateSpeed(1));
    }
    
}
