/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class FastQC extends Tool {
    
    public static File FASTQC;
    

    @Override
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outputFile) {
        
        if(! FASTQC.exists()){ return null;}
        
        String[] commandArray =  
            {   FASTQC.getAbsolutePath(),
                "-o", outputFile.getAbsolutePath(),
                "-f", "fastq",
                "-t", Integer.toString(atacseqInput.getCpu_units()),
                inputFile.getAbsolutePath()
            };

        return commandArray;   
    }

    @Override
    public String[] runCommand(String[] commandArray) {
        String[] log =  new String[2];
        try {
            ProcessBuilder fastqcBuilder = new ProcessBuilder(commandArray);
            Process process =  fastqcBuilder.start();
            String stdOUT = new Bowtie().getSTDoutput(process);
            String errorLog = new Bowtie().getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
            
        } catch (IOException ex) {
            Logger.getLogger(FastQC.class.getName()).log(Level.SEVERE, null, ex);
        }
        return log;
    }

    @Override
    public boolean isWorking() {
        return FastQC.fastQCPath();
    }
    
    public static boolean fastQCPath(){
        try {
            File jarFile = new File( MainJFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            File libDir = new File(jarFile.getParentFile()+System.getProperty("file.separator")+"lib");
            FASTQC = new File(libDir.getAbsoluteFile()+System.getProperty("file.separator")+"fastqc"+System.getProperty("file.separator")+"fastqc");
 
            if(FASTQC.exists() && FASTQC.isFile()){
                System.out.println("\t\tFASTQC:\t\tYup, Ready !!");
                return true;
            }
            else{
                System.err.println("FASTQC not found, please download fresh GUAVA setup ");
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(Picard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    
    }
    
    /********* Visualizing FastQC report*/
    private void viewReport(File FastqcDir){
    
    }
    
}
