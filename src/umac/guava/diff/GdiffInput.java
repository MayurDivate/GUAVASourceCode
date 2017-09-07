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
    
    public File getOutputfolder() {
        return outputfolder;
    }

    public void setOutputfolder(File outputfolder) {
        this.outputfolder = outputfolder;
    }

    public DifferentialOutputFiles getDifferentialOutputFiles() {
        return differentialOutputFiles;
    }

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGenomeBuild() {
        return genomeBuild;
    }

    public void setGenomeBuild(String genomeBuild) {
        this.genomeBuild = genomeBuild;
    }

    public int getUpstream() {
        return upstream;
    }

    public void setUpstream(int upstream) {
        if(upstream < 0){
        this.upstream = upstream;
        }
    }

    public int getDownstream() {
        return downstream;
    }

    public void setDownstream(int downstream) {
        if(downstream > 0 ){
        this.downstream = downstream;
        }
    }

    @Override
    public String toString() {
        String gdiffInput = "";
        gdiffInput = gdiffInput + " project name = "+ this.getProjectName() + "\n";
        gdiffInput = gdiffInput + " fold change = "+this.getFoldChange()+ "\n";
        gdiffInput = gdiffInput + " p value = "+this.getPvalue()+ "\n";
        gdiffInput = gdiffInput + " upstream = "+this.getUpstream()+ "\n";
        gdiffInput = gdiffInput + " downstream = "+this.getDownstream()+ "\n";
        gdiffInput = gdiffInput + " out dir = "+this.getOutputfolder()+ "\n";
        
        for(int i=0; i < this.getDiffInputfiles().size(); i++ ){
            gdiffInput = gdiffInput + "Diff file["+i+"]"+this.getDiffInputfiles().get(i) +"\n";
        }
        
        return gdiffInput; 
    }

   
    
}
