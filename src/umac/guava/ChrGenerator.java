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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class ChrGenerator extends Tool{
    
    List<String> chromosomes;
    
    List<String> getBowtieIndexChromosomes(String indexPath){
        ChrGenerator chrGenerator = new ChrGenerator();
        String indexString = chrGenerator.getIndex(indexPath);
        String[] log = chrGenerator.runCommand(chrGenerator.getBowtieCommand(indexString));
        return chrGenerator.getChrList(log[0]);
    }
    
    
    List<String> getBowtie2IndexChromosomes(String indexPath){
        ChrGenerator chrGenerator = new ChrGenerator();
        String indexString = chrGenerator.getIndex(indexPath);
        String[] log = chrGenerator.runCommand(chrGenerator.getBowtie2Command(indexString));
        return chrGenerator.getChrList(log[0]);
    }

    List<String> getChrList(String chrString){
        String[] chrs =  chrString.split("\n");
        List<String> chrList = new ArrayList<>();
        for(String chr: chrs){
            chrList.add(chr);
        }
        return chrList;
    }
    
    
    @Override
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outputFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String[] getBowtieCommand(String bowtieIndex) {
        String[] commandArray =  
            {   "bowtie-inspect","-n",
                bowtieIndex
            };
        return commandArray; 
    }
    
    
    public String[] getBowtie2Command(String bowtie2Index) {
        String[] commandArray =  
            {   "bowtie2-inspect", "-n",
                bowtie2Index
            };
        return commandArray; 
    }
    
    private String getIndex(String indexString){
            
        if(indexString.endsWith("ebwt")){
            String bowtieIndexString = indexString.replaceAll("\\.rev\\.\\d+\\.ebwt", "");
            bowtieIndexString = bowtieIndexString.replaceAll("\\.\\d+\\.ebwt", "");
            return bowtieIndexString;
        }
        else if(indexString.endsWith("bt2")){
            String bowtieIndexString = indexString.replaceAll("\\.rev\\.\\d+\\.bt2", "");
            bowtieIndexString = bowtieIndexString.replaceAll("\\.\\d+\\.bt2", "");
            return bowtieIndexString;
        }
        return null;
    }
    
    
    @Override
    public String[] runCommand(String[] commandArray) {
        
        String[] log =  new String[2];
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandArray);
            Process process =  processBuilder.start();
            String stdOUT = new Cutadapt().getSTDoutput(process);
            String errorLog = new Cutadapt().getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
            return log;
        } catch (IOException ex) { 
            Logger.getLogger(ChrGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean isWorking() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
