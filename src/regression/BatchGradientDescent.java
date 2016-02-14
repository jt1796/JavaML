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
            double[] newCoeff = new double[coeff.getLength()];
            for(int omega = 0; omega < coeff.getLength(); omega++) {
                double sum = 0;
                for(int dataRow = 0; dataRow < dataSet.getDataSize(); dataRow++) {
                    //sum += (coeff.dotProduct(<dataRow>) - response<dataRow>) * omegath element on dataRow
                    sum = sum * stepSize * (-1);
                    newCoeff[omega] = coeff.get(omega) - sum;
                }
            }
            coeff = new Vector(dataSet.getDataSize(), newCoeff);
        }
    }
    
    public Vector getCoeffs() {
        if(null == coeff) {
            throw new RuntimeException("Must call execute() before this call");
        }
        return coeff;
    }
    
}
