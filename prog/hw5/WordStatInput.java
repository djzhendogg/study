import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class WordStatInput {
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
                    for (String key : wordMap.keySet()) {
                        writer.write(key + " " + wordMap.get(key));
                        writer.newLine();
                    }
                } catch (IOException e) {
                    System.err.println("Error I/O operation in read functions: " + Arrays.toString(e.getStackTrace()));
                } finally {
                    writer.close();
                }
            } catch (FileNotFoundException e) {
                System.err.println("Write file not found: " + Arrays.toString(e.getStackTrace()));
            } catch (UnsupportedEncodingException e) {
                System.err.println("Writer encoding is not supported: " + Arrays.toString(e.getStackTrace()));
            } catch (IOException e) {
                System.err.println("Error I/O operation for writer: " + Arrays.toString(e.getStackTrace()));
            } finally {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Read file found error: " + Arrays.toString(e.getStackTrace()));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Reader encoding is not supported: " + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            System.err.println("Error I/O operation for reader: " + Arrays.toString(e.getStackTrace()));
        }
    }
}
