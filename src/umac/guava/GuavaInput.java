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

public class GuavaInput extends Input {
    
    private static String basenameBLACKLIST;
    private static File BLACKLIST;
    
// object attributes    
    private File r1Fastq;
    private File r2Fastq;
    private File bowtieIndex;
    private File outputFolder;
    private int maxGenomicHits;
    private int cpu_units;
    private int insertSize;
    private int ramMemory;
    private String Organism;
    private String genome;
    private String chromosome;
    private double pqCutOff;
    private String pqString;
    private Cutadapt cutadapt;
    private boolean trim;
    private String aligner;
    private int mapQ;
    private Genome genomeObject;
    
    
    public String getInputSummary(){
        String inputSummary = "";
        
        inputSummary = inputSummary +""
                + "R1 fastq: "+this.getR1Fastq().getAbsolutePath()+"\n"
                + "R2 fatsq: "+this.getR2Fastq().getAbsolutePath()+"\n"
                + "Genome Index: "+this.getbowtieIndexString()+"\n\n"
                + "Aligner: "+ this.getAligner()+"\n"
                + "Max insert size: "+this.getInsertSize()+"\n";
        
        if(this.aligner.equals("bowtie")){
            inputSummary = inputSummary + "Max genomic hits: "+this.getMaxGenomicHits()+"\n";
        }
        else if(this.aligner.equals("bowtie2")){
            inputSummary = inputSummary + "Min mapping quality: "+this.getMapQ()+"\n";
        }
        inputSummary = inputSummary +""
                + "chrs: "+this.getChromosome()+"\n";

        inputSummary = inputSummary +"\n"
                + "Genome : "+ this.getGenomeObject().getGenomeName()+" ("+ this.getGenomeObject().getOrganismName()+")\n";
        
        if(this.isTrim()){
            inputSummary = inputSummary +""
                    + "Adapter trimming using Cutadapt" + "\n"
                    + ""+this.getCutadapt().toString()
                    + "\n";
            
        }
        
        inputSummary = inputSummary +""
                + "Peak calling: "+this.getPqString()+" = "+this.getPqCutOff()+"\n"
                + "\n";
        
        
        return inputSummary;
    }
    
// constructor     
    public GuavaInput(){
        this.Organism = null;
        this.chromosome = "chrM";
        this.cpu_units = 1;
        this.ramMemory = 1;
        this.insertSize = 2000;
        this.maxGenomicHits = 1;
        this.bowtieIndex = null;
        this.genome = null;
        this.r1Fastq = null;
        this.r2Fastq = null;
        this.outputFolder = null;
        this.pqCutOff = 0.05;
        this.pqString = "-q";
        this.mapQ = 10;
        this.aligner = "bowtie";
    }
   
    /**
     * @return the ramMemory
     */
    public int getRamMemory() {
        return ramMemory;
    }

    /**
     * @param ramMemory the ramMemory to set
     */
    public void setRamMemory(int ramMemory) {
        this.ramMemory = ramMemory;
    }
    /** Getters and setters  **/
    public File getR1Fastq() {
        return r1Fastq;
    }

    public void setR1Fastq(String r1Fastq) {
        this.setR1Fastq(new File(r1Fastq));
    }
    
    public void setR1Fastq(File r1Fastq) {
        this.r1Fastq = r1Fastq;
    }

    public File getR2Fastq() {
        return r2Fastq;
    }

    public void setR2Fastq(String r2Fastq) {
        this.setR2Fastq(new File(r2Fastq));
    }
    
    public void setR2Fastq(File r2Fastq) {
        this.r2Fastq = r2Fastq;
    }

    public File getBowtieIndex() {
        return bowtieIndex;
    }

    public void setBowtieIndex(String bowtieIndex) {
        this.setBowtieIndex(new File(bowtieIndex));
    }

    public void setBowtieIndex(File bowtieIndex) {
        this.bowtieIndex = bowtieIndex;
    }
    
