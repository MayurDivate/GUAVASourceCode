/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author mayurdivate
 */
public class AnalysisWorkflow {
    public static boolean go = true;
    public static boolean bowtie = true;
    public static boolean commandLine = false;
    public static FilteredAlignment alignmentFilteringResults = new FilteredAlignment(-1, -1, -1, -1, -1);
    public static HashMap<String, Integer> chrSTAT;
    public static AlignmentResult alignmentResults ;
    public static AnalysisResultWriter analysisResultWriter = new AnalysisResultWriter();
    
    public static boolean validateToolPaths() {
        
        System.out.println("Operating System of machine :"+System.getProperty("os.name"));
        boolean isWorking = new Bowtie().isWorking();
        if(isWorking){
            isWorking =  FastQC.fastQCPath();
        }
        if(isWorking){
            isWorking = new Samtools().isWorking();
        }
        if(isWorking){
            isWorking = new MACS2().isWorking();
        }
        if(isWorking){
            isWorking = new R().isWorking();
        }
        if(isWorking){
            isWorking = Picard.picardPath();
        }
        if(isWorking){
            isWorking = Samtools.checkBlackListFile();
        }
        if(isWorking){
            isWorking =  IGV.igvPath();
        }
        if(isWorking){
            isWorking = BedTools.bedtoolsPath();
        }
        if(isWorking){
            isWorking = new UCSCtools().isWorking();
        }

        return isWorking;
    }

