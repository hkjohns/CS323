package trie.autocomplete;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class RunAutocomplete
{
    private IAutocomplete<?> auto_complete;

    public RunAutocomplete()
    {
        auto_complete = new JohnsonAutocomplete();
    }

    public RunAutocomplete(IAutocomplete<?> auto)
    {
        auto_complete = auto;
    }

    public void initDictionary(InputStream in) throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        int count;

        for (count=0; (line = reader.readLine()) != null; count++)
            auto_complete.put(line.trim(), null);

        System.out.println("# of initial words: "+count);
    }

    public void run() throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        List<String> candidates;
        String prefix, pick;

        do
        {
            System.out.print("Enter a prefix: ");
            prefix = reader.readLine();

            // TODO: print out the top 10 candidates
            candidates = auto_complete.getCandidates(prefix);
            System.out.println(candidates.stream().collect(Collectors.joining("\n")));

            System.out.print("\nPick: ");
            pick = reader.readLine();

            // TODO: update your Trie with this pick.
            auto_complete.pickCandidate(prefix, pick);
            System.out.println("\""+ pick +"\" is learned.\n");
        }
        while (true);
    }

    static public void main(String[] args) throws Exception
    {
        // change the path to a local file if you have no internet connection
        final String filepath = "https://raw.githubusercontent.com/emory-courses/cs323/master/dat/word-list.txt.gz";
        InputStream in = filepath.startsWith("http") ? new URL(filepath).openStream() : new FileInputStream(filepath);
        GZIPInputStream zin = new GZIPInputStream(in);

        // change the DummyAutocomplete to your class (e.g., ChoiAutocomplete)
        IAutocomplete<?> auto = new JohnsonAutocomplete();
        RunAutocomplete run = new RunAutocomplete(auto);
        run.initDictionary(zin);
        run.run();
    }
}