/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class FastQC extends Tool {
    

    @Override
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outputFile) {
        
        String[] commandArray =  
            {   "fastqc",
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
        
        String[] commandArray =  {"fastqc", "-v" };
        String[] log = new FastQC().runCommand(commandArray);
        if(log[0].startsWith("FastQC")){
            System.out.println("\t\tFastQC:\t\tAffirmative :)");
            return true;
        }
        System.out.println("\t\tFastQC:\t\tNegative :(");
        return false;
    }
    
    
}
