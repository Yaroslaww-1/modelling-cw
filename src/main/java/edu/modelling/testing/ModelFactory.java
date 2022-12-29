package edu.modelling.testing;

import edu.modelling.Model;
import edu.modelling.elements.Element;
import edu.modelling.elements.arcs.DirectArc;
import edu.modelling.elements.arcs.ShortestQueueArc;
import edu.modelling.elements.arcs.TerminateArc;
import edu.modelling.elements.distributions.Distribution;
import edu.modelling.elements.distributions.ExpDistribution;
import edu.modelling.elements.distributions.NormDistribution;
import edu.modelling.elements.generators.BasicGenerator;
import edu.modelling.elements.processors.BasicProcessor;
import edu.modelling.elements.queues.LimitedQueue;
import edu.modelling.elements.queues.UnlimitedQueue;
import edu.modelling.loggers.DefaultLogger;
import edu.modelling.specific.*;

import java.util.ArrayList;

public class ModelFactory {
    public Model<SensorMessageItem> createNew(
            int N,
            Distribution T1, Distribution T2, Distribution T3,
            int S1, int S2, int S3, int S4,
            int L, int k) {
        var generator = new SensorMessageGenerator(T1);
        var duplexChannel = new BasicProcessor<SensorMessageItem>(T2, new UnlimitedQueue<>());

        var eoms = new ArrayList<EomProcessor>();
        var emergencyMode = new EmergencyModeLatch(N, S4);
        for (int i = 0; i < N; i++) {
            var eom = new EomProcessor(i + 1, T3, new LimitedQueue<>(L), emergencyMode, S1, S2, S3, k);
            eoms.add(eom);
        }
        generator.setArc(new DirectArc<>(duplexChannel));
        duplexChannel.setArc(new ShortestQueueArc<>(eoms));
        var terminateArc = new SensorMessageTerminateArc();
        eoms.forEach(eom -> eom.setArc(terminateArc));

        generator.setName("Generator");
        duplexChannel.setName("Duplex");
        for (int i = 0; i < eoms.size(); i++) eoms.get(i).setName("Eom " + i);

        ArrayList<Element<SensorMessageItem>> list = new ArrayList<>();
        list.add(generator);
        list.add(duplexChannel);
        list.addAll(eoms);

        return new Model<>(list, new DefaultLogger(false));
    }
}
