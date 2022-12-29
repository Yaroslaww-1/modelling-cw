package edu.modelling.specific;


import edu.modelling.elements.arcs.Arc;
import edu.modelling.elements.items.Item;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SensorMessageTerminateArc implements Arc<SensorMessageItem> {
    private final Set<Integer> loggedIntervals = new HashSet<>();

    @Override
    public void moveToNextElement(SensorMessageItem item, double tcurr) {
        if (item == null) return;
        item.end(tcurr);
        var interval = (int) tcurr / 1000;

        var result = item.getTime();

        if (!loggedIntervals.contains(interval)) {
//            System.out.println(result);
            loggedIntervals.add(interval);
        }
    }
}
