package edu.modelling.elements.processors;

import edu.modelling.elements.Element;
import edu.modelling.elements.distributions.Distribution;
import edu.modelling.elements.items.Item;
import edu.modelling.elements.queues.Queue;

public abstract class AbstractProcessor<T extends Item> extends Element<T> {
    protected int failure;
    protected double meanQueue;
    protected double meanLoad;
    protected Queue<T> queue;
    protected T itemInProcess;

    public AbstractProcessor(Distribution distribution, Queue<T> queue) {
        super(distribution);
        this.queue = queue;
        meanQueue = 0.0;
        meanLoad = 0.0;
    }

    @Override
    public void inAct(T item) {
        quantity++;
        if (isFree()) {
            startProcessing(item);
        } else {
            if (queue.getSize() < queue.getMaxSize()) {
                queue.add(item);
            } else {
                failure++;
            }
        }
    }

    @Override
    public void outAct() {
        var processedItem = endProcessAdnGetProcessedItem();
        super.getArc().moveToNextElement(processedItem, getTcurr());

        if (queue.getSize() > 0) {
            startProcessing(queue.pop());
        }
    }

    @Override
    public String getInfo(double tcurr) {
        return "failure = " + this.failure + "\n" +
                "mean length of queue = " + meanQueue / tcurr + "\n" +
                "failure probability  = " + failure / (double) quantity + "\n" +
                "mean load = " + meanLoad / tcurr + "\n";
    }

    @Override
    public void doStatistics(double delta) {
        meanQueue += queue.getSize() * delta;
        meanLoad += getState() * delta;
    }

    public abstract boolean isFree();
    public abstract void startProcessing(T item);
    public abstract T endProcessAdnGetProcessedItem();

    public int getQueueSize() {
        return queue.getSize();
    }
    public double getMeanQueue() {return meanQueue / getTcurr();}
    public double getMeanLoad() {return meanLoad / getTcurr();}
    public double getFailure() {return failure / (double) quantity;}
}
