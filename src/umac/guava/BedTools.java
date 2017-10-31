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
package umac.guava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class BedTools extends Tool{
    
    private static File  bedtoolsBin;
    private File outputFile;
    
    @Override 
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outputFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String[] getBamTobdgCommand(IGVdataTrack igvDataTrack){
        
        String[] commandArray =  
                    {   getGenomeCoverageTool().getAbsolutePath(),
                        "-bg", "-pc", 
                        "-scale", Double.toString(igvDataTrack.getScale()),
                        "-ibam", igvDataTrack.getBamFile().getAbsolutePath(),
                        "-g", getGenomeSize(igvDataTrack.getBuild()).getAbsolutePath(),
                        };
        return commandArray;
    
    }   

    public String[] getBedSortCommand(File input){
        
        String[] commandArray =  
                    {   getBedsortTool().getAbsolutePath(),
                        "-i", input.getAbsolutePath(),
                        };
        return commandArray;
    
    }   

    @Override
    public String[] runCommand(String[] command) {
        try {
            String[] log =  new String[2];
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process =  processBuilder.start();
            boolean stdOUT = this.getSTDoutput(process,this.getOutputFile());
            String errorLog = this.getSTDerror(process);
            log[0] = ""+stdOUT;
            log[1] = errorLog;
            return log;
        } catch (IOException ex) {
            Logger.getLogger(BedTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public String[] runCommand(String[] command, Boolean flag) {
        try {
            String[] log =  new String[2];
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process =  processBuilder.start();
            String stdOUT = new BedTools().getSTDoutput(process);
            String errorLog = new BedTools().getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
            return log;
        } catch (IOException ex) {
            Logger.getLogger(BedTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public boolean getSTDoutput(Process process, File outputFile){
        try {
            FileWriter outputFileWriter =  new FileWriter(outputFile);
            PrintWriter pw =  new PrintWriter(outputFileWriter);
            String readLineString;
            
            BufferedReader brProcess = new BufferedReader(new InputStreamReader(process.getInputStream()));
            
                while((readLineString = brProcess.readLine()) != null){
                        pw.write(readLineString+"\n");
                        pw.flush();
                }
                
            pw.close();
            return true;
        } catch (IOException ex) { 
            Logger.getLogger(BedTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public BedTools() {
    }

    
    @Override
    public boolean isWorking() {
        return bedtoolsPath();
    }
    
    public static boolean bedtoolsPath() {
        String[] commandArray =  {"bedtools", "--version" };
        String[] log = new BedTools().runCommand(commandArray);
        System.out.println("1 >>>"+log[0]);
        System.out.println("2 >>>"+log[1]);
        if(log[0] != null && log[1] != null){
            System.out.println("\t\tbowtie:\t\tAffirmative :)");
            return true;
        }
        
        System.out.println("\t\tbowtie:\t\tNegative :(");
        return false;
    }
    
    public File getGenomeCoverageTool(){
        File genomeCov = new File(getBedtoolsBin().getAbsolutePath()+System.getProperty("file.separator")+"genomeCoverageBed");
        return genomeCov;
    }
    
    public File getBedsortTool(){
        File tool = new File(getBedtoolsBin().getAbsolutePath()+System.getProperty("file.separator")+"sortBed");
        return tool;
    }
    
    public static File getGenomeSize(String build){

        File bedtoolsDir = getBedtoolsBin().getParentFile();
        File genomes = new File(bedtoolsDir.getAbsolutePath()+System.getProperty("file.separator")+"genomes");
        if(build.equalsIgnoreCase("hg19")){
            File chrSizes =  new File(genomes.getAbsoluteFile()+System.getProperty("file.separator")+"human.hg19.genome");
            return chrSizes;
        }
        else if(build.equalsIgnoreCase("mm9")){
            File chrSizes =  new File(genomes.getAbsoluteFile()+System.getProperty("file.separator")+"mouse.mm9.genome");
            return chrSizes;
        }
        if(build.equalsIgnoreCase("mm10")){
            File chrSizes =  new File(genomes.getAbsoluteFile()+System.getProperty("file.separator")+"mouse.mm10.genome");
            return chrSizes;
        }
        return null;

    }

    /**
     * @return the bedtoolsBin
     */
    public static File getBedtoolsBin() {
        return bedtoolsBin;
    }

    /**
     * @param aBedtoolsBin the bedtoolsBin to set
     */
    public static void setBedtoolsBin(File aBedtoolsBin) {
        bedtoolsBin = aBedtoolsBin;
    }

    /**
     * @return the outputFile
     */
    public File getOutputFile() {
        return outputFile;
    }

    /**
     * @param outputFile the outputFile to set
     */
    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public BedTools(File outputFile) {
        this.outputFile = outputFile;
    }
    
    
    
}

