package md2html;

import md2html.markup.TextElement;

import java.util.ArrayList;
import java.util.List;

public class TextBlock {
    private final List<TextElement> textElements = new ArrayList<>();
    private final Pattern pattern;

    public TextBlock(Pattern pattern) {
        this.pattern = pattern;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void addElement(TextElement el) {
        textElements.add(el);
    }

    public void addElements(List<TextElement> elements) {
        textElements.addAll(elements);
    }

    public int getLength() {
        return textElements.size();
    }

    public List<TextElement> getTextElementList() {
        return textElements;
    }
}