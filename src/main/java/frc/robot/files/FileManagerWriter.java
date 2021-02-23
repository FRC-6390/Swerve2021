package frc.robot.files;

import java.io.FileWriter;
import java.io.IOException;

import frc.robot.files.FileManager.ID;

public class FileManagerWriter {

    long m_PreviousTime;
    double m_EventTime;
    FileWriter m_Writer; 
    String m_FileName;

    public FileManagerWriter(String fileName, long time){
        m_FileName = fileName;
        m_PreviousTime = time;
    }

    /**
     * Writes to the file created by FileManager
     */
    public void Write(String data){
        String output = data+"\n";
        WriteToFile(output);
    }
    
    /**
     * Writes to the file created by FileManager
     */
    public void Write(String data, ID id){
        String output = data+"\n";
        WriteToFile(output, id);
    }


    private void WriteToFile(String data, ID... id){
        String identifier = id != null ? id.toString() : "OUTPUT"; 
        m_EventTime = ((System.nanoTime()-m_PreviousTime)/100000000);
        
        try{
            m_Writer = new FileWriter(m_FileName, true);
            m_Writer.write("["+m_EventTime+"]"+"["+identifier+"] "+data);
            m_Writer.close();
            m_PreviousTime = System.nanoTime();
        }catch(IOException e){
            System.err.printf("[%s] Ran into an error writting to a file \n \t "+m_FileName,Class.class.getName());
        }

    }
}
