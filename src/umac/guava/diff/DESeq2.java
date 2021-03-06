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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mayurdivate
 */
public class DESeq2 extends Program {

    private File deseq2OutputFile;
    private ArrayList<DifferentialInputFile> dfInputFiles;
    private File commonPeakBedFile;
    private File deseq2CodeFile;
    private double pvalue;
    private double foldchange;
    private File volcanoPlotFile;
    private File pcaPlotFile;

    public String getGuavaDESeq2Code() {

        System.out.println("umac.guava.diff.DESeq2.getGuavaDESeq2Code()");
        String code = "\n"
                + getLibraries();

        return code;

    }

    public String getLibraries() {
        String code = "library(Rsubread)" + "\n"
                + "library(DESeq2)\n";
        return code;
    }

    public String getDESeq2Code(int cpus) {

        String code = "\n";
        code = code + getLibraries() + "\n";
        String coldata_sampleNames = "SampleName <- c(";
        String coldata_conditons = "condition <- c(";
        String bamFiles = "bamfiles <- c(";
        String seqType = "seqType <- c(";

        for (int index = 0; index < this.dfInputFiles.size(); index++) {
            if (this.dfInputFiles.get(index).getType().equalsIgnoreCase("bam")) {

                if (this.dfInputFiles.get(index).getCondition().equalsIgnoreCase("control")) {
                    coldata_conditons = coldata_conditons + "\"" + "control" + "\",";
                } else {
                    coldata_conditons = coldata_conditons + "\"" + "treated" + "\",";
                }
                seqType = seqType + "\"paired-end\",";
                coldata_sampleNames = coldata_sampleNames + "\"" + this.dfInputFiles.get(index).getDiifInputFile().getName() + "\",\n";
                bamFiles = bamFiles + "\"" + this.dfInputFiles.get(index).getDiifInputFile().getAbsolutePath() + "\",\n";
            }
        }

        coldata_conditons = coldata_conditons.replaceAll(",$", ")");
        coldata_sampleNames = coldata_sampleNames.replaceAll(",$", ")");
        bamFiles = bamFiles.replaceAll(",$", ")");
        seqType = seqType.replaceAll(",$", ")");

        code = code + "\n"
                + "\n" + coldata_sampleNames + "\n"
                + "\n" + coldata_conditons + "\n"
                + "\n" + bamFiles + "\n"
                + "\n" + seqType + "\n";

        code = code + "\n" + "coldata <- "
                + "as.data.frame(cbind(condition=condition,  type = seqType),"
                + "\n" + "row.names = SampleName)\n"
                + "\n";

        // count matrix 
        code = code
                + "\n"
                + "bedFile <- \"" + this.getCommonPeakBedFile().getAbsolutePath() + "\"" + "\n"
                + "peaks <- read.table(bedFile,stringsAsFactors = FALSE)" + "\n"
                + "peaks <- peaks[,c(4,1,2,3)]" + "\n"
                + "colnames(peaks) <- c(\"GeneID\",\"Chr\",\"Start\",\"End\")" + "\n"
                + "peaks <- cbind(peaks[,],Strand = rep(\"*\",length(peaks$GeneID)))" + "\n"
                + "peaks <- data.frame(peaks, stringsAsFactors = FALSE)" + "\n"
                + "\n"
                + "### count reads" + "\n"
                + "readCount <- featureCounts(files = bamfiles,\n"
                + "annot.ext = peaks,\n"
                + "isPairedEnd = TRUE, \n"
                + "nthreads = " + cpus + ",\n"
                + "countChimericFragments = FALSE,\n"
                + "countMultiMappingReads = TRUE,\n"
                + "requireBothEndsMapped = TRUE)\n"
                + "\n"
                + "\n" + "colnames(readCount$counts) <- SampleName"
                + "\n";

        int minReads = 10;
        int minSamples = 2;

        code = code
                + "### create DESeq2 object" + "\n"
                + "dds <- DESeqDataSetFromMatrix(readCount$counts,colData = coldata,design = ~ condition)" + "\n"
                + "" + "\n"
                + "### filter peaks base on minimum reads" + "\n"
                + "keep <- rowSums(counts(dds) >= " + minReads + ") >=" + minSamples + "\n"
                + "dds <- dds[keep,]" + "\n"
                + "\n" + ""
                + "### relevel conditions\n"
                + "dds$condition <- factor(dds$condition , levels=c(\"control\",\"treated\"))" + "\n"
                + "dds <- DESeq(dds)" + "\n"
                + "res <- results(dds)" + "\n"
                + "\n";

        code = code + getPlotPCACode();

        code = code
                + "\n\n### create DESeq2 differential peaks" + "\n"
                + "colnames(peaks)[1] <- \"name\"" + "\n"
                + "d2res <- as.data.frame(res)" + "\n"
                + "d2res$name <- rownames(d2res)" + "\n"
                + "d2res <- merge(peaks[,-5],d2res,by=c(\"name\"))" + "\n"
                + "d2res$regulation <- rep(NA,nrow(d2res))" + "\n"
                + "pcutoff <- " + this.getPvalue() + "\n"
                + "fcCutoff <- " + this.getFoldchange() + "\n"                + "\n"
                + "closeText <- \"gained-close\"" + "\n"
                + "openText <- \"gained-open\"" + "\n"
                + "noText <- \"No-change\"" + "\n" +"\n"
                + "d2res[d2res$log2FoldChange >= fcCutoff & d2res$pvalue <= pcutoff,11] <- openText" + "\n"
                + "d2res[d2res$log2FoldChange <= (-1 * fcCutoff) & d2res$pvalue <= pcutoff,11] <- closeText " + "\n"
                + "notchanged <- (d2res$log2FoldChange > -fcCutoff & d2res$log2FoldChange < fcCutoff )" + "\n"
                + "d2res[notchanged | d2res$pvalue > pcutoff,11] <- noText" + "\n"
                + "write.table(d2res, file=\"" + this.getDeseq2OutputFile().getAbsolutePath() + "\",\n"
                + "sep = \"\\t\", quote = FALSE)"
                + "\n"
                + "\n";

        code = code +getVplotCode(this.getPvalue(), this.getFoldchange(), this.getVolcanoPlotFile());
        
        return code;

    }

