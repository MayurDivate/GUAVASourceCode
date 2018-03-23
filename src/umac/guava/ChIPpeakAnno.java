/*
 * Copyright (C) 2018 mayurdivate
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package umac.guava;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mayurdivate
 */
public class ChIPpeakAnno extends Tool {
    
    private File inputFile;
    private String inputFileFormat;
    private File barChart;
    private File peakAnnoated;
    private File outputFolder;
    private File goAnalysisOutputFile;
    private File pathwayAnalysisOutputFile;
    private File rCodeFile;
    private Genome genome;
    
    String getChIPpeakAnnoRcode(){
       
        //SETWD
        // genome hg19 / hg18 etc
        
        String txDb = this.getGenome().getTxdb();
        String orgDB = this.getGenome().getOrgdb();
        String orgSymbol = this.getGenome().getOrgdbSymbol();
        
        // load lobraries
        String code = "\n"+
                "library(ChIPpeakAnno)\n" +
                "library(GenomicFeatures)\n" +
                "library( "+txDb+")\n"+
                "library(KEGG.db)\n";
        
        // set input files
        code = code + "setwd(\""+ this.getOutputFolder().getAbsolutePath()+"\")"+"\n";
        code = code + "macs <-\""+this.getInputFile().getAbsolutePath() +"\"\n";
        code = code + "macsOutput <- toGRanges(macs, format=\""+this.getInputFileFormat()+"\")"+"\n";
        
        // annoatate peaks
        code = code + "ucscTxDb <- \""+txDb+"\""+"\n";
        code = code + "ucscGenes <- genes("+txDb+")"+"\n";
        code = code + "macs.anno <- annotatePeakInBatch(macsOutput, AnnotationData=ucscGenes, \n" 
                +"output = \"nearestBiDirectionalPromoters\", \n"
                +"bindingRegion = c(-5000, 3000))"+"\n";
        
        // add gene symbols
        code = code + "OrgEgDb <- \""+orgDB+"\""+"\n" 
                + "macs.anno <- addGeneIDs(annotatedPeak=macs.anno, \n" 
                + "orgAnn=OrgEgDb,\n" 
                + "feature_id_type=\"entrez_id\",\n" 
                + "IDs2Add=\"symbol\")"
                +"\n"
                + "macs.anno@ranges@NAMES <- NULL"
                + "\n";
        
        // write to file
        code = code + "macsAnnoOut <- as.data.frame(macs.anno)\n"
                + "macsAnnoOut <- macsAnnoOut[,c(1,2,3,6,8,9,10,14,15,17,19,20,22,12,21)]" + "\n"
                + "colnames(macsAnnoOut) <- c(\"Chr\",\"Peak Start\",\"Peak End\",\"length\",\"pileup\",\"X.log10.qvalue.\",\"X.log10.qvalue.\",\"Gene Start\",\"Gene End\",\"Gene Strand\",\"Feature Location\",\"Distance to Feature\",\"Gene Symbol\",\"Peak Name\",\"Entrez ID\")"+"\n"
                + "write.table(macsAnnoOut,\""+this.getPeakAnnoated().getAbsolutePath()+"\", sep = \"\\t\", quote = FALSE)"+"\n"
                + "\n";
        
        // assign chromosomal regions 
        code = code + "aCR<-assignChromosomeRegion(macs.anno, nucleotideLevel=FALSE, \n" +
                "precedence=c(\"Promoters\", \"immediateDownstream\", \n" +
                "\"fiveUTRs\", \"threeUTRs\", \n" +
                "\"Exons\", \"Introns\"), \n" +
                "TxDb="+txDb+")"+"\n";
        code = code + ""+"\n";
        
        // barplot 
        code = code + getBarChartCode(this.getBarChart());
        code = code + ""+"\n";
        
        // gene ontology enrichment
        code = code + "overGO <- getEnrichedGO(macs.anno, orgAnn=\""+orgDB+"\",\n";
        code = code + "maxP=0.01, minGOterm=10,"+"\n";
        code = code + "multiAdjMethod=\"BH\","+"\n";
        code = code + "feature_id_type = \"entrez_id\","+"\n";
        code = code + "condense=FALSE)"+"\n";
        code = code + ""+"\n";
        code = code + "gores <- rbind.data.frame(overGO$bp,overGO$cc,overGO$mf)"+"\n";
        code = code + "gores$symbol <- xget(as.character(gores$EntrezID),"+orgSymbol+")"+"\n";
        code = code + "write.table(gores,\""+this.getGoAnalysisOutputFile().getAbsolutePath()+"\", sep = \"\\t\", quote = FALSE)"+"\n";
        code = code + ""+"\n";
        
        // pathway ennrichment
        code = code +"\n"+
                "overPath <- getEnrichedPATH(macs.anno, orgAnn = OrgEgDb,\n" +
                "pathAnn = \"KEGG.db\",\n" +
                "feature_id_type = \"entrez_id\",\n" +
                "maxP=0.01, minPATHterm=10, multiAdjMethod=NULL)"+"\n";
        code = code + "overPath$symbol <- xget(as.character(overPath$EntrezID),"+orgSymbol+")"+"\n";
        code = code + "write.table(overPath,\""+this.getPathwayAnalysisOutputFile().getAbsolutePath()+"\", sep = \"\\t\", quote = FALSE)"+"\n";

       
        code = code + ""+"\n";
        code = code + ""+"\n";
        code = code + ""+"\n";
        
        return code;
    }
    
