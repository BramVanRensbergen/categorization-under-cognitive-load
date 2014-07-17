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
import java.util.List;

import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.pattern.HighloadPattern;
import com.bram.concat.categorizationundercognitiveload.pattern.LowloadPattern;
import com.bram.concat.categorizationundercognitiveload.pattern.Pattern;

/**
 * A trialgroup comprises a number of Trials.
 * During the training phase, each trialgroup is preceded by a pattern to be memorized by the participant. 
 * At the end of the trialgroup, the participants recreates the pattern.
 * 
 * During the test-phase, there is nothing special about a trialgroup (with the exception that responses are only written to disk once a trialgroup is completed).
 */
@SuppressWarnings("serial")
public class TrialGroup extends ArrayList<Trial>{

    /**
     * In the training phase, participants will be asked to memorize this pattern, and re-create it afterwards.
     */
	public Pattern pattern;
			
	/**
	 * 
	 * @param trials The {@code trials.size()} trials that will make up this group.
	 * @param training Whether this is a trialgroup that will be displayed in the training phase. If true, a pattern is created and associated to the trialgroup.
	 * @throws IncorrectNumberOfTrialsException When {@code trials.size()} is not the same as {@Options.TRIALS_PER_GROUP}. 
	 */
	public TrialGroup(List<Trial> trials, boolean training) throws IncorrectNumberOfTrialsException {
		super(trials);
		
		if (trials.size() != Options.trialsPerTrialGroup) {
			//should not occur
			throw new IncorrectNumberOfTrialsException("Trying to create a group of " + Options.trialsPerTrialGroup + " trials, but only " 
					+ trials.size() + " were passed to the constructor!");
		} else {			
			for (int i = 0; i < trials.size(); i++) {
				trials.get(i).indexInTrialGroup = i;
			}
			
			if (training) { //we only show the dot pattern during training phase
				if (Options.highload) {
					pattern = new HighloadPattern();
				} else {
					pattern = new LowloadPattern();
				}
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
