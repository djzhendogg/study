import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int t = scanner.nextInt();
        int[] workers = new int[n];
        for (int i = 0; i < n; i++) {
            workers[i] = scanner.nextInt();
        }
        int c = scanner.nextInt() - 1;

        int ans = workers[n - 1] - workers[0];

        if (c == 0 || c == n - 1) {
            System.out.print(ans);
            return;
        }

        int d = Math.min(workers[c] - workers[0], workers[n - 1] - workers[c]);

        if (d > t) {
            ans += d;
        }
        System.out.print(ans);
    }
}