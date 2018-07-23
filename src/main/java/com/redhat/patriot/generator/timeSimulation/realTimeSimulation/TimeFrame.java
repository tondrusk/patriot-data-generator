package com.redhat.patriot.generator.timeSimulation.realTimeSimulation;

import net.objecthunter.exp4j.Expression;
import umontreal.ssj.randvar.RandomVariateGen;

/**
 * Created by jsmolar on 7/21/18.
 */
public class TimeFrame {

    private RandomVariateGen timeDistribution;

    private Expression expression;

    private Integer iteration = 0;

    public TimeFrame(RandomVariateGen timeDistribution) {
        this.timeDistribution = timeDistribution;
    }

    public TimeFrame(Expression expression) {
        this.expression = expression;
    }

    public double getTime() {
        if (timeDistribution != null) {
            return 0;
        } else if (expression != null) {
            expression.setVariable("y", iteration);
            iteration++;
            return expression.evaluate();
        } else {
            throw new IllegalArgumentException("Time Distribution or Expression needs to be defined");
        }
    }

}
