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

/**
 * A 4x4 grid in which the user can create a pattern (reproducing the one memorized earlier).
 */
@SuppressWarnings("serial")
public class ReproductionGrid extends Grid {

	public ReproductionGrid(int[][] squares) {
		super(squares); //this will call createCells		
	}
	
	@Override
	protected void createCells(int[][] squares) {
		cells = new ReproductionCell[NCELLS][NCELLS];
		
		for (int row = 0; row < NCELLS; row++) {
			for (int col = 0; col < NCELLS; col++) {
				cells[row][col] = new ReproductionCell();
				add(cells[row][col]);
			}
		}	
	}
}
