/*
 * Copyright (C) 2018 mayurdivate
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

import java.util.HashMap;


/**
 *
 * @author mayurdivate
 */
public class Genome {

    private String genomeName;
    private String orgCode;
    private String txdb;
    private String orgdb;
    private String orgdbSymbol;
    private String organismName;
    private String genomeSize;
    
    
    
    public static Genome getGenomeObject(String genomeBuild){
        
        HashMap<String, Genome> genomeHashMap = getAvailableGenomes();
        if(genomeHashMap.containsKey(genomeBuild)){
            return genomeHashMap.get(genomeBuild);
        }
        return null;
    }
    
    public static String[] getGenomeArray(){
        String[] genomes = {
            "-select-",
            "hg19",
            "hg38",
            "hg18",
            "mm10",
            "mm9",
            "rheMac8",
            "rheMac3",
            "rn6",
            "rn5",
            "rn4",
            "danRer10",
            "galGal4",
            "panTro4",
            "susScr3",
            "dm6",
            "dm3",
            "canFam3",
            "ce11",
            "ce6",
            "bosTau8"};
      
        return genomes;
    }
    
    
    public static HashMap<String, Genome> getAvailableGenomes(){
        HashMap<String, Genome> genomeHashMap = new HashMap<>();
        
        // url to txdb https://www.bioconductor.org/help/workflows/annotation/Annotation_Resources/
        
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        String orgName = "Homo sapiens";
        String genomeSize = "hs";
        Genome hg18 =  new Genome("hg18", "Hs", "TxDb.Hsapiens.UCSC.hg18.knownGene", "org.Hs.eg.db", "org.Hs.egSYMBOL",orgName,genomeSize);
        Genome hg19 =  new Genome("hg19", "Hs", "TxDb.Hsapiens.UCSC.hg19.knownGene", "org.Hs.eg.db", "org.Hs.egSYMBOL",orgName,genomeSize);
        Genome hg38 =  new Genome("hg38", "Hs", "TxDb.Hsapiens.UCSC.hg38.knownGene", "org.Hs.eg.db", "org.Hs.egSYMBOL",orgName,genomeSize);
        
        genomeHashMap.put("hg18", hg18);
        genomeHashMap.put("hg19", hg19);
        genomeHashMap.put("hg38", hg38);
        
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        orgName = "Mus musculus";
        genomeSize = "mm";
        Genome mm10 =  new Genome("mm10", "Mm", "TxDb.Mmusculus.UCSC.mm10.knownGene", "org.Mm.eg.db", "org.Mm.egSYMBOL",orgName,genomeSize);
        Genome mm9  =  new Genome("mm9", "Mm", "TxDb.Mmusculus.UCSC.mm9.knownGene",  "org.Mm.eg.db", "org.Mm.egSYMBOL",orgName,genomeSize);
        genomeHashMap.put("mm10", mm10);
        genomeHashMap.put("mm9", mm9);
        
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        orgName = "Macaca mulatta";
        genomeSize = "2.6e9";
        Genome rheMac3 =  new Genome("rheMac3", "Mmu", "TxDb.Mmulatta.UCSC.rheMac3.refGene", "org.Mmu.eg.db", "org.Mmu.egSYMBOL",orgName,genomeSize);
        Genome rheMac8 =  new Genome("rheMac8", "Mmu", "TxDb.Mmulatta.UCSC.rheMac3.refGene", "org.Mmu.eg.db", "org.Mmu.egSYMBOL",orgName,genomeSize);
        genomeHashMap.put("rheMac3", rheMac3);
        genomeHashMap.put("rheMac8", rheMac8);
        
        
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        orgName = "Rattus norvegicus";
        genomeSize = "2.2e9";
        Genome rn4 =  new Genome("rn4", "Rn", "TxDb.Rnorvegicus.UCSC.rn4.ensGene", "org.Rn.eg.db", "org.Rn.egSYMBOL",orgName,genomeSize);
        Genome rn5 =  new Genome("rn5", "Rn", "TxDb.Rnorvegicus.UCSC.rn5.refGene", "org.Rn.eg.db", "org.Rn.egSYMBOL",orgName,genomeSize);
        Genome rn6 =  new Genome("rn6", "Rn", "TxDb.Rnorvegicus.UCSC.rn6.refGene", "org.Rn.eg.db", "org.Rn.egSYMBOL",orgName,genomeSize);
        genomeHashMap.put("rn4",rn4 );
        genomeHashMap.put("rn5",rn5 );
        genomeHashMap.put("rn6",rn6 );

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        orgName = "Danio rerio";
        genomeSize = "1.3e9";
        Genome Dr10 =  new Genome("danRer10", "Dr", "TxDb.Drerio.UCSC.danRer10.refGene", "org.Dr.eg.db", "org.Dr.egSYMBOL",orgName,genomeSize);
        genomeHashMap.put("danRer10",Dr10 );
        
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        orgName = "Gallus gallus";
        genomeSize = "8.3e8";
        Genome gg4 =  new Genome("galGal4", "Hs", "TxDb.Ggallus.UCSC.galGal4.refGene", "org.Gg.eg.db", "org.Gg.egSYMBOL",orgName,genomeSize);
        genomeHashMap.put("galGal4",gg4 );
        
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        orgName = "Pan troglodytes";         
        genomeSize = "2.6e9";
        Genome pt4 =  new Genome("panTro4", "Hs", "TxDb.Ptroglodytes.UCSC.panTro4.refGene", "org.Pt.eg.db ", "org.Pt.egSYMBOL",orgName,genomeSize);
        genomeHashMap.put("panTro4",pt4 );
        
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        orgName = "Sus scrofa";
        genomeSize = "2.2e9";
        Genome susScr3 =  new Genome("susScr3", "Ss", "TxDb.Sscrofa.UCSC.susScr3.refGene", "org.Ss.eg.db", "org.Ss.egSYMBOL",orgName,genomeSize);
        genomeHashMap.put("susScr3",susScr3 );
        
        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        orgName = "Drosophila melanogaster";
        genomeSize = "dm";
        Genome dm3 =  new Genome("dm3", "Dm", "TxDb.Dmelanogaster.UCSC.dm3.ensGene", "org.Dm.eg.db", "org.Dm.egSYMBOL",orgName,genomeSize);
        Genome dm6 =  new Genome("dm6", "Dm", "TxDb.Dmelanogaster.UCSC.dm6.ensGene", "org.Dm.eg.db", "org.Dm.egSYMBOL",orgName,genomeSize);
        genomeHashMap.put("dm3",dm3 );
        genomeHashMap.put("dm6",dm6 );
        

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        orgName = "Canis familiaris";
        genomeSize = "2e9";
        Genome cf3 =  new Genome("canFam3", "Cf", "TxDb.Cfamiliaris.UCSC.canFam3.refGene", "org.Cf.eg.db", "org.Cf.egSYMBOL",orgName,genomeSize);
        genomeHashMap.put("canFam3",cf3 );
        

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        orgName = "Caenorhabditis elegans";
        genomeSize = "ce";
        Genome ce6  =  new Genome("ce6", "Ce", "TxDb.Celegans.UCSC.ce6.ensGene", "org.Ce.eg.db", "org.Ce.egSYMBOL",orgName,genomeSize);
        Genome ce11 =  new Genome("ce11", "Ce", "TxDb.Celegans.UCSC.ce11.refGene", "org.Ce.eg.db", "org.Ce.egSYMBOL",orgName,genomeSize);
        genomeHashMap.put("ce6",ce6 );
        genomeHashMap.put("ce11",ce11);
        
        orgName = "Bos taurus";
        genomeSize = "2.1e9";
        Genome bt8 =  new Genome("bosTau8", "Bt", "TxDb.Btaurus.UCSC.bosTau8.refGene", "org.Bt.eg.db", "org.Bt.egSYMBOL",orgName,genomeSize);
        genomeHashMap.put("bosTau8",bt8);
        
        return genomeHashMap;
    }

