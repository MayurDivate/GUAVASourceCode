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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import umac.guava.diff.DifferentialInputFile;

/**
 *
 * @author mayurdivate
 */
public class IGVdataTrack {
    private File bamFile;
    private File bdgFile;
    private File bigWigFile;
    private double scale;
    private Genome genome;
    
    public double getRPMScale(){
        int totalReads =  new Samtools().getReadCount(this.getBamFile());
        double million = 1000000;
        double scale = Math.round((million / totalReads)*100.0)/100.0;
        return scale;
    }

    public IGVdataTrack(File bamFile, File bdgFile, File bigWigFile, Genome genome) {
        this.bamFile = bamFile;
        this.bdgFile = bdgFile;
        this.bigWigFile = bigWigFile;
        this.genome = genome;
    }
    
    public boolean createDataTrackFromBamFile(){
        
        this.setScale(this.getRPMScale());
        boolean flag = false;
        this.createEmptyFile(this.getBdgFile());
        
        // temporary bedgraph before sort
        File tmpBdg = new File(this.getBdgFile().getAbsolutePath().replaceAll(".bdg$", "_tmp.bdg"));
        this.createEmptyFile(tmpBdg);
      
        //create bedgraph 
        BedTools bedTools = new BedTools(tmpBdg);
        flag = this.createBedGraphFromBam(bedTools);
        
        //sort bedgraph
        bedTools.setOutputFile(this.getBdgFile());
        
        if(flag){
            flag = this.sortBedGraph(bedTools, tmpBdg);
        }        

        //create bigwig
        if(flag){
            flag = this.convertBdgToBw();
            tmpBdg.delete();
            this.getBdgFile().delete();
        }
        
        return flag;

    }
    
    boolean createBedGraphFromBam(BedTools bedTools){
        String[] commad = bedTools.getBamTobdgCommand(this);
        String[] bedgraphlog = bedTools.runCommand(commad);
        bedTools.writeLog(bedgraphlog, "Bam To bedgraph");
        boolean success = bedTools.getOutputFile().exists();
        
        if(!success){
            System.out.println("could not create bedgraph file");
        }
        return success;
    }

    boolean sortBedGraph(BedTools bedTools,File inputBed){
        String[] commad = bedTools.getBedSortCommand(inputBed);
        String[] bedgraphlog = bedTools.runCommand(commad);
        bedTools.writeLog(bedgraphlog, "sort bedgraph");
        boolean success = bedTools.getOutputFile().exists();
        if(!success){
            System.out.println("could not sort bedgraph file");
        }
        
        return success;
    }
    
    boolean convertBdgToBw(){
        UCSCtools ucsc = new UCSCtools();
        String[] ucsclog = ucsc.runCommand(ucsc.getBedgraphToBigwigCommand(this));
        ucsc.writeLog(ucsclog, "ucsc bdgToBigwig");
        boolean  success = this.getBigWigFile().exists();
        if(!success){
            System.out.println("could not convert bedgraph to bigwig file");
        }

        return success;
    }
    
    public static File[]  getDifferentialTracks(ArrayList<DifferentialInputFile> dfInputList, Genome genome){
        
        File[] igvtracks =  new File[1+(dfInputList.size() / 2)];
        int tindex = 0;
        for(int index=0; index < dfInputList.size(); index++){
            
            if(dfInputList.get(index).getType().equalsIgnoreCase("bam")){
                
                File bamFile = dfInputList.get(index).getDiifInputFile();
                File bdgFile = new File(bamFile.getAbsolutePath().replaceAll("bam$", "bdg"));
                File bwFile = new File(bamFile.getAbsolutePath().replaceAll("bam$", "bw"));
                
                if(!bwFile.exists()){
                    System.out.println("No such track found");
                    System.out.println(bwFile.getAbsolutePath());
                    System.out.println("Creating new track, this may take while please wait...");
                    IGVdataTrack igvDataTrack = new IGVdataTrack(bamFile, bdgFile, bwFile, genome);
                    boolean track = igvDataTrack.createDataTrackFromBamFile();
                    if(track){
                        igvtracks[tindex] = bwFile;
                        tindex++;
                    }
                }else{
                    igvtracks[tindex] = bwFile;
                    tindex++;
                }
            }
        }
        
        return igvtracks;
    }
   
    
        /**
     * @return the bamFile
     */
    public File getBamFile() {
        return bamFile;
    }

    /**
     * @param bamFile the bamFile to set
     */
    public void setBamFile(File bamFile) {
        this.bamFile = bamFile;
    }

    /**
     * @return the bdgFile
     */
    public File getBdgFile() {
        return bdgFile;
    }

    /**
     * @param bdgFile the bdgFile to set
     */
    public void setBdgFile(File bdgFile) {
        this.bdgFile = bdgFile;
    }

    /**
     * @return the bigWigFile
     */
    public File getBigWigFile() {
        return bigWigFile;
    }

    /**
     * @param bigWigFile the bigWigFile to set
     */
    public void setBigWigFile(File bigWigFile) {
        this.bigWigFile = bigWigFile;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    public double getScale() {
        return scale;
    }
    
    public boolean createEmptyFile(File infile){
        try {
            if(infile.exists() &&  !infile.isDirectory()){
                infile.delete();
                return infile.createNewFile();
            }else{
                return infile.createNewFile();
            }
        } catch (IOException ex) {
                Logger.getLogger(IGVdataTrack.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return false;
    }

    /**
     * @return the genome
     */
    public Genome getGenome() {
        return genome;
    }

    /**
     * @param genome the genome to set
     */
    public void setGenome(Genome genome) {
        this.genome = genome;
    }
    
    
    
    
}
