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

package com.bram.concat.categorizationundercognitiveload.pattern.grid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A cell of the 4x4 grid that can be clicked by the user to add/remove dots.
 * This allows the user to recreate a pattern they previously memorized.
 */
@SuppressWarnings("serial")
public class ReproductionCell extends Cell implements ActionListener {
	protected ReproductionCell() {
		super(false);
		addActionListener(this);
		setEnabled(true);
	}	
	
	/**
	 * Cell was clicked: if it contained a dot, remove it; else, add a dot.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (containsDot) {
			removeDot();
		} else {
			addDot();	
		}
	}
}