    public File getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.setOutputFolder(new File(outputFolder));
    }

    public void setOutputFolder(File outputFolder) {
        this.outputFolder = outputFolder;
    }
    
    public int getMaxGenomicHits() {
        return maxGenomicHits;
    }

    public void setMaxGenomicHits(int maxGenomicHits) {
        this.maxGenomicHits = maxGenomicHits;
    }

    public int getCpu_units() {
        return cpu_units;
    }

    public void setCpu_units(int cpu_units) {
        this.cpu_units = cpu_units;
    }

    public String getOrganism() {
        return getOrgCode(Organism);
    }

    public boolean setOrganism(String Organism) {
        if (Organism == null) {
            return false;
        }
        this.Organism = Organism;
        return true;

    }
    
    public void setGenome(String genome) {
        if( genome.equals("hg19") || genome.equals("mm9") || genome.equals("mm10") ){
            IGV.genome = genome;
            setBlacklist(genome);
            this.genome = genome;
        }
        else{
            this.genome = null; 
        }
    }
    
    public static void setBlacklist(String genome) {
        if(genome.equals("hg19")){
            GuavaInput.setBLACKLIST(new File(GuavaInput.getBasenameBLACKLIST() + "hg19.bed"));
        }
        else if(genome.equals("mm9") ){
            GuavaInput.setBLACKLIST(new File(GuavaInput.getBasenameBLACKLIST() + "mm9.bed"));
        }
        else if(genome.equals("mm10") ){
            GuavaInput.setBLACKLIST(new File(GuavaInput.getBasenameBLACKLIST() + "mm10.bed"));
        }
        else if(genome.equals("hg38") ){
            GuavaInput.setBLACKLIST(new File(GuavaInput.getBasenameBLACKLIST() + "mm10.bed"));
        }

    }
    
    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public int getInsertSize() {
        return insertSize;
    }

    public void setInsertSize(int inserSize) {
        this.insertSize = inserSize;
    }

    public String getCutOff() {
        String pqCutOffString = this.getPqCutOff()+"";
        return pqCutOffString;
    }

    public void setCutOff(double pqCutOff) {
        this.setPqCutOff(pqCutOff) ;
        
    }
    
    public void setCutOff(String pqCutOff) {
        try{
            this.setPqCutOff(Double.parseDouble(pqCutOff));
        }
        catch(NumberFormatException e){
            System.err.println("Error: invalid p/q value cutoff \""+pqCutOff+"\"");
        }
        
    }

    public String getPqString() {
        return getValueCode(pqString);
    }

    public void setPqString(String pqString) {
        this.pqString = pqString;
    }

    public String getGenome() {
        return genome;
    }

    @Override
    public String toString() {
        
        String inpuToString =""
                + "R1 fastq : " + this.getR1Fastq()+"\n"
                + "R2 fastq : " +this.getR2Fastq()+"\n"
                + "Bowtie Index: " +this.getBowtieIndex()+"\n"
                + "Genome version: " +this.getGenome()+"\n"
                + "Max genomic hits: " +this.getMaxGenomicHits()+"\n"
                + "Max Insert Size:" +this.getInsertSize()+"\n"
                + "CPU: " +this.getCpu_units()+"\n"
                + "RAM: " +this.getRamMemory()+"\n"
                + this.getPqString()+ " : " +this.getCutOff()+"\n";
                
        return inpuToString; 
    }
    
    public String getbowtieIndexString() {
        String bowtieIndexString = this.getBowtieIndex().getAbsolutePath();
        if(bowtieIndexString.endsWith("ebwt")){
            bowtieIndexString = bowtieIndexString.replaceAll("\\.rev\\.\\d+\\.ebwt", "");
            bowtieIndexString = bowtieIndexString.replaceAll("\\.\\d+\\.ebwt", "");
            return bowtieIndexString;
        }
        else if(bowtieIndexString.endsWith("bt2")){
            bowtieIndexString = bowtieIndexString.replaceAll("\\.rev\\.\\d+\\.bt2", "");
            bowtieIndexString = bowtieIndexString.replaceAll("\\.\\d+\\.bt2", "");
            return bowtieIndexString;
        }
        return null;
    }
    
    private static String getOrgCode(String organismName){
        if(organismName.equals("Human")){
            return "hs";
        }
        else if(organismName.equals("Mouse")){
            return "mm";
        }
        return null;
    }

    private static String getValueCode(String pqValueString){
        if(pqValueString.equals("p")){
            return "-p";
        }  
            return "-q";
    }

    public void setCutadapt(Cutadapt cutadapt) {
        this.cutadapt = cutadapt;
    }

    public Cutadapt getCutadapt() {
        return cutadapt;
    }

    public boolean isTrim() {
        return trim;
    }

    public void setTrim(boolean trim) {
        this.trim = trim;
    }

    /**
     * @return the basenameBLACKLIST
     */
    public static String getBasenameBLACKLIST() {
        return basenameBLACKLIST;
    }

    /**
     * @param aBasenameBLACKLIST the basenameBLACKLIST to set
     */
    public static void setBasenameBLACKLIST(String aBasenameBLACKLIST) {
        basenameBLACKLIST = aBasenameBLACKLIST;
    }

    /**
     * @return the BLACKLIST
     */
    public static File getBLACKLIST() {
        return BLACKLIST;
    }

    /**
     * @param aBLACKLIST the BLACKLIST to set
     */
    public static void setBLACKLIST(File aBLACKLIST) {
        BLACKLIST = aBLACKLIST;
    }

    /**
     * @return the pqCutOff
     */
    public double getPqCutOff() {
        return pqCutOff;
    }

    /**
     * @param pqCutOff the pqCutOff to set
     */
    public void setPqCutOff(double pqCutOff) {
        this.pqCutOff = pqCutOff;
    }

    /**
     * @return the aligner
     */
    public String getAligner() {
        return aligner;
    }

    /**
     * @param aligner the aligner to set
     */
    public void setAligner(String aligner) {
        this.aligner = aligner;
     
    }

    /**
     * @return the mapQ
     */
    public int getMapQ() {
        return mapQ;
    }

    /**
     * @param mapQ the mapQ to set
     */
    public void setMapQ(int mapQ) {
        this.mapQ = mapQ;
    }

    /**
     * @return the genomeObject
     */
    public Genome getGenomeObject() {
        return genomeObject;
    }

    /**
     * @param genomeObject the genomeObject to set
     */
    public void setGenomeObject(Genome genomeObject) {
        this.genomeObject = genomeObject;
    }
    
    
    
    
}
