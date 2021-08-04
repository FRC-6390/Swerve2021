package frc.robot.subsystems.drivetrain;

import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

public class DesiredPosition {

    public Pose2d desiredCord;
    public List<DesiredSettings> settings;
    public DesiredPID drivePid, rotationPid;
    public DesiredSpeeds desiredSpeeds;

    public DesiredPosition(Pose2d desiredCord, DesiredSettings... settings){
        this(desiredCord, new DesiredPID(false), new DesiredPID(true), settings);
    }

    public DesiredPosition(Pose2d desiredCord, DesiredPID drivePid, DesiredPID rotationPid, DesiredSettings... settings){
        this.desiredCord = desiredCord;
        this.drivePid = drivePid;
        this.rotationPid = rotationPid;
        this.settings = Arrays.asList(settings);
        this.desiredSpeeds = new DesiredSpeeds();
    }

    public DesiredSpeeds getDesiredSpeeds(Pose2d currentPosition){

        double xDistance = calculateDistance(currentPosition.getX(), desiredCord.getX());
        double yDistance = calculateDistance(currentPosition.getY(), desiredCord.getY());
        double thetaDistance = calculateDistance(currentPosition.getRotation().getDegrees(), desiredCord.getRotation().getDegrees());

        if(settings.contains(DesiredSettings.Point_To_Heading)){
            desiredCord = pointToHeading(currentPosition);
        }
        
        if(settings.contains(DesiredSettings.Ignore_Drive)){
            desiredSpeeds.x = 0.0;
            desiredSpeeds.y = 0.0;
        }else{
            desiredSpeeds.x = drivePid.calculateSpeed(xDistance);
            desiredSpeeds.y = drivePid.calculateSpeed(yDistance);
        }
        
        if(settings.contains(DesiredSettings.Ignore_Rotation)){
            desiredSpeeds.theta = 0.0;
        }else{
            desiredSpeeds.theta = rotationPid.calculateSpeed(thetaDistance);    
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

    public boolean atDesiredPosition(){
        return drivePid.underThreshold() && rotationPid.underThreshold();
    }

    public static DesiredPosition fromCords(double x, double y, double theta, DesiredSettings... settings){
        return fromCords(x,y,theta, new DesiredPID(false), new DesiredPID(true), settings);
    }

    public static DesiredPosition fromCords(double x, double y, double theta, DesiredPID drivePid, DesiredPID rotationPid, DesiredSettings... settings){
        return new DesiredPosition(new Pose2d(x,y,Rotation2d.fromDegrees(theta)), drivePid, rotationPid, settings);
    }

    public static enum DesiredSettings{
        Ignore_Rotation, Point_To_Heading, Ignore_Drive
    }


    public static class DesiredSpeeds{

        public double x, y, theta;

        public DesiredSpeeds(){
            this.x = 0;
            this.y = 0;
            this.theta = 0;
        }
    }

    public static class DesiredPID{
        private boolean rotation;
        public double p, i, d, iLimit, threshold;
        public double prevError = 0;
        public double errorRate = 0;
        public double sum = 0;
        public double timeStamp = Timer.getFPGATimestamp();

        public DesiredPID(boolean rotation){
            this.rotation = rotation;
            if(rotation){
                this.p = DEFAULT.ROTATION.P.get();
                this.i = DEFAULT.ROTATION.I.get();
                this.d = DEFAULT.ROTATION.D.get();
                this.iLimit = DEFAULT.ROTATION.I_LIMIT.get();
                this.threshold = DEFAULT.ROTATION.THRESHOLD.get();
            }else{
                this.p = DEFAULT.DRIVE.P.get();
                this.i = DEFAULT.DRIVE.I.get();
                this.d = DEFAULT.DRIVE.D.get();
                this.iLimit = DEFAULT.DRIVE.I_LIMIT.get();
                this.threshold = DEFAULT.DRIVE.THRESHOLD.get();
            }
                
        }

        public DesiredPID(double p, double i, double d, double iLimit, double threshold, boolean rotation){
            this.p = p;
            this.i = i;
            this.d = d;
            this.iLimit = iLimit;
            this.threshold = threshold;
            this.rotation = rotation;
        }

        public boolean underThreshold(){
            if(rotation){
                return Math.abs(prevError) < threshold;
            }
            return Math.abs(errorRate) < threshold;
        }

        public double calculateSpeed(double error){
            double dt = Timer.getFPGATimestamp() - timeStamp;
            if(Math.abs(error) < iLimit)
                sum += error * dt;
            errorRate = (error - prevError) / dt;
            double p_error = p * error;
            double i_error = i * sum;
            double d_error = d * errorRate;
            timeStamp = Timer.getFPGATimestamp();
            prevError = error;
            double output = p_error + i_error + d_error;
            output = Math.abs(output) < DEFAULT.SPEED_LIMIT ? output : output > 0 ? DEFAULT.SPEED_LIMIT : -DEFAULT.SPEED_LIMIT;
            return output;
        }

        public static DesiredPID fromValues(double p, double i, double d, double iLimit, double threshold, boolean rotation){
            return new DesiredPID(p, i, d, iLimit, threshold, rotation);
        }
        
    }

    private static interface DEFAULT {

        double SPEED_LIMIT = 0.03;
        
        enum DRIVE{
            P(0.045),
            I(0.03),
            D(0.00000000008),
            I_LIMIT(0.2),
            THRESHOLD(1000);

            private double val;
            private DRIVE(double val){
                this.val = val;
            }

            public double get(){
                return val;
            }
        }

        enum ROTATION{
            P(0.0015),
            I(0.005),
            D(0.0),
            I_LIMIT(10.0),
            THRESHOLD(0.1);

            private double val;

            private ROTATION(double val){
                this.val = val;
            }

            public double get(){
                return val;
            }
        }
        
    }
    
}
