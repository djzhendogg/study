package markup;

import java.util.List;

public abstract class AbstractTextElement implements TextElement {
    List<TextElement> textElements;

    protected AbstractTextElement(List<TextElement> textElements) {
        this.textElements = textElements;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        for (TextElement textElement : textElements) {
            textElement.toMarkdown(stringBuilder);
        }
    }
}
