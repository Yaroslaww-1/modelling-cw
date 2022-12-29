package edu.modelling;

import edu.modelling.testing.ModelFactory;
import edu.modelling.testing.ModelValidator;
import edu.modelling.testing.TestRunner;

public class Main {
    public static void main(String[] args) {
        var modelFactory = new ModelFactory();

//        var modelValidator = new ModelValidator(modelFactory);
//        modelValidator.validate(10_000);

        var testRunner = new TestRunner(modelFactory);
        testRunner.run();
    }
}