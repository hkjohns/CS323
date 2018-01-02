//
// BY: Hannah Johnson, netID: hkjohns
// THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
// A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - HANNAH JOHNSON
//

package graph.path;

import graph.Graph;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

public class SPWikiJohnson
{
    List<String> titles;
    int[][] links;

    public SPWikiJohnson(InputStream inTitles, InputStream inLinks) throws Exception
    {
        titles = getTitles(inTitles);
        links  = getLinks(inLinks, titles.size());

        Dijkstra d = new Dijkstra();
        int source = 1234;
        int target = 4321;

        // put all links into graph
        Graph g = new Graph(titles.size());
        for (int i = 0; i < titles.size(); i++) {
            int[] connections = links[i];
            for (int j = 0; j < connections.length; j++) {
                g.setDirectedEdge(i, connections[j],1);
            }
        }

        System.out.println("Finding shortest path from " + source + " (" + titles.get(source) +
                            ") to " + target + " (" + titles.get(target) + ") now...");
        Integer[] shortestPath = d.getShortestPath(g, source, target);
        int current = source;
        int distance = 0;
        System.out.println("Shortest path found!\n");
        for (Integer i : shortestPath) {
            distance++;
            System.out.println(current + " (" + titles.get(current) + ") ");
            if (shortestPath[current] != null) {
                current = shortestPath[current];
            }
            else
                break;
        }
        System.out.println("Total length = " + distance);
    }

    public List<String> getTitles(InputStream in) throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        List<String> list = new ArrayList<>();
        String line;

        while ((line = reader.readLine()) != null)
            list.add(line.trim());

        reader.close();
        return list;
    }

    public int[][] getLinks(InputStream in, int size) throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        Pattern p = Pattern.compile(" ");
        int[][] array = new int[size][];
        int[] links;
        String line;
        String[] t;
        int i, j;

        for (i=0; (line = reader.readLine()) != null; i++)
        {
            line = line.trim();

            if (line.isEmpty())
                array[i] = new int[0];
            else
            {
                t = p.split(line);
                links = new int[t.length];
                array[i] = links;

                for (j=0; j<links.length; j++)
                    links[j] = Integer.parseInt(t[j]);
            }
        }

        return array;
    }

    static public void main(String[] args) throws Exception
    {
        final String TITLE_FILE = "C:\\Users\\Hannah Johnson\\Documents\\FALL2017\\CS323\\wiki-titles-small.txt";
        final String LINK_FILE  = "C:\\Users\\Hannah Johnson\\Documents\\FALL2017\\CS323\\wiki-links-small.txt";
        new SPWikiJohnson(new FileInputStream(TITLE_FILE), new FileInputStream(LINK_FILE));
    }
}