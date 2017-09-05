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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author mayurdivate
 */
public class MergedATACPeak {
   
    private String chromosome;
    private int start;
    private int end;
    private HashSet<String> replicates;

    public MergedATACPeak(String chromosome, int start, int end, HashSet<String> replicates) {
        this.chromosome = chromosome;
        this.start = start;
        this.end = end;
        this.replicates = replicates;
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
        this.start = start;
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
        this.end = end;
    }

    /**
     * @return the replicates
     */
    public HashSet<String> getReplicates() {
        return replicates;
    }

    /**
     * @param replicates the replicates to set
     */
    public void setReplicates(HashSet<String> replicates) {
        this.replicates = replicates;
    }

    @Override
    public String toString() {
        String mergedPeakString =  this.getChromosome()+" : "+this.getStart()+" : "+this.getEnd()+" :"+this.getReplicateString();
        return mergedPeakString;
    }
    
    public String getReplicateString(){
        Iterator<String> iterator  = this.getReplicates().iterator();
        String replicates = ""; 
        while(iterator.hasNext()){
            replicates = replicates + iterator.next();
            if(iterator.hasNext()){
                replicates = replicates + ",";
            }
        }
        return replicates;
    }
    
    public static ArrayList<MergedATACPeak> mergeATACpeaks(ArrayList<MergedATACPeak> peaklList1,ArrayList<MergedATACPeak> peaklList2){
        ArrayList<MergedATACPeak> mergedATACseqPeaks = new ArrayList<>();
        mergedATACseqPeaks.addAll(peaklList1);
        mergedATACseqPeaks.addAll(peaklList2);
        Collections.sort(mergedATACseqPeaks, new MergedPeakComparator());
        return mergedATACseqPeaks;
        
    }
    
    
}
