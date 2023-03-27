package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }
    public void add(CharSequence word) {
        overallRoot = add(overallRoot, word, 0);
    }

    private Node add(Node node, CharSequence word, int index) {
        char ch = word.charAt(index);
        if (node == null) {
            node = new Node(ch);
        }
        if (ch < node.data) {
            node.left = add(node.left, word, index);
        } else if (ch > node.data) {
            node.right = add(node.right, word, index);
        } else if (index < word.length() - 1) {
            node.mid = add(node.mid, word, index + 1);
        } else {
            node.isTerm = true;
        }
        return node;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        for (CharSequence term : terms) {
            add(term);
        }
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> results = new ArrayList<>();
        Node node = searchN(overallRoot, prefix, 0);
        if (node == null) {
            return results;
        }
        if (node.isTerm) {
            results.add(prefix.toString());
        }
        getW(node.mid, new StringBuilder(prefix), results);
        return results;
    }

    private Node searchN(Node node, CharSequence prefix, int index) {
        if (node == null) {
            return null;
        }
        char ch = prefix.charAt(index);
        if (ch < node.data) {
            return searchN(node.left, prefix, index);
        } else if (ch > node.data) {
            return searchN(node.right, prefix, index);
        } else if (index < prefix.length() - 1) {
            return searchN(node.mid, prefix, index + 1);
        } else {
            return node;
        }
    }

    private void getW(Node node, StringBuilder prefix, List<CharSequence> results) {
        if (node == null) {
            return;
        }
        getW(node.left, prefix, results);
        if (node.isTerm) {
            results.add(prefix.toString() + node.data);
        }
        getW(node.mid, prefix.append(node.data), results);
        prefix.deleteCharAt(prefix.length() - 1);
        getW(node.right, prefix, results);
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private final char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}
