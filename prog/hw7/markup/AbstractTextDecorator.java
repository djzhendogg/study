package markup;

import java.util.List;

public abstract class AbstractTextDecorator extends AbstractTextIterator {
    protected final String separator;
    protected final String typstStart;
    protected final String typstEnd;

    protected AbstractTextDecorator(
            final List<TextElement> textElements,
            final String separator,
            final String typstStart,
            final String typstEnd
    ) {
        super(textElements);
        this.separator = separator;
        this.typstStart = typstStart;
        this.typstEnd = typstEnd;
    }

    @Override
    public void toMarkdown(StringBuilder stringBuilder) {
        stringBuilder.append(separator);
        super.toMarkdown(stringBuilder);
        stringBuilder.append(separator);
    }

    @Override
    public void toTypst(StringBuilder stringBuilder) {
        stringBuilder.append(typstStart);
        super.toTypst(stringBuilder);
        stringBuilder.append(typstEnd);
    }
}
