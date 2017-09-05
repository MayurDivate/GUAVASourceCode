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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class Pathway {
    
    private String pathwayname;
    private String keggID;
    private double pvalue;
    private String geneSymbol;
    private String entrezID;

    public Pathway(String pathwayname, String keggID, double pvalue, String entrezID,String geneSymbol) {
        this.pathwayname = pathwayname;
        this.keggID = keggID;
        this.pvalue = pvalue;
        this.entrezID = entrezID;
        this.geneSymbol = geneSymbol;
    }
    
    public void addEntrezID(String entrezID) {
        this.setEntrezID(this.getEntrezID()+"; "+entrezID);
    }
    
    public void addGeneSymbol(String geneSymbol) {
        this.setGeneSymbol(this.getGeneSymbol()+"; "+geneSymbol);
    }


    /**
     * @return the pathwayname
     */
    public String getPathwayname() {
        return pathwayname;
    }

    /**
     * @param pathwayname the pathwayname to set
     */
    public void setPathwayname(String pathwayname) {
        this.pathwayname = pathwayname;
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

    public void setPvalue(String pvalue) {
        try{
            double pv = Double.parseDouble(pvalue);
            this.setPvalue(pv);
        }catch(NumberFormatException ex){
            Logger.getLogger(Peak.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }

    /**
     * @return the keggID
     */
    public String getKeggID() {
        return keggID;
    }

    /**
     * @param keggID the keggID to set
     */
    public void setKeggID(String keggID) {
        this.keggID = keggID;
    }

//    public Pathway(String pathwayname, double pvalue, String keggID) {
//        this.pathwayname = pathwayname;
//        this.pvalue = pvalue;
//        this.keggID = keggID;
//    }
//    
//    public Pathway(String pathwayname, String pvalue, String keggID) {
//        this.pathwayname = pathwayname;
//        try{
//            double pv = Double.parseDouble(pvalue);
//            this.pvalue = pv;
//        }catch(NumberFormatException ex){
//            Logger.getLogger(Peak.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        this.keggID = keggID;
//    }
//    
    
    

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Pathway){
            String objString = obj.toString();
            String thisString = this.toString();
            if(objString.equals(thisString)){
                   return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String toString = this.getPathwayname()+" "+this.getKeggID();
        return toString;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.getPathwayname());
        hash = 79 * hash + Objects.hashCode(this.getKeggID());
        return hash;
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

    /**
     * @return the entrezID
     */
    public String getEntrezID() {
        return entrezID;
    }

    /**
     * @param entrezID the entrezID to set
     */
    public void setEntrezID(String entrezID) {
        this.entrezID = entrezID;
    }
    
    
    
    
}
