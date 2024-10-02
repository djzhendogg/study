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
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    "UTF8"));
                try {
                    char[] buffer = new char[1024];
                    int read = reader.read(buffer);
                    Map<String, Integer> wordMap = new LinkedHashMap<>();
                    StringBuilder word = new StringBuilder();
                    while (read >= 0) {
                        for (int i = 0; i < read; i++) {
                            if (isSplitChar(buffer[i])) {
                                if (!word.isEmpty()) {
                                    wordMap.put(word.toString(),
                                            wordMap.getOrDefault(word.toString(), 0) + 1);
                                }
                            } else {
                                word.append(buffer[i]);
                            }
                        }
                        if (!word.isEmpty()) {
                            wordMap.put(word.toString(), wordMap.getOrDefault(word.toString(), 0) + 1);
                        }
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
    public static boolean isSplitChar(char character) {
        return !Character.isLetter(character) && character != '\'' &&
                Character.getType(character) != Character.DASH_PUNCTUATION;
    }
}
