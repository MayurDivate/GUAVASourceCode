/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.File;

/**
 *
 * @author mayurdivate
 */
public class GuavaOutputFiles {

    public static File rootDir;
    public static File logFile;
    
    private File alignedSam;
    private File alignedCsrtBam;
    
    private File duplicateFilteredBam;
    private File duplicateMatrix;
    private File properlyAlignedBam;
    private File chrFilteredBam;
    private File blackListFilteredBam;
    private File tempBam;

    private File atacseqBam;
    private File atacseqSam;
    private File filteredSrtSam;
    
    private File macs2Dir;
    
    private File insertSizeTextFile;
    private File insertSizePDF;
    private File rInsertSize;
    private File rCode;
    private File fragmentSizeDistributionPlot;

    private File fastqcDir;

    private ChIPseeker chipSeeker;

    private File cutadaptOUT; 
    
    private File bedgraphFile;
    private File bigwigFile;
    
    
    public GuavaOutputFiles getOutputFiles(GuavaInput runATACseq){
        
        System.out.println("Setting output files and flders...");
        rootDir                = new File(runATACseq.getOutputFolder().getAbsolutePath()+System.getProperty("file.separator")+
                                               runATACseq.getR1Fastq().getName().replaceAll("\\.f(.*?)q", "_OUTPUT"));
        
        String sampleBaseName = runATACseq.getR1Fastq().getName().replaceAll("\\.f(.*?)q", "_");
        
        File logFile                = new File(getOutputFilePath(sampleBaseName+"log.txt"));
        File alignedSam             = new File(getOutputFilePath(sampleBaseName+"aligned.sam"));
        File alignedCsrtBam         = new File(getOutputFilePath(sampleBaseName+"aligned_csrt.bam"));
        File duplicateFilteredBam   = new File(getOutputFilePath(sampleBaseName+"aligned_duplicate_filtered.bam"));
        File duplicateMatrix        = new File(getOutputFilePath(sampleBaseName+"aligned_duplicate_matrix.txt"));
        File properlyAlignedBam     = new File(getOutputFilePath(sampleBaseName+"aligned_proper_aligned_reads.bam"));
        File chrFilteredBam         = new File(getOutputFilePath(sampleBaseName+"aligned_chr_filtered.bam"));
        File blackListFilteredBam   = new File(getOutputFilePath(sampleBaseName+"aligned_blacklist_filtered.bam"));
        File tempBam                = new File(getOutputFilePath(sampleBaseName+"aligned_temp.bam"));
        File filteredSrtSam         = new File(getOutputFilePath(sampleBaseName+"aligned_Filtered.sam"));
        File atacseqBam             = new File(getOutputFilePath(sampleBaseName+"aligned_ATACseq.bam"));
        File atacseqSam             = new File(getOutputFilePath(sampleBaseName+"aligned_ATACseq.sam"));
        File macs2Dir               = new File(getOutputFilePath(sampleBaseName+"PEAK_CALLING"));
        File insertSizeTextFile     = new File(getOutputFilePath(sampleBaseName+"picard_insert_size.txt"));
        File insertSizePDF          = new File(getOutputFilePath(sampleBaseName+"picard_insert_size.pdf"));
        File fragmentSizePlot       = new File(getOutputFilePath(sampleBaseName+"fragment_size_distribution.jpg"));
        File rInsertSize            = new File(getOutputFilePath(sampleBaseName+"insert_size.txt"));
        File rCode                  = new File(getOutputFilePath(sampleBaseName+"fragmentSizeDistribution.r"));
        File fastqcDir              = new File(getOutputFilePath(sampleBaseName+"fastQC_OUTPUT"));
        File cutadaptOutdir         = new File(getOutputFilePath(sampleBaseName+"Adapter_Trimming"));
        File bedgraph               = new File(getOutputFilePath(sampleBaseName+"aligned_ATACseq.bdg"));
        File bigwig                 = new File(getOutputFilePath(sampleBaseName+"aligned_ATACseq.bw"));
        
        ChIPseeker chipSeeker = ChIPseeker.getChIPSeekerObject();
        
        return new GuavaOutputFiles(rootDir, logFile, alignedSam, alignedCsrtBam, duplicateFilteredBam,duplicateMatrix ,properlyAlignedBam,
                                    chrFilteredBam, blackListFilteredBam, tempBam,filteredSrtSam ,atacseqBam,atacseqSam, macs2Dir, 
                                    insertSizeTextFile, insertSizePDF, rInsertSize,rCode,chipSeeker,fragmentSizePlot,
                                fastqcDir, cutadaptOutdir,bedgraph,bigwig);
        
    }

//     Method to create outfiles object later in workflow once rootDir set
     public static GuavaOutputFiles getOutputFiles(){
        
        String sampleBaseName = GuavaOutputFiles.rootDir.getName().replaceAll("_OUTPUT", "_");
        
        File logFile                = new File(getOutputFilePath(sampleBaseName+"log.txt"));
        File alignedSam             = new File(getOutputFilePath(sampleBaseName+"aligned.sam"));
        File alignedCsrtBam         = new File(getOutputFilePath(sampleBaseName+"aligned_csrt.bam"));
        File duplicateFilteredBam   = new File(getOutputFilePath(sampleBaseName+"aligned_duplicate_filtered.bam"));
        File duplicateMatrix        = new File(getOutputFilePath(sampleBaseName+"aligned_duplicate_matrix.txt"));
        File properlyAlignedBam     = new File(getOutputFilePath(sampleBaseName+"aligned_proper_aligned_reads.bam"));
        File chrFilteredBam         = new File(getOutputFilePath(sampleBaseName+"aligned_chr_filtered.bam"));
        File blackListFilteredBam   = new File(getOutputFilePath(sampleBaseName+"aligned_blacklist_filtered.bam"));
        File tempBam                = new File(getOutputFilePath(sampleBaseName+"aligned_temp.bam"));
        File filteredSrtSam         = new File(getOutputFilePath(sampleBaseName+"aligned_Filtered.sam"));
        File atacseqBam             = new File(getOutputFilePath(sampleBaseName+"aligned_ATACseq.bam"));
        File atacseqSam             = new File(getOutputFilePath(sampleBaseName+"aligned_ATACseq.sam"));
        File macs2Dir               = new File(getOutputFilePath(sampleBaseName+"PEAK_CALLING"));
        File insertSizeTextFile     = new File(getOutputFilePath(sampleBaseName+"picard_insert_size.txt"));
        File insertSizePDF          = new File(getOutputFilePath(sampleBaseName+"picard_insert_size.pdf"));
        File fragmentSizePlot       = new File(getOutputFilePath(sampleBaseName+"fragment_size_distribution.jpg"));
        File rInsertSize            = new File(getOutputFilePath(sampleBaseName+"insert_size.txt"));
        File rCode                  = new File(getOutputFilePath(sampleBaseName+"fragmentSizeDistribution.r"));
        File fastqcDir              = new File(getOutputFilePath(sampleBaseName+"R2_fastQC_OUTPUT"));
        File cutadaptOutdir         = new File(getOutputFilePath(sampleBaseName+"Adapter_Trimming"));
        File bedgraph               = new File(getOutputFilePath(sampleBaseName+"aligned_ATACseq.bdg"));
        File bigwig                 = new File(getOutputFilePath(sampleBaseName+"aligned_ATACseq.bw"));
        

        ChIPseeker chipSeeker       = ChIPseeker.getChIPSeekerObject();
        return new GuavaOutputFiles(rootDir, logFile, alignedSam, alignedCsrtBam, duplicateFilteredBam,duplicateMatrix ,properlyAlignedBam,
                                    chrFilteredBam, blackListFilteredBam, tempBam,filteredSrtSam ,atacseqBam,atacseqSam, macs2Dir, 
                                    insertSizeTextFile, insertSizePDF, rInsertSize,rCode,chipSeeker,fragmentSizePlot,fastqcDir,cutadaptOutdir,
                                    bedgraph, bigwig);
    }
    
    
    public static GuavaOutputFiles getGuavaOutputFiles(GuavaInput guavaInput){
        
        rootDir                = new File(guavaInput.getOutputFolder().getAbsolutePath()+System.getProperty("file.separator")+
                                               guavaInput.getR1Fastq().getName().replaceAll("\\.f(.*?)q", "_OUTPUT"));
                
        if(GuavaOutputFiles.rootDir != null){
            
        String sampleBaseName = GuavaOutputFiles.rootDir.getName().replaceAll("_OUTPUT", "_");
        
        File logFile                = new File(getOutputFilePath(sampleBaseName+"log.txt"));
        File alignedSam             = new File(getOutputFilePath(sampleBaseName+"aligned.sam"));
        File alignedCsrtBam         = new File(getOutputFilePath(sampleBaseName+"aligned_csrt.bam"));
        File duplicateFilteredBam   = new File(getOutputFilePath(sampleBaseName+"aligned_duplicate_filtered.bam"));
        File duplicateMatrix        = new File(getOutputFilePath(sampleBaseName+"aligned_duplicate_matrix.txt"));
        File properlyAlignedBam     = new File(getOutputFilePath(sampleBaseName+"aligned_proper_aligned_reads.bam"));
        File chrFilteredBam         = new File(getOutputFilePath(sampleBaseName+"aligned_chr_filtered.bam"));
        File blackListFilteredBam   = new File(getOutputFilePath(sampleBaseName+"aligned_blacklist_filtered.bam"));
        File tempBam                = new File(getOutputFilePath(sampleBaseName+"aligned_temp.bam"));
        File filteredSrtSam         = new File(getOutputFilePath(sampleBaseName+"aligned_Filtered.sam"));
        File atacseqBam             = new File(getOutputFilePath(sampleBaseName+"aligned_ATACseq.bam"));
        File atacseqSam             = new File(getOutputFilePath(sampleBaseName+"aligned_ATACseq.sam"));
        File macs2Dir               = new File(getOutputFilePath(sampleBaseName+"PEAK_CALLING"));
        File insertSizeTextFile     = new File(getOutputFilePath(sampleBaseName+"picard_insert_size.txt"));
        File insertSizePDF          = new File(getOutputFilePath(sampleBaseName+"picard_insert_size.pdf"));
        File fragmentSizePlot       = new File(getOutputFilePath(sampleBaseName+"fragment_size_distribution.jpg"));
        File rInsertSize            = new File(getOutputFilePath(sampleBaseName+"insert_size.txt"));
        File rCode                  = new File(getOutputFilePath(sampleBaseName+"fragmentSizeDistribution.r"));
        File fastqcDir              = new File(getOutputFilePath(sampleBaseName+"R2_fastQC_OUTPUT"));
        File cutadaptOutdir         = new File(getOutputFilePath(sampleBaseName+"Adapter_Trimming"));
        File bedgraph               = new File(getOutputFilePath(sampleBaseName+"aligned_ATACseq.bdg"));
        File bigwig                 = new File(getOutputFilePath(sampleBaseName+"aligned_ATACseq.bw"));
        

        ChIPseeker chipSeeker       = ChIPseeker.getChIPSeekerObject();
        return new GuavaOutputFiles(rootDir, logFile, alignedSam, alignedCsrtBam, duplicateFilteredBam,duplicateMatrix ,properlyAlignedBam,
                                    chrFilteredBam, blackListFilteredBam, tempBam,filteredSrtSam ,atacseqBam,atacseqSam, macs2Dir, 
                                    insertSizeTextFile, insertSizePDF, rInsertSize,rCode,chipSeeker,fragmentSizePlot,fastqcDir,cutadaptOutdir,
                                    bedgraph, bigwig);
        }
        
         System.out.println("Early call for Guava outfiles.");
         System.out.println("Can not be created at the moment.");
         return null;
    }
    
     
     
    
    private static String getOutputFilePath(String fileName){
        if(fileName != null){
            String path = rootDir.getAbsolutePath()+System.getProperty("file.separator")+fileName;
            return path;
        }
        return fileName;
    }
    
