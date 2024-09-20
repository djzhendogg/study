public class SumFloatPunct {
    public static void main(String[] args) {
        float sum = 0;
        for (String arg: args) {
            int pointer = 0;
            boolean preSpace = true;
            arg = arg + " ";
            for (int i = 0; i < arg.length(); i++) {
                char character = arg.charAt(i);
                if (Character.isWhitespace(character)|| Character.getType(character) == Character.START_PUNCTUATION
                    || Character.getType(character) == Character.END_PUNCTUATION) {
                    if (!preSpace) {
                        sum += Float.parseFloat(arg.substring(pointer, i));
                    }
                    pointer = i;
                    preSpace = true;
                } else if (preSpace){
                    pointer = i;
                    preSpace = false;
                }
            }
        }
        System.out.println(sum);
    }
}
