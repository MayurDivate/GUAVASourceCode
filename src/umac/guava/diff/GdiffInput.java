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

import umac.guava.Input;
import java.io.File;
import java.util.ArrayList;


/**
 *
 * @author mayurdivate
 */
public class GdiffInput extends Input{
    
    private File outputfolder;
    private DifferentialOutputFiles differentialOutputFiles;
    private double foldChange;
    private double pvalue;
    private ArrayList<DifferentialInputFile> diffInputfiles;
    private String projectName;
    private String genomeBuild;
    private int upstream;
    private int downstream;
    
    /**
     * @return the outputfolder
     */
    public File getOutputfolder() {
        return outputfolder;
    }

    /**
     * @param outputfolder the outputfolder to set
     */
    public void setOutputfolder(File outputfolder) {
        this.outputfolder = outputfolder;
    }

    /**
     * @return the differentialOutputFiles
     */
    public DifferentialOutputFiles getDifferentialOutputFiles() {
        return differentialOutputFiles;
    }

    /**
     * @param differentialOutputFiles the differentialOutputFiles to set
     */
    public void setDifferentialOutputFiles(DifferentialOutputFiles differentialOutputFiles) {
        this.differentialOutputFiles = differentialOutputFiles;
    }

    public double getFoldChange() {
        return foldChange;
    }

    public void setFoldChange(double foldChange) {
        this.foldChange = foldChange;
    }

    public double getPvalue() {
        return pvalue;
    }

    public void setPvalue(double pvalue) {
        this.pvalue = pvalue;
    }

    public ArrayList<DifferentialInputFile> getDiffInputfiles() {
        return diffInputfiles;
    }

    public void setDiffInputfiles(ArrayList<DifferentialInputFile> diffInputfiles) {
        this.diffInputfiles = diffInputfiles;
    }

    public GdiffInput(File outputfolder, DifferentialOutputFiles differentialOutputFiles, double foldChange, double pvalue,
            ArrayList<DifferentialInputFile> diffInputfiles, String projectName, String genomeBuild, int upstream, int downstream) {
        this.outputfolder = outputfolder;
        this.differentialOutputFiles = differentialOutputFiles;
        this.foldChange = foldChange;
        this.pvalue = pvalue;
        this.diffInputfiles = diffInputfiles;
        this.projectName = projectName;
        this.genomeBuild = genomeBuild;
        if(upstream < 0){
            this.upstream = upstream;
        }
        else if(upstream > 0){
            this.upstream = -1 * upstream;
        }
        else{
            this.upstream =  0;
        }
        this.downstream = downstream;
    }

    
//    public ATACseqDiffInput(File outputfolder, DifferentialOutputFiles differentialOutputFiles, double foldChange, double pvalue, int minimumOverlap, int maximumPeakLength, ArrayList<DifferentialInputFile> diffInputfiles) {
//        this.outputfolder = outputfolder;
//        this.differentialOutputFiles = differentialOutputFiles;
//        this.foldChange = foldChange;
//        this.pvalue = pvalue;
//        this.minimumOverlap = minimumOverlap;
//        this.maximumPeakLength = maximumPeakLength;
//        this.diffInputfiles = diffInputfiles;
//    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the genomeBuild
     */
    public String getGenomeBuild() {
        return genomeBuild;
    }

    /**
     * @param genomeBuild the genomeBuild to set
     */
    public void setGenomeBuild(String genomeBuild) {
        this.genomeBuild = genomeBuild;
    }

    /**
     * @return the upstream
     */
    public int getUpstream() {
        return upstream;
    }

    /**
     * @param upstream the upstream to set
     */
    public void setUpstream(int upstream) {
        if(upstream < 0){
        this.upstream = upstream;
        }
    }

    /**
     * @return the downstream
     */
    public int getDownstream() {
        return downstream;
     
    }

    /**
     * @param downstream the downstream to set
     */
    public void setDownstream(int downstream) {
        if(downstream > 0 ){
        this.downstream = downstream;
        }
    }

    @Override
    public String toString() {
        String atacdiString = "";
        atacdiString = atacdiString + " project name = "+ this.getProjectName();
        atacdiString = atacdiString + " fold change = "+this.getFoldChange();
        atacdiString = atacdiString + " p value = "+this.getPvalue();
        atacdiString = atacdiString + " upstream = "+this.getUpstream();
        atacdiString = atacdiString + " downstream = "+this.getDownstream();
        atacdiString = atacdiString + " out dir = "+this.getOutputfolder();
        
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
