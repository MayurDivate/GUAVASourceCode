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
import umac.guava.OutputFiles;

/**
 *
 * @author mayurdivate
 */
public class DifferentialOutputFiles {
    private File outputFolder;
    private File deseqRcode;
    private File goPathwayRcode;
    private File deseqResult;
    private File volcanoPlot;
    private File controlTreatmentCommonPeakBed;
    private File goresults;
    private File pathwayresults;
    public static File logFile;
    static File outFolder;
    
    public static DifferentialOutputFiles getDifferentialOutputFiles(File destination){
        String project = DifferentialInputFrame1.projectName+"_";
        
        File outputFolder = new File (destination.getAbsolutePath()+System.getProperty("file.separator")+project+"GUAVA_Differental_analysis");
        File deseqRcode = new File (outputFolder.getAbsolutePath()+System.getProperty("file.separator")+project+"DESeq.R");
        File goPathwayRcode = new File (outputFolder.getAbsolutePath()+System.getProperty("file.separator")+project+"GoPathway.R");
        File deseqResult = new File (outputFolder.getAbsolutePath()+System.getProperty("file.separator")+project+"DESeq_results.txt");
        File goResult = new File (outputFolder.getAbsolutePath()+System.getProperty("file.separator")+project+"GO_results.txt");
        File pathwayResult = new File (outputFolder.getAbsolutePath()+System.getProperty("file.separator")+project+"Pathway_results.txt");
        File vplot = new File (outputFolder.getAbsolutePath()+System.getProperty("file.separator")+project+"vplot.jpeg");
        DifferentialOutputFiles.setLogFile(new File (outputFolder.getAbsolutePath()+System.getProperty("file.separator")+"log.txt"));
        File controlTreatmentBed = new File (outputFolder.getAbsolutePath()+System.getProperty("file.separator")+project+"controlTreatmentPeaks.bed");
        if(!outputFolder.exists()){
            outputFolder.mkdir();
            try {
                DifferentialOutputFiles.getLogFile().createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DifferentialOutputFiles.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        DifferentialOutputFiles dof = new DifferentialOutputFiles(outputFolder, deseqRcode, goPathwayRcode,  deseqResult,
                vplot, controlTreatmentBed,goResult,pathwayResult);
        OutputFiles.logFile = DifferentialOutputFiles.getLogFile();
        return dof;
    }
    
    
    public DifferentialOutputFiles(File outputFolder, File deseqRcode, File goPathwayCode,  File deseqResult, File volcanoPlot,
            File controlTreatmentCommonPeakBed, File goresults, File pathwayresults) {
        this.outputFolder = outputFolder;
        this.deseqRcode = deseqRcode;
        this.goPathwayRcode =  goPathwayCode;
        this.deseqResult = deseqResult;
        this.volcanoPlot = volcanoPlot;
        this.controlTreatmentCommonPeakBed = controlTreatmentCommonPeakBed;
        this.goresults = goresults;
        this.pathwayresults = pathwayresults;
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
     * @return the goresults
     */
    public File getGoresults() {
        return goresults;
    }

    public void setGoresults(File goresults) {
        this.goresults = goresults;
    }

    public File getPathwayresults() {
        return pathwayresults;
    }

    public void setPathwayresults(File pathwayresults) {
        this.pathwayresults = pathwayresults;
    }

    public static File getLogFile() {
        return logFile;
    }
 
    public static void setLogFile(File aLogFile) {
        logFile = aLogFile;
    }

    /**
     * @return the goPathwayRcode
     */
    public File getGoPathwayRcode() {
        return goPathwayRcode;
    }

    /**
     * @param goPathwayRcode the goPathwayRcode to set
     */
    public void setGoPathwayRcode(File goPathwayRcode) {
        this.goPathwayRcode = goPathwayRcode;
    }
    
    static void writeSummary(GdiffInput atacInput){
        
        try {
            FileWriter logFileWriter = new FileWriter(DifferentialOutputFiles.getLogFile(),true);
            BufferedWriter logFileBufferedWriter =  new BufferedWriter(logFileWriter);
            PrintWriter logPrintWriter = new PrintWriter(logFileBufferedWriter);
            
            logPrintWriter.append("\nProject summary \n");
            logPrintWriter.flush();

            logPrintWriter.append("Project Name:\t"+atacInput.getProjectName()+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("Genome build:\t"+atacInput.getGenomeBuild()+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("Pvalue:\t"+atacInput.getPvalue()+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("Foldchange:\t"+atacInput.getFoldChange()+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("Upstream:\t"+atacInput.getUpstream()+"\n");
            logPrintWriter.flush();
            logPrintWriter.append("Downstream:\t"+atacInput.getDownstream()+"\n");
            logPrintWriter.flush();

            logPrintWriter.append("Input files\n");
            logPrintWriter.flush();

            for(int i=0; i < atacInput.getDiffInputfiles().size(); i++){
                logPrintWriter.append(atacInput.getDiffInputfiles().get(i).getCondition()+" REP ");
                logPrintWriter.append(atacInput.getDiffInputfiles().get(i).getReplicateNumber()+" ");
                logPrintWriter.append(atacInput.getDiffInputfiles().get(i).getType()+":\t");
                logPrintWriter.append(atacInput.getDiffInputfiles().get(i).getDiifInputFile().getName()+"\n");
                logPrintWriter.flush();
            }
            logPrintWriter.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
}
