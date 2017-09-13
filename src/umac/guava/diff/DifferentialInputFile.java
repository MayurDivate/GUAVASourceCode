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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;


/**
 *
 * @author mayurdivate
 */
public class DifferentialInputFile {
    private File diifInputFile;
    private String type;
    private String replicateNumber;
    private String condition;

    public DifferentialInputFile() {
    }

    public DifferentialInputFile(File diifInputFile, String type, String replicateNumber, String condition) {
        this.diifInputFile = diifInputFile;
        this.type = type;
        this.replicateNumber = replicateNumber;
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    public File getDiifInputFile() {
        return diifInputFile;
    }

    public String getReplicateNumber() {
        return replicateNumber;
    }

    public void setCondition(String condition) {
        if(condition.equalsIgnoreCase("Control") ){
            this.condition = "Control";
        }
        else if(condition.equalsIgnoreCase("Treatment")){
            this.condition = "Treatment";
        }
    }

    public void setDiifInputFile(File diifInputFile) {
        if(diifInputFile.getAbsolutePath().endsWith(".bam")){
            this.diifInputFile = diifInputFile;
            this.setType("BAM");
        }
        if(diifInputFile.getAbsolutePath().endsWith(".bed")){
            this.diifInputFile = diifInputFile;
            this.setType("BED");
        }
    }

    public void setType(String type) {
        if(type.equals(".bam") || type.equals(".bed")){
           this.type = type;
        }
    }
    

    public void setReplicateNumber(String replicateNumber) {
        this.replicateNumber = replicateNumber;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return this.getDiifInputFile().getName();
    }
    
    
    
    public static boolean isValidInput(ArrayList<DifferentialInputFile> dfList){
        
        HashMap<String,DFCount> inputHashMap = new HashMap<>();
        
        for(int index = 0; index < dfList.size(); index++){
            DifferentialInputFile dfInput = dfList.get(index);
            String inputRecord = dfInput.getCondition()+dfInput.getReplicateNumber();
            
            if(!inputHashMap.containsKey(inputRecord)){
                int bamCount = 0;
                int bedCount = 0;
                if(dfInput.getType().equalsIgnoreCase("bam")){ bamCount++; } else{ bedCount++; }
                DFCount dfc = new DFCount(inputRecord, bamCount, bedCount);
                inputHashMap.put(inputRecord, dfc);
            }
            else if(dfInput.getType().equalsIgnoreCase("bam")){
                if(inputHashMap.get(inputRecord).getBamCount() == 1){
                    JOptionPane.showMessageDialog(null, "One replicate can have only one bam file"
                            + "\ncondition: "+dfInput.getCondition()+" replicate:"+dfInput.getReplicateNumber()+" has > 1 bam files");
                    return false;
                }else{
                    inputHashMap.get(inputRecord).addBamCount(1);
                }
            }
            else if(dfInput.getType().equalsIgnoreCase("bed")){
                if(inputHashMap.get(inputRecord).getBedCount() == 1){
                    JOptionPane.showMessageDialog(null, "One replicate can have only one bed file"
                            + "\ncondition: "+dfInput.getCondition()+" replicate:"+dfInput.getReplicateNumber()+" has > 1 bed files");
                    return false;
                }else{
                    inputHashMap.get(inputRecord).addBamCount(1);
                }
            }
        }
        
        // Round 2 validation bam and bed presence
        Set<String> inputKeySet = inputHashMap.keySet();
        for(String key : inputKeySet){
            if(inputHashMap.get(key).getBamCount() != 1){
                JOptionPane.showMessageDialog(null, "Each replicate must have one and only one bam file");
                return false;
            }
            else if(inputHashMap.get(key).getBedCount() != 1){
                JOptionPane.showMessageDialog(null, "Each replicate must have one and only one bed file");
                return false;
            }
        }
            
        return true;
    }
    
    public static ArrayList<File> getInputFiles (ArrayList<DifferentialInputFile> dfList, String condition, String type){
        ArrayList<File> inputFiles = new ArrayList<>();
        
        for(int index = 0; index < dfList.size(); index++){
            DifferentialInputFile dfInput =  dfList.get(index);
            if(dfInput.getType().equalsIgnoreCase(type) && dfInput.getCondition().equalsIgnoreCase(condition)){
                inputFiles.add(dfInput.getDiifInputFile());
            }
        }
        return inputFiles;
    }
    
    public static ArrayList<DifferentialInputFile> getDifferentialPeakInput (ArrayList<DifferentialInputFile> dfList, String condition){
        ArrayList<DifferentialInputFile> inputPeakFiles = new ArrayList<>();
        
        System.out.println("Condition == "+condition);
        for(int index = 0; index < dfList.size(); index++){
            DifferentialInputFile dfInput =  dfList.get(index);
            
            if(dfInput.getCondition().equalsIgnoreCase(condition)){
                if(dfInput.isPeakFile()){
                    System.out.println("File == "+dfInput.getDiifInputFile().getName());
                    inputPeakFiles.add(dfInput);
                }
            }
        }
        return inputPeakFiles;
    }
    
    public boolean isPeakFile(){
        if(this.getType().equals("bed") || this.getType().equals("narrowPeak") ){
            return true;
        }
        return false;
    }
    
    public static ArrayList<File> getInputFiles (ArrayList<DifferentialInputFile> dfList, String condition){
        ArrayList<File> inputFiles = new ArrayList<>();
        
        for(int index = 0; index < dfList.size(); index++){
            DifferentialInputFile dfInput =  dfList.get(index);
            if(dfInput.getCondition().equals(condition)){
                inputFiles.add(dfInput.getDiifInputFile());
            }
        }
        return inputFiles;
    }
 
    public HashSet<ATACseqPeak> parsePeaks() {
        if(this.getType().equalsIgnoreCase("bed") || this.getType().equalsIgnoreCase("narrowPeak")){
        HashSet<ATACseqPeak> atacSeqPeaks = new HashSet<>();
        try {
            FileReader peakFileReader = new FileReader(this.getDiifInputFile());
            BufferedReader peakBufferedReader = new BufferedReader(peakFileReader);
            String line ;

            Pattern narrowPeakFormat = Pattern.compile("^(.*?)\t(\\d+)\t(\\d+)");

            while((line =  peakBufferedReader.readLine())!= null){
                Matcher narrowPeakMatch = narrowPeakFormat.matcher(line);
                if(narrowPeakMatch.find()){
                    String chromosome = narrowPeakMatch.group(1);
                    int start = getNumber(narrowPeakMatch.group(2));
                    int end = getNumber(narrowPeakMatch.group(3));
                    ATACseqPeak peak = new ATACseqPeak(chromosome, start, end,this.getCondition(),this.getReplicateNumber());
                    atacSeqPeaks.add(peak);
                }
            }
            
        }   catch (FileNotFoundException ex) {
                Logger.getLogger(DifferentialInputFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DifferentialInputFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        return atacSeqPeaks;
        }
        return null;
    }
    
    public static int getNumber(String num){
        try{
            return Integer.parseInt(num);
        }
        catch(NumberFormatException e){
            System.out.println("Can not parse number from string "+num);
        }
        return 0;
    }
    
    
    
    
}

class DFCount{

    private String inputID;
    private int bamCount;
    private int bedCount;

    public DFCount(String inputID, int bamCount, int bedCount) {
        this.inputID = inputID;
        this.bamCount = bamCount;
        this.bedCount = bedCount;
    }
    
    
    public void addBamCount(int add){
        this.setBamCount(this.getBamCount() + add);
    }
    public void addBedCount(int add){
        this.setBedCount(this.getBedCount() + add);
    }

    public int getBamCount() {
        return bamCount;
    }

    public int getBedCount() {
        return bedCount;
    }

    public String getInputID() {
        return inputID;
    }

    public void setBamCount(int bamCount) {
        this.bamCount = bamCount;
    }

    public void setBedCount(int bedCount) {
        this.bedCount = bedCount;
    }

    public void setInputID(String inputID) {
        this.inputID = inputID;
    }
    
    
}
