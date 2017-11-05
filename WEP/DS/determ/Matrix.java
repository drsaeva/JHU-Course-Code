/**
 * Class Matrix
 * @author David Saeva
 * 
 * Contains a number of methods that allow for the creation of a Matrix containing numerical elements.
 * 	Also enables the calculation of the Matrice's determinant, provided that the Matrix itself is square.
 */
package determ;

public class Matrix {
	
	private int rows;
	private int cols;
	private double mat[][];
	
	/**
	 * Constructor. Generates a new Matrix object that encapsulates an empty double array
	 * 	with r rows and c columns
	 * @param r number of rows in the Matrix
	 * @param c number of columns in the Matrix
	 */
	public Matrix(int r, int c, double[][] dat) {
		this.rows = r;
		this.cols = c;
		mat = new double[r][c];
		if (this.rows == dat.length) {
			for (int i=0; i<rows; i++) {
				for (int j=0; j<rows; j++) {
					mat[i][j] = dat[i][j];
				}
			}
		} else {
			System.out.println("Input matrix does not match number of rows/cols assigned.");
		}
	
	}
	
	/**
	 * Simple recursive method generating the determinant of the Matrix object. Calls itself
	 * 	on the minor of the called-on Matrix, using a row/column combination to skip as specified 
	 * 	through iteration in addition to the order of that matrix
	 * @return
	 */
	public double getDet() {
		double det = 0.0;															// determinant
		int o = this.getRows();														// Matrix order
		double[][] mat = this.getArray();											// double array encapsulated by Matrix
		
		if (o == 1) det = mat[0][0]; 												// stop case for trivial 1-element Matrix
		else if (o == 2) det = mat[0][0]*mat[1][1] - mat[1][0]*mat[0][1]; 			// stop case for trivial 2x2 Matrices

		else {																		// algorithm for 3x3 and larger Matrices
			for(int a=0; a<o; a++) {		
				for (int b=0; b<o; b++) {
					det += Math.pow(-1, a+b)*mat[a][b]*getMinor(o, a, b).getDet();	// Laplace's formula for a Matrix determinant
				}
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
	public Matrix getMinor(int o, int a, int b) {
		Matrix minor;									// new minor Matrix, order is 1 less than parent Matrix
		double[][] dat = new double[o-1][o-1];			// double array that will populate the minor Matrix object
		int x=0;										// counter for row index in the double array
		
		if (o < 3) {									// return null for matrices with an order less than 3, the minor of an order 2 matrix is one element
			return null;
			
		} else {
			
			for (int i=0; i<o; i++) {					// iterate over rows
				int y=0;								// counter for column index in the double array
				if (i==a) continue;						// if this row was passed in to be skipped, just increment i - otherwise iterate over columns then increment x and i
			
				for (int j=0; j<o; j++) {				// iterate over columns
					if (j==b) continue;					// if this column was passed in to be skipped, just increment j
					dat[x][y] = getArray()[i][j];		// otherwise, set element at x,y in minor equal to element at i,j in parent and increment y
					y++;
				}
				x++;
			}							
		
			minor = new Matrix(o-1, o-1, dat);						// instantiate the new minor Matrix with the data from the array and return it
		}
		/*System.out.println("\nThe minor matrix is:\n");	// bug tracker, prints out the minor matrix
		for (int c=0; c<minor.getRows(); c++) {
			for (int d=0; d<minor.getCols(); d++) {
				System.out.print(minor.getArray()[c][d] +" ");
			}
			System.out.println();
		}*/
		
		return minor;
	}
	
	/**
	 * Getter for the double 2d array that the Matrix object encapsulates. Individual elements can be
	 * 	accessed via row/column locations as per normal 2d arrays
	 * @return double[][] 2-dimensional array
	 */
	public double[][] getArray() {
		return mat;
	}
	
	/**
	 * Getter for the number of columns in the Matrix
	 * @return int number of columns
	 */
	public int getCols() {
		return cols;
	}
	
	/**
	 * Getter for the number of rows in the Matrix
	 * @return int number of rows
	 */
	public int getRows() {
		return rows;
	}
}
