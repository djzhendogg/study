package markup;

import java.util.List;

public class Strong extends AbstractTextElement {

    protected Strong(List<TextElement> textElements) {
        super(textElements, "__");
    }
}
