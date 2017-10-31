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
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mayurdivate
 */
public class UCSCtools extends Tool{
    
    static File  ucscDir; 
    
    @Override 
    public String[] getCommand(GuavaInput atacseqInput, File inputFile, File outputFile) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String[] getBedgraphToBigwigCommand(IGVdataTrack igvTrack){
        
        String[] commandArray =  
                    {   getbedgraphToBigWig().getAbsolutePath(),
                        igvTrack.getBdgFile().getAbsolutePath(),
                        getGenomeSize(igvTrack.getBuild()).getAbsolutePath(),
                        igvTrack.getBigWigFile().getAbsolutePath(),
                        };
        return commandArray;
    
    }

    @Override
    public String[] runCommand(String[] command) {
        String[] log =  new String[2];
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process =  processBuilder.start();
            String stdOUT = this.getSTDoutput(process);
            String errorLog = this.getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
            
        } catch (IOException ex) {
            Logger.getLogger(UCSCtools.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return log;
    }

    @Override
    public boolean isWorking() {
        String[] commandArray =  {"bedtools", "--version" };
        String[] log = new UCSCtools().runCommand(commandArray);
        System.out.println("1 >>>"+log[0]);
        System.out.println("2 >>>"+log[1]);
        if(log[0] != null && log[1] != null){
            System.out.println("\t\tbowtie:\t\tAffirmative :)");
            return true;
        }
        return false;
    }
    
    
    public File getbedgraphToBigWig(){
        File bdg2bigwig = new File("bedGraphToBigWig");
        return bdg2bigwig;
    }
    
    public File getGenomeSize(String build){
        File chrSizes =  BedTools.getGenomeSize(build);
        return chrSizes;

    }
    
}
