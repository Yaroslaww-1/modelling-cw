package edu.modelling.testing;

import edu.modelling.elements.Element;
import edu.modelling.elements.distributions.ExpDistribution;
import edu.modelling.elements.distributions.NormDistribution;
import edu.modelling.specific.CostAffecting;
import edu.modelling.specific.EomProcessor;
import edu.modelling.specific.SensorMessageItem;

import java.util.ArrayList;
import java.util.stream.Stream;

public class TestRunner {
    private final ModelFactory modelFactory;

    public TestRunner(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    public void run() {
//        runWithFixedLK(5, 1);
//        runWithFixedLK(6, 1);
//        runWithFixedLK(7, 1);
//        runWithFixedLK(8, 1);
//        runWithFixedLK(9, 1);
//        runWithFixedLK(10, 1);
//        runWithFixedLK(20, 1);
//        runWithFixedLK(50, 1);

//        runWithFixedLK(5, 1);
//        runWithFixedLK(5, 2);
//        runWithFixedLK(5, 3);
//        runWithFixedLK(5, 4);
//        runWithFixedLK(5, 5);
//        runWithFixedLK(5, 6);
//        runWithFixedLK(5, 7);
//        runWithFixedLK(5, 8);
//        runWithFixedLK(5, 9);
//        runWithFixedLK(5, 10);
//        runWithFixedLK(5, 20);
//        runWithFixedLK(5, 50);

        runWithFixedLK(5, 1);
        runWithFixedLK(5, 2);
        runWithFixedLK(5, 3);

        runWithFixedLK(6, 1);
        runWithFixedLK(6, 2);
        runWithFixedLK(6, 3);

        runWithFixedLK(7, 1);
        runWithFixedLK(7, 2);
        runWithFixedLK(7, 3);


//        for (int i = 0; i < 20; i++) {
//            runWithFixedLK(5, 5);
//        }
//        runWithFixedLK(5, 10);
//        runWithFixedLK(5, 2);
//        runWithFixedLK(7, 1);
//        runWithFixedLK(7, 2);
//        runWithFixedLK(10, 1);
//        runWithFixedLK(10, 2);
//        runWithFixedLK(15, 1);
//        runWithFixedLK(50, 1);
    }

    private void runWithFixedLK(int L, int k) {
        var NUMBER_OF_RUNS = 10;
        var SIMULATING_TIME = 100_000;
        long cost = 0;

        var runs = new ArrayList<Long>();

        for (int run = 0; run < NUMBER_OF_RUNS; run++) {
            var model = modelFactory.createNew(Config.N, Config.T1, Config.T2, Config.T3, Config.S1, Config.S2, Config.S3, Config.S4, L, k);
            model.simulate(SIMULATING_TIME);
            var runCost = (long) model.getElements().stream()
                    .filter(e -> e instanceof CostAffecting)
                    .map(e -> (CostAffecting) e)
                    .mapToDouble(CostAffecting::getCost).sum();
            cost += runCost;
            runs.add(runCost);
//            System.out.println("run cost " + runCost);

        }

        long avgCost = cost / NUMBER_OF_RUNS;

        long D = 0;
        for (var run : runs) {
            D += (run - avgCost) * (run - avgCost);
        }

//        System.out.println("For L = " + L + ", K = " + k + " avg. cost is " + avgCost);
        System.out.println(avgCost);
    }
}
