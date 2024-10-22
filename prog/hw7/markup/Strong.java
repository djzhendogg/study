package markup;

import java.util.List;

public class Strong extends AbstractTextElementWithSeparator {
    public Strong(List<TextElement> textElements) {
        super(textElements, "__");
    }
}
