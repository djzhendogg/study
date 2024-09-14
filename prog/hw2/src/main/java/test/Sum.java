public class Sum {
    public static void main(String[] args) {
        int sum = 0;
        String n = "";
        for (String arg: args) {
            for (char ch: arg.toCharArray()) {
                if (Character.isWhitespace(ch)) {
                    if (!n.isEmpty()) {
                        int foo = Integer.parseInt(n);
                        sum += foo;
                    }
                    n = "";
                } else {
                    n += ch;
                }
            }
            if (!n.isEmpty()) {
                int foo = Integer.parseInt(n);
                sum += foo;
            }
            n = "";
        }
        System.out.println(sum);
    }
}
