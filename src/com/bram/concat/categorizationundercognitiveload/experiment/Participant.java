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

/**
 * Represents the user of the experiment.
 */
public class Participant {
	
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
	}
}
