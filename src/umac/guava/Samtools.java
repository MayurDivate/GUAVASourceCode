/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mayurdivate
 */
public class Samtools extends Tool {
    
    //public static String SAMTOOLS = "/Users/mayurdivate/Programs/samtools-1.3.1/samtools";
    public static String SAMTOOLS = "samtools";
    
    @Override 
     // CHROMOSOME FILTER 
    public String[] getCommand(GuavaInput atacseqInput,File inFile, File outFile) {
      //  System.out.println("Call for chromosome filtering");

        String[] log = new Samtools().runCommand(new Samtools().getCommand(atacseqInput, inFile));
        new Samtools().writeLog(log, "bam indexing for chromosome filtering");

        String command =  SAMTOOLS
                + " view"
                + " -b"
                + " -o " + outFile.getAbsolutePath()
                + " -@ " + Integer.toString(atacseqInput.getCpu_units())
                + " -O " + "bam "
                + inFile.getAbsolutePath() + " "
                + atacseqInput.getChromosome();
        
        String[] commandArray = command.split(" ");
        return commandArray;

    }

    // overloaded for SAM / BAM ------> sorted SAM /BAM
    public String[] getCommand(GuavaInput atacseqInput,File inFile, File outFile,String outformat) {
       // System.out.println("Call for SBAM to sorted SBAM");
        String[] commandArray =  
            {   SAMTOOLS,
                "sort",
                "-o", outFile.getAbsolutePath(),
                "-@", Integer.toString(atacseqInput.getCpu_units()),
                "-O", outformat,
                inFile.getAbsolutePath()
            };
        
        return commandArray;
    }

    // overloaded for SAM / BAM ------> query sorted SAM /BAM
    public String[] getCommand(GuavaInput atacseqInput,File inFile, File outFile,String outformat,boolean querySorted) {
       // System.out.println("Call for SBAM to query sorted SBAM");
        if(!querySorted){
               return new Samtools().getCommand(atacseqInput, inFile, outFile, outformat);
                
        }
        
        String[] commandArray =  
            {   SAMTOOLS,
                "sort", "-n",
                "-o", outFile.getAbsolutePath(),
                "-@", Integer.toString(atacseqInput.getCpu_units()),
                "-O", outformat,
                inFile.getAbsolutePath()
            };
        
        return commandArray;
    }
    
    
    // BLACK LIST filtering
    public String[] getCommand(GuavaInput atacseqInput,File inFile, File outFile, File tempFile, File BlacklistFile) {
       // System.out.println("Call for black list filtering");
        String[] commandArray =  
            {   SAMTOOLS,
                "view",
                "-b",
                "-h",
                "-o", tempFile.getAbsolutePath(),
                "-@", Integer.toString(atacseqInput.getCpu_units()),
                "-L", BlacklistFile.getAbsolutePath(),
                "-U", outFile.getAbsolutePath(),
                "-O", "bam",
                inFile.getAbsolutePath()
            };
        return commandArray;
    }

    // bam file indexing
    public String[] getCommand(GuavaInput atacseqInput,File inFile) {
       // System.out.println("Call for bam indexing");
        String[] commandArray =  
            {   SAMTOOLS,
                "index",
                inFile.getAbsolutePath()
            };
        return commandArray;

    }
    
    //samtools idxstats 
    public String[] getCommand(File inFile) {
       // System.out.println("Call for idxstats");
        
        String[] commandArray =  
            {   SAMTOOLS,
                "idxstats",
                inFile.getAbsolutePath()
            };
        return commandArray;

    }

    //samtools idxstats 
    public String[] getIndexCommand(File inFile) {
       // System.out.println("Call for bam indexing");
        String[] commandArray =  
            {   SAMTOOLS,
                "index",
                inFile.getAbsolutePath()
            };
        return commandArray;
    }    
    
    // Properly aligned BAM
    public String[] getCommand(GuavaInput atacseqInput, GuavaOutputFiles outFiles) {
       // System.out.println("Call for -f3 to get properly aligned pairs");
        String[] commandArray =  
            {   SAMTOOLS,
                "view", "-b", 
                "-f"  , "3",
                "-o"  , outFiles.getProperlyAlignedBam().getAbsolutePath(),
                "-@"  , Integer.toString(atacseqInput.getCpu_units()),
                "-O"  , "bam",
                outFiles.getDuplicateFilteredBam().getAbsolutePath()
            };
        
        return commandArray;

    }

