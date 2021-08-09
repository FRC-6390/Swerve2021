// package frc.robot.files;

// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;
// import java.io.InputStream;
// import java.io.InputStreamReader;
// import java.io.Reader;
// import java.lang.reflect.Type;
// import java.nio.file.FileSystem;
// import java.nio.file.FileSystems;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.util.List;
// import java.util.Map;

// import com.fasterxml.jackson.core.JsonParser;
// import com.google.gson.Gson;
// import com.google.gson.reflect.TypeToken;
// import com.google.gson.stream.JsonReader;

// import edu.wpi.first.wpilibj.Filesystem;
// import frc.robot.subsystems.drivetrain.DesiredPosition;

// public class DesiredPositionFileReader {
//     // public DesiredPositionFileReader(String fileName) throws FileNotFoundException{
//     //     gson = new Gson();
//     //     //inputStream = new InputStreamReader(new FileInputStream(Filesystem.getDeployDirectory()+"/autoRoutes/"+fileName));
//     //     reader = new JsonReader(inputStream);
//     // }
 

//     public static void main(String[] args) {

//         try {
//             // create Gson instance
//             Gson gson = new Gson();
            
//             // create a reader
//             String filePath = "C:\\Users\\Mohammad\\Documents\\GitHub\\Swerve2021\\src\\main\\java\\frc\\robot\\files\\testing\\auto1.json";
//             Reader reader = Files.newBufferedReader(Paths.get(filePath));
        
//             // convert JSON string to User object
//             User user = gson.fromJson(reader,User.class);

//             // print user object
//             System.out.println(user);

        
//             // close reader
//             reader.close();
        
//         } catch (Exception ex) {
//             ex.printStackTrace();
//         }
//     }

// }
