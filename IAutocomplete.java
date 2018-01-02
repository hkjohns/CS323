package trie.autocomplete;

import java.util.List;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public interface IAutocomplete<T>
{
    /**
     * @param prefix the prefix of candidate words to return.
     * @return the list of candidate words for the specific prefix.
     */
    List<String> getCandidates(String prefix);

    /**
     * Memorize the specific candidate word for the specific prefix.
     * @param prefix the prefix.
     * @param candidate the selected candidate for the prefix.
     */
    void pickCandidate(String prefix, String candidate);

    /** @return the previously inserted value if the key already exists; otherwise, the new value. */
    T put(String key, T value);
}