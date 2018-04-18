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
package umac.guava.commandline;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import umac.guava.Cutadapt;
import umac.guava.GUAVA;
import umac.guava.GuavaInput;
import umac.guava.Input;

/**
 *
 * @author mayurdivate
 */
public class GuavaCommand extends CommandlineTool{
    
    
    
    // Guvava options
    private static Set<String> guavaOptions = new HashSet<>(Arrays.asList(
            "-R1",
            "-R2",
            "-g",
            "-a",
            "-v",
            "-C","--cutoff",
            "-X",
            "-O","--outdir",
            "-m",
            "-ram",
            "-cpu",
            "-chrs",
            "--aligner",
            "--trim",
            "-n",
            "--adapter",
            "-e"));
    
    private static Set<String> guavaCompulsoryOptions = new HashSet<>(Arrays.asList("-R1","-R2","-g","-a"));
    
    private File r1Fastq;
    private File r2Fastq;
    private File bowtieIndex;
    private File outputFolder = GUAVA.getPwDir().getAbsoluteFile();
    private int maxGenomicHits = 1;
    private int cpu_units = 1;
    private int insertSize = 2000;
    private int ramMemory = 1;
    private String Organism;
    private String genome;
    private String chromosome;
    private double pqCutOff = 0.05;
    private String pqString = "q";
    private Cutadapt cutadapt;
    private boolean trim = false;
//    private String aligner ;
    private int mapQ = 10;
    
    
    @Override
    public boolean isCorrectCommand(Command command) {
        int index = 0 ;
        String[] options = command.getOptions();
        
        while(index < options.length){
            if(!options[index].startsWith("-") || ! guavaOptions.contains(options[index])){
                System.out.println("Error: Unrecognized option,\n\tPlease check options here '"+ options[index]+ "' ");
                this.printHelpMessage();
                return false;
            }
            else if (options[index+1].startsWith("-")){
                System.out.println("Error: Please check options here '"+ options[index+1]+ "'");
                this.printHelpMessage();
                return false;
            }
            index = index+2;
        }
        
        if(!hasCompulsoryOptions(command)){
            System.out.println("Error: Input fastq files, Genome index and Genome assembly name required");
            this.printHelpMessage();
            return false;
        }
        
        return hasDuplicates(command);
    }

    @Override
    public boolean hasCompulsoryOptions(Command command) {
        HashSet<String> options =  new HashSet<String>(Arrays.asList(command.getOptions()));
        
        for(String item : getGuavaCompulsoryOptions()){
               if(!options.contains(item)){
                   System.err.println("\nError: "+item+" is missing");
                   return false;
               }
        }
        return true;
    }

    public boolean hasCompulsoryCutadapterOptions(Command command) {
        HashSet<String> options =  new HashSet<String>(Arrays.asList(command.getOptions()));
        
        for(String item : getGuavaCompulsoryOptions()){
               if(!options.contains(item)){
                   System.err.println("\nError: "+item+" is missing");
                   return false;
               }
        }
        return true;
    }
    
    @Override
    public boolean hasDuplicates(Command command) {
       String commandString = getCommandString(command.getOptions());
        Pattern cutoff =Pattern.compile("--cutoff");
        Pattern c =Pattern.compile("-C");
        Pattern out =Pattern.compile("-O");
        Pattern outdir =Pattern.compile("--outdir");
        
        Matcher cutoffMatch = cutoff.matcher(commandString);
        Matcher cMatch = c.matcher(commandString);
        Matcher outMatch = out.matcher(commandString);
        Matcher outdirMatch = outdir.matcher(commandString);
        
        if( (cutoffMatch.find() && cMatch.find()) ||
            (outMatch.find() && outdirMatch.find())  ){
            System.out.println("Error: Duplicate options are not allowed in command line options ");
                this.printHelpMessage();
                return false;
        }
        
        return true;
    }

    @Override
    public boolean isHelpCall(Command command) {
        String[] options = command.getOptions();
        if(options.length == 1 && (options[0].equals("--help") || options[0].equals("-h"))){
            return true;
        }
        return false;
        
    }

