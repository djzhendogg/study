import java.io.*;

public class WriterReaderExample {
    public static void main(String[] args) {
        try {
            Reader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(args[0]),
                "UTF8"));
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(args[1]),
                    "UTF8"));
                try {
                    char[] buffer = new char[1024];
                    int read = reader.read(buffer);
                    System.out.println(read);
                    while (read >= 0) {
                        writer.write("---------");
                        writer.newLine();
                        writer.write(new String(buffer, 0, read));
                        writer.newLine();
                        writer.write("---------");
//                        System.out.println(buffer);
                        //                    System.out.print(new String(buffer, 0, read));
                        read = reader.read(buffer);
                    }
                } finally {
                    writer.close();
                }
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
