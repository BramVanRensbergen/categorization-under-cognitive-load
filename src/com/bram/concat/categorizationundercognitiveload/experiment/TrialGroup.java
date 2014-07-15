/**
  	This file is part of a task where participants give word associations
  	under cognitive load.
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
import java.util.List;

import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.pattern.HighloadPattern;
import com.bram.concat.categorizationundercognitiveload.pattern.LowloadPattern;
import com.bram.concat.categorizationundercognitiveload.pattern.Pattern;

@SuppressWarnings("serial")
public class TrialGroup extends ArrayList<Trial>{
		
	 /**
     * @return All trials divided in {@code TrialGroups}, each containing {@code Options.TRIALS_PER_GROUP} trials (defaults to 5).
	 * @throws IncorrectNumberOfTrialsException Total number of trials should a multiple of {@code Options.TRIALS_PER_GROUP}
     */
    public static List<TrialGroup> createTrialGroups(List<Trial> trials) throws IncorrectNumberOfTrialsException{
    	List<TrialGroup> trialGroups = new ArrayList<TrialGroup>();
    	
    	while (!trials.isEmpty()) {
    		List<Trial> trialsInThisGroup = new ArrayList<Trial>();
			
    		for (int iTrial = 0; iTrial < Options.TRIALS_PER_GROUP; iTrial++) { //0 to 4
				if (!trials.isEmpty()) {
					trialsInThisGroup.add(trials.remove(0));
				} else {
					//this should not occur: experimenter should make sure the number of trials is a multiple of TRIALS_PER_GROUP
					throw new IncorrectNumberOfTrialsException("Not enough trials left to create group of "+ Options.TRIALS_PER_GROUP + " trials! " +
					"Make sure the numer of trials in " + Options.STIM_FILE +" is a multiple of "+ Options.TRIALS_PER_GROUP +"!");
				}
			}
			trialGroups.add(new TrialGroup(trialsInThisGroup, trialsInThisGroup.get(0).lowLoad));
		}    	
    	return trialGroups;
    }
    
    /**
     * The Pattern corresponding to this trialgroup. Displayed before the first trial, replicated by participant after the last trial.
     */
	public Pattern pattern;
		
	/**
	 * 
	 * @param trials The {@code trials.size()} trials that will make up this group.
	 * @param lowLoad All trials in this trialgroup have this load (difficulty)
	 * @throws IncorrectNumberOfTrialsException When {@code trials.size()} is not the same as {@Options.TRIALS_PER_GROUP}. 
	 */
	public TrialGroup(List<Trial> trials, boolean lowLoad) throws IncorrectNumberOfTrialsException {
		super(trials);
		if (trials.size() != Options.TRIALS_PER_GROUP) {
			//should not occur
			throw new IncorrectNumberOfTrialsException("Trying to create a group of " + Options.TRIALS_PER_GROUP + " trials, but only " 
					+ trials.size() + " were passed to the constructor!");
		} else {			
			for (int i = 0; i < trials.size(); i++) {
				trials.get(i).indexInTrialGroup = i;
			}
			
			if (lowLoad) {
				pattern = new LowloadPattern();
			} else {
				pattern = new HighloadPattern();
			}
		}
		
	}
	
	/**
	 * An error to indicate that number of trials is not a multiple of {@code Options.TRIALS_PER_GROUP}.
	 * We do NOT allow trialgroups smaller than this!
	 */
	public static class IncorrectNumberOfTrialsException extends RuntimeException {		
		public IncorrectNumberOfTrialsException(String message){
			super(message);
		}
    }
}