    private String getPlotPCACode() {

        int width = 640;
        int height = 400;

        String code = "\n"
                + "\n"
                + "library(ggplot2)\n"
                + "vsd <- vst(dds, blind=FALSE)\n"
                + "pcaData <- plotPCA(vsd, intgroup=c(\"condition\"), returnData=TRUE)\n"
                + "percentVar <- round(100 * attr(pcaData, \"percentVar\"))\n"
                + "p <- ggplot(pcaData, aes(PC1, PC2, color=condition, shape=condition)) +\n"
                + "  geom_point(size=3) +\n"
                + "  xlab(paste0(\"PC1: \",percentVar[1],\"% variance\")) +\n"
                + "  ylab(paste0(\"PC2: \",percentVar[2],\"% variance\")) \n"
                + "p <- p + theme(legend.text = element_text(size = 11))\n"
                + "p <- p + theme(legend.title = element_text(size = 12))\n"
                + "\n"
                + "jpeg(\"" + this.getPcaPlotFile().getAbsolutePath() + "\",width = " + width + ", height = " + height + ")\n"
                + "print(p)\n"
                + "dev.off()\n"
                + "";

        return code;
    }

    private String getVplotCode(double pvalue, double foldChange, File outFile) {
        int height = 400;
        int width = 730;

        String code = "";
        code = code + "\n"
                + "library(ggplot2)"
                + "\n"
                + "\n"
                + "\n"
                + "pcutoff <- " + pvalue + "\n"
                + "fcCutoff <- " + foldChange + "\n"
                + "\n"
                + "plotDF <- d2res[,c(1,6,9,11)]" + "\n"
                + "plotDF$regulation <- factor(plotDF$regulation,levels = c(closeText,noText,openText))" + "\n"
                + "downColor <- \"red\"" + "\n"
                + "noChangeColor <- \"black\"" + "\n"
                + "upColor <- \"green4\"" + "\n"
                + "levels(plotDF$regulation)" + "\n"
                + "titleSum <- summary(plotDF$regulation)" + "\n"
                + "titleSum" + "\n"
                + "\n" 
                + "openX <- 0" + "\n"
                + "closeX <- 0" + "\n"
                + "if(!is.na(titleSum[openText])){" + "\n"
                + "  openX <- titleSum[openText]" + "\n"
                + "}" + "\n"
                + "\n"
                + "if(!is.na(titleSum[closeText])){" + "\n"
                + "  closeX <- titleSum[closeText]" + "\n"
                + "}" + "\n"
                + "\n" 
                + "plotSubTitle <- paste(paste(\"gained-closed regions =\",closeX,sep = \" \")," + "\n"
                + "                      paste(\"gained-open regions =\",openX,sep = \" \")," + "\n"
                + "                      sep = \"      \")" + "\n"
                + "\n"
                + "\n"
                + "xlimit <- round(max(abs(plotDF$log2FoldChange))+1.1)\n"
                + ""
                + "p <- ggplot(plotDF, aes(x = log2FoldChange, y = -1 * log10(pvalue), col=regulation))\n"
                + "p <- p + geom_point(size=0.8)" + "\n"
                + "p <- p + scale_color_manual(name = \"Regulation\", values = c(downColor,noChangeColor,upColor))" + "\n"
                + "p <- p + scale_shape_manual(name = \"Regulation\" , values = c(20,20,20))" + "\n"
                + "p <- p + scale_x_continuous(limits = c(-xlimit, xlimit))" + "\n"
                + "p <- p + labs(subtitle = plotSubTitle,x = \"log2(FoldChange)\", y = \"-log10(Pvalue)\")\n"
                + "p <- p + theme(legend.text = element_text(size = 10))\n"
                + "p <- p + theme(legend.title = element_text(size = 11))\n"
                + "p <- p + theme(axis.title = element_text(size = 10))\n"
                + "p <- p + theme(plot.subtitle = element_text(size = 11,hjust = 0.5))"+ "\n" 
                + "\n" 
                + "jpeg(" + "\"" + outFile.getAbsoluteFile() + "\"" + ",height=" + height + ",width=" + width + ",res = 100,quality = 100)" + "\n"
                + "print(p)" + "\n" 
                + "dev.off()";

        return code;
    }

