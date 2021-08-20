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

    private double pointToHeadingAngle = -300;

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

        desiredSpeeds.xDistance = calculateDistance(currentPosition.getX(), desiredCord.getX());
        desiredSpeeds.yDistance = calculateDistance(currentPosition.getY(), desiredCord.getY());
        desiredSpeeds.thetaDistance = calculateDistance(currentPosition.getRotation().getDegrees(), desiredCord.getRotation().getDegrees());

        if(settings.contains(DesiredSettings.Point_To_Heading)){
            desiredCord = pointToHeading(currentPosition);
        }
        
        desiredSpeeds.x = calculateX(desiredSpeeds);
        desiredSpeeds.y = calculateY(desiredSpeeds);
        desiredSpeeds.theta = calculateTheta(desiredSpeeds);
    
        
        return desiredSpeeds; 
    }

    public double calculateX(DesiredSpeeds speeds){

        if(settings.contains(DesiredSettings.Ignore_Drive))
            return 0.0;

        if(settings.contains(DesiredSettings.Rotate_Then_Drive)){
            if(!rotationPid.underThreshold()){
                return 0.0;
            }
        }

        if(settings.contains(DesiredSettings.Y_Then_X)){
            if(speeds.yDistance > drivePid.threshold){
                return 0.0;
            }
        }

        return drivePid.calculateSpeed(speeds.xDistance);
    }

    public double calculateY(DesiredSpeeds speeds){

        if(settings.contains(DesiredSettings.Ignore_Drive))
            return 0.0;

        if(settings.contains(DesiredSettings.Rotate_Then_Drive)){
            if(!rotationPid.underThreshold()){
                return 0.0;
            }
        }

        if(settings.contains(DesiredSettings.X_Then_Y)){
            if(speeds.xDistance > drivePid.threshold){
                return 0.0;
            }
        }

        return drivePid.calculateSpeed(speeds.yDistance);
    }

    public double calculateTheta(DesiredSpeeds speeds){

        if(settings.contains(DesiredSettings.Ignore_Rotation)){
            return 0.0;
        }

        if(settings.contains(DesiredSettings.Drive_Then_Rotate)){
                if(!drivePid.underThreshold()){
                   return 0.0;
                }
        }

        return rotationPid.calculateSpeed(speeds.thetaDistance);
    }

    public Pose2d pointToHeading(Pose2d currentPosition){
        if(desiredCord.getRotation().getDegrees() == pointToHeadingAngle)
            return desiredCord;

        pointToHeadingAngle = angleFromCoordinate(desiredCord.getX(), desiredCord.getY(), currentPosition.getX(), currentPosition.getY());

        return new Pose2d(desiredCord.getX(), desiredCord.getY(), Rotation2d.fromDegrees(pointToHeadingAngle));
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
        Ignore_Rotation, Point_To_Heading, Ignore_Drive, Rotate_Then_Drive, Drive_Then_Rotate, X_Then_Y, Y_Then_X;
    }


    public static class DesiredSpeeds{

        public double x, y, theta;
        public double xDistance, yDistance, thetaDistance;


        public DesiredSpeeds(){
            this.x = 0;
            this.y = 0;
            this.theta = 0;
        }
    }

    public static class DesiredPID{
        public double p, i, d, iLimit, threshold;
        public double prevError = 0;
        public double errorRate = 0;
        public double sum = 0;
        public double timeStamp = Timer.getFPGATimestamp();

        public DesiredPID(boolean rotation){
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

        public DesiredPID(double p, double i, double d, double iLimit, double threshold){
            this.p = p;
            this.i = i;
            this.d = d;
            this.iLimit = iLimit;
            this.threshold = threshold;
        }

        public boolean underThreshold(){
            return Math.abs(prevError) < threshold;
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

        public static DesiredPID fromValues(double p, double i, double d, double iLimit, double threshold){
            return new DesiredPID(p, i, d, iLimit, threshold);
        }
        
    }

    private static interface DEFAULT {

        double SPEED_LIMIT = 0.03;
        
        enum DRIVE{
            P(0.045),
            I(0.03),
            D(0.00000000008),
            I_LIMIT(0.2),
            THRESHOLD(0.025);

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
            THRESHOLD(0.2);

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
