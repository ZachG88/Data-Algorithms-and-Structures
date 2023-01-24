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
    private Node overallRoot;   // root of TST
    private int n;              // size of TST

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    public void put(CharSequence key) {
        if(key == null) {
            throw new IllegalArgumentException("calls put() with null key");
        }
        if(!contains(key)) this.n++;
        this.overallRoot = put(this.overallRoot, key, 0);
    }

    public boolean contains(CharSequence key) {
        if(key == null) {
            throw new IllegalArgumentException("argument to contains() is null");
        }
        return get(key);
    }

    public boolean get(CharSequence key) {
        if(key == null) {
            throw new IllegalArgumentException("calls get() with null argument");
        }
        if(key.length() == 0) {
            throw new IllegalArgumentException("key must at least be length 1");
        }
        Node x = get(this.overallRoot, key, 0);
        if(x == null) {
            return false;
        }
        return x.isTerm;
    }

    private Node get(Node x, CharSequence key, int d) {
        if(x == null) {
            return null;
        }
        if(key.length() == 0) {
            throw new IllegalArgumentException("key must at least be length 1");
        }
        char c = key.charAt(d);

        if(c < x.data) {
            return get(x.left, key, d);
        }
        else if(c > x.data) {
            return get(x.right, key, d);
        }
        else if(d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        }
        else {
            return x;
        }

    }

    private Node put(Node x, CharSequence key, int d) {
        char c = key.charAt(d);
        if (x == null) {
            x = new Node(c);
        }
        if(c < x.data) {
            x.left = put(x.left, key, d);
        }
        else if(c > x.data) {
            x.right = put(x.right, key, d);
        }
        else if(d < key.length() - 1) {
            x.mid   = put(x.mid,   key, d+1);
        }
        else {
            x.isTerm   = true;
        }
        return x;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        for (CharSequence term : terms) {
            put(term);
        }
    }


    public List<CharSequence> keysWithPrefix(CharSequence prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("calls keysWithPrefix() with null argument");
        }
        List<CharSequence> matches = new ArrayList<>();
        Node x = get(this.overallRoot, prefix, 0);
        if (x == null) return matches;
        if (x.isTerm) matches.add(prefix);
        collect(x.mid, new StringBuilder(prefix), matches);
        return matches;
    }

    // all keys in subtrie rooted at x with given prefix
    private void collect(Node x, StringBuilder prefix, List<CharSequence> matches) {
        if (x == null) return;
        collect(x.left,  prefix, matches);
        if (x.isTerm) matches.add(prefix.toString() + x.data);
        collect(x.mid,   prefix.append(x.data), matches);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, matches);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        //throw new UnsupportedOperationException("Not implemented yet");
        List<CharSequence> result = new ArrayList<>();
        if (prefix == null || prefix.length() == 0) {
            return result;
        }
        return result = keysWithPrefix(prefix);
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
