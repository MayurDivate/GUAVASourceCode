/*
 * Copyright (C) 2017 mayurdivate
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
package umac.guava.diff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import umac.guava.Bowtie;

/**
 *
 * @author mayurdivate
 */
public class DESeq2 extends Program{

    private File deseq2OutputCSV;
    private ArrayList<DifferentialInputFile> dfInputFiles;
    private File commonPeakBedFile;
    private File deseq2CodeFile;
    private double pvalue;
    private double foldchange;
    private File volcanoPlotFile;
    
    
    /**
     * @return the volacnoPlotFile
     */
    public File getVolacnoPlotFile() {
        return volcanoPlotFile;
    }

    /**
     * @param volacnoPlotFile the volacnoPlotFile to set
     */
    public void setVolacnoPlotFile(File volacnoPlotFile) {
        this.volcanoPlotFile = volacnoPlotFile;
    }
    

    public void createDESeq2Code(DESeq2 deseq2) {
        
    }
    
    public String getDESeq2Code(ArrayList<DifferentialInputFile> dfInput, File peakBedFile, File outputCSV) {
        
        String code = "library(Rsubread)"+"\n"+"library(DESeq2)\n";

        String coldata_sampleNames = "SampleName <- c(";
        String coldata_conditons = "condition <- c(";
        String bamFiles = "bamfiles <- c(";
        String seqType = "seqType <- c(";
        
        for(int index = 0; index < dfInput.size(); index++){
            if(dfInput.get(index).getType().equalsIgnoreCase("bam")){
                
             if(dfInput.get(index).getCondition().equalsIgnoreCase("control")){
                 coldata_conditons = coldata_conditons +"\""+ "untreated"+"\",";
             }else{
                 coldata_conditons = coldata_conditons +"\""+ "treated"+"\",";
             }
             seqType = seqType + "\"paired-end\",";
             coldata_sampleNames = coldata_sampleNames +"\""+ dfInput.get(index).getDiifInputFile().getName()+"\",";
             bamFiles = bamFiles + "\"" + dfInput.get(index).getDiifInputFile().getAbsolutePath()+"\",";
            }     
        }
            
        coldata_conditons = coldata_conditons.replaceAll(",$", ")");
        coldata_sampleNames = coldata_sampleNames.replaceAll(",$", ")");
        bamFiles = bamFiles.replaceAll(",$", ")");
        seqType = seqType.replaceAll(",$", ")");
        
        code = code + "\n" + coldata_sampleNames;
        code = code + "\n" + coldata_conditons;
        code = code + "\n" + bamFiles;
        code = code + "\n" + seqType; 
                
        code = code + "\n" + "coldata <- as.data.frame(cbind("
                + "filenames = SampleName, "
                + "condition=condition, "
                + "type = seqType"
                + "))";
        

        code = code + "\n" + "rownames(coldata) <- coldata[,1]";
        code = code + "\n" + "coldata <- coldata[,2:3]";
        code = code + "\n" + "bedFile <- \""+peakBedFile.getAbsolutePath()+"\"";
        code = code + "\n" + "peaks <- read.table(bedFile,stringsAsFactors = FALSE)";
        code = code + "\n" + "peaks <- peaks[,c(4,1,2,3)]";
        code = code + "\n" + "colnames(peaks) <- c(\"GeneID\",\"Chr\",\"Start\",\"End\")";
        code = code + "\n" + "peaks <- cbind(peaks[,],Strand = rep(\"+\",length(peaks$GeneID)))";
        code = code + "\n" + "peaks <- data.frame(peaks, stringsAsFactors = FALSE)";
        code = code + "\n" + "readCount <- featureCounts("+bamFiles+",annot.ext = peaks)";
        code = code + "\n" + "colnames(readCount$counts) <- SampleName";
        code = code + "\n" + "dds <- DESeqDataSetFromMatrix(readCount$counts,colData = coldata,design = ~ condition)";
        code = code + "\n" + "dds$condition <- factor(dds$condition , levels=c(\"untreated\",\"treated\"))";
        code = code + "\n" + "dds <- DESeq(dds)";
        code = code + "\n" + "res <- results(dds)";
        
        return code;

    }
  