    public Genome(String genomeName, String orgCode, String txdb, String orgdb, String orgdbSymbol, String organismName, String genomeSize) {
        this.genomeName = genomeName;
        this.orgCode = orgCode;
        this.txdb = txdb;
        this.orgdb = orgdb;
        this.orgdbSymbol = orgdbSymbol;
        this.organismName = organismName;
        this.genomeSize = genomeSize;
    }

    
    /**
     * @return the genomeName
     */
    public String getGenomeName() {
        return genomeName;
    }

    /**
     * @param genomeName the genomeName to set
     */
    public void setGenomeName(String genomeName) {
        this.genomeName = genomeName;
    }

    /**
     * @return the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * @param orgCode the orgCode to set
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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
     * @return the orgdb
     */
    public String getOrgdb() {
        return orgdb;
    }

    /**
     * @param orgdb the orgdb to set
     */
    public void setOrgdb(String orgdb) {
        this.orgdb = orgdb;
    }

    /**
     * @return the orgdbSymbol
     */
    public String getOrgdbSymbol() {
        return orgdbSymbol;
    }

    /**
     * @param orgdbSymbol the orgdbSymbol to set
     */
    public void setOrgdbSymbol(String orgdbSymbol) {
        this.orgdbSymbol = orgdbSymbol;
    }

    /**
     * @return the organismName
     */
    public String getOrganismName() {
        return organismName;
    }

    /**
     * @param organismName the organismName to set
     */
    public void setOrganismName(String organismName) {
        this.organismName = organismName;
    }

    /**
     * @return the genomeSize
     */
    public String getGenomeSize() {
        return genomeSize;
    }

    /**
     * @param genomeSize the genomeSize to set
     */
    public void setGenomeSize(String genomeSize) {
        this.genomeSize = genomeSize;
    }
    
    
    
    
}
