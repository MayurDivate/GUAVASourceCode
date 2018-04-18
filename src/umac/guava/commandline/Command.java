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
package umac.guava.commandline;

import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mayurdivate
 */
public class Command {
    
    private boolean guava;
    private boolean gdiff;
    private boolean help;
    private String[] options;

    public Command(boolean isGuava, boolean isGdiff, boolean isHelp, String[] options) {
        this.guava = isGuava;
        this.gdiff = isGdiff;
        this.help = isHelp;
        this.options = options;
    }
    
    // this method only returns the tool that is called by user
    public static Command getCommand(String[] args){
        
        boolean guava =  isGuavaCall(args);
        boolean gdiff =  isGdiffCall(args);
        boolean help =  isHelpCall(args);
        
        if(guava){
            help =  isGuavaHelpCall(args);
            if(help){
            Command command = new Command(guava, gdiff, help, null);
            return command;
            }
        }
        
        if(gdiff){
            help = isGdiffHelpCall(args);
            if(help){
            Command command = new Command(guava, gdiff, help, null);
            return command;
            }
        }

        if(!help){
            Command command = new Command(guava, gdiff, help, getGuavaOptions(args));
            return command;
        }
        
        Command command = new Command(guava, gdiff, help, null);
        return command;
    }
    
    // helper method for getCommand()
    private static String[] getGuavaOptions(String[] args){
        String[] options = Arrays.copyOfRange(args, 1, args.length); 
        return options;
    }
    
    // helper method for getCommand()
    private static boolean isGuavaCall(String[] args){
        return args[0].equals("guava");
    }
    
    // helper method for getCommand()
    private static boolean isGdiffCall(String[] args){
        return args[0].equals("gdiff");
    }
    
    // helper method for getCommand()
    private static boolean isHelpCall(String[] args){
        if(args.length == 1){
           if(args[0].equals("-h") || args[0].equals("--help")){
                return true;
            }
        }
        return false;
    }
    
    // helper method for getCommand()
    private static boolean isGuavaHelpCall(String[] args){
        
        if(args.length > 1){
          return args[0].equals("guava") && (args[1].equals("-h") || args[1].equals("--help"));
        }
        else if(args.length == 1){
            return true;
        }
        
        return false;

    }
    
    // helper method for getCommand()
    private static boolean isGdiffHelpCall(String[] args){
        
        if(args.length > 1){
          return args[0].equals("gdiff") && (args[1].equals("-h") || args[1].equals("--help"));
        }
            
        else if(args.length == 1){
            return true;
        }
        return false;
    }
    
    public boolean isCommandLengthEven(){
        return (this.getOptions().length & 1) != 1;
    }
    
    
    /*
    *
    * Getters and setters 
    *
    */
    
    public boolean isGuava() {
        return this.guava;
    }
    
    public boolean isGdiff(){
        return this.gdiff;
    }
    
    public boolean isHelp(){
        return this.help;
    }
    
    public String[] getOptions(){
        return this.options;
    }

    public void setGuava(boolean guava) {
        this.guava = guava;
    }

    public void setGdiff(boolean gdiff) {
        this.gdiff = gdiff;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }
    
    public double getDoubleParameter(String patternString, int group){
        Pattern pattern = Pattern.compile(patternString);
        System.out.println(patternString);
        Matcher match = pattern.matcher(this.getCommand());
                
        if(match.find()){
            try{
                double doubleNumber = Double.parseDouble(match.group(group));
                return doubleNumber;
            }
            catch(NumberFormatException ne){
                System.out.println("Invalid input: '"+match.group(group)+"'");
                killExecution();
            }
        }
        
        return 0;
    }
    
    public double getDoubleParameter(String patternString){
        Pattern pattern = Pattern.compile(patternString);
        Matcher match = pattern.matcher(this.getCommand());
        
        if(match.find()){
            try{
                double doubleNumber = Double.parseDouble(match.group(1));
                return doubleNumber;
            }
            catch(NumberFormatException ne){
                System.out.println("Invalid input: '"+match.group(1)+"'");
                killExecution();
            }
        }
        
        return 0;
    }
    
    
    public String getStringParameter(String patternString){
        
        Pattern pattern = Pattern.compile(patternString);
        Matcher match = pattern.matcher(this.getCommand());
        
        if(match.find()){
            String parameter = match.group(1);
            return parameter;
        }
        return null;
    }
    
    public int getIntParameter(String patternString){
        
        Pattern pattern = Pattern.compile(patternString);
        Matcher match = pattern.matcher(this.getCommand());
        
        if(match.find()){
            try{
                int parameter = Integer.parseInt(match.group(1));
                return parameter;
            }
            catch(NumberFormatException e){
                System.out.println("Invalid input: '"+match.group(1)+"'");
                killExecution();
            }
        }
        return 0;
    }
    
    public File getFileParameter(String patternString){
        
        Pattern pattern = Pattern.compile(patternString);
        Matcher match = pattern.matcher(this.getCommand());
        
        if(match.find()){
            String path = match.group(1);
            File infile = new File(path);
            if(infile.getAbsoluteFile().exists()){
                return infile.getAbsoluteFile();
            }
            
        }
        
        System.out.println("Invalid file : '"+match.group(1)+"'");
        killExecution();
        return null;
    }
    
    public File getFileParameter(String patternString, int group){
        
        Pattern pattern = Pattern.compile(patternString);
        Matcher match = pattern.matcher(this.getCommand());
        
        if(match.find()){
            String path = match.group(group);
            File infile = new File(path);
            if(infile.getAbsoluteFile().exists()){
                return infile.getAbsoluteFile();
            }
            
        }
        
        System.out.println("Invalid file : '"+match.group(1)+"'");
        killExecution();
        return null;
    }
    
    public boolean isUsed(String patternString){
        
        Pattern pattern = Pattern.compile(patternString);
        Matcher match = pattern.matcher(this.getCommand());
        return match.find();
    }
    
    // method to kill commandline execution of program 
    public static void killExecution(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for(StackTraceElement ste : stackTrace){
            System.out.println(ste);
        }
        System.err.println("Error: invalid command");
        System.err.println("stopping ... ");
        System.exit(-1);
    }
    
    // method to create command string 
    public String getCommand(){
        
        String command = "";
        for(String item: this.getOptions()){
            command =  command + " " + item;
        }
        
        command = command.trim();
        command = " "+command+" ";
        return command;
    } 

    @Override
    public String toString() {
        String command  = "Guava:"+this.isGuava();
        command = command + " Gdiff:"+this.isGdiff();
        command = command + " Help:"+this.isHelp();
        
        return command;
    }
    
    
}
