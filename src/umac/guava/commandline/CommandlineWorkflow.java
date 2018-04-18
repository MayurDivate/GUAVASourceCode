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

import umac.guava.AnalysisWorkflow;
import umac.guava.GuavaInput;
import umac.guava.Input;
import umac.guava.diff.GdiffInput;
import umac.guava.diff.DifferentialAnalysisWorkflow;
import umac.guava.diff.DifferentialInputFrame1;

/**
 *
 * @author mayurdivate
 */
public class CommandlineWorkflow {
    
    
    public boolean startCommandlineFlow(Command command){
        
        if(command.isGuava() && ! command.isHelp()){
            // Guava ATAC-seq data analysis program
            prepareGuavaCommandlineWorkflow(command);
        }
        
        else if (command.isGdiff() && ! command.isHelp()){
            // Guava ATAC-seq differential data analysis program
            prepareGdiffCommandlineWorkflow(command);
        }
        else if(command.isGuava() && command.isHelp()){
            new  GuavaCommand().printHelpMessage();
        }
        else if(command.isGdiff() && command.isHelp()){
            new  Gdiffcommand().printHelpMessage();
        }
        else if((!command.isGuava() && !command.isGdiff()) ||command.isHelp()){
            System.out.println("To use GUI version:");
            System.out.println("\tjava -jar GUAVA.jar\n\n");
            System.out.println("To use commandline version:\n");
            new  GuavaCommand().printHelpMessage();
            System.out.println("\n----------------------------------\n");
            new  Gdiffcommand().printHelpMessage();
        }
        
        return false;
    
    }
    
    
    public boolean prepareGuavaCommandlineWorkflow(Command command){
        
        // check dependencies
        if(AnalysisWorkflow.checkCommandlineDependencies()){

            GuavaCommand guavaCommand = new GuavaCommand();

            // check command compulsory and duplicates
            if(guavaCommand.isCorrectCommand(command)){
                // get guava command
                guavaCommand = GuavaCommand.getGuavaCommand(command);
                // create input object from guava
                Input input = guavaCommand.getInput(guavaCommand);
                
                if(input instanceof GuavaInput){
                    GuavaInput guavaInput = (GuavaInput) input;
                    AnalysisWorkflow aw = new AnalysisWorkflow();
                    guavaInput.setBlacklistFile(guavaInput.getGenome());
                    aw.startCommandlineGuavaAnalysis(guavaInput);
                }
                else{
                    System.out.println("Is not a GuavaInput");
                }
             }
        }
        else{
            System.out.println("Error: Missing dependancies");
        }
        return false;
    }
    
    public static boolean prepareGdiffCommandlineWorkflow(Command command){
        
        Gdiffcommand gdiffcommand = new Gdiffcommand();
        // check command compulsory and duplicates
        
        boolean alright = gdiffcommand.isCorrectCommand(command);
        
        if(alright){
        // get gdiff command
            gdiffcommand = Gdiffcommand.getGdiffCommand(command);
            Input input = gdiffcommand.getInput(gdiffcommand);
         
            if(input instanceof GdiffInput){
                
                GdiffInput atacdi = (GdiffInput) input;
        
                // improve method to of input file list validation    
                // System.out.println("valid input"+DifferentialInputFile.isValidInput(atacdi.getDiffInputfiles()));
                DifferentialAnalysisWorkflow analysisFlow = new DifferentialAnalysisWorkflow();
                
                // set project name 
                DifferentialInputFrame1.projectName = atacdi.getProjectName();
        
                analysisFlow.startCommandlineDifferentialAnalysis(atacdi);
            }
        }
        
        
        
        return false;
    }

    
    
    
}
