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

import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.bram.concat.categorizationundercognitiveload.Options.IncorrectOptionException;

/**
 * Helper class that contains text displayed in the instructions and in the goodbye message, font settings, datafile headers, ...
 * All text that is displayed to the participant is written here, so if you wish to translate the experiment, you should only need this file.
 */
public abstract class Text {

	/*
	 * The following six variables should be overwritten by text contained in Options.TEXT_FILE 
	 */
		
	/**
	 * Displayed at the beginning of the experiment, after having asked the ss information. After this, the training phase starts.
	 * Each entry is displayed as a separate screen.
	 * Uses HTML formatting.
	 */
	public static List<String> textInstructions = new ArrayList<String>();  
	
	/**
	 * Displayed at the end of each block during training phase, indicating the average performance during that block.
	 */
	public static String textBlockAccuracy = "Je behaalde een score van @ACC@%.";
	
	/**
	 * Displayed
	 */
	public static String textInterblockMessage = "Je gaat nu beginnen aan blok @BLOCK_NB@.";
	
	/**
	 * Displayed after the training phase, and before the actual experiment.
	 */
	public static String textPosttrainingInstructions = "Nu begint het echte experiment.";
		
	/**
	 * Displayed halfway the test phase.
	 */
	public static String textHalfway = "Je kan nu even pauzeren. Klik op 'Klaar' als je aan het volgende deel wil beginnen.";
		
	/**
	 * Displayed at the end of the experiment.
	 * Uses HTML formatting.
	 */
	public static String textXpOver = "Het experiment is afgelopen.";
		
	/**
	 * Title of the program (in your OS).
	 */
	public static String textWindowTitle = "Experiment";
			
	
	/*
	 * These are only defined in this file.
	 */
	
	/**
	 * Label for the ID form entry.
	 */
	public static final String FORM_ID = "Proefpersoon-nr (vraag aan proefleider): ";
	
	/**
	 * Label for the age form entry.
	 */
	public static final String FORM_AGE = "Leeftijd: ";
	
	/**
	 * Label for the gender form entry.
	 */
	public static final String FORM_GENDER = "Geslacht: ";
	
	/**
	 * Label for the 'male' radiobutton.
	 */
	public static final String FORM_MALE = "Man";
	
	/**
	 * Label for the 'female' radiobutton.
	 */
	public static final String FORM_FEMALE = "Vrouw";
	
	/**
	 * Error displayed when user's ID is not valid.
	 */
	public static final String FORM_ERROR_ID = "Gelieve je proefpersoon-nummer correct in te geven! (Enkel cijfers.)";
	
	/**
	 * Error displayed when user's age is not valid.
	 */
	public static final String FORM_ERROR_AGE = "Gelieve je leeftijd correct in te geven! (Enkel cijfers.)";
	
	/**
	 * Error displayed when user's gender is not valid.
	 */
	public static final String FORM_ERROR_GENDER = "Je hebt je geslacht nog niet aangeduid!";
		
	/**
	 * Label for the button the participant uses to indicate he/she is ready to progress to the next screen.
	 */
	public static final String BTN_READY = "Klaar";
	
	/**
	 * Label for the button the participant uses to indicate he/she is ready to begin the experiment.
	 */
	public static final String BTN_BEGIN = "Beginnen";
	
	/**
	 * Label for the button the participant uses to indicate he/she is ready to progress to the next screen.
	 */
	public static final String BTN_CONTINUE = "Verder Gaan";
	
	/**
	 * Label for the button the participant uses to go to the previous instructions screen.
	 */
	public static final String BTN_PREVIOUS = "Vorige";
	
	/**
	 * Label for the button the participant uses to go to the next instructions screen.
	 */
	public static final String BTN_NEXT = "Volgende";
	
	/**
	 * Label for the button the participant uses to quit the program (at the end).
	 */
	public static final String BTN_QUIT = "Afsluiten";

	/**
	 * Font used in all instruction screens and in the goodbye screen.
	 */
	public static final Font FONT_INSTRUCTIONS = new Font("Serif", Font.PLAIN, 24);
	
	/**
	 * Set the indicated text entry to contain the indicated value.
	 */
	public static void setText(String entry, String value) {
		switch (entry) {
			case "textInstructions": 				textInstructions.add(value.replace("\n", "<br>")); 	break;
			case "textBlockAccuracy": 				textBlockAccuracy = value; 							break;
			case "textInterblockMessage":			textInterblockMessage = value;						break;
			case "textPosttrainingInstructions":	textPosttrainingInstructions = value.replace("\n", "<br>");	break;
			case "textHalfway": 					textHalfway = value.replace("\n", "<br>"); 			break;
			case "textXpOver": 						textXpOver = value.replace("\n", "<br>"); 			break;
			case "textWindowTitle": 				textWindowTitle	= value; 							break;
			default: throw new IncorrectOptionException("Unknown Text: " + entry + " with value " + value + " described in " + Options.TEXT_FILE);
		}	
	}	
	
	/**
	 * @return The current month and day, formatted as a string, separated by an underscore.
	 */
	public static String getDate() {
		Calendar cal = Calendar.getInstance();
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);	
		String day = String.valueOf(cal.get(Calendar.DATE));
		return month + "_" + day;
	}
	
	/**
	 * @return The current time, formatted as a string, separated by an underscore.
	 */
	public static String getTime() {	
		Calendar cal = Calendar.getInstance();
		
		String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		if (hour.length() == 1) {
			hour = "0" + hour; //pad with zero for 0..9
		}
		
		String min = String.valueOf(cal.get(Calendar.MINUTE));
		if (min.length() == 1) {
			min = "0" + min; //pad with zero for 0..9
		}
		
		String sec = String.valueOf(cal.get(Calendar.SECOND));
		if (sec.length() == 1) {
			sec = "0" + sec; //pad with zero for 0..9
		}
		
		return hour + "_" + min + "_" + sec;	
	}
}