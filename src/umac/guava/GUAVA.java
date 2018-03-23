/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.File;
import javax.swing.JFrame;
import umac.guava.commandline.Command;
import umac.guava.commandline.CommandlineWorkflow;


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
//        chipPeakAnnoTest();
//        
//        System.out.println("Test mode is active");
        
        //Please deactivate test mode 
        
        if(args.length == 0){
                uiVersion();
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
    
    
    public static void chipPeakAnnoTest(){
        
        RunStatusJframe runStatusJframe = new RunStatusJframe();
        runStatusJframe.setVisible(true);
        
        
        String format = "MACS2";
        File inputFile = new File("/Users/mayurdivate/Work/GuavaFunctionalAnnotation/SRR3929040_DMSO_Rep1_R1_PEAK_CALLING/SRR3929040_DMSO_Rep1_R1_peaks.xls");
        File outputFolder = new File("/Users/mayurdivate/Work/GuavaFunctionalAnnotation/SRR3929040_DMSO_Rep1_R1_PEAK_CALLING/test_out");
        File barChart = new File(outputFolder,"SRR3929040_DMSO_Rep1_R1_barchart.jpg");
        File peakAnnoated = new File(outputFolder,"SRR3929040_DMSO_Rep1_R1_Annotated_peaks.txt");
        File goAnalysisOutputFile = new File(outputFolder,"SRR3929040_DMSO_Rep1_R1_GoAnalysis.txt");
        File pathwayAnalysisOutputFile = new File(outputFolder,"SRR3929040_DMSO_Rep1_R1_PathwayAnalysis.txt");
        File rCodeFile = new File(outputFolder,"SRR3929040_DMSO_Rep1_R1_ChIPpeakAnno.R");
        Genome genome = Genome.getGenomeObject("hg19");
        
        GuavaOutputFiles.logFile = new File(outputFolder,"SRR3929040_DMSO_Rep1_log.txt");
        
        ChIPpeakAnno chIPpeakAnno = new ChIPpeakAnno(inputFile, format, 
                barChart, peakAnnoated, outputFolder, goAnalysisOutputFile,
                pathwayAnalysisOutputFile,rCodeFile,genome);
        
                    AnalysisWorkflow aw = new AnalysisWorkflow();
                    System.out.println("----------- Peak annotation -------------");
                    //aw.runChIPpeakAnno(chIPpeakAnno);
                    // ExcelPrinter.printImage(chipSeeker.getPieChart(), "PieChart", 15.0, 25.0);
                    // ExcelPrinter.printPeakTable(chipSeeker.getGeneAnnotationPeaks());
                    
                    System.out.println("\n");
                    //runStatusJframe.addPeakTableRow(chipSeeker.getGeneAnnotationPeaks());
//                    runStatusJframe.displayACRbarChart(barChart);
//                    runStatusJframe.addPeakTableRow(chIPpeakAnno.getPeakAnnoated());
//                    runStatusJframe.addGoTableRows(goAnalysisOutputFile);
//                    runStatusJframe.addPathwayTableRows(pathwayAnalysisOutputFile);
                    //runStatusJframe.setVisible(true);
                    System.out.println("----------- F I N I S H E D -------------");
              
                
        
        /* public ChIPpeakAnno(File inputFile, String inputFileFormat, File barChart,
        File peakAnnoated, File outputFolder, File goAnalysisOutputFile,
        File pathwayAnalysisOutputFile, File rCodeFile, Genome genome) 
            */ 
       
        
        
        chIPpeakAnno.writeCode(chIPpeakAnno.getChIPpeakAnnoRcode(), rCodeFile);
        
        
        
        
    }
    
    
}
