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

import umac.guava.Input;

/**
 *
 * @author mayurdivate
 */
public abstract class CommandlineTool {
    
    
    public abstract boolean isCorrectCommand(Command command);
    
    public abstract boolean hasCompulsoryOptions(Command command);
    
    public abstract boolean hasDuplicates(Command command);
    
    public abstract boolean isHelpCall(Command command);
    
    public static String getCommandString(String[] options){

     String command = "";

        for (String inputItem : options){
            command = command+""+inputItem+" ";
        }

        return command;
    }
    
    // method to returninput object   
    public abstract Input getInput(CommandlineTool tool);
    
    public abstract boolean validateInput(Input input);
    
    public void printHelpMessage(){
        if(getHelpMessage() != null){
            System.out.println(getHelpMessage());
        }
        else{
            System.out.println("Nothing new to message");
            System.out.println("@author override getHelpMessage()");
            System.out.println("");
        }
    }
    
    public abstract String getHelpMessage();
    
    public void killExecution(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for(StackTraceElement ste : stackTrace){
            System.out.println(ste);
        }
        System.err.println("Please rectify above error in command");
        System.err.println("stopping ... ");
        System.exit(-1);
    }
    
    
}
