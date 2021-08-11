package frc.robot.files.models;

import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonManager {
   
    public JsonManager(){

    }

    public static void creatJson(Positions model){
    }

    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }

    public static List<Positions.DesiredPostion> pos;
    public static List<Positions.PID> pid;

    public static void readJson() throws Exception{
        String file = "C:\\Users\\Mohammad\\Documents\\GitHub\\Swerve2021\\src\\main\\java\\frc\\robot\\files\\models\\test.json";
        String json = readFileAsString(file);
        System.out.println(json);

        // This is to be used if the array is the root element of the json file 
        Type posTypeList = new TypeToken<ArrayList<Positions.DesiredPostion>>(){}.getType();
        pos = new Gson().fromJson(json, posTypeList);

        //Positions pos = new Gson().fromJson(json, Positions.class);


        for(int x = 0; x < pos.size(); x++){
            System.out.println(pos.get(x));
        }

        System.out.println("-------------------------------");
        System.out.println(pid.get(0));
    }

    public static void main(String[] args) throws Exception {
        JsonManager.readJson();
        System.out.println("___________________________________");
    }


}
