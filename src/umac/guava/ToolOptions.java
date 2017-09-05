package umac.guava;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mayurdivate
 */
public class ToolOptions {
    static Set<String> optionsKeys = new HashSet<>(Arrays.asList("-R1","-R2","-g","-a",
            "-value","-c","--cutoff","-X","-O","--outdir","-m","-ram","-cpu","-chrM","-chrY"));
    
    static Set<String> compulsoryOptions = new HashSet<>(Arrays.asList("-R1","-R2","-g","-a"));
 
    public boolean isHelpCall(String option){

        if( option.equals("-h") || option.equals("--help") ){
            new ToolOptions().printHelp();
            return true;
        }
        else{
            System.out.println("Option \""+option+"\" not recognized,");
            new ToolOptions().printHelp();
        }
        return false;
    }
   
    public boolean checkFormat(String[] args){
        
        int isOdd = (args.length & 1); // odd or even arguments
        if(isOdd == 1){
            System.out.println("\nError: Invalid command, please use correct command");
            new ToolOptions().printHelp();
            return false;
        }
        else{
            return checkString(args);
        }
    }
    
    public static boolean checkString(String[] args){

    int index = 0 ;
    
        while(index < args.length){
            if(!args[index].startsWith("-") || ! optionsKeys.contains(args[index])){
                System.out.println("Error: Unrecognized option,\n\tPlease check options here '"+ args[index]+ " ' ");
                new ToolOptions().printHelp();
                return false;
            }
            else if (args[index+1].startsWith("-")){
                System.out.println("Error: Please check options here '"+ args[index+1]+ "'");
                new ToolOptions().printHelp();
                return false;
            }

            index = index+2;
        }

        if(!hasCompulsory(args)){
            System.out.println("Error: Input fastq files, Genome index and Genome assembly name required");
            new ToolOptions().printHelp();
            return false;
        }
        
        return isDuplicate(args);
        
    }
    
    public static boolean hasCompulsory(String[] args){
        HashSet<String> options =  new HashSet<String>(Arrays.asList(args));
        
        for(String item : compulsoryOptions){
               if(!options.contains(item)){
                   System.err.println("\nError: "+item+" is missing");
                   return false;
               }
        }
        return true;
    }
    
    public static boolean isDuplicate(String[] args){

        String command = getCommandString(args);
        Pattern cutoff =Pattern.compile("--cutoff");
        Pattern c =Pattern.compile("-c");
        Pattern out =Pattern.compile("-O");
        Pattern outdir =Pattern.compile("--outdir");
        
        Matcher cutoffMatch = cutoff.matcher(command);
        Matcher cMatch = c.matcher(command);
        Matcher outMatch = out.matcher(command);
        Matcher outdirMatch = outdir.matcher(command);
        
        if( (cutoffMatch.find() && cMatch.find()) ||
            (outMatch.find() && outdirMatch.find())  ){
        
            System.out.println("Error: Duplicate options are not allowed in command line options ");
                new ToolOptions().printHelp();
                return false;
        }
        
        return true;
    }
   
    public static String getCommandString(String[] args){

     String command = "";

        for (String inputItem : args){
            command = command+""+inputItem+" ";
        }

        return command;
    }
    
    //cutadapter options
    private static Cutadapt getCutadapt(String commandString){
        if(isTrimCall(commandString)){
            int maxN = getMaxN(commandString);
            int minLen = getMinimumLength(commandString);
            String adapter = getAdapterSequence(commandString);
            double errorRate = getErrorRate(commandString);
            Cutadapt cutadapt = new Cutadapt(adapter, errorRate, maxN, minLen);
            return cutadapt;
        }
        
        return null;
    }
    
    
    private static boolean isTrimCall(String commandString){
        Pattern pattern = Pattern.compile("-trim\\s+([TF])\\s+");
        Matcher regXMatcher = pattern.matcher(commandString);
        
        if(regXMatcher.find()){
            if(regXMatcher.group(1).equals("T")){
                return true;
            }
            return false;
        }
        System.err.println("Error: invalid command !");
        System.err.println("\t valid options -trim [TF]");
        killATACanalyzer();
        return false;
    }
    
    
    private static int getMaxN(String commandString){
        Pattern pattern = Pattern.compile("-N\\s+(\\d+)\\s+");
        Matcher match = pattern.matcher(commandString);
        if(match.find()){
            if(isNumber(match.group(1))){
                return Integer.parseInt(match.group(1));
            }
            System.err.println("Error: invalid number at -N");
            killATACanalyzer();
        }
        return 2;
    }

