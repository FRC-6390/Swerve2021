package frc.robot.files.Models;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.text.NumberFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.text.DecimalFormat;
import java.math.BigDecimal;

import edu.wpi.first.wpilibj.Filesystem;

public class JsonManager {

    @SerializedName("pos")
    private List<PosData> pos = null;

    public List<PosData> getPositions() {
        return pos;
    }
    public void setPositions(List<PosData> pos) {
        this.pos = pos;
    }


    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    private static InputStreamReader inputStream;
    public static List<PosData> posList;
    public static List<Double> xList;
    public static List<Double> yList;
    public static List<Double> thetaList;
    public static List<List<Double>> driveLists;
    public static List<List<Double>> rotationLists;

    
    // public static void main(String[] args) throws Exception {
    public void readJson(String autoName) throws Exception {

        //for testing purposes
        // String file = "C:\\Users\\Mohammad\\Documents\\GitHub\\Swerve2021\\src\\main\\java\\frc\\robot\\files\\models\\" + autoName + ".json";
        
        //needs to be tested
        String file = Filesystem.getDeployDirectory()+"/"+ autoName + ".json";
        System.out.println(file);
        
        String json = readFileAsString(file);
        System.out.println(json);

        JsonManager manager = new Gson().fromJson(json, new TypeToken<JsonManager>() {}.getType());

        posList = manager.getPositions();
        xList = new ArrayList<>();
        yList = new ArrayList<>();
        thetaList = new ArrayList<>();
        driveLists = new ArrayList<List<Double>>();
        rotationLists = new ArrayList<List<Double>>();
        
        for (PosData pos : posList) {
            xList.add(pos.getX());
            yList.add(pos.getY());
            thetaList.add(pos.getTheta());

            
            for(PidData pid : pos.getDrive()){
                List<Double> drivePID = new ArrayList<>();
                drivePID.add(pid.getP());
                drivePID.add(pid.getI());
                drivePID.add(pid.getD());
                drivePID.add(pid.getILimit());
                drivePID.add(pid.getThreshold());
                driveLists.add(drivePID);
                break;
            }

            for(PidData pid : pos.getRotation()){
                List<Double> rotationPID = new ArrayList<>();
                rotationPID.add(pid.getP());
                rotationPID.add(pid.getI());
                rotationPID.add(pid.getD());
                rotationPID.add(pid.getILimit());
                rotationPID.add(pid.getThreshold());
                rotationLists.add(rotationPID);
                break;
            }

        }

        System.out.println(posList.size());
        System.out.println(xList);
        System.out.println(yList);
        System.out.println(thetaList);
        System.out.println(driveLists);
        System.out.println(rotationLists);
    }
    
}