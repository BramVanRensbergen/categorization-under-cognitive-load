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

package com.bram.concat.categorizationundercognitiveload;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * A list of options used throughout the experiment.
 * Feel free to modify some of these to tweak the experiment's parameters.
 */
public abstract class Options {
	
	/*
	 * FEEL FREE TO CHANGE THESE OPTIONS
	 */
	
	/**
	 * Used only during development; should ALWAYS be set to {@code TRUE} when an actual participant is using the experiment.
	 * When {@code TRUE}, the experiment is considerably faster, and does not ask for participant info (uses some default values).
	 */
	public static final boolean DEBUG = true;
	
	/**
	 * Data files are written out to this directory.
	 * Make sure this directory exists, and the program has write access to it.
	 */
	public static final String DATA_FOLDER    = "data/";
	
	/**
	 * File containing the stimuli that will be used in the experiment.
	 */
	public static final String STIM_FILE = "stimuli.txt";
	
	/**
	 * True: GUI has no titlebar, close-buttons, etc.
	 */
	public static final boolean DECORATED = false;
	
	/**
	 * Pattern (that the ss will have to reproduce later) will be displayed for this many MS. 
	 * Default value: 750 MS.
	 */
	public static final int PATTERN_DURATION  = (DEBUG ? 70 : 750); 
	
	/**
	 * Fixation cross will be displayed for this many MS. 
	 * Default value: 500 MS.
	 */
	public static final int FIXATION_DURATION = (DEBUG ? 50 : 500);
	
	/**
	 * Delay between two trials.
	 * Default: 500ms. 
	 */
	public static final int INTERTRIAL_DELAY  = (DEBUG ? 180 : 500);

	/**
	 * Delay between the pattern reproduction screen of one trialGroup, and the pattern screen of the subsequent trialGroup.
	 * Default: 2000ms.
	 */
	public static final int INTERN_PATTERN_DELAY  = (DEBUG ? 200 : 2000);
	
	/**
	 * Participants have this many MS to give each association; any longer, and the program skips to the next trial.
	 */
	public static final int MAX_RESPONSE_DURATION = (DEBUG ? 1200 : 7000);
	
	/**
	 * When participants answer too slowly, an error is displayed for this many MS before moving on.
	 */
	public static final int ERROR_DURATION = (DEBUG ? 1000 : 1000);
	
	/**
	 * Number of associations participants give to each cue. Defaults to 3; using other values may mess with the UI.
	 */
	public static final int N_ASSOCIATIONS = 3;
	
	/**
	 * The experiment will be split up in this many blocks, with a break for participants between each block.
	 */
	public static final int N_BLOCKS = 3;
	
	/*
	 * ONLY CHANGE THESE OPTIONS IF YOU KNOW WHAT YOU ARE DOING
	 */
	
	/**
	 * Width and height of each cell of the grid in which the dot-pattern is displayed.
	 */
	public static final int GRID_PIXELSIZE    = 150;
	
	/**
	 * Size (in pixels) of the gui.
	 */
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	//computers current resolution 

	/**
	 * Number of trials in one group; each group is assigned one pattern.
	 */
	public static final int TRIALS_PER_GROUP = 5;
}