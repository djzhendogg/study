package md2html;

import md2html.markup.*;

import java.util.List;

public enum Pattern {
    //TODO: переделать в классы
    EMPHASIS_STAR("*") {
        public TextElement create(List<TextElement> textElements) {
            return new Emphasis(textElements);
        }
    },
    EMPHASIS_UNDERSCORE("_") {
        public TextElement create(List<TextElement> textElements) {
            return new Emphasis(textElements);
        }
    },
    STRONG_STAR("**") {
        public TextElement create(List<TextElement> textElements) {
            return new Strong(textElements);
        }
    },
    STRONG_UNDERSCORE("__") {
        public TextElement create(List<TextElement> textElements) {
            return new Strong(textElements);
        }
    },
    STRIKEOUT("--") {
        public TextElement create(List<TextElement> textElements) {
            return new Strikeout(textElements);
        }
    },
    CODE("`") {
        public TextElement create(List<TextElement> textElements) {
            return new Code(textElements);
        }
    };

    private final String separator;

    Pattern(String separator) {
        this.separator = separator;
    }

    public int getAdditionalLen() {
        return getSeparator().length() - 1;
    }
    public int getLen() {
        return getSeparator().length();
    }
    public String getSeparator() {
        return separator;
    }
    public abstract TextElement create(List<TextElement> textElements);
}
