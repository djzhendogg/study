import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class HighLoadDatabase {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                int n = Integer.parseInt(reader.readLine());
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                int maxA = 0;
                List<Integer> l = new ArrayList<>();
                l.add(0);
                int li = 0;
                int k = 1;
                int sumB = 0;
                for (int num = 1; num <= n; num++) {
                    int ai = Integer.parseInt(tokenizer.nextToken());
                    sumB += ai;
                    while (k <= sumB) {
                        l.add(li);
                        k++;
                    }
                    if (ai > maxA) {
                        maxA = ai;
                    }
                    li = k;
                }

                int q = Integer.parseInt(reader.readLine());
                line = reader.readLine();
                tokenizer = new StringTokenizer(line, " ");
                for (int num = 0; num < q; num++) {
                    int t = Integer.parseInt(tokenizer.nextToken());
                    int blocks = 1;
                    int gap = 1;
                    if (t < maxA) {
                        System.out.println("Impossible");
                        continue;
                    }
                    while (gap + t < l.size()) {
                        gap = l.get(gap + t);
                        blocks++;
                    }
                    System.out.println(blocks);
                }

            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