    String getBarChartCode(File barPlotFile){
        
        int width = 750;
        int height =  400;
        int legendFS = 12;
        int titleFS = 15;
        
        String code = "\n"
                + "library(ggplot2)\n"
                + "Regions <- names(aCR$percentage)\n" 
                + "Freq <- as.vector(aCR$percentage)\n" 
                + "acrDF <- data.frame(Regions, Freq)\n" 
                + "acrDF$Regions <- factor(acrDF$Regions,levels = Regions)\n" 
                + "acrDF$text <- round(acrDF$Freq,2)\n" 
                + "acrDF$text <- paste(acrDF$text,\"%\")\n" 
                + "\n" 
                + "p <- ggplot(acrDF, aes(Regions,Freq, fill=Regions))\n" 
                + "p <- p + geom_col()\n" 
                + "p <- p + ylab(label = \"Percentage of Peaks\")\n" 
                + "p <- p + theme(plot.title = element_text(hjust = 0.5,size = "+titleFS+"))\n" 
                + "p <- p + ggtitle(\"Peak distribution in chromosomal regions\")\n" 
                + "p <- p + theme(legend.position = \"none\",axis.title.x = element_blank())\n" 
                + "p <- p + theme(axis.text.x = element_text(size = "+legendFS+",colour = \"black\"))\n" 
                + "p <- p + theme(axis.text.y = element_text(size = "+legendFS+",colour = \"black\"))\n"
                + "p <- p + geom_text(aes(label = text, y = Freq + 1))\n"
                + "\n"
                + "jpeg(\""+barPlotFile.getAbsolutePath()+"\",width = "+width+",height = "+height+")\n" 
                + "p\n" 
                + "dev.off()" 
                + "\n";
        
        return code;
    }
    
