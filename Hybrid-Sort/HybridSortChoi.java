package sort.hybrid;

import java.lang.reflect.Array;
import java.util.Arrays;

import sort.AbstractSort;
import sort.divide_conquer.QuickSort;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class HybridSortChoi<T extends Comparable<T>> implements HybridSort<T>
{
    private AbstractSort<T> engine;

    public HybridSortChoi()
    {
        engine = new QuickSort<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] sort(T[][] input)
    {
        int size = Arrays.stream(input).mapToInt(t -> t.length).sum();
        T[] output = (T[])Array.newInstance(input[0][0].getClass(), size);
        int beginIndex = 0;

        for (T[] t : input)
        {
            System.arraycopy(t, 0, output, beginIndex, t.length);
            beginIndex += t.length;
        }

        engine.sort(output);
        return output;
    }
}