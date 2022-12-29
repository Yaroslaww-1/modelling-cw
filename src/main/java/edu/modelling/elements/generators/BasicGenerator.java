package edu.modelling.elements.generators;


import edu.modelling.elements.distributions.Distribution;
import edu.modelling.elements.items.Item;

import java.util.function.Supplier;

public class BasicGenerator<T extends Item> extends AbstractGenerator<T> {
    public BasicGenerator(Distribution distribution, Supplier<T> itemSupplier) {
        super(distribution, itemSupplier);
    }
}
