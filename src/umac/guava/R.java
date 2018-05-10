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
    
    public boolean createRcode(GuavaOutputFiles outFiles){
        int height = 420;
        int width = 757;
                
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

                       
                rCodeString = ""
                        + "options(scipen=6)" + "\n"
                        + "insertsizes$density <- insertsizes$count * 1000 / sum(insertsizes$count)" + "\n"
                        + "library(ggplot2)" + "\n"
                        + "p <- ggplot(insertsizes, aes(insert_size,density))" + "\n"
                        + "p <- p + geom_area(fill=\"red\",col=\"black\")" + "\n"
                        + "p <- p + theme_grey()" + "\n"
                        + "p <- p + theme(axis.text.y = element_text(angle = 90,hjust = 0.5, vjust = 0.5,face = \"bold\"))" + "\n"
                        + "p <- p + theme(axis.text.y = element_text(angle = 90,hjust = 0.5, vjust = 0.5,face = \"bold\"))" + "\n"
                        + "p <- p + theme(legend.position = \"none\")" + "\n"
                        + "p <- p + theme(plot.title = element_text(hjust = 0.5))" + "\n"
                        + "p <- p + ggtitle(\"Fragment size distribution\")" + "\n"
                        + "p <- p + xlab(\"Fragment Size (bp)\")" + "\n"
                        + "p <- p + ylab(expression(Normalised~read~density~x~10^-3))" + "\n"
                        + "\n"
                        + "jpeg("+"\""+outFiles.getFragmentSizeDistributionPlot().getAbsoluteFile()+"\""+",height="+height+",width="+width+")" + "\n"
                        + "print(p)" + "\n"
                        + "dev.off()" + "\n"                        
                        + "\n";

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
        
        String[] commandArray =   
            {   "Rscript",
                inputFile.getAbsolutePath()
            };
        return commandArray;
    }
    
    public String[] getCommand(String bioconductorPackage) {
        File guava = GUAVA.getPackageBase();
        File rscript =  new File(new File(guava,"lib"),"installMissingBCpackage.R");
        
        String[] commandArray =   
            {   "Rscript", 
                rscript.getAbsolutePath(),
                bioconductorPackage
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
            return log;
        } catch (IOException ex) {
            System.out.println("\t\t"+ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean isWorking() {
        String[] commandArray =  {"R", "--version" };
        String[] log = new R().runCommand(commandArray);
        
        if(log[0] != null){
            Pattern versionPattern = Pattern.compile("R version (.*?) ");
            Matcher versionMatch = versionPattern.matcher(log[0]);
            
            if(versionMatch.find()){
                System.out.println("\t\tR ("+versionMatch.group(1)+"):\t\tWorking!");
                if(isPackagesWorking()){
                   return true;
                }
            }

        }

        System.out.println("\t\tR:\t\t\tNOT FOUND !!!");
        return false;
    }
    
    public boolean isPackagesWorking() {
        System.out.println("\t\tChecking required R packages, This may take a while.");
        System.out.println("\t\tPlease wait...");
        
        File lib = new File(GUAVA.getPackageBase(),"lib");
        File rScript = new File(lib,"InstallRequiredPackages.R");

        if(rScript.exists() && rScript.isFile()){
            
            String[] rcommand =  {"Rscript", rScript.getAbsolutePath()};
            String[] rcommandLog = new R().runCommand(rcommand);
            
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
        
        System.out.println("\t\tR Packages:\tclear! :)");
        return true;

            
    }
    
    public boolean isGenomeBCpackages(Genome genome){
        boolean isTXDB  = isBCpackage(genome.getTxdb());
        boolean isOrgDB = isBCpackage(genome.getOrgdb());
        
        if(isOrgDB && isTXDB){
            return true;
        }
        return false;
    }
    
    public boolean isBCpackage(String bcPackageName){
        R r = new R();
        String[] log = r.runCommand(r.getCommand(bcPackageName));
        log[0] = log[0].trim();
        
        if(!log[0].equals("[1] TRUE")){
            System.out.println(">>>>> "+bcPackageName+": FALSE"); 
        }
        return log[0].equals("[1] TRUE");
    }
    
    
}
