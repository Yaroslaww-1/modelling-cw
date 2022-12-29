package edu.modelling.elements.distributions;

public class NormDistribution implements Distribution {
    private final double mean;
    private final double dev;

    public NormDistribution(double mean, double dev) {
        this.mean = mean;
        this.dev = dev;
    }

    @Override
    public double generate() {
        return FunRand.Norm(mean, dev);
    }
}
