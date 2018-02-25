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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class ChIPseeker extends Tool {
    
    private File pieChart;
    private File barChart;
    private File geneAnnotationPeaks;
    private File pathwayPlot;
    private File pathwayAnnotationPeaks;
    private File chipseekerDir;
    private File chipseekerInput;
    private File chipseekerRcode;

    public ChIPseeker(File pieChart, File barChart, File geneAnnotationPeaks, File pathwayPlot, File pathwayAnnotationPeaks, File chipseekerDir, File chipseekerInput, File chipseekerRcode) {
        this.pieChart = pieChart;
        this.barChart = barChart;
        this.geneAnnotationPeaks = geneAnnotationPeaks;
        this.pathwayPlot = pathwayPlot;
        this.pathwayAnnotationPeaks = pathwayAnnotationPeaks;
        this.chipseekerDir = chipseekerDir;
        this.chipseekerInput = chipseekerInput;
        this.chipseekerRcode = chipseekerRcode;
    }

    public ChIPseeker() {
    }
    
    public static ChIPseeker getChIPSeekerObject(){
        
        if(GuavaOutputFiles.rootDir != null){
            String sampleBasename = GuavaOutputFiles.rootDir.getName().replaceAll("_OUTPUT", "");
            File csDir          =  new File(GuavaOutputFiles.rootDir.getAbsolutePath()+System.getProperty("file.separator")+sampleBasename+"_Functional_Analysis");
            File csPie          =  new File(csDir.getAbsolutePath()+System.getProperty("file.separator")+sampleBasename+"_PieChart.jpg");
            File csBar          =  new File(csDir.getAbsolutePath()+System.getProperty("file.separator")+sampleBasename+"_BarChart.jpg");
            File csGanno        =  new File(csDir.getAbsolutePath()+System.getProperty("file.separator")+sampleBasename+"_Gene_annotation.txt");
            File csPathwayPlot  =  new File(csDir.getAbsolutePath()+System.getProperty("file.separator")+sampleBasename+"_PathwayChart.jpg");
            File csPathwayAnno  =  new File(csDir.getAbsolutePath()+System.getProperty("file.separator")+sampleBasename+"_Pathway_annotation.txt");
            File csInput        =  new File(MACS2.getMACS2().getPeaksXLS().getAbsolutePath());
            File csRcode        =  new File(csDir.getAbsolutePath()+System.getProperty("file.separator")+sampleBasename+"_ChIPseeker.R");
            ChIPseeker chipseeker = new ChIPseeker(csPie, csBar, csGanno, csPathwayPlot, csPathwayAnno, csDir,csInput, csRcode);
            return chipseeker;
        }
        
        return null;
    }
    
    public static String getTXDB(GuavaInput atacInput){
        if(atacInput.getOrganism().equals("hs")){
            return  "TxDb.Hsapiens.UCSC."+IGV.genome+".knownGene";
        }
            return  "TxDb.Mmusculus.UCSC."+IGV.genome+".knownGene";
    }

    public static String getAnnoDB(GuavaInput atacInput){
        
        if(atacInput.getOrganism().equals("hs")){
            return  "org.Hs.eg.db";
        }
        else{
            return  "org.Mm.eg.db";
        }
    }

    public boolean createChiPseekerCode(GuavaOutputFiles outFiles, GuavaInput atacInput){
        String txdb = ChIPseeker.getTXDB(atacInput);
        String annodb = ChIPseeker.getAnnoDB(atacInput);
        ChIPseeker chipSeeker = outFiles.getChipSeeker();
        
        try {
              if(chipSeeker.getChipseekerDir().mkdir() && chipSeeker.getChipseekerRcode().createNewFile()){
                    
                    FileWriter rCodeWriter = new FileWriter(chipSeeker.getChipseekerRcode());
                    PrintWriter rCodePrintWriter = new PrintWriter(new BufferedWriter(rCodeWriter));
                    
                        String rCodeString = "setwd(\""+outFiles.getRootDir()+"\")\n";
                        rCodePrintWriter.append(rCodeString);
                        rCodePrintWriter.flush();

                        int tssUpRegion = -3000;
                        int tssDownRegion = 3000;
                        int width = 740;
                        int height = 355;
                        
                        rCodeString = "library(ChIPseeker)"+"\n"
                                    + "library(ReactomePA)"+"\n"
                                    + "library("+txdb+")"+"\n"
                                    + "txdb <- "+txdb+"\n"
                                    + "library(\""+annodb+"\")"+"\n"
                                    + "peakAnno <- annotatePeak(\""+chipSeeker.getChipseekerInput().getAbsolutePath()
                                    + "\", tssRegion=c("+tssUpRegion+", "+tssDownRegion+"),TxDb=txdb, annoDb=\""+annodb+"\")"+"\n"
                                    + "jpeg('"+chipSeeker.getPieChart().getAbsolutePath()+"',height="+height+",width="+width+")"+"\n"
                                    + "plotAnnoPie(peakAnno,r=1)"+"\n"
                                    + "dev.off()"+"\n"
                                    + "jpeg('"+chipSeeker.getBarChart()+"',height="+height+",width="+width+")"+"\n"
                                    + "plotDistToTSS(peakAnno,ylab = \"Peaks(%) (5' -> 3')\",title = \"\")"+"\n"
                                    + "dev.off()"+"\n"
                                    + "pathway1 <- enrichPathway(as.data.frame(peakAnno)$geneId)"+"\n"
                                    + "jpeg('"+chipSeeker.getPathwayPlot()+"',height="+height+",width="+width+")"+"\n"
                                    + "dotplot(pathway1)"+"\n"
                                    + "dev.off()"+"\n"
                                    + "write.table(as.data.frame(peakAnno)[,c(1:16,18,21,22,23)],file = \""+chipSeeker.getGeneAnnotationPeaks().getAbsolutePath()+"\",sep = \"\\t\",quote = FALSE)"+"\n"
                                    + "pathway_summary <- summary(pathway1)\n"
                                    + "write.table(pathway_summary[,c(2:7,9)],file = \""+chipSeeker.getPathwayAnnotationPeaks().getAbsolutePath()+"\",sep = \"\\t\",quote = FALSE)"+"\n";
                       
                       rCodePrintWriter.append(rCodeString);
                       rCodePrintWriter.flush();
                       return true;
            }
            return false;
        } catch (IOException ex) {
            Logger.getLogger(R.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
  
    public boolean getChiPseekerCodeDiffPeaks(ChIPseeker chipSeeker, String txdb, String annodb ){
        
        try {
              if(chipSeeker.getChipseekerDir().mkdir() && chipSeeker.getChipseekerRcode().createNewFile()){
                    
                    FileWriter rCodeWriter = new FileWriter(chipSeeker.getChipseekerRcode());
                    PrintWriter rCodePrintWriter = new PrintWriter(new BufferedWriter(rCodeWriter));
                    
                        String rCodeString = "setwd()\n";
                        rCodePrintWriter.append(rCodeString);
                        rCodePrintWriter.flush();

                        int tssUpRegion = -3000;
                        int tssDownRegion = 3000;
                        int width = 740;
                        int height = 355;
                        
                        rCodeString = "library(ChIPseeker)"+"\n"
                                    + "library(ReactomePA)"+"\n"
                                    + "library("+txdb+")"+"\n"
                                    + "txdb <- "+txdb+"\n"
                                    + "library(\""+annodb+"\")"+"\n"
                                    + "peakAnno <- annotatePeak(\""+chipSeeker.getChipseekerInput().getAbsolutePath()
                                    + "\", tssRegion=c("+tssUpRegion+", "+tssDownRegion+"),TxDb=txdb, annoDb=\""+annodb+"\")"+"\n"
                                    + "jpeg('"+chipSeeker.getPieChart().getAbsolutePath()+"',height="+height+",width="+width+")"+"\n"
                                    + "plotAnnoPie(peakAnno,r=1)"+"\n"
                                    + "dev.off()"+"\n"
                                    + "jpeg('"+chipSeeker.getBarChart()+"',height="+height+",width="+width+")"+"\n"
                                    + "plotDistToTSS(peakAnno,ylab = \"Peaks(%) (5' -> 3')\",title = \"\")"+"\n"
                                    + "dev.off()"+"\n"
                                    + "pathway1 <- enrichPathway(as.data.frame(peakAnno)$geneId)"+"\n"
                                    + "jpeg('"+chipSeeker.getPathwayPlot()+"',height="+height+",width="+width+")"+"\n"
                                    + "dotplot(pathway1)"+"\n"
                                    + "dev.off()"+"\n"
                                    + "write.table(as.data.frame(peakAnno)[,c(1:16,18,21,22,23)],file = \""+chipSeeker.getGeneAnnotationPeaks().getAbsolutePath()+"\",sep = \"\\t\",quote = FALSE)"+"\n"
                                    + "pathway_summary <- summary(pathway1)\n"
                                    + "write.table(pathway_summary[,c(2:7,9)],file = \""+chipSeeker.getPathwayAnnotationPeaks().getAbsolutePath()+"\",sep = \"\\t\",quote = FALSE)"+"\n";
                       
                       rCodePrintWriter.append(rCodeString);
                       rCodePrintWriter.flush();
                       return true;
            }
            return false;
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
    
    public String[] getCheckCommand(String packageName){
        String[] commandArray =  
            {   "R",
                "CMD",
                "check",
                packageName,
                "~/"
            };
        return commandArray;
    }

    // Method to run command
    public String[] runCommand(String[] commandArray) {
        String[] log =  new String[2];
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
            Process process = processBuilder.start();
            String stdOUT   = new ChIPseeker().getSTDoutput(process);
            String errorLog = new ChIPseeker().getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
            return log;
        } catch (IOException ex) { 
            System.out.println("\t\t"+ex.getMessage());
            return null;
        }
    }

    //Check R packages
    @Override   
    public boolean isWorking() {
        // current there is no code to check individual package 
        System.out.println("Method: umac.guava.ChIPseeker.isWorking()");
        System.out.println("This method is not complete");
        return true;
    }

    /**
     * @return the pieChart
     */
    public File getPieChart() {
        return pieChart;
    }

    /**
     * @param pieChart the pieChart to set
     */
    public void setPieChart(File pieChart) {
        this.pieChart = pieChart;
    }

    /**
     * @return the barChart
     */
    public File getBarChart() {
        return barChart;
    }

    /**
     * @param barChart the barChart to set
     */
    public void setBarChart(File barChart) {
        this.barChart = barChart;
    }

    /**
     * @return the geneAnnotationPeaks
     */
    public File getGeneAnnotationPeaks() {
        return geneAnnotationPeaks;
    }

    /**
     * @param geneAnnotationPeaks the geneAnnotationPeaks to set
     */
    public void setGeneAnnotationPeaks(File geneAnnotationPeaks) {
        this.geneAnnotationPeaks = geneAnnotationPeaks;
    }

    /**
     * @return the pathwayPlot
     */
    public File getPathwayPlot() {
        return pathwayPlot;
    }

    /**
     * @param pathwayPlot the pathwayPlot to set
     */
    public void setPathwayPlot(File pathwayPlot) {
        this.pathwayPlot = pathwayPlot;
    }

    /**
     * @return the pathwayAnnotationPeaks
     */
    public File getPathwayAnnotationPeaks() {
        return pathwayAnnotationPeaks;
    }

    /**
     * @param pathwayAnnotationPeaks the pathwayAnnotationPeaks to set
     */
    public void setPathwayAnnotationPeaks(File pathwayAnnotationPeaks) {
        this.pathwayAnnotationPeaks = pathwayAnnotationPeaks;
    }

    /**
     * @return the chipseekerDir
     */
    public File getChipseekerDir() {
        return chipseekerDir;
    }

    /**
     * @param chipseekerDir the chipseekerDir to set
     */
    public void setChipseekerDir(File chipseekerDir) {
        this.chipseekerDir = chipseekerDir;
    }

    /**
     * @return the chipseekerInput
     */
    public File getChipseekerInput() {
        return chipseekerInput;
    }

    /**
     * @param chipseekerInput the chipseekerInput to set
     */
    public void setChipseekerInput(File chipseekerInput) {
        this.chipseekerInput = chipseekerInput;
    }

    /**
     * @return the chipseekerRcode
     */
    public File getChipseekerRcode() {
        return chipseekerRcode;
    }

    /**
     * @param chipseekerRcode the chipseekerRcode to set
     */
    public void setChipseekerRcode(File chipseekerRcode) {
        this.chipseekerRcode = chipseekerRcode;
    }

    
      
}

