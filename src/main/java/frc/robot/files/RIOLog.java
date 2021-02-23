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
    SimpleDateFormat formatter;
    Date date;
    String m_FileName;
    
    public static final RIOWritter out = null;


    public RIOLog(String fileName) {

        //Gets date to avoid same name files
        formatter= new SimpleDateFormat("ddMMyy-hhmmss.SSS");
        date = new Date(System.currentTimeMillis());

        m_FileName = formatter.format(date)+"-"+fileName;

        m_Time = System.nanoTime();
        //RoboRIO Files
        m_Folder = new File(Constants.FILES.ROBORIO_OUTPUT.get());
        m_File = new File(Constants.FILES.ROBORIO_OUTPUT.getFolder()+m_FileName+".txt");
        //USB Files
        m_USBFolder = new File(Constants.FILES.USB_OUTPUT.get());
        m_Destination = new File(Constants.FILES.USB_OUTPUT.getFolder() + m_FileName + ".txt");

        new RIOWritter(Constants.FILES.ROBORIO_OUTPUT.getFolder()+m_FileName+".txt", m_Time);
    }

    public static void Init() {
        if(!CreateRoboRIOFolder())
            System.err.printf("[%s] Ran into an error creating a folder \n \t "+m_Folder.getAbsolutePath(),Class.class.getName());
        if(!CreateRoboRIOFile())
            System.err.printf("[%s] Ran into an error creating a file \n \t "+m_File.getAbsolutePath(),Class.class.getName());


    }

    public static void MoveFileToUsb(){
        if(!CreateUSBFolder())
            System.err.printf("[%s] Ran into an error creating a folder \n \t "+m_USBFolder.getAbsolutePath(),Class.class.getName());
        if(!CreateUSBFile())
            System.err.printf("[%s] Ran into an error creating a file \n \t "+m_Destination.getAbsolutePath(),Class.class.getName());
    }

    private static boolean CreateRoboRIOFolder(){
        return m_Folder.mkdirs();
    }

    private static boolean CreateRoboRIOFile(){
        try{
             m_File.createNewFile();
             System.out.printf("[%s] File created! \n \t "+m_File.getAbsolutePath(),Class.class.getName());
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
            System.out.printf("[%s] File created! \n \t "+m_Destination.getAbsolutePath(),Class.class.getName());
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static long getTime(){
        return m_Time;
    }



}
