/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package umac.guava;

import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
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


    public static File getPackageBase(){
        try {

            File jarFile = new File(GUAVA.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            return jarFile.getParentFile();

        } catch (URISyntaxException ex) {
            Logger.getLogger(GUAVA.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static File getBlacklistDir(){
        File packageBase =  getPackageBase();
        File lib =  new File(packageBase,"lib");
        File blacklistDir =  new File(lib,"blacklists");
        return blacklistDir;
    }

    public static void main(String[] args) {
        setPWD();
        System.out.println("GUAVA version 1");
        if(args.length == 0){
               uiVersion();
        }
        else{
            runGUAVAcommandline(args);
        }

    }
   
    private static void setPWD(){
        File file =  new File(".");
        file = new File(file.getAbsolutePath());
        pwd = file.getParentFile().getAbsolutePath();
    }

    private static void runGUAVAcommandline(String[] args){
        
        //System.out.println("GUAVA commandline version");
        
        Command command =  Command.getCommand(args);
        
        CommandlineWorkflow cmdWorkflow = new CommandlineWorkflow();
        
        cmdWorkflow.startCommandlineFlow(command);
        
    }

    public static void uiVersion() {
        
        //System.out.println("GUAVA GUI version");

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
