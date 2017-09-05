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
import umac.guava.AnalysisWorkflow;
import umac.guava.IGVdataTrack;

/**
 *
 * @author mayurdivate
 */
public class DifferentialAnalysisFlow {
    
    boolean errorFlag;
    boolean abortFlag;
 
    public boolean startDifferentialAnalysis(GdiffInput atacSeqDiffInput){
        
        DifferentialResultFrame resultFrame = new DifferentialResultFrame();
        resultFrame.setVisible(true);
        resultFrame.addSummary(atacSeqDiffInput);
        DifferentialOutputFiles.writeSummary(atacSeqDiffInput);
        
        
        boolean doNext =  true;
        DifferentialOutputFiles outputfiles =  atacSeqDiffInput.getDifferentialOutputFiles();

        if(doNext){
            System.out.println("---------- create common peaks ----------");
            int minRep = 1; 
            doNext = createCommonPeakList(atacSeqDiffInput.getDiffInputfiles(),outputfiles.getControlTreatmentCommonPeakBed(), minRep);
        }
        if(doNext){
            
            
            DESeq2 deseq2 = new DESeq2(outputfiles.getDeseqResult(), atacSeqDiffInput.getDiffInputfiles(), outputfiles.getControlTreatmentCommonPeakBed(), 
                    outputfiles.getDeseqRcode(), atacSeqDiffInput.getPvalue(), atacSeqDiffInput.getFoldChange(), outputfiles.getVolcanoPlot());
            
            System.out.println("---------- create DESeq2 code ----------");
            
            doNext = createDESeq2Code(deseq2);
            
            if(doNext){
                System.out.println("---------- run DESeq2 code ----------");
                doNext = runDESeq2(deseq2);
            }
            
            if(doNext){
                System.out.println("---------- Display results ----------");
                resultFrame.displayResultTable(outputfiles.getDeseqResult());
                resultFrame.displayVplot(deseq2.getVolacnoPlotFile());
            }
        }
        
        if(doNext){
            System.out.println("---------- create go and pathway enrichment code ----------");
            doNext = createGOPathwayRcode(outputfiles, atacSeqDiffInput.getUpstream(), atacSeqDiffInput.getDownstream());
        }
        if(doNext){
            System.out.println("---------- run go and pathway enrichment code ----------");
            doNext = runGOpathwayAnalysis(outputfiles.getGoPathwayRcode());
        }
        if(doNext){
            System.out.println("---------- Display go and pathway plots ----------");
            resultFrame.displayGO(outputfiles.getGoresults());
            resultFrame.displayPathways(outputfiles.getPathwayresults());
        }
        
        if(doNext){
            doNext = deleteIntermediateFiles(outputfiles);
        }
        
        if(!doNext){
            System.out.println("Error occured, analysis stopped");
        }
         return doNext;
                 
    }
    
    public boolean startCommandlineDifferentialAnalysis(GdiffInput atacSeqDiffInput){
        // print summary
        //System.out.println("Summary \n"+atacSeqDiffInput);
        DifferentialOutputFiles.writeSummary(atacSeqDiffInput);
        
        
        boolean doNext =  true;
        DifferentialOutputFiles outputfiles =  atacSeqDiffInput.getDifferentialOutputFiles();

        if(doNext){
            System.out.println("---------- create common peaks ----------");
            int minRep = 1; 
            doNext = createCommonPeakList(atacSeqDiffInput.getDiffInputfiles(),outputfiles.getControlTreatmentCommonPeakBed(), minRep);
        }
        if(doNext){
            
            DESeq2 deseq2 = new DESeq2(outputfiles.getDeseqResult(), atacSeqDiffInput.getDiffInputfiles(), outputfiles.getControlTreatmentCommonPeakBed(), 
                    outputfiles.getDeseqRcode(), atacSeqDiffInput.getPvalue(), atacSeqDiffInput.getFoldChange(), outputfiles.getVolcanoPlot());
            
            System.out.println("---------- create DESeq2 code ----------");
            
            doNext = createDESeq2Code(deseq2);
            
            if(doNext){
                System.out.println("---------- run DESeq2 code ----------");
                doNext = runDESeq2(deseq2);
            }
            
        }
        
        if(doNext){
            System.out.println("---------- create go and pathway enrichment code ----------");
            doNext = createGOPathwayRcode(outputfiles, atacSeqDiffInput.getUpstream(), atacSeqDiffInput.getDownstream());
        }
        if(doNext){
            System.out.println("---------- run go and pathway enrichment code ----------");
            doNext = runGOpathwayAnalysis(outputfiles.getGoPathwayRcode());
        }
        
        if(doNext){
            doNext = deleteIntermediateFiles(outputfiles);
        }

        if(!doNext){
            System.out.println("Error occured, analysis stopped");
        }         
         return doNext;
                 
    }
    
    
    boolean createDir(File dir){
        System.out.println(dir.getAbsolutePath());
        if(!dir.exists()){
            return dir.mkdir();
         }
        else{
            new AnalysisWorkflow().removeDir(dir);
            return dir.mkdir();
        }
        
    }
    
