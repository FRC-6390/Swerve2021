package frc.robot.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    String fileName;
    FileWriter writer;
    FileManager(String fileName){
        this.fileName = "/output/"+fileName+".txt";
    }
    
    private File file;
    public void Init(){
        try{
            file = new File(fileName);
            if(file.createNewFile()){
                System.out.println("File created: " + file.getName());
            } else {
              System.out.println("File already exists.");
            }

        }catch(IOException e){
            System.out.println("Error Creating File");
            e.printStackTrace();
        }
    }

    enum ID{
        DEBUG,
        INPUT,
        OUPUT,
        SYSTEM;
    }
    
    public void WriteLn(ID id, String data){
        try{
            writer = new FileWriter(fileName);
            writer.write("["+id+"] "+data);
            writer.close();
            System.out.println("Writted To File");
        }catch(IOException e){
            System.out.println("Error Writting A File");
            e.printStackTrace();
        }
    }

    public void MoveFileToUsb(){

    }



}
