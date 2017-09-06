/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mayurdivate
 */
public class R extends Tool{
    
    public static File fragmentSizeDistributionPlot;
    
    public boolean createRcode(GuavaOutputFiles outFiles){
        int height = 420;
        int width = 770;
                
        try {
            if(outFiles.getrCode().createNewFile()){
                FileWriter rCodeWriter = new FileWriter(outFiles.getrCode());
                PrintWriter rCodePrintWriter = new PrintWriter(new BufferedWriter(rCodeWriter));

                String rCodeString = "setwd(\""+outFiles.getRootDir()+"\")\n";
                       rCodePrintWriter.append(rCodeString);
                       rCodePrintWriter.flush();
                
                       rCodeString = "insertsizes <- read.table(\""+outFiles.getrInsertSize().getName()+"\",header = T,sep = \"\\t\")\n";
                       rCodePrintWriter.append(rCodeString);
                       rCodePrintWriter.flush();

                       rCodeString = "jpeg("+"\""+outFiles.getFragmentSizeDistributionPlot().getAbsoluteFile()+"\""+",height="+height+",width="+width+")\n";
                       rCodePrintWriter.append(rCodeString);
                       rCodePrintWriter.flush();
                       
                       rCodeString = "par(mar=c(3,3,0.2,1),mgp=c(1.5,0.5,0))\n";
                       rCodePrintWriter.append(rCodeString);
                       rCodePrintWriter.flush();
                       
                       rCodeString = "plot(insertsizes$insert_size,insertsizes$count,type =\"h\",col=\"red\",lwd=2,xlab = \"Fragment Size (bp)\",ylab = \"Read Count\")\n";
                       rCodePrintWriter.append(rCodeString);
                       rCodePrintWriter.flush();
                       
                       rCodeString = "dev.off()\n";
                       rCodePrintWriter.append(rCodeString);
                       rCodePrintWriter.flush();                
                
            }
        } catch (IOException ex) {
            Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    @Override
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outputFile) {
        return this.getCommand(atacseqInput, inputFile);
    }

    public String[] getCommand(GuavaInput atacseqInput, File inputFile) {
        System.out.println("call for Insert size R plot header");
        String[] commandArray =   
            {   "Rscript",
                inputFile.getAbsolutePath()
            };
        
        return commandArray;
    }

    @Override
    public String[] runCommand(String[] commandArray) {
        String[] log =  new String[2];
       
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
            Process process =  processBuilder.start();
            String stdOUT = new R().getSTDoutput(process);
            String errorLog = new R().getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
            
        } catch (IOException ex) { 
            Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
        }
        return log;
    }

    @Override
    public boolean isWorking() {
        String[] commandArray =  {"R", "--version" };
        String[] log = new R().runCommand(commandArray);
        
        if(log[0] != null){
            
            Pattern versionPattern = Pattern.compile("R version (.*?) ");
            Matcher versionMatch = versionPattern.matcher(log[0]);
            
            if(versionMatch.find()){
                System.out.println("\t\tR:\t\tStick Together, Team! :)");
                if(isPackagesWorking()){
                   return true;
                }
            }

        }

        System.out.println("\t\tR:\t\tDead :X ");
        return false;
    }
    
    public boolean isPackagesWorking() {
        System.out.println("\t\tChecking required R packages, This may take a while.");
        System.out.println("\t\tPlease wait...");
        
    try{

        File jarFile = new File( MainJFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        File rScript = new File(jarFile.getParentFile()+System.getProperty("file.separator")+""
                       + "lib"+System.getProperty("file.separator")+"InstallRequiredPackages.R");

        if(rScript.exists() && rScript.isFile()){
            String[] rcommand =  {"Rscript", rScript.getAbsolutePath() };
            String[] rcommandLog     = new ChIPseeker().runCommand(rcommand);
            
            String outLog = rcommandLog[0].replaceAll("\\[1\\] ", "");
            outLog = outLog.replaceAll("\"", "");
            outLog = outLog.replaceAll("\n\n", "");

            Pattern failpattern = Pattern.compile("FAILED");
            Matcher failMatch =  failpattern.matcher(outLog);

            if(failMatch.find()){
                System.out.println(outLog);
                System.out.println("Please install above R packages manually."
                        + "\nGUAVA is unable to install R packages because of insufficient rights.");
                return false;
            }
        }


        } catch (URISyntaxException ex) {   
            Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        System.out.println("\t\tR Packages:\tclear! :)");
        return true;
            
    }

    public boolean checkRpackages(){
    
        ArrayList<String> reqPackageList =getListOfRequiredPackages();
        
        for(int index=0; index < reqPackageList.size(); index++){
               String bioCpackage = reqPackageList.get(index);
               if(!isInstalled(bioCpackage)){
                   boolean flag = installBiocPackage(bioCpackage);
                   if(!flag){
                        System.out.println("Unable to install bioconductor package : "+bioCpackage);
                        return flag;
                   }
               }
        }
    
        return true;
    }
    
    public static boolean isInstalled(String bioConductorPackage){
        
        return false;
    }
    
    public static boolean installBiocPackage(String bioConductorPackage){
        
        return false;
    }
    public static ArrayList<String> getListOfRequiredPackages(){
        ArrayList<String> requiredPackages = new ArrayList<>();
        requiredPackages.add("ChIPseeker");
        requiredPackages.add("ReactomePA");
        requiredPackages.add("org.Hs.eg.db");
        requiredPackages.add("org.Mm.eg.db");
        requiredPackages.add("TxDb.Hsapiens.UCSC.hg19.knownGene");
//        requiredPackages.add("TxDb.Hsapiens.UCSC.hg18.knownGene");
        requiredPackages.add("TxDb.Mmusculus.UCSC.mm10.knownGene");
        requiredPackages.add("TxDb.Mmusculus.UCSC.mm9.knownGene");
        requiredPackages.add("ChIPpeakAnno");
        requiredPackages.add("GO.db");
        requiredPackages.add("KEGG.db");
        requiredPackages.add("EnsDb.Hsapiens.v75");
        return requiredPackages;
    }
    
    public void installBiocLite(){
    
        String source = "source(\"https://bioconductor.org/biocLite.R\")";
        String install = "biocLite()";
        
    }
    
     
    
    
}
