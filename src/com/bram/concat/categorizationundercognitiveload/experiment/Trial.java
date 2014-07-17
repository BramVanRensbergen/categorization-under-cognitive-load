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

import com.bram.concat.categorizationundercognitiveload.Options;

/**
 * Represents one trial in the experiment.
 * Each trial begins with a fixation cross, then the prime, a blank screen, the target, and finally another blank screen.
 */
public class Trial {
	 	
	/**
	 * Index of this trial in its trial group i.e. 0..3.
	 */
	public int indexInTrialGroup; 
	
	/**
	 * The stimulus i.e. image that will be displayed in this trial.
	 */
	public Stimulus stimulus;
	
	/**
	 * The name of the group to which this trial's stimulus corresponds.
	 * Not necessarily the same as stimulus.group, as group membership is reversed for half of the participants.
	 */
	public String group;
	
    Trial(Stimulus stimulus) {
        this.stimulus = stimulus;
        
        if (!Options.reversedGroupnames) { //regular group names
        	if (stimulus.group == 1) {
        		group = Options.group1name;
        	} else {
        		group = Options.group2name;
        	}  
        } else { //reverse group names!	
        	if (stimulus.group == 1) {
        		group = Options.group2name;
        	} else {
        		group = Options.group1name;
        	}        	
        } 
    }
}
