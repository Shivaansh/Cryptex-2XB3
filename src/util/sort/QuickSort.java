package util.sort;

import java.util.Comparator;

import coin.Coin;

import java.lang.Comparable; //imported by Shivaansh

public class quickSort {
	
	/**
	 * Will sort the given coin list using the Comparator c
	 * @param list coin list to be sorted
	 * @param c comparator object to use to compare coins
	 */
	public static void sort(Coin[] list, Comparator<Coin> c) {
		sort(list, 0, list.length-1);
		assert(isSorted(list));
	}
	
	//add private helper / sorting methods here
	//Reference: https://algs4.cs.princeton.edu/code/edu/princeton/cs/algs4/Quick.java.html

	/**
	 * Private sorting method to sort an array (works recursively)
	 * @param a The array to be sorted
	 * @param lo The lower index for sorting
	 * @param hi The upper index for sorting
	 */
    private static void sort(Comparable[] a, int lo, int hi) { 
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1);
        sort(a, j+1, hi);
        assert isSorted(a, lo, hi);
    }
	/**
	 * Used to check if array is sorted
	 * @param list coin list to be sorted
	 */
	private static boolean isSorted(Coin[] list) {
        return isSorted(list, 0, list.length - 1);
    }
	
	
	
	/**
	 * Used to check if array is sorted
	 * @param list coin list to be sorted
	 * @param lo lower limit for sorting
	 * @param hi upper limit for sorting
	 */
	private static boolean isSorted(Coin[] list, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(list[i], list[i-1])) return false;
        return true;
    }
	
	/**
	 * Checks result of isSorted() and returns as boolean
	 * @param a The array to be sorted
	 * @return Boolean value: whether the array has been sorted
	 */
	private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }
	
	
	
	/**
	 * Used to check if one value is less than another
	 * @param a the first value
	 * @param b the second value
	 */
	private static boolean less(Comparable a, Comparable b, Comparator<Coin> c) {
        if (a == b) return false;   
        return (c.compare(a,b) < 0);
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
	
}
