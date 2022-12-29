package edu.modelling.elements;

import edu.modelling.elements.arcs.Arc;
import edu.modelling.elements.distributions.Distribution;
import edu.modelling.elements.items.Item;

public abstract class Element<T extends Item> {
    private String name;
    private double tnext;
    private Distribution distribution;
    protected int quantity;
    private double tcurr;
    private int state;
    private Arc arc;
    private static int nextId = 0;
    private int id;

    public Element(Distribution distribution) {
        name = "anonymus";
        tnext = 0.0;
        this.distribution = distribution;
        tcurr = tnext;
        state = 0;
//        nextElement = null;
        id = nextId;
        nextId++;
        name = "element" + id;
    }

    public Element(String nameOfElement, Distribution distribution) {
        name = nameOfElement;
        tnext = 0.0;
        this.distribution = distribution;
        tcurr = tnext;
        state = 0;
//        nextElement = null;
        id = nextId;
        nextId++;
        name = "element" + id;
    }

    public double getDelay() {
        return distribution.generate();
    }

    public void setDistribution(Distribution distribution) {
        this.distribution = distribution;
    }

    public double getTcurr() {
        return tcurr;
    }

    public void setTcurr(double tcurr) {
        this.tcurr = tcurr;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Arc getArc() {
        return arc;
    }

    public void setArc(Arc arc) {
        this.arc = arc;
    }

    public void inAct(T item) {
    }

    public void outAct() {
        quantity++;
    }

    public double getTnext() {
        return tnext;
    }

    public void setTnext(double tnext) {
        this.tnext = tnext;
    }

    public String getResult() {
        return getName() + "  quantity = " + quantity;
    }

    public String getInfo(double tcurr) {
        return getName() + " state= " + state + " quantity = " + quantity + " tnext= " + tnext;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void doStatistics(double delta) {
    }

    public int getQuantity() {return quantity;}
}