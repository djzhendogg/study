package md2html.markup;

import java.util.List;

public class Strong extends AbstractTextDecorator {
    public Strong(List<TextElement> textElements) {
        super(textElements, "__", "#strong[", "]", "<strong>", "</strong>");
    }
}
