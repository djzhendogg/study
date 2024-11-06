package md2html;

import md2html.markup.*;

import java.io.*;
import java.util.*;

public class BlockReader {
    private BufferedReader reader;
    private boolean closed = false;

    public BlockReader(InputStream input) throws UnsupportedEncodingException {
        this.reader = new BufferedReader(
                new InputStreamReader(
                        input,
                        "UTF8"
                )
        );
    }

    public StringBuilder nextBlock() throws IOException {
        // хранит готовый литс тексовых элементов
        List<TextElement> rowParagraph;
        // линия ридера
        String line = reader.readLine();
        // для добавления \n в конце
        // TODO: есть ощущение, что можно добвлять всегда
        boolean previosLine = false;
        // выделяет блок как хедер, тк по разному парсятся
        boolean isHeader = false;
        // уровень хедера, также используется для скипа элементов
        int headerLevel = 0;
        // заполняется стрингой в HTML
        StringBuilder s = new StringBuilder();
        // заполняется блоком до разделителей (пустых строк)
        StringBuilder bigLine = new StringBuilder();
        // хранит итоговый класс разметки заполненный
        AbstractTextDecorator finalParagraph;

        // читает до содержательнойц строки
        if (line != null && line.isEmpty()) {
            while (line.isEmpty()) {
                line = reader.readLine();
            }
        }

        // обработка содержательных строк
        while (line != null && !line.isEmpty()) {
            if (previosLine) {
                bigLine.append("\n");
            }
            // заменяет спец символы на экранированные
            line = toEscapedString(line);

            // детектит header
            if (!previosLine && line.startsWith("#")) {
                while (line.charAt(headerLevel) == '#') {
                    headerLevel++;
                }
                if (Character.isWhitespace(line.charAt(headerLevel))) {
                    isHeader = true;
                }
            }
            previosLine = true;
            bigLine.append(line);
            line = reader.readLine();
        }
        if (isHeader) {
            // забирает сабстрингу
            rowParagraph = lineChecker(bigLine.substring(headerLevel + 1));
        } else {
            // забирает целую
            rowParagraph = lineChecker(bigLine.toString());
        }

        if (line == null) {
            close();
        }
        if (isHeader) {
            // кастит к заголовку
            finalParagraph = new Header(rowParagraph, headerLevel);
        } else {
            // кастит к параграфу
            finalParagraph = new Paragraph(rowParagraph);
        }
        finalParagraph.toHtml(s);
        if (s.isEmpty()) {
            return null;
        } else {
            return s;
        }
    }

    public boolean hasNext() throws IOException {
        return !closed;
    }

    public void close() throws IOException {
        reader.close();
        closed = true;
    }

    public String toEscapedString(String str) {
        return str.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private List<TextElement> lineChecker(String line) {
        // для просмотра в стеке
        Set<Pattern> patterns = new LinkedHashSet<>();
        // для хранения вложенных структур
        List<Token> tokens = new ArrayList<>();
        // добвляю базовый токен в котором будет хранится итоговая запись
        tokens.add(new Token(null, 0));
        // для расинга блоков текста (типа start)
        int pointer = 0;
        // хранит паттерн в текущем проходе
        Pattern nowP;

        for (int i = 0; i < line.length(); i++) {
            // чек экранирования
            if (line.charAt(i) == '\\') {
                // финт для удаления экранирования, плохо что если встретилось два экранировани в строке, съест оба
                line = line.replace("\\", "");
                continue;
            }
            // находит токен прохода, если нет, возвращает null
            if (i + 1 < line.length()) {
                nowP = whatToken(line.charAt(i), line.charAt(i + 1));
            } else {
                // TODO: replace '\u0000'
                nowP = whatToken(line.charAt(i), '\u0000');
            }

            if (nowP != null) {
                // двигает i чтоб не жрать символы разметки
                i += nowP.getLen();
                // вносит блок теста до/между знаками разметки
                tokens.getLast().update(new Text(line.substring(pointer, i - nowP.getLen())));
                pointer = i + 1;
                // добавляю патерн в сет, first это типа not exist
                boolean first = patterns.add(nowP);
                if (first) {
                    // если встречается первый раз, добавляю в стек
                    tokens.add(new Token(nowP, i));
                } else {
                    // если уже был, ищу номер токена в листе
                    int startTokenId = findToken(tokens, nowP);
                    // На случай фиктивных токенов, типа *_-* собирает все элемента на уровень *
                    for (int j = tokens.size() - 1; j > startTokenId; j--) {
                        // верх стека
                        Token currentToken = tokens.get(j);
                        // эл ниже
                        Token previosToken = tokens.get(j - 1);
                        // переношу из верха вниз
                        for (int k = 0; k > currentToken.getLength(); k++) {
                            previosToken.update(currentToken.getTextElement(k));
                        }
                        // удаляю верх из патернов, чищу кеш патернов
                        patterns.remove(currentToken.getPattern());
                        // удаляю верх из токенов
                        tokens.remove(j);
                    }
                    // -* * - так как * закрыт, переношу все элементы в обертке на уровень ниже
                    tokens.get(startTokenId - 1).update(nowP.create(tokens.get(startTokenId).getTextElementList()));

                    patterns.remove(nowP);
                    tokens.remove(startTokenId);
                }
            }
        }
        // итерации закончились, работаю с ниж уровнем
        Token finalToken = tokens.getFirst();
        // если стек не пуст, за искл нижнего, переношу все вниз
        for (int j = 1; j < tokens.size(); j++) {
            Token currentToken = tokens.get(j);
            // так как одиночные символы фиктивны, вставляю обратно в текст
            finalToken.update(new Text(currentToken.getPattern().getSeparator()));
            // перемещаю токены в последний
            for (TextElement el: currentToken.getTextElementList()) {
                finalToken.update(el);
            }
        }
        // забираю остаток
        finalToken.update(new Text(line.substring(pointer)));
        return tokens.getFirst().getTextElementList();
    }

    private int findToken(List<Token> tokens, Pattern desiredPattern) {
        for (int i = tokens.size() - 1; i >= 0; i--) {
            if (tokens.get(i).getPattern() == desiredPattern) {
                return i;
            }
        }
        return 0;
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
}
