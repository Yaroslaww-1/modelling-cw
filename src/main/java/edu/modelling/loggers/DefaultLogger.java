package edu.modelling.loggers;

public class DefaultLogger implements Logger {
    private final boolean isDebugEnabled;

    public DefaultLogger(boolean isDebugEnabled) {
        this.isDebugEnabled = isDebugEnabled;
    }

    @Override
    public void info(String message) {
        System.out.println(message);
    }

    @Override
    public void debug(String message) {
        if (isDebugEnabled) {
            System.out.println(message);
        }
    }
}