    void startGUIGuavaAnalysis(GuavaInput guavaInput) {
        
        Date start = new Date();
                
        RunStatusJframe runStatusJframe = null;
        if(!commandLine){
            runStatusJframe = new RunStatusJframe ();
            runStatusJframe.setVisible(false);
            runStatusJframe.displayInputSummary(guavaInput,bowtie); 
        }
        // ------------ start of workflow ------------------        
        AnalysisWorkflow aw = new AnalysisWorkflow();
        GuavaOutputFiles outFiles = new GuavaOutputFiles().getOutputFiles(guavaInput); 
        Samtools workflowSamtools = new Samtools();
           
        if(go){
          go = aw.createDir(outFiles.getRootDir());
        }
        
        try {
           go = GuavaOutputFiles.logFile.createNewFile();
        } catch (IOException ex) {
            System.out.println(GuavaOutputFiles.logFile);
            Logger.getLogger(AnalysisWorkflow.class.getName()).log(Level.SEVERE, null, ex);
        }
                if(guavaInput.isTrim()){
                    System.out.print("Adapter trimming...");
                    go = aw.runCutadapt(guavaInput,outFiles);
                    Cutadapt cutadapt = guavaInput.getCutadapt();
                    guavaInput.setR1Fastq(cutadapt.getTrimmed_R1());
                    guavaInput.setR2Fastq(cutadapt.getTrimmed_R2());
                }
                if(go){
                   System.out.print("fastq QC...");
                    go = aw.runFastQC(guavaInput, outFiles);
                }
                if(go && bowtie){
                    
                   System.out.print("Alignment...");
                   //"---------- bowtie ----------"
                   go = aw.runBowtie(guavaInput, outFiles);
                   
                   if(go){
                        analysisResultWriter.setAlignmentResult(alignmentResults);
                        alignmentFilteringResults.setTotalReads(alignmentResults.getTotalReads());
                        alignmentFilteringResults.setTotalAligned(alignmentResults.getReadsAligned());

                        if(!commandLine){
                            runStatusJframe.displayAlignmentResults(alignmentResults,bowtie);
                        }
                            ExcelPrinter.createExcelWoorkBook();
                            ExcelPrinter.addAlignmentResults(guavaInput, alignmentResults,true);
                   }
                   
                }
                else{
                   System.out.print("Alignment...");
                   //"---------- bowtie2 ----------"
                        Bowtie2 bowtie2 =  Bowtie2.getBowtie2(guavaInput, outFiles);
                        outFiles.setAlignedSam(bowtie2.getMapQBam());
                        go = aw.runBowtie2(bowtie2);
                        
                    if(go){
                        go = aw.getCsrtBAM(guavaInput, outFiles);
                        outFiles.getAlignedSam().delete();
                    }
                    if(go){
                        Samtools samtools = new Samtools();
                        int mapQ_alignedReads =  samtools.getReadCount(outFiles.getAlignedCsrtBam());
                        int suppressedReads = alignmentResults.getReadsAligned() - mapQ_alignedReads;
                        alignmentResults.setReadsAligned(mapQ_alignedReads);
                        alignmentResults.setReadsSuppressed(suppressedReads);
                        
                        analysisResultWriter.setAlignmentResult(alignmentResults);
                        alignmentFilteringResults.setTotalReads(alignmentResults.getTotalReads());
                        alignmentFilteringResults.setTotalAligned(alignmentResults.getReadsAligned());
    
                        if(!commandLine){
                            runStatusJframe.displayAlignmentResults(alignmentResults,bowtie);
                        }
                        
                        ExcelPrinter.createExcelWoorkBook();
                        ExcelPrinter.addAlignmentResults(guavaInput, alignmentResults,false);
                    }
                }
                
                if(go){
                   System.out.println("---- Alignment filtering ------");
                   go = aw.runAlignmentFiltering(guavaInput, outFiles, runStatusJframe);
                }
                if(go){
                   System.out.print("Alignment shifting...");

                   go =  aw.getAlignmentShiftedBAM(guavaInput,outFiles);
                   alignmentFilteringResults.setUsefulReads(workflowSamtools.getReadCount(outFiles.getAtacseqBam()));
                   analysisResultWriter.setAlignmentFilteringResult(alignmentFilteringResults);

                        if(!commandLine){
                           runStatusJframe.setFilteredResults(alignmentFilteringResults);
                        }
                               ExcelPrinter.printAlignmentFilteringResult(alignmentFilteringResults);
                        
                }
                if(go){
                    
                    System.out.print("Generating Data tracks...");
                    go = aw.createIGVTracks(guavaInput, outFiles);
                           
                }
                if(go){
                   System.out.print("Fragmentsize size distribution...");
                   go = aw.getInsertSizeDistribution(guavaInput, outFiles);
                   File tempDir = new File(GuavaOutputFiles.rootDir+""+System.getProperty("file.separator")+"tmp");
                   aw.removeDir(tempDir);
                   
                   if(outFiles.getInsertSizePDF().exists()){
                       outFiles.getInsertSizePDF().delete();
                   }
                }
                if(go){
                   System.out.print("Peak calling...");
                   go = aw.callPeaks(guavaInput, outFiles, runStatusJframe);
                   
                }
                if(go){
                    System.out.print("Fragmentsize plot...");
                   go = aw.createFragmentSizeDistributionGraph(guavaInput, outFiles);
                   workflowSamtools.deleteFile(outFiles.getrCode());
                   ExcelPrinter.printImage(R.fragmentSizeDistributionPlot, "insertsize",15.0,25.0);
                }
                if(!commandLine && go){
                    System.out.println("Display graph...Done!");
                    runStatusJframe.displayFragmentSizeGraph();
                    
                }
                if(go){
                    System.out.println("----------- Peak annotation -------------");
                    go =  aw.runChIPseekeer(guavaInput, outFiles);
                    ChIPseeker chipSeeker = ChIPseeker.getChIPSeekerObject();
                    
                    if(chipSeeker.getPieChart().exists()){
                        ExcelPrinter.printImage(chipSeeker.getPieChart(), "PieChart", 15.0, 25.0);
                    }
                    if(chipSeeker.getBarChart().exists()){                        
                        ExcelPrinter.printImage(chipSeeker.getBarChart(), "BarChart", 15.0, 25.0);
                    }
                    if(chipSeeker.getPathwayPlot().exists()){ 
                        ExcelPrinter.printImage(chipSeeker.getPathwayPlot(), "PathwayChart", 15.0, 25.0);
                    }
                    if(chipSeeker.getGeneAnnotationPeaks().exists()){
                        ExcelPrinter.printPeakTable(chipSeeker.getGeneAnnotationPeaks());
                    }
              
                }
                if(!commandLine && go){
                    System.out.println("----------- Display ChIPseeker plots and peak annotation-------------");
                    ChIPseeker chipSeeker = ChIPseeker.getChIPSeekerObject(); 
                    if(chipSeeker.getGeneAnnotationPeaks().exists()){
                        System.out.println("\n");
                        runStatusJframe.addPeakTableRow(chipSeeker.getGeneAnnotationPeaks());
                        runStatusJframe.displayChIPseekerPlots(chipSeeker);
                        runStatusJframe.setVisible(true);
                        System.out.println("----------- F I N I S H E D -------------");
                        Date finish = new Date();
                        String log[] = {"GUAVA ATAC-seq data analysis", "Started at:"+start+"\nFinished at:"+finish};
                        new Samtools().writeLog(log, "");
                    }
                }
                
                

                if(!go){
                    System.err.println("Error code 101: Unexpected exit from analysis workflow");
                    System.exit(-1);
                }
   
        if(!commandLine){
            runStatusJframe.setVisible(go);
        }
        if(commandLine){
            printCommandlineResults(guavaInput, alignmentResults, alignmentFilteringResults, chrSTAT, guavaInput.getChromosome());
        }
        
        
    }
    
