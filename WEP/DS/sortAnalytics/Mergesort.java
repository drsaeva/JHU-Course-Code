package sortAnalytics;

public class Mergesort {
	
	/**
	 * Executes merge sort, generating temp arrays for sorting from the passed-in array argument. Takes systemtime in 
	 * nanoseconds prior to start of sort and immediately after completion, returning the difference
	 * @param data array to be sorted
	 * @return	time in nanoseconds required to sort passed-in array
	 */
	public static long sort(int[] data) {
		int[] temp1 = data;
		int[] temp2 = data;
	
	    long startTime = System.nanoTime();
	    splitMerge(temp2, 0, temp1.length, temp1);   		// sort temp1 from temp2 into temp1
	    return System.nanoTime()-startTime;
	}
	
	/**
	 * 
	 * @param array1
	 * @param start
	 * @param mid
	 * @param end
	 * @param array2
	 */
	private static void topDownMerge(int[] array1, int start, int mid, int end, int[] array2) {
		int  i = start, j = mid;
	    for (int k = start; k < end; k++) {
	        if (i < mid && (j >= end || array1[i] <= array1[j])) {
	            array2[k] = array1[i];
	            i = i + 1;
	        } else {
	            array2[k] = array1[j];
	            j = j + 1;    
	        }
	    } 
	}

	/**
	 * Recursively sort halves of array1 by referencing array 2.
	 * @param array2	temp array for sorting
	 * @param start		start point in arrays
	 * @param end		end point in arrays
	 * @param array1	array to be sorted into
	 */
	private static void splitMerge(int[] array2, int start, int end, int[] array1) {
	    if(end - start < 2) return;                           	// base case, if length=1 the array is sorted
	    int mid = (end + start) / 2;              				// midpoint of sorting region	    
	    splitMerge(array1, start,  mid, array2);  				// recursively sort both halves of array1 into array2
	    splitMerge(array1, mid, end, array2);  						   
	    topDownMerge(array2, start, mid, end, array1);			// merge the sorted halves from array2 into array1
	}
	
}
