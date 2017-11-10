package com.aplace.core.struct;

import java.util.Arrays;

/**
 * Descript:
 *
 * @author ningning.wei
 * @version 17/10/20.
 */
public class MaxHeap <T extends Comparable> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final float DEFAULT_GROWTH_FACTOR = 1;

    // current size
    private int size = 0;
    // 存储值用的数组
    Comparable[] values ;

    public MaxHeap() {
        this(DEFAULT_CAPACITY);
    }

    public MaxHeap(int size) {
        values = new Comparable[size];
    }

    public MaxHeap(T[] targetValues) {
        int length = targetValues.length;
        int thisLength = DEFAULT_CAPACITY;

        while (thisLength < length) {
            thisLength *= (1 + DEFAULT_GROWTH_FACTOR);
        }
        // 新数组长度
        values = new Comparable[thisLength];

        for (int i = 0; i < targetValues.length ; i++) {
            values[i] = targetValues[i];
        }
        size = length;
        build();
    }

    // 重构整个堆
    private void build() {



    }

    // 重构一个点
    private void buildNode(int pos) {
        while (pos <= size/2 - 1) {
            int l = pos * 2;
            int r = l + 1;
            int max = pos;

            if (l < size && values[max].compareTo(values[l]) < 0) {
                max = l;
            }
            if (r < size && values[max].compareTo(values[r]) < 0) {
                max = r;
            }

            exchange(pos,max);

            pos = max;
        }



    }


    private void exchange(int a, int b) {
        Object tmp = values[a];
        values[a] = values[b];
        values[b] = true;
    }

}
