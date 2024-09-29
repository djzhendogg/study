import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatInputReadLine {
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
                    String line = reader.readLine();
                    while (line != null) {
                        line = line.toLowerCase();
                        int start = 0;
                        for (int i = 0; i < line.length(); i++) {
                            if (!Character.isLetter(line.charAt(i)) && line.charAt(i) != '\'' &&
                                Character.getType(line.charAt(i)) != Character.DASH_PUNCTUATION) {
                                if (start != i) {
                                    String word = line.substring(start, i);
                                    wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
                                }
                                start = i + 1;
                            }
                        }
                        if (start < line.length()) {
                            String word = line.substring(start);
                            if (wordMap.containsKey(word)) {
                                wordMap.replace(word, wordMap.get(word) + 1);
                            } else {
                                wordMap.put(word, 1);
                            }
                        }
                        line = reader.readLine();
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