    public GuavaOutputFiles() {
    }
    
    public GuavaOutputFiles(File rootDir, File logFile, File alignedSam, File alignedCsrtBam, File duplicateFilteredBam,File duplicateMatrix ,File properlyAlignedBam,
                              File chrFilteredBam, File blackListFilteredBam, File tempBam,File filteredSrtSam, File atacseqBam, File atacseqSam, File macs2Dir,
                              File insertSizeTextFile, File  insertSizePDF, File rInsertSize, File rCode, ChIPseeker chipSeeker,File fragmentSizeDistributionPlot,
                              File fastqcDir, File cutadaptOutdir, File bedgraph, File bigwig) {
        this.rootDir = rootDir;
        this.alignedSam = alignedSam;
        this.logFile = logFile;
        this.alignedCsrtBam = alignedCsrtBam;
        this.duplicateFilteredBam = duplicateFilteredBam;
        this.properlyAlignedBam = properlyAlignedBam;
        this.chrFilteredBam = chrFilteredBam;
        this.blackListFilteredBam = blackListFilteredBam;
        this.tempBam = tempBam;
        this.atacseqBam = atacseqBam;
        this.filteredSrtSam = filteredSrtSam;
        this.atacseqSam = atacseqSam;
        this.macs2Dir = macs2Dir;
        this.duplicateMatrix = duplicateMatrix;
        this.insertSizePDF = insertSizePDF;
        this.insertSizeTextFile = insertSizeTextFile;
        this.rInsertSize = rInsertSize;
        this.rCode = rCode;
        this.chipSeeker = chipSeeker;
        this.fragmentSizeDistributionPlot = fragmentSizeDistributionPlot;
        this.fastqcDir = fastqcDir;
        this.cutadaptOUT =  cutadaptOutdir;
        this.bedgraphFile =  bedgraph;
        this.bigwigFile = bigwig;
    }

    
    /**
     * @return the atacseqSam
     */
    public File getAtacseqSam() {
        return atacseqSam;
    }

