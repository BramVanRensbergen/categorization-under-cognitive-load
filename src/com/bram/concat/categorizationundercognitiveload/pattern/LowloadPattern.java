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

/**
 * A pattern of four dots in a 4x4 grid, with all dots on the same line or column (which line/column is randomized). 
 * 
 * Randomization was verified: 1000000 runs returns r1/r2/r3/r4/c1/c2/c3/c4: 124523/125240/125266/125180/125350/124737/125087/124617 
 */
public class LowloadPattern extends Pattern {	
	
	public LowloadPattern() {
		super(LOWLOAD);		
	}
	
	@Override
	protected Grid createGrid() {
		return new Grid(createPattern());
	}
	
	/**
	 * @return A 4x4 matrix with one row or column of 1's.
	 */
	private int[][] createPattern() {
		int[][] squares = new int[NCELLS][NCELLS];
		int n = RAND.nextInt(NCELLS);		
		
		if (RAND.nextBoolean()){ //make a column of ones
			for (int i = 0; i < squares.length; i++) {
				squares[i][n] = 1;
			}
		} else {  //make a row of ones
			for (int i = 0; i < squares.length; i++) {
				squares[n][i] = 1;
			}
		}
		return squares;
	}
}