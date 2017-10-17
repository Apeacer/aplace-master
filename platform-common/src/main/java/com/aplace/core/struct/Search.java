package com.aplace.core.struct;

/**
 * Descript:
 *
 * @author ningning.wei
 * @version 17/10/17.
 */
public class Search {

    /**
     * 二分查找
     *
     * @param arrays
     * @param target
     * @param <T>
     * @return
     */
    public static <T extends Comparable> int binarySearch(T[] arrays, T target) {
        int low = 0;
        int high = arrays.length - 1;

        while(low <= high) { // !!!

            int mid = (low + high)/2; // !!!

            if (target.compareTo(arrays[mid]) > 0) {
                low = mid + 1;
            } else if (target.compareTo(arrays[mid]) < 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
         return -1;
    }


    public static void main(String[] args) {
        Integer[] integers = new Integer[] {
          1,2,3,4,5,6,6,7,8,9,12,23,34,67,78,89
        };


        System.out.println(Search.binarySearch(integers, 0));
    }
}
