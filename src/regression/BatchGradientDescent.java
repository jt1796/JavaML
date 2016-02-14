/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regression;

import dataObjects.DataSet;
import javaml.MathObjects.Vector;

/**
 *
 * @author John
 */
public class BatchGradientDescent {
    private double stepSize = 0;
    private int iterations = 0;
    private Vector coeff = null;
    private DataSet dataSet = null;
    
    public BatchGradientDescent(DataSet dataSet, int iterations, double stepSize) {
        this.stepSize = stepSize;
        this.iterations = iterations;
        this.dataSet = dataSet;
    }
    
    public void execute() {
        coeff = new Vector(dataSet.getVarSpan() + 1);
        for(int i = 0; i < iterations; i++) {
            //first compute sum, multiply by stepSize.
            //Then take this sum, and multiply by each x_i corresponding to each coeff, then subtract from that coeff.
        }
    }
    
}
