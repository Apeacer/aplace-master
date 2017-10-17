package com.aplace.core.struct;

import java.util.Arrays;
import java.util.Random;

/**
 * Descript:
 *
 * @author ningning.wei
 * @version 17/10/17.
 */
public class Sort {

    public static <T extends Comparable> void insertSort(T[] array) {
        checkNullArray(array);

        for (int i = 1; i < array.length ; i++) {
            T current = array[i]; // 要往前插入的数字
            int j = i - 1; // 第一个j

            while (j >= 0 && array[j].compareTo(current) > 0) { // !!! 每次比较current
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = current; // !!! 比每次交换效率高多了
        }
    }

    public static <T extends Comparable> void selectSort(T[] array) {

    }

    public static <T extends Comparable> void quickSort(T[] array) {

    }

    /**
     * 检查空指针
     *
     * @param arrays
     * @param <T>
     */
    private static <T extends Comparable> void checkNullArray(T[] arrays) {
        if (arrays == null )
            throw new NullPointerException();
    }

    /**
     * 交换数组里两个值
     * @param a
     * @param b
     * @param arrays
     * @param <T>
     */
    private static <T extends Comparable> void exchange (int a, int b, T[] arrays){
        T tmp = arrays[a];
        arrays[a] = arrays [b];
        arrays[b] = tmp;
    }

    // unit test
    public static void main(String[] args) {
        Random random = new Random();
        int length = 30;
        Integer[] integers = new Integer[length];

        for (int i = 0; i < length ; i++) {
            integers[i] = random.nextInt(100);
        }

        Sort.insertSort(integers);
    }


}
