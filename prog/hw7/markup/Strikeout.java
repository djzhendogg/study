package markup;

import java.util.List;

public class Strikeout extends AbstractTextElement {

    protected Strikeout(List<TextElement> textElements) {
        super(textElements, "~");
    }
}
