package md2html;

import java.io.*;

public class BlockReader {
    BufferedReader reader;
    boolean closed = false;

    public BlockReader(InputStream input) throws UnsupportedEncodingException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        input,
                        "UTF8"
                )
        );
    }

    public StringBuilder nextBlock() throws IOException {
        StringBuilder block = new StringBuilder();
        String line = reader.readLine();
        boolean previosLine = false;

        if (line != null && line.isEmpty()) {
            while (line.isEmpty()) {
                line = reader.readLine();
            }
        }

        while (line != null && !line.isEmpty()) {
            if (previosLine) {
                block.append('\n');
            }
            block.append(line);
            line = reader.readLine();
            previosLine = true;
        }

        if (line == null) {
            close();
        }

        if (block.isEmpty()) {
            return null;
        } else {
            return block;
        }
    }

    public boolean hasNext() throws IOException {
        return !closed;
    }

    public void close() throws IOException {
        reader.close();
        closed = true;
    }
}
