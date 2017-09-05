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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class PeakTools {
    
    
    public static ArrayList<MergedATACPeak> getCommonPeaks(ArrayList<ATACseqPeak> mergedList){
        
        ArrayList<MergedATACPeak> mergedATACPeaks = new ArrayList<>();
        
        PeakTools peakTools = new PeakTools();
        int index = 1;
        int arrayListSize = mergedList.size();
        
        ATACseqPeak previousPeak =  mergedList.get(0);
        ATACseqPeak currentPeak;
        
        while(index < arrayListSize){
            
            currentPeak =  mergedList.get(index);
            index++;
            
            boolean isOverlap = peakTools.isOverlapping(previousPeak, currentPeak);
            
            if(isOverlap){
                MergedATACPeak mergedPeak = peakTools.mergeOverlappingPeaks(previousPeak, currentPeak);
                
                    while(index < arrayListSize){
                            currentPeak = mergedList.get(index);
                            index++;
                            isOverlap = peakTools.isOverlapping(mergedPeak, currentPeak);
                            if(isOverlap){
                                mergedPeak = peakTools.mergeOverlappingPeaks(mergedPeak, currentPeak);
                            }
                            else{
                                previousPeak = currentPeak;
                                break;
                            }
                    }
                mergedATACPeaks.add(mergedPeak);
            }
            else{
                previousPeak = currentPeak;
            }
        }
        return mergedATACPeaks;
    
    }
    
    public static ArrayList<MergedATACPeak> getCommonPeaks(ArrayList<MergedATACPeak> condition1CommonPeaks,
            ArrayList<MergedATACPeak> condition2CommonPeaks){
        
        ArrayList<MergedATACPeak> mergedList = MergedATACPeak.mergeATACpeaks(condition1CommonPeaks, condition2CommonPeaks);
        ArrayList<MergedATACPeak> mergedATACPeaks = new ArrayList<>();
        
        PeakTools peakTools = new PeakTools();
        int index = 1;
        int arrayListSize = mergedList.size();
        
        MergedATACPeak previousPeak =  mergedList.get(0);
        MergedATACPeak currentPeak;
        
        while(index < arrayListSize){
            
            currentPeak =  mergedList.get(index);
            index++;
            
            boolean isOverlap = peakTools.isOverlapping(previousPeak, currentPeak);
            
            if(isOverlap){
                MergedATACPeak mergedPeak = peakTools.mergeOverlappingPeaks(previousPeak, currentPeak);
                
                    while(index < arrayListSize){
                            currentPeak = mergedList.get(index);
                            index++;
                            isOverlap = peakTools.isOverlapping(mergedPeak, currentPeak);
                            if(isOverlap){
                                mergedPeak = peakTools.mergeOverlappingPeaks(mergedPeak, currentPeak);
                            }
                            else{
                                previousPeak = currentPeak;
                                break;
                            }
                    }
                mergedATACPeaks.add(mergedPeak);
            }
            else{
                previousPeak = currentPeak;
            }
        }
        return mergedATACPeaks;
    
    }
    
    
    
    private boolean isOverlapping(MergedATACPeak megeATACPeak, ATACseqPeak currentPeak){
        return new PeakTools().isOverlapping(megeATACPeak, currentPeak, 1);
    }
    
    private boolean isOverlapping(MergedATACPeak megeATACPeak, MergedATACPeak currentPeak){
        return new PeakTools().isOverlapping(megeATACPeak, currentPeak, 1);
    }
    
    private boolean isOverlapping(MergedATACPeak megeATACPeak, MergedATACPeak current, int minOverlap){
        if(megeATACPeak.getChromosome().equals(current.getChromosome())){
            if(current.getStart() <= megeATACPeak.getEnd()){
                int overlap = megeATACPeak.getEnd() - current.getStart() + 1;
                if(overlap >= minOverlap){
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isOverlapping(MergedATACPeak megeATACPeak, ATACseqPeak current, int minOverlap){
        if(megeATACPeak.getChromosome().equals(current.getChromosome())){
            if(current.getStart() <= megeATACPeak.getEnd()){
                int overlap = megeATACPeak.getEnd() - current.getStart() + 1;
                if(overlap >= minOverlap){
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean isOverlapping(ATACseqPeak previous, ATACseqPeak current, int minOverlap){
        if(previous.getChromosome().equals(current.getChromosome())){
            if(current.getStart() <= previous.getEnd()){
                int overlap = previous.getEnd() - current.getStart() + 1;
                if(overlap >= minOverlap){
                    return true;
                }
            }
        }
    return false;
    }
    
    private boolean isOverlapping(ATACseqPeak previous, ATACseqPeak current){
        return new PeakTools().isOverlapping(previous, current, 1);
    }    
    
    private MergedATACPeak mergeOverlappingPeaks(ATACseqPeak previousPeak, ATACseqPeak currentPeak){
        String chr =previousPeak.getChromosome();
        int start = Math.min(previousPeak.getStart(), currentPeak.getStart());
        int end = Math.max(previousPeak.getEnd(), currentPeak.getEnd());
        HashSet<String> replicates =  new HashSet<>();
        replicates.add(previousPeak.getReplicateNumber());
        replicates.add(currentPeak.getReplicateNumber());
        return new MergedATACPeak(chr, start, end, replicates);
    }
    
    private MergedATACPeak mergeOverlappingPeaks(MergedATACPeak previousMergedPeak, ATACseqPeak currentPeak){
        String chr =previousMergedPeak.getChromosome();
        int start = Math.min(previousMergedPeak.getStart(), currentPeak.getStart());
        int end = Math.max(previousMergedPeak.getEnd(), currentPeak.getEnd());
        HashSet<String> replicates =  new HashSet<>(previousMergedPeak.getReplicates());
        replicates.add(currentPeak.getReplicateNumber());
        return new MergedATACPeak(chr, start, end, replicates);
    }
    
    private MergedATACPeak mergeOverlappingPeaks(MergedATACPeak previousMergedPeak, MergedATACPeak currentPeak){
        String chr =previousMergedPeak.getChromosome();
        int start = Math.min(previousMergedPeak.getStart(), currentPeak.getStart());
        int end = Math.max(previousMergedPeak.getEnd(), currentPeak.getEnd());
        HashSet<String> replicates =  new HashSet<>();
        replicates.add("control");
        replicates.add("treatment");
        return new MergedATACPeak(chr, start, end, replicates);
    }
    
    public static ArrayList<MergedATACPeak> filterPeaks(ArrayList<MergedATACPeak> peakArrayList, int minReplicateNumber){
        ArrayList<MergedATACPeak> filterPeaks = new ArrayList<>();
        for(int index = 0; index < peakArrayList.size();  index++){
            if(peakArrayList.get(index).getReplicates().size() >=  minReplicateNumber){
                filterPeaks.add(peakArrayList.get(index));
            }
        
        }
        return filterPeaks;
    } 
    
    public static boolean writePeaksToBedFile(ArrayList<MergedATACPeak> mergedPeaks, File outputbed) {
        
        try {
            if(!outputbed.exists()){
                outputbed.createNewFile();
            }
            PrintWriter printWriter = new PrintWriter(outputbed);
            Collections.sort(mergedPeaks,new MergedPeakComparator());
            String peakName = "Peak_";
            for(int index = 0 ; index < mergedPeaks.size(); index++){
                printWriter.write(mergedPeaks.get(index).getChromosome()+"\t"+
                        mergedPeaks.get(index).getStart()+"\t"+
                        mergedPeaks.get(index).getEnd()+"\t"+
                        peakName+(index+1)+"\n");
                printWriter.flush();
            }
            printWriter.close();
            return true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PeakTools.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PeakTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