    @Override
    public Input getInput(CommandlineTool toolCommand) {
        
        if(toolCommand instanceof GuavaCommand){
            
            GuavaCommand guavaCommand = (GuavaCommand) toolCommand;
            
            // create guava input object for guava
            GuavaInput guavaInput = new GuavaInput();

            guavaInput.setR1Fastq(guavaCommand.getR1Fastq());
            guavaInput.setR2Fastq(guavaCommand.getR2Fastq());
            
            guavaInput.setGenome(guavaCommand.getGenome());                 //genome
            guavaInput.setBowtieIndex(guavaCommand.getBowtieIndex());
            guavaInput.setAligner(guavaCommand.getAligner());
            guavaInput.setInsertSize(guavaCommand.getInsertSize());
            guavaInput.setMaxGenomicHits(guavaCommand.getMaxGenomicHits()); 
            guavaInput.setMapQ(guavaCommand.getMapQ());
            
            guavaInput.setOutputFolder(guavaCommand.getOutputFolder());
            
            guavaInput.setCpu_units(guavaCommand.getCpu_units());
            guavaInput.setRamMemory(guavaCommand.getRamMemory());

            guavaInput.setChromosome(guavaCommand.getChromosome());     //chr
            guavaInput.setBlacklistFile(guavaInput.getGenome());        //blacklist
            guavaInput.setPqCutOff(guavaCommand.getPqCutOff());
            guavaInput.setPqString(guavaCommand.getPqString());
            guavaInput.setTrim(guavaCommand.isTrim());
            if(guavaInput.isTrim()){
                
                Cutadapt cutadapt = Cutadapt.getCutadapt(guavaInput, 
                        guavaCommand.getCutadapt().getAdapter(),
                        guavaCommand.getCutadapt().getErrorRate(),
                        guavaCommand.getCutadapt().getMaxNs(),
                        guavaCommand.getCutadapt().getMinLength());
                guavaInput.setCutadapt(cutadapt);
            }
           return guavaInput;
        }
        else{
            System.out.println("ToolCommand is not a GuavaCommand");
            return null;
        }
        
    }
    
    //cutadapter options
    private static Cutadapt getCutadapt(File r1, File r2, Command command){
       

        // cut adpter relater patterns 
        String maxNPattern = "-n\\s+(\\d+)\\s+";
        String minLenPattern = "-L\\s+(\\d+)\\s+";
        String ePattern = "-e\\s+((\\d+)\\.(\\d+))\\s+";
        String adapterPattern = "--adapter\\s+([ATGC]*?)\\s+";
        
        
        int maxN    = 2;
        int minLen  = 30;
        String adapter = "CTGTCTCTTATACACATCT"; // Nextra adpater
        double errorRate = 0.1 ;
            
        
            if(command.isUsed(maxNPattern)){            
                maxN =  command.getIntParameter(maxNPattern);
            }
            if(command.isUsed(minLenPattern)){            
                minLen =  command.getIntParameter(minLenPattern);
            }
            if(command.isUsed(adapterPattern)){            
                adapter =  command.getStringParameter(adapterPattern);
            }
            if(command.isUsed(ePattern)){            
                errorRate = command.getDoubleParameter(ePattern);
            }
            if(command.isUsed(adapterPattern)){            
                adapter = command.getStringParameter(adapterPattern);
            }
            
            Cutadapt cutadapt = Cutadapt.getCutadapt(r1, r2, adapter, errorRate, maxN, minLen);
            return cutadapt;
        
    }
    
    
    private static boolean isTrimCall(Command command){
        
        String trimPattern = "-trim\\s+(T)\\s+";
        return command.isUsed(trimPattern);
    }
    
        
    @Override
    public void printHelpMessage() {
       System.out.println("java -jar GUAVA.jar guava [options]* ");
       System.out.println(getHelpMessage());

    }
    
