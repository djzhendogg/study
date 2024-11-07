package md2html;

import markup.Text;
import markup.TextElement;
import md2html.patterns.AbstractPattern;

import java.util.*;

public class TokenStack {
    private final Set<AbstractPattern> patterns;
    private final Stack<TextBlock> textStack;

    public TokenStack() {
        patterns = new HashSet<>();
        textStack = new Stack<>();
        textStack.push(new TextBlock(null));
    }

    public boolean add(AbstractPattern p) {
        boolean success = patterns.add(p);
        if (success) {
            textStack.push(new TextBlock(p));
        }
        return success;
    }

    public void pushDown(AbstractPattern desiredPattern) {
        removeDummyNesting(desiredPattern);

        TextElement desiredToken = desiredPattern.create(textStack.peek().getTextElementList());
        patterns.remove(desiredPattern);
        textStack.pop();
        textStack.peek().addElement(desiredToken);
    }

    public void addRowText(String str) {
        textStack.peek().addElement(new Text(str));
    }

    public void removeDummyNesting(AbstractPattern desiredPattern) {
        while (textStack.peek().getPattern() != desiredPattern) {
            TextBlock toMove = textStack.peek();
            patterns.remove(toMove.getPattern());
            textStack.pop();
            textStack.peek().addElement(new Text(toMove.getPattern().getSeparator()));
            textStack.peek().addElements(toMove.getTextElementList());
        }
    }

    public List<TextElement> getFistTokenList() {
        return textStack.peek().getTextElementList();
    }
}
