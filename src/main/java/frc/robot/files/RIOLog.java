package frc.robot.files;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.text.SimpleDateFormat;

import frc.robot.Constants;


public class RioLog {

    private static File m_File, m_Destination, m_Folder, m_USBFolder;
    private static long m_Time;
    private static SimpleDateFormat m_formatter;
    private static Date m_date;
    private static String m_FileName, m_ClassName;
    private static int m_LogLevel;

    public static RioWritter out = null;


    public RioLog(String fileName) {

        //Gets date to avoid same name files
        m_formatter= new SimpleDateFormat("dd-MM-yy hh-mm-ss");
        m_date = new Date(System.currentTimeMillis());

        m_FileName = m_formatter.format(m_date)+"-"+fileName;

        m_Time = System.currentTimeMillis();
        //RoboRIO Files
        m_Folder = new File(Constants.FILES.ROBORIO_OUTPUT.get());
        m_File = new File(Constants.FILES.ROBORIO_OUTPUT.getFolder()+m_FileName+".txt");
        //USB Files
        m_USBFolder = new File(Constants.FILES.USB_OUTPUT.get());
        m_Destination = new File(Constants.FILES.USB_OUTPUT.getFolder() + m_FileName + ".txt");

        m_LogLevel = 0;
        
        m_ClassName = getClass().getName();
        out = new RioWritter(m_File.getAbsolutePath(), m_Time);
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
            System.err.printf("\n[%s] Ran into an error creating a folder \n \t "+m_Folder.getAbsolutePath(),m_ClassName);
        if(!CreateRoboRIOFile())
            System.err.printf("\n[%s] Ran into an error creating a file \n \t "+m_File.getAbsolutePath(),m_ClassName);


    }

     /**
     * Moves file to the first USB pluged into the RoboRio
     * This will automaticaly create any needed folders to move the file
     * if set to true the file on the roborio will stay after transfering to the usb
     * 
     * 
     */
    public static void MoveFileToUsb(){MoveFileToUsb(false);}

    /**
     * Moves file to the first USB pluged into the RoboRio
     * This will automaticaly create any needed folders to move the file
     * if set to true the file on the roborio will stay after transfering to the usb
     * 
     * 
     */
    public static void MoveFileToUsb(boolean keep){
        if(!CreateUSBFolder())
            System.err.printf("\n[%s] Ran into an error creating a folder \n \t "+m_USBFolder.getAbsolutePath(),m_ClassName);
        if(!CreateUSBFile())
            System.err.printf("\n[%s] Ran into an error creating a file \n \t "+m_Destination.getAbsolutePath(),m_ClassName);

        if(!(keep)){
            if(!(DeleteRoboRIoFile()))
                System.err.printf("\n[%s] Ran into an error deleting a file \n \t "+m_File.getAbsolutePath(),m_ClassName);
        }

    }

    private static boolean DeleteRoboRIoFile(){
        return m_File.delete();
    }

    private static boolean CreateRoboRIOFolder(){
        return m_Folder.exists() == false ? m_Folder.mkdirs() : true;
    }

    private static boolean CreateRoboRIOFile(){
        try{
             m_File.createNewFile();
             System.out.printf("\n[%s] File created! \n \t "+m_File.getAbsolutePath(),m_ClassName);
             return true;
        }catch(IOException ex){
            return false;
        }
    }

    private static boolean CreateUSBFolder(){
        return m_USBFolder.exists() == false ? m_USBFolder.mkdirs() : true;
    }

