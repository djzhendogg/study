package md2html;

import java.io.*;
import java.util.Map;

public class BlockReader {
    BufferedReader reader;
    boolean closed = false;

    public BlockReader(InputStream input) throws UnsupportedEncodingException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        input,
                        "UTF8"
                )
        );
    }

    public StringBuilder nextBlock() throws IOException {
        StringBuilder block = new StringBuilder();
        String line = reader.readLine();
        boolean previosLine = false;

        if (line != null && line.isEmpty()) {
            while (line.isEmpty()) {
                line = reader.readLine();
            }
        }

        while (line != null && !line.isEmpty()) {
            if (previosLine) {
                block.append('\n');
            }
            block.append(line);
            line = reader.readLine();
            previosLine = true;
        }

        if (line == null) {
            close();
        }

        if (block.isEmpty()) {
            return null;
        } else {
            return block;
        }
    }

    public boolean hasNext() throws IOException {
        return !closed;
    }

    public void close() throws IOException {
        reader.close();
        closed = true;
    }

    public void lineChecker(String line) {
        Map<Pattern, Boolean> ex = new Map<>;
        Pattern nowP;
        for (int i = 0; i < line.length(); i++) {
            if (i + 1 < line.length()) {
                nowP = whatToken(line.charAt(i), line.charAt(i + 1));
            } else {
                nowP = whatToken(line.charAt(i), '\u0000');
            }
        }
    }

    public Pattern whatToken(char c, char nextC) {
        if (c == '*') {
            if (nextC == '*') {
                return Pattern.STRONG;
            } else {
                return Pattern.EMPHASIS;
            }
        } else if (c == '_') {
            if (nextC == '_') {
                return Pattern.STRONG;
            } else {
                return Pattern.EMPHASIS;
            }
        } else if (c == '`') {
            return Pattern.CODE;
        } else if (c == '-' && nextC == '-') {
            return Pattern.STRIKEOUT;
        } else {
            return null;
        }
    }
}
