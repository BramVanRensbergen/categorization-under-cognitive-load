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
import com.bram.concat.categorizationundercognitiveload.Text;
import com.bram.concat.categorizationundercognitiveload.io.Output;

/**
 * Represents the user of the experiment.
 */
public class Participant {
	
	/**
	 * Full factorial of 3 conditions, in randomized order.
	 * Load (true: highload) x reversedGroupNames (true: reversed) x reversedButtonOrder (true: reversed)
	 * Each subject will be assigned one of these, depending on their subject number.
	 * In other words, conditions are counterbalanced (not randomized).
	 */
	private static final boolean[][] CONDITION_MATRIX = { 
		{true, false, true},
		{false, true, false},
		{false, true, true},
		{true, false, false},
		{false, false, false},
		{false, false, true},
		{true, true, false},
		{true, true, true}		
	};   
	
	/**
	 * Create a new data file with the ss's information, then display the instruction.
	 */
	public static void createParticipant(int ssNb, int age, char gender) {
		//create participant
		Experiment.pp = new Participant(ssNb, age, gender);
		
		//create the datafile for the participant
		Output.initializeWriting(ssNb + "_" + age + "_" + gender + "_" + Text.getDate() + "_" + Text.getTime() + ".txt");
				
		//create the trials the participant will see
		//only done here, not before, as participant number influences the condition of their trials
		Experiment.createTrials();			
	}	
	
	/**
	 * The participant's number. Should be given to him/her by the experimenter.
	 */
	public int ssNb;	
	
	/**
	 * Filled out by participant.
	 */
	public int age;	
	
	/**
	 * Filled out by participant.
	 */
	public char gender;
	
	public Participant(int ssNb, int age, char gender) {
		this.ssNb = ssNb;		
		this.age = age;		
		this.gender = gender;
		
		int ssOffset = ssNb % CONDITION_MATRIX.length; //0 to 7

		Options.highload = CONDITION_MATRIX[ssOffset][0];
		Options.reversedGroupnames = CONDITION_MATRIX[ssOffset][1];
		Options.reversedButtonPosition = CONDITION_MATRIX[ssOffset][2];		
	}
}
