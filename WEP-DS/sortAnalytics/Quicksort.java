package sortAnalytics;

public class Quicksort {
	

	/**
	 * Executes quick sort, generating temp array for sorting from the passed-in array argument. Takes systemtime in 
	 * nanoseconds prior to start of sort and immediately after completion, returning the difference
	 * @param values array to be sorted
	 * @return	time in nanoseconds required to sort passed-in array
	 */
	 public static long sort(int[] values) {
		 long startTime = System.nanoTime();
         int[] temp = values; 
         if (values == null || values.length==0)						// check for empty or null array
                 return System.nanoTime() - startTime;
         quicksort(temp, 0, temp.length-1);
         return System.nanoTime()-startTime;
	 }
	 
	 /**
	  * Iterates from both ends of the arrays, checking those elements values against the central/pivot element's value.
	  * Continues iterating if those current elements appear placed properly (left smaller, right larger than pivot).
	  * @param data array to be sorted
	  * @param low
	  * @param high
	  */
	 private static void quicksort(int[] data, int low, int high) {
         int i = low, j = high;
         int pivot = data[low + (high-low)/2];						// pivot element - middle of the list

         while (i <= j) {											// If current element from left list < pivot
                 while (data[i] < pivot) {							// get next element from left list
                         i++;	
                 }
                 while (data[j] > pivot) {							// If current element from right list > pivot
                         j--;										// get next element from right list
                 }

                 if (i <= j) {										// If val in right list < pivot and val in left list > pivot
                         exchange(data, i, j);						// exchange vals, increment left/right counters
                         i++;
                         j--;
                 }
         }
         
         if (low < j)												// recursive calls 
                 quicksort(data, low, j);
         if (i < high)
                 quicksort(data, i, high);
 	}
	 
	/**
	 * Exchanges two values in passed in array
	 * @param data	array in which values are stored
	 * @param i	index of first value
	 * @param j index of second value
	 */
 	private static void exchange(int[] data, int i, int j) {
         int temp = data[i];
         data[i] = data[j];
         data[j] = temp;
 	}
}
