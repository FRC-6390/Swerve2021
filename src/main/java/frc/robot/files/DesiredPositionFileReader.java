package frc.robot.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.file.FileSystem;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import edu.wpi.first.wpilibj.Filesystem;
import frc.robot.subsystems.drivetrain.DesiredPosition;

public class DesiredPositionFileReader {

    private Gson gson;
    private InputStreamReader inputStream;
    private JsonReader reader;

    public DesiredPositionFileReader(String fileName) throws FileNotFoundException{
        gson = new Gson();
        inputStream = new InputStreamReader(new FileInputStream(Filesystem.getDeployDirectory()+"/"+fileName));
         reader = new JsonReader(inputStream);
    }

    public List<double[]> getPositionList(){
        double x,y,theta, minDrive, maxDrive, minRotation, maxRotation, threshold;
        //gson.fromJson(reader, DesiredPosition.class);
        
        return null;
    } 
}
