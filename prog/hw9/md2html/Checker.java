package md2html;

public class Checker {
    public static void main(String[] args) {
        StringBuilder a = new StringBuilder("ли вы *вложенные __выделения__* **так**");
        Pattern now;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '*') {
                if (i + 1 < a.length() && a.charAt(i + 1) == '*') {
                    now = Pattern.STRONG;
                    int j = i + 1;
                    while (a.charAt(j) == '*') {
                        j++;
                    }

                }
                else {
                    now = Pattern.EMP;
                }
            } else if (a.charAt(i) == '_') {
                if (i + 1 < a.length() && a.charAt(i + 1) == '_') {
                    now = Pattern.STRONG;
                }
                else {
                    now = Pattern.EMP;
                }
            }
        }


    }
    private enum Pattern {
        EMP,
        STRONG
    }

}
