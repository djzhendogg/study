import java.util.Arrays;

public class IntList {
    private static final int INIT_CAPACITY = 10;
    private int size = 0;
    private int[] intList;

    public IntList() {
        this.intList = new int[INIT_CAPACITY];
    }

    public void add(int toAdd) {
        if (size >= intList.length) {
            intList = Arrays.copyOf(intList, intList.length * 2);
        }
        intList[size++] = toAdd;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        return intList[index];
    }

    public int size() {
        return size;
    }
}