    public boolean startCommandlineGuavaAnalysis(GuavaInput guavaInput) {

        boolean success = true; 
        Date start = new Date();
                
        AnalysisWorkflow aw = new AnalysisWorkflow();                               // to run other methods in this class
        GuavaOutputFiles outFiles = new GuavaOutputFiles().getOutputFiles(guavaInput);        // outputfiles 
        Samtools workflowSamtools = new Samtools();                                 // to run samtools
           
        success = aw.createDir(outFiles.getRootDir());                              //create root dir and get started 
        if(success){
            System.out.println("1");
            System.out.println(guavaInput.isTrim());
            success = aw.createFile(outFiles.logFile);
        }

//------------------- ADAPTER TRIMMING ------------------        

        if(guavaInput.isTrim()){
            System.out.println("Adapter trimming...");
            success = aw.runCutadapt(guavaInput,outFiles);                          // Trim adapter 
            guavaInput.setR1Fastq(guavaInput.getCutadapt().getTrimmed_R1());        // change R1 and R2 fastq 
            guavaInput.setR2Fastq(guavaInput.getCutadapt().getTrimmed_R2());        // to trimmed fastq
        }

//------------------- FASTQC ------------------        
        
        if(success){
            System.out.println("FastQC quality check...");
            success = aw.runFastQC(guavaInput, outFiles);
        }

//------------------- ALignment ------------------        
        if(success && guavaInput.getAligner().equals("bowtie")){

            System.out.println("Alignment...");
            //"---------- bowtie ----------"
            success = aw.runBowtie(guavaInput, outFiles);

            if(success){
                analysisResultWriter.setAlignmentResult(alignmentResults);
                alignmentFilteringResults.setTotalReads(alignmentResults.getTotalReads());
                alignmentFilteringResults.setTotalAligned(alignmentResults.getReadsAligned());
                ExcelPrinter.createExcelWoorkBook();
                ExcelPrinter.addAlignmentResults(guavaInput, alignmentResults,true);
            }

        }
        else if(success && guavaInput.getAligner().equals("bowtie2")){
            System.out.println("Alignment...");
            //"---------- bowtie2 ----------"
            Bowtie2 bowtie2 =  Bowtie2.getBowtie2(guavaInput, outFiles);
            outFiles.setAlignedSam(bowtie2.getMapQBam());
            success = aw.runBowtie2(bowtie2);

           if(success){
               success = aw.getCsrtBAM(guavaInput, outFiles);
               outFiles.getAlignedSam().delete();
           }
           if(success){
               Samtools samtools = new Samtools();
               int mapQ_alignedReads =  samtools.getReadCount(outFiles.getAlignedCsrtBam());
               int suppressedReads = alignmentResults.getReadsAligned() - mapQ_alignedReads;
               alignmentResults.setReadsAligned(mapQ_alignedReads);
               alignmentResults.setReadsSuppressed(suppressedReads);

               analysisResultWriter.setAlignmentResult(alignmentResults);
               alignmentFilteringResults.setTotalReads(alignmentResults.getTotalReads());
               alignmentFilteringResults.setTotalAligned(alignmentResults.getReadsAligned());

               ExcelPrinter.createExcelWoorkBook();
               ExcelPrinter.addAlignmentResults(guavaInput, alignmentResults,false);
           }
       }

//------------------- Alignment filtering ------------------        

    if(success){
       System.out.println("---- Alignment filtering ------");
       success = aw.runAlignmentFiltering(guavaInput, outFiles);
    }

//------------------- Alignment shifting ------------------        

    if(success){
       System.out.print("Alignment shifting...");
       success =  aw.getAlignmentShiftedBAM(guavaInput,outFiles);
       alignmentFilteringResults.setUsefulReads(workflowSamtools.getReadCount(outFiles.getAtacseqBam()));
       analysisResultWriter.setAlignmentFilteringResult(alignmentFilteringResults);
       ExcelPrinter.printAlignmentFilteringResult(alignmentFilteringResults);

    }

//------------------- IGV tracks ------------------        

    if(success){
        System.out.print("Generating Data tracks...");
        success = aw.createIGVTracks(guavaInput, outFiles);
    }

//------------------- fragment size distribution ------------------        
    
    if(success){
       System.out.print("Fragmentsize size distribution...");
       success = aw.getInsertSizeDistribution(guavaInput, outFiles);
       File tempDir = new File(GuavaOutputFiles.rootDir+""+System.getProperty("file.separator")+"tmp");
       aw.removeDir(tempDir);

       if(outFiles.getInsertSizePDF().exists()){
           outFiles.getInsertSizePDF().delete();
       }
    }

    if(success){
        System.out.print("Fragmentsize plot...");
        success = aw.createFragmentSizeDistributionGraph(guavaInput, outFiles);
        workflowSamtools.deleteFile(outFiles.getrCode());
        ExcelPrinter.printImage(R.fragmentSizeDistributionPlot, "insertsize",15.0,25.0);
    }

//------------------- peak calling ------------------        
    
    if(success){
       System.out.print("Peak calling...");
       success = aw.callPeaks(guavaInput, outFiles);
    }

//------------------- Peak annotation ------------------        

    if(success){
        System.out.println("----------- Peak annotation -------------");
        success =  aw.runChIPseekeer(guavaInput, outFiles);
        ChIPseeker chipSeeker = ChIPseeker.getChIPSeekerObject();

        if(chipSeeker.getPieChart().exists()){
            ExcelPrinter.printImage(chipSeeker.getPieChart(), "PieChart", 15.0, 25.0);
        }
        if(chipSeeker.getBarChart().exists()){                        
            ExcelPrinter.printImage(chipSeeker.getBarChart(), "BarChart", 15.0, 25.0);
        }
        if(chipSeeker.getPathwayPlot().exists()){ 
            ExcelPrinter.printImage(chipSeeker.getPathwayPlot(), "PathwayChart", 15.0, 25.0);
        }
        if(chipSeeker.getGeneAnnotationPeaks().exists()){
            ExcelPrinter.printPeakTable(chipSeeker.getGeneAnnotationPeaks());
        }
    }

//------------------- print results ------------------        
    if(success){
        printCommandlineResults(guavaInput, alignmentResults, alignmentFilteringResults, chrSTAT, guavaInput.getChromosome());
        System.out.println("\n");
        System.out.println("----------- F I N I S H E D -------------");
        Date finish = new Date();
        String log[] = {"GUAVA ATAC-seq data analysis", "Started at:"+start+"\nFinished at:"+finish};
        new Samtools().writeLog(log, "");
        return true;
    }
    else{
        System.err.println("Error code 101: Unexpected exit from analysis workflow");
        System.exit(-1);
        
    }

    return false;

        
    }
    
