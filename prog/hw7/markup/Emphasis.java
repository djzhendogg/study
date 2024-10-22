package markup;

import java.util.List;

public class Emphasis extends AbstractTextElement {
    protected Emphasis(List<TextElement> textElements) {
        super(textElements, "*");
    }
}
