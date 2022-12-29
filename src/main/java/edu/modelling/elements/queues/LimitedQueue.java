package edu.modelling.elements.queues;

import edu.modelling.elements.items.Item;

import java.util.LinkedList;

public class LimitedQueue<T extends Item> implements Queue<T> {
    private int maxSize;
    private java.util.Queue<T> queue;

    public LimitedQueue(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new LinkedList<>();
    }

    @Override
    public int getSize() {
        return queue.size();
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

    @Override
    public boolean isNotEmpty() {
        return !queue.isEmpty();
    }

    @Override
    public void add(T item) {
        queue.offer(item);
    }

    @Override
    public T pop() {
        return queue.poll();
    }
}
