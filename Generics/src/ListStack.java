/*
 * Author: Chris Castillo
 * Class: CSC 210
 * Purpose: This program constructs an Abstract Data Type ListStack which behaves like
 *          a stack but is backed by a linked list constructed using a Node Class.
 * 
 * This class contains several public methods used for accessing ListStack:
 * push(E value) - Used to add element to top of stack
 * pop() - Return and removes element from top of stack
 * peek() - Returns element from top of stack (Note: Doesnt remove it)
 * isEmpty() - Returns true if stack is empty
 * size() - Returns number of elements in the stack
 * clear() - Removes all elements from the stack
 * 
 * NOTE: It has its own overridden method equals() and its own toString() method
 *       It also contains a COPY constructor: ListStack(ListStack other)
 *       
 * EXAMPLE USAGE:
 * ListStack<Integer> myListStack = new ListStack<>();
 * myListStack.push(E value);
 * myListStack.pop();
 * 
 * All commands above are supported by this program. It assumes all inputs for those
 * methods are of similar format as described above.
 */
public class ListStack<E> implements StackInterface<E> {
    // Declaring private variables to keep track of head pointer and size
    private Node<E> head;
    private int size;

    /*
     * A constructor that initializes the head pointer to null and the size
     * to zero
     */
    public ListStack() {
        head = null;
        size = 0;
    }

    /*
     * A copy constructor that makes an identical ListStack
     * of same size and contents (with order mattering)
     */
    public ListStack(ListStack<E> other) {
        this.size = other.size;
        if (other.head == null) {
            this.head = null;
        } else {
            // Calls helper function to duplicate linked list
            head = cloneHelper(other.head);
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
     * A method to add an element to the linkedlist stack.
     */
    public void push(E value) {
        if (head == null) {
            head = new Node<E>(value, null);
            size++;
        } else {
            Node<E> newHead = new Node<E>(value, head);
            head = newHead;
            size++;
        }
    }

    @Override
    /*
     * A method that takes off the last element in the stack and
     * removes it from the linked list.
     * 
     * Return Value: Returns the E val from the linkedlist on top of stack
     */
    public E pop() {
        if (size == 0) { return null; }

        E retval = head.val;
        head = head.next;
        size--;
        return retval;
    }
    
    @Override
    /*
     * A method that returns the number off the top of the stack
     * but does not remove it
     * 
     * Return Value: Returns E off the top of the stack
     */
    public E peek() {
        // ERROR CHECKING: Trying to peek() on empty stack returns -1
        if (size == 0) { return null; }

        return head.val;
    }
    
    @Override
    /*
     * A method to check if the ListStack is empty
     * 
     * Return Value: Returns true if empty, false if otherwise
     */
    public boolean isEmpty() { return (size == 0); }

    @Override
    /*
     * A method that returns the size of the ListStack
     * 
     * Return Value: int size (representing how many nodes in list stack)
     */
    public int size() { return size; }

    @Override
    /*
     * A method that clears the entire ListStack
     */
    public void clear() {
        size = 0;
        head = null;
    }
    
    /*
     * A method to check if two ListStacks are equal by
     * size and content (order matters)
     * 
     * Return Value: returns true if equal, false otherwise
     */
    public boolean equals(Object other) {
        if (other instanceof ListStack) {
            ListStack<?> o = (ListStack<?>) other;
            return this.size == o.size && this.toString().equals(o.toString());
        } else
            return false;
    }
    
    /*
     * A method to have a string representation of the ListStack
     * { 1, 2, 3, } where 1 is bottom of stack and 3 is top of stack
     * 
     * Return Value: Returns a string representation of the ListStack
     */
    public String toString() {
        String retval = "";
        Node<E> curr = head;
        // Loops through linkedlist and gets the vals and adds to retval
        while (curr != null) {
            retval = curr.val + ", " + retval;
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