    // Alignment filtering by MapQ  ==> BAM
    public String[] getCommand(File inpuSBAMfile, File outBamFile, int mapQ, int cpu) {
       // System.out.println("Call for SBAM to quality filtering");
        
        String[] commandArray =  
            {   SAMTOOLS,
                "view", "-b",
                "-q", Integer.toString(mapQ),
                "-@", Integer.toString(cpu),
                "-o", outBamFile.getAbsolutePath(),
                "-O", "bam",
                inpuSBAMfile.getAbsolutePath()
            };
        
        return commandArray;
    }
    
    // header aligned BAM
    public String[] getCommand(File samFile, File header) {
       // System.out.println("call for sam / bam header");
        String[] commandArray =  
            {   SAMTOOLS,
                "view", "-H", 
                "-o"  , header.getAbsolutePath(),
                "-O"  , "sam",
                samFile.getAbsolutePath()
            };
        
        return commandArray;

    }
    
    @Override
    public String[] runCommand(String[] commandArray) {
        String[] log =  new String[2];
       
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
            Process process =  processBuilder.start();
            String stdOUT = new Samtools().getSTDoutput(process);
            String errorLog = new Samtools().getSTDerror(process);
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
        
        String[] commandArray =  {"samtools", "--version" };
        String[] log = new Samtools().runCommand(commandArray);
        
        if(log != null && (log[0] != null && log[1] != null)){
            System.out.println("\t\t"+commandArray[0]+":\tWorking!");
            return true;
        }

        System.out.println("\t\t"+commandArray[0]+":\t\t\tNOT FOUND !!!");
        return false;
    }
    
    public HashMap<String, Integer> getChrStat(File inBamFile) {
        
        Samtools samtools = new Samtools();
        File indexFile = new File(inBamFile.getAbsolutePath()+".bai");
            if(!indexFile.exists()){
                samtools.runCommand(samtools.getIndexCommand(inBamFile));
        }
        
        HashMap <String, Integer> chrStat =  new HashMap<>();
        
        String[] prefilteringLog = samtools.runCommand(samtools.getCommand(inBamFile));
        samtools.writeLog(prefilteringLog, "idxstats");
        String[] chromosomes = prefilteringLog[1].trim().split("\n");
        
        for(String chrRecord : prefilteringLog[0].split("\n")){
            Pattern pattern = Pattern.compile("(.*?)\t(.*?)\t(.*?)\t(.*?)");
            Matcher matcher = pattern.matcher(chrRecord);
           
            if(matcher.find()){
                int alignedReads = Integer.parseInt(matcher.group(3));
                chrStat.put(matcher.group(1),alignedReads);
            }
            else{
                System.out.println("No patternsfound for idxstats\t"+chrRecord);
                return null;
            }
        }
        
        
        return chrStat;
        
    }
    
    public int getReadCount(File inBamFile) {
        Samtools samtools = new Samtools();
        
        HashMap<String, Integer> chrSTAT = samtools.getChrStat(inBamFile);
        int count = 0 ;
        
        for(String chr : chrSTAT.keySet()){
            count = count + chrSTAT.get(chr);
        }
        count = count / 2 ;
        return count;
    }
    
    public static boolean checkBlackListFile(){
            File packageBase = GUAVA.getPackageBase();
            File lib = new File(packageBase,"lib");
            
            ArrayList<File> blacklistBeds =  new ArrayList<>();
            blacklistBeds.add(new File(lib,"hg19.bed"));
            blacklistBeds.add(new File(lib,"hg19.bed"));
            blacklistBeds.add(new File(lib,"hg318.bed"));
            blacklistBeds.add(new File(lib,"mm10.bed"));
            blacklistBeds.add(new File(lib,"mm9.bed"));
            
            if(areAllFilesExists(blacklistBeds)){
                    GuavaInput.setBasenameBLACKLIST(lib.getAbsolutePath());
                    return true;
            }
            
        System.out.println("EROOR: BlackList bed files are absent");
        return false;
    
    }
    
    public static boolean areAllFilesExists(ArrayList<File> files){
        for(File f: files){
            if(!f.exists()){
                System.err.println("File Not Found: "+f.getAbsolutePath());
                return false;
            }
        }
        
        return true;
    }
    
    
}
