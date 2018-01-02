package trie.autocomplete;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class CandidateCountPair
{
    public String candidate;
    public int    count;

    public CandidateCountPair(String candidate, int count)
    {
        this.candidate = candidate;
        this.count = count;
    }
}