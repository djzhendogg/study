import java.util.ArrayList;
import java.util.List;

public class Checker {
    public static void main(String[] args) {
//        List<Integer> first = new ArrayList<>(2_000_00);
        int[] a = new int[] {2, 4, 5, 10};
        System.out.println(a[binarySearchLeft(a, 7)]);
        System.out.println(a[binarySearchRight(a, 7)]);
    }

    public static boolean checkRight(int[] arr, int mid, int x) {
        return arr[mid] >= x;
    }

    public static boolean checkLeft(int[] arr, int mid, int x) {
        return arr[mid] <= x;
    }

    public static int binarySearchRight(int[] arr, int x) {
        int left = -1;
        int right = arr.length;
        while (right - left > 1) {
            int mid = (left + right) / 2;
            if (checkRight(arr, mid, x)) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return right;
    }

    public static int binarySearchLeft(int[] arr, int x) {
        int left = -1;
        int right = arr.length;
        while (right - left > 1) {
            int mid = (left + right) / 2;
            if (checkLeft(arr, mid, x)) {
                left = mid;
            } else {
                right = mid;
            }
        }
        return left;
    }

}
