/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFrame;
import umac.guava.commandline.Command;
import umac.guava.commandline.CommandlineWorkflow;
import umac.guava.diff.Peak;


/**
 *
 * @author mayurdivate
 */
public class GUAVA {

    /**
     * @return the pwd
     */
    public static String getPwd() {
        return pwd;
    }
    public static File getPwDir() {
        File pwDir =  new File(pwd);
        return pwDir;
    }
    
    private static String pwd;

    public static void main(String[] args) {
        setPWD();
        
        if(args.length == 0){
               uiVersion();
//               System.err.println("TEST MODE");
//               testOveralap();
        }
        else{
            runGUAVAcommandline(args);
        }

    }
   
    private static void setPWD(){
        File file =  new File(".");
        pwd = file.getAbsolutePath();
    }

    private static void runGUAVAcommandline(String[] args){
        
        System.out.println();
        // roger that 
        Command command =  Command.getCommand(args);
        
        CommandlineWorkflow cmdWorkflow = new CommandlineWorkflow();
        
        cmdWorkflow.startCommandlineFlow(command);
        
    }

    public static void uiVersion() {
        
        try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } 
            catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
   
        HomeFrame homeFrame = new HomeFrame();
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeFrame.setVisible(true);

    }

    private static void testOveralap() {
        
        File bed1 = new File("/Users/mayurdivate/Work/Guava_testing/Gdiff_test/Test_Overlapping/SRR3929040_DMSO_Rep1_R1_peaks.narrowPeak");
        File bed2 = new File("/Users/mayurdivate/Work/Guava_testing/Gdiff_test/Test_Overlapping/SRR3929041_DMSO_Rep2_R1_peaks.narrowPeak");
        
        ArrayList<Peak> peakList1 = Peak.getPeakList(bed1);
        ArrayList<Peak> peakList2 = Peak.getPeakList(bed2);
        
        System.out.println("umac.guava.GUAVA.testOveralap()");
        System.out.println(peakList1.size());
        System.out.println(peakList2.size());
        
        ArrayList<Peak> mergedList = new ArrayList<>();
        mergedList.addAll(peakList1);
        mergedList.addAll(peakList2);
        System.out.println(mergedList.size());

        ArrayList<Peak> mergedOverlappingList2 = new Peak().mergeOnlyOverlappingPeaks(mergedList);
        for(Peak peak : mergedOverlappingList2){
            System.out.println(peak.getChromosome()+": "+peak.getStart()+" - "+peak.getEnd());
        }
                
    }
}
