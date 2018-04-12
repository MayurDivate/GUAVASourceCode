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
package umac.guava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class GeneOntology {

    private String goTerm;
    private String goID;
    private String goDefination;
    private String goCategory;
    private double pvalue;
    private double adjustedPvalue;
    private int count;
    private String entrezIDs;
    private String geneSymbols;

    public GeneOntology(String goTerm, String goID, String goDefination, String goCategory, double pvalue,
            double adjustedPvalue, int count, String EntrezID, String geneSymbol) {
        this.goTerm = goTerm;
        this.goID = goID;
        this.goDefination = goDefination;
        this.goCategory = goCategory;
        this.pvalue = pvalue;
        this.adjustedPvalue = adjustedPvalue;
        this.count = count;
        this.entrezIDs = EntrezID;
        this.geneSymbols = geneSymbol;
    }

    //    "go.id"	"go.term"	"Definition"	"Ontology"	"pvalue"	"EntrezID"	"symbol"
    public GeneOntology(String goID, String goTerm, String goDefination, String goCategory, double pvalue,
            String EntrezID, String geneSymbol) {
        this.goTerm = goTerm;
        this.goID = goID;
        this.goDefination = goDefination;
        this.goCategory = goCategory;
        this.pvalue = pvalue;
        this.entrezIDs = EntrezID;
        this.geneSymbols = geneSymbol;
    }

    public static HashMap<GeneOntology, GeneOntology> parseDiffGeneOntologyAnalysisOutput(File goAnalysisFile) {

        try {

            FileReader goFileReader = new FileReader(goAnalysisFile);
            BufferedReader goBufferedReader = new BufferedReader(goFileReader);
            String line = goBufferedReader.readLine(); // discarding line 1
            HashMap<GeneOntology, GeneOntology> goHashMap = new HashMap<>();

            while ((line = goBufferedReader.readLine()) != null) {

                line = line.replaceAll("\"", "");
                String[] lineData = line.split("\t");

                //"go.id"	"go.term"	"Definition"	"Ontology"	"pvalue"	"EntrezID"	"symbol"
                String id = lineData[1];
                String term = lineData[2];
                String def = lineData[3];
                String type = lineData[4];
                double pvalue = Double.parseDouble(lineData[5]);
                String entrezIDs = lineData[6];
                String symbol = lineData[7];

                GeneOntology go = new GeneOntology(id, term, def, type, pvalue, entrezIDs, symbol);

                if (!goHashMap.containsKey(go)) {
                    goHashMap.put(go, go);
                } else {
                    goHashMap.get(go).addEntrezID(lineData[6]);
                    goHashMap.get(go).addGeneSymbol(lineData[7]);
                }

            }

            return goHashMap;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeneOntology.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneOntology.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public static HashMap<GeneOntology, GeneOntology> parseGOAnalysisOutputFile(File goAnalysisFile) {
        //System.out.println("umac.guava.GeneOntology.parseGOAnalysisOutputFile()");
        try {
            FileReader goFileReader = new FileReader(goAnalysisFile);
            BufferedReader goBufferedReader = new BufferedReader(goFileReader);

            String line = goBufferedReader.readLine(); // discarding line 1
            HashMap<GeneOntology, GeneOntology> goHashMap = new HashMap<>();

            while ((line = goBufferedReader.readLine()) != null) {
                String[] lineData = line.split("\t");

                if (lineData.length == 13) {
                    String goID = lineData[1];
                    String goTerm = lineData[2];
                    String goDef = lineData[3];
                    String goType = lineData[4];
                    double pvalue = Double.parseDouble(lineData[5]);
                    double adjPvalue = Double.parseDouble(lineData[10]);
                    int count = Integer.parseInt(lineData[6]);
                    String entrezID = lineData[11];
                    String geneSymbol = lineData[12];

                    GeneOntology go = new GeneOntology(goTerm, goID, goDef, goType,
                            pvalue, adjPvalue, count, entrezID, geneSymbol);

                    if (!goHashMap.containsKey(go)) {
                        goHashMap.put(go, go);
                    } else {
                        goHashMap.get(go).addEntrezID(lineData[11]);
                        goHashMap.get(go).addGeneSymbol(lineData[12]);
                    }

                }
            }

            return goHashMap;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeneOntology.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneOntology.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public void addEntrezID(String entrezID) {
        this.setEntrezIDs(this.getEntrezIDs() + "; " + entrezID);
    }

    public void addGeneSymbol(String geneSymbol) {
        this.setGeneSymbols(this.getGeneSymbols() + "; " + geneSymbol);
    }

    @Override
    public String toString() {
        String goString = this.getGoID();
        return goString;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GeneOntology) {
            String objString = obj.toString();
            String thisString = this.toString();
            if (objString.equals(thisString)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.getGoID());
        return hash;
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
     * @return the adjustedPvalue
     */
    public double getAdjustedPvalue() {
        return adjustedPvalue;
    }

    /**
     * @param adjustedPvalue the adjustedPvalue to set
     */
    public void setAdjustedPvalue(double adjustedPvalue) {
        this.adjustedPvalue = adjustedPvalue;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
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
     * @return the geneSymbols
     */
    public String getGeneSymbols() {
        return geneSymbols;
    }

    /**
     * @param geneSymbols the geneSymbols to set
     */
    public void setGeneSymbols(String geneSymbols) {
        this.geneSymbols = geneSymbols;
    }

}
