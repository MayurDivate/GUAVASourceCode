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
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class DESeq2AnnotatedPeaks {
    private String chromosome;
    private int start;
    private int end; 
    private int length; 
    private String name;
    private double foldchange;
    private double pvalue;
    private String adjPvalue;
    private String regulation;
    private String geneSymbol;
    private int distance;
    

    
    static ArrayList<DESeq2AnnotatedPeaks> getDESeq2ResultList(File deseqResultFile){
       
        try{
            FileReader deseqResultFR = new FileReader(deseqResultFile);
            BufferedReader deseqResultBR = new BufferedReader(deseqResultFR);
            String line ;
            ArrayList<DESeq2AnnotatedPeaks>  resultList =  new ArrayList<>();
            
            while((line = deseqResultBR.readLine()) != null){
                line = line.replaceAll("\"", "");
                String[] lineData =  line.split("\t");
                System.out.println(lineData[1]+" ~~~ "+lineData.length);

                if (lineData.length == 20 && !lineData[11].equalsIgnoreCase("No-change")) {
                    
                        String peakName = lineData[1];
                        String chr = lineData[2];
                        int start = Integer.parseInt(lineData[3]);
                        int end = Integer.parseInt(lineData[4]);
                        int length = Integer.parseInt(lineData[5]);
                        int distance = Integer.parseInt(lineData[9]);
                        double foldChange = Double.parseDouble(lineData[14]);
                        double pvalue = Double.parseDouble(lineData[17]);
                        double adjPvalue = 1000;
                        if (!lineData[18].equalsIgnoreCase("NA")) {
                            adjPvalue = Double.parseDouble(lineData[18]);
                        }
                        
                        String adjP =  "NA";
                        if(adjPvalue != 1000){
                            adjP = ""+adjPvalue ;
                        }
                        
                        String regulation = lineData[19];
                        String geneSymbol = lineData[11];

                        DESeq2AnnotatedPeaks deseqResult = new DESeq2AnnotatedPeaks(peakName, chr, start, end, length,
                                foldChange, pvalue, adjP, regulation,
                                geneSymbol, distance);
                        resultList.add(deseqResult);

                }
            }
            
        
        return resultList;
        
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DESeq2AnnotatedPeaks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DESeq2AnnotatedPeaks.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       return null;
       
   } 


    public DESeq2AnnotatedPeaks(String name, String chromosome, int start, int end, int length,
            double foldchange, double pvalue, String adjPvalue, String regulation,
            String geneSymbol , int distance   ) {
        this.name = name;
        this.chromosome = chromosome;
        this.start = start;
        this.end = end;
        this.length = length;
        this.foldchange = foldchange;
        this.pvalue = pvalue;
        this.adjPvalue = adjPvalue;
        this.regulation = regulation;
        this.geneSymbol = geneSymbol;
        this.distance = distance;
                
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DESeq2AnnotatedPeaks){
            DESeq2AnnotatedPeaks dres = (DESeq2AnnotatedPeaks) obj;
            if(dres.getName().equals(this.getName())){
                return true;
            }
        } 
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.getName());
        return hash;
    }

    
    
    public String getRegulation() {
        return regulation;
    }

    public void setRegulation(String regulation) {
        this.regulation = regulation;
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
        this.foldchange = foldchange;
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

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    
    public String getGeneSymbol() {
        return geneSymbol;
    }

    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }

    /**
     * @return the adjPvalue
     */
    public String getAdjPvalue() {
        return adjPvalue;
    }

    /**
     * @param adjPvalue the adjPvalue to set
     */
    public void setAdjPvalue(String adjPvalue) {
        this.adjPvalue = adjPvalue;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }
   
}
