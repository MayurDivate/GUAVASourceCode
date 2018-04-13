/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author mayurdivate
 */

public class IGV extends Tool implements Runnable{
    public static HashMap<String, Integer> chromosomes;
    public static Genome genome;

    private File[] tracks;
    private String chromosome;
    private int start;
    private int end;
    private int distance;

    public IGV(File[] tracks, String chromosome, int start, int end, int distance) {
        this.tracks = tracks;
        this.chromosome = chromosome;
        this.start = start;
        this.end = end;
        this.distance = distance;
    }

    public IGV() {
    }
    
    public void viewInIGV(){
       
       //System.out.println("START : View in IGV : "+this.getIGVLocation());
       String[] command =  this.getCommand();
       this.runCommand(command);
       //System.out.println("END : View in IGV :"+this.getIGVLocation());
       
    }
    
    public static String getLocationbyDistance(String chr, int start, int end, int distance){
        int x  = start - Math.abs(distance);
        int y  = end + Math.abs(distance);
        
            if(x < 0){
                x = 1;
            }
            if(y > chromosomes.get(chr)){
                y =  chromosomes.get(chr);
            }
        return chr+":"+x+"-"+y;
    }

    @Override
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outputFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String[] getCommand() {
        
        String command = "igv "; 
        String tracks = "";
            for(File track : this.getTracks()){
                    tracks = tracks+","+track.getAbsolutePath()+"";
                }
        tracks = tracks.replaceFirst(",", "");
        command = command + tracks + " " + this.getIGVLocation();
        
        System.out.println("umac.guava.IGV.getCommand()");
        System.out.println("IGV command : "+command);
        
        return command.split("\\s");
    }
    
    @Override
    public String[] runCommand(String[] commandArray) {
        String[] log =  new String[2];
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
            Process process =  processBuilder.start();
            String stdOUT = new IGV().getSTDoutput(process);
            String errorLog = new IGV().getSTDerror(process);
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
        String[] commandArray =  {"which", "igv" };
        String[] log = new IGV().runCommand(commandArray);
               
        if(log != null && log[0] != null){
            System.out.println("\t\t"+commandArray[1]+":\t\tWorking!");
            return true;
        }

        System.out.println("\t\t"+commandArray[1]+":\t\t\tNOT FOUND !!!");
        return false;

    }
  
    @Override
    public void run() {
        this.viewInIGV();
    }

    /**
     * @return the tracks
     */
    public File[] getTracks() {
        return tracks;
    }

    static File[] getIGVTracks() {
        File[] igvtracks =  new File[]{
            GuavaOutputFiles.getOutputFiles().getBigwigFile(),
            MACS2.getMACS2().getNarrowPeak()
        };
        return igvtracks;
    }

    
    /**
     * @param tracks the tracks to set
     */
    public boolean setTracks(File[] tracks) {
        
        for(File track : tracks){
            if(! track.isFile() ){
                return false;
            }
        }
        
        this.tracks = tracks;
        return true;
    }

    /**
     * @return the chromosome
     */
    public String getChromosome() {
        return chromosome;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
        
    }
    
    public String getIGVLocation() {
        return this.chromosome+":"+this.getStart()+"-"+this.getEnd();
    }
    
}
