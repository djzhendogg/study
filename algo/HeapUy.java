import java.util.List;

public class HeapUy {
    public static void main(String[] args) {

    }

    public static void swap(List<Integer> arr, int f, int s) {
        int temp = arr.get(f);
        arr.set(f, arr.get(s));
        arr.set(s, temp);
    }

    public static void siftUp(List<Integer> arr, int pos) {
        int parent = (pos - 1) / 2;
        while (arr.get(parent) < arr.get(pos)) {
            swap(arr, parent, pos);
            parent = (parent - 1) / 2;
        }
    }

    public static void siftDown(List<Integer> arr, int pos) {
        int childLeft = 2 * pos + 1;
        while (childLeft < arr.size()) {
            int childRight = 2 * pos + 2;
            int swapWith = childLeft;
            if (childRight < arr.size() && arr.get(childLeft) < arr.get(childRight)) {
                swapWith = childRight;
            }
            if (arr.get(pos) >= arr.get(childRight)) {
                break;
            }
            swap(arr, pos, swapWith);
            pos = swapWith;
            childLeft = 2 * pos + 1;
        }
    }

    public static int extract(List<Integer> arr) {
        int max = arr.getFirst();
        arr.set(0, arr.getLast());
        arr.removeLast();
        siftDown(arr, 0);
        return max;
    }

    public static void insert(List<Integer> arr, int x) {
        arr.add(x);
        siftUp(arr, arr.size() - 1);
    }
}
