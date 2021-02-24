package frc.robot.files;

import java.io.File;
import java.io.FileWriter;
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

    public enum RIOLevel{
        DEBUG(0),
        IO(1), // change maybe but works for now
        SYSTEM(2),
        ERROR(3);
        private int id;
        private RIOLevel(int level){
            id = level;
        }
    
        public int getLevel(){
            return id;
        }
    
    }

    public class RIOWritter{

        long m_PreviousTime;
        double m_EventTime;
        FileWriter m_Writer; 
        String m_FileName, m_ClassName;
    
        public RIOWritter(String fileName, long time){
            m_FileName = fileName;
            m_PreviousTime = time;
            m_ClassName = getClass().getName();
            WriteHephaestus();
        }
    
        /**
         * Writes a String to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(String data){
            String output = data+"\n";
            WriteToFile(output);
        }
    
        /**
         * Writes a int to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(int data){
            String output = data+"\n";
            WriteToFile(output);
        }
        
        /**
         * Writes a boolean to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(boolean data){
            String output = data+"\n";
            WriteToFile(output);
        }
    
         /**
         * Writes a char to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(char data){
            String output = data+"\n";
            WriteToFile(output);
        }
    
        /**
         * Writes a byte to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(byte data){
            String output = data+"\n";
            WriteToFile(output);
        }
    
        /**
         * Writes a long to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(long data){
            String output = data+"\n";
            WriteToFile(output);
        }
        
        /**
         * Writes a double to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(double data){
            String output = data+"\n";
            WriteToFile(output);
        }
    
         /**
         * Writes a float to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(float data){
            String output = data+"\n";
            WriteToFile(output);
        }
    
         /**
         * Writes a short to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(short data){
            String output = data+"\n";
            WriteToFile(output);
        }
    
        ///////////////////////////////////////////////////////////////////
        
        /**
         * Writes a char with an RIOLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(char data, RIOLevel id, boolean... bypass){
            String output = data+"\n";
            boolean overRide = bypass != null ? true : false;
            if(!(overRide)){
                WriteToFile(output, id);
            }else{
                WriteToFileBypass(output, id);
            }
        }
    
        /**
         * Writes a String with an RIOLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(String data, RIOLevel id, boolean... bypass){
            String output = data+"\n";
            WriteToFile(output, id);
            boolean overRide = bypass != null ? true : false;
            if(!(overRide)){
                WriteToFile(output, id);
            }else{
                WriteToFileBypass(output, id);
            }
        }
    
         /**
         * Writes a boolean with an RIOLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(boolean data, RIOLevel id, boolean... bypass){
            String output = data+"\n";
            WriteToFile(output, id);
            boolean overRide = bypass != null ? true : false;
            if(!(overRide)){
                WriteToFile(output, id);
            }else{
                WriteToFileBypass(output, id);
            }
        }
    
        /**
         * Writes a int with an RIOLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(int data, RIOLevel id, boolean... bypass){
            String output = data+"\n";
            WriteToFile(output, id);
            boolean overRide = bypass != null ? true : false;
            if(!(overRide)){
                WriteToFile(output, id);
            }else{
                WriteToFileBypass(output, id);
            }
        }
        /**
         * Writes a short with an RIOLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
    
        public void Write(short data, RIOLevel id, boolean... bypass){
            String output = data+"\n";
            WriteToFile(output, id);
            boolean overRide = bypass != null ? true : false;
            if(!(overRide)){
                WriteToFile(output, id);
            }else{
                WriteToFileBypass(output, id);
            }
        }
    
        /**
         * Writes a byte with an RIOLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(byte data, RIOLevel id, boolean... bypass){
            String output = data+"\n";
            WriteToFile(output, id);
            boolean overRide = bypass != null ? true : false;
            if(!(overRide)){
                WriteToFile(output, id);
            }else{
                WriteToFileBypass(output, id);
            }
        }
    
         /**
         * Writes a long with an RIOLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(long data, RIOLevel id, boolean... bypass){
            String output = data+"\n";
            WriteToFile(output, id);
            boolean overRide = bypass != null ? true : false;
            if(!(overRide)){
                WriteToFile(output, id);
            }else{
                WriteToFileBypass(output, id);
            }
        }
    
        /**
         * Writes a double with an RIOLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(double data, RIOLevel id, boolean... bypass){
            String output = data+"\n";
            WriteToFile(output, id);
            boolean overRide = bypass != null ? true : false;
            if(!(overRide)){
                WriteToFile(output, id);
            }else{
                WriteToFileBypass(output, id);
            }
        }
    
        /**
         * Writes a float with an RIOLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(float data, RIOLevel id, boolean... bypass){
            String output = data+"\n";
            WriteToFile(output, id);
            boolean overRide = bypass != null ? true : false;
            if(!(overRide)){
                WriteToFile(output, id);
            }else{
                WriteToFileBypass(output, id);
            }
        }
    
        private void WriteToFile(String data, RIOLevel... id){
            int level = id != null ? id[0].getLevel() : 2;
            String identifier = id != null ? id.toString() : "OUTPUT"; 
            m_EventTime = (double)((System.nanoTime()-m_PreviousTime)/100000000);
            String line = "["+m_EventTime+"]"+"["+identifier+"] "+data;
    
            try{
                m_Writer = new FileWriter(m_FileName, true);
                m_Writer.write(line);
                m_Writer.close();
                m_PreviousTime = System.nanoTime();
    
                if(level >= RIOLog.getLogLevel()){
                    System.out.println(line);
                }
            }catch(IOException e){
                System.err.printf("[%s] Ran into an error writting to a file \n \t "+m_FileName,m_ClassName);
            }
    
        }
    
        private void WriteToFileBypass(String data, RIOLevel id){ 
            m_EventTime = (double)((System.nanoTime()-m_PreviousTime)/100000000);
            String line = "["+m_EventTime+"]"+"["+id+"] "+data;
            try{
                m_Writer = new FileWriter(m_FileName, true);
                m_Writer.write(line);
                m_Writer.close();
                System.out.println(line);
                m_PreviousTime = System.nanoTime();
            }catch(IOException e){
                System.err.printf("[%s] Ran into an error writting to a file \n \t "+m_FileName,m_ClassName);
            }
    
        }
    
        private void WriteHephaestus(){
            try{
                m_Writer = new FileWriter(m_FileName, true);
                m_Writer.write(Constants.FILES.HEPHAESTUS.get());
                m_Writer.close();
                System.out.println(Constants.FILES.HEPHAESTUS.get());
            }catch(IOException e){
                System.err.printf("[%s] Ran into an error writting to a file \n \t "+m_FileName,m_ClassName);
            }
        }
    }
}
