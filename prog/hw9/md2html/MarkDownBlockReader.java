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
        // хранит готовый литс тексовых элементов
        List<TextElement> rowParagraph;
        // выделяет блок как хедер, тк по разному парсятся
        boolean isHeader = false;
        // уровень хедера, также используется для скипа элементов
        int headerLevel = 0;
        // хранит итоговый класс разметки заполненный
        AbstractTextDecorator finalParagraph;

        StringBuilder block = readBlock();

//        if (block.isEmpty()) {
//            return null;
//        }
        // обработка содержательных строк
        if (block.toString().startsWith("#")) {
            while (block.charAt(headerLevel) == '#') {
                headerLevel++;
            }
            if (Character.isWhitespace(block.charAt(headerLevel))) {
                isHeader = true;
            }
        }

        if (isHeader) {
            // забирает сабстрингу
            rowParagraph = lineChecker(block.substring(headerLevel + 1));
            finalParagraph = new Header(rowParagraph, headerLevel);
        } else {
            // забирает целую
            rowParagraph = lineChecker(block.toString());
            finalParagraph = new Paragraph(rowParagraph);
        }

        return finalParagraph;
    }

    private StringBuilder readBlock() throws IOException {
        // линия ридера
        String line = reader.readLine();
        // заполняется блоком до разделителей (пустых строк)
        boolean previosLine = false;
        StringBuilder textBlock = new StringBuilder();
        if (line != null && line.isEmpty()) {
            while (line.isEmpty()) {
                line = reader.readLine();
            }
        }
        while (line != null && !line.isEmpty()) {
            if (previosLine) {
                textBlock.append("\n");
            }
            // заменяет спец символы на экранированные
            line = toEscapedString(line);

            // детектит header

            previosLine = true;
            textBlock.append(line);
            line = reader.readLine();
        }

        if (line == null) {
            close();
        }
        return textBlock;
    }

    public boolean hasNext() throws IOException {
        return !closed;
    }

    public void close() throws IOException {
        reader.close();
        closed = true;
    }

    public String toEscapedString(String str) {
        // TODO: стрингбилдером сабстрингу + экранированный чар
        return str.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

    private List<TextElement> lineChecker(String line) {
        // для просмотра в стеке
        Set<Pattern> patterns = new HashSet<>();
        // для хранения вложенных структур
        List<TextBlock> textBlocks = new ArrayList<>();
        // добвляю базовый токен в котором будет хранится итоговая запись
        textBlocks.add(new TextBlock(null));
        // для расинга блоков текста (типа start)
        int pointer = 0;

        for (int i = 0; i < line.length(); i++) {
            // хранит паттерн в текущем проходе
            Pattern nowP;
            // чек экранирования
            if (line.charAt(i) == '\\') {
                // финт для удаления экранирования, плохо что если встретилось два экранировани в строке, съест оба
                line = line.replace("\\", "");
                continue;
            }
            // находит токен прохода, если нет, возвращает null
            // TODO: заменить на проход по токенам
            if (i + 1 < line.length()) {
                nowP = whatToken(line.charAt(i), line.charAt(i + 1));
            } else {
                // TODO: replace '\u0000'
                nowP = whatToken(line.charAt(i), '\u0000');
            }

            if (nowP == null) {
                continue;
            }

            // двигает i чтоб не жрать символы разметки
            i += nowP.getLen();
            // вносит блок теста до/между знаками разметки
            textBlocks.getLast().addElement(new Text(line.substring(pointer, i - nowP.getLen())));
            pointer = i + 1;
            // добавляю патерн в сет, first это типа not exist
            // TODO: выделить в отдельны класс с tokens patterns
            boolean first = patterns.add(nowP);
            if (first) {
                // если встречается первый раз, добавляю в стек
                textBlocks.add(new TextBlock(nowP));
            } else {
                // если уже был, ищу номер токена в листе
                int startTokenId = findToken(textBlocks, nowP);
                // На случай фиктивных токенов, типа *_-* собирает все элемента на уровень *
                for (int j = textBlocks.size() - 1; j > startTokenId; j--) {
                    // верх стека
                    // эл ниже

                    // TODO: сделать while сконца c if на == nowP, когда нахожу, оборачиваю и сохраняю, нет - обычную.
                    // TODO: переделать на дэк
                    textBlocks.get(j - 1).addElements(textBlocks.get(j).getTextElementList());
                    // удаляю верх из патернов, чищу кеш патернов
                    patterns.remove(textBlocks.get(j).getPattern());
                    // удаляю верх из токенов
                    textBlocks.remove(j);
                }
                // -* * - так как * закрыт, переношу все элементы в обертке на уровень ниже
                TextElement nowPatterTextElement = nowP.create(textBlocks.get(startTokenId).getTextElementList());
                textBlocks.get(startTokenId - 1).addElement(nowPatterTextElement);

                patterns.remove(nowP);
                textBlocks.remove(startTokenId);
            }
        }
        // итерации закончились, работаю с ниж уровнем
        // TODO: переделать на вайл выше
        TextBlock finalTextBlock = textBlocks.getFirst();
        // если стек не пуст, за искл нижнего, переношу все вниз
        for (int j = 1; j < textBlocks.size(); j++) {
            TextBlock currentTextBlock = textBlocks.get(j);
            // так как одиночные символы фиктивны, вставляю обратно в текст
            finalTextBlock.addElement(new Text(currentTextBlock.getPattern().getSeparator()));
            // перемещаю токены в последний
            finalTextBlock.addElements(currentTextBlock.getTextElementList());
        }
        // забираю остаток
        finalTextBlock.addElement(new Text(line.substring(pointer)));
        return finalTextBlock.getTextElementList();
    }

    private int findToken(List<TextBlock> textBlocks, Pattern desiredPattern) {
        for (int i = textBlocks.size() - 1; i >= 0; i--) {
            if (textBlocks.get(i).getPattern() == desiredPattern) {
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