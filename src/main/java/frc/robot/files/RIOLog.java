package frc.robot.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.SimpleDateFormat;

import frc.robot.Constants;

public class RIOLog {

    private static File m_File, m_Destination, m_Folder, m_USBFolder;
    private static long m_Time;
    private static SimpleDateFormat m_formatter;
    private static Date m_date;
    private static String m_FileName, m_ClassName;
    private static int m_LogLevel;

    public static final RIOWritter out = null;


    public RIOLog(String fileName) {

        //Gets date to avoid same name files
        m_formatter= new SimpleDateFormat("ddMMyy-hhmmss.SSS");
        m_date = new Date(System.currentTimeMillis());

        m_FileName = m_formatter.format(m_date)+"-"+fileName;

        m_Time = System.nanoTime();
        //RoboRIO Files
        m_Folder = new File(Constants.FILES.ROBORIO_OUTPUT.get());
        m_File = new File(Constants.FILES.ROBORIO_OUTPUT.getFolder()+m_FileName+".txt");
        //USB Files
        m_USBFolder = new File(Constants.FILES.USB_OUTPUT.get());
        m_Destination = new File(Constants.FILES.USB_OUTPUT.getFolder() + m_FileName + ".txt");

        m_LogLevel = 0;
        
        m_ClassName = getClass().getName();
        new RIOWritter(Constants.FILES.ROBORIO_OUTPUT.getFolder()+m_FileName+".txt", m_Time);
    }

    /**
     * Creates any folders and fiels needed to make the log file
     * Sets up preperations to properly use the log writter
     * 
     * 
     * 
     */
    public static void Init() {
        if(!CreateRoboRIOFolder())
            System.err.printf("[%s] Ran into an error creating a folder \n \t "+m_Folder.getAbsolutePath(),m_ClassName);
        if(!CreateRoboRIOFile())
            System.err.printf("[%s] Ran into an error creating a file \n \t "+m_File.getAbsolutePath(),m_ClassName);


    }

    /**
     * Moves file to the first USB pluged into the RoboRio
     * This will automaticaly create any needed folders to move the file
     * if set to true the file on the roborio will stay after transfering to the usb
     * 
     * 
     */
    public static void MoveFileToUsb(boolean... keep){
        boolean keepingFile = keep != null ? keep[0] : false;
        if(!CreateUSBFolder())
            System.err.printf("[%s] Ran into an error creating a folder \n \t "+m_USBFolder.getAbsolutePath(),m_ClassName);
        if(!CreateUSBFile())
            System.err.printf("[%s] Ran into an error creating a file \n \t "+m_Destination.getAbsolutePath(),m_ClassName);

        if(!(keepingFile)){
            if(!(DeleteRoboRIoFile()))
                System.err.printf("[%s] Ran into an error deleting a file \n \t "+m_File.getAbsolutePath(),m_ClassName);
        }

    }

    private static boolean DeleteRoboRIoFile(){
        return m_File.delete();
    }

    private static boolean CreateRoboRIOFolder(){
        return m_Folder.mkdirs();
    }

    private static boolean CreateRoboRIOFile(){
        try{
             m_File.createNewFile();
             System.out.printf("[%s] File created! \n \t "+m_File.getAbsolutePath(),m_ClassName);
             return true;
        }catch(IOException ex){
            return false;
        }
    }

    private static boolean CreateUSBFolder(){
        return m_USBFolder.mkdir();
    }

    private static boolean CreateUSBFile(){
        try {
            Files.move(m_File.toPath(), m_Destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.printf("[%s] File created! \n \t "+m_Destination.getAbsolutePath(),m_ClassName);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Returns time when RIOLog was started
     * 
     * 
     * 
     * 
     */
    public static long getTime(){
        return m_Time;
    }

    /**
     * Everything in the RIOLog File gets saved to file but this method sets what get sent to robot console. The default log level is SYSTEM(2)
     * 
     * 
     * 
     * 
     */
    public static void setLogLevel(RIOLevel level){
        m_LogLevel = level.getLevel();
    }

     /**
     * Returns the log level previously set, the default log level is SYSTEM(2)
     * 
     * 
     * 
     * 
     */
    public static int getLogLevel(){
        return m_LogLevel;
    }
}