    private static int getMinimumLength(String commandString){
        Pattern pattern = Pattern.compile("-l\\s+(\\d+)\\s+");
        Matcher match = pattern.matcher(commandString);
        if(match.find()){
            if(isNumber(match.group(1))){
                return Integer.parseInt(match.group(1));
            }
            System.err.println("Error: invalid length at -l >");
            killATACanalyzer();
        }
        return 30;
    }
    
    private static double getErrorRate(String commandString){
        Pattern pattern = Pattern.compile("-e\\s+(.*?)\\s+");
        Matcher match = pattern.matcher(commandString);
        if(match.find()){
            try{
                    double errorRate = Double.parseDouble(match.group(1));
                    if(errorRate >= 0.0 && errorRate < 1){
                        return errorRate;
                    }
                    System.err.println("Error: Invalid error rate");
                    System.err.println("\t error rate should 0<= errorate <1");
                    killATACanalyzer();

            }
            catch(NumberFormatException e){
                    System.err.println("Error: Invalid error rate");
                    killATACanalyzer();
            }
            
            
            System.err.println("Error: invalid length at -l >");
            killATACanalyzer();
        }
        return 30;
    }
    
    private static String getAdapterSequence(String commandString){
        Pattern pattern = Pattern.compile("-s\\s+(\\w+)\\s+");
        Matcher match = pattern.matcher(commandString);
        if(match.find()){
                Pattern seqDNA = Pattern.compile("-s\\s+([ATGC]+)\\s+");
                Matcher matchDNA = pattern.matcher(commandString);
                if(matchDNA.find()){
                    return match.group(1);
                }
            System.err.println("Error: invalid length at -l >");
            killATACanalyzer();
        }
        System.out.println("Using Nextera Adapter Sequence");
        return "CTGTCTCTTATACACATCT";
    }
    
    /****************************************************
     * 
     * very important method of this class of input object 
     * construction using command typed by user
     * 
     * *************************************************/
    
    public static GuavaInput getInput(String[] args) {
        
        String commandLineString =  getCommandString(args);

        // build input object
        GuavaInput commandLineInput = new GuavaInput();
        
            commandLineInput.setR1Fastq(getInputR1FastqFile(commandLineString));
            commandLineInput.setR2Fastq(getInputR2FastqFile(commandLineString));
            commandLineInput.setBowtieIndex(getGenomeIndex(commandLineString));
            commandLineInput.setOrganism(getOrganismName(commandLineString));
            commandLineInput.setGenome(getGenomeAssembly(commandLineString));
            
            commandLineInput.setPqString(getPQvalue(commandLineString));
            commandLineInput.setCutOff(getPQCutOff(commandLineString));
            commandLineInput.setInsertSize(getXsize(commandLineString));
            commandLineInput.setOutputFolder(getOutputdir(commandLineString));
            commandLineInput.setMaxGenomicHits(getHits(commandLineString));
            commandLineInput.setRamMemory(getRam(commandLineString));
            commandLineInput.setCpu_units(getCpus(commandLineString));
            commandLineInput.setChromosome(getChromosomes(commandLineString));
            
        
        if(!validateInput(commandLineInput)){
            System.err.println("Error: Invalid input(s)");
            killATACanalyzer();
            return null;
        }
        else{
             if(isTrimCall(commandLineString)){
                Cutadapt cutadaptPartial  = getCutadapt(commandLineString);
                Cutadapt cutadapt = Cutadapt.getCutadapt(commandLineInput, cutadaptPartial.getAdapter(), 
                            cutadaptPartial.getErrorRate(), cutadaptPartial.getMaxNs(), cutadaptPartial.getMinLength());
                commandLineInput.setTrim(true);
                commandLineInput.setCutadapt(cutadapt);
            } 
        
        }
        return commandLineInput;
    }
    
