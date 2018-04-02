package util.sort;

import java.util.Comparator;

import coin.Coin;

public class QuickSort implements Comparator<Coin>{
	
	/**
	 * Will sort the given coin list using the Comparator c
	 * @param list coin list to be sorted
	 * @param c comparator object to use to compare coins
	 */
	public static void sort(Coin[] list, Comparator<Coin> c) {
		sort(list, 0, list.length-1);
	}
	
	//add private helper / sorting methods here
	//Reference: https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Quick.java.html

	/**
	 * Private sorting method to sort an array (works recursively)
	 * @param list The array to be sorted
	 * @param lo The lower index for sorting
	 * @param hi The upper index for sorting
	 */
    private static void sort(Coin[] list, int lo, int hi) { 
        if (hi <= lo) return;
        int j = partition(list, lo, hi);
        sort(list, lo, j-1);
        sort(list, j+1, hi);
    }


    private static int partition(Coin[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
       int v = (int) a[lo].getPrice();
        while (true) { 

            // find item on lo to swap
            while (less1(a[++i].getPrice(), a[v].getPrice())) {
                if (i == hi) break;
            }

            // find item on hi to swap
            while (less1(a[v].getPrice(), a[--j].getPrice())) {
                if (j == lo) break;      // redundant since a[lo] acts as sentinel
            }

            // check if pointers cross
            if (i >= j) break;

           swap(a, i, j);
        }

        // put partitioning item v at a[j]
        swap(a, lo, j);

        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }

	/**
	 * Used to check if one value is less than another
	 * @param d the first value
	 * @param e the second value
	 */
	private static boolean less1(double d, double e) {
        if (d == e) return false;   
        return  (d - e) < 0;
    }
        	
	/**
	 * Used to exchange two values in an array
	 * @param ob the array in which the swap takes place
	 * @param i the first value
	 * @param j the second value
	 */
    private static void swap(Coin[] ob, int i, int j) {
        Coin exch = ob[i];
        ob[i] = ob[j];
        ob[j] = exch;
    }

	@Override
	public int compare(Coin o1, Coin o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
