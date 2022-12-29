package edu.modelling.elements.distributions;

public class ExpDistribution implements Distribution {
    private final double mean;

    public ExpDistribution(double mean) {
        this.mean = mean;
    }

    @Override
    public double generate() {
        double a = 0;
        while (a == 0) {
            a = Math.random();
        }
        a = -mean * Math.log(a);
        return a;
    }
}
