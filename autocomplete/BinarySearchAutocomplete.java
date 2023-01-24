package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Binary search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class BinarySearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public BinarySearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        //throw new UnsupportedOperationException("Not implemented yet");
        for (CharSequence term : terms) {
            this.terms.add(term);
        }
        Collections.sort(this.terms, CharSequence::compare);
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        //Create output ArrayList
        List<CharSequence> output = new ArrayList<>();

        //Check null condition
        if (prefix == null) {
            return output;
        }

        //Ensure i is always positive to run next for loop
        int i = Collections.binarySearch(this.terms, prefix, CharSequence::compare);
        if(i < 0) {
            i = -(i++);
        }

        // adds elements that contain prefix to output ArrayList
        for (int j=i; j< this.terms.size(); j++) {
            if (Autocomplete.isPrefixOf(prefix, this.terms.get(j))) {
                output.add(this.terms.get(j));
            }else {
                return output;
            }
        }
        return output;
    }
}
