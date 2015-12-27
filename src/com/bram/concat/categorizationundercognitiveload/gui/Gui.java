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

package com.bram.concat.categorizationundercognitiveload.gui;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.Text;

/**
 * Class to assist with the layout of the experiment.
 */
@SuppressWarnings("serial")
public class Gui extends JFrame {
	
	/**
	 * Contains whatever is currently showing, e.g. the introduction, the actual experiment, ...
	 */
	protected Container bg;
				
	/**
	 * Contains either a stimulus, dot-pattern, fixation-cross, pattern-reproduction screen, feedback, or a blank screen.
	 */
	public ExperimentPanel xpPane;

	/**
	 * Contains a text message; either the main instructions, the post-training instructions, or the goodbye text.
	 */
	public InstructionPanel instructionPanel;
	
	/**
	 * Used to hide the cursor (we can't actually hide it, so we replace it with an empty image).
	 */
	private Cursor blankCursor;
	
	/**
	 * Initialize the layout
	 */
	public Gui() {
		setTitle(Text.textWindowTitle);					//set the application's title
		setUndecorated(!Options.DECORATED);				//removes title bar and close buttons if necessary
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setSize(Options.screenSize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //on exit, stop all processes
		setVisible(true);								//display the frame
		setBackground(Options.backgroundColor);
		blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "blank cursor");
		bg = getContentPane();							//bg contains whatever we are currently displaying
		xpPane = new ExperimentPanel(); 				//create the panel that holds the experiment
		instructionPanel = new InstructionPanel();		//create the panel that displays instructions/goodbye text
	}
	
	/**
	 * Restore the default cursor.
	 */
	public void showCursor() {
		setCursor(Cursor.getDefaultCursor());
	}
	
	/**
	 * Replace the cursor with an empty image, effectively hiding it.
	 */
	public void hideCursor() {
		setCursor(blankCursor);
	}
	
	/**
	 * Display the screen that asks for the participants basic info.
	 */
	public void showSsInfo() {
		SsInfoPanel info = new SsInfoPanel(); //create the screen
		bg.add(info);						  //add it to the background
		bg.validate();				
		bg.repaint();
		info.snTextField.grabFocus(); 		  //set focus to usernumber field, to allow the user to type without clicking something first
	}				
		
	/**
	 * Display the actual experiment.
	 */
	public void showXP() {
		bg.removeAll(); //remove the instructions panel
		bg.add(xpPane); //add the experiment panel
		bg.validate();  //repaint background
		bg.repaint();		
	}		
}