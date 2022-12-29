package edu.modelling.specific;

public interface CostAffecting {
    default boolean isCostAffecting() {
        return true;
    }

    double getCost();
}
