package lab2;

public class Matrix {
	private int[][] matrix;
	
	public Matrix(int size) {
		matrix = setMatrixSize(size);
	}
	
	public void populateRow(int[] row, int rowIndex) {
		matrix[rowIndex] = row;
	}
	
	private int[][] setMatrixSize(int i) {
		return new int[i][i];
	}
	
	public int getNumEle() {
		int sum =0;
		for (int i=0; i<matrix.length; i++) {
			sum+=matrix[i].length;
		}
		
		return sum;
	}
	
	public int getSideLen() {
		return matrix.length;
	}
	
	public int[][] getMatrix() {
		return matrix;
	}
}

