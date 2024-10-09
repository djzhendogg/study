import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;

public class Reverse {
    public static void main(String[] args) throws IOException {
        MyScanner in = new MyScanner(System.in);
        List<List<Integer>> listLine = new ArrayList<>();
        while (in.hasNext()) {
            String line = in.nextLine();
            List<Integer> listInt = new ArrayList<>();
            MyScanner stringScanner = new MyScanner(line);
            while (stringScanner.hasNextInt()) {
                listInt.add(stringScanner.nextInt());
            }
            listLine.add(listInt);
        }
        for (int i = listLine.size() - 1; i >= 0; i--) {
            for (int j = listLine.get(i).size() - 1; j >= 0; j--) {
                System.out.print(listLine.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }
}