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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bram.concat.categorizationundercognitiveload.Options;

/**
 * A block consists of a number of TrialGroups, each of which contain a number of Trials.
 */
@SuppressWarnings("serial")
public class Block extends ArrayList<TrialGroup>{
	
	/**
	 * During the training phase, participants only see this a subset of each group. 
	 * These are picked at random from all stimuli of that group.
	 * In other words, different participants see different stimuli during the training phase.
	 */
	private static List<Stimulus> trainingStimuli;
	
	/**
	 * A number of training blocks are created at program start.
	 * Note that the training phase continues until participant reaches 90% correct classifications in two subsequent blocks.
	 * If this takes more than the amount of blocks created in this method, new blocks are called using {@code createTrainingBlock()}.
	 */
	public static List<Block> createInitialTrainingBlocks() {
		
		//each participant only sees six images of each group, we'll shuffle the files and pick 6
		List<Stimulus> allGroup1Stimuli = Arrays.asList(Stimulus.group1stimuli);
		List<Stimulus> allGroup2Stimuli = Arrays.asList(Stimulus.group2stimuli);
		
		Collections.shuffle(allGroup1Stimuli);
		Collections.shuffle(allGroup2Stimuli);		
				
		//store the 6 random stimuli of each group in a list
		trainingStimuli = new ArrayList<Stimulus>();
		trainingStimuli.addAll(allGroup1Stimuli.subList(0, Options.trainingStimuliPerGroup));
		trainingStimuli.addAll(allGroup2Stimuli.subList(0, Options.trainingStimuliPerGroup));
		//now contains the 12 stimuli this participant will see during training

		List<Block> initialTrainingBlocks = new ArrayList<Block>();
		for (int i = 0; i < Options.initialTrainingBlocksAmount; i++) {						
			initialTrainingBlocks.add(createTrainingBlock());
		}		
		
		return initialTrainingBlocks;
	}
	
	/**
	 * Create a new training block.
	 * @param blockNumber of the block
	 * @return A new training block.
	 */
	public static Block createTrainingBlock() {
		return createBlock(trainingStimuli, true);
	}
		
	/**
	 * Create all blocks the participants will see during the testing phase.
	 */
	public static List<Block> createAllTestBlocks() {
		List<Stimulus> usedStimuli = new ArrayList<Stimulus>(); 
		usedStimuli.addAll(Arrays.asList(Stimulus.group1stimuli));
		usedStimuli.addAll(Arrays.asList(Stimulus.group2stimuli));
		//now contains all 36 stimuli
		
		List<Block> testBlocks = new ArrayList<Block>();
		for (int i = 0; i < Options.testBlocksAmount; i++) {
			testBlocks.add(createBlock(usedStimuli, false));
		}	
		return testBlocks;
	}
	
	/**
	 * Create a block that contains the indicated stimuli.
	 */
	private static Block createBlock(List<Stimulus> stimuli, boolean training) {
		List<TrialGroup> trialGroups = new ArrayList<TrialGroup>(); ;
		List<Trial> trialsInCurrentTrialGroup = new ArrayList<Trial>();

		Collections.shuffle(stimuli);	//randomize stimulusorder to the order in which they will be displayed in this block

		//go through all stimuli
		for (Stimulus s:stimuli) {				
			if (trialsInCurrentTrialGroup.size() < Options.trialsPerTrialGroup) { //still working on the current trialGroup
				trialsInCurrentTrialGroup.add(new Trial(s));
			} else { //current trialGroup is complete, create it and start a new one
				trialGroups.add(new TrialGroup(trialsInCurrentTrialGroup, training));
				trialsInCurrentTrialGroup.clear();
			}				
		}			

		return new Block(trialGroups);
	}
	
	/**
	 * A block contains a number of trials, grouped into trialgroups.
	 * Training blocks contain 12 stimuli, 6 from each group, randomly selected out of all stimuli (per participant).
	 * Test blocks contain 36 stimuli, 18 from each group.
	 */
	public Block(List<TrialGroup> trialGroups) {
		super(trialGroups);
	}	
}
