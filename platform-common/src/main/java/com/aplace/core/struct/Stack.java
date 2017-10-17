package com.aplace.core.struct;

import java.util.Arrays;

/**
 * @author apeace
 * @version 2017/10/15.
 */
public class Stack<T> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final float DEFAULT_GROWTH_FACTOR = 1;

    private Object[] values;
    private int top = -1;
    private float growthFactor;


    public Stack() {
        this(DEFAULT_CAPACITY);
    }

    public Stack(int capacity) {
        this(capacity, DEFAULT_GROWTH_FACTOR);
    }

    public Stack(int capacity, float growthFactor) {
        values = new Object[capacity];
        this.growthFactor = growthFactor;
    }

    /**
     * "弹"，从栈弹出
     * @return
     */
    public T pop() {
        if (isEmpty()) {
            return null;
        }

        return (T) values[top--]; // !!!
    }

    /**
     * 压栈
     * @param value
     */
    public void push(T value) {
        if (isFull()) {
            growth();
        }
        values[++top] = value; // !!!

    }

    /**
     * 对满栈进行扩容
     */
    private void growth() {
        int newLength = (int) (values.length * (1 + growthFactor));
        this.values = Arrays.copyOf(values, newLength);
    }

    /**
     * 判断栈是否已满
     * @return
     */
    private boolean isFull() {
        return top == (values.length - 1);
    }

    /**
     * 判断栈是否为空
     * @return
     */
    private boolean isEmpty() {
        return top < 0;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = top; i >= 0; i--) {
            stringBuilder.append(values[i]);
            stringBuilder.append(',');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<Integer>();

        for (int i = 0; i < 55; i++) {
            stack.push(i);
            System.out.println(stack);

        }

        System.out.println(stack.pop());
        System.out.println(stack);
        System.out.println(stack.pop());
        System.out.println(stack);

    }
}
