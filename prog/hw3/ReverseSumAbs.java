import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReverseSumAbs {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<List<Integer>> listLine = new ArrayList<>();
        int maxLenRow = 0;
        while (in.hasNextLine()) {
            String line = in.nextLine();
            List<Integer> listInt = new ArrayList<>();
            Scanner stringScanner = new Scanner(line);
            while (stringScanner.hasNextInt()) {
                listInt.add(stringScanner.nextInt());
            }
            if (maxLenRow < listInt.size()) {
                maxLenRow = listInt.size();
            }
            listLine.add(listInt);
        }

        int[] rowSum = new int[listLine.size()];
        int[] colSum = new int[maxLenRow];

        for (int i = 0; i < listLine.size(); i++) {
            for (int j = 0; j < listLine.get(i).size(); j++) {
                rowSum[i] += Math.abs(listLine.get(i).get(j));
                colSum[j] += Math.abs(listLine.get(i).get(j));
            }
        }

        for (int i = 0; i < listLine.size(); i++) {
            for (int j = 0; j < listLine.get(i).size(); j++) {
                System.out.print((rowSum[i] + colSum[j] - Math.abs(listLine.get(i).get(j))) + " ");
            }
            System.out.println();
        }
    }
}
