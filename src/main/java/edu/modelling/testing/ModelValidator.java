package edu.modelling.testing;

import edu.modelling.Model;
import edu.modelling.elements.Element;
import edu.modelling.elements.distributions.ExpDistribution;
import edu.modelling.elements.distributions.NormDistribution;
import edu.modelling.elements.processors.BasicProcessor;
import edu.modelling.specific.EomProcessor;
import edu.modelling.specific.SensorMessageItem;

import java.util.*;
import java.util.stream.Collectors;

public class ModelValidator {
    private final ModelFactory modelFactory;
    private double simulationTime;
    private final Map<String, List<String>> statistics;

    public ModelValidator(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
        this.statistics = new HashMap<>();
    }

    public void validate(double simulationTime) {
        this.simulationTime = simulationTime;
        runTestSuite(5, 1);
        new CsvExporter().export(statistics, "validation2");
    }

    private void runTestSuite(int L, int k) {
        mergeMetadataToGlobalStatistics(getModelMetadata(modelFactory.createNew(
                3,
                new NormDistribution(11, 5), new NormDistribution(10, 3), new ExpDistribution(35),
                10, 5, 100, 2,
                L, k)));

        mergeMetadataToGlobalStatistics(getModelMetadata(modelFactory.createNew(
                3,
                new NormDistribution(5, 5), new NormDistribution(10, 3), new ExpDistribution(35),
                10, 5, 100, 2,
                L, k)));

        mergeMetadataToGlobalStatistics(getModelMetadata(modelFactory.createNew(
                3,
                new NormDistribution(11, 5), new NormDistribution(5, 3), new ExpDistribution(35),
                10, 5, 100, 2,
                L, k)));

        mergeMetadataToGlobalStatistics(getModelMetadata(modelFactory.createNew(
                3,
                new NormDistribution(11, 5), new NormDistribution(10, 3), new ExpDistribution(20),
                10, 5, 100, 2,
                L, k)));

        mergeMetadataToGlobalStatistics(getModelMetadata(modelFactory.createNew(
                3,
                new NormDistribution(11, 5), new NormDistribution(10, 3), new ExpDistribution(35),
                20, 5, 100, 2,
                L, k)));

        mergeMetadataToGlobalStatistics(getModelMetadata(modelFactory.createNew(
                3,
                new NormDistribution(11, 5), new NormDistribution(10, 3), new ExpDistribution(35),
                10, 50, 100, 2,
                L, k)));

        mergeMetadataToGlobalStatistics(getModelMetadata(modelFactory.createNew(
                3,
                new NormDistribution(11, 5), new NormDistribution(10, 3), new ExpDistribution(35),
                10, 5, 1000, 2,
                L, k)));

        mergeMetadataToGlobalStatistics(getModelMetadata(modelFactory.createNew(
                3,
                new NormDistribution(11, 5), new NormDistribution(10, 3), new ExpDistribution(35),
                10, 5, 100, 20,
                L, k)));

        mergeMetadataToGlobalStatistics(getModelMetadata(modelFactory.createNew(
                3,
                new NormDistribution(11, 5), new NormDistribution(10, 3), new ExpDistribution(35),
                10, 5, 100, 20,
                L * 2, k)));

        mergeMetadataToGlobalStatistics(getModelMetadata(modelFactory.createNew(
                3,
                new NormDistribution(11, 5), new NormDistribution(10, 3), new ExpDistribution(35),
                10, 5, 100, 20,
                L, k * 2)));
    }

    private Map<String, String> getModelMetadata(Model<SensorMessageItem> model) {
        model.simulate(simulationTime);

        var generator = model.getElements().stream()
                .filter(e -> e.getName().equals("Generator"))
                .findFirst().get();

        var duplex = (BasicProcessor<SensorMessageItem>) model.getElements().stream()
                .filter(e -> e.getName().equals("Duplex"))
                .findFirst().get();

        var eoms = model.getElements().stream()
                .filter(e -> e.getName().startsWith("Eom"))
                .sorted(Comparator.comparing(Element::getName))
                .map(e -> (EomProcessor) e)
                .toList();

        var metadata = new HashMap<String, String>();
        metadata.put("Generated items", String.valueOf(generator.getQuantity()));

        metadata.put("Duplex mean queue", String.valueOf(duplex.getMeanQueue()));
        metadata.put("Duplex mean load", String.valueOf(duplex.getMeanLoad()));
        metadata.put("Duplex quantity", String.valueOf(duplex.getQuantity()));

        for (int i = 0; i < eoms.size(); i++) {
            metadata.put("Eom %s mean queue".formatted(i + 1), String.valueOf(eoms.get(i).getMeanQueue()));
            metadata.put("Eom %s mean load".formatted(i + 1), String.valueOf(eoms.get(i).getMeanLoad()));
            metadata.put("Eom %s quantity".formatted(i + 1), String.valueOf(eoms.get(i).getQuantity()));
            metadata.put("Eom %s cost".formatted(i + 1), String.valueOf(eoms.get(i).getCost()));
            metadata.put("Eom %s failure".formatted(i + 1), String.valueOf(eoms.get(i).getFailure()));
        }

        metadata.put("Emergency mode cost", String.valueOf(eoms.get(0).getEmergencyCost()));

        return metadata;
    }

    private void mergeMetadataToGlobalStatistics(Map<String, String> metadata) {
        for (String key : metadata.keySet()) {
            statistics.merge(key, List.of(metadata.get(key)), (arr1, arr2) -> {
                var arr = new ArrayList<>(arr1);
                arr.addAll(arr2);
                return arr;
            });
        }
    }
}