    @Override
    public String getHelpMessage() {
        String  guavaHelpMessage = ""+"\n";
       
       guavaHelpMessage = guavaHelpMessage +"options"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-R1"+      "\t"+"path to the fastq file containing upstream mates"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-R2"+      "\t"+"path to the fastq file containing downstream mates"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-g"+       "\t"+"path to the bowtie genome index"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-a"+       "\t"+"Genome assembly"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"  "+       "\t"+"e.g. hg19, mm10"+"\n";
       guavaHelpMessage = guavaHelpMessage +"cutadapt realated:"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"--trim"+   "\t"+"[TF] default:F do not perform adapter trimming"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-n"+       "\t"+"INT Discard read if contains Ns more than n "+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"  "+       "\t"+"default: 3"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-L"+       "\t"+"INT Discrad adapter trimmed read is shorter than L"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"  "+       "\t"+"default: 30"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-e"+       "\t"+"0-1 Error rate to identify adapter sequence with mismatches"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"  "+       "\t"+"default: 0.1"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"--adapter"+"\t"+"Adapter sequence Default: Nextra XT adapter"+"\n";
       guavaHelpMessage = guavaHelpMessage +"Optional:"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-v"+      "\t"+"p / q value for MACS2 peak filtering Default: q"+"\n";
       guavaHelpMessage = guavaHelpMessage +" "+"-C | --cutoff"+"\t"+"0 - 0.05 value cutoff. defalut 0.05"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-X"+       "\t"+"INT Maximum insert size for paired end alignment"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"  "+       "\t"+"default: 2000 "+"\n";
       guavaHelpMessage = guavaHelpMessage +" "+"-O | --outdir"+"\t"+"path for output directory "+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"  "+       "\t"+"default: current directory "+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-m"+       "\t"+"INT If used with bowtie then Maximum number of genomic hits. default: 1"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"  "+       "\t"+"INT If used with bowtie2 then minimum mapping quality. default: 30"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-ram"+     "\t"+"INT RAM memory in GB. Default: 1"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-cpu"+     "\t"+"INT Number of cpus. Default 1"+"\n";
       guavaHelpMessage = guavaHelpMessage +"\t"+"-chrs"+     "\t"+"comma separeted list of chromosomes e.g. chrM,chrY"+"\n";
       guavaHelpMessage = guavaHelpMessage + "\n";
       
       return guavaHelpMessage;
    }
    
    public static GuavaCommand getGuavaCommand(Command command){
        GuavaCommand guavaCommand =  new GuavaCommand();
        String userCommand =  command.getCommand();
        
        userCommand =  userCommand.replace(".*gdiff", command.getCommand());
        
        String r1Pattern = "-R1\\s+(.*?)\\s+";
        String r2Pattern = "-R2\\s+(.*?)\\s+";
        String genomeIndexPattern = "-g\\s+(.*?)\\s+";
        String genomeBuildPattern = "-a\\s+(.*?)\\s+";
        String valuePattern  = "-v\\s+([pq])\\s+";
        String cutoffPattern = "((-C)|(--cutoff))\\s+((\\d+)\\.(\\d+))\\s+";
        String insertSizePattern = "-X\\s+(\\d+)\\s+";
        String outdirPattern = "((-O)|(--outdir))\\s+(.*?)\\s+";
        String maxGenomicHitsPattern = "-m\\s+(\\d+)\\s+";
        String mapQPattern = "-m\\s+(\\d+)\\s+";
        String ramPattern = "-ram\\s+(\\d+)\\s+";
        String cpuPattern = "-cpu\\s+(\\d+)\\s+";
        String chrsPattern = "-chrs\\s+(.*?)\\s+";
        

        // R1 and R2 fastq files  
        if(command.isUsed(r1Pattern)){
            File r1     =  command.getFileParameter(r1Pattern);
            guavaCommand.setR1Fastq(r1);
        }

        if(command.isUsed(r2Pattern)){
            File r2     =  command.getFileParameter(r2Pattern);
            guavaCommand.setR2Fastq(r2);
        }
        
        // genome
        if(command.isUsed(genomeIndexPattern)){
            File genomeIndex     =  command.getFileParameter(genomeIndexPattern);
            guavaCommand.setBowtieIndex(genomeIndex);
        }
        
        //genome build
        if(command.isUsed(genomeBuildPattern)){
            String build     =  command.getStringParameter(genomeBuildPattern);
            guavaCommand.setGenome(build);
        }
        
        // value
        if(command.isUsed(valuePattern)){
            String value =  command.getStringParameter(valuePattern);
            guavaCommand.setPqString(value);
        }
        
        if(command.isUsed(cutoffPattern)){
            double cutoff =  command.getDoubleParameter(cutoffPattern,4);
            guavaCommand.setPqCutOff(cutoff);
        }
        
        // insert size
        if(command.isUsed(insertSizePattern)){
            int insertSize  =  command.getIntParameter(insertSizePattern);
            guavaCommand.setInsertSize(insertSize);
        }
        
        if(command.isUsed(outdirPattern)){
            File outdir =  command.getFileParameter(outdirPattern,4);
            guavaCommand.setOutputFolder(outdir);
        }else{
            System.out.println("umac.guava.commandline.GuavaCommand.getGuavaCommand()");
            System.out.println(GUAVA.getPwDir().getAbsolutePath());
            
            guavaCommand.setOutputFolder(GUAVA.getPwDir());
        }
        
        if(command.isUsed(maxGenomicHitsPattern)){
            int mhits =  command.getIntParameter(maxGenomicHitsPattern);
            guavaCommand.setMaxGenomicHits(mhits);
        }
        
        if(command.isUsed(mapQPattern)){
            int mapq =  command.getIntParameter(mapQPattern);
            guavaCommand.setMapQ(mapq);
        }
        
        if(command.isUsed(ramPattern)){
            int ram  =  command.getIntParameter(ramPattern);
            guavaCommand.setRamMemory(ram);
        }
        
        if(command.isUsed(cpuPattern)){
            int cpu  =  command.getIntParameter(cpuPattern);
            guavaCommand.setCpu_units(cpu);
        }
        
   //________________________________________________________________________
   //________________________________________________________________________
   //________________________________________________________________________
        //  chromosome filtering parameter
        if(command.isUsed(chrsPattern)){
            String chrs  =  command.getStringParameter(chrsPattern);
            if(chrs != null ){
                 guavaCommand.setChromosome(chrs);
            }
        }

    //________________________________________________________________________
   //________________________________________________________________________
   //________________________________________________________________________
        
        // cutadapt paramters 
        if(isTrimCall(command)){
            Cutadapt cutadapt = getCutadapt(guavaCommand.getR1Fastq(), guavaCommand.getR2Fastq(), command);
            guavaCommand.setTrim(true);
            guavaCommand.setCutadapt(cutadapt);
        }
        
     //________________________________________________________________________
   //________________________________________________________________________
   //________________________________________________________________________
          
        return guavaCommand;
        
    }
    
