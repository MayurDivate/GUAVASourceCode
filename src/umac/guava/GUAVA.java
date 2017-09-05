/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.File;
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

        if(args.length == 0){
                uiVersion();
        }
        else{
              
            runGUAVAcommandline(args);
            //commandLineVersion(args);
        }

    }
    
    private static void setPWD(){
        File file =  new File(".");
        pwd = file.getAbsolutePath();
    }

    private static void commandLineVersion(String[] args) {

        // check format and duplicates in parameter input list
        
        
//        if(commandLineFormValidation(args)){
//        
//                if(!AnalysisWorkflow.validateToolPaths()){
//                    System.exit(1);
//                }
//
//            Input atacAnalyzerInput = new ToolOptions().getInput(args);
//            AnalysisWorkflow.commandLine = true;
//            System.out.println(atacAnalyzerInput);
//            new AnalysisWorkflow().runStepWiseAnalysis(atacAnalyzerInput);
//        }
    }
    
    private static boolean commandLineFormValidation(String[] args){

        ToolOptions toolOptions = new ToolOptions();
        boolean optionFlag = false;
        
        if(args.length == 1){
            if(toolOptions.isHelpCall(args[0])){
                System.exit(1);
            }
        }
        else{
            optionFlag = true;
        }
        
        if(optionFlag){
            //optionFlag = toolOptions.checkFormat(args);
            optionFlag = false;
            
        }
      
        return optionFlag;

    }
    
    private static void runGUAVAcommandline(String[] args){
        
        System.out.println();
        // roger that 
        Command command =  Command.getCommand(args);
        //System.out.println(command);
        
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
        homeFrame.setVisible(true);
    }
    
    
}
