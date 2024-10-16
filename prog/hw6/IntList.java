import java.util.Arrays;

public class IntList {
    int size = 0;
    int[] intList;
//    int pointer = 0;

    public IntList() {
        this.intList = new int[10];
    }

    public boolean add(int toAdd) {
        if (size >= intList.length) {
            intList = Arrays.copyOf(intList, intList.length * 2);
        }
        intList[size++] = toAdd;
        return true;
    }

    public int get(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
        return intList[index];
    }
}