    public DESeq2() {
    }

    public DESeq2(File deseq2OutputCSV, ArrayList<DifferentialInputFile> dfInputFiles,
            File commonPeakBedFile, File deseq2CodeFile,
            double pvalue, double foldchange, File volacnoPlotFile, File pcaPlotfile) {
        this.deseq2OutputFile = deseq2OutputCSV;
        this.dfInputFiles = dfInputFiles;
        this.commonPeakBedFile = commonPeakBedFile;
        this.deseq2CodeFile = deseq2CodeFile;
        this.pvalue = pvalue;
        this.foldchange = foldchange;
        this.volcanoPlotFile = volacnoPlotFile;
        this.pcaPlotFile = pcaPlotfile;
    }

    @Override
    public String[] getCommand(File inputFile) {
        String[] commandArray
                = {"Rscript",
                    inputFile.getAbsolutePath()
                };
        return commandArray;
    }

    @Override
    public String[] runCommand(String[] commandArray) {
        String[] log = new String[2];

        try {
            
            ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
            Process process = processBuilder.start();
            String stdOUT = getSTDoutput(process);
            String errorLog = getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
            return log;
        } catch (IOException ex) {
            System.out.println("\t\t" + ex.getMessage());
            return null;
        }
    }

    public boolean isSuccessful(String[] log) {

        if (log[1] != null) {
            Pattern errorPattern = Pattern.compile("error", Pattern.CASE_INSENSITIVE);
            Pattern exeHaltedPattern = Pattern.compile("Execution halted", Pattern.CASE_INSENSITIVE);
            Matcher errorMatcher = errorPattern.matcher(log[1]);
            Matcher exeHaltedMatcher = exeHaltedPattern.matcher(log[1]);
            if (errorMatcher.find() || exeHaltedMatcher.find()) {
                return false;
            }
        }
        return true;

    }

    @Override
    public boolean isWorking() {

        String[] commandArray = {"R", "--version"};
        String[] log = runCommand(commandArray);
        return log[0] != null;
    }

    /**
//     * @return the deseq2OutputFile
     */
    public File getDeseq2OutputFile() {
        return deseq2OutputFile;
    }

    /**
     * @param deseq2OutputCSV the deseq2OutputFile to set
     */
    public void setDeseq2OutputFile(File deseq2OutputFile) {
        this.deseq2OutputFile = deseq2OutputFile;
    }

    /**
     * @return the dfInputFiles
     */
    public ArrayList<DifferentialInputFile> getDfInputFiles() {
        return dfInputFiles;
    }

    /**
     * @param dfInputFiles the dfInputFiles to set
     */
    public void setDfInputFiles(ArrayList<DifferentialInputFile> dfInputFiles) {
        this.dfInputFiles = dfInputFiles;
    }

    /**
     * @return the commonPeakBedFile
     */
    public File getCommonPeakBedFile() {
        return commonPeakBedFile;
    }

    /**
     * @param commonPeakBedFile the commonPeakBedFile to set
     */
    public void setCommonPeakBedFile(File commonPeakBedFile) {
        this.commonPeakBedFile = commonPeakBedFile;
    }

    /**
     * @return the deseq2CodeFile
     */
    public File getDeseq2CodeFile() {
        return deseq2CodeFile;
    }

    /**
     * @param deseq2CodeFile the deseq2CodeFile to set
     */
    public void setDeseq2CodeFile(File deseq2CodeFile) {
        this.deseq2CodeFile = deseq2CodeFile;
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

    /**
     * @return the volcanoPlotFile
     */
    public File getVolcanoPlotFile() {
        return volcanoPlotFile;
    }

    /**
     * @param volcanoPlotFile the volcanoPlotFile to set
     */
    public void setVolcanoPlotFile(File volcanoPlotFile) {
        this.volcanoPlotFile = volcanoPlotFile;
    }

    /**
     * @return the pcaPlotFile
     */
    public File getPcaPlotFile() {
        return pcaPlotFile;
    }

    /**
     * @param pcaPlotFile the pcaPlotFile to set
     */
    public void setPcaPlotFile(File pcaPlotFile) {
        this.pcaPlotFile = pcaPlotFile;
    }

}
