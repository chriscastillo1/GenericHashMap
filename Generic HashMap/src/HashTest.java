import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HashTest {
	public static void main(String[] args) {
		MyHashMap<String, Integer> test = new MyHashMap<>();
		
		test.put("HELLO", 11);
		test.put("FROM", 22);
		test.put("THE", 33);
		test.put("OTHER", 44);
		test.put("SIDE", 55);
		test.put("OF", 66);
		test.put("TOWN", 77);
		test.put("YALL", 88);
		
		test.put("THIS IS ME", 777);
		test.printTable();
	}
}
