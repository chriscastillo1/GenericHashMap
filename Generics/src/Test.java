
public class Test {
    public static void main(String[] args) {
        ListStack<String> lqString = new ListStack<>();

        ListStack<Integer> lqInt = new ListStack<Integer>();

        System.out.println(lqString.equals(lqInt));
    }
}
