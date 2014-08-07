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

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.bram.concat.categorizationundercognitiveload.Options;
import com.bram.concat.categorizationundercognitiveload.Text;
import com.bram.concat.categorizationundercognitiveload.experiment.Experiment;
import com.bram.concat.categorizationundercognitiveload.experiment.Stimulus;
import com.bram.concat.categorizationundercognitiveload.io.Input;
import com.bram.concat.categorizationundercognitiveload.pattern.Pattern;

/**
 * Panel used to display the experiment; contains a stimulus/image, feedback, a dot-pattern, a fixation-cross, a pattern-reproduction screen, or a blank screen.
 */
@SuppressWarnings("serial")
public class ExperimentPanel extends JPanel {				

	private JLabel fixationCross;
	
	/**
	 * Will hold the figure the participant will judge durign a trial.
	 */
	private JLabel stimulusContainer;
	
	/**
	 * Participant uses this to indicate which category he/she believes the stimulus corresponds to.
	 */
	private JButton group1button, group2button;
	
	/**
	 * Will hold feedback on either the trial (right/wrong) or the entire block (mean % correct for all trials).
	 */
	private JLabel feedbackContainer;
	
	/**
	 * Used to move on the next block, when participant is presented with % correct for the last block.
	 */
	private JButton feedbackDoneButton;	
	
	/**
	 * A button the ss can use to indicate he/she finished reproducing the pattern.
	 */
	private JButton patternReproductionDoneButton; 
	
	ExperimentPanel() {				
		setLayout(null);	//we use absolute positioning
		setVisible(true);	
		setBackground(Options.backgroundColor);
		int w = Options.screenSize.width;
		int h = Options.screenSize.height;
		
		stimulusContainer = new JLabel("");
		stimulusContainer.setHorizontalAlignment(SwingConstants.CENTER);
		int stimSize = Stimulus.maxSize;
		stimulusContainer.setBounds(w / 2 - stimSize / 2, h / 2 - stimSize / 2, stimSize, stimSize); //center

		feedbackContainer = new JLabel("");
		feedbackContainer.setFont(Text.FONT_INSTRUCTIONS);
		feedbackContainer.setHorizontalAlignment(SwingConstants.CENTER);
		int feedbackWidth = Math.max(Input.feedbackRight.getIconWidth(), Input.feedbackWrong.getIconWidth());
		feedbackWidth = Math.min(w, 800);
		int feedbackHeight = Math.max(Input.feedbackRight.getIconHeight(), Input.feedbackWrong.getIconHeight());
		feedbackHeight = Math.min(h, 600);
		feedbackContainer.setBounds(w / 2 - feedbackWidth / 2, h / 2 - feedbackHeight / 2, feedbackWidth, feedbackHeight); //centered
		
		feedbackDoneButton = new JButton(Text.BTN_CONTINUE);
		feedbackDoneButton.setBounds(w / 2 - 100 / 2, h / 2 + feedbackHeight / 2 + 50, 200, 50);
		feedbackDoneButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, start the experiment		
			Experiment.currentPhase.startNextBlock();
		}});
		
		patternReproductionDoneButton = new JButton(Text.BTN_READY); //add ok button, to end pattern reproduction (not displayed yet)
		patternReproductionDoneButton.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { //on click, start the experiment		
			Experiment.currentPhase.correctPatternReproduction();
		}});
		int heightOffset = h - (h - Options.GRID_PIXELSIZE * Pattern.NCELLS) / 4 - 50 / 2; //halfway between grid and bottom		
		patternReproductionDoneButton.setBounds(w / 2 - 100 / 2, heightOffset, 100, 50);
		
		fixationCross = new JLabel("+");
		fixationCross.setFont(new Font("Serif", Font.PLAIN, 50));
		int fixationCrossSize = 30;
		fixationCross.setBounds(w / 2 - fixationCrossSize / 2, h / 2 - fixationCrossSize / 2, fixationCrossSize, fixationCrossSize);
	}		
	
	public void createCategorizationButtons() {
		
		//each button always corresponds to one group
		group1button = new JButton(Options.group1name);
		group2button = new JButton(Options.group2name); 
		
		//which of these buttons is on the left, and which on the right, depends on Options.reversedButtonPosition	
		int leftButtonX = Options.screenSize.width / 2 - Options.responseButtonWidth - 50;
		int rightButtonX = Options.screenSize.width / 2 + 50;
		
		int group1buttonX = ( Options.reversedButtonPosition ? rightButtonX : leftButtonX ); 
		int group2buttonX = ( Options.reversedButtonPosition ? leftButtonX  : rightButtonX );
				
		group1button.setBounds(group1buttonX, Options.screenSize.height / 2 + Stimulus.maxSize / 2 + 100, Options.responseButtonWidth, 50);
		group2button.setBounds(group2buttonX, Options.screenSize.height / 2 + Stimulus.maxSize / 2 + 100, Options.responseButtonWidth, 50);
		
		group1button.addActionListener(new ResponseListener(1));
		group2button.addActionListener(new ResponseListener(2));
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
	 * Display the indicated stimulus, which the participant will categorize.
	 */
	public void showStimulus(Stimulus stimulus) {
		removeAll();
		stimulusContainer.setIcon(stimulus.image);		
		add(stimulusContainer);
		add(group1button);
		add(group2button);
		validate();
		repaint();
	}
	
	/**
	 * Show whether the lastly categorized stimulus was categorized correctly.
	 */
	public void showTrialFeedback(boolean correct) {
		feedbackContainer.setText("");
		if (correct) {
			feedbackContainer.setIcon(Input.feedbackRight);
		} else {
			feedbackContainer.setIcon(Input.feedbackWrong);
		}
		removeAll();
		add(feedbackContainer);
		validate();
		repaint();
	}
	
	/**
	 * Display the mean accuracy on the just-completed block.
	 */
	public void showBlockAccuracy(int accuracy) {
		feedbackContainer.setIcon(null);
		String feedback = Text.textBlockAccuracy.replace("@ACC@", accuracy + "");
		feedbackContainer.setText(feedback);
		removeAll();
		add(feedbackContainer);
		add(feedbackDoneButton);
		validate();
		repaint();
	}

	/**
	 * Show the screen in which the ss can reproduce the previously memorized pattern.
	 * Only used during training phase.
	 */
	public void showPatternReproduction() {		
		removeAll();
		showPattern(Pattern.reproductionPattern);
		add(patternReproductionDoneButton);
		validate(); 
		repaint();	
	}
	
	private class ResponseListener implements ActionListener {
		private int groupNb;		
		
		private ResponseListener(int groupNb) {
			this.groupNb = groupNb;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			removeAll();
			String response = (groupNb == 1 ? Options.group1name : Options.group2name);
			Experiment.currentPhase.submitResponse(response, System.currentTimeMillis());			
		}
		
	}
}