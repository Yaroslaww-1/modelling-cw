package edu.modelling.elements.queues;

import edu.modelling.elements.items.Item;

public interface Queue<T extends Item> {
    int getSize();
    int getMaxSize();
    boolean isNotEmpty();


    void add(T item);
    T pop();
}
