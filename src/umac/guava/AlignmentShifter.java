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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mayurdivate
 */
public class AlignmentShifter {
    
    public File shiftAlignment(GuavaOutputFiles outFiles, File samFile){
        try {
            outFiles.getAtacseqSam().createNewFile();

            FileWriter fileWriter = new FileWriter(outFiles.getAtacseqSam());
            PrintWriter printWriter =  new PrintWriter(fileWriter);

            HashMap<String,Integer> chrSizes = new AlignmentShifter().getChrSizes(samFile);
            FileReader fileReader = new FileReader(samFile);
            BufferedReader fileBufferedReader = new BufferedReader(fileReader);
            
            String read1;
            String read2;
            int lineNum = 0;
          
            while((read1 = fileBufferedReader.readLine()) != null){
                    lineNum++;
                    fileBufferedReader.mark(100000);
                    
                    if(read1.startsWith("@")){
                            printWriter.write(read1+"\n");
                            printWriter.flush();
                            continue;
                    }
                    
                    if((read2 = fileBufferedReader.readLine()) != null){
                        lineNum++;

                        String  rec1_ID = read1.split("\t")[0];
                        String  rec2_ID = read2.split("\t")[0];

                        if(rec1_ID.equals(rec2_ID)){
                            String newRead1 =  new AlignmentShifter().shiftRecord(read1,chrSizes);
                            String newRead2 =  new AlignmentShifter().shiftRecord(read2,chrSizes);
                            printWriter.write(newRead1+"\n");
                            printWriter.flush();
                            printWriter.write(newRead2+"\n");
                            printWriter.flush();
                            
                        }
                        else{
                            fileBufferedReader.reset();
                            lineNum--;
                        }
                    }
            }
            printWriter.close();
            return outFiles.getAlignedSam();
        }


        
        catch (FileNotFoundException ex) {
            Logger.getLogger(AlignmentShifter.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
            Logger.getLogger(AlignmentShifter.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
    }
    
    private String shiftRecord (String samRecord, HashMap<String, Integer> chrSizes){
        String[] recs = samRecord.split("\t");
        int recs_3 = Integer.parseInt(recs[3]);
        int recs_7 = Integer.parseInt(recs[7]);
        int recs_8 = Integer.parseInt(recs[8]);
        int seqLen = recs[9].length();
        
// before shift          --------->                <---------
//                ---------------------------------------------------    
// after shift     --------->                            <---------
//


//Reads on positive strands

                if (recs[1].equals("99") || recs[1].equals("163")) {
                            recs_3 = recs_3 - 4 ; 
                            recs_7 = recs_7 + 5 ;
                            recs_8 = recs_8 + 9 ;
                            
// after shifting it should not exceed chromosome boundaries
                    if(recs_3 > 0 && recs_7 + seqLen <= chrSizes.get(recs[2])){
                        recs[3] = recs_3+"";
                        recs[7] = recs_7+"";
                        recs[8] = recs_8+"";
                    }
                }
                
//Reads on positive strands
                else if( recs[1].equals("147") || recs[1].equals("83")) {
                            recs_3 = recs_3 + 5 ;
                            recs_7 = recs_7 - 4 ;
                            recs_8 = recs_8 - 9 ;

                    if( recs_3 + seqLen <= chrSizes.get(recs[2]) && recs_7 >= 1 ){
                           recs[3] = recs_3+"";
                           recs[7] = recs_7+"";
                           recs[8] = recs_8+"";
                        
                    }
                }

            String newSamRecord = String.join("\t", recs);
            return newSamRecord;
    }
    
    
    
    private HashMap<String,Integer> getChrSizes(File samFile){
        FileReader fileReader = null;
        try {
            File headerFile = new File(samFile.getAbsolutePath()+"_header.txt");
            String[] log = new Samtools().runCommand(new Samtools().getCommand(samFile,headerFile));
            HashMap<String,Integer> chrSizes = new HashMap<>();
            fileReader = new FileReader(headerFile);
            BufferedReader fileBufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = fileBufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile("^@SQ\tSN:(.*?)\tLN:(\\d+)$");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String chr = matcher.group(1);
                    int len = Integer.parseInt(matcher.group(2));
                    chrSizes.put(chr, len);
                }
            }
            new Samtools().deleteFile(headerFile);
            IGV.chromosomes = new HashMap<>(chrSizes);
            return chrSizes;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AlignmentShifter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AlignmentShifter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileReader.close();
            } catch (IOException ex) {
                Logger.getLogger(AlignmentShifter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     return null;
    }    
            
    
}