    private static void killATACanalyzer(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for(StackTraceElement ste : stackTrace){
            System.out.println(ste);
        }
        System.err.println("Please rectify above error in command");
        System.err.println("stopping ... ");
        System.exit(-1);
    }

    // Methods to get each parameters or their default values
    
    private static File getInputR1FastqFile(String commandString){
        Pattern pattern = Pattern.compile("-R1 (.*?)\\s+");
        Matcher regXMatcher = pattern.matcher(commandString);
        if(regXMatcher.find()){
            return new File(regXMatcher.group(1));
        }
        System.err.println("Error: R1 fastq file not supplied");
        killATACanalyzer();
        return null;
    }
    
    private static File getInputR2FastqFile(String commandString){
        Pattern pattern = Pattern.compile("-R2 (.*?)\\s+");
        Matcher regXMatcher = pattern.matcher(commandString);
        if(regXMatcher.find()){
            return new File(regXMatcher.group(1));
        }
        System.err.println("Error: R2 fastq file not supplied");
        killATACanalyzer();
        return null;
    }

    private static String getGenomeIndex(String commandString){
        Pattern pattern = Pattern.compile("-g (.*?)\\s+");
        Matcher regXMatcher = pattern.matcher(commandString);
        if(regXMatcher.find()){
            return regXMatcher.group(1);
        }
        System.err.println("Error: genome not supplied");
        killATACanalyzer();
        return null;
    }

    private static String getPQvalue(String commandString){
        
        Pattern pattern = Pattern.compile("-value (.*?)\\s+");
        Matcher regXMatcher = pattern.matcher(commandString);
        if(regXMatcher.find()){
            if(regXMatcher.group(1).endsWith("p") || regXMatcher.group(1).endsWith("q") ){
                    return regXMatcher.group(1);
            }
            else{
                    System.err.println("Error: Invalid use of -value option here \""+regXMatcher.group(1)+"\"");
                    killATACanalyzer();
                    return null;
            }
        }
        
        return "q"; 
    }
    
    private static double getPQCutOff(String commandString){
        
        Pattern pattern1 = Pattern.compile("-c (.*?)\\s+");
        Pattern pattern2 = Pattern.compile("--cutoff (.*?)\\s+");
        Matcher regXMatcher1 = pattern1.matcher(commandString);
        Matcher regXMatcher2 = pattern2.matcher(commandString);
        try{
                if(regXMatcher1.find()){
                    return Double.parseDouble(regXMatcher1.group(1));
                }
                else if(regXMatcher2.find()){
                    return Double.parseDouble(regXMatcher2.group(1));
                }
        }
        catch(NumberFormatException e){
            System.err.println("Error: invalid p/q value cutoff, must be of double data type");
            killATACanalyzer();
            return 0;
        }
       return 0.05; // returning default value

    }

    private static int getXsize(String commandString){
        
        Pattern pattern = Pattern.compile("-X (.*?)\\s+");
        Matcher regXMatcher = pattern.matcher(commandString);
        if(regXMatcher.find()){
            if(isNumber(regXMatcher.group(1))){
                return Integer.parseInt(regXMatcher.group(1));
            }
            else{
                System.err.println("Error: invalid max. insert size for here "+regXMatcher.group(1));
                killATACanalyzer();
                return -1;
            }
        }
        return 2000; // default
    }

