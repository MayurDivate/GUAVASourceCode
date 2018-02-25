/*
 * Copyright (C) 2018 mayurdivate
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
package umac.guava.diff;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static umac.guava.diff.Program.getArrayToString;

/**
 *
 * @author mayurdivate
 */
public class DiffBind extends Program{
    
    private String sampleID;
    private String tissue;
    private String factor;
    private String condition;
    private int replicate;
    private File bamReads;
    private File peaks;
    private String peakCaller;
    
    
    private File getDiffBindInFile(ArrayList<DifferentialInputFile> dfArrayList){
                
        for(DifferentialInputFile inFile : dfArrayList){
            
        }
        
        
        
        return null;
    }
    
    private File getDiffBindinputEntry(ArrayList<DifferentialInputFile> dfArrayList){
        
        HashMap<String, DiffBind> diffbindHM =  new HashMap<>();
        
        for(DifferentialInputFile diffFile : dfArrayList){
            String key = diffFile.getCondition()+" "+diffFile.getReplicateNumber();
            System.out.println(key);
            
                DiffBind diffBind;
                if(diffbindHM.containsKey(key)){
                    diffBind =  diffbindHM.get(key);
                }else{
                    diffBind = new DiffBind();
                }
            
                if(diffFile.isPeakFile()){
                    diffBind.setPeaks(diffFile.getDiifInputFile());
                }else{
                    diffBind.setBamReads(diffFile.getDiifInputFile());
                }
                
                diffBind.setCondition(diffFile.getCondition());
                //diffBind.setPeakCaller(diffFile.getCondition());
                diffBind.setReplicate(diffFile.getReplicateNumber());
                //diffBind.setSampleID(diffFile.getCondition());
                
            
                if(diffBind != null){
                    diffbindHM.put(key, diffBind);
                }
        }
        
        
        return null;
    }
    
    

    @Override
    public String[] getCommand(File inputFile) {
        System.out.println("DiffBind");
        String[] commandArray = {
        } ;
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String[] runCommand(String[] commandArray) {
        
        String[] log =  new String[2];

        try {
            System.out.println(getArrayToString(commandArray));
            ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
            Process process =  processBuilder.start();
            String stdOUT = getSTDoutput(process);
            String errorLog = getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
            return log;
        } catch (IOException ex) {
            System.out.println("\t\t"+ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean isWorking() {
        String[] commandArray =  {"R", "--version" };
        String[] log = runCommand(commandArray);
        return log[0] != null;
    }

    /**
     * @return the sampleID
     */
    public String getSampleID() {
        return sampleID;
    }

    /**
     * @param sampleID the sampleID to set
     */
    public void setSampleID(String sampleID) {
        this.sampleID = sampleID;
    }

    /**
     * @return the tissue
     */
    public String getTissue() {
        return tissue;
    }

    /**
     * @param tissue the tissue to set
     */
    public void setTissue(String tissue) {
        this.tissue = tissue;
    }

    /**
     * @return the factor
     */
    public String getFactor() {
        return factor;
    }

    /**
     * @param factor the factor to set
     */
    public void setFactor(String factor) {
        this.factor = factor;
    }

    /**
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(String condition) {
        if(condition.equalsIgnoreCase("Control")){
            this.condition = "Control";
        }
        else if(condition.equalsIgnoreCase("Treatment")){
            this.condition = "Treatment";
        }
    }

    /**
     * @return the replicate
     */
    public int getReplicate() {
        return replicate;
    }

    /**
     * @param replicate the replicate to set
     */
    public void setReplicate(int replicate) {
        this.replicate = (replicate > 0) ? replicate : null;
    }
    
    public void setReplicate(String replicate) {
        int rep; 
        if(replicate.equals("I")){
            this.replicate = 1;
        }
        else if(replicate.equals("II")){
            this.replicate = 2;
        }
        else if(replicate.equals("III")){
            this.replicate = 3;
        }
        else if(replicate.equals("IV")){
            this.replicate = 4;
        }
    }

    /**
     * @return the bamReads
     */
    public File getBamReads() {
        return bamReads;
    }

    /**
     * @param bamReads the bamReads to set
     */
    public void setBamReads(File bamReads) {
        this.bamReads = bamReads;
    }

    /**
     * @return the peaks
     */
    public File getPeaks() {
        return peaks;
    }

    /**
     * @param peaks the peaks to set
     */
    public void setPeaks(File peaks) {
        this.peaks = peaks;
    }

    /**
     * @return the peakCaller
     */
    public String getPeakCaller() {
        return peakCaller;
    }

    /**
     * @param peakCaller the peakCaller to set
     */
    public void setPeakCaller(String peakCaller) {
        this.peakCaller = peakCaller;
    }
    
    
}
