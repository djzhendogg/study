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
            // чек экранирования
            if (line.charAt(i) == '\\') {
                line = line.substring(0, i) + line.substring(i + 1);
                continue;
            }
            // находит токен прохода, если нет, возвращает null
            // TODO: заменить на проход по токенам
            if (i + 1 < line.length()) {
                currentPattern = whatToken(line.charAt(i), line.charAt(i + 1));
            } else {
                // TODO: replace '\u0000'
                currentPattern = whatToken(line.charAt(i), '\u0000');
            }

            if (currentPattern == null) {
                continue;
            }

            i += currentPattern.getLen();
            tokenQueue.addRowText(line.substring(pointer, i - currentPattern.getLen()));
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

    private Pattern whatToken(char c, char nextC) {
        if (c == '*') {
            if (nextC == '*') {
                return Pattern.STRONG_STAR;
            } else {
                return Pattern.EMPHASIS_STAR;
            }
        } else if (c == '_') {
            if (nextC == '_') {
                return Pattern.STRONG_UNDERSCORE;
            } else {
                return Pattern.EMPHASIS_UNDERSCORE;
            }
        } else if (c == '`') {
            return Pattern.CODE;
        } else if (c == '-' && nextC == '-') {
            return Pattern.STRIKEOUT;
        } else {
            return null;
        }
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
