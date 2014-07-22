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

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.experiment.Block;
import com.bram.concat.categorizationundercognitiveload.experiment.Experiment;
import com.bram.concat.categorizationundercognitiveload.io.Output;
import com.bram.concat.categorizationundercognitiveload.pattern.NoloadPattern;
import com.bram.concat.categorizationundercognitiveload.pattern.Pattern;

/**
 * In the first phase of the experiment, participants learn to categorize the stimuli.
 * After each trial, they receive feedback on whether they had classified it correctly.
 * If participants attain a high enough accuracy, the experiment moves to the test phase.
 */
public class TrainingPhase extends ExperimentPhase {
	
	/**
	 * Keep track of the mean accuracy of the trials in each completed block during the training phase.
	 * When two subsequent blocks have an accuracy of over .90, we move on to the test phase.
	 */
	private List<Double> completedBlockAccuracy;
	
	/**
	 * Keep a list of the accuracy of all trials in the current block. Each value is either 1 (ss categorized correctly) or 0.
	 */
	private List<Integer> currentBlockTrialAccuracy;
	
	public TrainingPhase(List<Block> blocks) {
		super(blocks);
		name = "TRAINING";
		completedBlockAccuracy = new ArrayList<Double>();
		Pattern.reproductionPattern = new NoloadPattern();	//initialize the 'empty' pattern (on which participants reproduce a previous pattern)	
	}
	
	@Override
	boolean phaseShouldFinish() {
		if (completedBlockAccuracy.size() < Options.trainingBlocksAboveCrit) {
			return false;
		} else {
			boolean allAboveCrit = true;
			for (double acc : completedBlockAccuracy.subList(completedBlockAccuracy.size() - Options.trainingBlocksAboveCrit, completedBlockAccuracy.size())) {
				if (acc < Options.trainingCriterion) {
					allAboveCrit = false;
				}
			}			
			return allAboveCrit;	
		}
	}

	@Override
	void finishPhase() {
		Experiment.startTestPhase();
	}

	@Override
	Block getNextBlock() {		
		if (!allBlocks.isEmpty()) { //start the next pre-created training block
			return super.getNextBlock();
		} else { //no more pre-created training blocks, create a new one and start it
			return Block.createTrainingBlock();
		}
	}
	
	@Override
	protected void startBlock(Block block) {
		currentBlockTrialAccuracy = new ArrayList<Integer>();
		super.startBlock(block);
	}	
	
	/**
	 * Calculate the mean accuracy in the current block, and display it.
	 */
	private void showBlockAccuracy() {
		int sum = 0;
		for (int tAcc : currentBlockTrialAccuracy) {
			sum += tAcc;
		}	
		double acc = (double) sum / currentBlockTrialAccuracy.size();		
		int accperc = (int) (100 * acc);
		
		completedBlockAccuracy.add(acc);
		Experiment.gui.xpPane.showBlockAccuracy(accperc);	
	}
	
	/**
	 * Start a new TrialGroup (beginning by showing that TrialGroup's pattern). 
	 * If there are no more TrialGroups in the current phase of the experiment, move on to the next phase.
	 */
	@Override
	void startNextTrialGroup() {
		if (currentBlock.isEmpty()) { //all trials for this block have been shown
			showBlockAccuracy();
		} else { //show the next trialgroup for this block			
			trialGroupNb++;
			currentTrialGroup = currentBlock.remove(0);
			showPrePatternFixationCross();
		}
	}
	
	@Override
	void finishTrialGroup() {		
		showPatternReproduction();		
	}
	
	@Override
	void finishTrial(boolean responseWasCorrect) {
		currentBlockTrialAccuracy.add(responseWasCorrect ? 1 : 0);
		showFeedback(responseWasCorrect);
	}
		
	/**
	 * Show whether or not the previous Trial was categorized correctly.
	 */
	private void showFeedback(boolean correct) {
		Experiment.gui.xpPane.showTrialFeedback(correct);
		timer.schedule(new TimerTask() {          
		    @Override
		    public void run() {
		    	startNextTrial();
		    }
		}, Options.durationFeedback);	
	}

	/**
	 * Display a fixation-cross for {@code Options.FIXATION_DURATION} MS; after this, show the pattern reproduction screen.
	 */
	private void showPrePatternFixationCross() {
		Experiment.gui.xpPane.showFixationCross();
		timer.schedule(new TimerTask() {          
		    @Override
		    public void run() {
		    	showPattern();
		    }
		}, Options.durationFixation);	
	}
	
	/**
	 * Display the indicated pattern on the screen. After {@code Options.PATTERN_DURATION} MS, the pattern is removed, and the next trial starts.
	 */
	private void showPattern() {
		Experiment.gui.hideCursor();
		Experiment.gui.xpPane.showPattern(currentTrialGroup.pattern);

		timer.schedule(new TimerTask() {          
			@Override
			public void run() {
				startNextTrial();    
			}
		}, Options.durationPattern);
	} 	
	
	/**
	 * Show the screen that allows the user to reproduce the pattern he/she recalls.
	 */
	private void showPatternReproduction() {		
		Pattern.reproductionPattern.clearDots();	
		Experiment.gui.xpPane.showPatternReproduction();    
	}

	/**
	 * Correct the pattern produced by the ss. 
	 * After this, the inter-trial blank screen is displayed.
	 */
	public void correctPatternReproduction() {
		int[] originalDots = currentTrialGroup.pattern.dotArray;     //original pattern, presented at the beginning of the trial
		int[] responseDots = Pattern.reproductionPattern.computeDotArray(); //pattern reproduced by the ss

		int hits = 0; 					//nb of dots correctly reproduced
		int misses = 0; 				//nb of dots that were not reproduced
		int falseAlarms = 0; 			//nb of dots placed in a spot where there was no dot in the original pattern 
		String originalDotString = "";  //16-digits (0 = no dot, 1 = dot) representation of the original 4x4 matrix
		String responseDotString = "";  //16-digits (0 = no dot, 1 = dot) representation of the user's response
		for (int iDot = 0; iDot < originalDots.length; iDot++) { //go through the 16 squares

			if (originalDots[iDot] == 1 && responseDots[iDot] == 1) {
				hits++;
			}

			if (originalDots[iDot] == 1 && responseDots[iDot] == 0) {
				misses++;
			}

			if (originalDots[iDot] == 0 && responseDots[iDot] == 1) {
				falseAlarms++;
			}

			originalDotString += originalDots[iDot]; //add a 0 or a 1, depending on whether there's a dot in this square in the original pattern
			responseDotString += responseDots[iDot]; //add a 0 or a 1, depending on whether there's a dot in this square in the reproduced pattern
		}

		Output.writePatternResponse(currentTrialGroup.pattern.loadString, originalDotString, responseDotString, originalDotString.equals(responseDotString), 
				hits, misses, falseAlarms);
		showInterPatternBlankScreen();	
	}
	
	/**
	 * A blank screen, after reproduction of the previous pattern, before displaying the subsequent one.
	 */
	void showInterPatternBlankScreen() {
		Experiment.gui.xpPane.showBlankScreen();
		timer.schedule(new TimerTask() {          
		    @Override
		    public void run() {
		    	startNextTrialGroup();    
		    }
		}, Options.durationInterpatternDelay);
	}
}
