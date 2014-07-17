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

package com.bram.concat.categorizationundercognitiveload.experiment.phase;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.bram.concat.categorizationundercognitiveload.IO;
import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.experiment.Block;
import com.bram.concat.categorizationundercognitiveload.experiment.Experiment;
import com.bram.concat.categorizationundercognitiveload.experiment.Trial;
import com.bram.concat.categorizationundercognitiveload.experiment.TrialGroup;

/**
 * The program consists of two phases: 
 * a training phase, where participants learn which stimuli correspond to which category, and a test phase, where they 'show' what they learned.
 * See {@code TrainingPhase} or {@code TestPhase} for more information. 
 */
public abstract class ExperimentPhase {
	
	/**
	 * Name of the phase, used when writign away responses.
	 */
	public String name;
	
	/**
	 * The trial that is currently in progress.
	 */
	protected Trial currentTrial;	
	
	/**
	 * The TrialGroup that is currently in progress.
	 */
	protected TrialGroup currentTrialGroup;
	
	/**
	 * The Block that is currently in progress.
	 */
	protected Block currentBlock;
	
	/**
	 * All blocks that should be displayed in this phase.
	 */
	protected List<Block> allBlocks;
	
	/**
	 * Used to pause the experiment for certain delays, e.g. to display fixation-cross for x ms, or a blank screen for y ms, etc.
	 */
	public Timer timer;
		
	/**
	 * Used to save the participant's response to the trials in the current trial group. 
	 * They are not written to text directly, but only when the pattern has been reproduced (because we want pattern reproduction scores in each line).
	 */
	protected List<String> responseLines;
	
	/**
	 * The index of the current Trial in the current phase of the experiment (i.e., separate count for training and for test phase)
	 */
	protected int trialNb = 0;
	
	/**
	 * The index of the current TrialGroup in the current phase, i.e. 1..{@code nbOfTrialGroups}
	 */
	protected int trialGroupNb = 0;
	
	/**
	 * The index of the current block in the current phase.
	 */
	protected int blockNb = 0;
	
	/**
	 * Used to measure participant's reaction time. Starts when the stimulus is shown.
	 */
	public long RT_start;
	
	ExperimentPhase(List<Block> blocks) {
		allBlocks = blocks;
		timer = new Timer();						//initialize timer
	}
	
	/**
	 * List of actions to perform when the phase is completed.
	 */
	abstract void finishPhase();
	
	/**
	 * Start a new TrialGroup. If there are no more TrialGroups in the current phase of the experiment, move on to the next phase.
	 */
	abstract void startNextTrialGroup();
	
	/**
	 * List of action to perform when a TrialGroup is completed.
	 */
	abstract void finishTrialGroup();
	
	/**
	 * List of action to perform when a Trial is completed.
	 */
	abstract void finishTrial(boolean responseWasCorrect);	
	
	/**
	 * @return {@code TRUE} when the current phase should finish, at which point {@code finishPhase()} will be called.
	 */
	boolean phaseShouldFinish() {
		return allBlocks.isEmpty();
	}
	
	/**
	 * @return The next block that should be displayed.
	 */
	Block getNextBlock() {
		return allBlocks.remove(0);
	};
	
	/**
	 * Attempt to start the next block in this phase.
	 */
	public void startNextBlock() {
		if (phaseShouldFinish()) {
			finishPhase();
		} else {
			startBlock(getNextBlock());
		}
	}	

	/**
	 * Start displaying the trials in the indicated block.
	 */
	protected void startBlock(Block block) {
		currentBlock = block;
		blockNb++;
		startNextTrialGroup();
	}

	/**
	 * Attempt to start the next Trial of the current TrialGroup.
	 */
	void startNextTrial() {		
		Experiment.gui.showCursor();
		
		if (currentTrialGroup.isEmpty()) { 
			finishTrialGroup();			
		} else {
			trialNb++;
			currentTrial = currentTrialGroup.remove(0);
			showPreTrialBlankScreen();
		}		
	}
	
	/**
	 * Show a blank screen before showing a new stimulus.
	 */
	private void showPreTrialBlankScreen() {
		Experiment.gui.xpPane.showBlankScreen();
		timer.schedule(new TimerTask() {          
		    @Override
		    public void run() {
		    	showStimulus();
		    }
		}, Options.durationIntertrialDelay);	
	}
	
	/**
	 * Display the next stimulus.
	 */
	private void showStimulus() {
		Experiment.gui.xpPane.showStimulus(currentTrial.stimulus);
		RT_start = System.currentTimeMillis();
	}
	
	/**
	 * Save the participant's response to the current Trial.  
	 * They are not written to text directly, but only when the pattern has been reproduced (because we want pattern reproduction scores in each line).
	 */
	public void submitResponse(String response, long timeAtSubmission) {
		Trial t = currentTrial;
		int correct = response.equals(t.group) ? 1 : 0;
		int RT = (int) (timeAtSubmission - RT_start);
		String responseLine = name + "\t" + trialNb + "\t" + trialGroupNb + "\t" + blockNb + "\t" + (t.indexInTrialGroup + 1) + "\t" + t.stimulus.filename 
				 + "\t" + t.group + "\t" + response + "\t" + correct + "\t" + RT;
		responseLines.add(responseLine);	
		
		finishTrial(correct == 1);
	}		
	
	/**
	 * Write all responses to the current TrialGroup to disk.
	 */
	void writeResponsesToDisk() {
		for (String responseLine : responseLines) {
			IO.writeData(responseLine);
		}
	}
	
	public abstract void correctPatternReproduction();
		
}
