/**
 * Implementation of a generic stack 
 * @author David Saeva 
 */
package Elevator;

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
