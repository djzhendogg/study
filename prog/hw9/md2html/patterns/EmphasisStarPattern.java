package md2html.patterns;

import md2html.markup.Emphasis;
import md2html.markup.TextElement;

import java.util.List;

public class EmphasisStarPattern extends AbstractPattern {
    public EmphasisStarPattern() {
        super("*");
    }

    @Override
    public TextElement create(List<TextElement> textElements) {
        return new Emphasis(textElements);
    }
}
