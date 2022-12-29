package edu.modelling.elements.arcs;

import edu.modelling.elements.items.Item;

public interface Arc<T extends Item> {
    void moveToNextElement(T item, double tcurr);
}
