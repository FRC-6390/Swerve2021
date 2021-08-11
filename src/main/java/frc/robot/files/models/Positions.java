package frc.robot.files.models;

import java.util.List;

public class Positions {
    
    // public List <DesiredPostion> positions;

    // @Override
    // public String toString(){
    //     return getClass().getSimpleName() + " " + positions; 
    // }

    public class DesiredPostion {
        public double x;
        public double y;
        public double theta;
        public List<PID> pid;

        @Override
        public String toString(){
            return getClass().getSimpleName() + "[x=" + x + " y=" + y + " theta=" + theta + " " + pid + "]"; 
        }
    }

    public class PID{
        public boolean rotation;
        public double p;
        public double i;
        public double d;
        public double iLimit;
        public double threshold;

        @Override
        public String toString(){
            return getClass().getSimpleName() + "[rotation=" + rotation + " p=" + p + " i="+ i + " d="+ d + " iLimit="+ iLimit + " threshold="+ threshold + "]"; 
        }
    }


    
    public static void main(String[] args) throws Exception {
        JsonManager.readJson();
    }
}
