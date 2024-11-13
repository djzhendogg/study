import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IdealPyramid {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    System.in,
                    "UTF8"));
            try {
                int xl = Integer.MAX_VALUE;
                int xr = Integer.MIN_VALUE;
                int yl = Integer.MAX_VALUE;
                int yr = Integer.MIN_VALUE;
                int n = Integer.parseInt(reader.readLine());
                for (int i = 0; i < n; i++) {
                    String[] str = reader.readLine().split("\\s");
                    int x = Integer.parseInt(str[0]);
                    int y = Integer.parseInt(str[1]);
                    int h = Integer.parseInt(str[2]);
                    if (x - h < xl) {
                        xl = x - h;
                    }
                    if (x + h > xr) {
                        xr = x + h;
                    }
                    if (y - h < yl) {
                        yl = y - h;
                    }
                    if (y + h > yr) {
                        yr = y + h;
                    }
                }
                int h = (int) Math.ceil((double) Math.max(xr - xl, yr - yl) / 2);
                int x = (xr + xl) / 2;
                int y = (yr + yl) / 2;
                System.out.println(x + " " + y + " " + h);
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
