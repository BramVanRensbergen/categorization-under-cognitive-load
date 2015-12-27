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

import java.util.Random;

import com.bram.concat.categorizationundercognitiveload.pattern.grid.Grid;

/**
 * A pattern consists of a number of dots in a 4x4 grid.
 */
public abstract class Pattern {
	
	/**
	 * No dot pattern.
	 */
	public static final int NOLOAD = 0;
	
	/**
	 * A simple dot pattern: four dots in a straight line.
	 */
	public static final int LOWLOAD = 1; 
	
	/**
	 * A more complex dot pattern: four dots in a semi-random pattern. See HighloadPattern for more info.
	 */
	public static final int HIGHLOAD = 2;
	
	/**
	 * Number of rows and columns of the grid. Don't change this :)
	 */
	public static final int NCELLS = 4;
	
	/**
	 * Used to generate random numbers for creating the pattern.
	 */
	protected static final Random RAND = new Random();	
	
	/**
	 * A pattern that is special in that is empty; used for the pattern-reproduction screen.
	 */
	public static NoloadPattern reproductionPattern;
		
	/**
	 * "HIGH_LOAD", "LOW_LOAD", or "NO_LOAD"; used when writing away the data, to denote the difficulty of the pattern.
	 */
	public String loadString;
	
	/**
	 * 4x4 grid containing 16 Cell objects
	 */
	protected Grid grid;
	
	/**
	 * Array of 16 elements, with for each position in the 4x4 grid 0 if it contains no dot, 1 if it does.
	 */
	public int[] dotArray;
	
	protected Pattern(int load) {
		if (load == NOLOAD) {
			loadString = "NO_LOAD";
		} else if (load == LOWLOAD) {
			loadString = "LOW_LOAD";
		} else if (load == HIGHLOAD) {
			loadString = "HIGH_LOAD";
		}
		
		grid = createGrid();		
		dotArray = computeDotArray();
	}
	
	/**
	 * Defined in {@code HighloadPattern}, {@code LowloadPattern}, or {@code NoloadPattern}
	 * @return
	 */
	protected abstract Grid createGrid();
		
	/**
	 * Returns an array of 16 elements, with for each position in the 4x4 grid 0 if it contains no dot, 1 if it does.
	 */
	public int[] computeDotArray() {
		int[] dotArray = new int[NCELLS * NCELLS];
		
		int index = 0;
		for (int row = 0; row < NCELLS; row++) {
			for (int col = 0; col < NCELLS; col++) {
				if (grid.cells[row][col].containsDot) {
					dotArray[index++] = 1;
				} else {
					dotArray[index++] = 0;
				}
			}
		}
		return dotArray;
	}
	
	public Grid getGrid() {
		return grid;
	}		
}