    public boolean runBowtie(GuavaInput atacseqInput, GuavaOutputFiles outFiles){
        
        System.out.print("Please wait while bowtie alignment is finished...");
        Bowtie bw = new Bowtie();
        String[] bowtielog = bw.runCommand(bw.getCommand(atacseqInput,atacseqInput.getR1Fastq(),outFiles.getAlignedSam()));
        bw.writeLog(bowtielog,"Bowtie log");
        AlignmentResult.getAlignmentStatistics(bowtielog[1]);

                if(alignmentResults != null){
                    System.out.println("Done!");
                    return true;
                }

        return false;
    }

    public boolean runBowtie2(Bowtie2 bowtie2){
        System.out.print("Please wait while bowtie2 alignment is finished..");
        
        // running bowtie for alignment
        String[] bowtie2log = bowtie2.runCommand(bowtie2.getCommand());
        bowtie2.writeLog(bowtie2log,"Bowtie2 log");
        
        AlignmentResult.getBowtie2AlignmentStatistics(bowtie2log[1]);
        
            if(alignmentResults != null){
                // filter low mapping quality reads 
                boolean filterFlag = new AnalysisWorkflow().filterBambyMapQ(bowtie2.getOutputSam(), bowtie2.getMapQBam(),
                                                                            bowtie2.getMapQ(), bowtie2.getCpu());
                if(filterFlag){
                    System.out.println("Done!");
                    // delete bowtie2 sam file
                    return bowtie2.getOutputSam().delete();
                }
                
            }

        return false;
    }

    public boolean filterBambyMapQ(File inputFile, File ouputFile, int mapQ,int cpu){
        Samtools samtools = new Samtools();
        String[]  command =  samtools.getCommand(inputFile, ouputFile, mapQ, cpu);
        String[] log = samtools.runCommand(command);
        samtools.writeLog(log, "Quality filter log");
            if(log[0] == null || log[0].equals("")){
                return true;
            }
        return false;
    }
    
