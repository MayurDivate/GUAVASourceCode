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
    
    
    public static Genome getGenomeObject(String genomeBuild){
        
        HashMap<String, Genome> genomeHashMap = new HashMap<>();
        
        // Homo sapiens
        Genome hg18 =  new Genome("hg18", "Hs", "TxDb.Hsapiens.UCSC.hg18.knownGene", "org.Hs.eg.db", "org.Hs.egSYMBOL");
        Genome hg19 =  new Genome("hg19", "Hs", "TxDb.Hsapiens.UCSC.hg19.knownGene", "org.Hs.eg.db", "org.Hs.egSYMBOL");
        Genome hg38 =  new Genome("hg38", "Hs", "TxDb.Hsapiens.UCSC.hg38.knownGene", "org.Hs.eg.db", "org.Hs.egSYMBOL");
        
        genomeHashMap.put("hg18", hg18);
        genomeHashMap.put("hg19", hg19);
        genomeHashMap.put("hg38", hg38);
        
        //## 12 Mus musculus       
        Genome mm10 =  new Genome("mm10", "Mm", "TxDb.Mmusculus.UCSC.mm10.knownGene", "org.Mm.eg.db", "org.Mm.egSYMBOL");
        Genome mm9  =  new Genome("mm9", "Mm", "TxDb.Mmusculus.UCSC.mm9.knownGene",  "org.Mm.eg.db", "org.Mm.egSYMBOL");
        genomeHashMap.put("mm10", mm10);
        genomeHashMap.put("mm9", mm9);
        
        //Macaca mulatta           
        Genome rheMac3 =  new Genome("rheMac3", "Mmu", "TxDb.Mmulatta.UCSC.rheMac3.refGene", "org.Mmu.eg.db", "org.Mmu.egSYMBOL");
        Genome rheMac8 =  new Genome("rheMac8", "Mmu", "TxDb.Mmulatta.UCSC.rheMac3.refGene", "org.Mmu.eg.db", "org.Mmu.egSYMBOL");
        genomeHashMap.put("rheMac3", rheMac3);
        genomeHashMap.put("rheMac8", rheMac8);
        
        
        //18 Rattus norvegicus       org.Rn.eg.db       
        Genome rn4 =  new Genome("rn4", "Rn", "TxDb.Rnorvegicus.UCSC.rn4.ensGene", "org.Rn.eg.db", "org.Rn.egSYMBOL");
        Genome rn5 =  new Genome("rn5", "Rn", "TxDb.Rnorvegicus.UCSC.rn5.refGene", "org.Rn.eg.db", "org.Rn.egSYMBOL");
        Genome rn6 =  new Genome("rn6", "Rn", "TxDb.Rnorvegicus.UCSC.rn6.refGene", "org.Rn.eg.db", "org.Rn.egSYMBOL");
        genomeHashMap.put("rn4",rn4 );
        genomeHashMap.put("rn5",rn5 );
        genomeHashMap.put("rn6",rn6 );

        //Danio rerio               
        Genome Dr10 =  new Genome("Dr10", "Dr", "TxDb.Drerio.UCSC.danRer10.refGene", "org.Dr.eg.db", "org.Dr.egSYMBOL");
        genomeHashMap.put("Dr10",Dr10 );
        
        //Gallus gallus
        Genome gg4 =  new Genome("gg4", "Hs", "TxDb.Ggallus.UCSC.galGal4.refGene", "org.Gg.eg.db", "org.Gg.egSYMBOL");
        genomeHashMap.put("gg4",gg4 );
        
        //Pan troglodytes         
        Genome pt4 =  new Genome("pt4", "Hs", "TxDb.Ptroglodytes.UCSC.panTro4.refGene", "org.Pt.eg.db ", "org.Pt.egSYMBOL");
        genomeHashMap.put("pt4",pt4 );
        
        //Sus scrofa              
        Genome susScr3 =  new Genome("susSc3", "Ss", "TxDb.Sscrofa.UCSC.susScr3.refGene", "org.Ss.eg.db", "org.Ss.egSYMBOL");
        genomeHashMap.put("susScr3",susScr3 );
        
        // Drosophila melanogaster  
        Genome dm3 =  new Genome("dm3", "Dm", "TxDb.Dmelanogaster.UCSC.dm3.ensGene", "org.Dm.eg.db", "org.Dm.egSYMBOL");
        Genome dm6 =  new Genome("dm6", "Dm", "TxDb.Dmelanogaster.UCSC.dm6.ensGene", "org.Dm.eg.db", "org.Dm.egSYMBOL");
        genomeHashMap.put("dm3",dm3 );
        genomeHashMap.put("dm6",dm6 );
        

        //Canis familiaris           
        Genome cf3 =  new Genome("cf3", "Cf", "TxDb.Cfamiliaris.UCSC.canFam3.refGene", "org.Cf.eg.db", "org.Cf.egSYMBOL");
        genomeHashMap.put("cf3",cf3 );
        

        //Caenorhabditis elegans          
        Genome ce6  =  new Genome("ce6", "Ce", "TxDb.Celegans.UCSC.ce6.ensGene", "org.Ce.eg.db", "org.Ce.egSYMBOL");
        Genome ce11 =  new Genome("ce11", "Ce", "TxDb.Celegans.UCSC.ce11.refGene", "org.Ce.eg.db", "org.Ce.egSYMBOL");
        genomeHashMap.put("ce6",ce6 );
        genomeHashMap.put("ce11",ce11);
        
        //Bos taurus
        Genome bt8 =  new Genome("bt8", "Bt", "TxDb.Btaurus.UCSC.bosTau8.refGene", "org.Bt.eg.db", "org.Bt.egSYMBOL");
        genomeHashMap.put("bt8",bt8);
        
        if(genomeHashMap.containsKey(genomeBuild)){
            return genomeHashMap.get(genomeBuild);
        }
        
        return null;
    
    }

    public Genome(String genomeName, String orgCode, String txdb, String orgdb, String orgdbSymbol) {
        this.genomeName = genomeName;
        this.orgCode = orgCode;
        this.txdb = txdb;
        this.orgdb = orgdb;
        this.orgdbSymbol = orgdbSymbol;
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
    
    
    
    
}
