import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatInput {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Usage: java WordStatInput <input_file_name> <output_file_name>");
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(args[0]),
                "UTF8"));
            Map<String, Integer> wordMap = new LinkedHashMap<>();
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    "UTF8"));
                try {
                    char[] buffer = new char[4];
                    int read = reader.read(buffer);
                    char[] residue = new char[0];
                    int start = 0;
                    int len = 0;
                    while (read >= 0) {
                        char[] fullBuffer;
                        if (residue.length > 0) {
                            fullBuffer = new char[residue.length + read];
                            System.arraycopy(residue, 0, fullBuffer, 0, residue.length);
                            System.arraycopy(buffer, 0, fullBuffer, residue.length, read);
                        } else {
                            fullBuffer = buffer;
                        }
                        for (int i = 0; i < fullBuffer.length; i++) {
                            if (!Character.isLetter(fullBuffer[i]) && fullBuffer[i] != '\'' &&
                                Character.getType(fullBuffer[i]) != Character.DASH_PUNCTUATION) {
                                if (start != i) {
                                    String word = new String(fullBuffer, start, len).toLowerCase();
                                    wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
                                }
                                len = 0;
                                start = i + 1;
                            } else {
                                len++;
                            }
                        }
                        if (len > 0) {
                            residue = new char[len];
                            System.arraycopy(fullBuffer, start, residue, 0, len);
                            len = 0;
                        } else {
                            residue = new char[0];
                        }
                        start = 0;
                        read = reader.read(buffer);
                    }
                    for (String key: wordMap.keySet()) {
                        writer.write(key + " " + wordMap.get(key));
                        writer.newLine();
                    }
                } finally {
                    writer.close();
                }
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found error: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println("Encoding is not supported: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error I/O operation: " + e.getMessage());
        }
    }
}