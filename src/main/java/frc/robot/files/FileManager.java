package frc.robot.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager implements Runnable {
    static String fileLocation, fileName, fileDest;
    static FileWriter writer;
    public static Thread thread;
    private static File file, destination, folder, usbFolder;
    private static long previousTime;
    private static double eventTime;


    public FileManager(String fileName) {
        this.fileLocation = "/home/lvuser/output";
        this.fileName = "/home/lvuser/output/" + fileName + ".txt";
        destination = new File("u/output/" + fileName + ".txt");
        thread = new Thread(this);
        thread.start();
        previousTime = System.nanoTime();
        file = new File(fileName);
        folder = new File(fileLocation);
        usbFolder = new File("u/output");
    }

    public void run() {

    }

    public static void Init() {
        try{
            folder.mkdirs();
            
            if(file.createNewFile()){
                System.out.println("File created: " + file.getName());
            } else {
              System.out.println("File, "+file.getName()+" already exists.");
            }

        }catch(IOException e){
            System.out.println("Error Creating "+file.getName());
            e.printStackTrace();
        }
    }

    public enum ID{
        DEBUG,
        INPUT,
        OUPUT,
        SYSTEM;
    }
 
    public static void WriteLn(ID id, String data){
        try{
            eventTime = ((System.nanoTime()-previousTime)/100000000);
            writer = new FileWriter(fileName, true);
            writer.write("["+eventTime+"]"+"["+id+"] "+data+"\n");
            writer.close();
            previousTime = System.nanoTime();
            System.out.println("Wrote To File");
        }catch(IOException e){
            System.out.println("Error Writting A File");
            e.printStackTrace();
        }
    }
    public static void MoveFileToUsb(){
        usbFolder.mkdir();
        file.renameTo(destination);

    }



}
