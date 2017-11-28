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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class Cutadapt extends Tool {
    
    private File r1_fastq;
    private File r2_fastq;
    private File trimmed_R1;
    private File trimmed_R2;
    private String adapter;
    private double errorRate;
    private int maxNs;
    private File cutadaptDir;  
    private int minLength; 

    
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outputFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String[] getCommand(Cutadapt cutadapt) {
        
        String[] commandArray =  
                    {   "cutadapt",
                        "-a", cutadapt.getAdapter(),
                        "-A", cutadapt.getAdapter(),
                        "-f", "fastq",
                        "-e", Double.toString(cutadapt.getErrorRate()),
                        "--trim-n",
                        "--max-n", Integer.toString(cutadapt.getMaxNs()),
                        "-m", Integer.toString(cutadapt.getMinLength()),
                        "-o",cutadapt.getTrimmed_R1().getAbsolutePath(),
                        "-p",cutadapt.getTrimmed_R2().getAbsolutePath(),
                        cutadapt.getR1_fastq().getAbsolutePath(),
                        cutadapt.getR2_fastq().getAbsolutePath()
                        };
        return commandArray;
    }
    
    public static Cutadapt getCutadapt(GuavaInput input,String adapter, double errorRate, int maxN,int minLength){
        
            File rootDir          = new File(input.getOutputFolder().getAbsolutePath()+System.getProperty("file.separator")+
                                               input.getR1Fastq().getName().replaceAll("\\.f(.*?)q", "_OUTPUT"));
            String sampleBasename = rootDir.getName().replaceAll("_OUTPUT", "");
            File cutadaptDir      = new File(rootDir.getAbsolutePath()+System.getProperty("file.separator")+sampleBasename+"_Adapter_Trimming");
            File r1Fastq          = input.getR1Fastq();
            File r2fastq          = input.getR2Fastq();
            String r1outTrimmed   = input.getR1Fastq().getName().replaceAll("\\.f(.*?)q", "_trimmed.fastq");
            String r2outTrimmed   = input.getR2Fastq().getName().replaceAll("\\.f(.*?)q", "_trimmed.fastq");
            File trimmedR1        = new File(cutadaptDir.getAbsolutePath()+System.getProperty("file.separator")+r1outTrimmed);
            File trimmedR2        = new File(cutadaptDir.getAbsolutePath()+System.getProperty("file.separator")+r2outTrimmed);

            Cutadapt cutadapt = new Cutadapt(r1Fastq, r2fastq, trimmedR1, trimmedR2, adapter, errorRate, maxN, cutadaptDir, minLength);
            return cutadapt;
    }
    
    public static Cutadapt getCutadapt(File r1, File r2, File outdir ,String adapter, double errorRate, int maxN,int minLength){
        
            File rootDir          = new File(outdir.getAbsolutePath()+System.getProperty("file.separator")+
                                               r1.getName().replaceAll("\\.f(.*?)q", "_OUTPUT"));
            
            String sampleBasename = rootDir.getName().replaceAll("_OUTPUT", "");
            
            File cutadaptDir      = new File(rootDir.getAbsolutePath()+System.getProperty("file.separator")+sampleBasename+"_Adapter_Trimming");
            String r1outTrimmed   = r1.getName().replaceAll("\\.f(.*?)q", "_trimmed.fastq");
            String r2outTrimmed   = r2.getName().replaceAll("\\.f(.*?)q", "_trimmed.fastq");
            File trimmedR1        = new File(cutadaptDir.getAbsolutePath()+System.getProperty("file.separator")+r1outTrimmed);
            File trimmedR2        = new File(cutadaptDir.getAbsolutePath()+System.getProperty("file.separator")+r2outTrimmed);

            Cutadapt cutadapt = new Cutadapt(r1, r2, trimmedR1, trimmedR2, adapter, errorRate, maxN, cutadaptDir, minLength);
            
            return cutadapt;
    }
    
    public Cutadapt(String adapter, double errorRate, int maxNs, int minLength) {
        this.adapter = adapter;
        this.errorRate = errorRate;
        this.maxNs = maxNs;
        this.minLength = minLength;
    }

    public Cutadapt(File r1_fastq, File r2_fastq, File trimmed_R1, File trimmed_R2, String adapter, 
            double errorRate, int maxNs, File cutadaptDir, int minLength) {
        this.r1_fastq = r1_fastq;
        this.r2_fastq = r2_fastq;
        this.trimmed_R1 = trimmed_R1;
        this.trimmed_R2 = trimmed_R2;
        this.adapter = adapter;
        this.errorRate = errorRate;
        this.maxNs = maxNs;
        this.cutadaptDir = cutadaptDir;
        this.minLength = minLength;
    }

    public Cutadapt() {
    }

    @Override
    public String toString() {
        String cutadaptObjectString = "";
        cutadaptObjectString = "R1 => "+this.getR1_fastq().getName()+"\n"
                + "R2 => "+this.getR2_fastq().getName()+"\n"
                + "Adapter Sequence => "+this.getAdapter()+"\n"
                + "Trimmed R1 => "+this.getTrimmed_R1().getName()+"\n"
                + "Trimmed R2 => "+this.getTrimmed_R2().getName()+"\n"
                + "dir => "+this.getCutadaptDir().getName()+"\n"
                + "Max N => "+this.getMaxNs()+"\n"
                + "error rate => "+this.getErrorRate()+"\n"
                + "Min len => "+this.getMinLength()+"\n";
        return cutadaptObjectString; 
    }

    @Override
    public String[] runCommand(String[] commandArray) {
        String[] log =  new String[2];
        try {
            ProcessBuilder cutadaptBuilder = new ProcessBuilder(commandArray);
            Process process =  cutadaptBuilder.start();
            String stdOUT = new Cutadapt().getSTDoutput(process);
            String errorLog = new Cutadapt().getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
        } catch (IOException ex) { 
            Logger.getLogger(Cutadapt.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return log;
    }

    @Override
    public boolean isWorking() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }
    
    
    // FASTQ files
    
    public File getR1_fastq() {
        return r1_fastq;
    }
    public void setR1_fastq(File r1_fastq) {
        this.r1_fastq = r1_fastq;
    }

    public File getR2_fastq() {
        return r2_fastq;
    }

    public void setR2_fastq(File r2_fastq) {
        this.r2_fastq = r2_fastq;
    }

    public File getTrimmed_R1() {
        return trimmed_R1;
    }

    public void setTrimmed_R1(File trimmed_R1) {
        this.trimmed_R1 = trimmed_R1;
    }

    public File getTrimmed_R2() {
        return trimmed_R2;
    }

    public void setTrimmed_R2(File trimmed_R2) {
        this.trimmed_R2 = trimmed_R2;
    }

    // ---------------
    
    public String getAdapter() {
        return adapter;
    }

    public void setAdapter(String adapter) {
        this.adapter = adapter;
    }

    /**
     * @return the errorRate
     */
    public double getErrorRate() {
        return errorRate;
    }

    /**
     * @param errorRate the errorRate to set
     */
    public void setErrorRate(double errorRate) {
        this.errorRate = errorRate;
    }

    /**
     * @return the maxNs
     */
    public int getMaxNs() {
        return maxNs;
    }

    /**
     * @param maxNs the maxNs to set
     */
    public void setMaxNs(int maxNs) {
        this.maxNs = maxNs;
    }

    /**
     * @return the cutadaptDir
     */
    public File getCutadaptDir() {
        return cutadaptDir;
    }

    /**
     * @param cutadaptDir the cutadaptDir to set
     */
    public void setCutadaptDir(File cutadaptDir) {
        this.cutadaptDir = cutadaptDir;
    }



    
}
