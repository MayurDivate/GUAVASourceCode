/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.File;
import java.io.IOException;

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
            return log;
        } catch (IOException ex) {
            System.out.println("\t\t"+ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean isWorking() {
        
        String[] commandArray =  {"fastqc", "-v" };
        String[] log = new FastQC().runCommand(commandArray);
        
        if(log != null && log[0].startsWith("FastQC")){
            System.out.println("\t\t"+commandArray[0]+":\t\tWorking!");
            return true;
        }

        System.out.println("\t\t"+commandArray[0]+":\t\t\tNOT FOUND !!!");
        return false;
    }
    
    
}
