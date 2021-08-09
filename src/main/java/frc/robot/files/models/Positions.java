package frc.robot.files.models;

import java.util.List;

import com.google.gson.Gson;

public class Positions {
    
    // public DesiredPostion positions[];
   
    // public static class DesiredPostion {
    
    //     public double x;
    //     public double y;
    //     public double theta;
    //     public String settings[];
    //     public PID pid[];
        
    // }

    // public static class PID{

    //     public boolean rotation;
    //     public double p;
    //     public double i;
    //     public double d;
    //     public double ilimit;
    //     public double threshold;
    // }

    String name;
    String website;
    List<Founder> founders;

    @Override
    public String toString(){
        return getClass().getSimpleName() + "[name=" + name + " website=" + website + " founders"+ founders + "]"; 
    }

    public class Founder{
        String name;
        int x;

        @Override
        public String toString(){
            return getClass().getSimpleName() + "[name=" + name + " x=" + x + "]"; 
        }
    }

}
