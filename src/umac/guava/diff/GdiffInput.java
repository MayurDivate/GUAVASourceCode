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
import umac.guava.Genome;


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
    private Genome genome;
    private int upstream;
    private int downstream;
    private String method;
    private int cpus;

    public GdiffInput(File outputfolder, DifferentialOutputFiles differentialOutputFiles, 
            double foldChange, double pvalue, ArrayList<DifferentialInputFile> diffInputfiles, 
            String projectName, Genome genome, int upstream, int downstream, String method, int cpus) {
        this.outputfolder = outputfolder;
        this.differentialOutputFiles = differentialOutputFiles;
        this.foldChange = foldChange;
        this.pvalue = pvalue;
        this.diffInputfiles = diffInputfiles;
        this.projectName = projectName;
        this.genome = genome;
        this.upstream = upstream;
        this.downstream = downstream;
        this.method = method;
        this.cpus = cpus;
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
            gdiffInput = gdiffInput + i+"\t"
                    +this.getDiffInputfiles().get(i).getCondition()+"\t"
                    +this.getDiffInputfiles().get(i).getReplicateNumber()+"\t"
                    +this.getDiffInputfiles().get(i).getType()+"\t"
                    +this.getDiffInputfiles().get(i) +"\n";
        }
        
        return gdiffInput; 
    }

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

    /**
     * @return the foldChange
     */
    public double getFoldChange() {
        return foldChange;
    }

    /**
     * @param foldChange the foldChange to set
     */
    public void setFoldChange(double foldChange) {
        this.foldChange = foldChange;
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
     * @return the diffInputfiles
     */
    public ArrayList<DifferentialInputFile> getDiffInputfiles() {
        return diffInputfiles;
    }

    /**
     * @param diffInputfiles the diffInputfiles to set
     */
    public void setDiffInputfiles(ArrayList<DifferentialInputFile> diffInputfiles) {
        this.diffInputfiles = diffInputfiles;
    }

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

    /**
     * @return the upstream
     */
    public int getUpstream() {
        return upstream;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the cpus
     */
    public int getCpus() {
        return cpus;
    }

    /**
     * @param cpus the cpus to set
     */
    public void setCpus(int cpus) {
        this.cpus = cpus;
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
   
    
}
