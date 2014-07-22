package com.bram.concat.categorizationundercognitiveload.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.Text;
import com.bram.concat.categorizationundercognitiveload.experiment.Experiment;
import com.bram.concat.categorizationundercognitiveload.experiment.Trial;
import com.bram.concat.categorizationundercognitiveload.experiment.phase.ExperimentPhase;

public class Output {
	
	/**
	 * Name of the file to which this participant's data will be written.
	 * This filename contains student number, age, gender, date, and time.
	 */
	private static String outputFilename;
	
	/**
	 * Used to write this participant's data to his/her datafile.
	 */
	private static PrintWriter out;  
	

	/**
	 * Output headers i.e. first line of every datafile.
	 */
	private static final String[] HEADER = { 
		"ssId",
		"ssAge",
		"ssGender",
		"date",
		"reversedGroups",
		"reversedButtonPosition",
		"load",
		"currentTime",
		"phase", 
		"trial",
		"trialGroup",
		"block",
		"indexInGroup",
		"filename",
		"group",
		"response",
		"correct",
		"RT",
		"patternLoad",
		"originalPattern",
		"reproducedPattern",
		"patternWasCorrect",
		"patternHits",
		"patterMisses",
		"patternFalseAlarms"
	}; 

	/**
	 * Create a datafile for the current user; filename contains student number, age, gender, date, and time.
	 */
	public static void initializeWriting(String filename) {
		File outputdir = new File(Options.dirOutput);
		outputdir.mkdir(); //does nothing if dir already exists
		
		outputFilename = outputdir.getPath() + File.separatorChar + filename;
		try {	
			out = new PrintWriter(new FileWriter(outputFilename));
			out.close();
		} catch (IOException e){ 
			e.printStackTrace();   
		}	
				
		writeData(HEADER, false); //write the headers as the first line
	}

	public static void writeResponse(Trial t, String response, int RT) {
		ExperimentPhase phase = Experiment.currentPhase;		
		String[] entry = {
				Integer.toString(Experiment.pp.ssNb),
				Integer.toString(Experiment.pp.age),
				Character.toString(Experiment.pp.gender),
				Text.getDate(),
				Boolean.toString(Options.reversedGroupnames),
				Boolean.toString(Options.reversedButtonPosition),
				Options.highload ? "HIGH" : "LOW",
				Text.getTime(),
				phase.name,
				Integer.toString(phase.trialNb),
				Integer.toString(phase.trialGroupNb),
				Integer.toString(phase.blockNb),
				Integer.toString(t.indexInTrialGroup + 1),
				t.stimulus.filename,
				t.group,
				response,
				Boolean.toString(response.equals(t.group)),
				Integer.toString(RT)			
		};

		writeData(entry, true);		
	}
	
	public static void writePatternResponse(String patternLoad, String originalPattern, String reproducedPattern, boolean patternWasCorrect, 
			int patternHits, int patternMisses, int patternFalseAlarms) {
		String[] entry = {
				patternLoad,
				originalPattern,
				reproducedPattern,
				Boolean.toString(patternWasCorrect),
				Integer.toString(patternHits),
				Integer.toString(patternMisses),
				Integer.toString(patternFalseAlarms)
		};
		writeData(entry, false);
	}
	
	/**
	 * Write out the indicated array of strings tab-delimited to this participant's data file, and start a new line.
	 */
	private static void writeData(String[] a, boolean startOnNewLine) {	
		String joined = "";
		for (String s : a) {
			joined += s + "\t";
		}		
		
		try {	 
			out = new PrintWriter(new FileWriter(outputFilename,true)); //open the file
			if (startOnNewLine) {
				out.println(); //start new line
			}
			out.append(joined); //append the string
			out.close();   //close file
		} catch (IOException e) { 
			e.printStackTrace();   
		}
	}
}