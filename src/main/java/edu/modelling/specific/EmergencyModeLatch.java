package edu.modelling.specific;

import edu.modelling.elements.processors.AbstractProcessor;

import java.util.ArrayList;
import java.util.List;

public class EmergencyModeLatch implements CostAffecting {
    private boolean isEmergency;
    private double lastEnabledTime;
    private double totalTime;

    private int N, S4;
    private List<EomProcessor> processors;

    public EmergencyModeLatch(int N, int S4) {
        this.lastEnabledTime = 0;
        this.totalTime = 0;

        this.N = N;
        this.S4 = S4;
        this.processors = new ArrayList<>();
    }

    public void register(EomProcessor processor) {
        processors.add(processor);
    }

    public void update(double tcurr) {
        var sumLength = processors.stream().mapToInt(AbstractProcessor::getQueueSize).sum();
        var shouldEnableEmergency = sumLength >= 4 * processors.size();

        if (shouldEnableEmergency) {
            isEmergency = true;
            lastEnabledTime = tcurr;
            return;
        }

        if (isEmergency) {
            isEmergency = false;
            totalTime += tcurr - lastEnabledTime;
        }
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public double getCost() {
        return totalTime * S4;
    }
}