    /**
     * @param atacseqSam the atacseqSam to set
     */
    public void setAtacseqSam(File atacseqSam) {
        this.atacseqSam = atacseqSam;
    }

    public ChIPseeker getChipSeeker() {
        return chipSeeker;
    }

    public static File getLogFile() {
        return logFile;
    }

    public void setChipSeeker(ChIPseeker chipSeeker) {
        this.chipSeeker = chipSeeker;
    }

    public File getFragmentSizeDistributionPlot() {
        return fragmentSizeDistributionPlot;
    }

    public void setFragmentSizeDistributionPlot(File fragmentSizeDistributionPlot) {
        this.fragmentSizeDistributionPlot = fragmentSizeDistributionPlot;
    }
        /**
     * @return the rootDir
     */
    public File getRootDir() {
        return rootDir;
    }

    /**
     * @param rootDir the rootDir to set
     */
    public void setRootDir(File rootDir) {
        this.rootDir = rootDir;
    }

    /**
     * @return the alignedSam
     */
    public File getAlignedSam() {
        return alignedSam;
    }

    /**
     * @param alignedSam the alignedSam to set
     */
    public void setAlignedSam(File alignedSam) {
        this.alignedSam = alignedSam;
    }

    /**
     * @return the alignedCsrtBam
     */
    public File getAlignedCsrtBam() {
        return alignedCsrtBam;
    }

