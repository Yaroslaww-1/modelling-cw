package edu.modelling.elements.queues;

import edu.modelling.elements.items.Item;

import java.util.LinkedList;

public class UnlimitedQueue<T extends Item> implements Queue<T> {
    private java.util.Queue<T> queue;

    public UnlimitedQueue() {
        this.queue = new LinkedList<>();
    }

    @Override
    public int getSize() {
        return queue.size();
    }

    @Override
    public int getMaxSize() {
        return Integer.MAX_VALUE;
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
