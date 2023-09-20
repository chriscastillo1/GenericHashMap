/*
 * Author: Chris Castillo
 * Class: CSC 210
 * Purpose: This program constructs an Abstract Data Type ListQueue with different methods
 *          where it acts like a normal queue (to users) but it is backed by a LinkedList 
 *          using a Node Class.
 * 
 * This class contains several public methods used for accessing the ListQueue:
 * enqueue(E value) - Puts an element at the back of the queue
 * dequeue() - Returns and removes the front element of the queue
 * peek() - Returns but does not remove the front element of the queue
 * isEmpty() - Returns true if queue is empty
 * size() - Returns the number of elements in the queue
 * clear() - Removes all elements from queue
 * 
 * NOTE: It has its own overridden method equals() and its own toString() method
 *       It also contains a COPY constructor: ListQueue(ListQueue other)
 * 
 * EXAMPLE USAGE:
 * ListQueue<String> myListQueue = new ListQueue<>();
 * myListQueue.enqueue(E value);
 * myListQueue.dequeue();
 * 
 * All commands above are supported by this program. It assumes all inputs for those
 * methods are of similar format as described above.
 */
public class ListQueue<E> implements QueueInterface<E> {
    // Declaring private variables to keep track of size and front/tail pointers
    private int size;
    private Node<E> tail, front;
    
    /*
     * A constructor that initializes the front and tail pointers to null and
     * the size to zero
     */
    public ListQueue() {
        size = 0;
        tail = front = null;
    }
    
    /*
     * A constructor that creates a duplicate ListQueue object of same
     * size and contents (but not same object)
     */
    public ListQueue(ListQueue<E> other) {
        this.size = other.size;
        if (other.front == null) {
            this.front = this.tail = null;
        }
        // Sets front pointer to reference of copied pointer
        this.front = cloneHelper(other.front);
        
        // Sets tail pointer to end node
        Node<E> curr = front;
        while (curr != null) {
            if (curr.next == null) { tail = curr; }
            curr = curr.next;
        }
    }

    /*
     * A private helper method that clones the Nodes
     * to form a cloned linked list
     * 
     * Return Value: Returns a reference pointer to the head of the linked list
     */
    private Node<E> cloneHelper(Node<E> node) {
        if (node == null) {
            return null;
        }
        Node<E> temp = new Node<E>(node.val, null);
        temp.next = cloneHelper(node.next);
        return temp;
    }

    @Override
    /*
     * A method that puts an element into the back of the queue
     */
    public void enqueue(E value) {
        // Creates new node to put in list
        Node<E> newNode = new Node<E>(value, null);
        
        // If front node is null, sets front to new node
        if (front == null) {
            front = tail = newNode;
            size++;
        } else {
            tail.next = newNode;
            tail = newNode;
            size++;
        }
    }
    
    @Override
    /*
     * A method that takes off the first element in the queue
     * 
     * Return Value: Returns the E associated with the first element in queue
     */
    public E dequeue() {
        if (size == 0) { return null; }
        
        E retval = front.val;
        front = front.next;
        size--;
        return retval;
    }
    
    @Override
    /*
     * A method that returns the first element in queue but does not
     * remove it from queue
     * 
     * Return Value: Returns element first in queue
     */
    public E peek() {
        if (size == 0) { return null; }
        return front.val;
    }
    
    @Override
    /*
     * A method that checks if the queue is empty
     * 
     * Return Value: Returns true if empty, false otherwise
     */
    public boolean isEmpty() { return (size == 0); }

    @Override
    /*
     * A method that returns the size of the queue
     * 
     * Return Value: int size
     */
    public int size() { return size; }

    @Override
    /*
     * A method that removes all elements in the queue
     */
    public void clear() {
        size = 0;
        front = tail = null;
    }

    @Override
    /*
     * A method that checks if two ListQueues are equal to each other by
     * size and contents (order matters)
     */
    public boolean equals(Object other) {
        if (other instanceof ListQueue) {
            ListQueue<?> o = (ListQueue<?>) other;
            return this.size == o.size && this.toString().equals(o.toString());
        } else return false;
    }

    @Override
    /*
     * A method that makes a string representation of ListQueue such that
     * { 1, 2, 3, } where 1 is next item to be dequeued and 3 is last.
     * 
     * Return Value: string retval (representing the ListQueue)
     */
    public String toString() {
        String retval = "";
        Node<E> curr = front;
        // Loops through the linkedlist and gets the value and adds to retval
        while (curr != null) {
            retval += curr.val + ", ";
            curr = curr.next;
        }
        return "{ " + retval + "}";
    }

    /*
     * This class is used to construct a generic LinkedList. The Node class has
     * two variables val and next where val is a generic E and next is a pointer
     * reference to another Node.
     */
    private static class Node<E> {
        E val;
        Node<E> next;

        /*
         * A constructor that initializes the value and next pointer
         */
        public Node(E val, Node<E> next) {
            this.val = val;
            this.next = next;
        }
    }
}
