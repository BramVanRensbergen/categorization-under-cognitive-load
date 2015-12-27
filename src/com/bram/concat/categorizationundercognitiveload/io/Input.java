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

package com.bram.concat.categorizationundercognitiveload.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.Text;
import com.bram.concat.categorizationundercognitiveload.experiment.Stimulus;

/**
 * Class to assist with input of figures, text, and options, and with output of data.
 */
public abstract class Input {

	/**
	 * Image that will be displayed after each trial during the training phase, indicated whether the previous trial was correct.
	 */
	public static ImageIcon feedbackRight, feedbackWrong;
	
	/**
	 * Read the instructions and some other text from {@code Options.TEXT_FILE}.
	 * The file contains several segments, each preceded by {@code @DEFINE <segmentname>}. After this, a number of lines containing the text of that segment.
	 * The first segment, textInstructions, is special in that in can contain multiple pages (each of which is preceded by {@code @DEFINE textInstructions}
	 */
	public static void readText() {
		File optionsFile = new File(Options.TEXT_FILE);
		
		try {
			BufferedReader fReader = new BufferedReader(new InputStreamReader(new FileInputStream(optionsFile), "UTF-8"));
            String line;				// will contain  one line of the text file
            String segmentName = null; 	// name of the segment (e.g., main instructions) we are currently reading
            String segment = null;		// will contain all lines belonging to one 'segment', such as the main instructions
            
            while ((line = fReader.readLine()) != null) {
            	if (line.trim().startsWith("@DEFINE")) { //start of new segment            		
            		
            		if (segmentName != null) {  // this is not the first segment; being here means previous segment was finished, so submit it
            			Text.setText(segmentName, segment);
            		}
            		
            		segmentName = line.replace("@DEFINE", "").split("//")[0].trim(); //get the name
            		segment = "";
            		
            	} else { //continue existing segment
            		
            		if (!line.trim().startsWith("/") && !line.trim().startsWith("*")){ //ignore comment lines
            			segment = segment + line + "\n"; //newline gets replaced with <br> for segments that support html, in Text.setText().            			
            		}
            	}          	
            }
			fReader.close();

		} catch (NumberFormatException | IOException e) { 
			e.printStackTrace(); 
		}  	
	}
	
	/**
	 * Read options from {@code Options.OPTION_FILE}, which will overwrite the default options in {@code Options}.
	 */
	public static void readAndSetOptions() {
		File optionsFile = new File(Options.OPTION_FILE);
		
		try {
			BufferedReader fReader = new BufferedReader(new InputStreamReader(new FileInputStream(optionsFile), "UTF-8"));
            String line;			// will contain  one line of the text file
            String[] splitline;		// will contain the line, without comments, split by "="
            
            while ((line = fReader.readLine()) != null) {
            	line = line.trim();
            	
            	if (!line.startsWith("/") && !line.startsWith("*") && line.contains("=")) { //ignore comment lines and lines without a define
            		splitline = line.split("=");
                	Options.setOption(splitline[0].trim(), splitline[1].trim());
            	}            	
            }
			fReader.close();

		} catch (NumberFormatException | IOException e) { 
			e.printStackTrace(); 
		}  		
	}
	
	/**
	 * Read all images that will be used, from disk.
	 */
	public static void readImages() throws IOException {				
		List<File> allGroup1files = getImageFiles(Options.dirGroup1Stimuli);
		List<File> allGroup2files = getImageFiles(Options.dirGroup2Stimuli);
		
		Stimulus.group1stimuli = new Stimulus[allGroup1files.size()];
		Stimulus.group2stimuli = new Stimulus[allGroup2files.size()];
		
		//convert the files to images, store them
		for (int i = 0; i < allGroup1files.size(); i++) {			
			Stimulus.group1stimuli[i] = new Stimulus(createImage(allGroup1files.get(i)), allGroup1files.get(i).getName(), 1);
		}
		
		for (int i = 0; i < allGroup2files.size(); i++) {			
			Stimulus.group2stimuli[i] = new Stimulus(createImage(allGroup2files.get(i)), allGroup2files.get(i).getName(), 2);
		}		
		
		feedbackRight = createImage(new File(Options.fileFeedbackCorrect));
		feedbackWrong = createImage(new File(Options.fileFeedbackWrong));
	}
	
	private static ImageIcon createImage(File file) throws IOException {
		return new ImageIcon(ImageIO.read(file)); 
	}

	private static ArrayList<File> getImageFiles(String folder) throws FileNotFoundException {
		File figDir = new File(folder); //open stimFolder

		if (!figDir.isDirectory()) { //not a valid dir: display error
			throw new FileNotFoundException("Error on loading images! Folder "+folder +" does not exist!");
		}

		ArrayList<File> imageList = new ArrayList<File>(); //add all bmp, png, jpg and gif files to this list
		imageList.addAll(Arrays.asList(figDir.listFiles(new GenericExtFilter("bmp"))));
		imageList.addAll(Arrays.asList(figDir.listFiles(new GenericExtFilter("png"))));
		imageList.addAll(Arrays.asList(figDir.listFiles(new GenericExtFilter("jpg"))));
		imageList.addAll(Arrays.asList(figDir.listFiles(new GenericExtFilter("gif"))));

		return imageList;
	}

	/**
	 * Used to select files of the specified extension.
	 *
	 */
	private static class GenericExtFilter implements FilenameFilter {		 
		private String ext; //extension to look at

		public GenericExtFilter(String ext) {
			this.ext = ext;
		}

		public boolean accept(File dir, String name) {
			return (name.endsWith(ext)); //only accept files that end with the extensions
		}
	}

}