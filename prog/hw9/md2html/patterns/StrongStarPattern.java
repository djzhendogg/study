package md2html.patterns;

import md2html.markup.Code;
import md2html.markup.Strong;
import md2html.markup.TextElement;

import java.util.List;

public class StrongStarPattern extends AbstractPattern {
    public StrongStarPattern() {
        super("**");
    }

    @Override
    public TextElement create(List<TextElement> textElements) {
        return new Strong(textElements);
    }
}
