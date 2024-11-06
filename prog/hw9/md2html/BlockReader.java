package md2html;

import md2html.markup.*;

import java.io.*;
import java.util.*;

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
        List<TextElement> rowParagraph = new ArrayList<>();
        String line = reader.readLine();
        boolean previosLine = false;
        boolean isHeader = false;
        int headerLevel = 0;
        StringBuilder s = new StringBuilder();
        AbstractTextDecorator finalParagraph;

        if (line != null && line.isEmpty()) {
            while (line.isEmpty()) {
                line = reader.readLine();
            }
        }

        while (line != null && !line.isEmpty()) {
            if (previosLine) {
                rowParagraph.add(new Text("\n"));
            }
            line = toEscapedString(line);
            List<TextElement> toIntegrate;
            // for header
            if (!previosLine && line.startsWith("#")) {
                while (line.charAt(headerLevel) == '#') {
                    headerLevel++;
                }
                if (Character.isWhitespace(line.charAt(headerLevel))) {
                    isHeader = true;
                }
            }
            if (!previosLine && isHeader) {
                toIntegrate = lineChecker(line.substring(headerLevel + 1));
            } else {
                toIntegrate = lineChecker(line);
            }

            // for paragraph
            rowParagraph.addAll(toIntegrate);
            previosLine = true;
            line = reader.readLine();
        }

        if (line == null) {
            close();
        }
        if (isHeader) {
            finalParagraph = new Header(rowParagraph, headerLevel);
        } else {
            finalParagraph = new Paragraph(rowParagraph);
        }
        finalParagraph.toHtml(s);
        if (s.isEmpty()) {
            return null;
        } else {
            return s;
        }
    }

    public boolean isHeaderStart(String line) {
        if (line.startsWith("#")) {
            int p = 0;
            while (line.charAt(p) == '#') {
                p++;
            }
            if (Character.isWhitespace(line.charAt(p))) {
                return true;
            }
        }
        return false;
    }

    public boolean hasNext() throws IOException {
        return !closed;
    }

    public void close() throws IOException {
        reader.close();
        closed = true;
    }

    public String toEscapedString(String str) {
//        TODO: экранированние
        return str.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\\", "");
    }

    public List<TextElement> lineChecker(String line) {
        Set<Pattern> patterns = new LinkedHashSet<>();
        List<Token> tokens = new ArrayList<>();
        tokens.add(new Token(null, 0));
        int pointer = 0;
        Pattern nowP;
        for (int i = 0; i < line.length(); i++) {
            if (i + 1 < line.length()) {
                nowP = whatToken(line.charAt(i), line.charAt(i + 1));
            } else {
                // TODO: replace '\u0000'
                nowP = whatToken(line.charAt(i), '\u0000');
            }

            if (nowP != null) {
                i += nowP.getLen();
                tokens.getLast().update(new Text(line.substring(pointer, i - nowP.getLen())));
                pointer = i + 1;
                boolean first = patterns.add(nowP);
                if (first) {
                    tokens.add(new Token(nowP, i));
                } else {
                    int startTokenId = findToken(tokens, nowP);
                    for (int j = tokens.size() - 1; j > startTokenId; j--) {
                        Token currentToken = tokens.get(j);
                        Token previosToken = tokens.get(j - 1);
                        for (int k = 0; k > currentToken.getLength(); k++) {
                            previosToken.update(currentToken.getTextElement(k));
                        }
                        patterns.remove(currentToken.getPattern());
                        tokens.remove(j);
                    }
                    tokens.get(startTokenId - 1).update(nowP.create(tokens.get(startTokenId).getTextElementList()));
                    patterns.remove(nowP);
                    tokens.remove(startTokenId);
                }
            }
        }
        Token finalToken = tokens.getFirst();
        for (int j = 1; j < tokens.size(); j++) {
            Token currentToken = tokens.get(j);
            finalToken.update(new Text(currentToken.getPattern().getSeparator()));
            for (int k = 0; k < currentToken.getLength(); k++) {
                finalToken.update(currentToken.getTextElement(k));
            }
            patterns.remove(currentToken.getPattern());
        }
        finalToken.update(new Text(line.substring(pointer)));
        return tokens.getFirst().getTextElementList();
    }

    public TextElement makeText(String s) {
        return new Text(s);
    }

    public int findToken(List<Token> tokens, Pattern desiredPattern) {
        for (int i = tokens.size() - 1; i >= 0; i--) {
            if (tokens.get(i).getPattern() == desiredPattern) {
                return i;
            }
        }
        return 0;
    }

    public Pattern whatToken(char c, char nextC) {
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
}
