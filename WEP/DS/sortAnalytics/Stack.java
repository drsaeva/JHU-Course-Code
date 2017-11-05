/**
 * Implementation of a generic stack 
 * @author David Saeva 
 */

package sortAnalytics;

public class Stack<T> {
	 
	private T[] arr;

	private int top = -1;
	private int limit = 0;

	public Stack(int fixedSize) {
		setLimit(fixedSize);
		arr = (T[]) new Object[limit];
	}

	/**
	 * Adds Object to the top of the stack.
	 * Throws IndexOutOfBoundsException in the event that push is called on a full stack.
	 * @param object {@link T}
	 */
	public Stack<T> push (T ele) {
		if (top+1 == limit) throw new IndexOutOfBoundsException("Stack overflow!");
		arr[++top] = ele;
		return this;
	}

	/**
	 * Gets and removes Object currently at the top of the stack.
	 * Throws NoSuchElementException in the event that pop is called on an empty stack.
	 * @return Object currently at the top of the stack and removes Object from stack.
	 * 
	 */
	public T pop() {
			if (isEmpty()) throw new java.util.NoSuchElementException("Stack underflow!");
			return arr[top--];
	}

	/**
	 * Method peek
	 * Throws NoSuchElementException in the event that peek is called on an empty stack.
	 * @return {@link T} Object currently at the op of the stack without removing
	 */
	public T peek() {
		if (isEmpty()) throw new java.util.NoSuchElementException("Stack underflow!");
		return arr[top];
	}

	/**
	 * Gets the current amount of Objects in the stack
	 * @return {@link #top}
	 */
	public int size() {
		return top+1;
	}

	/**
	 * Sets the maximum amount of Objects allowed in the stack
	 * @param size
	 */
	private void setLimit(int size) {
		limit = size;
	}
	
	/**
	 * Gets the maximum amount of Objects allowed in the stack
	 * @param size
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * States whether there are passengers in the elevator.
	 * @return true if there are no passengers in the elevator;
	 *         false otherwise
	 */
	public boolean isEmpty() {
		if (top == -1) {
			return true;
		}
		return false;
	}
    
}
