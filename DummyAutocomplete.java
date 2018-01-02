package trie.autocomplete;

import trie.Trie;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class DummyAutocomplete extends Trie<List<String>> implements IAutocomplete<List<String>>
{
    @Override
    public List<String> getCandidates(String prefix)
    {
        // TODO must be modified
        List<String> list = new ArrayList<>();

        list.add("These");
        list.add("are");
        list.add("dummy");
        list.add("candidates");

        return list;
    }

    @Override
    public void pickCandidate(String prefix, String candidate)
    {
        // TODO must be filled
    }
}