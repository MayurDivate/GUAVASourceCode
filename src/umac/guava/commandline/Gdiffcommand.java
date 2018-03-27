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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import umac.guava.Genome;
import umac.guava.Input;
import umac.guava.diff.GdiffInput;
import umac.guava.diff.DifferentialInputFile;
import umac.guava.diff.DifferentialOutputFiles;

/**
 *
 * @author mayurdivate
 */

public class Gdiffcommand extends CommandlineTool{
    
    private double foldchange = 2;
    private double pvalue = 0.05;
    private String method = "deseq2";
    private String genome = "hg19";
    private int upstreamDistance =  1000;
    private int downstreamDistance =  1000;
    private File outdir;
    private File inFile;
    private String projectName;
   
    // gdiff options 
    private static Set<String> gdiffOptions = new HashSet<>(Arrays.asList("-fc","-p","-m","-g","-o","-u","-d","-files","-n"));
    private static Set<String> gdiffCompulsoryOptions = new HashSet<>(Arrays.asList("-files","-n","-o"));
    
    /*
    This method will return if all compulsory parameters, no duplicates and correct format detected 
     */
    
    @Override
    public boolean isCorrectCommand(Command command) {
        int index = 0 ;
        String[] options = command.getOptions();
        
        while(index < options.length){
            if(!options[index].startsWith("-") || ! gdiffOptions.contains(options[index])){
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
        HashSet<String> optionsHashSet =  new HashSet<String>(Arrays.asList(command.getOptions()));

        for(String item : getGdiffCompulsoryOptions()){
               if(!optionsHashSet.contains(item)){
                   System.err.println("\nError: "+item+" is missing");
                   return false;
               }
        }
        return true;
    }

    @Override
    public boolean hasDuplicates(Command command) {
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
        if(toolCommand instanceof Gdiffcommand){
            
            Gdiffcommand gdiff = (Gdiffcommand) toolCommand;
            // get ouput files for differential analysis 
            DifferentialOutputFiles diffOutputFiles = DifferentialOutputFiles.getDifferentialOutputFiles(gdiff.getOutdir());
            
            // build ArrayList of differential input file 
            ArrayList<DifferentialInputFile> differentialInputFiles = gdiff.getDifferentialInputFiles();
            Genome genome =  Genome.getGenomeObject("hg19");
                    
            GdiffInput atacSeqDiffInput = new GdiffInput(gdiff.getOutdir(), 
                   diffOutputFiles,
                   gdiff.getFoldchange(),
                   gdiff.getPvalue(),
                   differentialInputFiles,
                   gdiff.getProjectName(),
                   genome,
                   gdiff.getUpstreamDistance(),
                   gdiff.getDownstreamDistance(), 
                    "DESeq2", 2);
           
           return atacSeqDiffInput;

            
        }
        else{
            System.out.println("Is not a GdiffCommand");
            return null;
        }
        
        
    }
    
    private ArrayList<DifferentialInputFile> getDifferentialInputFiles(){
        
        ArrayList<DifferentialInputFile> dfInputFiles =  new ArrayList<>();
        
        if(this.isInputFile()){

            Pattern inputFileFormat = Pattern.compile("^(.*?)\\t(\\d+)\\t((control)|(treatment))");
            try {
                FileReader fr = new FileReader(this.getInFile());
                BufferedReader br =  new BufferedReader(fr);
                String line = "";

                while((line = br.readLine()) != null){
                    Matcher inputFileMatcher = inputFileFormat.matcher(line);
                        if(inputFileMatcher.find()){
                            File infile =  new File(inputFileMatcher.group(1));
                            String rep  = inputFileMatcher.group(2);
                            String condition = inputFileMatcher.group(3);
                            String type = infile.getName().replaceAll(".*\\.", "");
                            DifferentialInputFile dfFile =  new DifferentialInputFile(infile, type, rep, condition);
                            dfInputFiles.add(dfFile);
                        }
                }
                return dfInputFiles;
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(Gdiffcommand.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex) {
                Logger.getLogger(Gdiffcommand.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        
        }
        
        return null;
    }
    
    private boolean isInputFile(){
        
        // Tab seperated file 
        // File                 Replicate number     condition
        // Absolute File path  (0-1)                 control/treatment
        
        Pattern inputFileFormat = Pattern.compile("^(.*?)\\t(\\d+)\\t((control)|(treatment))");
        
        try {
            
            FileReader fr = new FileReader(this.getInFile());
            BufferedReader br =  new BufferedReader(fr);
            String line = "";
            
            if(isMinimunLineCount(this.getInFile(), 4)){
                while((line = br.readLine()) != null){
                    Matcher inputFileMatcher = inputFileFormat.matcher(line);
                        if(!inputFileMatcher.find()){
                            return false;
                        }
                }
                return true;
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Gdiffcommand.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Gdiffcommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return  false;
    }

    private boolean isMinimunLineCount(File infile, int count){
        try {
            FileReader fr = new FileReader(this.getInFile());
            BufferedReader br =  new BufferedReader(fr);
            String line;
            int lineCount = 0;
            
            while((line = br.readLine() ) != null ){
                lineCount++;
                if(count == lineCount){
                    return true;
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Gdiffcommand.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Gdiffcommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean validateInput(Input input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void printHelpMessage() {
       System.out.println(getHelpMessage());
    }
    
    @Override
    public String getHelpMessage() {
       String gdiffHelpMessage = "\n";
       gdiffHelpMessage = gdiffHelpMessage +"java -jar GUAVA.jar gdiff [options]* \n";
       
       gdiffHelpMessage = gdiffHelpMessage +"required options"+"\n";
       gdiffHelpMessage = gdiffHelpMessage + "\t"+"-n"+"\t"+"project name  "+"\n";
       gdiffHelpMessage = gdiffHelpMessage + "\t"+"-o" +"\t"+"output folder to save results"+"\n";
       gdiffHelpMessage = gdiffHelpMessage + "\t"+"-files" +"\t"+"text file containing input bed and bam files "+"\n";
    
       gdiffHelpMessage = gdiffHelpMessage +"optional"+"\n";
       gdiffHelpMessage = gdiffHelpMessage + "\t"+"-fc"+"\t"+"log2(FoldChange) >=  1, default: 2 "+"\n";
       gdiffHelpMessage = gdiffHelpMessage + "\t"+"-p" +"\t"+"p value =< 0.05"+"\n";
       gdiffHelpMessage = gdiffHelpMessage + "\t"+"-m" +"\t"+"[deseq2] Differential analysis method. default deseq2"+"\n";
       gdiffHelpMessage = gdiffHelpMessage + "\t"+"-g" +"\t"+"[hg19] genome build."+"\n";
       gdiffHelpMessage = gdiffHelpMessage + "\t"+"-u" +"\t"+"upstream distance cut off in bp, default 1000"+"\n";
       gdiffHelpMessage = gdiffHelpMessage + "\t"+"-d" +"\t"+"downstream distance cut off in bp, default 1000"+"\n";
       
       return gdiffHelpMessage;
    }
    
  

    /*****
     *
     * BUILD Gdiffcommand object from commandline input
     *
     * @param command
     * @return 
     *****/
    
    public static Gdiffcommand getGdiffCommand(Command command){
        
        Gdiffcommand gdiffCommand =  new Gdiffcommand();
        String userCommand =  command.getCommand();
        
        userCommand =  userCommand.replace(".*gdiff", userCommand);
        
        String foldChangePattern = "-fc\\s+([(\\d+\\.\\d+)(\\d+)])\\s+";
        String pvaluePattern = "-p\\s+([(\\d+\\.\\d+)(\\d+)])\\s+";
        String methodPattern = "-m\\s+(.*?)\\s+";
        String genomePattern = "-g\\s+(.*?)\\s+";
        String outputdirPattern = "-o\\s+(.*?)\\s+";
        String filesPattern = "-files\\s+(.*?)\\s+";
        String upstreamDistancePattern = "-u\\s+(\\d+)\\s+";
        String downstreamDistancePatern  = "-d\\s+(\\d+)\\s+";
        String projectNamePatern  = "-n\\s+(.*?)\\s+";
        
        // p value 
        if(command.isUsed(pvaluePattern)){
            double pvalue     =  command.getDoubleParameter(pvaluePattern);
            gdiffCommand.setPvalue(pvalue);
            
        }
        if(command.isUsed(foldChangePattern)){
            double foldChange =  command.getDoubleParameter(foldChangePattern);
            gdiffCommand.setFoldchange(foldChange);
        }
        if(command.isUsed(methodPattern)){
            String method     =  command.getStringParameter(methodPattern);
            gdiffCommand.setMethod(method);
        }
        if(command.isUsed(genomePattern)){
            String genome     =  command.getStringParameter(genomePattern);
            gdiffCommand.setGenome(genome);
        }
        if(command.isUsed(projectNamePatern)){
            String project    =  command.getStringParameter(projectNamePatern);
            gdiffCommand.setProjectName(project);
        }
        if(command.isUsed(outputdirPattern)){
            File infile    =  command.getFileParameter(outputdirPattern);
            gdiffCommand.setOutdir(infile);
        }
        if(command.isUsed(filesPattern)){
            File inFile       =  command.getFileParameter(filesPattern);
            gdiffCommand.setInFile(inFile);
        }
        if(command.isUsed(upstreamDistancePattern)){
            int upstream      =  command.getIntParameter(upstreamDistancePattern);
            gdiffCommand.setUpstreamDistance(upstream);
        }
        if(command.isUsed(downstreamDistancePatern)){
            int downstream    =  command.getIntParameter(downstreamDistancePatern);
            gdiffCommand.setDownstreamDistance(downstream);
        }
        
        return gdiffCommand;
        
     }
     
    
     
    public File[] getFilesFromString(String inputFileString){
        inputFileString = inputFileString.trim();
        inputFileString = inputFileString.replace(",$", "");
        
        String[] fstrings =  inputFileString.split(",?\\s*");
        File[] files =  new File[fstrings.length];
        
        for(int i=0; i > fstrings.length; i++){
            System.out.println(">>> "+fstrings[i]);
            File file = new File(fstrings[i]);
            files[i] = file;
        }
        
        return files;
    }
    
    
    /**
     * @return the foldchange
     */
    public double getFoldchange() {
        return foldchange;
    }

    /**
     * @param foldchange the foldchange to set
     */
    public void setFoldchange(double foldchange) {
        if(foldchange > 1){
            this.foldchange = foldchange;
        }
    }

    /**
     * @return the pvalue
     */
    public double getPvalue() {
        return pvalue;
    }

    /**
     * @param pvalue the pvalue to set
     */
    public void setPvalue(double pvalue) {
        if(pvalue <= 0.05 && pvalue > 0){
           this.pvalue = pvalue;
        }
        else{
            System.out.println("invalid pvalue");
            killExecution();
        }
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        if(method.equals("deseq2")){
            this.method = method;
        }
        else{
            System.out.println("Invalid method: "+method);
            killExecution();
        }
        
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
        
        if(genome.equals("hg19")){
            this.genome = genome;
        }
        else{
            System.out.println("Invalid genome: "+genome);
            killExecution();
        }
    }

    /**
     * @return the upstreamDistance
     */
    public int getUpstreamDistance() {
        return upstreamDistance;
    }

    /**
     * @param upstreamDistance the upstreamDistance to set
     */
    public void setUpstreamDistance(int upstreamDistance) {
        if(upstreamDistance > 0){
            this.upstreamDistance = upstreamDistance;
        }
        else{
            System.out.println("Invalid upstream distance: "+upstreamDistance);
            killExecution();
        }
    }

    /**
     * @return the downstreamDistance
     */
    public int getDownstreamDistance() {
        return downstreamDistance;
    }

    /**
     * @param downstreamDistance the downstreamDistance to set
     */
    public void setDownstreamDistance(int downstreamDistance) {
        if(downstreamDistance > 0){
            this.downstreamDistance = downstreamDistance;
        }
        else{
            System.out.println("Invalid downstream distance: "+downstreamDistance);
            killExecution();
        }
    }

    /**
     * @return the outdir
     */
    public File getOutdir() {
        return outdir;
    }

    /**
     * @param outdir the outdir to set
     */
    public void setOutdir(File outdir) {
        
        if(outdir.getAbsoluteFile().isDirectory()){
            this.outdir = outdir;
        }
        else{
            System.out.println("Invalid output directory: "+outdir.getAbsolutePath());
            killExecution();
        }
    }

   
    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public File getInFile() {
        return inFile;
    }

    public void setInFile(File inFile) {
        if(inFile.getAbsoluteFile().isFile()){
            this.inFile = inFile;
        }
        else{
            System.out.println("Invalid input file: "+inFile.getAbsolutePath());
            killExecution();
        }
    }

    
    
    public Gdiffcommand() {
    }

    @Override
    public String toString() {
        
        String gdiffCommand = "gdiff command\n";
        gdiffCommand =  gdiffCommand+" project name = "+this.getProjectName()+"\n";
        gdiffCommand =  gdiffCommand+" foldchange = "+this.getFoldchange()+"\n";
        gdiffCommand =  gdiffCommand+" pvalue = "+this.getPvalue()+"\n";
        gdiffCommand =  gdiffCommand+" method = "+this.getMethod()+"\n";
        gdiffCommand =  gdiffCommand+" genome"+this.getGenome()+"\n";
        gdiffCommand =  gdiffCommand+" upstream = "+this.getUpstreamDistance()+"\n";
        gdiffCommand =  gdiffCommand+" downstream = "+this.getDownstreamDistance()+"\n";
        gdiffCommand =  gdiffCommand+" outputdir = "+this.getOutdir().getAbsolutePath()+"\n";
        gdiffCommand =  gdiffCommand+" input file = "+this.getInFile().getAbsolutePath()+"\n";
        
        return gdiffCommand; //To change body of generated methods, choose Tools | Templates.
    }

    public static Set<String> getGdiffCompulsoryOptions() {
        return gdiffCompulsoryOptions;
    }

    public static Set<String> getGdiffOptions() {
        return gdiffOptions;
    }
    
    
    
    
    
}
