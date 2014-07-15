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

package com.bram.concat.categorizationundercognitiveload.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.Text;
import com.bram.concat.categorizationundercognitiveload.experiment.Experiment;
import com.bram.concat.categorizationundercognitiveload.pattern.Pattern;

/**
 * Panel used to display the experiment; contains the dot-pattern, a fixation-cross, the pattern-reproduction screen, or a blank screen.
 */
@SuppressWarnings("serial")
public class ExperimentPanel extends JPanel {				
	
	/**
	 * The cue to which the ss is asked to give associations.
	 */
	private JLabel cueLabel;
		
	/**
	 * Text that displays the participants first two responses to the cue.
	 */
	private JLabel[] previousAssociationLabels;   

	/**
	 * Displays an error if participant responds too slowly.
	 */
	private JLabel tooSlowLabel;
	
	/**
	 * Ss enters their responses i.e. associations in this.
	 */
	private JTextField responseField;	
	
	/**
	 * Ss can click this to move to the next cue, because they don't know the word, or because they don't have any more associations.
	 */
	private SkipButton skipButton;					  
	
	private JLabel fixationCross;
	
	/**
	 * A button the ss can use to indicate he/she finished reproducing the pattern.
	 */
	private JButton patternReproductionDoneButton; 
		
	/**
	 * Used to record the time of the first keypress of each association, and to listen to 'enter' to submit an answer.
	 */
    private KeyboardFocusManager keyboardManager;
    
    /**
	 * Used to record the time of the first keypress of each association, and to listen to 'enter' to submit an answer.
	 */
	private ReponseDispatcher responseDispatcher;
	
	/**
	 * For each association, we record when ss began typing (first key pressed). If this is set to true, timeAtAssociationsFirstKeypress is set at the first keypress.
	 */
	private boolean listenToFirstKeypress;
	
	/**
	 * For each association, we record when ss began typing (first key pressed).
	 */
	private long timeAtAssociationsFirstKeypress;
	
