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
import umac.guava.ChIPpeakAnno;
import umac.guava.Genome;
import umac.guava.IGVdataTrack;

/**
 *
 * @author mayurdivate
 */
public class DifferentialAnalysisWorkflow {

    boolean errorFlag;
    boolean abortFlag;

    public boolean startDifferentialAnalysis(GdiffInput gdiff_Input) {

        DifferentialResultFrame resultFrame = new DifferentialResultFrame();
        resultFrame.setVisible(true);
        resultFrame.addSummary(gdiff_Input);

        boolean doNext = true;
        // get output files 
        DifferentialOutputFiles gdiff_outputfiles = gdiff_Input.getDifferentialOutputFiles();
        doNext = createDir(gdiff_outputfiles.getOutputFolder());
        
        DIfferentialAnalysisExcelPrinter excelPrinter = new DIfferentialAnalysisExcelPrinter(gdiff_outputfiles.getOutExcel(), "Summary");
        int sheetNumber = 0;
        excelPrinter.printSummary(gdiff_Input);
        sheetNumber++;
        
        // create log file
        if(doNext){
            try {
                DifferentialOutputFiles.getLogFile().createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DifferentialAnalysisWorkflow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // write input summary
        doNext = gdiff_outputfiles.writeSummary(gdiff_Input);

        if (doNext) {
            System.out.println("---------- Create read count matrix ----------");
            int minRep = 1; // to get peaks present in min number of replicates;
            doNext = createCommonPeakList(gdiff_Input.getDiffInputfiles(), gdiff_outputfiles.getControlTreatmentCommonPeakBed(), minRep);
        }

        if (doNext) {
            System.out.println("---------- Run DESeq2 code ----------");

            DESeq2 deseq2 = new DESeq2(gdiff_outputfiles.getDeseqResult(), gdiff_Input.getDiffInputfiles(),
                    gdiff_outputfiles.getControlTreatmentCommonPeakBed(), gdiff_outputfiles.getDeseqRcode(),
                    gdiff_Input.getPvalue(), gdiff_Input.getFoldChange(), gdiff_outputfiles.getVolcanoPlot(), gdiff_outputfiles.getPcaPlot());

            doNext = runDifferentialAnalysis(gdiff_Input, deseq2);

            if (doNext) {
                System.out.println("---------- Display volcano plot ----------");
                resultFrame.displayVplot(deseq2.getVolcanoPlotFile());
                resultFrame.displayPCAplot(deseq2.getPcaPlotFile());
                
                excelPrinter.setSheetName("VolcanoPlot");
                excelPrinter.printImage(deseq2.getVolcanoPlotFile(), sheetNumber++, 13, 27);
                excelPrinter.setSheetName("PCA_plot");
                excelPrinter.printImage(deseq2.getPcaPlotFile(), sheetNumber++, 12, 27);
                
            } else {
                System.err.println("Error in the differential analysis step");
            }
        }
        
        if (doNext) {
            System.out.println("---------- functional annotation ----------");

            ChIPpeakAnno chipPeakAnno = gdiff_outputfiles.getChipPeakAnno();
            doNext =  runFunctionalAnnotation(gdiff_Input, chipPeakAnno);

            if (doNext) {
                System.out.println("---------- Display go and pathway results and plots ----------");
                resultFrame.displayResultTable(chipPeakAnno.getPeakAnnoated());
                resultFrame.displayBarChart(chipPeakAnno.getBarChart());
                resultFrame.displayGO(chipPeakAnno.getGoAnalysisOutputFile());
                resultFrame.displayPathways(chipPeakAnno.getPathwayAnalysisOutputFile());
                
                
                excelPrinter.setSheetName("AnnotatedPeaks");
                excelPrinter.printPeakTable(chipPeakAnno.getPeakAnnoated(),sheetNumber++);
                
                excelPrinter.setSheetName("BarChart");
                excelPrinter.printImage(chipPeakAnno.getBarChart(), sheetNumber++, 14, 27,1,3);
                
                excelPrinter.setSheetName("GeneOntology");
                excelPrinter.printGeneOntologyTable(chipPeakAnno.getGoAnalysisOutputFile(), sheetNumber++);
                
                excelPrinter.setSheetName("Pathways");
                excelPrinter.printPathwayTable(chipPeakAnno.getPathwayAnalysisOutputFile(), sheetNumber++);
            }
        }

        if (doNext) {
            doNext = deleteIntermediateFiles(gdiff_outputfiles);
        }

        if (!doNext) {
            System.out.println("Error occured, analysis stopped");
            return false;
        }
        
        System.out.println("--------- F I N I S H E D -----------");
        return doNext;

    }

    public boolean startCommandlineDifferentialAnalysis(GdiffInput atacSeqDiffInput) {
        boolean doNext = true;
        // get gdiff_outputfiles
        DifferentialOutputFiles outputfiles = atacSeqDiffInput.getDifferentialOutputFiles();
        
        // create output dir
        doNext = createDir(outputfiles.getOutputFolder());
        // create log file
        if(doNext){
            try {
                DifferentialOutputFiles.getLogFile().createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(DifferentialAnalysisWorkflow.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // write input summary
        doNext = outputfiles.writeSummary(atacSeqDiffInput);
        
        if (doNext) {
            System.out.println("---------- create common peaks ----------");
            int minRep = 1;
            doNext = createCommonPeakList(atacSeqDiffInput.getDiffInputfiles(), outputfiles.getControlTreatmentCommonPeakBed(), minRep);
        }
        if (doNext) {
            System.out.println("---------- Run Differential Analysis ----------");

            DESeq2 deseq2 = new DESeq2(outputfiles.getDeseqResult(), atacSeqDiffInput.getDiffInputfiles(),
                    outputfiles.getControlTreatmentCommonPeakBed(), outputfiles.getDeseqRcode(),
                    atacSeqDiffInput.getPvalue(), atacSeqDiffInput.getFoldChange(),
                    outputfiles.getVolcanoPlot(), outputfiles.getPcaPlot());

            doNext = runDifferentialAnalysis(atacSeqDiffInput, deseq2);
        }

        if (doNext) {
            System.out.println("---------- Run Functional analysis ----------");
            //doNext = createGOPathwayRcode(gdiff_outputfiles, atacSeqDiffInput.getUpstream(), atacSeqDiffInput.getDownstream());
            ChIPpeakAnno chipPeakAnno =  ChIPpeakAnno.getChIPpeakAnnoObject(outputfiles.getDeseqResult(),
                    atacSeqDiffInput.getProjectName(), "BED", atacSeqDiffInput.getGenome());
            doNext = runFunctionalAnnotation(atacSeqDiffInput, chipPeakAnno);
        }

        if (doNext) {
            doNext = deleteIntermediateFiles(outputfiles);
        }

        if (!doNext) {
            System.out.println("Error occured, analysis stopped");
        }
        return doNext;

    }

    public boolean createCommonPeakList(ArrayList<DifferentialInputFile> inputFiles, File outFile, int minRep) {

       
        //get control common peaks  
        ArrayList<Peak> controlCommonPeaks = getCommonPeaksForConditionByOverlap(inputFiles, "control", minRep);
        //System.out.println("=======>" + "Control peaks == " + controlCommonPeaks.size());

        //get treatment common peaks  
        ArrayList<Peak> treatmentCommonPeaks = getCommonPeaksForConditionByOverlap(inputFiles, "treatment", minRep);
        //System.out.println("=======>" + "Treatment peaks == " + treatmentCommonPeaks.size());

        //get control-treatment common peaks  
        if (controlCommonPeaks != null && treatmentCommonPeaks != null) {
            //get control-treatment common peaks  
            ArrayList<Peak> commonPeaks = new Peak().mergePeakLists(controlCommonPeaks, treatmentCommonPeaks);
            //write control-treatment peaks to bed file 
            if (commonPeaks != null) {
                return commonPeaks.get(0).writePeaks(commonPeaks, outFile);
            }
        }
        
        return false;
    }

    public boolean runDifferentialAnalysis(GdiffInput gdiffInput, DESeq2 deseq2) {
        // DESeq2 code
        String code = deseq2.getDESeq2Code(gdiffInput.getCpus());

        //write DESeq2 complete code
        if (deseq2.writeCode(code, deseq2.getDeseq2CodeFile())) {
            String[] deseqLog = deseq2.runCommand(deseq2.getCommand(deseq2.getDeseq2CodeFile()));
            deseq2.writeLog(deseqLog, "--------- DESeq2 ---------");
            return deseq2.isSuccessful(deseqLog);
        }
        return false;
    }

    public static ArrayList<Peak> getCommonPeaksForConditionByOverlap(ArrayList<DifferentialInputFile> dfInput, String condition, int minReplicates) {

        // create master merge list
        ArrayList<Peak> mergedPeakList = new ArrayList<>();
        DifferentialInputFile df = new DifferentialInputFile();
        ArrayList<DifferentialInputFile> peakFiles = df.getDifferentialPeakInput(dfInput, condition);

        for (int i = 0; i < peakFiles.size(); i++) {
            ArrayList<Peak> peakList = Peak.getPeakList(peakFiles.get(i).getDiifInputFile());
            System.out.println(peakFiles.get(i).getDiifInputFile().getName() + " == " + peakList.size());
            mergedPeakList.addAll(peakList);
        }
        
        Peak p = new Peak();
        ArrayList<Peak> conditionPeaks = p.mergeOnlyOverlappingPeaks(mergedPeakList);
        return conditionPeaks;
    }

    public boolean runFunctionalAnnotation(GdiffInput gdiffInput, ChIPpeakAnno chipPeakaAnno) {

        String chipPeakAnnoCode = chipPeakaAnno.getChIPpeakAnnoDiffRcode(gdiffInput.getUpstream(), gdiffInput.getDownstream());
        
        if (chipPeakaAnno.writeCode(chipPeakAnnoCode, chipPeakaAnno.getrCodeFile())) {
            String[] log = chipPeakaAnno.runCommand(chipPeakaAnno.getCommand());
            return chipPeakaAnno.isSuccessful(log);
        }
        return false;
    }

    boolean deleteIntermediateFiles(DifferentialOutputFiles outFiles) {
        
        if(!deleteFile(outFiles.getDeseqRcode())){
            return false;
        }
        if(!deleteFile(outFiles.getChipPeakAnno().getrCodeFile())){
            return false;
        }
        
        return true;
    }

    private boolean createIGVTracks(File inputBam, Genome genome) {

        String bamPath = inputBam.getAbsolutePath();
        File bedgraphFile = new File(bamPath.replaceFirst("bam$", "bdg"));
        File bigwigFile = new File(bamPath.replaceFirst("bam$", "bw"));

        if (!bigwigFile.exists()) {
            IGVdataTrack iGVdataTrack = new IGVdataTrack(inputBam, bedgraphFile, bigwigFile, genome);
            return iGVdataTrack.createDataTrackFromBamFile();
        }

        return true;
    }
    
    public boolean createDir(File dir) {
        System.out.println(dir.getAbsolutePath());
        if (!dir.exists()) {
            return dir.mkdir();
        } else {
            this.removeDir(dir);
            return dir.mkdir();
        }

    }
    
    public boolean removeDir(File dir) {
        if (dir.isDirectory()) {
            File[] childrens = dir.listFiles();
            for (File child : childrens) {
                boolean success = this.removeDir(child);
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
    
    public boolean deleteFile(File file){
        if(file.exists() && file.isFile()){
            return file.delete();
        }
        else if(!file.exists()){
            return true;
        }
        return false;
    }

}
