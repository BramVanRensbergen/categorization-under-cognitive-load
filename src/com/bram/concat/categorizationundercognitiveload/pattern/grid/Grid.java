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

package com.bram.concat.categorizationundercognitiveload.pattern.grid;

import java.awt.GridLayout;

import javax.swing.JPanel;

import com.bram.concat.categorizationundercognitiveload.pattern.Pattern;

/**
 * A 4x4 grid containing 16 cells, each of which may contain a dot, forming a pattern.
 */
@SuppressWarnings("serial")
public class Grid extends JPanel {		
	public static int NCELLS = Pattern.NCELLS;	
	public Cell[][] cells;
	
	/**
	 * Create an empty grid, then populate it with a pattern.
	 * @param squares Which cells contain a dot.
	 */
	public Grid(int[][] squares) { 
		super(new GridLayout(NCELLS,NCELLS));	 //empty grid	
		createCells(squares); //populate it
	}
	
	protected void createCells(int[][] squares) {
		cells = new Cell[NCELLS][NCELLS];
		
		for (int row = 0; row < NCELLS; row++) {
			for (int col = 0; col < NCELLS; col++) {
				cells[row][col] = new Cell(squares[row][col] == 1);
				add(cells[row][col]);
			}
		}	
	}
}