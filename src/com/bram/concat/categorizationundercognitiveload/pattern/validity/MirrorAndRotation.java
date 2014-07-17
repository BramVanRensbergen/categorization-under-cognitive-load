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

package com.bram.concat.categorizationundercognitiveload.pattern.validity;

import java.util.Arrays;

/**
 * Check whether an indicated pattern of dots would be accepted by the algorithm described in:
 * Garner, W.R., & Clement, D. E. (1963). Goodness of Pattern and Pattern Uncertainty. Journal of Verbal Learning and Verbal Behavior 2, 446-452.
 * In short: mirroring or rotating the pattern may not result in a duplicate.
 */
public abstract class MirrorAndRotation {
	
	/**
	 * Check whether the indicated pattern is valid according to the mirror method.
	 * @param squares The pattern to investigate.
	 * @return True if the pattern is valid according to this approach.
	 */
	public static boolean validByMirroring(int[][] squares) {				
		int[][] original 			= squares;
		int[][] originalRight90  	= rotateRight(original);
		int[][] originalRight180 	= rotateRight(originalRight90); // = horizontally mirrored
		int[][] originalRight270 	= rotateRight(originalRight180);// = left 90
		
		int[][] mirrored  		 	= mirrorVertically(original);
		int[][] mirroredRight90  	= rotateRight(mirrored);
		int[][] mirroredRight180 	= rotateRight(mirroredRight90); // = horizontally mirrored
		int[][] mirroredRight270 	= rotateRight(mirroredRight180);// = left 90
		
		int[][][] patterns = { original, originalRight90, originalRight180, originalRight270, 
					           mirrored, mirroredRight90, mirroredRight180, mirroredRight270 };
		
		for (int i = 0; i < patterns.length; i++) {
			for (int j = i; j < patterns.length; j++) {
				if (j > i && Arrays.deepEquals(patterns[i], patterns[j])) {
					return false;
				}
			}
		}
		return true;
	}
	
	private static int[][] rotateRight(int[][] squares) {
		int[][] rotate90right = new int[4][4];
		for (int row = 0; row < 4; row++) {
		    for (int col = 0; col < 4; col++) {
		         rotate90right[row][col] = squares[4 - col - 1][row];
		    }
		}
		return rotate90right;
	}
	
	private static int[][] mirrorVertically(int[][] squares) {
		int[][] mirrorVertically = new int[4][4];
		for (int row = 0; row < 4; row++) {
		    for (int col = 0; col < 4; col++) {
		    	mirrorVertically[row][3-col] = squares[row][col];
		    }
		}
		return mirrorVertically;
	}	
}