    public boolean runAlignmentFiltering(GuavaInput atacseqInput, GuavaOutputFiles outFiles, RunStatusJframe runStatusJframe){
        
        Samtools samtools =  new Samtools();
        AnalysisWorkflow aw =  new AnalysisWorkflow();
        
        //Sam to sorted BAM
        if(go){
            if(!outFiles.getAlignedCsrtBam().exists()){
                go = aw.getCsrtBAM(atacseqInput, outFiles);
                samtools.deleteFile(outFiles.getAlignedSam());    
            }
        }
        //Pre filtering stat
                    chrSTAT = samtools.getChrStat(outFiles.getAlignedCsrtBam());
                    ExcelPrinter.printChrStat(chrSTAT, atacseqInput.getChromosome());

        if(!commandLine){
            runStatusJframe.setChrStat(chrSTAT, atacseqInput.getChromosome());
        }
        //duplicate filtering
        if(go){
            System.out.print("Remove duplicates...");
            go = aw.getDuplicateFilteredBAM(atacseqInput, outFiles);
            alignmentFilteringResults.setDuplicateFilteredReads(samtools.getReadCount(outFiles.getDuplicateFilteredBam()));
            System.out.println("Done!");
        }
        //properly aligned bam
        if(go){
            go = aw.getProperlyAlignedBAM(atacseqInput, outFiles);
            samtools.deleteFile(outFiles.getDuplicateFilteredBam());
            samtools.deleteFile(outFiles.getDuplicateMatrix());
        }        
        //chromosome filtered BAM 
        if(go){
            
            go = aw.getChrFilteredBAM(atacseqInput, outFiles, chrSTAT.keySet());
            alignmentFilteringResults.setChromosomeFilteredReads(samtools.getReadCount(outFiles.getChrFilteredBam()));
            samtools.deleteFile(outFiles.getProperlyAlignedBam());
            System.out.println("Done!");
        }        
        //Black list filtered bam
        if(go){
            System.out.print("filter blacklist...");
            go = aw.getBlacklistFilteredBAM(atacseqInput, outFiles);
            alignmentFilteringResults.setBlacklistFilteredReads(samtools.getReadCount(outFiles.getBlackListFilteredBam()));
            samtools.deleteFile(outFiles.getChrFilteredBam());
            System.out.println("Done!");
        }    
        
        if(!go){
              System.err.println("*********************\n please check log, call from runAlignmentFiltering\n***************************");
              return go;
        }
        
        return go;
    }
    
    public boolean runAlignmentFiltering(GuavaInput atacseqInput, GuavaOutputFiles outFiles){
        
        Samtools samtools =  new Samtools();
        AnalysisWorkflow aw =  new AnalysisWorkflow();
        
        //Sam to sorted BAM
        if(go){
            if(!outFiles.getAlignedCsrtBam().exists()){
                go = aw.getCsrtBAM(atacseqInput, outFiles);
                samtools.deleteFile(outFiles.getAlignedSam());    
            }
        }
        //Pre filtering stat
            chrSTAT = samtools.getChrStat(outFiles.getAlignedCsrtBam());
            ExcelPrinter.printChrStat(chrSTAT, atacseqInput.getChromosome());

        //duplicate filtering
        if(go){
            System.out.print("Remove duplicates...");
            go = aw.getDuplicateFilteredBAM(atacseqInput, outFiles);
            alignmentFilteringResults.setDuplicateFilteredReads(samtools.getReadCount(outFiles.getDuplicateFilteredBam()));
            System.out.println("Done!");
        }
        //properly aligned bam
        if(go){
            go = aw.getProperlyAlignedBAM(atacseqInput, outFiles);
            samtools.deleteFile(outFiles.getDuplicateFilteredBam());
            samtools.deleteFile(outFiles.getDuplicateMatrix());
        }        
        //chromosome filtered BAM 
        if(go){
            
            go = aw.getChrFilteredBAM(atacseqInput, outFiles, chrSTAT.keySet());
            alignmentFilteringResults.setChromosomeFilteredReads(samtools.getReadCount(outFiles.getChrFilteredBam()));
            samtools.deleteFile(outFiles.getProperlyAlignedBam());
            System.out.println("Done!");
        }        
        //Black list filtered bam
        if(go){
            System.out.print("filter blacklist...");
            go = aw.getBlacklistFilteredBAM(atacseqInput, outFiles);
            alignmentFilteringResults.setBlacklistFilteredReads(samtools.getReadCount(outFiles.getBlackListFilteredBam()));
            samtools.deleteFile(outFiles.getChrFilteredBam());
            System.out.println("Done!");
        }    
        
        if(!go){
              System.err.println("*********************\n please check log, call from runAlignmentFiltering\n***************************");
              return go;
        }
        
        return go;
    }
    
    public boolean getAlignmentShiftedBAM(GuavaInput atacseqInput, GuavaOutputFiles outFiles){
        Samtools samtools =  new Samtools();
        //BAM to query sorted SAM
        String[] bamToSamlog = samtools.runCommand(samtools.getCommand(atacseqInput,outFiles.getBlackListFilteredBam(), outFiles.getFilteredSrtSam(), "sam", true));
        samtools.writeLog(bamToSamlog, "bam to query sorted sam log");

        //Alignment shift 
        AlignmentShifter shifter = new AlignmentShifter();
        shifter.shiftAlignment(outFiles, outFiles.getFilteredSrtSam());
            if(outFiles.getAtacseqSam().exists()){
                samtools.runCommand(samtools.getCommand(atacseqInput,outFiles.getAtacseqSam(), outFiles.getAtacseqBam(),"bam"));
                samtools.deleteFile(outFiles.getBlackListFilteredBam());
                samtools.deleteFile(outFiles.getAtacseqSam());
                samtools.deleteFile(outFiles.getFilteredSrtSam());
                System.out.println("Done!");
                return true;
            }
        return false;
    }
    
