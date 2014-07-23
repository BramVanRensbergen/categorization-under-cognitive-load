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

package com.bram.concat.categorizationundercognitiveload;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * A list of options used throughout the experiment.
 * The first group of options is defined only in this class.
 * The second group is set when the participant is created; the participant's number is used to counterbalance these.
 * The last group is set in {@code OPTION_FILE}. Some default values are set here, just in case.
 * The default values to this group should NOT be considered optimal; rather they are useful during debugging as they lead to a shorter/faster program.
 */
public abstract class Options {
	
	/**
	 * Some of the options below can be overwritten by options defined in this file.
	 * This file is read by {@code IO.readAndSetOptions()}
	 * Syntax: OPTION_NAME = OPTION_VALUE
	 * One option per line.
	 */
	public static final String OPTION_FILE = "data/options.txt";
		
	/**
	 * Contains text to the instructions etc.
	 * This file is read by {@code IO.readText()}
	 * The file contains several segments, each preceded by {@code @DEFINE <segmentname>}. After this, a number of lines containing the text of that segment.
	 * The first segment, textInstructions, is special in that in can contain multiple pages (each of which is preceeded by {@code @DEFINE textInstructions}
	 */
	public static final String TEXT_FILE = "data/text.txt";
	
	/**
	 * Used only during development; should ALWAYS be set to {@code TRUE} when an actual participant is using the experiment.
	 * When {@code TRUE}, options in {@code OPTION_FILE} are ignored, and the program runs with default, fast, settings.
	 */
	public static final boolean DEBUG = true;
		
	/**
	 * True: GUI has no titlebar, close-buttons, etc.
	 */
	public static final boolean DECORATED = false;
	
	/**
	 * Width and height of each cell of the grid in which the dot-pattern is displayed.
	 */
	public static final int GRID_PIXELSIZE    = 150;
	
	/**
	 * Size (in pixels) of the gui.
	 */
	public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();	//computers current resolution 
	
	public static final Color backgroundColor = Color.white;
	
	/*
	 * These options are set when the participant is created.
	 */
	
	/**
	 * True: the patterns the participant has to memorize will be difficult.  
	 */
	public static boolean highload;
	
	/**
	 * True: the labels for the groups are reversed, i.e. {@code group2name} refers to the stimuli of group 1, and vice versa.
	 */
	public static boolean reversedGroupnames;
	
	/**
	 * True: reverse the order of the buttons, i.e. button 2 is on the left, button 1 is on the right.
	 */
	public static boolean reversedButtonPosition;
	
	/*
	 * THESE OPTIONS CAN ALL BE OVERWRITTEN BY OPTIONS FOUND IN OPTIONS_FILE
	 */

	/**
	 * The name of one of the two categories. Is used for the buttons with which the participant answers, and for writing away results. 
	 */
	public static String group1name = "Mapapilio";
	
	/**
	 * The name of one of the two categories. Is used for the buttons with which the participant answers, and for writing away results. 
	 */
	public static String group2name = "Geen Mapapilio";
	
	/**
	 * True (default): enable the dot patterns, which participants memorize during the training phase.
	 */
	public static boolean includePatterns = false;
	
	/**
	 * Training continues until participant has a sufficient accuracy in this many preceding blocks. 
	 * Default = 2
	 */
	public static int trainingBlocksAboveCrit = 2;
	
	/**
	 * If participant reaches this amount of correct classifications, accuracy in this block qualifies as sufficient.
	 * Default = 0.9
	 */
	public static double trainingCriterion = 0.1;
		
	/**
	 * During the training phase, participants only see this many stimuli of each group.
	 * Default = 6 
	 * IMPORTANT: this value, x2, should be divisible by {@code trialsPerTrialGroup}!!! Otherwise it's impossible to create trialgroups of equal size.
	 * The stimuli are picked at random from all stimuli of that group.
	 * In other words, different participants see different stimuli during the training phase.
	 */
	public static int trainingStimuliPerGroup = 4; 
	
	/**
	 * The number of training blocks to create before starting the experiment. 
	 * Training phase continues until participant reaches 90% correct classifications in two subsequent blocks.
	 * If this takes more than this amount of blocks, new blocks are created on the fly.
	 */
	public static int initialTrainingBlocksAmount = 10;
	
	/**
	 * Number of blocks during testing phase.
	 * Each block contains all stimuli once.
	 */
	public static int testBlocksAmount = 2;
	
