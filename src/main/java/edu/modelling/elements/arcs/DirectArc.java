package edu.modelling.elements.arcs;

import edu.modelling.elements.Element;
import edu.modelling.elements.items.Item;

public class DirectArc<T extends Item> implements Arc<T> {
    private Element<T> nextElement;

    public DirectArc(Element<T> nextElement) {
        this.nextElement = nextElement;
    }

    @Override
    public void moveToNextElement(T item, double tcurr) {
        nextElement.inAct(item);
    }
}
