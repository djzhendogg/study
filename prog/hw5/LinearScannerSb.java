import java.io.*;
import java.nio.charset.StandardCharsets;

public class LinearScannerSb {
    private static final int BUFFER_SIZE = 2048;
    private final BufferedReader reader;
    private char[] buffer = new char[BUFFER_SIZE];
    private int lookupPointer = 0;
    private int readedCharNum;
    private Cache cache = null;
    private char ch = 0xffff;

    public LinearScannerSb(InputStream input) throws UnsupportedEncodingException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        input,
                        "UTF8"
                )
        );
    }

    public LinearScannerSb(String input) throws UnsupportedEncodingException {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        this.reader = new BufferedReader(
                new InputStreamReader(
                        stream,
                        "UTF8"
                )
        );
    }

    public String nextWord() throws IOException {
        return nextItem(false, Pattern.WORD);
    }

    public boolean hasNextWord() throws IOException {
        return !nextItem(true, Pattern.WORD).isEmpty();
    }

    public String nextWordWithDigit() throws IOException {
        return nextItem(false, Pattern.WORDDIGIT);
    }

    public boolean hasNextWordWithDigit() throws IOException {
        return !nextItem(true, Pattern.WORDDIGIT).isEmpty();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextItem(false, Pattern.INTEGER));
    }

    public boolean hasNextInt() throws IOException {
        return isInteger(nextItem(true, Pattern.INTEGER));
    }

    public long nextLong() throws IOException {
        return Long.parseLong(nextItem(false, Pattern.INTEGER));
    }

    public boolean hasNexLong() throws IOException {
        return hasNextInt();
    }

    public boolean hasNext() throws IOException {
        readInBufferIfEmpty();
        return readedCharNum > lookupPointer;
    }

    private void nextChar() throws IOException {
        readInBufferIfEmpty();
        ch = buffer[lookupPointer++];
    }

    public void close() throws IOException {
        reader.close();
    }

    private String nextItem(boolean isLookup, Pattern pattern) throws IOException {
        if (cache != null && cache.pattern() == pattern) {
            String item = cache.value();
            if (!isLookup) {
                lookupPointer = cache.cachePointer();
                ch = buffer[lookupPointer - 1];
                cache = null;
            }
            return item;
        }
        skipWhiteSpace(pattern);
        StringBuilder itemSb = new StringBuilder();
        itemSb.append(ch);
        while (hasNext()) {
            nextChar();
            if (isSplitChar(ch, pattern)) {
                break;
            }
            itemSb.append(ch);
        }
        if (itemSb.isEmpty()) {
            return "";
        }
        if (isLookup) {
            cache = new Cache(pattern, itemSb.toString(), lookupPointer);
            lookupPointer -= itemSb.length();
            ch = buffer[lookupPointer - 1];
        }
        return itemSb.toString();
    }

    private void readInBufferIfEmpty() throws IOException {
        if (lookupPointer >= readedCharNum) {
            readedCharNum = reader.read(buffer);
            lookupPointer = 0;
        }
    }
    
    private void skipWhiteSpace(Pattern pattern) throws IOException {
        while (hasNext()) {
            nextChar();
            if (!isSplitChar(ch, pattern)) {
                break;
            }
        }
    }

    private boolean isLineBreaker() throws IOException {
        if (ch == '\r') {
            if (hasNext() && buffer[lookupPointer] == '\n') {
                nextChar();
            }
            return true;
        } else if (ch == '\n') {
            nextChar();
            return true;
        }
        return false;
    }

    private boolean isInteger(String s) {
        if (s.isEmpty()) {
            return false;
        }
        int startIndex = 0;
        if (s.charAt(0) == '-') {
            if (s.length() == 1) {
                return false;
            }
            startIndex = 1;
        }
        for (int i = startIndex; i < s.length(); i++) {
            if (Character.digit(s.charAt(i), 10) < 0) {
                return false;
            }
        }
        return true;
    }

    private boolean isSplitChar(char character, Pattern pattern) {
        if (pattern == Pattern.WORD) {
            return isSplitCharForWord(character);
        } else if (pattern == Pattern.WORDDIGIT) {
            return isSplitCharForWordWithDigit(character);
        }  else if (pattern == Pattern.INTEGER) {
            return isSplitCharForInt(character);
        } else {
            throw new IllegalArgumentException("not supported argument: " + pattern);
        }
    }

    private boolean isSplitCharForWord(char character) {
        return !Character.isLetter(character) && character != '\'' &&
                Character.getType(character) != Character.DASH_PUNCTUATION;
    }

    private boolean isSplitCharForWordWithDigit(char character) {
        return !Character.isLetterOrDigit(character) && character != '\'' &&
                Character.getType(character) != Character.DASH_PUNCTUATION;
    }

    private boolean isSplitCharForInt(char character) {
        return !Character.isDigit(character) &&
                Character.getType(character) != Character.DASH_PUNCTUATION;
    }

    private enum Pattern {
        WORD,
        INTEGER,
        WORDDIGIT
    }

    private record Cache(Pattern pattern, String value, int cachePointer) {
    }
}

