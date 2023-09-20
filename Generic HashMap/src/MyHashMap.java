import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 * Author: Chris Castillo
 * Class: CSC 210
 * Purpose: This program constructs an ADT of a Generic HashMap. It is backed by
 * 			an arraylist of linked hashnodes.
 * 
 * This class contains several public methods for accessing the HashMap:
 * put - Puts a K,V pair into the HashMap
 * get - Returns a value from a given key
 * remove - Removes a K,V pair from the HashMap
 * keySet - returns a Set of all keys in the HashMap
 * containsKey - checks if a key is in the HashMap
 * containsValue - checks if a value is in the HashMap
 * clear - removes ALL K,V pairs from the HashMap
 * size - returns the number of K,V pairs in the HashMap
 * isEmpty - Checks if the HashMap is empty
 * printTable - prints out a representation of the HashMap with all its buckets and keys
 * 
 * EXAMPLE USAGE:
 * MyHashMap<String, Integer> hash = new MyHashMap<>();
 * hash.put("Key1", 1);
 * hash.remove("Key2") - (NOTE: Since key2 doesn't exist it returns null and does nothing)
 * 
 * All commands above are supported by this program. It assumes all inputs for those
 * methods are of similar format as described above.
 */
public class MyHashMap<K, V> {
	// Declaring several private variables to manage the HashMap
	private static final int numBuckets = 8;
	private int[] collisionCount;
	private List<HashNode<K, V>> container;
	private Set<K> keys;
	
	/*
	 * Default constructor for making a HashMap. It initializes
	 * keys (to new Set), container (to new arraylist), and
	 * collisionCount (to new int array)
	 */
	public MyHashMap() {
		collisionCount = new int[numBuckets];
		container = new ArrayList<>();
		container.add(null); container.add(null); container.add(null); container.add(null);
		container.add(null); container.add(null); container.add(null); container.add(null);
		keys = new HashSet<>();
	}
	
	/*
	 * A method that puts a K,V pair into the HashMap.
	 * 
	 * Return Value: returns the previous value associated with the Key or
	 * 				 return null if the key had no mapping value
	 */
	public V put(K key, V value) {
		// Gets the index for the container (which bucket to put it in)
		int index = this.hash(key);
		// Creates new HashNode to insert into HashMap
		HashNode<K, V> newNode = new HashNode<K, V>(key, value);
		// Adds the new key to a set of all keys in HashMap
		keys.add(key);
		
		// Checks if the bucket (index) is empty, if so it puts newNode in the bucket
		if (container.get(index) == null) {
			container.set(index, newNode);
			
		} else {
			// Gets previous value that Key had (CAN BE NULL TOO)
			V prevVal = this.get(key);
			
			// If the key already exists in the LinkedList, it goes and updates
			// the value of the key (NOTE: THIS DOES NOT COUNT AS COLLISION)
			if (!doesExist(container.get(index), key, value)) {
				// Key is not in linked list so it adds it to the front of the list
				newNode.setNext(container.get(index));
				container.set(index, newNode);
				collisionCount[index] += 1;
				return null;
			}
			return prevVal;
		}
		return null;
	}
	
	/*
	 * A private helper method that checks if a key exists in a linked list. If
	 * it does exist it updates the key with the new value (from put function)
	 * 
	 * Return Value: True if it does, false otherwise
	 */
	private boolean doesExist(HashNode<K, V> front, K key, V value) {
		// Sets a temporary pointer to the front node
		HashNode<K, V> curr = front;
		while (curr != null) {
			// If the key exists in the list, it updates the current value
			if (curr.getKey().equals(key)) {
				curr.setValue(value);
				return true;
			}
			curr = curr.getNext();
		}
		return false;
	}
	
	/*
	 * A method that returns the value associated with the key. Null otherwise
	 * 
	 * Return Value: V value associated w/ Key, or null if doesn't exist
	 */
	public V get(K key) {
		int index = this.hash(key);
		HashNode<K, V> curr = container.get(index);
		
		while (curr != null) {	
			// If the key exists, it returns the Value mapped to key
			if (curr.getKey().equals(key)) {
				return curr.getValue();
			}
			curr = curr.getNext();
		}
		return null;
	}
	
	/*
	 * Removes they Key,Val pair from HashMap
	 * 
	 * Return Value: Returns the V value associated with key
	 * 				 (NOTE: can return null if no value was mapped to key)
	 */
	public V remove(K key) {
		V prevVal = this.get(key);
		int index = this.hash(key);
		collisionCount[index] -= 1;
		// Sets curr pointer temp and prev pointer
		HashNode<K, V> temp = container.get(index);
		HashNode<K, V> prev = null;
		keys.remove(key);
		
		// If the first node is the key we are looking for we remove the node
		// and set the head pointer to the node.next
		if (temp != null && temp.getKey().equals(key)) {
			container.set(index, temp.getNext());
			return prevVal;
		}
		// Loops through until it reaches the key we are looking for
		while (temp != null && !(temp.getKey().equals(key))) {
			prev = temp;
			temp = temp.getNext();
		}
		// If the pointer is null, the key is NOT in list so it returns null
		if (temp == null) { return null; }
		// It takes the node out of the list and fixes the pointers
		prev.setNext(temp.getNext());
		return prevVal;
	}
	
	/*
	 * A method that returns the set of all keys in the HashMap
	 */
	public Set<K> keySet() { return keys; }
	
	/*
	 * A method to check if a certain key is in the HashMap
	 * 
	 * Return Value: True if key exists, false otherwise
	 */
	public boolean containsKey(K k) { return keys.contains(k); }
	
	/*
	 * A method to check if a certain value is in the HashMap
	 * 
	 * Return Value: True if val exists, false otherwise
	 */
	public boolean containsValue(V Val) {
		// Loops through all the buckets in the container
		for (int i = 0; i < 8; i++) {
			// Makes a head pointer for each bucket
			HashNode<K, V> curr = container.get(i);
			// Loops through each linked list in the bucket
			while (curr != null) {
				if (curr.getValue().equals(Val)) {
					return true;
				}
				curr = curr.getNext();
			}
		}
		return false;
	}
	
	/*
	 * A method that removes all mappings from the HashMap
	 */
	public void clear() {
		keys.clear();
		collisionCount = new int[numBuckets];
		container = new ArrayList<>();
		container.add(null); container.add(null); container.add(null); container.add(null);
		container.add(null); container.add(null); container.add(null); container.add(null);
	}
	
	/*
	 * A method that returns the number of Key,Val pairs in the HashMap
	 * 
	 * Return Val: int size
	 */
	public int size() { return keys.size(); }
	
	/*
	 * A method that checks if a HashMap is empty (size == 0)
	 * 
	 * Return Value: True if empty, false otherwise
	 */
	public boolean isEmpty() { return keys.size() == 0; }
	
	/*
	 * Prints out the string representation of the HashMaps with all the buckets
	 * and all the collisions the buckets have.
	 */
	public void printTable() {
		// Count for how many collisions there are total
		int count = 0;
		
		// Loops through each bucket
		for (int i = 0; i < 8; i++) {
			// Adds the collisionCount to total count
			count += collisionCount[i];
			String s = "Index " + i +  ": " + "(" + collisionCount[i] + " conflicts), ";
			
			HashNode<K, V> curr = container.get(i);
			String keyList = "";
			// Loops through the linked list and adds the keys in order front->last
			while (curr != null) {
				keyList = keyList + curr.getKey() + ", ";
				curr = curr.getNext();
			}
			System.out.println(s +  "[" + keyList + "]");
		}
		System.out.println("Total # of conflicts: " + count);
	}
	
	private int hash(K key) {
		int hashCode = key.hashCode();
		int index = hashCode % numBuckets;
		return Math.abs(index);
	}
}
