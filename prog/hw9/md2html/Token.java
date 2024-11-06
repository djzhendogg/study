package md2html;

import md2html.markup.TextElement;

import java.util.ArrayList;
import java.util.List;

public class Token {
    private List<TextElement> textElements = new ArrayList<>();
    private Pattern pattern;
    private int start;

    public Token(Pattern pattern, int start) {
        this.pattern = pattern;
        this.start = start;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public int getStart() {
        return start;
    }

    public void update(TextElement el) {
        textElements.add(el);
    }

    public TextElement getTextElement(int index) {
        return textElements.get(index);
    }

    public int getLength() {
        return textElements.size();
    }

    public List<TextElement> getTextElementList() {
        return textElements;
    }
}
