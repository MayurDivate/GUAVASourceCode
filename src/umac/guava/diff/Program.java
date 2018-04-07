/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava.diff;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public abstract class Program {
    
    public abstract String[] getCommand(File inputFile);
    public abstract String[] runCommand(String[] command);
    public abstract boolean isWorking();

    
    static String getSTDoutput(Process process){
        try {
            String stdOut = "";
            String readLineString;
            BufferedReader brProcess = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while((readLineString = brProcess.readLine()) != null){
                    stdOut = stdOut + readLineString + "\n";
                }
            return stdOut;
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    static String getSTDerror(Process process){
        
        try {
            String stdError = "";
            String readLineString;
            BufferedReader brProcess = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while((readLineString = brProcess.readLine()) != null){
                    stdError = stdError + readLineString +"\n";
                }
            return stdError;
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    static String getArrayToString(String[] array){
    
        String arrayString = "";
        for (String s : array){
            arrayString = arrayString + " " + s ;
        }
        
        return arrayString;
    }
    
    void printCommand(String[] array){
        System.out.println("Command: "+this.getArrayToString(array));
    }
    
    void writeLog(String[] logs,String header){
        
        
        try {
            FileWriter logFileWriter = new FileWriter(DifferentialOutputFiles.getLogFile(),true);
            BufferedWriter logFileBufferedWriter =  new BufferedWriter(logFileWriter);
            PrintWriter logPrintWriter = new PrintWriter(logFileBufferedWriter);
            
            logPrintWriter.append("\n\n"+header+"\n");
            logPrintWriter.flush();

            logPrintWriter.append("-------1-----------------------\n");
            logPrintWriter.append(logs[0]+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("------------------------------\n");
            logPrintWriter.flush();

            logPrintWriter.append("_______2_______________________\n");
            logPrintWriter.append(logs[1]+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("______________________________\n");
            logPrintWriter.flush();
            logPrintWriter.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
    public boolean writeCode(String code, File rcodefile ){
        try {
            PrintWriter pw = new PrintWriter(rcodefile);
            pw.write(code);
            pw.flush();
            pw.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean deleteFile(File myFile){
            if(myFile.exists()){
                // return myFile.delete();
                return true;
            }
            return false;
    }
    
    
}
