import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatInputBufferWhile {
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
                    char[] buffer = new char[1024];
                    int read = reader.read(buffer);
                    String residue = "";
                    while (read >= 0) {
                        String sequence = new String(buffer, 0, read);
                        sequence = residue + sequence;
                        sequence = sequence.toLowerCase();
                        int start = 0;
                        for (int i = 0; i < sequence.length(); i++) {
                            if (!Character.isLetter(sequence.charAt(i)) && sequence.charAt(i) != '\'' &&
                                Character.getType(sequence.charAt(i)) != Character.DASH_PUNCTUATION) {
                                if (start != i) {
                                    String word = sequence.substring(start, i);
                                    wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
                                }
                                start = i + 1;
                            }
                        }
                        if (start < sequence.length()) {
                            residue = sequence.substring(start);
                        } else {
                            residue = "";
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
}
