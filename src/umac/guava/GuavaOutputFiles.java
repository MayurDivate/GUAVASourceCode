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
    public static String PATTERN="\\.f(ast)?q(\\.)?(gz)?$";
    public static String OUTPATTERN="_OUTPUT";
    
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

    private ChIPpeakAnno chipPeakAnno;

    private File cutadaptOUT; 
    
    private File bedgraphFile;
    private File bigwigFile;

    @Override
    public String toString() {
        String guavaOutputFilesString = "";
        guavaOutputFilesString = guavaOutputFilesString + this.getCutadaptOUT()+"\n";
        guavaOutputFilesString = guavaOutputFilesString + this.getFastqcDir()+"\n";
        return guavaOutputFilesString;
    }
    
    public static void setRootDir(GuavaInput guavaInput) {
        String rootDirName = guavaInput.getR1Fastq().getName().replaceAll(PATTERN, OUTPATTERN); 
        rootDir = new File(guavaInput.getOutputFolder(),rootDirName);
    }
    
    public static String getOutBaseName(){
        if(rootDir != null){
            return rootDir.getName().replaceAll(OUTPATTERN+"$", "_");
        }
        return null;
    }
    
    public GuavaOutputFiles getOutputFiles(GuavaInput guavaInput){
        setRootDir(guavaInput);
        String outBaseName = getOutBaseName();
        return getGuavaOutputFiles(rootDir, outBaseName);
    }

//     Method to create outfiles object later in workflow once rootDir set
    public static GuavaOutputFiles getOutputFiles(){
        String outBaseName = getOutBaseName();
        return getGuavaOutputFiles(rootDir, outBaseName);
    }
    
    // one method to create list of files
    public static GuavaOutputFiles getGuavaOutputFiles(File rootFolder, String outBaseName){
        
        File logFile                = new File(rootFolder,outBaseName+"log.txt");
        File alignedSam             = new File(rootFolder,outBaseName+"aligned.sam");
        File alignedCsrtBam         = new File(rootFolder,outBaseName+"aligned_csrt.bam");
        File duplicateFilteredBam   = new File(rootFolder,outBaseName+"aligned_duplicate_filtered.bam");
        File duplicateMatrix        = new File(rootFolder,outBaseName+"aligned_duplicate_matrix.txt");
        File properlyAlignedBam     = new File(rootFolder,outBaseName+"aligned_proper_aligned_reads.bam");
        File chrFilteredBam         = new File(rootFolder,outBaseName+"aligned_chr_filtered.bam");
        File blackListFilteredBam   = new File(rootFolder,outBaseName+"aligned_blacklist_filtered.bam");
        File tempBam                = new File(rootFolder,outBaseName+"aligned_temp.bam");
        File filteredSrtSam         = new File(rootFolder,outBaseName+"aligned_Filtered.sam");
        File atacseqBam             = new File(rootFolder,outBaseName+"aligned_ATACseq.bam");
        File atacseqSam             = new File(rootFolder,outBaseName+"aligned_ATACseq.sam");
        File macs2Dir               = new File(rootFolder,outBaseName+"PEAK_CALLING");
        File insertSizeTextFile     = new File(rootFolder,outBaseName+"picard_insert_size.txt");
        File insertSizePDF          = new File(rootFolder,outBaseName+"picard_insert_size.pdf");
        File fragmentSizePlot       = new File(rootFolder,outBaseName+"fragment_size_distribution.jpg");
        File rInsertSize            = new File(rootFolder,outBaseName+"insert_size.txt");
        File rCode                  = new File(rootFolder,outBaseName+"fragmentSizeDistribution.r");
        File fastqcDir              = new File(rootFolder,outBaseName+"fastQC_OUTPUT");
        File cutadaptOutdir         = new File(rootFolder,outBaseName+"Adapter_Trimming");
        File bedgraph               = new File(rootFolder,outBaseName+"aligned_ATACseq.bdg");
        File bigwig                 = new File(rootFolder,outBaseName+"aligned_ATACseq.bw");
        
        //chippeak anno files
        File macs2Xls = new File(macs2Dir,outBaseName+"peaks.xls");
        Genome genome = Genome.getGenomeObject(IGV.genome);
        
        ChIPpeakAnno chIPpeakAnno = ChIPpeakAnno.getChIPpeakAnnoObject(macs2Xls, "MACS2", genome);
        
        return new GuavaOutputFiles(rootDir, logFile, alignedSam, alignedCsrtBam, duplicateFilteredBam,duplicateMatrix ,properlyAlignedBam,
                                    chrFilteredBam, blackListFilteredBam, tempBam,filteredSrtSam ,atacseqBam,atacseqSam, macs2Dir, 
                                    insertSizeTextFile, insertSizePDF, rInsertSize,rCode,chIPpeakAnno,fragmentSizePlot,fastqcDir,cutadaptOutdir,
                                    bedgraph, bigwig);
    }
    
    
    public GuavaOutputFiles() {
    }
    
    public GuavaOutputFiles(File rootDir, File logFile, File alignedSam, File alignedCsrtBam, File duplicateFilteredBam,File duplicateMatrix ,File properlyAlignedBam,
                              File chrFilteredBam, File blackListFilteredBam, File tempBam,File filteredSrtSam, File atacseqBam, File atacseqSam, File macs2Dir,
                              File insertSizeTextFile, File  insertSizePDF, File rInsertSize, File rCode, ChIPpeakAnno chipPeakAnno,File fragmentSizeDistributionPlot,
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
        this.chipPeakAnno = chipPeakAnno;
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

    public static File getLogFile() {
        return logFile;
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
    
    
}
