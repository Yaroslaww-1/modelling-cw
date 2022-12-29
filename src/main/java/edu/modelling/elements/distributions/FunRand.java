package edu.modelling.elements.distributions;

import java.util.Random;

public class FunRand {
    /*** Generates a random value according to a uniform distribution** @param timeMin* @param timeMax* @return a random value according to a uniform distribution*/
    public static double Unif(double timeMin, double timeMax) {
        double a = 0;
        while (a == 0) {
            a = Math.random();
        }
        a = timeMin + a * (timeMax - timeMin);
        return a;
    }

    /*** Generates a random value according to a normal (Gauss) distribution** @param timeMean* @param timeDeviation* @return a random value according to a normal (Gauss) distribution*/
    public static double Norm(double timeMean, double timeDeviation) {
        double a;
        Random r = new Random();
        a = timeMean + timeDeviation * r.nextGaussian();
        return a;
    }

    public static double Erlang(double mean, int k) {
        double product = 1;
        for (int i = 0; i < k; i++) {
            product *= new Random().nextDouble();
        }
        return -1 / mean * Math.log(product);
    }
}