    public boolean getCsrtBAM(GuavaInput atacseqInput, GuavaOutputFiles outFiles){
        Samtools samtools =  new Samtools();
            if(outFiles.getAlignedSam().exists()){
                //SAM  to BAM conversion
                String log[] = samtools.runCommand(samtools.getCommand(atacseqInput,outFiles.getAlignedSam(),outFiles.getAlignedCsrtBam(),"bam"));
                samtools.writeLog(log, "sam to sorted bam log");
                return outFiles.getAlignedCsrtBam().exists();
            }
        return false;
    }
    
    public boolean getDuplicateFilteredBAM(GuavaInput atacseqInput, GuavaOutputFiles outFiles){
        Picard picard =  new Picard();
            if(outFiles.getAlignedCsrtBam().exists()){
                //Mark duplicate
                String[] log = picard.runCommand(picard.getCommand(atacseqInput,outFiles.getAlignedCsrtBam(),outFiles.getDuplicateFilteredBam()));
                picard.writeLog(log, "Picard Mark duplicate log");
                return true;
            }
        return false;
    }

    public boolean getProperlyAlignedBAM (GuavaInput atacseqInput, GuavaOutputFiles outFiles){
        Samtools samtools =  new Samtools();
            if(outFiles.getDuplicateFilteredBam().exists()){
                //Idexing bam
                String[] log = samtools.runCommand(samtools.getCommand(atacseqInput,outFiles.getDuplicateFilteredBam()));
                samtools.writeLog(log, "Indexing duplicate filered bam");
                //get properly aligned bam
                samtools.runCommand(samtools.getCommand(atacseqInput,outFiles));
                samtools.writeLog(log, "samtool properly aligned bam (-f3) log");
                return true;
            }
        return false;
    }
    
    public boolean getChrFilteredBAM (GuavaInput atacseqInput, GuavaOutputFiles outFiles, Set<String> chromosomes){
        String retainChr = "";

        for(String chr : atacseqInput.getChromosome().split(" ")){
            System.out.print("Filter ["+chr+"] reads...");
            chromosomes.remove(chr);
        }
            chromosomes.remove("*");
            
        for(String chr : chromosomes){
            retainChr = retainChr + " " + chr;
        }
        retainChr =  retainChr.trim();
        atacseqInput.setChromosome(retainChr);

        Samtools samtools =  new Samtools();
            if( outFiles.getProperlyAlignedBam().exists()){
                String[] log = samtools.runCommand(samtools.getCommand(atacseqInput,outFiles.getProperlyAlignedBam(), outFiles.getChrFilteredBam()));
                samtools.writeLog(log, "chromosome filetering log");
                return true;
            }
        System.out.println("File Not Found");
        return false;
    }
    
    public boolean getBlacklistFilteredBAM (GuavaInput atacseqInput, GuavaOutputFiles outFiles){
        Samtools samtools =  new Samtools();
            if(outFiles.getChrFilteredBam().exists()){
                String[] log = samtools.runCommand(samtools.getCommand(atacseqInput,outFiles.getChrFilteredBam(),outFiles.getBlackListFilteredBam(), outFiles.getTempBam(), GuavaInput.getBLACKLIST()));
                samtools.deleteFile(outFiles.getTempBam());
                samtools.writeLog(log, "Black list region filtering");
                return true;
            }
        return false;
    }
    
    public boolean callPeaks(GuavaInput atacseqInput, GuavaOutputFiles outFiles, RunStatusJframe runStatusJframe){
    
            MACS2 macs2 = new MACS2();
            String[] log = macs2.runCommand(macs2.getCommand(atacseqInput, outFiles.getAtacseqBam(), outFiles.getMacs2Dir()));
            macs2.writeLog(log, "********************************** MACS2 Peak calling log **********************************");
            MACS2 resMacs2 = MACS2.getMACS2();
            int peakCount = macs2.getPeakCount(resMacs2);
            
            ExcelPrinter.printMACS2results(atacseqInput, peakCount);

            if(!commandLine){
                runStatusJframe.setPeakCallingResult(atacseqInput,peakCount);
            }
            System.out.println("Done!");
            // write code to check success
            return true;
        
    }    
    
