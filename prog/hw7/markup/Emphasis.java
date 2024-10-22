package markup;

import java.util.List;

public final class Emphasis extends AbstractTextElementWithSeparator {
    public Emphasis(List<TextElement> textElements) {
        super(textElements, "*");
    }
}
