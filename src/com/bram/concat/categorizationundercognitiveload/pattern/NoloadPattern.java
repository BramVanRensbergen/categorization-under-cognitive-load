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

package com.bram.concat.categorizationundercognitiveload.pattern;

import com.bram.concat.categorizationundercognitiveload.pattern.grid.Grid;
import com.bram.concat.categorizationundercognitiveload.pattern.grid.ReproductionGrid;

/**
 * An empty grid, used for pattern reproduction.
 */
public class NoloadPattern extends Pattern{	
	public NoloadPattern() {
		super(NOLOAD);	
	}
	
	@Override
	protected Grid createGrid() {
		return new ReproductionGrid(new int[NCELLS][NCELLS]); //reproduction grid: cells are clickable
	}
	
	/**
	 * Removes all dots, ran before presenting the grid. (The dots were added at reproduction phase of the previous trial.)
	 */
	public void clearDots() {
		for (int row = 0; row < NCELLS; row++) {
			for (int col = 0; col < NCELLS; col++) {
				grid.cells[row][col].removeDot();
			}
		}	
	}
}