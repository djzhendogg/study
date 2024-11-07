package md2html;

import md2html.markup.*;

import java.io.*;
import java.util.*;

public class MarkDownBlockReader {
    private BufferedReader reader;
    private boolean closed = false;

    public MarkDownBlockReader(InputStream input) throws UnsupportedEncodingException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        input,
                        "UTF8"
                )
        );
    }

    public AbstractTextDecorator nextBlock() throws IOException {
        List<TextElement> rowParagraph;
        AbstractTextDecorator finalParagraph;
        boolean isHeader = false;
        int headerLevel = 0;

        StringBuilder block = readBlock();
        block = toEscapedString(block);
        if (block.toString().startsWith("#")) {
            while (block.charAt(headerLevel) == '#') {
                headerLevel++;
            }
            if (Character.isWhitespace(block.charAt(headerLevel))) {
                isHeader = true;
            }
        }

        if (isHeader) {
            rowParagraph = transformToTextElements(block.substring(headerLevel + 1));
            finalParagraph = new Header(rowParagraph, headerLevel);
        } else {
            rowParagraph = transformToTextElements(block.toString());
            finalParagraph = new Paragraph(rowParagraph);
        }

        return finalParagraph;
    }

    public boolean hasNext() throws IOException {
        return !closed;
    }

    public void close() throws IOException {
        reader.close();
        closed = true;
    }

    private StringBuilder readBlock() throws IOException {
        boolean previosLine = false;
        StringBuilder textBlock = new StringBuilder();

        String line = reader.readLine();

        if (line != null && line.isEmpty()) {
            while (line.isEmpty()) {
                line = reader.readLine();
            }
        }

        while (line != null && !line.isEmpty()) {
            if (previosLine) {
                textBlock.append("\n");
            }
            previosLine = true;
            textBlock.append(line);
            line = reader.readLine();
        }

        if (line == null) {
            close();
        }
        return textBlock;
    }

    private List<TextElement> transformToTextElements(String line) {
        TokenQueue tokenQueue = new TokenQueue();
        int pointer = 0;

        for (int i = 0; i < line.length(); i++) {
            Pattern currentPattern;
            if (line.charAt(i) == '\\') {
                line = line.substring(0, i) + line.substring(i + 1);
                continue;
            }

            currentPattern = takePattern(line, i);

            if (currentPattern == null) {
                continue;
            }

            i += currentPattern.getAdditionalLen();
            tokenQueue.addRowText(line.substring(pointer, i - currentPattern.getAdditionalLen()));
            pointer = i + 1;
            boolean success = tokenQueue.add(currentPattern);
            if (success) {
                continue;
            }
            tokenQueue.pushDown(currentPattern);
        }
        tokenQueue.removeDummyNesting(null);
        tokenQueue.addRowText(line.substring(pointer));
        return tokenQueue.getFistTokenList();
    }

    private Pattern takePattern(String line, int index) {
        Pattern outPattern = null;
        for (Pattern p: Pattern.values()) {
            String toCheck = line.substring(index, Math.min(line.length(), index + p.getLen()));
            if (toCheck.equals(p.getSeparator())) {
                if (outPattern == null) {
                    outPattern = p;
                    continue;
                }
                if (outPattern.getLen() < p.getLen()) {
                    outPattern = p;
                }
            }
        }
        return outPattern;
    }

    private StringBuilder toEscapedString(StringBuilder str) {
        StringBuilder escapedStr = new StringBuilder();
        int start = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '&') {
                escapedStr.append(str, start, i);
                escapedStr.append("&amp;");
                start = i + 1;
            } else if (str.charAt(i) == '<') {
                escapedStr.append(str, start, i);
                escapedStr.append("&lt;");
                start = i + 1;
            } else if (str.charAt(i) == '>') {
                escapedStr.append(str, start, i);
                escapedStr.append("&gt;");
                start = i + 1;
            }
        }
        escapedStr.append(str, start, str.length());
        return escapedStr;
    }
}
