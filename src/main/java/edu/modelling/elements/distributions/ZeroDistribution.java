package edu.modelling.elements.distributions;

public class ZeroDistribution implements Distribution {
    @Override
    public double generate() {
        return 0;
    }
}
