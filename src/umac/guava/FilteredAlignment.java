/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

/**
 *
 * @author mayurdivate
 */
public class FilteredAlignment {

    /**
     * @return the totalAligned
     */
    public int getTotalAligned() {
        return totalAligned;
    }

    /**
     * @param totalAligned the totalAligned to set
     */
    public void setTotalAligned(int totalAligned) {
        this.totalAligned = totalAligned;
    }

    /**
     * @return the usefulReads
     */
    public int getUsefulReads() {
        return usefulReads;
    }

    /**
     * @param usefulReads the usefulReads to set
     */
    public void setUsefulReads(int usefulReads) {
        this.usefulReads = usefulReads;
    }

    /**
     * @return the duplicateFilteredReads
     */
    public int getDuplicateFilteredReads() {
        return duplicateFilteredReads;
    }

    /**
     * @param duplicateFilteredReads the duplicateFilteredReads to set
     */
    public void setDuplicateFilteredReads(int duplicateFilteredReads) {
        this.duplicateFilteredReads = duplicateFilteredReads;
    }

    /**
     * @return the blacklistFilteredReads
     */
    public int getBlacklistFilteredReads() {
        return blacklistFilteredReads;
    }

    /**
     * @param blacklistFilteredReads the blacklistFilteredReads to set
     */
    public void setBlacklistFilteredReads(int blacklistFilteredReads) {
        this.blacklistFilteredReads = blacklistFilteredReads;
    }

    /**
     * @return the chromosomeFilteredReads
     */
    public int getChromosomeFilteredReads() {
        return chromosomeFilteredReads;
    }

    /**
     * @param chromosomeFilteredReads the chromosomeFilteredReads to set
     */
    public void setChromosomeFilteredReads(int chromosomeFilteredReads) {
        this.chromosomeFilteredReads = chromosomeFilteredReads;
    }

    public FilteredAlignment() {
    }
    
    

    
    private int totalReads;
    private int totalAligned;
    private int usefulReads;
    private int duplicateFilteredReads;
    private int blacklistFilteredReads;
    private int chromosomeFilteredReads;

    
    public FilteredAlignment(int totalReads, int usefulReads, int duplicateFilteredReads, int blacklistFilteredReads, int chromosomeFilteredReads) {
        this.usefulReads = totalReads;
        this.usefulReads = usefulReads;
        this.duplicateFilteredReads = duplicateFilteredReads;
        this.blacklistFilteredReads = blacklistFilteredReads;
        this.chromosomeFilteredReads = chromosomeFilteredReads;
    }
    
    public double getPercentage(int numerator, int denominator){
        double percentage =  ( numerator * 100.00 ) / denominator ;
        percentage = Math.round(percentage * 100.0) / 100.0;
        return percentage;
    }

    @Override
    public String toString() {
        String filetringResults = "**************************************************\n"+
                                  "totalReads "+this.getTotalReads()+"\n"+
                                  "totalAligned "+this.getTotalAligned()+"\n"+
                                  "duplicateReads "+this.getDuplicateFilteredReads()+"\n"+
                                  "chromosomeReads "+this.getChromosomeFilteredReads()+"\n"+
                                  "blacklistReads "+this.getBlacklistFilteredReads()+"\n"+
                                  "usefulReads "+this.getUsefulReads()+"\n"+
                                   "****************************************************\n";
        return filetringResults;
    }

    /**
     * @return the totalReads
     */
    public int getTotalReads() {
        return totalReads;
    }

    /**
     * @param totalReads the totalReads to set
     */
    public void setTotalReads(int totalReads) {
        this.totalReads = totalReads;
    }
    
    
    
    
}
