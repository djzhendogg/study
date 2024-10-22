package markup;

import java.util.List;

public class Strikeout extends AbstractTextElementWithSeparator {
    public Strikeout(List<TextElement> textElements) {
        super(textElements, "~");
    }
}
