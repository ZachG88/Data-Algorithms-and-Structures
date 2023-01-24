package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sequential search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class SequentialSearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public SequentialSearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        //throw new UnsupportedOperationException("Not implemented yet");
        for(CharSequence term : terms) {
            this.terms.add(term);
        }
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> output = new ArrayList<>();
        if(prefix == null) {
            return output;
        }

        for (CharSequence term : this.terms) {
            if( Autocomplete.isPrefixOf(prefix, term)) {
                output.add(term);
            }
        }

        return output;
        //throw new UnsupportedOperationException("Not implemented yet");
    }
}
