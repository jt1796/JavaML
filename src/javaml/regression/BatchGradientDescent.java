/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaml.regression;

import javaml.dataObjects.DataElement;
import javaml.dataObjects.DataSet;
import javaml.mathObjects.Vector;

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
        coeff = new Vector(dataSet.getVarSpan());
        for(int i = 0; i < iterations; i++) {
            double[] newCoeff = new double[coeff.getLength()];
            for(int omega = 0; omega < coeff.getLength(); omega++) {
                double sum = 0;
                for(DataElement dataRow : dataSet) {
                    sum += (coeff.dotProduct(dataRow.independenceAsVector()) - dataRow.getResponse()) * dataRow.getIth(omega);
                }
                sum = sum * stepSize;
                newCoeff[omega] = coeff.get(omega) - sum;
            }
            coeff = new Vector(dataSet.getVarSpan(), newCoeff);
        }
    }
    
    public Vector getCoeffs() {
        if(null == coeff) {
            throw new RuntimeException("Must call execute() before this call");
        }
        return coeff;
    }
    
}
