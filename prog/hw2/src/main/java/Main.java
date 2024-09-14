package main.java;

public class Sum {
    public static void main(String[] args) {
        System.err.println(args);
        int sum = 0;
        if (args.length > 0) {
            for (int wordId = 0; wordId < args.length; wordId++) {
                if (args[wordId].isBlank()) {
                } else {
                    String word = args[wordId];
                    String stringNumber = "";
                    for (int letterId = 0; letterId < word.length(); letterId++) {
                        char letter = word.charAt(letterId);
                        if (Character.isWhitespace(letter)) {
                            if (stringNumber.isBlank()) {
                            } else {
                                int number = Integer.parseInt(stringNumber);
                                sum += number;
                                stringNumber = "";
                            }
                        } else {
                            stringNumber += letter;
                        }
                    }
                }
            }
            System.out.println(sum);
        } else {
            System.out.println(sum);
        }
    }
}
