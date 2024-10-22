package markup;

import java.util.List;

public class Paragraph implements TextElement {
    List<TextElement> textElements;

    protected Paragraph(List<TextElement> textElements) {
        this.textElements = textElements;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        for (TextElement textElement : textElements) {
            textElement.toMarkdown(stringBuilder);
        }
    }
}
