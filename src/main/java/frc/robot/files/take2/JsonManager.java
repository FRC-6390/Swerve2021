package frc.robot.files.take2;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

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

    public static void main(String[] args) throws Exception {
        String file = "C:\\Users\\Mohammad\\Documents\\GitHub\\Swerve2021\\src\\main\\java\\frc\\robot\\files\\take2\\test1.json";
        String json = readFileAsString(file);
        System.out.println(json);

        JsonManager manager = new Gson().fromJson(json, new TypeToken<JsonManager>() {}.getType());

        List<PosData> posList = manager.getPositions();
        List<Double> xList = new ArrayList<>();
        List<Double> yList = new ArrayList<>();
        List<Double> thetaList = new ArrayList<>();
        List<List<Double>> driveLists = new ArrayList<List<Double>>();

        
        for (PosData pos : posList) {
            xList.add(pos.getX());
            yList.add(pos.getY());
            thetaList.add(pos.getTheta());

            for(PidData pid : pos.getDrive()){
                List<Double> drivePID = new ArrayList<>();
                drivePID.add(pid.getP());
                drivePID.add(pid.getI());
                drivePID.add(pid.getD());
                driveLists.add(drivePID);
                break;
            }

        }

        System.out.println(xList);
        System.out.println(yList);
        System.out.println(thetaList);
        System.out.println(driveLists);
    }
}
