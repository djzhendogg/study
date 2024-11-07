package md2html;

import md2html.markup.Text;
import md2html.markup.TextElement;
import md2html.patterns.AbstractPattern;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TokenQueue {
    private final Set<AbstractPattern> patterns;
    private final List<TextBlock> textQueue;

    protected TokenQueue() {
        patterns = new HashSet<>();
        textQueue = new ArrayList<>();
        textQueue.add(new TextBlock(null));
    }

    public boolean add(AbstractPattern p) {
        boolean success = patterns.add(p);
        if (success) {
            textQueue.add(new TextBlock(p));
        }
        return success;
    }

    public void pushDown(AbstractPattern desiredPattern) {
        int startTokenId = findToken(desiredPattern);

        removeDummyNesting(desiredPattern);

        TextElement desiredToken = desiredPattern.create(textQueue.get(startTokenId).getTextElementList());
        textQueue.get(startTokenId - 1).addElement(desiredToken);

        patterns.remove(desiredPattern);
        textQueue.remove(startTokenId);
    }

    public void addRowText(String str) {
        textQueue.getLast().addElement(new Text(str));
    }

    public void removeDummyNesting(AbstractPattern desiredPattern) {
        int index = textQueue.size() - 1;

        while (textQueue.get(index).getPattern() != desiredPattern) {
            TextBlock toMove = textQueue.get(index);
            textQueue.get(index - 1).addElement(new Text(toMove.getPattern().getSeparator()));
            textQueue.get(index - 1).addElements(toMove.getTextElementList());
            patterns.remove(toMove.getPattern());
            textQueue.remove(index);
            index--;
        }
    }

    public List<TextElement> getFistTokenList() {
        return textQueue.getFirst().getTextElementList();
    }

    private int findToken(AbstractPattern desiredPattern) {
        for (int i = textQueue.size() - 1; i >= 0; i--) {
            if (textQueue.get(i).getPattern() == desiredPattern) {
                return i;
            }
        }
        return 0;
    }
}
