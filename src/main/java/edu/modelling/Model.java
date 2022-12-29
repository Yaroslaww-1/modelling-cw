package edu.modelling;

import edu.modelling.loggers.Logger;
import edu.modelling.elements.Element;
import edu.modelling.elements.items.Item;

import java.util.ArrayList;
import java.util.List;

public class Model<T extends Item> {
    private ArrayList<Element<T>> list = new ArrayList<>();
    double tnext, tcurr;
    private final Logger log;

    public Model(ArrayList<Element<T>> elements, Logger log) {
        list = elements;
        tnext = 0.0;
        tcurr = tnext;
        this.log = log;
    }

    public void simulate(double time) {
        while (tcurr < time) {
            tnext = Double.MAX_VALUE;
            var minIdx = 0;

            for (int i = 0; i < list.size(); i++) {
                var e = list.get(i);
                if (e.getTnext() < tnext) {
                    tnext = e.getTnext();
                    minIdx = i;
                }
            }

            log.debug("\nIt's time for event in " + list.get(minIdx).getName() + ", time =   " + tnext);

            for (Element<T> e : list) {
                e.doStatistics(tnext - tcurr);
            }
            tcurr = tnext;
            for (Element<T> e : list) {
                e.setTcurr(tcurr);
            }
            list.get(minIdx).outAct();
            for (Element<T> e : list) {
                var tnext = e.getTnext();
                if (tnext == tcurr) {
                    e.outAct();
                }
            }

            printInfo();
        }

        printResult();
    }

    private void printInfo() {
        for (Element<T> e : list) {
            log.debug(e.getInfo(tcurr));
        }
    }

    private void printResult() {
        log.debug("\n-------------RESULTS-------------");
        for (Element<T> e : list) {
            log.debug(e.getResult());
            log.debug(e.getInfo(tcurr));
        }
    }

    public List<Element<T>> getElements() {
        return list;
    }
}
