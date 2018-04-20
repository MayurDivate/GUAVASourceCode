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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import umac.guava.ChIPpeakAnno;
import umac.guava.Genome;
import umac.guava.GuavaOutputFiles;

/**
 *
 * @author mayurdivate
 */
public class DifferentialOutputFiles {
    
    private static File logFile;
    private static File outFolder;
    
    private File outputFolder;
    private File controlTreatmentCommonPeakBed;
    private File deseqRcode;
    private File deseqResult;
    private File volcanoPlot;
    private File pcaPlot;
    private File diffpeakBed;
    private ChIPpeakAnno chipPeakAnno;
    private File outExcel;
    
    public DifferentialOutputFiles(File outputFolder, File controlTreatmentCommonPeakBed,
            File deseqRcode, File deseqResult, File volcanoPlot, File pcaplot,
            File diffpeakBed, ChIPpeakAnno chipPeakAnno, File outExcel) {
        this.outputFolder = outputFolder;
        this.controlTreatmentCommonPeakBed = controlTreatmentCommonPeakBed;
        this.deseqRcode = deseqRcode;
        this.deseqResult = deseqResult;
        this.volcanoPlot = volcanoPlot;
        this.pcaPlot = pcaplot;    
        this.diffpeakBed = diffpeakBed;
        this.chipPeakAnno = chipPeakAnno;
        this.outExcel = outExcel;
    }
    
    public static DifferentialOutputFiles getDifferentialOutputFiles(File destination, Genome genome){
        
        String project = DifferentialInputFrame1.projectName + "_";
        
        File outputFolder        = new File(destination, project + "GUAVA_Differental_analysis");
        DifferentialOutputFiles.setLogFile(new File(outputFolder, project + "log.txt"));
        
        File controlTreatmentBed = new File(outputFolder, project + "controlTreatmentPeaks.bed");
        File diffPeakBed         = new File(outputFolder, project + "diffpeaks.bed");
        File deseqRcode          = new File(outputFolder, project + "DESeq2.R");
        File deseqResult         = new File(outputFolder, project + "DESeq2_results.txt");
        File vplot               = new File(outputFolder, project + "vplot.jpg");
        File pcaPlot             = new File(outputFolder, project + "PCA_plot.jpg");
        File outExcel             = new File(outputFolder, project + "Results.xlsx");
        
        ChIPpeakAnno chipPeakAnno = ChIPpeakAnno.getChIPpeakAnnoObject(deseqResult, project, "BED", genome);
        
        DifferentialOutputFiles dof = new DifferentialOutputFiles(outputFolder, controlTreatmentBed,
                deseqRcode, deseqResult, vplot, pcaPlot, diffPeakBed, chipPeakAnno,outExcel);
        
        GuavaOutputFiles.logFile = DifferentialOutputFiles.getLogFile();

        return dof;

    }
    
    
    public boolean writeSummary(GdiffInput atacInput){
        
        try {
            FileWriter logFileWriter = new FileWriter(DifferentialOutputFiles.getLogFile(),true);
            BufferedWriter logFileBufferedWriter =  new BufferedWriter(logFileWriter);
            PrintWriter logPrintWriter = new PrintWriter(logFileBufferedWriter);
            
            logPrintWriter.append("#######################################################");
            logPrintWriter.flush();

            logPrintWriter.append("\n# Project summary \n");
            logPrintWriter.flush();

            logPrintWriter.append("# Name of the project:\t"+atacInput.getProjectName()+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("# Genome build:\t"+atacInput.getGenome().getGenomeName()+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("# Pvalue:\t"+atacInput.getPvalue()+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("# Foldchange:\t"+atacInput.getFoldChange()+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("# Upstream:\t"+atacInput.getUpstream()+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("# Downstream:\t"+atacInput.getDownstream()+"\n");
            logPrintWriter.flush();

            logPrintWriter.append("# Input files\n");
            logPrintWriter.flush();

            for(int i=0; i < atacInput.getDiffInputfiles().size(); i++){
                logPrintWriter.append("# "+atacInput.getDiffInputfiles().get(i).getCondition()+" REP ");
                logPrintWriter.append(atacInput.getDiffInputfiles().get(i).getReplicateNumber()+" ");
                logPrintWriter.append(atacInput.getDiffInputfiles().get(i).getType()+":\t");
                logPrintWriter.append(atacInput.getDiffInputfiles().get(i).getDiifInputFile().getName()+"\n");
                logPrintWriter.flush();
            }
            
            logPrintWriter.append("#######################################################");
            logPrintWriter.flush();
            logPrintWriter.close();
            return true;
            
        } catch (IOException ex) {
            Logger.getLogger(DifferentialOutputFiles.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    
    /**
     * @return the logFile
     */
    public static File getLogFile() {
        return logFile;
    }

    /**
     * @param aLogFile the logFile to set
     */
    public static void setLogFile(File aLogFile) {
        logFile = aLogFile;
    }

    /**
     * @return the outFolder
     */
    public static File getOutFolder() {
        return outFolder;
    }

    /**
     * @param aOutFolder the outFolder to set
     */
    public static void setOutFolder(File aOutFolder) {
        outFolder = aOutFolder;
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
     * @return the controlTreatmentCommonPeakBed
     */
    public File getControlTreatmentCommonPeakBed() {
        return controlTreatmentCommonPeakBed;
    }

    /**
     * @param controlTreatmentCommonPeakBed the controlTreatmentCommonPeakBed to set
     */
    public void setControlTreatmentCommonPeakBed(File controlTreatmentCommonPeakBed) {
        this.controlTreatmentCommonPeakBed = controlTreatmentCommonPeakBed;
    }

    /**
     * @return the deseqRcode
     */
    public File getDeseqRcode() {
        return deseqRcode;
    }

    /**
     * @param deseqRcode the deseqRcode to set
     */
    public void setDeseqRcode(File deseqRcode) {
        this.deseqRcode = deseqRcode;
    }

    /**
     * @return the deseqResult
     */
    public File getDeseqResult() {
        return deseqResult;
    }

    /**
     * @param deseqResult the deseqResult to set
     */
    public void setDeseqResult(File deseqResult) {
        this.deseqResult = deseqResult;
    }

    /**
     * @return the volcanoPlot
     */
    public File getVolcanoPlot() {
        return volcanoPlot;
    }

    /**
     * @param volcanoPlot the volcanoPlot to set
     */
    public void setVolcanoPlot(File volcanoPlot) {
        this.volcanoPlot = volcanoPlot;
    }

    /**
     * @return the diffpeakBed
     */
    public File getDiffpeakBed() {
        return diffpeakBed;
    }

    /**
     * @param diffpeakBed the diffpeakBed to set
     */
    public void setDiffpeakBed(File diffpeakBed) {
        this.diffpeakBed = diffpeakBed;
    }

    /**
     * @return the chipPeakAnno
     */
    public ChIPpeakAnno getChipPeakAnno() {
        return chipPeakAnno;
    }

    /**
     * @param chipPeakAnno the chipPeakAnno to set
     */
    public void setChipPeakAnno(ChIPpeakAnno chipPeakAnno) {
        this.chipPeakAnno = chipPeakAnno;
    }

    public File getPcaPlot() {
        return pcaPlot;
    }

    public void setPcaPlot(File pcaPlot) {
        this.pcaPlot = pcaPlot;
    }

    /**
     * @return the outExcel
     */
    public File getOutExcel() {
        return outExcel;
    }

    /**
     * @param outExcel the outExcel to set
     */
    public void setOutExcel(File outExcel) {
        this.outExcel = outExcel;
    }

    
    
}
