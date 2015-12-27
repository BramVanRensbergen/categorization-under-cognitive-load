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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.bram.concat.categorizationundercognitiveload.pattern.grid.Grid;
import com.bram.concat.categorizationundercognitiveload.pattern.validity.CRC;
import com.bram.concat.categorizationundercognitiveload.pattern.validity.MirrorAndRotation;
import com.bram.concat.categorizationundercognitiveload.pattern.validity.NoAdjacencies;

/**
 * A random pattern of four dots in a 4x4 matrix, fulfilling three conditions:
 * 1) no two vertically or horizontally adjacent dots, and no three or four dots in either diagonal (see {@code validity.NoAdjacencies})
 * 2) CRC is below the cutoff value (see {@code validity.CRC})
 * 3) Mirroring or rotating the pattern may not result in a duplicate (see {@code validity.MirrorAndRotation})
 */
public class HighloadPattern extends Pattern {	
	public HighloadPattern() {
		super(HIGHLOAD);		
	}
	
	@Override
	protected Grid createGrid() {	
		return new Grid(createPattern());
	}
	
	private int[][] createPattern() {
		List<Integer> positionNumbers = Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15); //indices of the 16 places where a dot COULD be placed
		Collections.shuffle(positionNumbers); //randomize their order		
		List<Integer> dotPositions = positionNumbers.subList(0, 4); //get the first four i.e. four random numbers between 0 and 15 (without replacement)
		
		int[][] squares = new int[NCELLS][NCELLS]; 
		
		for (int nb:dotPositions) { //go through the four indices of the positions where we will place dots
			//get the row/col in a 4x4 matrix corresponding to those indices
			int row = (int) Math.floor(nb / NCELLS); 
			int col = nb % NCELLS;
			squares[row][col] = 1;
		}		
		
		//if either of the three criteria fails to uphold for the created pattern, create a new pattern.
		if (!CRC.validByCrc(squares)) {
			return createPattern(); //CRC not below the threshold
		}
		
		if (!NoAdjacencies.validByAdjacencies(squares)) {
			return createPattern();  //2 adjacent dots, or 3 or 4 dots in either diagonal
		}
		
		if (!MirrorAndRotation.validByMirroring(squares)) {
			return createPattern(); //mirroring or rotating the pattern creates at least one duplicate
		}
		
		//all criteria upheld -> pattern is valid!
		return squares;
	}		
}