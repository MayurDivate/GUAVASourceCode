/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mayurdivate
 */
public class MACS2 extends Tool{
    
    private File peaksXLS;
    private File controlBdg;
    private File narrowPeak;
    private File summitBed;
    private File treatPileupBdg;
    
    private static String MACS = "macs2";
    
    public static MACS2 getMACS2(){
        
        String sampleBasename = GuavaOutputFiles.rootDir.getName().replaceAll("_OUTPUT", "");
        String macs2ParentName = GuavaOutputFiles.rootDir.getAbsolutePath()+System.getProperty("file.separator")+sampleBasename+"_PEAK_CALLING"+System.getProperty("file.separator");
            File xls = new File(macs2ParentName+""+sampleBasename+"_peaks.xls");
            File cBdg = new File(macs2ParentName+""+sampleBasename+"_control_lambda.bdg");
            File nPeak = new File(macs2ParentName+""+sampleBasename+"_peaks.narrowPeak");
            File sBed = new File(macs2ParentName+""+sampleBasename+"_summits.bed");
            File tBdg = new File(macs2ParentName+""+sampleBasename+"_treat_pileup.bdg");

            MACS2 macs2 = new MACS2(xls, cBdg, nPeak, sBed, tBdg);    
            
        return macs2;
    }
    
    public int getPeakCount(MACS2 macs2) {
        
        try {
            FileReader fileReader  = new FileReader(macs2.getPeaksXLS());
            BufferedReader fileBufferedReader = new BufferedReader(fileReader);
            String line = null;
            
            HashSet<String> peakSet = new HashSet<>();
             
            while( (line = fileBufferedReader.readLine()) != null ){
                Pattern p =  Pattern.compile("^(.*?)\\t(\\d+)\\t(\\d+)");
                Matcher m = p.matcher(line);
                if(m.find()){
                    String peak_record = m.group(1)+":"+m.group(2)+"-"+m.group(3);
                    peakSet.add(peak_record);
                }
                
            }
            fileReader.close();
            return peakSet.size();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MACS2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MACS2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outputFile) {
        
        String name = GuavaOutputFiles.rootDir.getName().replaceAll("_OUTPUT", "");
             
        String[] commandArray =  
                    {   getMACS(),
                        "callpeak",
                        "-t", inputFile.getAbsolutePath(),
                        "-f", "BAM",
                        "-g", atacseqInput.getGenomeObject().getGenomeSize(),
                        "--keep-dup", "all",
                        "--outdir", outputFile.getAbsolutePath(),
                        "-n", name,
                        atacseqInput.getPqString(), atacseqInput.getCutOff(),
                        "--bdg", "--trackline", "--nomodel", "--nolambda"
                        };
        return commandArray;
    }

    @Override
    public String[] runCommand(String[] commandArray) {
        String[] log =  new String[2];
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
            Process process =  processBuilder.start();
            String stdOUT = new MACS2().getSTDoutput(process);
            String errorLog = new MACS2().getSTDerror(process);
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

        String[] commandArray =  { "macs2", "--version" };
        String[] log = new MACS2().runCommand(commandArray);
        String macs2Version = "macs2 2.1.1.20160309";

        if(log != null && (log != null && log[1].trim().equals(macs2Version))){
            System.out.println("\t\t"+commandArray[0]+":\t\tWorking!");
            return true;
        }

        System.out.println("\t\tMACS2 version ("+macs2Version+") :\t\t\tNOT FOUND !!!");
        return false;

    }

    /**
     * @return the peaksXLS
     */
    public File getPeaksXLS() {
        return peaksXLS;
    }

    /**
     * @return the controlBdg
     */
    public File getControlBdg() {
        return controlBdg;
    }

    /**
     * @return the narrowPeak
     */
    public File getNarrowPeak() {
        return narrowPeak;
    }

    /**
     * @return the summitBed
     */
    public File getSummitBed() {
        return summitBed;
    }

    /**
     * @return the treatPileupBdg
     */
    public File getTreatPileupBdg() {
        return treatPileupBdg;
    }

    /**
     * @return the MACS
     */
    public static String getMACS() {
        return MACS;
    }

    public MACS2() {
    }

    public MACS2(File peaksXLS, File controlBdg, File narrowPeak, File summitBed, File treatPileupBdg) {
        this.peaksXLS = peaksXLS;
        this.controlBdg = controlBdg;
        this.narrowPeak = narrowPeak;
        this.summitBed = summitBed;
        this.treatPileupBdg = treatPileupBdg;
    }
    
//    public void getNormalizedBedgraph(File bedgraph, File normBedgraph, int totalReads){
//       //double normalizationFactor = 1000000 / totalReads;
//       
//       try {
//            FileReader bdgFileReader = new FileReader(bedgraph);
//            BufferedReader bdgBufferedReader = new BufferedReader(bdgFileReader);
//            String line = "";
//            while((line=bdgBufferedReader.readLine()) != null){
//                String[] bdgRecord = line.split("\t");
//                try {
//                    double covValue = Double.parseDouble(bdgRecord[2]);
//                } catch (NumberFormatException e) {
//                    System.out.println();
//                }
//            }
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(MACS2.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(MACS2.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }
    
}
