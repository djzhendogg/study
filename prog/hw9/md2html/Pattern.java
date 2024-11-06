package md2html;

import md2html.markup.*;

import java.util.List;
import java.util.Objects;

public enum Pattern {
    //TODO: как правильно назвать
    EMPHASIS_STAR(0, "*") {
        public TextElement create(List<TextElement> textElements) {
            return new Emphasis(textElements);
        }
    },
    EMPHASIS_UNDERSCORE(0, "_") {
        public TextElement create(List<TextElement> textElements) {
            return new Emphasis(textElements);
        }
    },
    STRONG_STAR(1, "**") {
        public TextElement create(List<TextElement> textElements) {
            return new Strong(textElements);
        }
    },
    STRONG_UNDERSCORE(1, "__") {
        public TextElement create(List<TextElement> textElements) {
            return new Strong(textElements);
        }
    },
    STRIKEOUT(1, "--") {
        public TextElement create(List<TextElement> textElements) {
            return new Strikeout(textElements);
        }
    },
    CODE(0, "`") {
        public TextElement create(List<TextElement> textElements) {
            return new Code(textElements);
        }
    };

    private final int len;
    private final String separator;

    private Pattern(int len, String separator) {
        this.len = len;
        this.separator = separator;
    }

    public int getLen() {
        return len;
    }
    public String getSeparator() {
        return separator;
    }
    public abstract TextElement create(List<TextElement> textElements);
}
