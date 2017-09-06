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

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class Bowtie2 extends Tool{
    
    private File r1;
    private File r2;
    private File outputSam;
    private File mapQBam;
    private String bowtie2Index;
    private int mapQ;
    private int cpu;
    private int insertSize;
    
    public static Bowtie2 getBowtie2(GuavaInput input, GuavaOutputFiles outputFiles) {
        
        String outSam =  outputFiles.getAlignedSam().getAbsolutePath().replaceAll(".sam$", "_bowtie2.sam");
        String mapQ_Bam =  outputFiles.getAlignedSam().getAbsolutePath().replaceAll(".sam$", "_mapQ_filtered.bam");
        File outputSam = new File(outSam);
        File mapQBam = new File(mapQ_Bam);
        
        Bowtie2 bowtie2 = new Bowtie2(input.getR1Fastq(), input.getR2Fastq(), outputSam, mapQBam,
                input.getbowtieIndexString(), input.getMapQ(), input.getCpu_units(),input.getInsertSize());
        
        return bowtie2;
        
    }

    public Bowtie2(File r1, File r2, File outputSam, File mapQBam, String bowtie2Index, int mapQ, int cpu, int insertSize) {
        this.r1 = r1;
        this.r2 = r2;
        this.outputSam = outputSam;
        this.mapQBam = mapQBam;
        this.bowtie2Index = bowtie2Index;
        this.mapQ = mapQ;
        this.cpu = cpu;
        this.insertSize = insertSize;
    }

    @Override
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outputFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String[] getCommand() {

        String[] commandArray =  
            {   "bowtie2",
                "-X", Integer.toString(this.getInsertSize()),
                "--no-unal",
                "--no-mixed",
                "--no-discordant",
                "-p", Integer.toString(this.getCpu()),
                "-x", this.getBowtie2Index(), 
                "-1", this.getR1().getAbsolutePath(),
                "-2", this.getR2().getAbsolutePath(),
                "-S", this.getOutputSam().getAbsolutePath()
            };
        return commandArray;
    }

    @Override
    public String[] runCommand(String[] commandAarray) {
       String[] log =  new String[2];
        try {
            ProcessBuilder bowtieBuilder = new ProcessBuilder(commandAarray);
            Process process =  bowtieBuilder.start();
            String stdOUT = new Bowtie().getSTDoutput(process);
            String errorLog = new Bowtie().getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
            
        } catch (IOException ex) {
            Logger.getLogger(Bowtie.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return log;
    }

    @Override
    public boolean isWorking() {
        
        String[] commandArray =  {   "bowtie2", "--version" };
        String[] log = new Bowtie().runCommand(commandArray);
        if(log[0] != null && log[1] != null){
            System.out.println("\t\tbowtie:\t\tAffirmative :)");
            return true;
        }
        
        System.out.println("\t\tbowtie:\t\tNegative :(");
        return false;
    }

    public File getR1() {
        return r1;
    }

    public void setR1(File r1) {
        this.r1 = r1;
    }

    public File getR2() {
        return r2;
    }

    public void setR2(File r2) {
        this.r2 = r2;
    }

    public int getMapQ() {
        return mapQ;
    }

    public void setMapQ(int mapQ) {
        if(mapQ > 0){
            this.mapQ = mapQ;
        }
        else{
            this.mapQ = 1;
        }
    }

    public int getCpu() {
        return cpu;
    }

    public void setCpu(int cpu) {
        if(cpu > 0){
            this.cpu = cpu;
        }else{
            this.cpu =1;
        }
    }

    public int getInsertSize() {
        return insertSize;
    }

    public void setInsertSize(int insertSize) {
        if(insertSize > 500){
            this.insertSize = insertSize;
        }
        else{
            this.insertSize = 500;
        }
    }

    /**
     * @return the outputSam
     */
    public File getOutputSam() {
        return outputSam;
    }

    /**
     * @param outputSam the outputSam to set
     */
    public void setOutputSam(File outputSam) {
        this.outputSam = outputSam;
    }

    /**
     * @return the bowtie2Index
     */
    public String getBowtie2Index() {
        return bowtie2Index;
    }

    /**
     * @param bowtie2Index the bowtie2Index to set
     */
    public void setBowtie2Index(String bowtie2Index) {
        this.bowtie2Index = bowtie2Index;
    }

    public File getMapQBam() {
        return mapQBam;
    }

    public void setMapQBam(File mapQBam) {
        this.mapQBam = mapQBam;
    }
    
    
    
    
}
