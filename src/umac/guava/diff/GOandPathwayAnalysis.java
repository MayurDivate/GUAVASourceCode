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
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import umac.guava.Bowtie;
import static umac.guava.diff.Program.getArrayToString;

/**
 *
 * @author mayurdivate
 */
public class GOandPathwayAnalysis extends Program {
    
    private File inputPeakBedFile;
    private File goOutputFile;
    private File pathwayOutputFile;
    private String txdb = "TxDb.Hsapiens.UCSC.hg19.knownGene";
    private String ensdb = "EnsDb.Hsapiens.v75";
    private String org = "org.Hs.eg.db";
        
    public String getGOPathwayAnalysisCode(int upstream , int downstream ){
    
        String txdb = "TxDb.Hsapiens.UCSC.hg19.knownGene";
        String ensdb = "EnsDb.Hsapiens.v75";
        String org = "org.Hs.eg.db";
                
        String code = "";

        code = code + "\n" + "library(ChIPpeakAnno)";
        code = code + "\n" + "d2res <- read.table(\""+this.getInputPeakBedFile()+"\")";
        code = code + "\n" + "d2res <-d2res[d2res$regulation != \"not-changed\",]";
        code = code + "\n" + "peaks <- toGRanges(d2res[,c(2:4,1)], format=\"BED\", header=FALSE) ";
        code = code + "\n" + "library("+txdb+")";
        code = code + "\n" + "annoData <- toGRanges("+txdb+", feature=\"gene\")";
        code = code + "\n" + "seqlevelsStyle(peaks) <- seqlevelsStyle(annoData)";
        code = code + "\n" + "annotatedPeaks <- annotatePeakInBatch(peaks, \n" +
                            "AnnotationData=annoData, \n" +
                            "output=\"nearestBiDirectionalPromoters\",\n" +
                            "PeakLocForDistance=\"middle\",\n" +
                            "bindingRegion=c("+upstream+","+downstream+"))";
        code = code + "\n" + "library("+org+")";
        code = code + "\n" + "annotatedPeaks$symbol <- xget(annotatedPeaks$feature,org.Hs.egSYMBOL)";
        code = code + "\n" + "annotatedPeaks$feature <- annotatedPeaks$symbol";
        code = code + "\n" + "library(GO.db)";
        code = code + "\n" + "enrichedGO <- getEnrichedGO(annotatedPeaks,orgAnn = \""+org+"\",maxP=0.001,"
                + "feature_id_type = \"gene_symbol\")\n";
        code = code + "\n" + "bp <- unique(enrichedGO[['bp']][,c(1,2,3,4,7,10)])";
        code = code + "\n" + "cc <- unique(enrichedGO[['cc']][,c(1,2,3,4,7,10)])";
        code = code + "\n" + "mf <- unique(enrichedGO[['mf']][,c(1,2,3,4,7,10)])";
        code = code + "\n" + "gores <- rbind.data.frame(bp,cc,mf)";
        code = code + "\n" + "gores$symbol <- xget(as.character(gores$EntrezID),org.Hs.egSYMBOL)";
        code = code + "\n" + "write.table(gores,\""+this.getGoOutputFile()+"\", sep = \"\\t\" )";
        code = code + "\n" + "library(KEGG.db)";
        code = code + "\n" + "path <- getEnrichedPATH(annotatedPeaks,orgAnn = \""+org+"\",pathAnn = \"KEGG.db\","
                + "feature_id_type=\"gene_symbol\", maxP=0.05)";
        code = code + "\n" + "path <- unique(path[,c(8,5,1,2)])";
        code = code + "\n" + "path$symbol <- xget(as.character(path$EntrezID),org.Hs.egSYMBOL)";
        code = code + "\n" + "write.table(path,\""+this.getPathwayOutputFile()+"\", sep = \"\\t\" )";
        return code;
    }
    
    @Override
    public String[] getCommand(File inputFile) {
        System.out.println("DESeq2");
        String[] commandArray =  
            {   "Rscript",
                inputFile.getAbsolutePath()
            };
        return commandArray;
    }

    @Override
    public String[] runCommand(String[] commandArray) {
        String[] log =  new String[2];
       
        try {
            System.out.println(getArrayToString(commandArray));
            ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
            Process process =  processBuilder.start();
            String stdOUT = getSTDoutput(process);
            String errorLog = getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
            return log;
        } catch (IOException ex) {
            System.out.println("\t\t"+ex.getMessage());
            return null;
        }
    }

    @Override
    public boolean isWorking() {
        return true;
    }

    public GOandPathwayAnalysis(File inputPeakBedFile, File goOutputFile, File pathwayOutputFile) {
        this.inputPeakBedFile = inputPeakBedFile;
        this.goOutputFile = goOutputFile;
        this.pathwayOutputFile = pathwayOutputFile;
    }

    public GOandPathwayAnalysis() {
    }

    
    /**
     * @return the inputPeakBedFile
     */
    public File getInputPeakBedFile() {
        return inputPeakBedFile;
    }

    /**
     * @param inputPeakBedFile the inputPeakBedFile to set
     */
    public void setInputPeakBedFile(File inputPeakBedFile) {
        this.inputPeakBedFile = inputPeakBedFile;
    }

    /**
     * @return the goOutputFile
     */
    public File getGoOutputFile() {
        return goOutputFile;
    }

    /**
     * @param goOutputFile the goOutputFile to set
     */
    public void setGoOutputFile(File goOutputFile) {
        this.goOutputFile = goOutputFile;
    }

    /**
     * @return the pathwayOutputFile
     */
    public File getPathwayOutputFile() {
        return pathwayOutputFile;
    }

    /**
     * @param pathwayOutputFile the pathwayOutputFile to set
     */
    public void setPathwayOutputFile(File pathwayOutputFile) {
        this.pathwayOutputFile = pathwayOutputFile;
    }

    /**
     * @return the txdb
     */
    public String getTxdb() {
        return txdb;
    }

    /**
     * @param txdb the txdb to set
     */
    public void setTxdb(String txdb) {
        this.txdb = txdb;
    }

    /**
     * @return the ensdb
     */
    public String getEnsdb() {
        return ensdb;
    }

    /**
     * @param ensdb the ensdb to set
     */
    public void setEnsdb(String ensdb) {
        this.ensdb = ensdb;
    }

    /**
     * @return the org
     */
    public String getOrg() {
        return org;
    }

    /**
     * @param org the org to set
     */
    public void setOrg(String org) {
        this.org = org;
    }
    
    
    
}
