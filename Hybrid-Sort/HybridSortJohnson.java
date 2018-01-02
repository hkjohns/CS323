//
// BY: Hannah Johnson, netID: hkjohns
// THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
// A TUTOR OR CODE WRITTEN BY OTHER STUDENTS - HANNAH JOHNSON
//

package sort.hybrid;

import sort.AbstractSort;
import sort.comparison.InsertionSort;
import sort.comparison.ShellSortKnuth;
import sort.divide_conquer.IntroSort;
import sort.divide_conquer.QuickSort;

import java.lang.reflect.Array;
import java.util.Arrays;

public class HybridSortJohnson<T extends Comparable<T>> implements HybridSort<T> {

    private AbstractSort<T> engine;

    public HybridSortJohnson() {
        engine = new QuickSort<>();
    }

    @Override
    public T[] sort(T[][] input) {
        int size = Arrays.stream(input).mapToInt(t -> t.length).sum();
        T[] output = (T[]) Array.newInstance(input[0][0].getClass(), size);
        int beginIndex = 0;
        for (int i = 0; i < input.length; i++) {
            boolean mostlySorted = false,sorted = true,descendingOrder = true,descendingMostlySorted = false;

            if (input[i] == null || input[i].length == 1)
                continue;
            //check to see if it's sorted already
            // O(n)
            for (int j = 0; j < input[i].length-1; j++) {
                if (input[i][j].compareTo(input[i][j + 1]) > 0) {
                    sorted = false;
                    break;
                }
            }
            // if sorted already, it jumps to next array
            if (sorted) {
                continue;
            }
            // check to see if in descending order
            for (int j = 0; j < input[i].length-1; j++) {
                if (input[i][j].compareTo(input[i][j + 1]) < 0) {
                    descendingOrder = false;
                    break;
                }
            }
            // flips array if in entirely descending order
            if (descendingOrder) {
                flipArray(input[i]);
                continue;
            }

            else {
                // when array is entirely random, quicksort is the best option
                quickSort(input[i]);
            }
        }
        // copies each sorted array into one single array, sorts it again, and then returns it
        for (T[] t : input)
        {
            System.arraycopy(t, 0, output, beginIndex, t.length);
            beginIndex += t.length;
        }
        // quick sort is used to sort the entire output as one array and is then returned
        engine = new QuickSort<>();
        engine.sort(output);
        return output;
    }

    private void flipArray(T[] array) {
        // reverses array that's in descending order
        // O(n/2)
        int point1 = 0,point2 = array.length - 1;
        while (point1 < point2) {
            T t = array[point1];
            array[point1] = array[point2];
            array[point2] = t;
            point1++;
            point2--;
        }
    }

    private void insertionSort(T[] array) {
        engine = new InsertionSort<>();
        engine.sort(array);
    }

    @SuppressWarnings("unchecked")
    private void introSort(T[] array) {
        engine = new IntroSort<T>(new ShellSortKnuth<>());
        engine.sort(array);
    }

    private void quickSort(T[] array) {
        engine = new QuickSort<>();
        engine.sort(array);
    }
}
