package md2html;

import markup.Text;
import markup.TextElement;
import md2html.patterns.AbstractPattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TokenStack {
    private final Set<AbstractPattern> patterns;
    private final List<TextBlock> textStack;

    public TokenStack() {
        patterns = new HashSet<>();
        textStack = new ArrayList<>();
        textStack.add(new TextBlock(null));
    }

    public boolean add(AbstractPattern p) {
        boolean success = patterns.add(p);
        if (success) {
            textStack.add(new TextBlock(p));
        }
        return success;
    }

    public void pushDown(AbstractPattern desiredPattern) {
        removeDummyNesting(desiredPattern);

        TextElement desiredToken = desiredPattern.create(textStack.getLast().getTextElementList());
        patterns.remove(desiredPattern);
        textStack.removeLast();
        textStack.getLast().addElement(desiredToken);
    }

    public void addRowText(String str) {
        textStack.getLast().addElement(new Text(str));
    }

    public void removeDummyNesting(AbstractPattern desiredPattern) {
        while (textStack.getLast().getPattern() != desiredPattern) {
            TextBlock toMove = textStack.getLast();
            patterns.remove(toMove.getPattern());
            textStack.removeLast();
            textStack.getLast().addElement(new Text(toMove.getPattern().getSeparator()));
            textStack.getLast().addElements(toMove.getTextElementList());
        }
    }

    public List<TextElement> getFistTokenList() {
        return textStack.getFirst().getTextElementList();
    }
}
