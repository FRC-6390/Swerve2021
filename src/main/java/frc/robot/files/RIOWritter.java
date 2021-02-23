package frc.robot.files;

import java.io.FileWriter;
import java.io.IOException;

public class RIOWritter{

    long m_PreviousTime;
    double m_EventTime;
    FileWriter m_Writer; 
    String m_FileName;

    public RIOWritter(String fileName, long time){
        m_FileName = fileName;
        m_PreviousTime = time;
    }

    /**
     * Writes a String to the file created by FileManager
     */
    public void Write(String data){
        String output = data+"\n";
        WriteToFile(output);
    }

    /**
     * Writes a int to the file created by FileManager
     */
    public void Write(int data){
        String output = data+"\n";
        WriteToFile(output);
    }
    
    /**
     * Writes a boolean to the file created by FileManager
     */
    public void Write(boolean data){
        String output = data+"\n";
        WriteToFile(output);
    }

     /**
     * Writes a char to the file created by FileManager
     */
    public void Write(char data){
        String output = data+"\n";
        WriteToFile(output);
    }

    /**
     * Writes a byte to the file created by FileManager
     */
    public void Write(byte data){
        String output = data+"\n";
        WriteToFile(output);
    }

    /**
     * Writes a long to the file created by FileManager
     */
    public void Write(long data){
        String output = data+"\n";
        WriteToFile(output);
    }
    
    /**
     * Writes a double to the file created by FileManager
     */
    public void Write(double data){
        String output = data+"\n";
        WriteToFile(output);
    }

     /**
     * Writes a float to the file created by FileManager
     */
    public void Write(float data){
        String output = data+"\n";
        WriteToFile(output);
    }

     /**
     * Writes a short to the file created by FileManager
     */
    public void Write(short data){
        String output = data+"\n";
        WriteToFile(output);
    }

    ///////////////////////////////////////////////////////////////////
    
    /**
     * Writes a char with an RIOLevel to the file created by FileManager
     */
    public void Write(char data, RIOLevel id){
        String output = data+"\n";
        WriteToFile(output, id);
    }

    /**
     * Writes a String with an RIOLevel to the file created by FileManager
     */
    public void Write(String data, RIOLevel id){
        String output = data+"\n";
        WriteToFile(output, id);
    }

     /**
     * Writes a boolean with an RIOLevel to the file created by FileManager
     */
    public void Write(boolean data, RIOLevel id){
        String output = data+"\n";
        WriteToFile(output, id);
    }

    /**
     * Writes a int with an RIOLevel to the file created by FileManager
     */
    public void Write(int data, RIOLevel id){
        String output = data+"\n";
        WriteToFile(output, id);
    }
    /**
     * Writes a short with an RIOLevel to the file created by FileManager
     */

    public void Write(short data, RIOLevel id){
        String output = data+"\n";
        WriteToFile(output, id);
    }

    /**
     * Writes a byte with an RIOLevel to the file created by FileManager
     */
    public void Write(byte data, RIOLevel id){
        String output = data+"\n";
        WriteToFile(output, id);
    }

     /**
     * Writes a long with an RIOLevel to the file created by FileManager
     */
    public void Write(long data, RIOLevel id){
        String output = data+"\n";
        WriteToFile(output, id);
    }

    /**
     * Writes a double with an RIOLevel to the file created by FileManager
     */
    public void Write(double data, RIOLevel id){
        String output = data+"\n";
        WriteToFile(output, id);
    }

    /**
     * Writes a float with an RIOLevel to the file created by FileManager
     */
    public void Write(float data, RIOLevel id){
        String output = data+"\n";
        WriteToFile(output, id);
    }

    private void WriteToFile(String data, RIOLevel... id){
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
