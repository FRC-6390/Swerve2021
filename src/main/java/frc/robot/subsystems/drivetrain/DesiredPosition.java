package frc.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DesiredPosition {

    //brain hurt but brain got it working so the sacrifice was necessary

    private static final double DEFAULT_THRESHOLD = 0.05;

    private Pose2d desiredPos;
    private DesiredPositionSpeeds desiredSpeeds;
    private double threshold;

    public DesiredPosition(Pose2d desiredPos) {
        this(desiredPos, new DesiredPositionSpeeds());
    }

    public DesiredPosition(Pose2d desiredPos, DesiredPositionSpeeds desiredSpeeds) {
        this(desiredPos, desiredSpeeds, DEFAULT_THRESHOLD);
    }

    public DesiredPosition(Pose2d desiredPos, DesiredPositionSpeeds desiredSpeeds, double threshold) {
        this.desiredPos = desiredPos;
        this.desiredSpeeds = desiredSpeeds;
        this.threshold = threshold;
    }

    public Pose2d getDesiredPosition() {
        return desiredPos;
    }
    /**
     * gets all the values relating to the robot
     * @param currentPos the current robot position
     * @return all values related to the robot (current x, current y, current theta, speed x, speed y, speed theta, distance x, distance y, distance theta)
     */
    public double[] getValues(Pose2d currentPos) {

        double[] distances = getDistances(currentPos);
        double[] speeds = getSpeed(currentPos);

        return new double[] { currentPos.getX(), currentPos.getY(), currentPos.getRotation().getDegrees(),speeds[0],speeds[1],speeds[2],distances[0],distances[1],distances[2]};
    }

    /**
     * gets the speed of the robot in each dimension within the speed limit givin
     * @param currentPos the current robot position
     * @return speeds (x,y,theta)
     */
    public double[] getSpeed(Pose2d currentPos) {

        double[] distances = getDistances(currentPos); 
        double[] speeds = new double[distances.length];
        
        for (int i = 0; i < speeds.length; i++) {
            if(i != distances.length-1)
                speeds[i] = (Math.abs(distances[i]) <= desiredSpeeds.drive[0] ? desiredSpeeds.drive[0] : Math.abs(distances[i])) >= desiredSpeeds.drive[1] ? desiredSpeeds.drive[1] : Math.abs(distances[i]);
            else
                speeds[i] = (Math.abs(distances[i]) <= desiredSpeeds.rotation[0] ? desiredSpeeds.rotation[0] : Math.abs(distances[i])) >= desiredSpeeds.rotation[1] ? desiredSpeeds.rotation[1] : Math.abs(distances[i]);
            
            if(distances[i] < 0)
                speeds[i] *= -1;
        }

       return speeds;
    }

    /**
     * gets how far away the robot is in each dimension in meters and degrees for theta
     * @param currentPos the current robot position
     * @return the distance in meter from its desired location (x,y,theta)
     */
    public double[] getDistances(Pose2d currentPos) {
        double distanceX = desiredPos.getX() - currentPos.getX();
        double distanceY = desiredPos.getY() - currentPos.getY();
        double distanceTheta = desiredPos.getRotation().getDegrees() - currentPos.getRotation().getDegrees(); 

        return new double[] { distanceX,distanceY,distanceTheta};
    }

    /**
     * Checks distances to see if all are within the threshold indacating it has reached it's location
     * @param currentPos the current robot position
     * @return whether or not the robot has reached its desired position
     */
    public boolean atDesiredPosition(Pose2d currentPos){

        boolean[] thresholds =  thresholdCheck(getDistances(currentPos));
       
        for (int i = 0; i < thresholds.length; i++){
            if(!thresholds[i])
                return false;
        } 

        return true;
    }

    /**
     * Compares the distance to the threshold
     * @param distances distances x, y, theta
     * @return boolean array (x,y,theta)
     */
    public boolean[] thresholdCheck(double[] distances){
        boolean[] thresholds = new boolean[distances.length];
        for (int i = 0; i < distances.length; i++) {
            thresholds[i] = Math.abs(distances[i]) < threshold;
        }

        return thresholds;
    }

    public static DesiredPosition fromCoordinates(double x, double y, double theta){
        return fromCoordinates(x, y, theta, new DesiredPositionSpeeds());
    }

    public static DesiredPosition fromCoordinates(double x, double y, double theta, DesiredPositionSpeeds speeds){
        return fromCoordinates(x, y, theta, speeds, DEFAULT_THRESHOLD);
    }

    public static DesiredPosition fromCoordinates(double x, double y, double theta, DesiredPositionSpeeds speeds, double threshold){
        return new DesiredPosition(new Pose2d(x,y,Rotation2d.fromDegrees(theta)),speeds, threshold);
    }

    public static class DesiredPositionSpeeds {

        private static final double[] DEFAULT_SPEEDS = new double[] {0.1,0.3};

        public double[] drive, rotation;

        public DesiredPositionSpeeds(){
            this(DEFAULT_SPEEDS, DEFAULT_SPEEDS);
        }

        public DesiredPositionSpeeds(double[] drive, double[] rotation){
            this.drive = drive;
            this.rotation = rotation;
        }

        public DesiredPositionSpeeds(double minDrive, double maxDrive, double minRotation, double maxRotation){
            this.drive = new double[]{minDrive, maxDrive};
            this.rotation =new double[]{minRotation, maxRotation};
        }
        
    }
 
    //Simulated testing

    // public static void main(String[] args) {
    //     DesiredPosition desiredPosition = new DesiredPosition(new Pose2d(10, 10, Rotation2d.fromDegrees(90.0)));
    //     double[] values = desiredPosition.getValues(new Pose2d(2, 0, Rotation2d.fromDegrees(0.0)));
    //     for (int i = 0; i <= 20; i++) {
    //         values = desiredPosition.getValues(new Pose2d(0.1*i, 0.1*i, Rotation2d.fromDegrees(9*i)));
    //         System.out.printf("====%d====%nX - (%f), Y - (%f), Theta -(%f)%nX speed - (%f), Y speed - (%f), Theta speed - (%f)%nX distance - (%f), Y distance - (%f), Theta distance - (%f)%n", i,values[0], values[1], values[2],values[3], values[4], values[5],values[6], values[7], values[8]);

    //     }

    //     int t = 1;
    //     System.out.println("\nsimulated test\n");
    //     Pose2d currentPos = new Pose2d(0, 0, Rotation2d.fromDegrees(0.0));
    //     double[] speed = desiredPosition.getSpeed(currentPos);
    //     double[] data = desiredPosition.getValues(currentPos);
    //     while(!desiredPosition.atDesiredPosition(currentPos)){
    //         speed = desiredPosition.getSpeed(currentPos);
    //         data = desiredPosition.getValues(currentPos);
    //         currentPos = new Pose2d(currentPos.getX()+speed[0], currentPos.getY()+speed[1], Rotation2d.fromDegrees(currentPos.getRotation().getDegrees()+speed[2]));
    //         System.out.printf("====%d====%nX - (%f), Y - (%f), Theta -(%f)%nX speed - (%f), Y speed - (%f), Theta speed - (%f)%nX distance - (%f), Y distance - (%f), Theta distance - (%f)%n", t,data[0], data[1], data[2],speed[0], speed[1], speed[2],data[6], data[7], data[8]);
    //         t++;
    //     }
    //     System.out.printf("====DONE====%nX - (%f), Y - (%f), Theta -(%f)%nX speed - (%f), Y speed - (%f), Theta speed - (%f)%nX distance - (%f), Y distance - (%f), Theta distance - (%f)%n",data[0], data[1], data[2],speed[0], speed[1], speed[2],data[6], data[7], data[8]);
        
    // }
}
