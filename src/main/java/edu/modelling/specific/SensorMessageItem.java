package edu.modelling.specific;

import edu.modelling.elements.items.Item;

public class SensorMessageItem implements Item {
    private double createdAt = -1;
    private double endAt;

    public void start(double tcurr) {
        this.createdAt = tcurr;
    }

    public void end(double tcurr) {
        this.endAt = tcurr;
    }

    public double getTime() {
//        System.out.println(endAt + " - " + createdAt);
        return endAt - createdAt;
    }
}
