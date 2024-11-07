package md2html.patterns;

import md2html.markup.Code;
import md2html.markup.Strikeout;
import md2html.markup.TextElement;

import java.util.List;

public class StrikeoutPattern extends AbstractPattern{
    public StrikeoutPattern() {
        super("--");
    }

    @Override
    public TextElement create(List<TextElement> textElements) {
        return new Strikeout(textElements);
    }
}
