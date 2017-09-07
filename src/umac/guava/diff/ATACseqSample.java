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
package umac.guava.diff;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author mayurdivate
 */
public class ATACseqSample {
    
    private String sampleName;
    private File bamFile;
    private File bedFile;
    private String condition;
    private String ReplicateNumber;

    public ATACseqSample(File bamFile, File bedFile, String condition, String ReplicateNumber) {
        this.sampleName = bamFile.getName();
        this.bamFile = bamFile;
        this.bedFile = bedFile;
        this.condition = condition;
        this.ReplicateNumber = ReplicateNumber;
    }
    
    public ATACseqSample(File inFile, String condition, String ReplicateNumber, String type) {
        
        if(type.equalsIgnoreCase("bam")){
            this.sampleName = inFile.getName();
            this.bamFile = inFile;
            this.condition = condition;
            this.ReplicateNumber = ReplicateNumber;
        }
        else if(type.equalsIgnoreCase("bed")){
            this.bedFile = inFile;
            this.condition = condition;
            this.ReplicateNumber = ReplicateNumber;
        }
    }

    public ATACseqSample(File inFile,String type) {
        
        if(type.equalsIgnoreCase("bam")){
            this.bamFile = inFile;
        }
        else if(type.equalsIgnoreCase("bed")){
            this.bedFile = inFile;
        }
    }
    public ATACseqSample(File bedFile, String condition, String ReplicateNumber) {
        
    }
    
    public static ArrayList<ATACseqSample> getATACseqSampleFromDifferentialInput(ArrayList<DifferentialInputFile> dfInputList){
        HashMap<ATACseqSample,ATACseqSample> atacSeqHashMap = new HashMap<>();
        
        for(int index = 0; index < dfInputList.size(); index++){
            DifferentialInputFile dfInput =  dfInputList.get(index);
            ATACseqSample atacSeqSample = new ATACseqSample(dfInput.getDiifInputFile(), dfInput.getCondition(), dfInput.getReplicateNumber(), dfInput.getType());
            if(atacSeqHashMap.containsKey(atacSeqSample)){
                if(dfInput.getType().equalsIgnoreCase("bam")){
                    atacSeqHashMap.get(atacSeqSample).setBamFile(dfInput.getDiifInputFile());
                }
                else{
                    atacSeqHashMap.get(atacSeqSample).setBedFile(dfInput.getDiifInputFile());
                }
            }
            else{
                atacSeqHashMap.put(atacSeqSample, atacSeqSample);
            }
        }
        
        ArrayList<ATACseqSample> atacSeqSampleList = new ArrayList<>();
        
        for(ATACseqSample atacSample : atacSeqHashMap.keySet()){
            atacSeqSampleList.add(atacSeqHashMap.get(atacSample));
        }
        
        return atacSeqSampleList;
    }
    
    
    @Override
    public String toString() {
        String atacseSample = this.getCondition()+" Rep_"+this.getReplicateNumber();
        return atacseSample; 
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ATACseqSample){
            if(obj.toString().equals(this.toString())){
                return true;
            }
        }
        return false;
    }
    

    /**
     * @return the sampleName
     */
    public String getSampleName() {
        return sampleName;
    }

    
    /**
     * @return the bamFile
     */
    public File getBamFile() {
        return bamFile;
    }

    /**
     * @param bamFile the bamFile to set
     */
    public void setBamFile(File bamFile) {
        this.bamFile = bamFile;
    }

    /**
     * @return the bedFile
     */
    public File getBedFile() {
        return bedFile;
    }

    /**
     * @param bedFile the bedFile to set
     */
    public void setBedFile(File bedFile) {
        this.bedFile = bedFile;
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
        this.condition = condition;
    }

    /**
     * @return the ReplicateNumber
     */
    public String getReplicateNumber() {
        return ReplicateNumber;
    }

    /**
     * @param ReplicateNumber the ReplicateNumber to set
     */
    public void setReplicateNumber(String ReplicateNumber) {
        this.ReplicateNumber = ReplicateNumber;
    }
    
    
    
    
    
}