	/**
	 * Number of trials in one group; each group is assigned one pattern.
	 * IMPORTANT: ALL trialgroups have this amount of trials. This means that the total number of stimuli/images should be divisible by this amount!
	 */
	public static int trialsPerTrialGroup = 4;
	
	/**
	 * Displayed during training phase when participant made a correct classification.
	 */
	public static String fileFeedbackCorrect = "data/feedback_right.jpg";
	
	/**
	 * Displayed during training phase when participant made an incorrect classification.
	 */
	public static String fileFeedbackWrong = "data/feedback_wrong.jpg";
	
	/**
	 * Data files are written out to this directory.
	 * Per participant, one file is created.
	 */
	public static String dirOutput    = "data/output";
	
	/**
	 * File containing the stimuli that will be used in the experiment.
	 * In this folder, there should be some amount of images.
	 */
	public static String dirGroup1Stimuli = "data/stimuli/test1";
	public static String dirGroup2Stimuli = "data/stimuli/test2";
	
	/**
	 * Pattern (that the ss will have to reproduce later) will be displayed for this many MS. 
	 * Default value: 750 MS.
	 */
	public static int durationPattern  = 70; 
	
	/**
	 * Fixation cross will be displayed for this many MS. 
	 * Default value: 500 MS.
	 */
	public static int durationFixation = 50;
	
	/**
	 * Delay between two trials.
	 * Default: 500ms. 
	 */
	public static int durationIntertrialDelay  = 180;

	/**
	 * Delay between the pattern reproduction screen of one trialGroup, and the pattern screen of the subsequent trialGroup.
	 * Default: 2000ms.
	 */
	public static int durationInterpatternDelay  = 200;

	/**
	 * During training phase, after each trial, participant is given feedback on whether they had classified it correctly. This feedback is displayed for this many ms.
	 */
	public static int durationFeedback = 300;
		
	/**
	 * The width of the two buttons the participant uses to categorize the stimuli. If you use particularly long button names, you might have to increase this.
	 */
	public static int responseButtonWidth = 200;
	
	/**
	 * Set the indicated option to the indicated value.
	 * This function is called for each line in {@code OPTION_FILE}.
	 */
	public static void setOption(String option, String value) {
		if (!DEBUG) {
			switch (option) {
				case "group1name": 					group1name 					= value; 						break;
				case "group2name": 					group2name 					= value; 						break;
				case "includePatterns":				includePatterns 			= Boolean.valueOf(value);		break;
				case "trainingBlocksAboveCrit": 	trainingBlocksAboveCrit 	= Integer.parseInt(value); 		break;
				case "trainingCriterion": 			trainingCriterion 			= Double.parseDouble(value); 	break;
				case "trainingStimuliPerGroup": 	trainingStimuliPerGroup 	= Integer.parseInt(value); 		break;
				case "initialTrainingBlocksAmount": initialTrainingBlocksAmount = Integer.parseInt(value); 		break;
				case "testBlocksAmount": 			testBlocksAmount 			= Integer.parseInt(value); 		break;
				case "trialsPerTrialGroup": 		trialsPerTrialGroup 		= Integer.parseInt(value); 		break;
				case "fileFeedbackCorrect": 		fileFeedbackCorrect 		= value; 						break;
				case "fileFeedbackWrong": 			fileFeedbackWrong 			= value; 						break;
				case "dirOutput" : 					dirOutput 					= value; 						break;
				case "dirGroup1Stimuli": 			dirGroup1Stimuli 			= value; 						break;
				case "dirGroup2Stimuli": 			dirGroup2Stimuli 			= value; 						break;
				case "durationPattern": 			durationPattern 			= Integer.parseInt(value); 		break;
				case "durationFixation": 			durationFixation 			= Integer.parseInt(value); 		break;
				case "durationIntertrialDelay": 	durationIntertrialDelay 	= Integer.parseInt(value); 		break;
				case "durationInterpatternDelay": 	durationInterpatternDelay 	= Integer.parseInt(value); 		break;
				case "durationFeedback": 			durationFeedback 			= Integer.parseInt(value); 		break;
				case "responseButtonWidth": 		responseButtonWidth 		= Integer.parseInt(value); 		break;
				default: throw new IncorrectOptionException("Unknown option: " + option + " with value " + value + " described in " + OPTION_FILE);
			}
		}		
	}
	
	/**
	 * An unknown option was passed in {@code OPTION_FILE}.
	 */
	@SuppressWarnings("serial")
	public static class IncorrectOptionException extends RuntimeException {		
		public IncorrectOptionException(String message){
			super(message);
		}
    }	
}