    private static String getOutputdir(String commandString){
        
        Pattern pattern1 = Pattern.compile("-O (.*?)\\s+");
        Pattern pattern2 = Pattern.compile("--outdir (.*?)\\s+");
        Matcher regXMatcher1 = pattern1.matcher(commandString);
        Matcher regXMatcher2 = pattern2.matcher(commandString);

        String outdir = null; 
        
        if(regXMatcher1.find()){
            
            outdir = regXMatcher1.group(1);
        }
        else if(regXMatcher2.find()){
            outdir = regXMatcher2.group(1);
        }
        
        if(outdir != null ){
            File userDir = new File(outdir);
            if(!userDir.exists()){
                userDir.mkdir();
            }
            return outdir; 
        }
        
        return GUAVA.getPwd(); //default
    }

    private static int getHits(String commandString){
        
        Pattern pattern = Pattern.compile("-m (.*?)\\s+");
        Matcher regXMatcher = pattern.matcher(commandString);

        if(regXMatcher.find()){
            if(isNumber(regXMatcher.group(1))){
                return Integer.parseInt(regXMatcher.group(1));
            }
            else{
                System.err.println("Error: invalid use of -m for here "+regXMatcher.group(1));
                killATACanalyzer();
                return -1;
            }
        }
 
        return 1; //default value
    }

    private static int getRam(String commandString){
        
        Pattern pattern = Pattern.compile("-ram (.*?)\\s+");
        Matcher regXMatcher = pattern.matcher(commandString);
        if(regXMatcher.find()){
            if(isNumber(regXMatcher.group(1))){
                return Integer.parseInt(regXMatcher.group(1));
            }
            else{
                System.err.println("Error: invalid value RAM here "+regXMatcher.group(1));
                killATACanalyzer();
                return -1;
            }
        }
        return 1; // default value
    }

    private static int getCpus(String commandString){
        
        Pattern pattern = Pattern.compile("-cpu (.*?)\\s+");
        Matcher regXMatcher = pattern.matcher(commandString);
        if(regXMatcher.find()){
            if(isNumber(regXMatcher.group(1))){
                return Integer.parseInt(regXMatcher.group(1));
            }
            else{
                System.err.println("Error: invalid -cpu value for here "+regXMatcher.group(1));
                killATACanalyzer();
                return -1;
            }
        }
        return 1;
    }
    
    private static String getOrganismName(String commandString){
        String organism = getGenomeAssembly(commandString);
        if(organism.equals("hg18") || organism.equals("hg19") || organism.equals("hg38")  ){
            return "Human";
        }
        else if(organism.equals("mm9") || organism.equals("mm10")  ){
            return "Mouse";
        }
        
        killATACanalyzer();
        return null;
    }

    private static String getGenomeAssembly(String commandString){
        
        Pattern pattern1 = Pattern.compile("-a (.*?)\\s+");
        Matcher regXMatcher1 = pattern1.matcher(commandString);
        if(regXMatcher1.find()){
            return regXMatcher1.group(1);
        }
        killATACanalyzer();
        return null;
    }

    private static String getChromosomes(String commandString){
        
        Pattern patternChrM = Pattern.compile("-chrM (.*?)\\s+");
        Pattern patternChrY = Pattern.compile("-chrY (.*?)\\s+");
        
        Matcher regXMatcherChrM = patternChrM.matcher(commandString);
        Matcher regXMatcherChrY = patternChrY.matcher(commandString);
        
        String chrFilter = "chrM "; // default filter
        
        if(regXMatcherChrM.find()){
            if(regXMatcherChrM.group(1).equals("T")){
                chrFilter = "chrM "; //default filter reassignment
            }
            else if(regXMatcherChrM.group(1).equals("F")){
                chrFilter = ""; // remove chrM filter
            }
            else {
                System.err.println("Error: Invalid use of -chrM here \""+regXMatcherChrM.group(1)+"\"");
                killATACanalyzer();
                return null;
            }

        }
        
        if(regXMatcherChrY.find()){
            if(regXMatcherChrY.group(1).equals("T")){
                chrFilter = chrFilter + "chrY ";
                return chrFilter.trim();
            }
            else if(regXMatcherChrY.group(1).equals("F")){
                // do nothing and return as it is
                return chrFilter.trim();
            }
            else{
                System.err.println("Error: Invalid use of -chrY here \""+regXMatcherChrY.group(1)+"\"");
                killATACanalyzer();
                return null;
            }
        }
        
        return chrFilter.trim();
    }
    
/**************
 * 
 * validate file existence 
  */



