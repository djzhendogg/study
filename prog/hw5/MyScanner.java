import java.io.*;
import java.nio.charset.StandardCharsets;

public class MyScanner {
    private static final int BUFFER_SIZE = 2048;
    private final BufferedReader reader;
    private char[] buffer = new char[BUFFER_SIZE];
    int realPointer = 0;
    int fakePointer = 0;
    int readedCharNum;

    public MyScanner(InputStream input) throws UnsupportedEncodingException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        input,
                        "UTF8"
                )
        );
    }
    public MyScanner(String input) throws UnsupportedEncodingException {
        InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        this.reader = new BufferedReader(
                new InputStreamReader(
                        stream,
                        "UTF8"
                )
        );
    }

    private void readInBuffer() throws IOException {
        if (realPointer < fakePointer) {
            int offset = buffer.length - realPointer;
            char[] newBuffer = new char[offset + BUFFER_SIZE];
            readedCharNum = reader.read(newBuffer, offset, BUFFER_SIZE);
            System.arraycopy(buffer, realPointer, newBuffer, 0, offset);
            buffer = newBuffer;
            fakePointer = offset;
            realPointer = 0;
            if (readedCharNum < 0) {
                readedCharNum = offset;
            } else {
                readedCharNum += offset;
            }
        } else {
            buffer = new char[BUFFER_SIZE];
            readedCharNum = reader.read(buffer);
            realPointer = 0;
            fakePointer = 0;
        }
    }

    private char nextChar() throws IOException {
        if (fakePointer >= readedCharNum) {
            readInBuffer();
        }
        return buffer[fakePointer++];
    }


    public String nextIntItem(boolean fake) throws IOException {
        StringBuilder line = new StringBuilder();
        boolean previosSpace = true;
        char c = nextChar();
        while (fakePointer <= readedCharNum) {
            if (!isSplitCharForInt(c)) {
                if (previosSpace) {
                    previosSpace = false;
                }
                line.append(c);
            } else if (!previosSpace){
                break;
            }
            c = nextChar();
        }

        if (fake) {
            fakePointer = realPointer;
        } else {
            realPointer = fakePointer;
        }
        return line.toString();
    }

    public String nextLine() throws IOException {
        StringBuilder line = new StringBuilder();
        char c = nextChar();
        while (fakePointer <= readedCharNum) {
            if (Character.getType(c) == Character.CONTROL) {
                break;
            }
            line.append(c);
            c = nextChar();
        }
        realPointer = fakePointer;
        return line.toString();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(nextIntItem(false));
    }

    public String nextWordItem(boolean fake) throws IOException {
        StringBuilder line = new StringBuilder();
        boolean previosSpace = true;
        char c = nextChar();
        while (fakePointer <= readedCharNum) {
            if (!isSplitCharForWord(c)) {
                if (previosSpace) {
                    previosSpace = false;
                }
                line.append(c);
            } else if (!previosSpace){
                break;
            }
            c = nextChar();
        }
        if (fake) {
            fakePointer = realPointer;
        } else {
            realPointer = fakePointer;
        }
        return line.toString();
    }

    public String nextWord() throws IOException {
        return nextWordItem(false);
    }

    public boolean hasNextWord() throws IOException {
        String toCheck = nextWordItem(true);
        return !toCheck.isEmpty();
    }

    public boolean hasNext() throws IOException {
        if (fakePointer == readedCharNum) {
            readInBuffer();
        }
        return readedCharNum >= fakePointer;
    }

    public boolean hasNextInt() throws IOException {
        String toCheck = nextIntItem(true);
        return isInteger(toCheck);
    }

    public void close() throws IOException {
        reader.close();
    }

    public static boolean isInteger(String s) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i), 10) < 0) return false;
        }
        return true;
    }

    private boolean isSplitCharForWord(char character) {
        return !Character.isLetter(character) && character != '\'' &&
                Character.getType(character) != Character.DASH_PUNCTUATION;
    }

    private boolean isSplitCharForInt(char character) {
        return !Character.isDigit(character) && Character.getType(character) != Character.DASH_PUNCTUATION;
    }

}