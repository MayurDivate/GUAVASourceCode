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
public class Bowtie extends Tool {
   
    public Bowtie(){
    }
 
    
    @Override
    public String[] getCommand(GuavaInput atacseqInput, File inFile, File outFile) {
        
        String[] commandArray =  
            {   "bowtie",
                "-X",Integer.toString(atacseqInput.getInsertSize()),
                "-m",Integer.toString(atacseqInput.getMaxGenomicHits()),
                "--chunkmbs", "2000","-S",
                "-p",Integer.toString(atacseqInput.getCpu_units()),
                "--un",outFile.getParent()+"/unaligned.fastq",
                atacseqInput.getbowtieIndexString(),
                "-1", atacseqInput.getR1Fastq().getAbsolutePath(),
                "-2", atacseqInput.getR2Fastq().getAbsolutePath(),
                outFile.getAbsolutePath()
            };
        return commandArray;
    }

    @Override
    public String[] runCommand(String[] commandAarray) {
       String[] log =  new String[2];
        try {
            ProcessBuilder bowtieBuilder = new ProcessBuilder(commandAarray);
            Process process =  bowtieBuilder.start();
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
        
        String[] commandArray =  {"bowtie", "--version" };
        String[] log = new Bowtie().runCommand(commandArray);
            
        if(log != null && log[0].startsWith("bowtie version")){
            System.out.println("\t\t"+commandArray[0]+":\t\tWorking!");
            return true;
        }
        
        System.out.println("\t\t"+commandArray[0]+":\t\t\tNOT FOUND !!!");

        return false;
    }
    
}