    /**
     * @param alignedCsrtBam the alignedCsrtBam to set
     */
    public void setAlignedCsrtBam(File alignedCsrtBam) {
        this.alignedCsrtBam = alignedCsrtBam;
    }

    /**
     * @return the duplicateFilteredBam
     */
    public File getDuplicateFilteredBam() {
        return duplicateFilteredBam;
    }

    /**
     * @param duplicateFilteredBam the duplicateFilteredBam to set
     */
    public void setDuplicateFilteredBam(File duplicateFilteredBam) {
        this.duplicateFilteredBam = duplicateFilteredBam;
    }

    /**
     * @return the properlyAlignedBam
     */
    public File getProperlyAlignedBam() {
        return properlyAlignedBam;
    }

    /**
     * @param properlyAlignedBam the properlyAlignedBam to set
     */
    public void setProperlyAlignedBam(File properlyAlignedBam) {
        this.properlyAlignedBam = properlyAlignedBam;
    }

    /**
     * @return the chrFilteredBam
     */
    public File getChrFilteredBam() {
        return chrFilteredBam;
    }

    /**
     * @param chrFilteredBam the chrFilteredBam to set
     */
    public void setChrFilteredBam(File chrFilteredBam) {
        this.chrFilteredBam = chrFilteredBam;
    }

