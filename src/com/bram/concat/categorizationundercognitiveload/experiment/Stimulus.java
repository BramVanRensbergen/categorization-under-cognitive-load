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

import javax.swing.ImageIcon;

/**
 * A stimulus comprises an image, which will be used in the experiment's trials.
 * The stimuli are read from disk, in {@code IO.createStimuli()}.
 */
public class Stimulus {
	
	/**
	 * All stimuli (figures) in group 1.
	 */
	public static Stimulus[] group1stimuli;
	
	/**
	 * All stimuli (figures) in group 2.
	 */
	public static Stimulus[] group2stimuli;
	
	/**
	 * The largest width or height of any image. Used to determine the size of the layout element that displays the images.
	 */
	public static int maxSize;
	
	/**
	 * Filename of the image that is the source of this figure.
	 */
	public String filename;
	
	/**
	 * The group to which this stimulus corresponds, i.e. 1 or 2.
	 */
	public int group;
	
	/**
	 * The actual stimulus / image.
	 */
	public ImageIcon image;
	
	public Stimulus(ImageIcon image, String filename, int group) {
		this.image = image;
		this.filename = filename;
		this.group = group;
		
		//we want to keep track of the largest image; this is used to create the layout.
		if (image.getIconWidth() > maxSize) {
			maxSize = image.getIconWidth();
		}
		
		if (image.getIconHeight() > maxSize) {
			maxSize = image.getIconHeight();
		}	
		
	}
	
}