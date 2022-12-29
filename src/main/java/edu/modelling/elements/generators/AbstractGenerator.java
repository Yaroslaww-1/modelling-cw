package edu.modelling.elements.generators;


import edu.modelling.elements.Element;
import edu.modelling.elements.distributions.Distribution;
import edu.modelling.elements.items.Item;

import java.util.function.Supplier;

public abstract class AbstractGenerator<T extends Item> extends Element<T> {
    protected final Supplier<T> itemSupplier;

    public AbstractGenerator(Distribution distribution, Supplier<T> itemSupplier) {
        super(distribution);
        this.itemSupplier = itemSupplier;
    }

    @Override
    public void outAct() {
        super.outAct();
        super.setTnext(super.getTcurr() + super.getDelay());
        super.getArc().moveToNextElement(itemSupplier.get(), getTcurr());
    }
}
