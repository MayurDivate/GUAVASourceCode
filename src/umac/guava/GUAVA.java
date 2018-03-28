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
import umac.guava.diff.DifferentialResultFrame;


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
            System.out.println("umac.guava.GUAVA.main()");
            System.out.println("TEST MODE TEST MODE TEST MODE TEST MODE TEST MODE TEST MODE");
                GUAVA guava = new GUAVA();
                guava.gdiffTest();
                
                //uiVersion();
        }
        else{
            runGUAVAcommandline(args);
        }
        

    }
    
    private void gdiffTest(){
        
        DifferentialResultFrame resultFrame =  new DifferentialResultFrame();
        resultFrame.setVisible(true);
        File outFolder =  new File("/Users/mayurdivate/Work/Guava_testing/Gdiff_test/TEST_Mayur_GUAVA_Differental_analysis");
        File vplot = new File(outFolder,"TEST_Mayur_vplot.jpg");
        resultFrame.displayVplot(vplot);
        File pcaPlot = new File(outFolder,"TEST_Mayur_PCA_plot.jpg");
        File barchart = new File(outFolder,"TEST_Mayur_Functional_Analysis/TEST_Mayur_bar_chart.jpg");
        resultFrame.displayBarChart(barchart);
        
        File annoPeak = new File(outFolder,"TEST_Mayur_Functional_Analysis/TEST_Mayur_Annotated_Peaks.txt");
        resultFrame.displayResultTable(annoPeak);
        
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
}
