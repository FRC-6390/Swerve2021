package frc.robot.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager implements Runnable {
    static String fileLocation, fileName, fileDest;
    static FileWriter writer;
    public static Thread thread;
    private static File file, folder;
    private static long previousTime;
    private static double eventTime;

    public FileManager(String fileName) {
        this.fileLocation = "output/";
        this.fileName = "output/" + fileName + ".txt";
        thread = new Thread(this);
        thread.start();
        previousTime = System.nanoTime();
    }

    public static void main(String[] args) {
        new FileManager("test");
        Init();
        WriteLn(ID.DEBUG, "Debug Start");
        WriteLn(ID.SYSTEM, "System Start");
        try {
            thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        WriteLn(ID.SYSTEM, "System Start");

        try {
            thread.sleep(123);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        WriteLn(ID.SYSTEM, "System Start");
    }

    public void run(){
        
    }
    
    public static void Init(){
        try{
            folder = new File(fileLocation);
            folder.mkdirs();
            file = new File(fileName);
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

    enum ID{
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
            System.out.println("Writted To File");
        }catch(IOException e){
            System.out.println("Error Writting A File");
            e.printStackTrace();
        }
    }
    private File destFile = new File("u/output/"+fileName+".txt");
    public static void MoveFileToUsb(){
        try{
            file = new File("output/");
            file.mkdir();
            file = new File(fileName);
            file.renameTo("u/"+fileName);
        }catch(IOException e){
            System.out.println("Error Copying A File");
            e.printStackTrace();
        }

    }



}