    public boolean createCommonPeakList(ArrayList<DifferentialInputFile> inputFiles, File outFile , int minRep){
        /**********************************************************/
        //get control common peaks  
        ArrayList<Peak> controlCommonPeaks = getCommonPeaksForConditionByOverlap(inputFiles , "control", minRep);
        //get treatment common peaks  
        ArrayList<Peak> treatmentCommonPeaks = getCommonPeaksForConditionByOverlap(inputFiles, "treatment", minRep);
        //get control-treatment common peaks  
    
        if(controlCommonPeaks != null && treatmentCommonPeaks != null){
            ArrayList<Peak> commonPeaks = new Peak().mergePeaks(controlCommonPeaks, treatmentCommonPeaks);
            //write control-treatment peaks to bed file 
            if(commonPeaks != null){
                return commonPeaks.get(0).writePeaks(commonPeaks, outFile);
            }
        }
        /**********************************************************/
        return false;
    }
    
    public boolean createDESeq2Code(DESeq2 deseq2){
        System.out.println("umac.guava.diff.DifferentialAnalysisFlow.createDESeq2Code()");
        // DESeq2 code
        String code = deseq2.getDESeq2Code(deseq2.getDfInput(), deseq2.getPeakBedFile(),deseq2.getDESeq2OutputCSV());
        //map peak locations
        code = code + deseq2.getDESeq2ResultsCode();
        

        //DESeq2 volcano plot
        code = code + deseq2.getVplotCode(deseq2.getPvalue(), deseq2.getFoldchange(),deseq2.getVolacnoPlotFile());
        //write DESeq2 complete code
        deseq2.writeCode(code, deseq2.getDeseq2Code());
        
        return true;
    }
    
    public boolean runDESeq2(DESeq2 deseq2){
        System.out.println("umac.guava.diff.DifferentialAnalysisFlow.runDESeq2()");
        String[] deseqLog = deseq2.runCommand(deseq2.getCommand(deseq2.getDeseq2Code()));
        deseq2.writeLog(deseqLog, "--------- DESeq2 ---------");
        return true;
    }
    
    public static ArrayList<Peak> getCommonPeaksForConditionByOverlap(ArrayList<DifferentialInputFile> dfInput, String condition, int minReplicates){
        
        // create master merge list
        ArrayList<Peak> mergedPeakList =  new ArrayList<>();
        DifferentialInputFile df = new DifferentialInputFile();  
        ArrayList<DifferentialInputFile> peakFiles = df.getDifferentialPeakInput(dfInput, condition);
        
        for(int i =0; i < peakFiles.size(); i++){
            ArrayList<Peak> peakList =  Peak.getPeakList(peakFiles.get(i).getDiifInputFile());
            mergedPeakList.addAll(peakList);
        }

        Peak p = new Peak();
        ArrayList<Peak> conditionPeaks = p.mergeOverlappingPeaks(mergedPeakList);
        
        return conditionPeaks;
    }
    public static ArrayList<Peak> getCommonPeaksForCondition(ArrayList<DifferentialInputFile> dfInput, String condition, int minReplicates){
        System.out.println("umac.guava.diff.DifferentialAnalysisFlow.getCommonPeaksForCondition()");
        
        ArrayList<Peak> mergedPeaks = new ArrayList<>();
        DifferentialInputFile df = new DifferentialInputFile();  
        
        //Input control peak files
        ArrayList<DifferentialInputFile> peakFiles = df.getDifferentialPeakInput(dfInput, condition);
        
        for(int i =0; i < peakFiles.size(); i++){
            ArrayList<Peak> peakList =  Peak.getPeakList(peakFiles.get(i).getDiifInputFile());
            mergedPeaks = new Peak().mergePeaks(mergedPeaks, peakList);
        }
        
        return mergedPeaks;
    }
    
    boolean createGOPathwayRcode(DifferentialOutputFiles outFiles, int upstream, int downstream){
        
        GOandPathwayAnalysis goPath = new GOandPathwayAnalysis(outFiles.getDeseqResult(),outFiles.getGoresults(),outFiles.getPathwayresults());
        String code = goPath.getGOPathwayAnalysisCode(upstream, downstream);
        goPath.writeCode(code, outFiles.getGoPathwayRcode());
        
        return true;
    }
    
    boolean runGOpathwayAnalysis(File goPathwayRcode){
        GOandPathwayAnalysis goPath = new GOandPathwayAnalysis();
        goPath.runCommand(goPath.getCommand(goPathwayRcode));
        return true;
    }
 

    
    boolean deleteIntermediateFiles(DifferentialOutputFiles outFiles){
        boolean flag = false; 
        // delete DeseqRcode
        if(outFiles.getDeseqRcode().exists()){
            flag = outFiles.getDeseqRcode().delete();
        }
        if(outFiles.getGoPathwayRcode().exists()){
            flag = outFiles.getGoPathwayRcode().delete();
        }
        return flag;
    }
    
    
    private boolean createIGVTracks(File inputBam, String genomebuild){
        
        String bamPath = inputBam.getAbsolutePath();
        File bedgraphFile = new File(bamPath.replaceFirst("bam$", "bdg"));
        File bigwigFile = new File(bamPath.replaceFirst("bam$", "bw"));
        
        if(!bigwigFile.exists()){
            IGVdataTrack iGVdataTrack = new IGVdataTrack(inputBam, bedgraphFile,bigwigFile, genomebuild );
            return iGVdataTrack.createDataTrackFromBamFile();
        }
        
        return true;
    }    
}
