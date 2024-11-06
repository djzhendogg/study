package md2html;

import md2html.markup.Emphasis;
import md2html.markup.Strikeout;
import md2html.markup.Strong;
import md2html.markup.TextElement;

import java.util.List;

public enum Pattern {
    EMPHASIS(1) {
        public TextElement create(List<TextElement> textElements) {
            return new Emphasis(textElements);
        }
    },
    STRONG(2) {
        public TextElement create(List<TextElement> textElements) {
            return new Strong(textElements);
        }
    },
    STRIKEOUT(2) {
        public TextElement create(List<TextElement> textElements) {
            return new Strikeout(textElements);
        }
    },
    CODE(1) {
        public TextElement create(List<TextElement> textElements) {
            return new Strikeout(textElements);
        }
    };

    private final int len;

    private Pattern(int len) {
        this.len = len;
    }

    public int getLen() {
        return len;
    }
}