    public boolean callPeaks(GuavaInput atacseqInput, GuavaOutputFiles outFiles){
    
            MACS2 macs2 = new MACS2();
            String[] log = macs2.runCommand(macs2.getCommand(atacseqInput, outFiles.getAtacseqBam(), outFiles.getMacs2Dir()));
            macs2.writeLog(log, "********************************** MACS2 Peak calling log **********************************");
            MACS2 resMacs2 = MACS2.getMACS2();
            int peakCount = macs2.getPeakCount(resMacs2);
            ExcelPrinter.printMACS2results(atacseqInput, peakCount);
            System.out.println("Done!");
            // write code to check success
            return true;
        
    }    

    boolean createDir(File dir){
        
        if(!dir.exists()){
            return dir.mkdir();
         }
        else{
            new AnalysisWorkflow().removeDir(dir);
            return dir.mkdir();
        }
        
    }
 
    public boolean removeDir(File dir) { 
        if (dir.isDirectory()) { 
            File[] childrens = dir.listFiles();
            for (File child : childrens) {
                boolean success = new AnalysisWorkflow().removeDir(child);
                if (!success) {
                    return false; 
                }
            }
        }
        // either file or an empty directory
        System.out.println("removing file or directory : " + dir.getName());
        return dir.delete();
         
    }

    public boolean getInsertSizeDistribution(GuavaInput atacseqInput,GuavaOutputFiles outFiles) { 
        if (outFiles.getAtacseqBam().exists()) { 
            Picard picard = new Picard();
            double min_pct = 0.05;
            String[] log = picard.runCommand(picard.getCommand( atacseqInput, outFiles.getAtacseqBam(), outFiles.getInsertSizeTextFile(), outFiles.getInsertSizePDF(), min_pct ));
            picard.writeLog(log, "**************************** InsertSizelog START ****************************");
            System.out.println("Done!");
            return true;
        }
        return false;
    }
  
