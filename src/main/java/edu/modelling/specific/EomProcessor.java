package edu.modelling.specific;

import edu.modelling.elements.distributions.Distribution;
import edu.modelling.elements.processors.AbstractProcessor;
import edu.modelling.elements.queues.Queue;

public class EomProcessor extends AbstractProcessor<SensorMessageItem> implements CostAffecting {
    private final EmergencyModeLatch emergencyMode;
    private double cost;
    private final int id;
    private final int S1, S2, S3,  k;
    private double processingCost;
    private double failureCost;

    public EomProcessor(
            int id,
            Distribution distribution, Queue<SensorMessageItem> queue, EmergencyModeLatch emergencyMode,
            int S1, int S2, int S3, int k) {
        super(distribution, queue);
        this.id = id;
        this.emergencyMode = emergencyMode;
        this.cost = 0;
        this.S1 = S1;
        this.S2 = S2;
        this.S3 = S3;
        this.k = k;
        this.processingCost = 0;
        this.failureCost = 0;
        emergencyMode.register(this);
    }

    public boolean isFree() {
        return getState() == 0;
    }

    @Override
    public void inAct(SensorMessageItem item) {
        quantity++;
        if (isFree()) {
            startProcessing(item);
        } else {
            if (queue.getSize() < queue.getMaxSize()) {
                queue.add(item);
                if (id == 1) emergencyMode.update(getTcurr());
            } else {
                failure++;
                failureCost += S3;
            }
        }
    }

    public void startProcessing(SensorMessageItem item) {
        super.setState(1);
        itemInProcess = item;

        if (emergencyMode.isEmergency()) {
            var processingTime = super.getDelay() - k;
            processingCost += k * S2;
            super.setTnext(super.getTcurr() + processingTime);
        } else {
            var processingTime = super.getDelay();
            super.setTnext(super.getTcurr() + processingTime);
        }
    }

    @Override
    public void outAct() {
        var processedItem = endProcessAdnGetProcessedItem();
        super.getArc().moveToNextElement(processedItem, getTcurr());

        if (queue.getSize() > 0) {
            startProcessing(queue.pop());
            if (id == 1) emergencyMode.update(getTcurr());
        }
    }

    public SensorMessageItem endProcessAdnGetProcessedItem() {
        super.setTnext(Double.MAX_VALUE);
        super.setState(0);
        processingCost += (queue.getMaxSize() - 5) * S1;
        return itemInProcess;
    }

    @Override
    public String getInfo(double tcurr) {
        return "failure = " + this.failure + "\n" +
                "mean length of queue = " + meanQueue / tcurr + "\n" +
                "failure probability  = " + failure / (double) quantity + "\n" +
                "mean load = " + meanLoad / tcurr + "\n" +
                "processing cost = " + processingCost + "\n" +
                "failure cost = " + failureCost + "\n" +
                "total cost = " + getCost() + "\n";
    }

    public double getCost() {
        return processingCost + failureCost;
    }

    public double getEmergencyCost() {
        return emergencyMode.getCost();
    }
}