import java.io.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class WsppEvenDigits {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Usage: java WsppEvenDigits <input_file_name> <output_file_name>");
        }
        try {
            Scanner reader = new Scanner(new FileInputStream(args[0]));
            Map<String, IntList> wordMap = new LinkedHashMap<>();
            try {
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(
                                new FileOutputStream(args[1]),
                                "UTF8"
                        )
                );
                try {
                    int lineState = 0;
                    while (reader.hasNext()) {
                        String line = reader.nextLine();
                        Scanner stringScanner = new Scanner(line);
                        lineState++;
                        int totalState = 1;
                        while (stringScanner.hasNextWordWithDigit()) {
                            String word = stringScanner.nextWordWithDigit().toLowerCase();
                            IntList statList = wordMap.getOrDefault(word, new IntList());
                            statList.add(totalState);
                            statList.add(lineState);
                            wordMap.put(word, statList);
                            totalState++;
                        }
                    }
                    for (Map.Entry<String, IntList> entry : wordMap.entrySet()) {
                        IntList statInfo = entry.getValue();
                        writer.write(entry.getKey() + " " + statInfo.size() / 2);
                        int line = 1;
                        int occurency = 0;
                        for (int i = 0; i < statInfo.size() - 1; i += 2) {
                            if (line == statInfo.get(i + 1)) {
                                occurency++;
                            } else {
                                line = statInfo.get(i + 1);
                                occurency = 1;
                            }
                            if (occurency % 2 == 0) {
                                writer.write(" " + statInfo.get(i));
                            }
                        }
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
