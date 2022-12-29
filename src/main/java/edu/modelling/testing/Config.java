package edu.modelling.testing;

import edu.modelling.elements.distributions.Distribution;
import edu.modelling.elements.distributions.ExpDistribution;
import edu.modelling.elements.distributions.NormDistribution;

public class Config {
    public static int N = 3;

    public static Distribution T1 = new NormDistribution(11, 5);
    public static Distribution T2 = new NormDistribution(10, 3);
    public static Distribution T3 = new ExpDistribution(35);

    public static int S1 = 10;
    public static int S2 = 5;
    public static int S3 = 100;
    public static int S4 = 2;
}
