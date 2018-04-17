/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mayurdivate
 */
public class AlignmentResult {
    
    private int readsAligned;
    private int readsUnaligned;
    private int readsSuppressed;
    private int totalReads;
    private double readsAligned_pc;
    private double readsUnaligned_pc;
    private double readsSuppressed_pc;
    
    
    
    public static void getAlignmentStatistics(String errorLog) {
        AnalysisWorkflow.alignmentResults = new AlignmentResult(0, 0, 0, 0, 0, 0, 0, 0);
        
        //Total reads 
        Pattern readsPattern            = Pattern.compile("# reads processed: (\\d+)\n");
        Matcher matcher = readsPattern.matcher(errorLog);
        if(matcher.find()){
                int totalReads = Integer.parseInt(matcher.group(1));
                AnalysisWorkflow.alignmentResults.setTotalReads(totalReads);
            }

        //aligned reads
        Pattern alignedReadsPattern     = Pattern.compile("# reads with at least one reported alignment: (\\d+) \\((.*?)%\\)\n");
        matcher = alignedReadsPattern.matcher(errorLog);
        if(matcher.find()){
                int readsAligned = Integer.parseInt(matcher.group(1));
                double readsAligned_pc = Double.parseDouble(matcher.group(2));
                AnalysisWorkflow.alignmentResults.setReadsAligned(readsAligned);
                AnalysisWorkflow.alignmentResults.setReadsAligned_pc(readsAligned_pc);
            }
        
        //failed reads
        Pattern failedReadsPattern      = Pattern.compile("# reads that failed to align: (\\d+) \\((.*?)%\\)\n");
        matcher = failedReadsPattern.matcher(errorLog);
        if(matcher.find()){
                int failedReads = Integer.parseInt(matcher.group(1));
                double failedReads_pc = Double.parseDouble(matcher.group(2));
                AnalysisWorkflow.alignmentResults.setReadsUnaligned(failedReads);
                AnalysisWorkflow.alignmentResults.setReadsUnaligned_pc(failedReads_pc);
            }
        
        //suppressed reads
        Pattern suppressedReadsPattern  = Pattern.compile("# reads with alignments suppressed due to \\-m: (\\d+) \\((.*?)%\\)\n");
        matcher = suppressedReadsPattern.matcher(errorLog);
        if(matcher.find()){
                int suppressedReads = Integer.parseInt(matcher.group(1));
                double suppressedReads_pc = Double.parseDouble(matcher.group(2));
                AnalysisWorkflow.alignmentResults.setReadsSuppressed(suppressedReads);
                AnalysisWorkflow.alignmentResults.setReadsSuppressed_pc(suppressedReads_pc);
            }
        
    }

    public static void getBowtie2AlignmentStatistics(String errorLog) {
        AnalysisWorkflow.alignmentResults = new AlignmentResult(0, 0, 0, 0, 0, 0, 0, 0);
        
        // 36003 reads; of these:
        // 36003 (100.00%) were paired; of these:
        
        Pattern readsPattern   = Pattern.compile("(\\d+) reads; of these:\n");
        Matcher matcher = readsPattern.matcher(errorLog);
        if(matcher.find()){
                int totalReads = Integer.parseInt(matcher.group(1));
                AnalysisWorkflow.alignmentResults.setTotalReads(totalReads);
        }

        //aligned reads
        //33114 (91.98%) aligned concordantly exactly 1 time
        //2855 (7.93%) aligned concordantly >1 times
        Pattern alignedReadsOncePattern = Pattern.compile("(\\d+) (.*?) aligned concordantly exactly 1 time\n");
        Pattern alignedReadsPattern     = Pattern.compile("(\\d+) (.*?) aligned concordantly >1 times\n");
        Matcher matcherOnce = alignedReadsOncePattern.matcher(errorLog);
        matcher = alignedReadsPattern.matcher(errorLog);
        
        int readsAligned = 0 ;
        if(matcherOnce.find()){
            readsAligned =  readsAligned + Integer.parseInt(matcherOnce.group(1));
        }
        
        if(matcher.find()){
                readsAligned = readsAligned + Integer.parseInt(matcher.group(1));
                
        }

        //percentage mapping 
        Pattern alignedPC     = Pattern.compile("(.*?)% overall alignment rate\n");
        matcher = alignedPC.matcher(errorLog);
        
        if(matcher.find()){
                double readsAligned_pc = Double.parseDouble(matcher.group(1));
                AnalysisWorkflow.alignmentResults.setReadsAligned(readsAligned);
                AnalysisWorkflow.alignmentResults.setReadsAligned_pc(readsAligned_pc);
        }
        
        //failed reads
        Pattern failedReadsPattern  = Pattern.compile("(\\d+) \\((.*?)%\\) aligned concordantly 0 times\n");
        matcher = failedReadsPattern.matcher(errorLog);
        if(matcher.find()){
                int failedReads = Integer.parseInt(matcher.group(1));
                double failedReads_pc = Double.parseDouble(matcher.group(2));
                AnalysisWorkflow.alignmentResults.setReadsUnaligned(failedReads);
                AnalysisWorkflow.alignmentResults.setReadsUnaligned_pc(failedReads_pc);
            }
        
        //suppressed reads == 0; later <- aligned - maqfiltered 
                AnalysisWorkflow.alignmentResults.setReadsSuppressed(0);
                AnalysisWorkflow.alignmentResults.setReadsSuppressed_pc(0.0);
        
    }
    
