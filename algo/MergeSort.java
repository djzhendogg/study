import java.util.Scanner;

public class MergeSort {
    public static int INV = 0;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = in.nextInt();
        }
        mergeSort(array, array.length);
        System.out.print(INV);
    }
    public static void myMergeSort(int[] arr, int left, int right) {
        if (left + 1 >= right) {
            return;
        }
        int mid = (left + right) / 2;
        myMergeSort(arr, left, mid);
        myMergeSort(arr, mid, right);
        myMerge(arr, left, mid, right);
    }

    public static void myMerge(int[] arr, int left, int mid, int right) {
        int iterO = 0;
        int iterL = 0;
        int[] temp = new int[right - left];
        while ((left + iterO) < mid  && (mid + iterL) < right) {
            if (arr[left + iterO] < arr[mid + iterL]) {
                temp[iterO + iterL] = arr[left + iterO];
                iterO++;
            } else {
                temp[iterO + iterL] = arr[mid + iterL];
                iterL++;
                INV += mid - left - iterO;
            }
        }
        while (left + iterO < mid) {
            temp[iterO + iterL] = arr[left + iterO];
            iterO++;
//            INV++;
        }

        while (mid + iterL < right) {
            temp[iterO + iterL] = arr[mid + iterL];
            iterL++;
        }

        for (int i = 0; i < iterO + iterL; i++) {
            arr[left + i] = temp[i];
        }
    }

    public static void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);

        merge(a, l, r, mid, n - mid);
    }
    public static void merge(
            int[] a, int[] l, int[] r, int left, int right) {

        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i] <= r[j]) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];

            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

}
