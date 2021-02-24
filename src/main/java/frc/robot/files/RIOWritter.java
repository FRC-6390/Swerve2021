package frc.robot.files;

import java.io.FileWriter;
import java.io.IOException;

import frc.robot.Constants;

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