    public boolean writeCode(String code, File ouputFile) {

        try {
            if (ouputFile.createNewFile()) {
                FileWriter rCodeWriter = new FileWriter(ouputFile);
                PrintWriter rCodePrintWriter = new PrintWriter(new BufferedWriter(rCodeWriter));
                rCodePrintWriter.append(code);
                rCodePrintWriter.flush();
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(ChIPpeakAnno.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }
    
    public static ChIPpeakAnno getChIPpeakAnnoObject(File inputFile, String inputFileFormat, Genome genome) {

        if (GuavaOutputFiles.rootDir != null) {

            String basename = GuavaOutputFiles.getOutBaseName();
            File outputFolder               = new File(GuavaOutputFiles.rootDir, basename + "_Functional_Analysis");
            
            File barChart                   = new File(outputFolder, basename + "_bar_chart.jpg");
            File annoatedPeakFile           = new File(outputFolder, basename + "_Annotated_Peaks.txt");
            File goAnalysisOutputFile       = new File(outputFolder, basename + "_GeneOntology_Analysis.txt");
            File pathwayAnalysisOutputFile  = new File(outputFolder, basename + "_KEGG_Pathway_Analysis.txt");
            File rCodeFile                  = new File(outputFolder, basename + "_ChIPpeakAnno.R");

            ChIPpeakAnno chipPeakAnno = new ChIPpeakAnno(inputFile, inputFileFormat,
                    barChart, annoatedPeakFile, outputFolder, goAnalysisOutputFile, 
                    pathwayAnalysisOutputFile, rCodeFile, genome);
            
            return chipPeakAnno;
        }
        
        System.out.println("umac.guava.ChIPpeakAnno.getChIPpeakAnnoObject()");
        System.out.println("Null pointer because of early call!");
        
        return null;
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
     * @return the inputFile
     */
    public File getInputFile() {
        return inputFile;
    }

    /**
     * @param inputFile the inputFile to set
     */
    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * @return the inputFileFormat
     */
    public String getInputFileFormat() {
        return inputFileFormat;
    }

    /**
     * @param inputFileFormat the inputFileFormat to set
     */
    public void setInputFileFormat(String inputFileFormat) {
        this.inputFileFormat = inputFileFormat;
    }

    /**
     * @return the goAnalysisOutputFile
     */
    public File getGoAnalysisOutputFile() {
        return goAnalysisOutputFile;
    }

    /**
     * @param goAnalysisOutputFile the goAnalysisOutputFile to set
     */
    public void setGoAnalysisOutputFile(File goAnalysisOutputFile) {
        this.goAnalysisOutputFile = goAnalysisOutputFile;
    }

    /**
     * @return the pathwayAnalysisOutputFile
     */
    public File getPathwayAnalysisOutputFile() {
        return pathwayAnalysisOutputFile;
    }

    /**
     * @param pathwayAnalysisOutputFile the pathwayAnalysisOutputFile to set
     */
    public void setPathwayAnalysisOutputFile(File pathwayAnalysisOutputFile) {
        this.pathwayAnalysisOutputFile = pathwayAnalysisOutputFile;
    }

    /**
     * @return the genome
     */
    public Genome getGenome() {
        return genome;
    }

    /**
     * @param genome the genome to set
     */
    public void setGenome(Genome genome) {
        this.genome = genome;
    }

   

    /**
     * @return the outputFolder
     */
    public File getOutputFolder() {
        return outputFolder;
    }

    /**
     * @param outputFolder the outputFolder to set
     */
    public void setOutputFolder(File outputFolder) {
        this.outputFolder = outputFolder;
    }

    /**
     * @return the peakAnnoated
     */
    public File getPeakAnnoated() {
        return peakAnnoated;
    }

    /**
     * @param peakAnnoated the peakAnnoated to set
     */
    public void setPeakAnnoated(File peakAnnoated) {
        this.peakAnnoated = peakAnnoated;
    }

    /**
     * @return the rCodeFile
     */
    public File getrCodeFile() {
        return rCodeFile;
    }

    /**
     * @param rCodeFile the rCodeFile to set
     */
    public void setrCodeFile(File rCodeFile) {
        this.rCodeFile = rCodeFile;
    }

    public ChIPpeakAnno(File inputFile, String inputFileFormat, File barChart, File peakAnnoated, File outputFolder, File goAnalysisOutputFile, File pathwayAnalysisOutputFile, File rCodeFile, Genome genome) {
        this.inputFile = inputFile;
        this.inputFileFormat = inputFileFormat;
        this.barChart = barChart;
        this.peakAnnoated = peakAnnoated;
        this.outputFolder = outputFolder;
        this.goAnalysisOutputFile = goAnalysisOutputFile;
        this.pathwayAnalysisOutputFile = pathwayAnalysisOutputFile;
        this.rCodeFile = rCodeFile;
        this.genome = genome;
    }

    @Override
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outputFile) {
        String[] commandArray =  
            {   "Rscript",
                inputFile.getAbsolutePath()
            };
        return commandArray;
    }

    public String[] getCommand() {
        String[] commandArray =  
            {   "Rscript",
                this.getrCodeFile().getAbsolutePath()
            };
        return commandArray;
    }
     
    @Override
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

    @Override
    public boolean isWorking() {
        // current there is no code to check individual package 
        System.out.println("Method: umac.guava.ChIPseeker.isWorking()");
        System.out.println("This method is not complete");
        return false;
    }

 
    boolean isSuccess(String errorlog){
        Pattern pFail = Pattern.compile("fail",Pattern.CASE_INSENSITIVE);
        Pattern pError = Pattern.compile("error",Pattern.CASE_INSENSITIVE);
        Matcher mFail =  pFail.matcher(errorlog);
        Matcher mError =  pError.matcher(errorlog);
        
        if(mFail.find() || mError.find()){
            return false;
        }
        return true;
    }
    
}
