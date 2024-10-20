import java.util.ArrayList;
import java.util.List;

public class Checker {
    public static void main(String[] args) {
        List<Integer> first = new ArrayList<>();
        first.add(1);
        first.add(2);
        first.add(3);

        first.remove(0);
        System.out.println(first);
    }
}
