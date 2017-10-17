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
    private int capacity;
    private float growthFactor;


    public Stack() {
        this(DEFAULT_CAPACITY);
    }

    public Stack(int capacity) {
        this(capacity, DEFAULT_GROWTH_FACTOR);
    }

    public Stack(int capacity, float growthFactor) {
        values = new Object[capacity];
        this.capacity = capacity;
        this.growthFactor = growthFactor;
    }

    public void push(T value) {
        if (isFull()) {
            growth();
        }
        values[++top] = value;

    }

    public T pop() {
        if (isEmpty()) {
            return null;
        }

        return (T) values[top--];
    }

    private boolean isFull() {
        return top == (capacity - 1);
    }

    private boolean isEmpty() {
        return top < 0;
    }

    private void growth() {
        int newLength = (int) (capacity * (1 + growthFactor));
        this.capacity = newLength;
//        Object[] newValues = new Object[newLength];

        this.values = Arrays.copyOf(values, newLength);
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

        for (int i = 0; i < 100; i++) {
            stack.push(i);
            System.out.println(stack);

        }

        System.out.println(stack.pop());
        System.out.println(stack);
        System.out.println(stack.pop());
        System.out.println(stack);

    }
}
