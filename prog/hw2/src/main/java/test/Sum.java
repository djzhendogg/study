public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        StringBuilder numberString = new StringBuilder();
        for (String arg: args) {
            for (char character: arg.toCharArray()) {
                if (Character.isWhitespace(character)) {
                    if (!numberString.isEmpty()) {
                        sum += Integer.parseInt(numberString.toString());
                    }
                    numberString = new StringBuilder();
                } else {
                    numberString.append(character);
                }
            }
            if (!numberString.isEmpty()) {
                sum += Integer.parseInt(numberString.toString());
            }
            numberString = new StringBuilder();
        }
        System.out.println(sum);
    }
}
