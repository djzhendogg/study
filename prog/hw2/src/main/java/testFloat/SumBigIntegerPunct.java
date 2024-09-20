import java.math.BigInteger;

public class SumBigIntegerPunct {
    public static void main(String[] args) {
        BigInteger sum = BigInteger.ZERO;
        for (String arg: args) {
            int start = 0;
            boolean space = true;
            arg = arg + " ";
            for (int i = 0; i < arg.length(); i++) {
                char character = arg.charAt(i);
                if (Character.isWhitespace(character)|| Character.getType(character) == Character.START_PUNCTUATION
                    || Character.getType(character) == Character.END_PUNCTUATION) {
                    if (!space) {
                        sum = sum.add(new BigInteger(arg.substring(start, i)));
                    }
                    start = i;
                    space = true;
                } else if (space){
                    start = i;
                    space = false;
                }
            }
        }
        System.out.println(sum);
    }
}