    @Override
    public boolean validateInput(Input input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the guavaOptions
     */
    public static Set<String> getGuavaOptions() {
        return guavaOptions;
    }

    /**
     * @param aGuavaOptions the guavaOptions to set
     */
    public static void setGuavaOptions(Set<String> aGuavaOptions) {
        guavaOptions = aGuavaOptions;
    }

    /**
     * @return the guavaCompulsoryOptions
     */
    public static Set<String> getGuavaCompulsoryOptions() {
        return guavaCompulsoryOptions;
    }

    /**
     * @param aGuavaCompulsoryOptions the guavaCompulsoryOptions to set
     */
    public static void setGuavaCompulsoryOptions(Set<String> aGuavaCompulsoryOptions) {
        guavaCompulsoryOptions = aGuavaCompulsoryOptions;
    }

    /**
     * @return the r1Fastq
     */
    public File getR1Fastq() {
        return r1Fastq;
    }

    /**
     * @param r1Fastq the r1Fastq to set
     */
    public void setR1Fastq(File r1Fastq) {
        this.r1Fastq = r1Fastq;
    }

    /**
     * @return the r2Fastq
     */
    public File getR2Fastq() {
        return r2Fastq;
    }

    /**
     * @param r2Fastq the r2Fastq to set
     */
    public void setR2Fastq(File r2Fastq) {
        this.r2Fastq = r2Fastq;
    }

    /**
     * @return the bowtieIndex
     */
    public File getBowtieIndex() {
        return bowtieIndex;
    }

    /**
     * @param bowtieIndex the bowtieIndex to set
     */
    public void setBowtieIndex(File bowtieIndex) {
        this.bowtieIndex = bowtieIndex;
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
     * @return the maxGenomicHits
     */
    public int getMaxGenomicHits() {
        return maxGenomicHits;
    }

    /**
     * @param maxGenomicHits the maxGenomicHits to set
     */
    public void setMaxGenomicHits(int maxGenomicHits) {
        this.maxGenomicHits = maxGenomicHits;
        this.mapQ = maxGenomicHits;
    }

    /**
     * @return the cpu_units
     */
    public int getCpu_units() {
        return cpu_units;
    }

    /**
     * @param cpu_units the cpu_units to set
     */
    public void setCpu_units(int cpu_units) {
        this.cpu_units = cpu_units;
    }

    /**
     * @return the insertSize
     */
    public int getInsertSize() {
        return insertSize;
    }

    /**
     * @param insertSize the insertSize to set
     */
    public void setInsertSize(int insertSize) {
        this.insertSize = insertSize;
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

    /**
     * @return the Organism
     */
    public String getOrganism() {
        
        if(this.getGenome() != null){
            if(this.getGenome().equals("hg19") || this.getGenome().equals("hg38") || this.getGenome().equals("hg18")){
                return "Human";
            }
            else if(this.getGenome().equals("mm10") || this.getGenome().equals("mm9")){
                return "Mouse";
            }
        }
        return Organism;
    }

    /**
     * @param Organism the Organism to set
     */
    public void setOrganism(String Organism) {
        
        this.Organism = Organism;
    }

    /**
     * @return the genome
     */
    public String getGenome() {
        return genome;
    }

    /**
     * @param genome the genome to set
     */
    public void setGenome(String genome) {
        this.genome = genome;
    }

    /**
     * @return the chromosome
     */
    public String getChromosome() {
        return chromosome;
    }

    /**
     * @param chromosome the chromosome to set
     */
    public void setChromosome(String chromosome) {
        chromosome =  chromosome.replaceAll(",", " ");
        this.chromosome = chromosome;
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
     * @return the pqString
     */
    public String getPqString() {
        return pqString;
    }

    /**
     * @param pqString the pqString to set
     */
    public void setPqString(String pqString) {
        this.pqString = pqString;
    }

    /**
     * @return the cutadapt
     */
    public Cutadapt getCutadapt() {
        return cutadapt;
    }

    /**
     * @param cutadapt the cutadapt to set
     */
    public void setCutadapt(Cutadapt cutadapt) {
        this.cutadapt = cutadapt;
    }

    /**
     * @return the trim
     */
    public boolean isTrim() {
        return trim;
    }

    /**
     * @param trim the trim to set
     */
    public void setTrim(boolean trim) {
        this.trim = trim;
    }

    /**
     * @return the aligner
     */
    public String getAligner() {
        if(this.getBowtieIndex() != null){
            String extension = this.getBowtieIndex().getName().replaceAll(".*\\.", "");
            if(extension.equals("ebwt")){
                return "bowtie";
            }
            else if(extension.equals("bt2")){
                return "bowtie2";
            }
        }
        return null;
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
        this.maxGenomicHits = mapQ;
        this.mapQ = mapQ;
    }

    @Override
    public String toString() {
        String guava = "\n";
        
        guava = guava + " R1:\t\t"+this.getR1Fastq().getName()+"\n";
        guava = guava + " R2:\t\t"+this.getR2Fastq().getName()+"\n";
        guava = guava + " aligner:\t"+this.getAligner()+"\n";
        guava = guava + " index:\t\t"+this.getBowtieIndex().getName()+"\n";
        guava = guava + " genome:\t"+this.getGenome()+"\n";
        guava = guava + " organism:\t"+this.getOrganism()+"\n";
        
        guava = guava + " cutadapt:\t\n"+this.getCutadapt()+"\n";
        
        guava = guava + " value:\t\t"+this.getPqString()+"\n";
        guava = guava + " cutoff:\t"+this.getPqCutOff()+"\n";
        guava = guava + " insert size:\t"+this.getInsertSize()+"\n";
        guava = guava + " outdir:\t"+this.getOutputFolder().getAbsoluteFile().getAbsolutePath()+"\n";
        guava = guava + " hits:\t\t"+this.getMaxGenomicHits()+"\n";
        guava = guava + " mapQ:\t\t"+this.getMapQ()+"\n";
        guava = guava + " ram:\t\t"+this.getRamMemory()+"\n";
        guava = guava + " cpu:\t\t"+this.getCpu_units()+"\n";
        guava = guava + " chr:\t\t"+this.getChromosome()+"\n";
        
        
        return guava;
    }
    
    
    
        
        
}
