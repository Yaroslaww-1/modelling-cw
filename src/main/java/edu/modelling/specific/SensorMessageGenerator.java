package edu.modelling.specific;


import edu.modelling.elements.Element;
import edu.modelling.elements.distributions.Distribution;
import edu.modelling.elements.generators.AbstractGenerator;
import edu.modelling.elements.items.Item;

import java.util.function.Supplier;

public class SensorMessageGenerator extends Element<SensorMessageItem> {
    public SensorMessageGenerator(Distribution distribution) {
        super(distribution);
    }

    @Override
    public void outAct() {
        super.outAct();
        super.setTnext(super.getTcurr() + super.getDelay());
        var item = new SensorMessageItem();
        item.start(getTcurr());
//        System.out.println("ds" + getTcurr());
        super.getArc().moveToNextElement(item, getTcurr());
    }
}
