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
package umac.guava.genomeindexbuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author mayurdivate
 */
public class GenomeIndexBuilder {

    private File genomeFasta;
    private File outputFile;
    private String Aligner;

    public GenomeIndexBuilder(File genomeFasta, File outputFile, String Aligner) {
        this.genomeFasta = genomeFasta;
        this.outputFile = outputFile;
        this.Aligner = Aligner;
    }
    
    // Create genome index
    public void CreateGenomeIndex(){
        
        String[] log = this.runCommand(this.getCommand());
        
        if(log[1] != null ){
            JOptionPane.showMessageDialog(null, log[1]);
        }
        else{
            JOptionPane.showMessageDialog(null, "Finished !");
        }
        
        
    }
    
    /**
     * @return the genomeFasta
     */
    public File getGenomeFasta() {
        return genomeFasta;
    }

    /**
     * @param genomeFasta the genomeFasta to set
     */
    public void setGenomeFasta(File genomeFasta) {
        this.genomeFasta = genomeFasta;
    }

    /**
     * @return the outputFile
     */
    public File getOutputFile() {
        return outputFile;
    }

    /**
     * @param outputFile the outputFile to set
     */
    public void setOutputFfile(File outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * @return the Aligner
     */
    public String getAligner() {
        return Aligner;
    }

    /**
     * @param Aligner the Aligner to set
     */
    public void setAligner(String Aligner) {
        this.Aligner = Aligner;
    }

    public String[] getCommand() {
        String[] command = {
            this.getAligner(),
            this.getGenomeFasta().getAbsolutePath(),
            this.getOutputFile().getAbsolutePath()
        };
        return command;
    }

    public String[] runCommand(String[] command) {
        String[] log = new String[2];
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            Process process = builder.start();
            String stdOUT = this.getSTDoutput(process);
            String errorLog = this.getSTDerror(process);
            log[0] = stdOUT;
            log[1] = errorLog;
            return log;
        } catch (IOException ex) {
            Logger.getLogger(GenomeIndexBuilder.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    private String getSTDoutput(Process process) {
        try {
            String stdOut = "";
            String readLineString;
            BufferedReader brProcess = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((readLineString = brProcess.readLine()) != null) {
                stdOut = stdOut + readLineString + "\n";
            }
            return stdOut;
        } catch (IOException ex) {
            Logger.getLogger(GenomeIndexBuilder.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    private String getSTDerror(Process process) {
        try {
            String stdError = "";
            String readLineString;
            BufferedReader brProcess = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((readLineString = brProcess.readLine()) != null) {
                stdError = stdError + readLineString + "\n";
            }
            return stdError;
        } catch (IOException ex) {
            Logger.getLogger(GenomeIndexBuilder.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
            return null;
        }
    }

}
