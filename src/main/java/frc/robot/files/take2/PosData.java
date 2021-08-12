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
    private List<Double> drive = null;

    @SerializedName("p")
    private double p;

    @SerializedName("i")
    private double i;



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


    public double getP() {
        return p;
    }
    public void setP(double p) {
        this.p = p;
    }
    

    public double getI() {
        return i;
    }
    public void seti(double i) {
        this.i = i;
    }

}