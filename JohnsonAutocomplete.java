//
// BY: Hannah Johnson, netID: hkjohns
// THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
// A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - HANNAH JOHNSON
//

package trie.autocomplete;
import trie.Trie;
import trie.TrieNode;

import java.util.*;


public class JohnsonAutocomplete extends Trie<List<String>> implements IAutocomplete<List<String>> {

    /**
     * @param prefix the prefix of candidate words to return.
     * @return the list of candidate words for the specific prefix.
     */
    public List<String> getCandidates(String prefix) {

        // if prefix isn't in dictionary, add it
        TrieNode<List<String>> node = find(prefix);
        if (node == null) {
            // introduce node to trie
            addToTrie(prefix);
           // find(prefix).setEndState(false); //don't do this
            return new ArrayList<>();
        }
        if (!node.isEndState())                 // ADDED IN
            node.setEndState(true);
        // if node has a list of 20 candidates already, the list can just be returned
        // no need for BFS if it's been done before
        if (node.getValue() != null && node.getValue().size() >= 20) {
            return node.getValue();
        }
        else {
           // BFS must be done 20 times (max) to fill prefix's candidate list
            node.setValue(bfs(node));
            return node.getValue();
        }
    }

    /**
     * Memorize the specific candidate word for the specific prefix.
     * @param prefix the prefix.
     * @param candidate the selected candidate for the prefix.
     */
    public void pickCandidate(String prefix, String candidate) {                // USE ARRAY DEQUE TO ADD TO FRONT
        // if candidate is not in trie, add it                                  // AND BACK FASTER (removeLast() & addFirst())
        List<String> list;                                                      // this'll automatically shift other elements over
        if (find(candidate) == null)
            addToTrie(candidate);
        if (find(prefix).getValue() == null) {
            list = new ArrayList<>(20);
            list.add(0,candidate);
            find(prefix).setValue(list);
        }
        else if (find(prefix).getValue().size() == 1) {
            list = new ArrayList<>(20);
            list.add(0,candidate);
            list.add(find(prefix).getValue().get(0));
            find(prefix).setValue(list);
        }
        else if (find(prefix).getValue().size() < 20) {
            list = new ArrayList<>(20);
            list.add(0, candidate);
            for (int i = 0; i < find(prefix).getValue().size(); i++) {
                list.add(find(prefix).getValue().get(i-1));
            }
            find(prefix).setValue(list);
        }
        else {
            // adds candidate to prefix's list of candidates at highest priority
            // candidate is at first position and all others are shifted over
            list = new ArrayList<>(20);
            list.add(0, candidate);
            for (int i = 1; i < 20; i++) {
                list.add(find(prefix).getValue().get(i - 1));
            }
            // removes the now 21st candidate and sets list to prefix's value
            if (list.size() > 20)
                list.remove(list.size() - 1);
            find(prefix).setValue(list);
        }
    }

    public List<String> addCandidate(List<String> list, String candidate) {
        TrieNode<List<String>> node = find(candidate);
        // add candidate if not there already
        // makes endstate true to say it's a word
        if (node == null) {
            addToTrie(candidate);
            find(candidate).setEndState(true);
        }
        list.add(0, candidate);

        return list;
    }

    public void addToTrie(String word) {
        put(word, null);
    }

    private List<String> bfs(TrieNode<List<String>> node) {

        List<String> list;
        if (node.getValue() == null)
            list = new ArrayList<>();
        else
            list = node.getValue();

        Queue<TrieNode<List<String>>> q = new ArrayDeque<>();                            // Map; List<Characters> keys; Deque<NodeStringPair> queue;
        q.add(node);                                                                     // p = queue.poll();
        while(!q.isEmpty()) {
            TrieNode<List<String>> n = q.remove();
            if (n.isEndState()) {                                                       // if (p.node.isEndState() && !list.contains(p.form)
                // add candidate and check to see if list has reached 20 yet            //      list.add(p.form) <- adds the string automatically
                list.add(getString(n));
                if (list.size() >= 20) {
                    return list;                                                        //      if (list.size() >= 20) break; <- breaks when 20 candidates are reached
                }
            }
            // get new children keys
            Set<Character> s = n.getChildrenMap().keySet();                             // map = p.node.getChildrenMap(); keys = new ArrayList<>(map.keySet());
            List<Character> keys = new ArrayList<>(s);
            Collections.sort(keys);                                                     // Collections.sot(keys);
            // sort the children keys in alphabetical order,
            // and then put their nodes into the queue in order
            for (Character c : keys) {                                                  // for (Character key : keys)
                q.add(n.getChildrenMap().get(c));                                       //      queue.add(NodeStringPair(node, form + key)
            }
        }
        return list;
    }

    private String getString(TrieNode<List<String>> node) {
        Stack stack = new Stack();
        while (node.getParent() != null) {
            stack.push(node.getKey());
            node = node.getParent();
        }
        String s = "";
        while (!stack.isEmpty()) {
            s += stack.pop();
        }
        return s;
    }

    /*
    BFS - faster in this application bc we're asked to give shortest words first, which is what BFS checks for first
    DFS -
     */

}