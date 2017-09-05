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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mayurdivate
 */
public class Peak {
    private String chromosome;
    private int start;
    private int end;
    private String name;
    
    public boolean writePeaks(ArrayList<Peak> peakList, File outputFile){
        
        try {
            FileWriter outputFileWriter = new FileWriter(outputFile);
            PrintWriter outputFilePrintWriter = new PrintWriter(outputFileWriter);
            
            for(int index=0; index < peakList.size(); index++){
                String outLine = peakList.get(index).getChromosome()+"\t"
                        +peakList.get(index).getStart()+"\t"
                        +peakList.get(index).getEnd()+"\t"
                        +"Peak_"+(index+1)+"\n";
                outputFilePrintWriter.write(outLine);
                outputFilePrintWriter.flush();
            }
            outputFilePrintWriter.close();
            outputFileWriter.close();
            return true;
            
        } catch (IOException ex) {
            Logger.getLogger(Peak.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static ArrayList<Peak> getPeakList(File bedFile){
        
        try{
            ArrayList<Peak> peakList = new ArrayList<>();
            FileReader bedFileReader = new FileReader(bedFile);
            BufferedReader bedBufferedReader = new BufferedReader(bedFileReader);
            String line ;
            Pattern bed = Pattern.compile("^(.*?)\t(\\d+)\t(\\d+)");
            
            while((line = bedBufferedReader.readLine()) != null ){
            
                Matcher bedMatch = bed.matcher(line);
                if(bedMatch.find()){
                    String[] lineArray = line.split("\t");
                    String chromosome =  lineArray[0];
                    int start =  Integer.parseInt(lineArray[1]);
                    int end =  Integer.parseInt(lineArray[2]);
                    Peak peak = new Peak(chromosome, start, end);
                    peakList.add(peak);
                }
            
            }
            return peakList;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Peak.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            Logger.getLogger(Peak.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Peak.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }

    public ArrayList<Peak> mergeOverlappingPeaks(ArrayList<Peak> peakList){
        
        // sort peaks
        Collections.sort(peakList,new PeakSortComparator());
        
        // list after merging overlapping peaks
        ArrayList<Peak> resultPeakList = new ArrayList<>();
        
        Peak previousPeak = peakList.get(0);
        Peak currentPeak = null; 
        Peak mergePeak = null;
        
        boolean lastMerge = false;
        boolean keep = false;
        
        for(int index=1; index < peakList.size(); index++){
            
            lastMerge = false;
            currentPeak = peakList.get(index);
            
            if(currentPeak.isOverlapping(previousPeak, currentPeak)){
                mergePeak = mergeOverlappingPeak(previousPeak, currentPeak);
                previousPeak =  mergePeak;
                lastMerge = true;
                keep = true;
            }
            else if(keep){
                //overlap was found but now there is no more overlap
                resultPeakList.add(mergePeak);
                previousPeak =  currentPeak;
            }
            
        }
        
            if(lastMerge){
                    resultPeakList.add(mergePeak);
            }
                    

        return resultPeakList;
    }
    
    public ArrayList<Peak> mergePeaks(ArrayList<Peak> list1, ArrayList<Peak> list2){
        
//        ArrayList<Peak> uniquePeakList = new ArrayList<>(peakList1);
//        uniquePeakList.addAll(peakList2);
//        Collections.sort(uniquePeakList,new PeakSortComparator());

        
        ArrayList<Peak> uniquePeaks = getUniquePeaks(list1, list2);
        ArrayList<Peak> meregedPeaks = new ArrayList<>();
        
        Peak previousPeak = uniquePeaks.get(0);
        Peak currentPeak = null; 
        Peak mergePeak = null;
        boolean lastMerge = false;
        
        for(int index=1; index < uniquePeaks.size(); index++){
            lastMerge = false;
            currentPeak = uniquePeaks.get(index);
            if(currentPeak.isOverlapping(previousPeak, currentPeak)){
                mergePeak = mergeOverlappingPeak(previousPeak, currentPeak);
                previousPeak =  mergePeak;
                lastMerge = true;
            }
            else{
                meregedPeaks.add(previousPeak);
                previousPeak =  currentPeak;
            }
        }
        
        if(lastMerge){
            meregedPeaks.add(mergePeak);
        }
        else if(!lastMerge){
            meregedPeaks.add(currentPeak);
        }

        return meregedPeaks;
    }
    
    public Peak mergeOverlappingPeak(Peak peak1, Peak peak2){
        int start = Math.min(peak1.getStart(), peak2.getStart());
        int end = Math.max(peak1.getEnd(), peak2.getEnd());
        Peak mergedPeak = new Peak(peak1.getChromosome(), start, end);
        return mergedPeak;
    }
    
    public boolean isOverlapping(Peak previousPeak, Peak currentPeak){
        if(previousPeak.getChromosome().equals(currentPeak.getChromosome())){
            if(currentPeak.getStart() <= previousPeak.getEnd()){
                return true;
            }
        }
        return false;
    }
    
    public boolean isGoodOverlap(Peak previousPeak, double fraction){
        if(this.isOverlapping(this, previousPeak)){
            
        }
        return false;
    }
    
    public static ArrayList<Peak> getUniquePeaks(ArrayList<Peak> list1, ArrayList<Peak> list2){
        
        HashSet<Peak> uniquePeaks = new HashSet<>();
        for(int index = 0;  index < list1.size();  index++){
            uniquePeaks.add(list1.get(index));
        }
        for(int index = 0;  index < list2.size();  index++){
            uniquePeaks.add(list2.get(index));
        }
        
        Iterator<Peak> iterator = uniquePeaks.iterator();
        ArrayList<Peak>  uniqueList = new ArrayList<>();

        while(iterator.hasNext()){
            uniqueList.add(iterator.next());
        }

        Collections.sort(uniqueList,new PeakSortComparator());
        return uniqueList;
    }
    
    public Peak(String chromosome, int start, int end) {
        this.chromosome = chromosome;
        this.start = start;
        this.end = end;
        this.name = name;
    }

    public Peak(String chromosome, int start, int end, String name) {
        this.chromosome = chromosome;
        this.start = start;
        this.end = end;
        this.name = name;
    }

    public Peak() {
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
        this.chromosome = chromosome;
    }

    /**
     * @return the start
     */
    public int getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(int start) {
        if(start > -1 ){
            this.start = start;
        }
    }

    /**
     * @return the end
     */
    public int getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(int end) {
        if(end > -1){
            this.end = end;
        }
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getChromosome()+":"+this.getStart()+"-"+this.getEnd();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Peak){
            String objString = obj.toString();
            String thisString = this.toString();
                if(objString.equals(thisString)){
                    return true;
                }
            return false;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.chromosome);
        hash = 37 * hash + this.start;
        hash = 37 * hash + this.end;
        return hash;
    }
    
    
    
    
    
    
    
}
