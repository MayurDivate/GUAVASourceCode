/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class PeakTable {
    
    private String chromosome;
    private int start;
    private int end;
    private int length;
    private int pileupHeight;
    private double pvalue;
    private double qvalue;
    private String annotation;
    private int distanceToTSS;
    private String geneSymbol;

    public PeakTable(String chromosome, int start, int end, int length, int pileupHeight, double pvalue, double qvalue, String annotation, int distanceToTSS, String geneSymbol) {
        this.chromosome = chromosome;
        this.start = start;
        this.end = end;
        this.length = length;
        this.pileupHeight = pileupHeight;
        this.pvalue = pvalue;
        this.qvalue = qvalue;
        this.annotation = annotation;
        this.distanceToTSS = distanceToTSS;
        this.geneSymbol = geneSymbol;
    }

//    public PeakTable() {
//    }
//    
    
    
    public static ArrayList getPeakList(File geneAnnotationFile){
        
        ArrayList<PeakTable> peakTableList = new ArrayList<>();
        
        try {
            FileReader geneAnnotationFileReader = new FileReader(geneAnnotationFile);
            BufferedReader gaFileBufferedReader = new BufferedReader(geneAnnotationFileReader);
            String line = gaFileBufferedReader.readLine();
            
            while((line = gaFileBufferedReader.readLine()) != null){
                String [] lineData = line.split("\\t");
                if(lineData.length == 16){
                    String chr = lineData[1];
                    int start = Integer.parseInt(lineData[2]);
                    int end = Integer.parseInt(lineData[3]);
                    int length  = Integer.parseInt(lineData[4]);
                    int pileupHeight = Integer.parseInt(lineData[5]);
                    double pvalue = Double.parseDouble(lineData[6]);
                    double qvalue = Double.parseDouble(lineData[7]);
                    String anno = lineData[11];
                    int distTSS = new BigDecimal(lineData[12]).intValue();
                    String geneSymbol = lineData[13];

                    PeakTable peakTable = new PeakTable(chr, start, end, length, pileupHeight, pvalue, qvalue, anno, distTSS, geneSymbol);
                    peakTableList.add(peakTable);
                    
                }
            }
            return peakTableList;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PeakTable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PeakTable.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
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
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the pileupHeight
     */
    public int getPileupHeight() {
        return pileupHeight;
    }

    /**
     * @param pileupHeight the pileupHeight to set
     */
    public void setPileupHeight(int pileupHeight) {
        this.pileupHeight = pileupHeight;
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
     * @return the annotation
     */
    public String getAnnotation() {
        return annotation;
    }

    /**
     * @param annotation the annotation to set
     */
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    /**
     * @return the distanceToTSS
     */
    public int getDistanceToTSS() {
        return distanceToTSS;
    }

    /**
     * @param distanceToTSS the distanceToTSS to set
     */
    public void setDistanceToTSS(int distanceToTSS) {
        this.distanceToTSS = distanceToTSS;
    }

    /**
     * @return the geneSymbol
     */
    public String getGeneSymbol() {
        return geneSymbol;
    }

    /**
     * @param geneSymbol the geneSymbol to set
     */
    public void setGeneSymbol(String geneSymbol) {
        this.geneSymbol = geneSymbol;
    }
    
}
