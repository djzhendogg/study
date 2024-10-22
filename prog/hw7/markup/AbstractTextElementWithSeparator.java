package markup;

import java.util.List;

public abstract class AbstractTextElementWithSeparator extends AbstractTextElement {
    protected final String separator;

    protected AbstractTextElementWithSeparator(final List<TextElement> textElements, final String separator) {
        super(textElements);
        this.separator = separator;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(separator);
        super.toMarkdown(stringBuilder);
        stringBuilder.append(separator);
    }
}
