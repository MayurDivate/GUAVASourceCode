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
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import umac.guava.AnalysisWorkflow;
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

    public boolean startDifferentialAnalysis(GdiffInput atacSeqDiffInput) {

        DifferentialResultFrame resultFrame = new DifferentialResultFrame();
        resultFrame.setVisible(true);
        resultFrame.addSummary(atacSeqDiffInput);
        DifferentialOutputFiles.writeSummary(atacSeqDiffInput);

        boolean doNext = true;
        DifferentialOutputFiles outputfiles = atacSeqDiffInput.getDifferentialOutputFiles();

        if (doNext) {
            System.out.println("---------- create common peaks ----------");
            int minRep = 1; // to get peaks present in min number of replicates;
            doNext = createCommonPeakList(atacSeqDiffInput.getDiffInputfiles(), outputfiles.getControlTreatmentCommonPeakBed(), minRep);
        }

        if (doNext) {
            System.out.println("---------- create DESeq2 code ----------");

            DESeq2 deseq2 = new DESeq2(outputfiles.getDeseqResult(), atacSeqDiffInput.getDiffInputfiles(),
                    outputfiles.getControlTreatmentCommonPeakBed(), outputfiles.getDeseqRcode(),
                    atacSeqDiffInput.getPvalue(), atacSeqDiffInput.getFoldChange(), outputfiles.getVolcanoPlot(), outputfiles.getPcaPlot());

            doNext = runDifferentialAnalysis(atacSeqDiffInput, deseq2);

            if (doNext) {
                System.out.println("---------- Display results ----------");
                resultFrame.displayResultTable(outputfiles.getDeseqResult());
                resultFrame.displayVplot(deseq2.getVolcanoPlotFile());
            } else {
                System.err.println("Error in the differential analysis step");
            }
        }

        // run chipPeakAnno
        if (doNext) {
            System.out.println("---------- create go and pathway enrichment code ----------");

            ChIPpeakAnno chipPeakAnno = outputfiles.getChipPeakAnno();
            
            doNext =  runFunctionalAnnotation(atacSeqDiffInput, chipPeakAnno);
            if (doNext) {
                System.out.println("---------- Display go and pathway plots ----------");
                resultFrame.displayGO(chipPeakAnno.getGoAnalysisOutputFile());
                resultFrame.displayPathways(chipPeakAnno.getPathwayAnalysisOutputFile());
            }
        }

        if (doNext) {
            doNext = deleteIntermediateFiles(outputfiles);
        }

        if (!doNext) {
            System.out.println("Error occured, analysis stopped");
        }
        return doNext;

    }

    public boolean startCommandlineDifferentialAnalysis(GdiffInput atacSeqDiffInput) {
        // print summary
        //System.out.println("Summary \n"+atacSeqDiffInput);
        DifferentialOutputFiles.writeSummary(atacSeqDiffInput);

        boolean doNext = true;
        DifferentialOutputFiles outputfiles = atacSeqDiffInput.getDifferentialOutputFiles();

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
            //doNext = createGOPathwayRcode(outputfiles, atacSeqDiffInput.getUpstream(), atacSeqDiffInput.getDownstream());
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

    boolean createDir(File dir) {
        System.out.println(dir.getAbsolutePath());
        if (!dir.exists()) {
            return dir.mkdir();
        } else {
            new AnalysisWorkflow().removeDir(dir);
            return dir.mkdir();
        }

    }

    public boolean createCommonPeakList(ArrayList<DifferentialInputFile> inputFiles, File outFile, int minRep) {

        System.out.println("umac.guava.diff.DifferentialAnalysisFlow.createCommonPeakList()" + "BUG");
        /**
         * *******************************************************
         */

        //get control common peaks  
        ArrayList<Peak> controlCommonPeaks = getCommonPeaksForConditionByOverlap(inputFiles, "control", minRep);
        System.out.println("=======>" + "Control peaks == " + controlCommonPeaks.size());

        //get treatment common peaks  
        ArrayList<Peak> treatmentCommonPeaks = getCommonPeaksForConditionByOverlap(inputFiles, "treatment", minRep);
        System.out.println("=======>" + "Treatment peaks == " + treatmentCommonPeaks.size());

        //get control-treatment common peaks  
        if (controlCommonPeaks != null && treatmentCommonPeaks != null) {
            //get control-treatment common peaks  
            ArrayList<Peak> commonPeaks = new Peak().mergePeakLists(controlCommonPeaks, treatmentCommonPeaks);
            //write control-treatment peaks to bed file 
            if (commonPeaks != null) {
                return commonPeaks.get(0).writePeaks(commonPeaks, outFile);
            }
        }
        /**
         * *******************************************************
         */
        return false;
    }

    public boolean runDifferentialAnalysis(GdiffInput gdiffInput, DESeq2 deseq2) {
        System.out.println("umac.guava.diff.DifferentialAnalysisWorkflow.runDifferentialAnalysis()");

        System.out.println("Step 1: create and Write R Code");
        // DESeq2 code
        String code = deseq2.getDESeq2Code(gdiffInput.getCpus());
        //map peak locations
        code = code + deseq2.getDESeq2ResultsCode();
        //DESeq2 volcano plot
        code = code + deseq2.getVplotCode(deseq2.getPvalue(), deseq2.getFoldchange(), deseq2.getVolcanoPlotFile());

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
        ArrayList<Peak> conditionPeaks = p.mergeOverlappingPeaks(mergedPeakList);
        return conditionPeaks;
    }

    public boolean runFunctionalAnnotation(GdiffInput gdiffInput, ChIPpeakAnno chipPeakaAnno) {

        System.out.println("umac.guava.diff.DifferentialAnalysisWorkflow.runFunctionalAnnotation()");

        System.out.println("create code");
        String chipPeakAnnoCode = chipPeakaAnno.getChIPpeakAnnoDiffRcode(gdiffInput.getUpstream(), gdiffInput.getDownstream());

        if (chipPeakaAnno.writeCode(chipPeakAnnoCode, chipPeakaAnno.getrCodeFile())) {
            System.out.println("run chippeaknno");
            String[] log = chipPeakaAnno.runCommand(chipPeakaAnno.getCommand());
            chipPeakaAnno.writeLog(log, "----------- ChIPpeakAnno -----------");
            return chipPeakaAnno.isSuccessful(log);
        }
        return false;
    }

    boolean runGOpathwayAnalysis(File goPathwayRcode) {
        GOandPathwayAnalysis goPath = new GOandPathwayAnalysis();
        goPath.runCommand(goPath.getCommand(goPathwayRcode));
        return true;
    }

    boolean deleteIntermediateFiles(DifferentialOutputFiles outFiles) {
        boolean flag = false;

        return true;
    }

    private boolean createIGVTracks(File inputBam, String genomebuild) {

        String bamPath = inputBam.getAbsolutePath();
        File bedgraphFile = new File(bamPath.replaceFirst("bam$", "bdg"));
        File bigwigFile = new File(bamPath.replaceFirst("bam$", "bw"));

        if (!bigwigFile.exists()) {
            IGVdataTrack iGVdataTrack = new IGVdataTrack(inputBam, bedgraphFile, bigwigFile, genomebuild);
            return iGVdataTrack.createDataTrackFromBamFile();
        }

        return true;
    }
}
