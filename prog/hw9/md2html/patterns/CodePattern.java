package md2html.patterns;

import md2html.markup.Code;
import md2html.markup.Emphasis;
import md2html.markup.TextElement;

import java.util.List;

public class CodePattern extends AbstractPattern{
    public CodePattern() {
        super("`");
    }

    @Override
    public TextElement create(List<TextElement> textElements) {
        return new Code(textElements);
    }
}
