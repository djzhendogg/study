package main.java.test;

public class SumFloat {
    public static void main(String[] args) {
        float sum = 0;
        StringBuilder numberString = new StringBuilder();
        for (String arg: args) {
            for (char character: (arg + " ").toCharArray()) {
                if (Character.isWhitespace(character)) {
                    if (!numberString.isEmpty()) {
                        sum += Float.parseFloat(numberString.toString());
                    }
                    numberString = new StringBuilder();
                } else {
                    numberString.append(character);
                }
            }
        }
        System.out.println(sum);
    }
}
