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
public class Picard extends Tool{
    
    private File inputBam;
    private File outputBam;
    private File duplicateMatrix;
    private File insertSizeMatrix;
    private File insertSizeHist;
    private double min_pct;
    
    public static String PICARD = null;
    
    public boolean getInsertSizeMatrix(){
 
        return true ;
    }
    
    @Override
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outputFile) {
        String[] commandStrings =  
                {   
                    "java","-Xmx"+atacseqInput.getRamMemory()+"g",
                    "-Djava.io.tmpdir="+OutputFiles.rootDir+""+System.getProperty("file.separator")+"tmp", 
                    "-jar",
                    PICARD+System.getProperty("file.separator")+"MarkDuplicates.jar",
                    "REMOVE_DUPLICATES=true",
                    "I="+inputFile.getAbsolutePath(),
                    "O="+outputFile.getAbsolutePath(),
                    "M="+outputFile.getAbsolutePath()+"_matrix.txt"
                    };
        return commandStrings;
    }

    // method for insertSize distribution
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outFile, File outPDF, double min_pct) {
        
        String[] commandStrings =  
                {   
                    "java","-Xmx"+atacseqInput.getRamMemory()+"g",
                    "-Djava.io.tmpdir="+OutputFiles.rootDir+""+System.getProperty("file.separator")+"tmp", 
                    "-jar",
                    PICARD+System.getProperty("file.separator")+"CollectInsertSizeMetrics.jar",
                    "I="+inputFile.getAbsolutePath(),
                    "O="+outFile.getAbsolutePath(),
                    "H="+outPDF.getAbsolutePath(),
                    "M="+min_pct
                };
        return commandStrings;
    }

    public String[] getCommand(File inputFile) {
        System.out.println("insert size graph");
        
        String[] commandStrings =  
                {   "R"
                };
        return commandStrings;
    }
    
    @Override
    public String[] runCommand(String[] commandStrings) {
        String[] log =  new String[2];
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandStrings);
            processBuilder.directory(OutputFiles.rootDir);
            Process process =  processBuilder.start();
            String stdOUT = new Picard().getSTDoutput(process);
            String errorLog = new Picard().getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
         
        } catch (IOException ex) {
            Logger.getLogger(Picard.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return log;
    }

    @Override
    public boolean isWorking() {
        return picardPath();
    }
    
    public static boolean picardPath(){
        try {
            File jarFile = new File( MainJFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            File picardDir = new File(jarFile.getParentFile()+System.getProperty("file.separator")+"lib"+System.getProperty("file.separator")+"picard");
            if(picardDir.exists() && picardDir.isDirectory()){
                PICARD = picardDir.getAbsolutePath();
                System.out.println("\t\tPicard:\tFound! :)");
                return true;
            }
            else{
                System.out.println("\t\tPicard:\tMissing! :)");
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(Picard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    
    }
    
}
