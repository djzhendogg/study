public class Checker {
    public static void main(String[] args) {
//        int[] ints = new int[] {
//                1, 2, 3
//        };
//        int b = ints[3];

        IntList ints = new IntList();

        ints.add(1);
        ints.add(2);
        ints.add(2);

        int c = ints.size() / 2;

        System.out.println(c);
    }
}