    private static boolean validateInput(GuavaInput atacSeq){
        boolean flag = true;


        
//------ Validation of compulsory parameters --------------
        if(!atacSeq.getR1Fastq().exists()){ 
            System.err.println("File does not exists: "+atacSeq.getR1Fastq());
        }
        if(!atacSeq.getR2Fastq().exists()){ 
            flag = false;
            System.err.println("File does not exists: "+atacSeq.getR2Fastq());

        }
        if(!atacSeq.getBowtieIndex().exists()){ 
            flag = false;
            System.err.println("File does not exists: "+atacSeq.getbowtieIndexString());
        }
        if(!atacSeq.getOutputFolder().getParentFile().exists()){ 
            flag = false;
            System.err.println("Can not create output dir here : "+atacSeq.getOutputFolder().getAbsolutePath());
        }
        
        if(!flag){
            // kill if any of the above file does not exists
            killATACanalyzer();
        }
// -------------------------------------------------------

        try{
                Double.parseDouble(atacSeq.getCutOff());
            }
        catch(NumberFormatException e){
                flag = false;  
                System.err.println("invalid input for pvalue");
            }
        return flag;
    }
    
    private static boolean isNumber(String numberString){
        try  {  
            Integer.parseInt(numberString);
            return true;
        }
        catch(NumberFormatException e){
            System.out.println(numberString+" : "+"is not a number ");
            new ToolOptions().printHelp();
            System.exit(-1);
        }
        return false;
    }
    
    private void printHelp(){

       System.out.println("Usage: ");
       System.out.println("java -jar GUAVA.jar [options]* ");
       System.out.println("\t"+"-R1"+"\t"+"path to the fastq file containing upstream mates");
       System.out.println("\t"+"-R2"+"\t"+"path to the fastq file containing downstream mates");
       System.out.println("\t"+"-g"+"\t"+"path to the bowtie genome index");
       System.out.println("\t"+"-a"+"\t"+"Genome assembly");
       System.out.println("\t"+"  "+"\t"+"Human: [ hg18 | hg19 | hg38 ]");
       System.out.println("\t"+"  "+"\t"+"Mouse: [ mm9  | mm10 ]");
       System.out.println("Optional:");
       System.out.println("\t"+"-value <p|q> "+"\t"+"p / q pavlue for MACS2 peak filtering ");
       System.out.println("\t"+"  "+"\t"+"default: q");
       System.out.println("\t"+"-c | --cutoff <0.05 | 5E-2>"+"\t"+"p/q value cutoff");
       System.out.println("\t"+"  "+"\t"+"default: 0.05");
       System.out.println("\t"+"-X <int>"+"\t"+"Maximum insert size for paired end alignment");
       System.out.println("\t"+"  "+"\t"+"default: 2000 ");
       System.out.println("\t"+"-O | --outdir <path>"+"\t"+"path for output directory ");
       System.out.println("\t"+"  "+"\t"+"default: current directory ");
       System.out.println("\t"+"-m <int>"+"\t"+"Maximum number of genomic hits");
       System.out.println("\t"+"  "+"\t"+"default: 1");
       System.out.println("\t"+"-ram <int>"+"\t"+"RAM memory in GB ");
       System.out.println("\t"+"  "+"\t"+"default: 1 GB");
       System.out.println("\t"+"-cpu "+"\t"+"Number of cpus");
       System.out.println("\t"+"  "+"\t"+"default: 1");
       System.out.println("\t"+"-chrM <T/F>"+"\t"+"Filter chromosome M reads True / False");
       System.out.println("\t"+"  "+"\t"+"default: T");
       System.out.println("\t"+"-chrY <T/F>"+"\t"+"Filter chromosome Y reads True / False");
       System.out.println("\t"+"  "+"\t"+"default: F");
       
    }

    
}