    private boolean createFragmentSizeDistributionGraph(GuavaInput runATACseq, GuavaOutputFiles outFiles) {
        
        try {
            FileReader peakFileReader = new FileReader(outFiles.getInsertSizeTextFile());
            BufferedReader peakBufferedReader = new BufferedReader(peakFileReader);
            String peakLine = null;
            
            if(outFiles.getrInsertSize().createNewFile()){
                    FileWriter rInputFileWriter =  new FileWriter(outFiles.getrInsertSize());
                    BufferedWriter rInputBufferedWriter = new BufferedWriter(rInputFileWriter);
                    PrintWriter rInputPrintWriter = new PrintWriter(rInputBufferedWriter);
                    rInputPrintWriter.write("insert_size\tcount\n");
                    rInputPrintWriter.flush();
                    
                    while((peakLine = peakBufferedReader.readLine()) != null){
                        Pattern pattern1 =  Pattern.compile("^(\\d+)\\t(\\d+)$");
                        Pattern pattern2 =  Pattern.compile("^(\\d+)\\t(\\d+)\\t(\\d+)$");
                        Matcher matcher1 = pattern1.matcher(peakLine);
                        Matcher matcher2 = pattern2.matcher(peakLine);
                        
                        if(matcher1.find()){
                            rInputPrintWriter.write(peakLine+"\n");
                            rInputPrintWriter.flush();
                        }else if(matcher2.find()){
                            int count = Integer.parseInt(matcher2.group(2)) + Integer.parseInt(matcher2.group(3));
                            rInputPrintWriter.write(matcher2.group(1)+"\t"+count+"\n");
                            rInputPrintWriter.flush();
                        }
                    }
            }
        
            R rplot = new R();
            go = rplot.createRcode(outFiles);
            String log[] = rplot.runCommand(rplot.getCommand(runATACseq, outFiles.getrCode()));
            rplot.writeLog(log, "**************************** Fragment distribution plot log ****************************");
            R.fragmentSizeDistributionPlot = outFiles.getFragmentSizeDistributionPlot();
            
        return true;
        } catch (IOException ex) {
            Logger.getLogger(AnalysisWorkflow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    private boolean runChIPseekeer(GuavaInput atacseqInput, GuavaOutputFiles filesFolders){
        
        boolean status = false;
        ChIPseeker chipSeeker = filesFolders.getChipSeeker();
        status = chipSeeker.createChiPseekerCode(filesFolders, atacseqInput);
        
        if(status){
            String log[] = chipSeeker.runCommand(chipSeeker.getCommand(atacseqInput, chipSeeker.getChipseekerRcode()));
            chipSeeker.writeLog(log, "**************************** ChIPseeker log ****************************");
            return status;
        }
        return status;
    }
        
    private static void printCommandlineResults(GuavaInput atacseqInput, AlignmentResult alignmentResults,
            FilteredAlignment afRes,HashMap<String, Integer> chrSTAT, String chrString){
         
        System.out.println("________________________________________________________");

        System.out.println("___Input Summary _______________________________________");
        System.out.println("R1 fastq: "+atacseqInput.getR1Fastq().getName());
        System.out.println("R2 fastq: "+atacseqInput.getR2Fastq().getName());
        System.out.println("Genome file: "+atacseqInput.getbowtieIndexString().replaceAll(".*\\/", ""));
        System.out.println("Maximum Insert Size: "+atacseqInput.getInsertSize());
        System.out.println("Maximum genomic hits: "+atacseqInput.getMaxGenomicHits());
        System.out.println("");
        
        if(alignmentResults != null){
        System.out.println("________________________________________________________");
        System.out.println("___Alignment Results ___________________________________");
        System.out.println("#Total Reads: "+alignmentResults.getTotalReads());
        System.out.println("#Total Aligned Reads: "+alignmentResults.getReadsAligned()+" ("+alignmentResults.getReadsAligned_pc()+"%)");
        System.out.println("#Total Reads Failed to Align: "+ alignmentResults.getReadsUnaligned()+" ("+alignmentResults.getReadsUnaligned_pc()+"%)");
        System.out.println("#Total Suppressed Reads: "+alignmentResults.getReadsSuppressed()+" ("+alignmentResults.getReadsSuppressed_pc()+"%)");
        
        String[] chrs = chrString.trim().split("\\s");
            for(String chromosome : chrs){
             if(chrSTAT.containsKey(chromosome)){
                System.out.println("#Total "+chromosome+" Reads: "+chrSTAT.get(chromosome));
              }
            else{
                System.out.println("#Total "+chromosome+" Reads: 0");
             }
            }
        }
        System.out.println("");
       
         
        int dupReads = afRes.getTotalAligned() - afRes.getDuplicateFilteredReads();
        int chrReads = afRes.getDuplicateFilteredReads() - afRes.getChromosomeFilteredReads();
        int blacklistReads = afRes.getDuplicateFilteredReads() - afRes.getBlacklistFilteredReads();

        double dup_pc = afRes.getPercentage(dupReads, afRes.getTotalReads()); 
        double chr_pc = afRes.getPercentage(chrReads, afRes.getTotalReads()); 
        double blist_pc = afRes.getPercentage(blacklistReads, afRes.getTotalReads()); 
        double useful_pc = afRes.getPercentage(afRes.getUsefulReads() , afRes.getTotalReads()); 
        
        System.out.println("________________________________________________________");
        System.out.println("___Alignment Filtering Results _________________________");
        System.out.println("#Total Duplicate Reads: "+dupReads+" ("+dup_pc+"%)");
        System.out.println("#Chr[MY] Reads after duplicate filtering: "+chrReads+" ("+chr_pc +"%)");
        System.out.println("#ChrM Blacklist Region Reads: "+blacklistReads+" ("+blist_pc +"%)");
        System.out.println("#Total Useful Reads: "+afRes.getUsefulReads() +" ("+useful_pc +"%)");
        System.out.println("________________________________________________________");
      
    }

    private boolean runFastQC(GuavaInput runATACseq, GuavaOutputFiles outFiles) {
        FastQC fastQC =  new FastQC();
        
            if(outFiles.getFastqcDir().mkdir()){
                //R1 fastqc
                String[] log = fastQC.runCommand(fastQC.getCommand(runATACseq, runATACseq.getR1Fastq(), outFiles.getFastqcDir()));
                fastQC.writeLog(log, "FastQC R1 fastq");

                log = fastQC.runCommand(fastQC.getCommand(runATACseq, runATACseq.getR2Fastq(), outFiles.getFastqcDir()));
                fastQC.writeLog(log, "FastQC R2 fastq");

                return true;
            }
        return false;
        
    }

    private boolean runCutadapt(GuavaInput runATACseq, GuavaOutputFiles outputFiles) {
        
        Cutadapt cutadapt = runATACseq.getCutadapt();
        if(outputFiles.getCutadaptOUT().mkdir()){
            String[] log = cutadapt.runCommand(cutadapt.getCommand(cutadapt));
            cutadapt.writeLog(log, "Cutadapt Adapter Trimming");
            System.out.println("Done");
            return true;
        }
        return false;
    }
    
    private boolean createIGVTracks(GuavaInput runATACseq, GuavaOutputFiles outfiles){
        
        IGVdataTrack iGVdataTrack = new IGVdataTrack(outfiles.getAtacseqBam(), outfiles.getBedgraphFile(),
                                                         outfiles.getBigwigFile(), runATACseq.getGenome() );
        
        
        return iGVdataTrack.createDataTrackFromBamFile();
   
    }
    
    private boolean createFile(File file){
        try {
            return file.createNewFile();
        } catch (IOException ex) {
            System.out.println("ERROR: Insufficient rights, Can not create log file!"); 
            System.out.println(file);
            Logger.getLogger(AnalysisWorkflow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
}
