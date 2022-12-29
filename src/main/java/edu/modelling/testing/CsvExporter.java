package edu.modelling.testing;

import edu.modelling.elements.Element;

import java.io.FileWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CsvExporter {
    public void export(Map<String, List<String>> data, String fileName) {
        List<String> headers = data.keySet().stream().sorted().toList();

        String path = fileName + ".csv";

        try (var writer = new FileWriter(path, false);){
            for (String string : headers) {
                writer.write(string);
                writer.write(",");
            }
            writer.write("\r\n");

            var rowsCount = data.get(data.keySet().stream().findFirst().get()).size();

            for (var row = 0; row < rowsCount; row++) {
                for (var column : headers) {
                    var cell = data.get(column).get(row);
                    writer.write(cell);
                    writer.write(",");
                }
                writer.write("\r\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