    private static boolean CreateUSBFile(){
        try {
            Files.copy(m_File.toPath(), m_Destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.printf("\n[%s] File created! \n \t "+m_Destination.getAbsolutePath(),m_ClassName);
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
    public static void setLogLevel(RioLevel level){
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

    public enum RioLevel{
        DEBUG(0),
        IO(1),      // change maybe but works for now
        SYSTEM(2),
        ERROR(3);
        private int id;
        private RioLevel(int level){
            id = level;
        }
    
        public int getLevel(){
            return id;
        }
    
    }

    public class RioWritter{

        long m_StartTime;
        double m_EventTime;
        FileWriter m_Writer; 
        String m_FileName, m_ClassName;
    
        private boolean defualtBypassValue = false;
    
        public RioWritter(String fileName, long time){
            m_FileName =  fileName;
            m_StartTime = time;
            m_ClassName = getClass().getName();
            WriteHephaestus();
        }
        /**
         * Writes a String to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(String data){Write(data, RioLevel.SYSTEM,defualtBypassValue);}
    
        /**
         * Writes a int to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(int data){Write(data, RioLevel.SYSTEM,defualtBypassValue);}
        
        /**
         * Writes a boolean to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(boolean data){Write(data, RioLevel.SYSTEM,defualtBypassValue);}
    
         /**
         * Writes a char to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(char data){Write(data, RioLevel.SYSTEM,defualtBypassValue);}
    
        /**
         * Writes a byte to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(byte data){Write(data, RioLevel.SYSTEM,defualtBypassValue);}
    
        /**
         * Writes a long to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(long data){Write(data, RioLevel.SYSTEM,defualtBypassValue);}
        
        /**
         * Writes a double to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(double data){Write(data, RioLevel.SYSTEM,defualtBypassValue);}
    
         /**
         * Writes a float to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(float data){Write(data, RioLevel.SYSTEM,defualtBypassValue);}
    
         /**
         * Writes a short to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(short data){Write(data, RioLevel.SYSTEM,defualtBypassValue);}
    
        ///////////////////////////////////////////////////////////////////
        /**
         * Writes a String to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(String data, RioLevel id){Write(data, id ,defualtBypassValue);}
    
        /**
         * Writes a int to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(int data, RioLevel id){Write(data, id,defualtBypassValue);}
        
        /**
         * Writes a boolean to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(boolean data, RioLevel id){Write(data, id,defualtBypassValue);}
    
         /**
         * Writes a char to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(char data, RioLevel id){Write(data, id,defualtBypassValue);}
    
        /**
         * Writes a byte to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(byte data, RioLevel id){Write(data, id,defualtBypassValue);}
    
        /**
         * Writes a long to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(long data, RioLevel id){Write(data, id,defualtBypassValue);}
        
        /**
         * Writes a double to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(double data, RioLevel id){Write(data, id,defualtBypassValue);}
    
         /**
         * Writes a float to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(float data, RioLevel id){Write(data, id,defualtBypassValue);}
    
         /**
         * Writes a short to the file created by RIOLog
         * defaults log level to SYSTEM
         */
        public void Write(short data, RioLevel id){Write(data, id,defualtBypassValue);}
        //////////////////////////////////////////////////////////////////
        /**
         * Writes a char with an RioLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(char data, RioLevel id, boolean bypass){
            String output = data+"\n";
            if(bypass){
                WriteToFileBypass(output, id);
            }else{
                WriteToFile(output, id);
            }
        }
    
        /**
         * Writes a String with an RioLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(String data, RioLevel id, boolean bypass){
            String output = data+"\n";
            if(bypass){
                WriteToFileBypass(output, id);
            }else{
                WriteToFile(output, id);
            }
        }
    
         /**
         * Writes a boolean with an RioLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(boolean data, RioLevel id, boolean bypass){
            String output = data+"\n";
            if(bypass){
                WriteToFileBypass(output, id);
            }else{
                WriteToFile(output, id);
            }
        }
    
        /**
         * Writes a int with an RioLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(int data, RioLevel id, boolean bypass){
            String output = data+"\n";
            if(bypass){
                WriteToFileBypass(output, id);
            }else{
                WriteToFile(output, id);
            }
        }
        /**
         * Writes a short with an RioLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
    
        public void Write(short data, RioLevel id, boolean bypass){
            String output = data+"\n";
            if(bypass){
                WriteToFileBypass(output, id);
            }else{
                WriteToFile(output, id);
            }
        }
    
        /**
         * Writes a byte with an RioLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(byte data, RioLevel id, boolean bypass){
            String output = data+"\n";
            if(bypass){
                WriteToFileBypass(output, id);
            }else{
                WriteToFile(output, id);
            }
        }
    
         /**
         * Writes a long with an RioLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(long data, RioLevel id, boolean bypass){
            String output = data+"\n";
            if(bypass){
                WriteToFileBypass(output, id);
            }else{
                WriteToFile(output, id);
            }
        }
    
        /**
         * Writes a double with an RioLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(double data, RioLevel id, boolean bypass){
            String output = data+"\n";
            if(bypass){
                WriteToFileBypass(output, id);
            }else{
                WriteToFile(output, id);
            }
        }
    
        /**
         * Writes a float with an RioLevel to the file created by RIOLog
         * True will bypass log level set
         * The default log level is SYSTEM(2)
         */
        public void Write(float data, RioLevel id, boolean bypass){
            String output = data+"\n";
            if(bypass){
                WriteToFileBypass(output, id);
            }else{
                WriteToFile(output, id);
            }
        }
    
        private void WriteToFile(String data, RioLevel id){
            int level = id.getLevel();
            String identifier = id.toString(); 
            m_EventTime = (((double)System.currentTimeMillis()-m_StartTime)/100);
            String line = "["+m_EventTime+"]"+"["+identifier+"] "+data;
    
            try{
                m_Writer = new FileWriter(m_FileName, true);
                m_Writer.write(line);
                m_Writer.close();
    
                if(level >= RioLog.getLogLevel()){
                    System.out.println(line);
                }
            }catch(IOException e){
                System.err.printf("\n[%s] Ran into an error writting to a file \n \t "+m_FileName,m_ClassName);
            }
    
        }
    
        private void WriteToFileBypass(String data, RioLevel id){ 
            m_EventTime = (((double)System.currentTimeMillis()-m_StartTime)/100);
            String line = "["+m_EventTime+"]"+"["+id+"] "+data;
            try{
                m_Writer = new FileWriter(m_FileName, true);
                m_Writer.write(line);
                m_Writer.close();
                System.out.println(line);
            }catch(IOException e){
                System.err.printf("\n[%s] Ran into an error writting to a file \n \t "+m_FileName,m_ClassName);
            }
    
        }
    
        private void WriteHephaestus(){
            try{
                m_Writer = new FileWriter(m_FileName, true);
                m_Writer.write(Constants.FILES.HEPHAESTUS.get());
                m_Writer.close();
                System.out.println(Constants.FILES.HEPHAESTUS.get());
            }catch(IOException e){
                System.err.printf("\n[%s] Ran into an error writting to a file \n \t "+m_FileName,m_ClassName);
            }
        }
    }

}