    public String getVplotCode(double pvalue, double foldChange,File outFile ) {
        int height = 350;
        int width = 500;
        String code = "";
        code = code + "\n" + "library(ggplot2)";
        code = code + "\n" + "";
        code = code + "\n" + "";
        code = code + "\n" + "pcutoff <- "+pvalue;
        code = code + "\n" + "fcCutoff <- "+foldChange;
        code = code + "\n";
        code = code + "\n" + "plotDF <- results[,c(1,6,9,11)]";
        code = code + "\n" + "plotDF[,4] <- factor(plotDF[,4])";
        code = code + "\n" + "titleSum <- summary(plotDF[,4])";
        code = code + "\n";
        code = code + "\n" + "openX <- 0";
        code = code + "\n" + "closeX <- 0";
        code = code + "\n";
        code = code + "\n" + "if(!is.na(titleSum[\"gained-open\"])){" ;
        code = code + "\n" +"  openX <- titleSum[\"gained-open\"]" ;
        code = code + "\n" +"}" ;
        code = code + "\n";
        code = code + "\n" +"if(!is.na(titleSum[\"gained-closed\"])){" ;
        code = code + "\n" +"  closeX <- titleSum[\"gained-closed\"]" ;
        code = code + "\n" +"}";
        code = code + "\n" + "plotSubTitle <- paste(paste(\"gained-closed regions =\",closeX,sep = \" \"),";
        code = code + "\n" + "                      paste(\"gained-open regions=\",openX,sep = \" \"),";
        code = code + "\n" + "                      sep = \"    \")";
        code = code + "\n";
        code = code + "\n" + "p <- ggplot(plotDF, aes(x = log2FoldChange, y = -1 * log10(pvalue), col=regulation))";
        code = code + "\n" + "p <- p + scale_color_manual(values = c(\"red\",\"green\",\"black\"))";
        code = code + "\n" + "p <- p + geom_jitter()";
        code = code + "\n" + "p <- p + labs(subtitle = plotSubTitle,x = \"log2(FoldChange)\", y = \"-log10(Pvalue)\", colour = \"Regulation\")";
        
        code = code + "\n" + "jpeg("+"\""+outFile.getAbsoluteFile()+"\""+",height="+height+",width="+width+")\n";
        code = code + "\n" + "print(p)";
        code = code + "\n" + "dev.off()";

        return code;
    }
    
    public String getDESeq2ResultsCode(){
        String code = "";
        code = code + "\n" + "ctCommonPeaks <- read.table(bedFile)";
        code = code + "\n" + "colnames(ctCommonPeaks) <- c(\"chr\",\"start\",\"end\",\"name\")";
        code = code + "\n" + "head(ctCommonPeaks)";
        code = code + "\n" + "d2res <- as.data.frame(res)";
        code = code + "\n" + "d2res$name <- rownames(d2res)";
        code = code + "\n" + "d2res <-merge(ctCommonPeaks,d2res,by=c(\"name\"))";
        code = code + "\n" + "d2res$regulation <- rep(\"not-changed\",nrow(d2res))";
        code = code + "\n" + "pcutoff <- "+this.getPvalue();
        code = code + "\n" + "fcCutoff <- "+this.getFoldchange();
        code = code + "\n" + "d2res[d2res$log2FoldChange >= fcCutoff & d2res$pvalue <= pcutoff,11] <- \"gained-open\"";
        code = code + "\n" + "d2res[d2res$log2FoldChange <= -fcCutoff & d2res$pvalue <= pcutoff,11] <- \"gained-closed\"";
        code = code + "\n" + "library(ChIPpeakAnno)";
        code = code + "\n" + "library(TxDb.Hsapiens.UCSC.hg19.knownGene)";
        code = code + "\n" + "annoData <- toGRanges(TxDb.Hsapiens.UCSC.hg19.knownGene, feature=\"gene\")";
        code = code + "\n" + "peaks <- toGRanges(d2res[,c(2:4,1)], format=\"BED\", header=FALSE) ";
        code = code + "\n" + "seqlevelsStyle(peaks) <- seqlevelsStyle(annoData)";
        code = code + "\n" + "annotatedPeaks <- annotatePeakInBatch(peaks,AnnotationData=annoData)";
        code = code + "\n" + "library(org.Hs.eg.db)";
        code = code + "\n" + "annotatedPeaks$symbol <- xget(annotatedPeaks$feature,org.Hs.egSYMBOL)";
        code = code + "\n" + "dfAnnPeak <- as.data.frame(annotatedPeaks)[,c(6,15,11,12)]";
        code = code + "\n" + "results <- merge(d2res,dfAnnPeak,by.x=\"name\",by.y=\"peak\")";
        code = code + "\n" + "";
        code = code + "\n" + "write.table(results,file = \""+this.getDESeq2OutputCSV().getAbsolutePath()+"\", sep = \"\\t\")";
        return code;
    }

