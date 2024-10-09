import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatInput {

    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Usage: java WordStatInput <input_file_name> <output_file_name>");
        }
        try {
            MyScannerOptimize reader = new MyScannerOptimize(new FileInputStream(args[0]));
            Map<String, Integer> wordMap = new LinkedHashMap<>();
            try {
                BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                        new FileOutputStream(args[1]),
                        "UTF8"
                    )
                );
                try {
                    while (reader.hasNextWord()) {
                        String word = reader.nextWord().toLowerCase();
                        wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
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
        } catch (IOException e) {
            System.err.println("Error I/O operation: " + e.getMessage());
        }


    }
}
