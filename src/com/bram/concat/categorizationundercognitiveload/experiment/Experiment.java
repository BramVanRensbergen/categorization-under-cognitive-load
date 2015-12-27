/**
  	This file is part of a task where participants categorize under cognitive load.
	Copyright (C) 2014 Bram Van Rensbergen (mail@bramvanrensbergen.com)

    This is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    It is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this software.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.bram.concat.categorizationundercognitiveload.experiment;

import java.io.IOException;

import com.bram.concat.categorizationundercognitiveload.experiment.phase.*;
import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.Text;
import com.bram.concat.categorizationundercognitiveload.gui.Gui;
import com.bram.concat.categorizationundercognitiveload.io.Input;
import com.bram.concat.categorizationundercognitiveload.io.Output;

/**
 * Handles the flow of the experiment.
 */
public abstract class Experiment {
	/**
	 * Handles the experiment's layout.
	 */
	public static Gui gui;

	/**
	 * The current participant. Created after he/she fills of their information.
	 */
	public static Participant pp;
	
	public static ExperimentPhase currentPhase, trainingPhase, testPhase;
	
	public static void initialize() throws IOException {
		Input.readAndSetOptions();
		Input.readText();
		Input.readImages(); 		// read stimuli from disk
		gui = new Gui();			// create GUI
		
		if (!Options.DEBUG) {			
			gui.showSsInfo();		
		} else {
			createParticipantAndContinue(2, 18, 'm'); // create 'default' participant during development
		}	
	}
	
	/**
	 * Create the participant, create his output file, create the trials he will see, 
	 * initialize the buttons in the experiment (order of which depends on the participant),
	 * and finally, show the instructions.
	 */
	public static void createParticipantAndContinue(int ssNb, int age, char gender) {
		pp = new Participant(ssNb, age, gender);	
		
		//c reate the datafile for the participant
		Output.initializeWriting(ssNb + "_" + age + "_" + gender + "_" + Text.getDate() + "_" + Text.getTime() + ".txt");
				
		// create the trials the participant will see
		// only done here, not before, as participant number influences the condition of their trials
		Experiment.createTrials();	
				
		// only done here, as which buttons is left, and which is right, depends on participant
		gui.xpPane.createCategorizationButtons();
		
		Experiment.showInstructions();
	}	
		
	/**
	 * Show the experimental screen, and display the next trial.
	 */
	public static void displayAndContinue() {
		gui.showXP();
		currentPhase.startNextBlock();
	}
	
	/**
	 * Create the trials this participant will see.
	 */
	public static void createTrials() {
		trainingPhase = new TrainingPhase(Block.createInitialTrainingBlocks());
		testPhase = new TestPhase(Block.createAllTestBlocks());
		currentPhase = trainingPhase;
	}
	
	/**
	 * Show the main instructions.
	 */
	public static void showInstructions() {
		Experiment.gui.instructionPanel.showMainInstructions(1);
	}
	
	/**
	 * Start the second phase of the experiment.
	 */
	public static void startTestPhase() {
		currentPhase = testPhase;
		Experiment.gui.instructionPanel.showPostTrainingInstructions();		
	}			
}