	ExperimentPanel() {				
		setLayout(null);	//we use absolute positioning
		setVisible(true);	
		
		cueLabel = new JLabel("");
		cueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cueLabel.setBounds(Options.screenSize.width / 2 - 400 / 2, 100, 400, 100); //center top
		cueLabel.setFont(Text.FONT_CUE);
			
		previousAssociationLabels = new JLabel[Options.N_ASSOCIATIONS - 1];
		for (int i = 0; i < Options.N_ASSOCIATIONS - 1; i++) {
			previousAssociationLabels[i] = new JLabel("");
			previousAssociationLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
			previousAssociationLabels[i].setBounds(Options.screenSize.width / 2 - 400 / 2, 150 + (1 + i) * 50, 400, 50); //mid top, below cue
			previousAssociationLabels[i].setForeground(Color.gray);
			previousAssociationLabels[i].setFont(Text.FONT_PREVIOUS_RESPONSES);
		}
		
		tooSlowLabel = new JLabel(Text.TEXT_TOO_SLOW);
		tooSlowLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tooSlowLabel.setBounds(Options.screenSize.width / 2 - 400 / 2, 300, 400, 100);
		tooSlowLabel.setFont(Text.FONT_TOO_SLOW_MESSAGE);
		tooSlowLabel.setForeground(Color.red);

		responseField = new JTextField(50);
		responseField.setBounds(Options.screenSize.width/2 - 200 / 2, Options.screenSize.height - 300, 200, 55); //center bottom
		responseField.setFont(Text.FONT_ANSWER_TEXTFIELD);
		
		skipButton = new SkipButton();
		skipButton.setBounds(Options.screenSize.width - 200 - 75, Options.screenSize.height - 50 - 75, 200, 50); //right bottom
		
		patternReproductionDoneButton = new JButton(Text.BTN_READY); //add ok button, to end pattern reproduction (not displayed yet)
		patternReproductionDoneButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, start the experiment		
			Experiment.xp.correctPatternReproduction();
		}});
		int heightOffset = Options.screenSize.height - (Options.screenSize.height - Options.GRID_PIXELSIZE * Pattern.NCELLS) / 4 - 50 / 2; //halfway between grid and bottom		
		patternReproductionDoneButton.setBounds(Options.screenSize.width / 2 - 100 / 2, heightOffset, 100, 50);
		
		fixationCross = new JLabel("+");
		fixationCross.setFont(new Font("Serif", Font.PLAIN, 50));
		int size = 30;
		fixationCross.setBounds(Options.screenSize.width / 2 - size / 2,  Options.screenSize.height / 2 - size / 2, size, size);
		
		keyboardManager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		responseDispatcher = new ReponseDispatcher();
	}		
	
	/**
	 * Show a small fixation cross in the center of an otherwise empty screen.
	 */
	public void showFixationCross() {
		removeAll();
		add(fixationCross);
		validate(); 
		repaint();				
	}
	
	/**
	 * Show an empty screen.
	 */
	public void showBlankScreen(){ 
		removeAll();
		validate(); 
		repaint();				
	}
	
	/**
	 * Display the indicated pattern, so the ss can memorize it.
	 * @param pattern The pattern to be displayed.
	 */
	public void showPattern(Pattern pattern) {
		removeAll();
		add(pattern.getGrid()); 
		int size = Options.GRID_PIXELSIZE * Pattern.NCELLS;
		pattern.getGrid().setBounds(Options.screenSize.width / 2 - size / 2, Options.screenSize.height / 2 - size / 2, size,  size);
		validate(); 
		repaint();			
	}		
	
	/**
	 * Display the indicated cue, so the participant can give associations to it.
	 * @param word	The cue to be displayed.
	 */
	public void showCue(String word) {
		removeAll();
		cueLabel.setText(word);
		add(cueLabel);
		skipButton.setState(Text.BTN_UNKNOWN_CUE);
		add(skipButton);
		responseField.setText(""); //remove previous answer
		add(responseField);
		for(int i = 0; i < previousAssociationLabels.length; i++) {
			previousAssociationLabels[i].setText("");
			add(previousAssociationLabels[i]);
		}
		
		validate();
		repaint();
		listenToFirstKeypress = true;
		enableKeyListener();
		responseField.grabFocus(); 		
	}
	
	/**
	 * Displayed if participant does not start typing within  {@code Options.MAX_RESPONSE_DURATION} MS.
	 * Count starts when the cue is displayed, or when they submitted a previous association to the cue.
	 */
	public void showTooSlowError() {
		removeAll();
		add(tooSlowLabel);		
		validate();
		repaint();
	}	

	/**
	 * Submit the association the participant gave; if it was a first or second association, display it while pp thinks about subsequent associations.
	 */
	private void submitResponse(String a) {
		responseField.setText("");
		
		if (Experiment.xp.currentAssociationIndex < Options.N_ASSOCIATIONS) {				 
			// this was first or second association	
			previousAssociationLabels[Experiment.xp.currentAssociationIndex - 1].setText(a); //-1 because that var is 1..3 and the array is 0..2		
			listenToFirstKeypress = true;
			skipButton.setState(Text.BTN_NO_FURTHER_RESPONSES);
		} else { 
			//this was third association
			disableKeyListener();
		}
		Experiment.xp.submitResponse(a, timeAtAssociationsFirstKeypress, System.currentTimeMillis());
	}
	
	/**
	 * Show the screen in which the ss can reproduce the previously memorized pattern.
	 */
	public void showPatternReproduction() {		
		removeAll();
		showPattern(Pattern.emptyPattern);
		add(patternReproductionDoneButton);
		validate(); 
		repaint();	
	}
	
	private class SkipButton extends JButton {

		public String text;
		
		private SkipButton() {
			super(Text.BTN_UNKNOWN_CUE);
			text = Text.BTN_UNKNOWN_CUE;
			
			this.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { 
				disableKeyListener();
				if (text == Text.BTN_UNKNOWN_CUE) {
					Experiment.xp.skipFurtherAssociations("__UNKNOWN__");
				} else if (text == Text.BTN_NO_FURTHER_RESPONSES) {
					Experiment.xp.skipFurtherAssociations("__NO_FURTHER_RESPONSES__");
				} else {
					Experiment.xp.skipFurtherAssociations("__ERROR_(this should no be here)__");
				}
			}});	
		}
		
		public void setState(String state) {
			this.text = state;
			this.setText(text);
		}
	}
	
	private class ReponseDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() == KeyEvent.KEY_RELEASED) {            	
            	if (e.getKeyCode() == KeyEvent.VK_ENTER) { 
            	//user pressed enter: try to submit response
            		if (responseField.getText().length() > 0) {
            			//response long enough: submit it
            			submitResponse(responseField.getText());
            		} //else: just do nothing!
            	} else {
            	//user pressed different key -> we might have to write away RT
            		if (listenToFirstKeypress) {
            			//not RT to first keypress saved already -> do so now
            			Experiment.xp.removeResponseTimer();
            			listenToFirstKeypress = false;
            			timeAtAssociationsFirstKeypress = System.currentTimeMillis();
            		}
            	}			
            } 
            return false; //do not perform default action
        }
	}

	public void enableKeyListener() {
		keyboardManager.addKeyEventDispatcher(responseDispatcher);
	}

	public void disableKeyListener() {
		keyboardManager.removeKeyEventDispatcher(responseDispatcher);
	}	
}