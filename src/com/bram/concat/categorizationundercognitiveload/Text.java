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

import java.awt.Font;
import java.util.Calendar;

/**
 * Helper class that contains text displayed in the instructions and in the goodbye message, font settings, datafile headers, ...
 * All text that is displayed to the participant is written here, so if you wish to translate the experiment, you should only need this file.
 * Some
 */
public abstract class Text {
	
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
	 * Label for the button the participant uses to indicate he/she does not know the cue word.
	 */
	public static final String BTN_UNKNOWN_CUE = "Onbekend woord";
	
	/**
	 * Label for the button the participant uses to indicate he/she does not have any further associations to the cue word.
	 */
	public static final String BTN_NO_FURTHER_RESPONSES = "Geen verdere antwoorden";
	
	/**
	 * Title of the program (in your OS).
	 */
	public static final String TEXT_WINDOW_TITLE = "Experiment";
	
	/**
	 * Message that is displayed when participant responds too slowly.
	 */
	public static final String TEXT_TOO_SLOW = "Te traag!";
	
	/**
	 * Displayed at the beginning of the experiment, after having asked the ss information. After this, the training phase starts.
	 * Each String is displayed as a separate screen.
	 * Uses HTML formatting.
	 */
	public static final String[] TEXT_INSTRUCTIONS = {  
		"Welkom."+
		"<br><br>In dit experiment zal je twee dingen moeten doen. "
		+ "<br><br>Eerst zal je een 4 bij 4 veld te zien krijgen waarin een aantal punten staan. "
		+ "Jouw taak is om de locatie van de punten te memoriseren. Let op, het patroon wordt slechts voor een korte tijd aangeboden. "
		+ "Na enige tijd krijg je een leeg 4 bij 4 veld te zien waarin je vervolgens het originele patroon moet invullen. "
		+ "Dit doe je door met de muis op de velden te klikken waarin volgens jou een punt moet verschijnen. "
		+ "Als je per ongeluk een punt op de foute plaats hebt ingevuld, kan je dit terug verwijderen door er een tweede maal op te klikken. "
		+ "Het is de bedoeling dat je zo accuraat mogelijk werkt. ",
		
		"Dus je krijgt eerst een patroon te zien dat je moet onthouden en later krijg je een leeg veld te zien waarin je het patroon moet invullen. "
		+ "In de tijd daartussen, ga je een tweede opdracht krijgen." 
		+ "<br><br>Hierbij gaat er gevraagd worden om <b>drie</b> associaties te geven bij een bepaald woord. Concreet zal je bovenaan een woord te zien krijgen met daaronder een leeg vakje. "
		+ "Het is de bedoeling dat je in dat vakje de <b>eerste</b> associatie schrijft die direct bij je opkomt als je dit woord leest. Hiervoor kan je gewoon het toetsenbord gebruiken en "
		+ "wanneer je de associatie hebt ingetypt, druk je op ENTER. Vervolgens zal er een nieuw leeg vakje verschijnen waarin je de <b>tweede</b> associatie kan invullen. Wanneer je dit hebt "
		+ "gedaan en opnieuw op ENTER hebt gedrukt, verschijnt er weer een leeg vakje voor de <b>derde</b> associatie. Druk je ten slotte nogmaals op ENTER verschijnt er een nieuw woord "
		+ "waarvoor je drie associaties moet geven." 
		+ "<br><br>Belangrijk: Geef enkel associaties voor het woord dat bovenaan getoond wordt (niet op basis van een vorig antwoord) en vermijd het gebruik van zinnen. "
		+ "Klik op de Onbekend Woord knop rechtsonder als je het woord niet kent en op de Geen associatie knop als je geen andere associaties vlot kan bedenken.",

		"Voor de geheugentaak is het de bedoeling dat je zo <b>accuraat</b> mogelijk bent. Voor de associatietaak is het daarnaast ook de bedoeling dat je zo <b>snel</b> mogelijk antwoordt, "
		+ "als je te lang wacht verschijnt de boodschap “Te traag!”. Snelheid is niet belangrijk in de geheugentaak. Beide taken zijn even belangrijk. Eerst zal je de procedure "
		+ "kunnen oefenen in een oefenfase." 	
		}; 

	
	/**
	 * Displayed after the training phase, and before the actual experiment.
	 */
	public static final String TEXT_POSTTRAINING_INSTRUCTIONS =
			"Nu begint het echte experiment. Het experiment bestaat uit drie delen, tussenin heb je de mogelijkheid om te pauzeren. Mocht je nog vragen hebben, roep dan de proefleider.";
		
	/**
	 * Displayed between two blocks, but not before the last block.
	 */
	public static final String TEXT_INTERBLOCK = "Je kan nu even pauzeren. Klik op 'Klaar' als je aan het volgende deel wil beginnen.";
	
	/**
	 * Displayed before the last block.
	 */
	public static final String TEXT_INTERBLOCK_LAST = "Je kan nu even pauzeren. Klik op 'Klaar' als je aan het laatste deel wil beginnen.";
		
	/**
	 * Displayed at the end of the experiment.
	 * Uses HTML formatting.
	 */
	public static final String TEXT_XP_OVER_MESSAGE = 
			"<br><br><br><br>Het experiment is afgelopen." +
			"<br><br>Bedankt voor je medewerking!" +
			"<br><br>Je mag het programma nu sluiten.";

	/**
	 * Output headers i.e. first line of every datafile.
	 */
	public static final String HEADER = "trialNb\tgroupNb\tindexInGroup\tcue\tassociation\tassoNb\ttimeToFirstKeypress\ttimeToSubmission\tlist\t"
			+ "load\toriginal_pattern\treproduced_pattern\tcorrect\thits\tmisses\tfalseAlarms";
	
	
	/**
	 * Font used in all instruction screens and in the goodbye screen.
	 */
	public static final Font FONT_INSTRUCTIONS = new Font("Serif", Font.PLAIN, 24);
	
	/**
	 * The cue is displayed in this font.
	 */
	public static final Font FONT_CUE = new Font("Serif", Font.PLAIN, 50);
	
	/**
	 * The previous associations of this participant to the current cue are displayed in this font.
	 */
	public static final Font FONT_PREVIOUS_RESPONSES = new Font("Serif", Font.PLAIN, 30);
	
	/**
	 * The text-field in which the participant gives association is formatted in this font.
	 */
	public static final Font FONT_ANSWER_TEXTFIELD = new Font("Serif", Font.PLAIN, 40);
	
	/**
	 * The errormessage when participant is too slow is displayed in this font.
	 */
	public static final Font FONT_TOO_SLOW_MESSAGE = new Font("Serif", Font.PLAIN, 60);
		
	/**
	 * @return The current month and day, formatted as a string, seperated by an underscore.
	 */
	public static String getDate() {
		Calendar cal = Calendar.getInstance();
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);	
		String day = String.valueOf(cal.get(Calendar.DATE));
		return month + "_" + day;
	}
	
	/**
	 * @return The current time, formatted as a string, seperated by an underscore.
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