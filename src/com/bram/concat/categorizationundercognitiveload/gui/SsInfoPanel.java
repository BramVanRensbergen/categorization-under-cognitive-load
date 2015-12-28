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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.Text;
import com.bram.concat.categorizationundercognitiveload.experiment.Experiment;

/**
 * Panel that asks for the participant's basic info.	
 */
@SuppressWarnings("serial")
public class SsInfoPanel extends JPanel {
	/**
	 * Field for the user to enter his/her student number.
	 */
	JTextField snTextField;
	
	/**
	 * Field for the user to enter his/her age.
	 */
	private JTextField ageTextField;
	
	/**
	 * The gender of the user; default, nonsensical, value is overwritten when the user selects one of the radiobuttons. 
	 */
	private char gender = 'x';
	
	SsInfoPanel() {
		setLayout(null);		//we use an absolute layout
		setBackground(Options.backgroundColor);
		int labelWidth = 270;	//width of each label
		int formWidth = 205;	//width of each form entry
		int h = 35; 			//height of each field
		int space = 5; 			//space between fields
		
		JLabel snLabel = new JLabel(Text.FORM_ID, JLabel.TRAILING);	//create text-labels	
		JLabel ageLabel = new JLabel(Text.FORM_AGE, JLabel.TRAILING);
		JLabel sexLabel = new JLabel(Text.FORM_GENDER, JLabel.TRAILING);
		
		snTextField = new JTextField(10); //create fields for user to answer in
		ageTextField = new JTextField(10);
		JPanel genderRadio = getGenderPanel(); //get the gender radio-buttons
	
		JButton infoDone = new JButton(Text.BTN_READY); //add ok button, to go to next screen
		infoDone.addActionListener(new infoListener()); //make sure pressing the button will only advance to the next screen when all info has been entered
		
		snLabel.setFont(Text.FONT_INSTRUCTIONS);
		snTextField.setFont(Text.FONT_INSTRUCTIONS);
		ageLabel.setFont(Text.FONT_INSTRUCTIONS);
		ageTextField.setFont(Text.FONT_INSTRUCTIONS);
		sexLabel.setFont(Text.FONT_INSTRUCTIONS);
		
		add(snLabel); //add everything to the panel
		add(snTextField);
		add(ageLabel);
		add(ageTextField);
		add(sexLabel);
		add(genderRadio);
		add(infoDone);
		
		//center panels vertically and horizontally
		int wOffset = Options.screenSize.width / 2  - (labelWidth + formWidth - space) / 2; //centered panel's leftmost position
		int hOffset = Options.screenSize.height / 2 - (h * 3 - space * 2) / 2;				//centered panel's top position
		
		snLabel.setBounds		(wOffset, 					hOffset, 				labelWidth, h); //first row, first column
		snTextField.setBounds	(wOffset+labelWidth+space, 	hOffset, 				formWidth, h); //first row, second column
		ageLabel.setBounds		(wOffset,					hOffset+h+space, 		labelWidth, h); //second row, first column
		ageTextField.setBounds	(wOffset+labelWidth+space, 	hOffset+h+space,		formWidth, h); //second row, second column
		sexLabel.setBounds		(wOffset, 					hOffset+2*(h+space), 	labelWidth, h); //third row, first column
		genderRadio.setBounds	(wOffset+labelWidth+space, 	hOffset+2*(h+space), 	formWidth, h); //third row, second column
		
		infoDone.setBounds(wOffset + labelWidth + space, hOffset + 3 * (h + space) + 20, 120, 35); //fourth row, centered
		
		setVisible(true);
	}
	
	/**
	 * Creates and returns two radio-buttons to select the users gender.
	 */
	private JPanel getGenderPanel() {	
		JRadioButton maleButton = new JRadioButton(Text.FORM_MALE); //create the 'male' button
	    maleButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e)   { 
	    	//if button is clicked, set gender to male
	    	gender = 'm'; }   
	    }); 
	    maleButton.setBackground(Options.backgroundColor);
	    maleButton.setFont(Text.FONT_INSTRUCTIONS);

	    JRadioButton femaleButton = new JRadioButton(Text.FORM_FEMALE); //create the 'female' button
	    femaleButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent e)   { 
	    	//if button is clicked, set gender to female
	    	gender = 'f'; }   
	    }); 
	    femaleButton.setBackground(Options.backgroundColor);
	    femaleButton.setFont(Text.FONT_INSTRUCTIONS);
	    
	    ButtonGroup sexGroup = new ButtonGroup(); //group the two buttons (so clicking one de-selects the other)
	    sexGroup.add(maleButton);
	    sexGroup.add(femaleButton);
	    
	    JPanel genderPanel = new JPanel(); //add them to a panel
	    genderPanel.setLayout(new GridLayout(1, 2));
	    genderPanel.setBackground(Options.backgroundColor);
	    genderPanel.add(maleButton);
	    genderPanel.add(femaleButton);
	    return genderPanel;	//and return the panel
	}
	
	/**
	 * Listener to handle the participant's info: if all is in order, proceed; if not, display what is not, and remain here.
	 */
	private class infoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {	
			boolean valid = true; 	//used to keep track of whether all conditions are still valid
			String errorString = "";//used to keep track of all errors
			
			int ssNb = -1;
			try {
				ssNb = Integer.parseInt(snTextField.getText()); // read age from its text field, check if it is a valid number
			} catch(NumberFormatException error) {  			// wasn't a valid number: age remains at -1
				errorString += Text.FORM_ERROR_ID + "\n\n"; 	// add mistake to error string
				valid = false; 									// indicate we can't proceed				
			}	
						
			int age = -1;
			try {
				age = Integer.parseInt(ageTextField.getText()); // read age from its text field, check if it is a valid number
			} catch(NumberFormatException error) {  			// wasn't a valid number: age remains at -1
				errorString += Text.FORM_ERROR_AGE + "\n\n"; 	// add mistake to error string
				valid = false; 									// indicate we can't proceed				
			}		
								
			if (gender == 'x') { 								// gender hasn't been set yet (or this would be 'm' or 'f')
				errorString += Text.FORM_ERROR_GENDER + "\n\n"; // add mistake to error string
				valid = false; 									// indicate we can't proceed
			}
								
			if (valid) { //student number, age, and gender are all set correctly: proceed
				Experiment.createParticipantAndContinue(ssNb, age, gender);
			} else {
				JOptionPane.showMessageDialog(null,errorString,"",JOptionPane.ERROR_MESSAGE); //something was not entered correctly: display errors, and remain here
			}
		}	
	}
}