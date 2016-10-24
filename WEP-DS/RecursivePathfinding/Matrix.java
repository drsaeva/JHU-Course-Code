package lab2;

public class Matrix {
	int[][] matrix;  
	
	public static void main(String[] args) {
		Matrix m = new Matrix(3);
		System.out.println(m.getNumEle());
	}
	
	public Matrix(int size) {
		matrix = new int[size][size];
	}
	
	public void populateRow(int[] row, int rowIndex) {
		matrix[rowIndex] = row;
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
	
	public int readRow(int row) {
		
		for (int i=0; i<matrix.length; i++) {
			return matrix[row][i];
		}
		
		return -1;
		
	}
	
	public int readCol(int col) {
		
		for (int i=0; i<matrix.length; i++) {
			return matrix[i][col];
		}
		
		return -1;
		
	}
	
}
