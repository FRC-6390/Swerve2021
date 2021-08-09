package frc.robot.files;

import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import frc.robot.files.models.Positions;

public class JsonManager {
   
    public JsonManager(){

    }

    public static void creatJson(Positions model){
    }

    public static String readFileAsString(String file)throws Exception
    {
        return new String(Files.readAllBytes(Paths.get(file)));
    }


    public static void main(String[] args) throws Exception {
        String file = "C:\\Users\\Mohammad\\Documents\\GitHub\\Swerve2021\\src\\main\\java\\frc\\robot\\files\\models\\test2.json";
        String json = readFileAsString(file);
        System.out.println(json);

        // This is to be used if the array is the root element of the json file 
        Type posTypeList = new TypeToken<ArrayList<Positions>>(){}.getType();
        List<Positions> pos = new Gson().fromJson(json, posTypeList);

        //Positions pos = new Gson().fromJson(json, Positions.class);


        for(int x = 0; x < pos.size(); x++){
            System.out.println(pos.get(x));
        }
        

    }

}
