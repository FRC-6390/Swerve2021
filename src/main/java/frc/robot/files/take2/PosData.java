package frc.robot.files.take2;

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


    public double getX() {
        return x;
    }
    public void setPartNumber(double x) {
        this.x = x;
    }


    public double getY() {
        return y;
    }
    public void setPartType(double y) {
        this.y = y;
    }


    public double getTheta() {
        return theta;
    }
    public void setId(double theta) {
        this.theta = theta;
    }


    public List<PidData> getDrive(){
        return drive;
    }
    public void setDrive(List<PidData> drive){
        this.drive = drive;
    }

}