    public DESeq2() {
    }
    
    

    /**
     * @return the differentialResults
     */
    public File getDESeq2OutputCSV() {
        return deseq2OutputCSV;
    }

    /**
     * @param differentialResults the differentialResults to set
     */
    public void setDESeq2OutputCSV(File differentialResults) {
        this.deseq2OutputCSV = differentialResults;
    }

    /**
     * @return the dfInput
     */
    public ArrayList<DifferentialInputFile> getDfInput() {
        return dfInputFiles;
    }

    /**
     * @param dfInput the dfInput to set
     */
    public void setDfInput(ArrayList<DifferentialInputFile> dfInput) {
        this.dfInputFiles = dfInput;
    }

    /**
     * @return the peakBedFile
     */
    public File getPeakBedFile() {
        return commonPeakBedFile;
    }

    /**
     * @param peakBedFile the peakBedFile to set
     */
    public void setPeakBedFile(File peakBedFile) {
        this.commonPeakBedFile = peakBedFile;
    }

    /**
     * @return the deseq2Code
     */
    public File getDeseq2Code() {
        return deseq2CodeFile;
    }

    /**
     * @param deseq2Code the deseq2Code to set
     */
    public void setDeseq2Code(File deseq2Code) {
        this.deseq2CodeFile = deseq2Code;
    }

    public DESeq2(File deseq2OutputCSV, ArrayList<DifferentialInputFile> dfInputFiles, File commonPeakBedFile, File deseq2CodeFile, double pvalue, double foldchange, File volacnoPlotFile) {
        this.deseq2OutputCSV = deseq2OutputCSV;
        this.dfInputFiles = dfInputFiles;
        this.commonPeakBedFile = commonPeakBedFile;
        this.deseq2CodeFile = deseq2CodeFile;
        this.pvalue = pvalue;
        this.foldchange = foldchange;
        this.volcanoPlotFile = volacnoPlotFile;
    }


    @Override
    public String[] getCommand(File inputFile) {
        System.out.println("DESeq2");
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
            System.out.println(getArrayToString(commandArray));
            ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
            Process process =  processBuilder.start();
            String stdOUT = getSTDoutput(process);
            String errorLog = getSTDerror(process);
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
        String[] log = runCommand(commandArray);
        return log[0] != null;
    }

    /**
     * @return the pvalue
     */
    public double getPvalue() {
        return pvalue;
    }

    /**
     * @param pvalue the pvalue to set
     */
    public void setPvalue(double pvalue) {
        this.pvalue = pvalue;
    }

    /**
     * @return the foldchange
     */
    public double getFoldchange() {
        return foldchange;
    }

    /**
     * @param foldchange the foldchange to set
     */
    public void setFoldchange(double foldchange) {
        this.foldchange = foldchange;
    }
    
    
}
