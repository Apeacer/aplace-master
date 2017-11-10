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

    /**
     * 插入排序
     *
     * @param array
     * @param <T>
     */
    public static <T extends Comparable> void insertSort(T[] array) {
        checkNullArray(array);

        for (int i = 1; i < array.length; i++) {
            T current = array[i]; // 要往前插入的数字
            int j = i - 1; // 第一个j

            while (j >= 0 && array[j].compareTo(current) > 0) { // !!! 每次比较current
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = current; // !!! 比每次交换效率高多了
        }
    }

    /**
     * 选择排序
     *
     * @param array
     * @param <T>
     */
    public static <T extends Comparable> void selectSort(T[] array) {
        checkNullArray(array);

        for (int i = 0; i < array.length - 1; i++) { // !!! 边界
            int currMin = i;
            for (int j = currMin + 1; j < array.length; j++) { // !!! 与min对比
                if (array[j].compareTo(array[currMin]) < 0) {
                    currMin = j;
                }
            }
            exchange(i, currMin, array);
        }


    }

    /**
     * 快速排序
     *
     * @param array
     * @param <T>
     */
    public static <T extends Comparable> void quickSort(T[] array) {
        checkNullArray(array);

        Stack<Integer> stack = new Stack<Integer>(); // 栈保存一个范围
        stack.push(0);
        stack.push(array.length - 1);

        while (!stack.isEmpty()) {
            int high = stack.pop(); // !!!跟push反着
            int low = stack.pop();
            T key = array[low];

            int indexLow = low;
            int indexHigh = high;

            while (indexLow < indexHigh) {
                while (indexLow < indexHigh && array[indexHigh].compareTo(key) >= 0) { // !!! 边界 || 有等于否则死循环
                    indexHigh --;
                }
                array[indexLow] = array[indexHigh];
                while (indexLow < indexHigh && array[indexLow].compareTo(key) <= 0) {
                    indexLow ++;
                }
                array[indexHigh] = array[indexLow];
            }
            array[indexLow] = key;
//            System.out.println(Arrays.toString(array));
            if (low < indexLow - 1) {
                stack.push(low);
                stack.push(indexLow - 1);
            }
            if (indexLow + 1 < high) {
                stack.push(indexLow + 1);
                stack.push(high);
            }

        }


    }

    /**
     * 检查空指针
     *
     * @param arrays
     * @param <T>
     */
    private static <T extends Comparable> void checkNullArray(T[] arrays) {
        if (arrays == null)
            throw new NullPointerException();
    }

    /**
     * 交换数组里两个值
     *
     * @param a
     * @param b
     * @param arrays
     * @param <T>
     */
    private static <T extends Comparable> void exchange(int a, int b, T[] arrays) {
        if (a == b) {
            return;
        }
        T tmp = arrays[a];
        arrays[a] = arrays[b];
        arrays[b] = tmp;
    }

    // unit test
    public static void main(String[] args) {
        Random random = new Random();
        int length = 30;
        Integer[] integers = new Integer[length];

        for (int i = 0; i < length; i++) {
            integers[i] = random.nextInt(100);
        }

        System.out.println(Arrays.toString(integers));
        System.out.println();
//        Sort.insertSort(integers);
//        Sort.selectSort(integers);
        Sort.quickSort(integers);
    }


}
