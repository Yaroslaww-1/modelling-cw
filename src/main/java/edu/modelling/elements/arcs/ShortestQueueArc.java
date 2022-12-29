package edu.modelling.elements.arcs;


import edu.modelling.elements.items.Item;
import edu.modelling.elements.processors.AbstractProcessor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ShortestQueueArc<T extends Item> implements Arc<T> {
    private final List<? extends AbstractProcessor<T>> processors;

    public ShortestQueueArc(List<? extends AbstractProcessor<T>> processors) {
        this.processors = processors;
    }

    @Override
    public void moveToNextElement(T item, double tcurr) {
        var minQueue = processors.get(0).getQueueSize();

        for (int i = 1; i < processors.size(); i++) {
            if (processors.get(i).getQueueSize() < minQueue) {
                minQueue = processors.get(i).getQueueSize();
            }
        }

        var minIdxs = new ArrayList<Integer>();
        for (var i = 0; i < processors.size(); i++) {
            if (processors.get(i).getQueueSize() == minQueue) {
                minIdxs.add(i);
            }
        }

        var randomIdx = minIdxs.get(new Random().nextInt(0, minIdxs.size()));
        var nextElement = processors.get(randomIdx);
        nextElement.inAct(item);
    }
}