    /**
     * @return the blackListFilteredBam
     */
    public File getBlackListFilteredBam() {
        return blackListFilteredBam;
    }

    /**
     * @param blackListFilteredBam the blackListFilteredBam to set
     */
    public void setBlackListFilteredBam(File blackListFilteredBam) {
        this.blackListFilteredBam = blackListFilteredBam;
    }

    /**
     * @return the tempBam
     */
    public File getTempBam() {
        return tempBam;
    }

    /**
     * @param tempBam the tempBam to set
     */
    public void setTempBam(File tempBam) {
        this.tempBam = tempBam;
    }

    /**
     * @return the atacseqBam
     */
    public File getAtacseqBam() {
        return atacseqBam;
    }

    /**
     * @param atacseqBam the atacseqBam to set
     */
    public void setAtacseqBam(File atacseqBam) {
        this.atacseqBam = atacseqBam;
    }

    /**
     * @return the macs2Dir
     */
    public File getMacs2Dir() {
        return macs2Dir;
    }

    /**
     * @param macs2Dir the macs2Dir to set
     */
    public void setMacs2Dir(File macs2Dir) {
        this.macs2Dir = macs2Dir;
    }

    public File getDuplicateMatrix() {
        return duplicateMatrix;
    }

    public void setDuplicateMatrix(File duplicateMatrix) {
        this.duplicateMatrix = duplicateMatrix;
    }

    public File getFilteredSrtSam() {
        return filteredSrtSam;
    }

    public void setFilteredSrtSam(File filteredSrtSam) {
        this.filteredSrtSam = filteredSrtSam;
    }

    public File getInsertSizePDF() {
        return insertSizePDF;
    }

    public void setInsertSizePDF(File insertSizePDF) {
        this.insertSizePDF = insertSizePDF;
    }

    public File getInsertSizeTextFile() {
        return insertSizeTextFile;
    }

    public void setInsertSizeTextFile(File insertSizeTextFile) {
        this.insertSizeTextFile = insertSizeTextFile;
    }

    public File getrInsertSize() {
        return rInsertSize;
    }

    public void setrInsertSize(File rInsertSize) {
        this.rInsertSize = rInsertSize;
    }

    public static void setLogFile(File logFile) {
        GuavaOutputFiles.logFile = logFile;
    }

    public File getrCode() {
        return rCode;
    }

    public void setrCode(File rCode) {
        this.rCode = rCode;
    }

    public void setFastqcDir(File fastqcDir) {
        this.fastqcDir = fastqcDir;
    }

    public File getFastqcDir() {
        return fastqcDir;
    }

    public File getCutadaptOUT() {
        return cutadaptOUT;
    }

    public void setCutadaptOUT(File cutadaptOUT) {
        this.cutadaptOUT = cutadaptOUT;
    }

    public File getBedgraphFile() {
        return bedgraphFile;
    }

    public void setBedgraphFile(File bedgraphFile) {
        this.bedgraphFile = bedgraphFile;
    }

    public File getBigwigFile() {
        return bigwigFile;
    }

    public void setBigwigFile(File bigwigFile) {
        this.bigwigFile = bigwigFile;
    }
    
    
}
