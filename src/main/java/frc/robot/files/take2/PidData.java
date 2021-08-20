package frc.robot.files.take2;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class PidData {

    
    @SerializedName("p")
    private double p;

    @SerializedName("i")
    private double i;

    @SerializedName("d")
    private double d;

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

    public double getD() {
        return i;
    }
    public void setD(double d) {
        this.d = d;
    }
}
