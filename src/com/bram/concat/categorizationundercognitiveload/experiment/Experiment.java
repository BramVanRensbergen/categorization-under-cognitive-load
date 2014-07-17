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
import com.bram.concat.categorizationundercognitiveload.IO;
import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.gui.Gui;

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
		IO.readAndSetOptions();
		IO.readText();
		IO.readImages(); 		  				//read stimuli from disk
		gui = new Gui();							//create GUI
		
		if (!Options.DEBUG) {			
			gui.showSsInfo();		
		} else {
			Participant.createParticipant(1, 18, 'm'); //create 'default' participant during development
//			showInstructions();
			displayAndContinue();
		}	
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