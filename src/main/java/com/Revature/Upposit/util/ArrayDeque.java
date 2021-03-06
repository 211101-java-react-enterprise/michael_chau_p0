package com.Revature.Upposit.util;

/**
 * Resizable-array implementation of the Deque interface. Array deques have no
 * capacity restrictions; they grow as necessary to support usage. Null elements
 * are prohibited.
 *
 * @param <T> the type of elements maintained by this list
 */
public class ArrayDeque<T> implements Deque<T> {

    private Object[] elements;
    private int size = 0;
    private int capacity = 16;
    private static final int DEFAULT = 16;
    private int head = -1; //index of head
    private int tail = -1; //index of tail


    /**
     * Constructs an empty array deque with an initial capacity sufficient to
     * hold 16 elements.
     */
    public ArrayDeque() {
        elements = new Object[16];
    }

    /**
     * Constructs an empty array deque with an initial capacity sufficient to
     * hold the specified number of elements.
     *
     * @param initialCapacity lower bound on initial capacity of the deque
     */

    public ArrayDeque(int initialCapacity) {
        elements = new Object[initialCapacity];
        capacity = initialCapacity;
    }

    public void grow(){
//        capacity += 16;

        Object[] result = new Object[capacity + 16];

        System.arraycopy(elements, 0, result, 8, capacity);

        capacity+=16;
        elements = result;
        head+= 8;
        tail+=8;
    }

    public void display() {
        String result = "";
        for(int i=0; i<capacity; i++){
            result = result + elements[i] + " ";
        }
        result += " Size: " + size + " Head: " + head + " Tail " +tail;
        System.out.println(result);
    }

    /**
     * Inserts the specified element at the end of this deque.
     *
     * @param element the element to add
     * @return true
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public boolean add(T element) {
        if (element == null) {
            throw new NullPointerException("Caught Null Pointer Exception");
        }

        if (tail==capacity-1) grow();

        if (head == -1 && tail == -1) {
            head = tail = capacity/2;
            elements[tail] = element;
        } else elements[++tail] = element;
        size++;
        return true;
    }


    /**
     * Returns true if this deque contains the specified element. More formally,
     * returns true if and only if this deque contains at least one element e such
     * that o.equals(e).
     *
     * @param element object to be checked for containment in this deque
     * @return true if this deque contains the specified element
     */
    @Override
    public boolean contains(T element) {
        for(int i = head; i <=tail ; i++){
            if (elements[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if this deque contains no elements.
     *
     * @return true if this deque contains no elements
     */
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Removes the first occurrence of the specified element in this deque (when
     * traversing the deque from head to tail). If the deque does not contain the
     * element, it is unchanged. More formally, removes the first element e such
     * that o.equals(e) (if such an element exists). Returns true if this deque
     * contained the specified element (or equivalently, if this deque changed
     * as a result of the call).
     *
     * @param element element to be removed from this deque, if present
     * @return true if the deque contained the specified element
     */
    @Override
    public boolean remove(T element) {
        if (isEmpty()) return false;

        for(int i = head; i <= tail; i++){
            if (elements[i].equals(element)) {

                for(int j = i; j <= tail; j++){
                    elements[j] = elements[j+1];
                }
                if( --size == 0){
                    head = tail = -1;
                }else tail--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Inserts the specified element at the front of this deque.
     *
     * @param element the element to add
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public void addFirst(T element) {
        if (element == null) {
            throw new NullPointerException("Caught Null Pointer Exception");
        }
        if (++size >= capacity) grow();

        if (head == -1 && tail == -1) {
            head = tail = capacity/2;
            elements[tail] = element;
        } else elements[--head] = element;

    }

    /**
     * Inserts the specified element at the end of this deque.
     *
     * @param element the element to add
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public void addLast(T element) {
        if (element == null) {
            throw new NullPointerException("Caught Null Pointer Exception");
        }
        if (++size >= capacity) grow();

        if (head == -1 && tail == -1) {
            head = tail = capacity/2;
            elements[tail] = element;
        } else elements[++tail] = element;
    }

    /**
     * Retrieves and removes the first element of this deque, or returns null
     * if this deque is empty.
     *
     * @return the head of this deque, or null if this deque is empty
     */
    @Override
    public T pollFirst() {
        if(isEmpty()) return null;
        T result = (T)elements[head];
        elements[head] = null;
        head++;
        if (--size==0) head = tail = -1;

        return result;
    }

    /**
     * Retrieves and removes the last element of this deque, or returns null if
     * this deque is empty.
     *
     * @return the tail of this deque, or null if this deque is empty
     */
    @Override
    public T pollLast() {
        if(isEmpty()) return null;
        T result = (T)elements[tail];
        elements[tail] = null;
        tail--;
        if (--size==0) head = tail = -1;

        return result;
    }

    /**
     * Retrieves, but does not remove, the first element of this deque, or returns null
     * if this deque is empty.
     *
     * @return the head of this deque, or null if this deque is empty
     */
    @Override
    public T peekFirst() {
        if(isEmpty()) return null;
        return (T)elements[head];
    }

    /**
     * Retrieves, but does not remove, the last element of this deque, or returns null
     * if this deque is empty.
     *
     * @return the tail of this deque, or null if this deque is empty
     */
    @Override
    public T peekLast() {
        if(isEmpty()) return null;
        return (T)elements[tail];
    }

    /**
     * Retrieves and removes the head of the queue represented by this deque (in other words,
     * the first element of this deque), or returns null if this deque is empty.
     *
     * This method is equivalent to pollFirst().
     *
     * @return the head of the queue represented by this deque, or null if this deque is empty
     */

    public T poll() {
        return pollFirst();
    }

    /**
     * Retrieves, but does not remove, the head of the queue represented by this deque, or
     * returns null if this deque is empty.
     *
     * This method is equivalent to peekFirst().
     *
     * @return the head of the queue represented by this deque, or null if this deque is empty
     */
    public T peek() {
        if(isEmpty()) return null;
        return (T)elements[head];
    }

}