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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class ATACseqPeak {
    private String chromosome;
    private int start;
    private int end;
    private double pvalue;
    private double qvalue;
    private String condition;
    private String replicateNumber;

    public ATACseqPeak(String chromosome, int start, int end, double pvalue, double qvalue, String condition, String replicateNumber) {
        if(end > start){
            this.chromosome = chromosome;
            this.start = start;
            this.end = end;
            this.pvalue = pvalue;
            this.qvalue = qvalue;
            this.condition = condition;
            this.replicateNumber = replicateNumber;
        }
        else{
            System.out.println("Peak width cannot be negative");
        }

    }
    
    public ATACseqPeak(String chromosome, int start, int end,String condition, String replicateNumber) {
        if(end > start){
            this.chromosome = chromosome;
            this.start = start;
            this.end = end;
            this.condition = condition;
            this.replicateNumber = replicateNumber;
        }
        else{
            System.out.println("Peak width cannot be negative");
        }
    }
    
    
    public boolean writeATACseqPeaks(HashSet<ATACseqPeak> peaks, File peakParser){
            
        try {
            PrintWriter printWriter = new PrintWriter(peakParser);
            Iterator<ATACseqPeak> hashIterator = peaks.iterator();
            
            while(hashIterator.hasNext()){
                ATACseqPeak peak = hashIterator.next();
                String peakRecord = peak.getChromosome()+"\t"+peak.getStart()+"\t"+peak.getEnd();
                printWriter.write(peakRecord);
                printWriter.flush();
            }
            
            printWriter.close();
            return true;
          } catch (FileNotFoundException ex) {
            Logger.getLogger(ATACseqPeak.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static ArrayList<ATACseqPeak> mergeATACpeaks(HashSet<ATACseqPeak> peakHashSet1,HashSet<ATACseqPeak> peakHashSet2){
    
        ArrayList<ATACseqPeak> mergedATACseqPeaks = new ArrayList<>();

        Iterator<ATACseqPeak> iterator =peakHashSet1.iterator();
        while(iterator.hasNext()){
            ATACseqPeak peak = iterator.next();
            mergedATACseqPeaks.add(peak);
        }

        iterator =peakHashSet2.iterator();
        while(iterator.hasNext()){
            ATACseqPeak peak = iterator.next();
            mergedATACseqPeaks.add(peak);
        }
        
        Collections.sort(mergedATACseqPeaks, new ATACpeakChrStartComparator());
        return mergedATACseqPeaks;
        
    } 

       public static ArrayList<ATACseqPeak> mergeATACpeaks(ArrayList<ATACseqPeak> peakHashSet1,HashSet<ATACseqPeak> peakHashSet2){
    
        ArrayList<ATACseqPeak> mergedATACseqPeaks = new ArrayList<>(peakHashSet1);

        Iterator<ATACseqPeak> iterator =peakHashSet2.iterator();
        while(iterator.hasNext()){
            ATACseqPeak peak = iterator.next();
            mergedATACseqPeaks.add(peak);
        }
        
        Collections.sort(mergedATACseqPeaks, new ATACpeakChrStartComparator());
        return mergedATACseqPeaks;
        
    } 

    @Override
    public String toString() {
        
        String thisString = this.getChromosome()+"-"+this.getStart()+"-"+this.getEnd();
        return thisString;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ATACseqPeak){
            ATACseqPeak peak = (ATACseqPeak) obj;
            if(this.toString().equals(peak.toString())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.getChromosome());
        hash = 47 * hash + this.getStart();
        hash = 47 * hash + this.getEnd();
        return hash;
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
     * @return the length
     */
    public int getLength() {
        return this.getEnd() - this.getStart();
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
        this.pvalue = pvalue;
    }

    /**
     * @return the qvalue
     */
    public double getQvalue() {
        return qvalue;
    }

    /**
     * @param qvalue the qvalue to set
     */
    public void setQvalue(double qvalue) {
        this.qvalue = qvalue;
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
     * @return the replicateNumber
     */
    public String getReplicateNumber() {
        return replicateNumber;
    }

    /**
     * @param replicateNumber the replicateNumber to set
     */
    public void setReplicateNumber(String replicateNumber) {
        this.replicateNumber = replicateNumber;
    }
    
  

    
}
