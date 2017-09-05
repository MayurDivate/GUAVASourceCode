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

import java.util.Objects;

/**
 *
 * @author mayurdivate
 */
public class GeneOntology {
    private String goID;
    private String goTerm;
    private String goDefination;
    private String goCategory;
    private String entrezIDs;
    private String geneSymbols;
    private double pvalue;

    public GeneOntology(String goID, String goTerm, String goDefination, String goCategory, String entrezIDs,String geneSymbol, double pvalue) {
        this.goID = goID;
        this.goTerm = goTerm;
        this.goDefination = goDefination;
        this.goCategory = goCategory;
        this.entrezIDs = entrezIDs;
        this.geneSymbols = geneSymbol;
        this.pvalue = pvalue;
    }

    public void addEntrezID(String entrezID) {
        this.setEntrezIDs(this.getEntrezIDs()+"; "+entrezID);
    }
    
    public void addGeneSymbol(String geneSymbol) {
        this.setGeneSymbols(this.getGeneSymbols()+"; "+geneSymbol);
    }

    @Override
    public String toString() {
        String goString =  this.getGoID();
        return goString;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof GeneOntology){
            String objString = obj.toString();
            String thisString = this.toString();
            if(objString.equals(thisString)){
                   return true;
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.goID);
        return hash;
    }

    public String getGeneSymbols() {
        return geneSymbols;
    }

    public void setGeneSymbols(String geneSymbols) {
        this.geneSymbols = geneSymbols;
    }
  
    
    /**
     * @return the goID
     */
    public String getGoID() {
        return goID;
    }

    /**
     * @param goID the goID to set
     */
    public void setGoID(String goID) {
        this.goID = goID;
    }

    /**
     * @return the goTerm
     */
    public String getGoTerm() {
        return goTerm;
    }

    /**
     * @param goTerm the goTerm to set
     */
    public void setGoTerm(String goTerm) {
        this.goTerm = goTerm;
    }

    /**
     * @return the goDefination
     */
    public String getGoDefination() {
        return goDefination;
    }

    /**
     * @param goDefination the goDefination to set
     */
    public void setGoDefination(String goDefination) {
        this.goDefination = goDefination;
    }

    /**
     * @return the goCategory
     */
    public String getGoCategory() {
        return goCategory;
    }

    /**
     * @param goCategory the goCategory to set
     */
    public void setGoCategory(String goCategory) {
        this.goCategory = goCategory;
    }

    /**
     * @return the entrezIDs
     */
    public String getEntrezIDs() {
        return entrezIDs;
    }

    /**
     * @param entrezIDs the entrezIDs to set
     */
    public void setEntrezIDs(String entrezIDs) {
        this.entrezIDs = entrezIDs;
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
    
    
    
    
}
