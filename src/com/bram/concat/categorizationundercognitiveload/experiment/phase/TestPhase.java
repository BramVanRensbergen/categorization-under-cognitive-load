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

import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.experiment.Block;
import com.bram.concat.categorizationundercognitiveload.experiment.Experiment;

/**
 * This second phase of the experiment starts when participants attain a high enough accuracy in the training phase.
 * In the test-phase, they no longer receive feedback on their classifications.
*/
public class TestPhase extends ExperimentPhase{
	
	/**
	 * After half of the blocks, display a break screen.
	 * This boolean is {@code TRUE} if we've displayed this screen yet.
	 */
	boolean displayedHalfwayInstructions;
	
	public TestPhase(List<Block> blocks) {
		super(blocks);
		name = "TEST";
		displayedHalfwayInstructions = false;
	}

	@Override
	void finishPhase() {
		Experiment.gui.instructionPanel.showXpOverText();		
	}
	
	@Override
	public void startNextBlock(){
		//if we're halfway, and no break was displayed yet, do so now.
		if (blockNb == Options.testBlocksAmount / 2 && !displayedHalfwayInstructions) {
			displayedHalfwayInstructions = true;
			Experiment.gui.instructionPanel.showHalfwayInstructions();
		} else {
			super.startNextBlock();
		}
	}
	
	/**
	 * Start a new TrialGroup (beginning by showing that TrialGroup's pattern). 
	 * If there are no more TrialGroups in the current phase of the experiment, move on to the next phase.
	 */
	@Override
	void startNextTrialGroup() {			
		if (currentBlock.isEmpty()) { //all trials for this block have been shown
			startNextBlock();
		} else { //show the next trialgroup for this block			
			trialGroupNb++;
			currentTrialGroup = currentBlock.remove(0);
			startNextTrial();
		}
	}
	
	@Override
	void finishTrialGroup() {
		startNextTrialGroup();		
	}
	
	@Override
	void finishTrial(boolean responseWasCorrect) {
		startNextTrial();
	}

	@Override
	public void correctPatternReproduction() {
		throw new RuntimeException("Method called in wrong experimental phase...");		
	}	
}