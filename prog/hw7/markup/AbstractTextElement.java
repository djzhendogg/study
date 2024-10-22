package markup;

import java.util.List;

public class AbstractTextElement implements TextElement{
    protected List<TextElement> textElements;
    protected String separator;

    protected AbstractTextElement(final List<TextElement> textElements, final String separator) {
        this.textElements = textElements;
        this.separator = separator;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(separator);
        for (TextElement textElement : textElements) {
            textElement.toMarkdown(stringBuilder);
        }
        stringBuilder.append(separator);
    }

}
