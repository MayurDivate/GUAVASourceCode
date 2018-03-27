/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
public abstract class Tool {
    
    public abstract String[] getCommand(GuavaInput atacseqInput,File inputFile, File outputFile);
    public abstract String[] runCommand(String[] command);
    public abstract boolean isWorking();

    
    public String getSTDoutput(Process process){
        try {
            String stdOut = "";
            String readLineString;
            BufferedReader brProcess = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while((readLineString = brProcess.readLine()) != null){
                    stdOut = stdOut + readLineString + "\n";
                }
            return stdOut;
        } catch (IOException ex) {
            Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
            return null;
        }
        
    }
    
    public String getSTDerror(Process process){
        
        try {
            String stdError = "";
            String readLineString;
            BufferedReader brProcess = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                while((readLineString = brProcess.readLine()) != null){
                    stdError = stdError + readLineString +"\n";
                }
            return stdError;
        } catch (IOException ex) {
            Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
            return null;
        }
        
    }
    
    public String getStringFromArray(String[] array){
    
        String arrayString = "";
        for (String s : array){
            arrayString = arrayString + " " + s ;
        }
        
        return arrayString;
    }
    
    public void printCommand(String[] array){
        System.out.println("Command: "+this.getStringFromArray(array));
    }
    
    public void writeLog(String[] logs,String header){
        
        try {
            FileWriter logFileWriter = new FileWriter(GuavaOutputFiles.logFile,true);
            BufferedWriter logFileBufferedWriter =  new BufferedWriter(logFileWriter);
            PrintWriter logPrintWriter = new PrintWriter(logFileBufferedWriter);
            
            logPrintWriter.append("\n\n"+header+"\n");
            logPrintWriter.flush();

            logPrintWriter.append("------------------------------\n");
            logPrintWriter.append(logs[0]+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("------------------------------\n");
            logPrintWriter.flush();

            logPrintWriter.append("______________________________\n");
            logPrintWriter.append(logs[1]+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("______________________________\n");
            logPrintWriter.flush();
            logPrintWriter.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
    public boolean deleteFile(File myFile){
 
        File bamIndex = new File(myFile.getAbsolutePath()+".bai");
        
        //System.out.println("Call for deleting file "+myFile.getAbsolutePath());
            if(bamIndex.exists()){
                bamIndex.delete();
            }

            if(myFile.exists()){
                return myFile.delete();
            }

            return false;
    }
    
}
