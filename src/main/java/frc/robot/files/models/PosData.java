package frc.robot.files.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class PosData {

    @SerializedName("x")
    private double x;

    @SerializedName("y")
    private double y;

    @SerializedName("theta")
    private double theta;

    @SerializedName("drive")
    private List<PidData> drive = null;

    @SerializedName("rotation")
    private List<PidData> rotation = null;


    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }


    public double getY() {
        return y;
    }
    public void setY(double y) {
        this.y = y;
    }


    public double getTheta() {
        return theta;
    }
    public void setTheta(double theta) {
        this.theta = theta;
    }


    public List<PidData> getDrive(){
        return drive;
    }
    public void setDrive(List<PidData> drive){
        this.drive = drive;
    }

    public List<PidData> getRotation(){
        return rotation;
    }
    public void setRotation(List<PidData> rotation){
        this.rotation = rotation;
    }
}