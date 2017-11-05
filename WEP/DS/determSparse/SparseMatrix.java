/**
 * Class Sparse Matrix
 * @author David Saeva
 * 
 * Contains a number of methods that allow for the creation of a Sparse Matrix containing numerical elements.
 * 	Also enables the calculation of the Matrice's determinant, provided that the Matrix itself is square.
 * 	Matrix elements are stored in a nested data structure (HashMap of HashMaps). Non-zero elements are not 
 * 	stored - in the event that a method is called on a non-existing key value (corresponding to a row/column
 * 	index of a zero-value element), a value is 0.0 is returned/used instead. See methods for more details.
 */

package determSparse;

import java.util.HashMap;
import java.util.Map;

public class SparseMatrix {
	
		private int rows;
		private int cols;
		private Map<Integer, HashMap<Integer, Double>> matrix;
		
		/**
		 * Constructor, generates new Matrix object with the designated row/col numbers. These values
		 * 	are stored locally for access/use with the getMinor() and getDet() methods, but are otherwise
		 * 	unused and not required for matrix operations or for storing data within the matrix object.
		 * @param r	int corresponding to the number of rows in the matrix
		 * @param c	int corresponding to the number of columns in the matrix
		 */
		public SparseMatrix(int r, int c) {
			this.rows = r;
			this.cols = c;
			matrix = new HashMap<Integer, HashMap<Integer, Double>>();
		}
		
		/**
		 * Adds a new element at {row, column} in the Matrix with a designated value. Zero-valued elements
		 * 	cannot be added to the matrix
		 * @param row int corresponding to the row index of the element in the matrix
		 * @param col int corresponding to the column index of the element in the matrix
		 * @param val double value of the element in the matrix
		 */
		public void addEle(int row, int col, double val) {
			if (val != 0) {											// prevents the addition of zero-valued elements to the matrix
				if (matrix.containsKey(row)) {					// if the row already has a HashMap designated in the matrix
					matrix.get(row).put(col, val);					// add the new value to that HashMap with a K-V pair equal to Column-Value
				} else {
					matrix.put(row, new HashMap<Integer,Double>());	// otherwise create a new HashMap at with a key of Row
					matrix.get(row).put(col, val);					// add the K-V pair of Column-Value to the new HashMap for the row
				
				}
				//System.out.println("Element (value = " + val + ") added at {" + row + ", " + col +"}");
			}
		}
		
		/**
		 * Simple recursive method generating the determinant of the Matrix object. Calls itself
		 * 	on the minor of the called-on Matrix, using a row/column combination to skip as specified 
		 * 	through iteration in addition to the order of that matrix
		 * @return
		 */
		public double getDet() {
			double det = 0.0;																	// determinant
			
			if (getRows() == 1) det = getEle(0,0); 												// stop case for trivial 1-element Matrix			
			if (getRows() == 2) det = getEle(0,0)*getEle(1,1) - getEle(0,1)*getEle(1,0);		// stop case for trivial 2x2 Matrices
			
			if (getRows() >= 3) {																// algorithm for 3x3 and larger Matrices
				for(int a=0; a<getRows(); a++) {		
					det += Math.pow(-1, a+1)*getEle(a, 1)*getMinor(getRows(), a, 1).getDet();	// calculate the determinant via the laplace expansion
				}
			}
			return det;
		}
		
		/**
		 * Generates a minor of the Matrix this method is called on that through ignoring the elements 
		 * 	located in the passed in row and column. Returns null if the order of the parent matrice is less than 3,
		 * 	since that results in a trivial or non-existent minor. 
		 * @param o	order of the parent Matrix generating the minor
		 * @param a	parent Matrix row to skip
		 * @param b parent Matrix column to skip
		 * @return Matrix object representing the minor
		 */
		public SparseMatrix getMinor(int o, int a, int b) {
			SparseMatrix minor = new SparseMatrix(o-1, o-1);				// new minor Matrix, order is 1 less than parent Matrix
			int x = 0;											// counter for row index in the double array
			
			if (o < 3) {										// return null for matrices with an order less than 3, the minor of an order 2 matrix is one element
				return null;
				
			} else {
				for (int i = 0; i<o; i++) {						// iterate over rows
					int y = 0;									// counter for column index in the double array
					if (i == a) continue;						// if this row was passed in to be skipped, just increment i - otherwise iterate over columns then increment x and i
				
					for (int j = 0; j<o; j++) {					// iterate over columns
						if (j == b) continue;					// if this column was passed in to be skipped, just increment j
						minor.addEle(x, y, getEle(i, j));		// otherwise, set element at x,y in minor equal to element at i,j in parent and increment y		
						y++;
					}
					x++;
				}							
			}
			//minor.printMatrix();
			return minor;
		}
		
		/**
		 * Gets the element stored at {row, col} in the matrix. If the HashMap does not contain a key for that
		 * 	row or that column value, and that row/col value is less than or equal to the order of the matrix,
		 *  then 0.0 is returned.
		 * @param row int corresponding to the row index of the element in the matrix
		 * @param col int corresponding to the column index of the element in the matrix
		 * @return double value of the element in the matrix
		 */
		public Double getEle(int row, int col) {
			if (matrix.containsKey(row)) {
				if (matrix.get(row).containsKey(col)) {
					return matrix.get(row).get(col);
				}
				if (col <= getCols()) return 0.0;			
			} 	
			if (row<= getRows()) return 0.0;
			return null;
		}
		
		/**
		 * Get the number of rows in the matrix
		 * @return int number of rows
		 */
		public int getRows() {
			return rows;
		}
		
		/**
		 * Get the number of columns in the matrix
		 * @return int number of columns
		 */
		public int getCols() {
			return cols;
		}
		
		/**
		 * Prints the matrix, element by element with spaces in between, 1 row per line. 
		 * 	Included for testing purposes. 
		 */
		public void printMatrix() {
			System.out.println("The matrix is:\n");
			for (int aa=0; aa<getRows(); aa++) {
				for (int bb=0; bb<getCols(); bb++) {
						System.out.print(getEle(aa,bb) +" ");
					
				}
				System.out.println();
			}
		}
		
}


