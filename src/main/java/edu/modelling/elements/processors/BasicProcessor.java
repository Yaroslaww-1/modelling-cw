package edu.modelling.elements.processors;

import edu.modelling.elements.distributions.Distribution;
import edu.modelling.elements.items.Item;
import edu.modelling.elements.queues.Queue;

public class BasicProcessor<T extends Item> extends AbstractProcessor<T> {
    public BasicProcessor(Distribution distribution, Queue<T> queue) {
        super(distribution, queue);
    }

    public boolean isFree() {
        return getState() == 0;
    }

    public void startProcessing(T item) {
        super.setState(1);
        super.setTnext(super.getTcurr() + super.getDelay());
        itemInProcess = item;
    }

    public T endProcessAdnGetProcessedItem() {
        super.setTnext(Double.MAX_VALUE);
        super.setState(0);
        return itemInProcess;
    }
}