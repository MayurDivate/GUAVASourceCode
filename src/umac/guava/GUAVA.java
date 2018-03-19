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
    
    
}
