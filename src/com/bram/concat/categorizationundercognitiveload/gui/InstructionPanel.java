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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.Text;
import com.bram.concat.categorizationundercognitiveload.experiment.Experiment;

/**
 * Panel that displays the introduction to / explanation of the experiment.
 */
@SuppressWarnings("serial")
public class InstructionPanel extends JPanel {		
	
	/**
	 * Holds the text.
	 */
	JEditorPane textPane;
	
	/**
	 * Starts the experiment.
	 */
	JButton mainButton;
	
	/**
	 * Display the previous instructions.
	 */
	JButton previousButton;
	
	/**
	 * Display the next instructions.
	 */
	JButton nextButton;
	
	InstructionPanel() {
		setLayout(null); 	//absolute positioning
		setBackground(Options.backgroundColor);
		
		int width = (int) (Options.screenSize.width * 0.8); //80% of screenwidth
		if (width > 1000) {
			width = 1000; //max 1000px
		}
		
		int height = Options.screenSize.height - 200 - 35 * 2;
		
		textPane = new JEditorPane("text/html", ""); //create text pane, make sure we can use html formatting
		textPane.setEditable(false);
		textPane.setBackground(getBackground());     //set backgroundcolor to parent's background
		textPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
		textPane.setFont(Text.FONT_INSTRUCTIONS);
		
		JScrollPane paneScrollPane = new JScrollPane(textPane);
        paneScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); //hopefully we never need scrollbars, but just in case
        paneScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        paneScrollPane.setBorder(BorderFactory.createEmptyBorder()); //remove border
		paneScrollPane.setBounds((int) (Options.screenSize.getWidth() / 2 - width / 2), 100, width, height); //centered
        add(paneScrollPane); //add instructions
        
        mainButton = new JButton(Text.BTN_BEGIN); 
		previousButton = new JButton(Text.BTN_PREVIOUS);
		nextButton = new JButton(Text.BTN_NEXT);
		
		mainButton.setVisible(false);
		previousButton.setVisible(false);
		nextButton.setVisible(false);
		
		add(mainButton); 
		add(previousButton);
		add(nextButton);
		
		int buttonWidth = 120;
		mainButton.setBounds(		(int)(Options.screenSize.getWidth() / 2 - buttonWidth / 2), 		height + 125, buttonWidth, 35); 	
		previousButton.setBounds(	(int)(Options.screenSize.getWidth() / 2 - 120 / 2 - buttonWidth),	height + 125, buttonWidth, 35); 	 
		nextButton.setBounds(		(int)(Options.screenSize.getWidth() / 2 + 120 / 2), 				height + 125, buttonWidth, 35); 	
		
		textPane.setCaretPosition(0); //scroll to top
	}
	
	/**
	 * Show the indicated page of the instructions.
	 */
	public void showMainInstructions(final int pageIndex) {
		
		if (Text.textInstructions.size() == 0) { //no instructions where defined, just move to experiment
			Experiment.displayAndContinue(); 
			return;
		}
		
		removeActionListeners(nextButton);
		removeActionListeners(previousButton);
		
		if (pageIndex < Text.textInstructions.size()) { 
			//not last page
			nextButton.setText(Text.BTN_NEXT);
			nextButton.setVisible(true);
			nextButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, close the gui		
				showMainInstructions(pageIndex + 1);
			}});
		} 
		
		if (pageIndex > 1) { 
			//not first page
			previousButton.setText(Text.BTN_PREVIOUS);
			previousButton.setVisible(true);
			previousButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, close the gui		
				showMainInstructions(pageIndex - 1);
			}});
		} else {
			previousButton.setVisible(false);
		}
		
		if (pageIndex == Text.textInstructions.size()) { 
			//last page			
			nextButton.setText(Text.BTN_BEGIN);
			nextButton.setVisible(true);
			nextButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, close the gui		
				previousButton.setVisible(false);
				nextButton.setVisible(false);
				Experiment.displayAndContinue(); 
			}});
		}				
		setInstructions(Text.textInstructions.get(pageIndex - 1));		
	}
	
	public void showPostTrainingInstructions() {
		mainButton.setVisible(true);
		mainButton.setText(Text.BTN_CONTINUE);
		removeActionListeners(mainButton);
		mainButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, close the gui		
			Experiment.displayAndContinue(); 
		}});
		setInstructions(Text.textPosttrainingInstructions);	
	}
	
	public void showHalfwayInstructions() {
		mainButton.setText(Text.BTN_CONTINUE);
		removeActionListeners(mainButton);
		mainButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, close the gui		
			Experiment.displayAndContinue(); 
		}});
		setInstructions(Text.textHalfway);
	}
	
	public void showXpOverText() {
		mainButton.setText(Text.BTN_QUIT);
		removeActionListeners(mainButton);
		mainButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, close the gui		
			Experiment.gui.dispose(); 		//remove the layout
	        System.exit(0); //exit	
		}});
		setInstructions(Text.textXpOver);			
	}
	
	/**
	 * Display the indicated text.
	 */
	private void setInstructions(String text) {
		Experiment.gui.showCursor();
		Experiment.gui.bg.removeAll(); 
		textPane.setText(text);	
		Experiment.gui.bg.add(this); //add introduction screen
		Experiment.gui.bg.validate(); 			  
		Experiment.gui.bg.repaint();
	}

	private void removeActionListeners(JButton button) {
		for (ActionListener al : button.getActionListeners()) {
			button.removeActionListener(al);
	    }
	}
}