    public AlignmentResult(int readsAligned, int readsUnaligned, int readsSuppressed, int totalReads, double readsAligned_pc, double readsUnaligned_pc, double readsSuppressed_pc, double totalReads_pc) {
        this.readsAligned = readsAligned;
        this.readsUnaligned = readsUnaligned;
        this.readsSuppressed = readsSuppressed;
        this.totalReads = totalReads;
        this.readsAligned_pc = readsAligned_pc;
        this.readsUnaligned_pc = readsUnaligned_pc;
        this.readsSuppressed_pc = readsSuppressed_pc;
    }

    public static double getPercentage(double numerator, double denominator){
        double fraction = (numerator  / denominator) * 100;
        fraction = Math.round(fraction * 100.0) / 100.0;
        return fraction;
    }
    
    
    /**
     * @return the readsAligned
     */
    public int getReadsAligned() {
        return readsAligned;
    }

    /**
     * @param readsAligned the readsAligned to set
     */
    public void setReadsAligned(int readsAligned) {
        this.readsAligned = readsAligned;
        if(this.getReadsAligned() > 0 ){
            double new_pc = AlignmentResult.getPercentage(readsAligned, this.getTotalReads());
            this.setReadsAligned_pc(new_pc);
        }
    }

    /**
     * @return the readsUnaligned
     */
    public int getReadsUnaligned() {
        return readsUnaligned;
    }

    /**
     * @param readsUnaligned the readsUnaligned to set
     */
    public void setReadsUnaligned(int readsUnaligned) {
        this.readsUnaligned = readsUnaligned;
        if(this.getReadsAligned() > 0 ){
            double new_pc = AlignmentResult.getPercentage(readsUnaligned, this.getTotalReads());
            this.setReadsUnaligned_pc(new_pc);
        }
    }

    /**
     * @return the readsSuppressed
     */
    public int getReadsSuppressed() {
        return readsSuppressed;
    }

    /**
     * @param readsSuppressed the readsSuppressed to set
     */
    public void setReadsSuppressed(int readsSuppressed) {
        this.readsSuppressed = readsSuppressed;
        if(this.getReadsAligned() > 0 ){
            double new_pc = AlignmentResult.getPercentage(readsSuppressed, this.getTotalReads());
            this.setReadsSuppressed_pc(new_pc);
        }
    }

    /**
     * @return the totalReads
     */
    public int getTotalReads() {
        return totalReads;
    }

    /**
     * @param totalReads the totalReads to set
     */
    public void setTotalReads(int totalReads) {
        this.totalReads = totalReads;
    }

    /**
     * @return the readsAligned_pc
     */
    public double getReadsAligned_pc() {
        return readsAligned_pc;
    }

    /**
     * @param readsAligned_pc the readsAligned_pc to set
     */
    public void setReadsAligned_pc(double readsAligned_pc) {
        this.readsAligned_pc = readsAligned_pc;
    }

    /**
     * @return the readsUnaligned_pc
     */
    public double getReadsUnaligned_pc() {
        return readsUnaligned_pc;
    }

    /**
     * @param readsUnaligned_pc the readsUnaligned_pc to set
     */
    public void setReadsUnaligned_pc(double readsUnaligned_pc) {
        this.readsUnaligned_pc = readsUnaligned_pc;
    }

    /**
     * @return the readsSuppressed_pc
     */
    public double getReadsSuppressed_pc() {
        return readsSuppressed_pc;
    }

    /**
     * @param readsSuppressed_pc the readsSuppressed_pc to set
     */
    public void setReadsSuppressed_pc(double readsSuppressed_pc) {
        this.readsSuppressed_pc = readsSuppressed_pc;
    }

    
}
